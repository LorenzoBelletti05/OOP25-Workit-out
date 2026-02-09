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

    private final String pathRawExerciwse = "/data/workout/exercise.json";
    private final List<Exercise> lista;

    /**
     * Costructor that when called it load the exercise.json.
     * 
     * @throws IOException exception.
     */
    public WorkoutCreatorImpl() throws IOException {
        lista = loadSaveData.loadFromResources(pathRawExerciwse, Exercise[].class);

        //DEBUG
        if (this.lista.isEmpty()) {
            System.out.println("DEBUG:WorkoutCreator Lista esercizi vuota. Controlla il percorso delle risorse.");
        } else {
            System.out.println("DEBUG:WorkoutCreator " + this.lista.size() + " esercizi caricati correttamente.");
        }
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

    /**
     * Method multiplier that moify the variable for the minutes cardio exercise.
     * 
     * @param exercise the raw exercise.
     * 
     * @return the multiplier.
     */
    private double getMinutesMultiplier(final Exercise exercise) {

        double minPerKm = 6.0;
        final String name = exercise.getName().toLowerCase();

        if (name.contains("ciclismo") || name.contains("bici")) {
            minPerKm = 1.5;
        } else if (name.contains("nuoto") || name.contains("piscina")) {
            minPerKm = 15.0;
        } else if (name.contains("camminata")) {
            minPerKm = 6.0;
        }

        return minPerKm;
    }

    /**
     * Method that based on the activity return the ammount of workout day and exercise a day.
     * 
     * @param activityLevel the preference of the user.
     * @return the list of the two data calculated.
     */
    private List<Integer> calculateLoopExercise(final ActivityLevel activityLevel) {
        Integer daysToGenerate = 0;
        Integer avgExercises = 0;

        switch (activityLevel) {
            case VERY_LOW:
                daysToGenerate = 0;
                avgExercises = 0;
                break;
            case LOW:
                daysToGenerate = 2; 
                avgExercises = 3;
                break;
            case MODERATE:
                daysToGenerate = 4;
                avgExercises = 5;
                break;
            case HIGH:
                daysToGenerate = 6;
                avgExercises = 7;
                break;
            case VERY_HIGH:
                daysToGenerate = 7;
                avgExercises = 9;
                break;
            }

        final List<Integer> dataGeneration = new ArrayList<>();
        dataGeneration.add(daysToGenerate);
        dataGeneration.add(avgExercises);
        return dataGeneration;
    }

    @Override
    /** {@inheritDoc} */
    public WorkoutPlan generatePlan(
        final double bmr,
        final double tdee,
        final double dailyCalories,
        final ActivityLevel activityLevel,
        final UserGoal userGoal
    ) {
        //variable for healty of the user
        final Boolean userCheckSafety = dailyCalories < bmr;

        //list of exercise based on the filter
        final List<Exercise> filteredRawExercise = new ArrayList<>();

        //variable multiplier
        final double calculatedTdee = (tdee <= 0) ? 2000.0 : tdee;
        final double tdeeMultiplier = calculatedTdee / 2100.0;
        double intensityExercise = 1;
        double goalWeightMul = 1.0;
        double goalDistanceMul = 1.0;

        //variable for number of day and exercise a day.
        final List<Integer> dataGenerationExercise = calculateLoopExercise(activityLevel);
        final Integer daysExercise = dataGenerationExercise.get(0);
        final Integer avgExerciseDay = dataGenerationExercise.get(1);

        //variable for data calculation
        int sets = 0;
        int reps = 0;
        final int distance = 5;
        int minutes = 10;
        double weight = 5;

        //Random variable for fiversity
        final Random random = new Random();

        final int currentInsity = (int) intensityExercise;
        final int activityBonus = activityLevel.ordinal() / 2; //bonus on the activity of the user

        //calculate the final sets based on the intensdity of the type of exercise previously setted.
        final LocalDate startDate = LocalDate.now();

        //creating the workoutplan with his custom name.
        final WorkoutPlan workoutPlan = new WorkoutPlanImpl("Workout plan" + userGoal.toString());

        //for to filter the exercise based on the goal.
        for (final Exercise exercise : lista) {
            final String goals = exercise.getExerciseAttitude();
            if (goals.contains(userGoal.name())) {
                filteredRawExercise.add(exercise);
            }
        }

        switch (userGoal) {
            case BUILD_MUSCLE:

                sets = random.nextInt(4, 5);
                reps = random.nextInt(8, 10);
                intensityExercise = 1.5;
                goalWeightMul = 2.0;
                goalDistanceMul = 0.5;
                break;

            case MAINTAIN_WEIGHT:

                sets = random.nextInt(3, 4);
                reps = random.nextInt(10, 12);
                intensityExercise = 1;
                goalWeightMul = 1.0;
                goalDistanceMul = 1.0;
                break;

            case GAIN_WEIGHT:

                sets = random.nextInt(1, 2);
                reps = random.nextInt(6, 8);
                intensityExercise = 0.4;
                goalWeightMul = 0.3;
                goalDistanceMul = 0.3;
                break;

            case LOSE_WEIGHT:

                sets = random.nextInt(3, 4);
                reps = random.nextInt(10, 15);
                intensityExercise = 0.7;
                goalWeightMul = 0.7;
                goalDistanceMul = 2.2; 
                break;

            default:
                break;
        }

        for (int j = 0; j < daysExercise; j++) {
            final String dateNext = startDate.plusDays(j).toString();

            //create the planned exercises with custom name
            final WorkoutSheet workoutSheet = new WorkoutSheetImpl("Workout Sheet: " + userGoal.toString() + " n." + (j+1));

            //here because the next day an exercise can be done again
            final List<Exercise> alreadyUsed = new ArrayList<>(); 

            for (int i = 0; i < avgExerciseDay + random.nextInt(0, 2) + currentInsity; i++) {

                Exercise rawExercise = filteredRawExercise.get(random.nextInt(filteredRawExercise.size()));

                //checking if the exercise has been already selected
                if (alreadyUsed.isEmpty()) {
                    alreadyUsed.add(rawExercise);
                } else if (alreadyUsed.contains(rawExercise)) {
                    while (alreadyUsed.contains(rawExercise)) {
                        rawExercise = filteredRawExercise.get(random.nextInt(filteredRawExercise.size()));
                    }
                    alreadyUsed.add(rawExercise);
                }

                //Create the plan then check which type is: CARDIO or STRENGHT
                final PlannedExercise plannedExercise;

                if (rawExercise.getExerciseType().equals(ExerciseType.STRENGTH)) {

                    final double baseWeight = weight + random.nextInt(0, 11);

                    //creating a weight based on the type of the exercise, all is corrected from the intensity modifier
                    double finalWeight = baseWeight
                        * tdeeMultiplier 
                        * goalWeightMul 
                        * this.getStrenghtMultiplierPerExercise(rawExercise) 
                        * intensityExercise;

                    finalWeight = Math.min(finalWeight, 120.0);

                    //regenerate sets and reps for diversity
                    int localSets = sets + random.nextInt(-1, 2); 
                    int localReps = reps + random.nextInt(-2, 3);

                    //if the user is under is daily-need it divide the fatique.
                    if (userCheckSafety) {
                        finalWeight *= 0.5;
                    }

                    //Delete the decimal
                    finalWeight = Math.round(finalWeight * 2) / 2.0;

                    //Security check for the weight
                    finalWeight = Math.min(finalWeight, 120.0);

                    //the effective inizializzation of the exercise
                    plannedExercise = new StrengthPlannedExerciseImpl(
                        rawExercise, 
                        (int) (localSets * 3), //indicative time based on sets * 3 (minutes)
                        Math.max(1, localSets + currentInsity + activityBonus),
                        Math.max(1, localReps + (currentInsity * 2)),
                        finalWeight
                    );

                } else {

                    final double baseDistance = distance + (random.nextDouble() * 3 - 1);

                    //creating a distance based on the type of the exercise, all is corrected from the intensity modifier
                    double finalDistance = baseDistance
                        * tdeeMultiplier
                        * goalDistanceMul
                        * this.getCardioMultiplierPerExercise(rawExercise)
                        * intensityExercise;

                    //Remove deciamel
                    finalDistance = Math.round(finalDistance * 100.0) / 100.0;

                    //Security check
                    finalDistance = Math.min(finalDistance, 20.0);

                    //the dinamic minutes based on distance
                    final double minPerKm = getMinutesMultiplier(rawExercise);

                    int finalMinutes = (int) Math.round((finalDistance * minPerKm) + random.nextInt(-2, 2));

                    //Security check
                    finalMinutes = Math.max(5, finalMinutes);

                    if (userCheckSafety) {
                        finalDistance *= 0.5;
                    }

                    plannedExercise = new CardioPlannedExerciseImpl(
                        rawExercise,
                        finalMinutes,
                        finalDistance
                    );

                }
                //adding the exercise to the sheet
                workoutSheet.addExercise(plannedExercise);
            }
            //adding the sheet to the plan only if ther's a sheet
            if (!workoutSheet.getWorkoutSheet().isEmpty()) {
                workoutPlan.addWorkSheet(dateNext, workoutSheet);
            }
        }
        return workoutPlan;
    }

}
