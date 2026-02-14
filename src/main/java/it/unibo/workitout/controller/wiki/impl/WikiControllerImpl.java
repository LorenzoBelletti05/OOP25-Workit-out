package it.unibo.workitout.controller.wiki.impl;

import it.unibo.workitout.controller.wiki.contracts.WikiController; 
import it.unibo.workitout.view.wiki.contracts.WikiView;
import it.unibo.workitout.model.food.api.Meal;
import it.unibo.workitout.model.main.datamanipulation.LoadSaveData;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.wiki.contracts.Video;
import it.unibo.workitout.model.wiki.contracts.Wiki;
import it.unibo.workitout.model.wiki.contracts.WikiContent;
import it.unibo.workitout.model.wiki.impl.SmartSuggestionImpl;
import it.unibo.workitout.model.wiki.impl.WikiRepositoryImpl;
import it.unibo.workitout.model.workout.impl.Exercise;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import it.unibo.workitout.controller.workout.impl.UserExerciseControllerImpl;
import it.unibo.workitout.model.workout.contracts.PlannedExercise;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.food.api.Food;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Implementation of WikiController.
 */
public class WikiControllerImpl implements WikiController {
    private static final int MAX_DISPLAY_ITEMS = 3;

    private final Wiki model;
    private final SmartSuggestionImpl smartSuggestion = new SmartSuggestionImpl();
    private final WikiView view;

    private UserProfile user;
    private List<Exercise> exercises;
    private Meal meal;

    /**
     * Builder.
     * 
     * @param model wiki model.
     * @param view wiki view.
     */
    @SuppressFBWarnings(value = "EI2", justification = "The model must been shared between controller and view.")
    public WikiControllerImpl(final Wiki model, final WikiView view) {
        this.model = model;
        this.view = view;

        final WikiRepositoryImpl repository = new WikiRepositoryImpl();
        repository.loadAll(this.model);
    }

