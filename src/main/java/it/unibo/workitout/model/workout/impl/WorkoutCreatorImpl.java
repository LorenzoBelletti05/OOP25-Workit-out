package it.unibo.workitout.model.workout.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import it.unibo.workitout.model.main.dataManipulation.loadSaveData;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.workout.contracts.PlannedExercise;
import it.unibo.workitout.model.workout.contracts.WorkoutCreator;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.contracts.WorkoutSheet;

/**
 * Creator class, with the logic for generate the workout plan.
 */
public class WorkoutCreatorImpl implements WorkoutCreator {

    private Boolean userCheckSafety = false;
    private final String pathRawExerciwse = "Workit-out\\src\\main\\resources\\data\\workout\\exercise.json";
    private final List<Exercise> lista;

    /**
     * Costructor that when called it load the exercise.json.
     * 
     * @throws IOException exception.
     */
    public WorkoutCreatorImpl() throws IOException {
        lista = loadSaveData.loadSavedDataFrom(pathRawExerciwse, Exercise[].class);
    }

    /**
     * Method multiplier that moify the variable for the cardio exercise.
     * 
     * @param exercise the row exercise.
     * 
     * @return the multiplier.
     */
    private double getCardioMultiplierPerExercise(final Exercise exercise) {
        final String nameExercise = exercise.getName();        
        if (nameExercise.contains("piscina") || nameExercise.contains("nuoto")) {
            return 0.2; //low distance
        }
        if (nameExercise.contains("ciclismo") || nameExercise.contains("bici")) {
            return 2.5; //high distance
        }
        if (nameExercise.contains("corsa") || nameExercise.contains("running")) {
            return 1.0; //mid distance
        }
        if (nameExercise.contains("mobilità") || nameExercise.contains("stretching")) {
            return 0.5;
        }

        return 1.0; //default
    }

    /**
     * Method multiplier that moify the variable for the strenght exercise.
     * 
     * @param exercise the raw exercise.
     * 
     * @return the multiplier.
    */
    private double getStrenghtMultiplierPerExercise(final Exercise exercise) {
        final String nameExercise = exercise.getName();        
        if (nameExercise.contains("bicipiti") || nameExercise.contains("tricipiti") || nameExercise.contains("spalle")) {
            return 0.8; //low weight
        }
        if (nameExercise.contains("gambe") || nameExercise.contains("squat") || nameExercise.contains("pressa")) {
            return 2.5; //high weight
        }
        if (nameExercise.contains("panca") || nameExercise.contains("dorso") || nameExercise.contains("rematore")) {
            return 1.8; //mid weight
        }
        if (nameExercise.contains("mobilità") || nameExercise.contains("stretching")) {
            return 0.5;
        }
        return 1.0; //default
    }

