package it.unibo.workitout.controller.wiki.impl;

import it.unibo.workitout.controller.wiki.contracts.WikiController; 
import it.unibo.workitout.view.wiki.contracts.WikiView;
import it.unibo.workitout.model.food.api.Meal;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.wiki.contracts.Video;
import it.unibo.workitout.model.wiki.contracts.Wiki;
import it.unibo.workitout.model.wiki.contracts.WikiContent;
import it.unibo.workitout.model.wiki.impl.SmartSuggestionImpl;
import it.unibo.workitout.model.wiki.impl.WikiRepositoryImpl;
import it.unibo.workitout.model.workout.impl.Exercise;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Implementation of WikiController.
 */
public class WikiControllerImpl implements WikiController {
    private final Wiki model;
    private final WikiView view;
    private final SmartSuggestionImpl smartSuggestion = new SmartSuggestionImpl();
    private final System.Logger logger = System.getLogger(WikiControllerImpl.class.getName());

    private UserProfile user;
    private List<Exercise> exercises;
    private Meal meal;

    /**
     * Constructor.
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
                    logger.log(System.Logger.Level.ERROR, "Errore nell'apertura del video: " + video.getUrl(), e);
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

            if (currentExercises != null) {
                this.exercises = new ArrayList<>(currentExercises);
            } else {
                this.exercises = null;
            }

            this.meal = currentMeal;
            this.view.updateContents(this.smartSuggestion.suggest(
                this.model, 
                this.user, 
                this.exercises, 
                this.meal
            ));
        }
    }
}