    /**
     * Start the model/view.
     */
    @Override
    public void start() {
        this.view.updateContents(this.model.getContents());

        this.view.addSelectionListener(content -> {
            if (content.isVideo()) {
                final Video video = (Video) content;
                try {
                    this.view.showVideoPlayer(video.getUrl().toString());
                } catch (final URISyntaxException e) {
                    JOptionPane.showMessageDialog(
                        null, 
                        "Errore nell'apertura del video: " + video.getUrl(), 
                        "Errore", JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                this.view.showDetail(content.getTitle(), content.getText());
            }
        });

        this.view.addBackListener(this.view::showList);

        this.view.addSearchListener(() -> 
            this.view.updateContents(this.model.search(this.view.getSearchQuery()))
        );

        this.view.addAllFilterListener(() -> 
            this.view.updateContents(this.model.getContents())
        );

        this.view.addArticlesFilterListener(() -> 
            this.view.updateContents(this.model.getContents().stream()
                .filter(c -> !c.isVideo())
                .collect(Collectors.toSet()))
        );

        this.view.addVideosFilterListener(() -> 
            this.view.updateContents(this.model.getContents().stream()
                .filter(WikiContent::isVideo)
                .collect(Collectors.toSet()))
        );

        this.view.addPrioFoodListener(() -> {
            if (user != null) {
                this.view.updateContents(this.smartSuggestion.suggest(model, user, null, meal));
            }
        });

        this.view.addPrioExerciseListener(() -> {
            if (user != null) {
                this.view.updateContents(this.smartSuggestion.suggest(model, user, exercises, null));
            }
        });
    }

    /**
     * New view with smart suggestions.
     * 
     * @param userProfile the current user.
     * @param currentExercises the current exercises.
     * @param currentMeal the current meal.
     */
    @Override
    public void showSmartSuggestions(
        final UserProfile userProfile, 
        final List<Exercise> currentExercises, 
        final Meal currentMeal) {
        //user
        if (userProfile != null) {
            this.user = new UserProfile(
                userProfile.getId(),
                userProfile.getName(),
                userProfile.getSurname(),
                userProfile.getAge(),
                userProfile.getHeight(),
                userProfile.getWeight(),
                userProfile.getSex(),
                userProfile.getActivityLevel(),
                userProfile.getUserGoal(),
                userProfile.getStrategy()
            );
            //exercises
            if (currentExercises != null) {
                this.exercises = new ArrayList<>(currentExercises);
            } else {
                this.exercises = null;
            }
            //meal
            this.meal = currentMeal;
            //update the view
            this.view.updateContents(this.smartSuggestion.suggest(
                this.model, 
                this.user, 
                this.exercises, 
                this.meal
            ));
        }
    }

    /**
     * Update the wiki with the current user data.
     * 
     * @param currentUser the current user.
     */
    public void updateWithCurrentData(final UserProfile currentUser) {
        if (currentUser == null) {
            return;
        }
        final List<Exercise> currentExercises = loadExercisesFromPlan();
        final Meal currentMeal = loadTodayMealFromHistory();
        final StringBuilder feedback = new StringBuilder(100);
        feedback.append("<html>");
        //exercises
        if (currentExercises != null && !currentExercises.isEmpty()) {
            feedback.append("Esercizi trovati: ")
                .append(currentExercises.stream()
                .map(Exercise::getName)
                .distinct()
                .limit(MAX_DISPLAY_ITEMS)
                .collect(Collectors.joining(", ")));
            if (currentExercises.size() > MAX_DISPLAY_ITEMS) {
                feedback.append("...");
            }
        } else {
            feedback.append("Nessun esercizio nel piano.");
        }
        feedback.append("<br>");
        //meal
        if (currentMeal != null && currentMeal.getFood() != null && !currentMeal.getFood().isEmpty()) {
            feedback.append("Cibi oggi: ")
                .append(currentMeal.getFood().stream()
                .map(Food::getName)
                .distinct()
                .limit(MAX_DISPLAY_ITEMS)
                .collect(Collectors.joining(", ")));
            if (currentMeal.getFood().size() > MAX_DISPLAY_ITEMS) {
                feedback.append("...");
            }
        } else {
            feedback.append("Nessun cibo registrato oggi.");
        }
        feedback.append("</html>");
        //update label
        this.view.updateLabel(feedback.toString());
        showSmartSuggestions(currentUser, currentExercises, currentMeal);
    }

    /**
     * Load exercises from the workout plan.
     * 
     * @return list of exercises.
     */
    private List<Exercise> loadExercisesFromPlan() {
        final WorkoutPlan plan = UserExerciseControllerImpl.getInstance().getGeneratedWorkoutPlan();
        if (plan.getAllExercise() != null) {
            return plan.getAllExercise().stream()
                .map(PlannedExercise::getExercise)
                .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Load today meal from history.csv.
     * 
     * @return a meal with today's foods.
     */
    private Meal loadTodayMealFromHistory() {
        //load history
        final List<String> history = LoadSaveData.loadCsvFile(
            LoadSaveData.createPath(LoadSaveData.HISTORY_FILE));
        final String today = LocalDate.now().toString();
        //get food names
        final List<String> foodNames = history.stream()
            .filter(line -> line.startsWith(today) && line.split(",").length > 1)
            .map(line -> line.split(",")[1])
            .distinct()
            .collect(Collectors.toList());

        if (foodNames.isEmpty()) {
            return null;
        }

        //create foods
        final List<Food> foods = new ArrayList<>();
        for (final String name : foodNames) {
            foods.add(new Food() {
                @Override
                public String getName() { 
                    return name; 
                }

                @Override
                public double getKcalPer100g() { 
                    return 0; 
                }

                @Override
                public double getProteins() { 
                    return 0; 
                }

                @Override
                public double getCarbs() { 
                    return 0; 
                }

                @Override
                public double getFats() { 
                    return 0; 
                }
            });
        }

        return new Meal() {
            @Override
            public String getTime() { 
                return today; 
            }

            @Override
            public List<Food> getFood() { 
                return List.copyOf(foods); 
            }
        };
    }
}