    public WorkoutPlan generatePlan(
        final double bmr,
        final double tdee,
        final double dailyCalories,
        final ActivityLevel activityLevel,
        final UserGoal userGoal
    ) {
        userCheckSafety = dailyCalories < bmr;
        final List<Exercise> filteredRawExercise = new ArrayList<>();
        int sets = 0;
        int reps = 0;
        double intensityExercise = 1;
        final double calculatedTdee = (tdee <= 0) ? 2000.0 : tdee;
        final double tdeeMultiplier = calculatedTdee / 2100.0;

        double weight = 5;
        int distance = 5;
        int minutes = 10;
        double goalWeightMul = 1.0;
        double goalDistanceMul = 1.0;

        for (final Exercise exercise : lista) {

            final String goals = exercise.getExerciseAttitude(); //get the hole string of goals

            //if the exercise match at least one of the goals then add it
            if (goals.contains(userGoal.name())) {
                filteredRawExercise.add(exercise);
            }
        }
        
        final Random random = new Random();
        switch (userGoal) {
            case BUILD_MUSCLE :

                sets = random.nextInt(4,5);
                reps = random.nextInt(8, 10);
                intensityExercise = 1.5;
                goalWeightMul = 2.0;
                goalDistanceMul = 0.5;
                break;

            case MAINTAIN_WEIGHT:

                sets = random.nextInt(3,4);
                reps = random.nextInt(10, 12);
                intensityExercise = 1;
                goalWeightMul = 1.0;
                goalDistanceMul = 1.0;
                break;

            case GAIN_WEIGHT:

                sets = random.nextInt(1,2);
                reps = random.nextInt(6, 8);
                intensityExercise = 0.4;
                goalWeightMul = 0.3;
                goalDistanceMul = 0.3;
                break;

            case LOSE_WEIGHT:

                sets = random.nextInt(3,4);
                reps = random.nextInt(10, 15);
                intensityExercise = 0.7;
                goalWeightMul = 0.7;
                goalDistanceMul = 2.2; 
                break;

            default:
                break;
        }

        final int currentInsity = (int) (intensityExercise);
        final int activityBonus = activityLevel.ordinal() / 2; //bonus on the activity of the user
        // //calculate the final sets based on the intensdity of the type of exercise previously setted.
        final LocalDate startDate = LocalDate.now();

        //creating the workoutplan with his custom name.
        final WorkoutPlan workoutPlan = new WorkoutPlanImpl("Workout plan" + userGoal.toString());

        for(int j = 0; j < 1 + activityLevel.ordinal(); j++) {
            final String dateNext = startDate.plusDays(j).toString();

            //create the planned exercises with custom name
            final WorkoutSheet workoutSheet = new WorkoutSheetImpl("Workout Sheet: " + userGoal.toString() + " n." + (j+1));

            //here because the next day an exercise can be done again
            final List<Exercise> alreadyUsed = new ArrayList<>(); 

            for(int i = 0; i < random.nextInt(5, 10) + currentInsity; i++) {

                Exercise rawExercise = filteredRawExercise.get(random.nextInt(filteredRawExercise.size()));

                //checking if the exercise has been already selected
                if (alreadyUsed.isEmpty()) {
                    alreadyUsed.add(rawExercise);
                }else if (alreadyUsed.contains(rawExercise)) {
                    while(alreadyUsed.contains(rawExercise)) {
                        rawExercise = filteredRawExercise.get(random.nextInt(filteredRawExercise.size()));
                    }
                    alreadyUsed.add(rawExercise);
                }

                //Create the plan then check which type is: CARDIO or STRENGHT
                final PlannedExercise plannedExercise;

                if (rawExercise.getExerciseType().equals(ExerciseType.STRENGTH)) {

                    //creating a weight based on the type of the exercise, all is corrected from the intensity modifier
                    double finalWeight = weight * tdeeMultiplier * goalWeightMul * this.getStrenghtMultiplierPerExercise(rawExercise) * intensityExercise;

                    finalWeight = Math.min(finalWeight, 120.0);

                    //regenerate sets and reps for diversity
                    int localSets = sets + random.nextInt(-1, 2); 
                    int localReps = reps + random.nextInt(-2, 3);

                    //if the user is under is daily-need it divide the fatique.
                    if(userCheckSafety) {
                        finalWeight *= 0.5;
                    }

                    //the effective inizializzation of the exercise
                    plannedExercise = new StrengthPlannedExerciseImpl(
                        rawExercise, 
                        (int) (minutes * intensityExercise), 
                        Math.max(1, localSets + currentInsity + activityBonus),
                        Math.max(1, localReps + (currentInsity * 2)),
                        finalWeight
                    );

                } else {

                    //creating a distance based on the type of the exercise, all is corrected from the intensity modifier
                    double finalDistance = distance * tdeeMultiplier * goalDistanceMul * this.getCardioMultiplierPerExercise(rawExercise) * intensityExercise;

                    finalDistance = Math.min(finalDistance, 20);
                    if(userCheckSafety) {
                        finalDistance *= 0.5;
                    }

                    plannedExercise = new CardioPlannedExerciseImpl(
                        rawExercise, 
                        (int) (minutes + 5 * intensityExercise),
                        finalDistance
                    );

                }
                //adding the exercise to the sheet
                workoutSheet.addExercise(plannedExercise);
            }
            //adding the sheet to the plan
            workoutPlan.addWorkSheet(dateNext, workoutSheet);
        }
        return workoutPlan;
    }

}
