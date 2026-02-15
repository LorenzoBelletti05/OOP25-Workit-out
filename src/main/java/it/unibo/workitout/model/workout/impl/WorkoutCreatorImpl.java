package it.unibo.workitout.model.workout.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import it.unibo.workitout.model.main.datamanipulation.LoadSaveData;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.workout.contracts.PlannedExercise;
import it.unibo.workitout.model.workout.contracts.WorkoutCreator;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.contracts.WorkoutSheet;

/**
 * Creator class, with the logic for generate the workout plan.
 */
public final class WorkoutCreatorImpl implements WorkoutCreator {

    private static final String PATH_RAW_EXERCISE = "/data/workout/exercise.json";

    //Const for distance and cardio
    private static final double CARDIO_LOW_DIST = 0.2;
    private static final double CARDIO_HIGH_DIST = 2.5;
    private static final double CARDIO_MID_DIST = 1.0;

    //Const for weight and strenght
    private static final double STRETCHING_MUL = 0.5;
    private static final double STRENGTH_LOW_W = 0.8;
    private static final double STRENGTH_HIGH_W = 2.5;
    private static final double STRENGTH_MID_W = 1.8;

    //Const for minutes
    private static final double MIN_PER_KM_DEFAULT = 6.0;
    private static final double MIN_PER_KM_BIKE = 1.5;
    private static final double MIN_PER_KM_SWIM = 15.0;
    private static final double MIN_PER_KM_WALK = 15.0;

    //Costants for calcs and limits
    private static final double TDEE_NORMALIZER = 2100.0;
    private static final double SAFETY_FACTOR = 0.5;
    private static final double MAX_WEIGHT_LIMIT = 120.0;
    private static final double MAX_DISTANCE_LIMIT = 20.0;

    //Const for random and base misure
    private static final int BASE_SETS_STRENGTH = 3;
    private static final int BASE_REPS_STRENGTH = 10;
    private static final int WEIGHT_VARIATION_RANGE = 11;
    private static final int DISTANCE_VARIATION_RANGE = 3;
    private static final double WEIGHT_ROUND_FACTOR = 2.0;
    private static final double DISTANCE_ROUND_FACTOR = 100.0;
    private static final int MIN_WORKOUT_MINUTES = 5;

    //Switch constant
    private static final double INTENSITY_HIGH = 1.5;
    private static final double INTENSITY_LOW = 0.4;
    private static final double INTENSITY_MID_LOW = 0.7;
    private static final double INTENSITY_MID_HIGH = 1;

    private static final double MUL_HIGH = 2.0;
    private static final double MUL_VERY_HIGH = 2.2;
    private static final double MUL_LOW = 0.3;
    private static final double MUL_MID = 0.3;

    //Switch loop const
    //Const VERY_LOW
    private static final int DAYS_VERY_LOW = 0;
    private static final int EXERCISES_VERY_LOW = 0;

    //Const LOW
    private static final int DAYS_LOW = 2;
    private static final int EXERCISES_LOW = 3;

    //Const MODERATE
    private static final int DAYS_MODERATE = 4;
    private static final int EXERCISES_MODERATE = 5;

    //Const HIGH
    private static final int DAYS_HIGH = 6;
    private static final int EXERCISES_HIGH = 7;

    //Const VERY_HIGH
    private static final int DAYS_VERY_HIGH = 7;
    private static final int EXERCISES_VERY_HIGH = 9;

    //CONST for RANDOM
    //Const BUILD_MUSCLE
    private static final int MUSCLE_MIN_SETS = 4;
    private static final int MUSCLE_MAX_SETS = 5;
    private static final int MUSCLE_MIN_REPS = 8;
    private static final int MUSCLE_MAX_REPS = 10;

    //Const MAINTAIN_WEIGHT
    private static final int MAINTAIN_MIN_SETS = 3;
    private static final int MAINTAIN_MAX_SETS = 4;
    private static final int MAINTAIN_MIN_REPS = 10;
    private static final int MAINTAIN_MAX_REPS = 12;

    //Const GAIN_WEIGHT
    private static final int GAIN_MIN_SETS = 1;
    private static final int GAIN_MAX_SETS = 2;
    private static final int GAIN_MIN_REPS = 6;
    private static final int GAIN_MAX_REPS = 8;

    //Const LOSE_WEIGHT
    private static final int LOSE_MIN_SETS = 3;
    private static final int LOSE_MAX_SETS = 4;
    private static final int LOSE_MIN_REPS = 10;
    private static final int LOSE_MAX_REPS = 15;

    //Const
    private static final int REPS_REDUCTION_OFFSET = -2;
    private static final int DEFAULT_WEIGHT = 5;
    private static final int DEFAULT_DISTANCE = 5;

    private final List<Exercise> listExercise;
    private final Random random = new Random();

    /**
     * Constructor that when called it load the exercise.json.
     * 
     * @throws IOException exception.
     */
    public WorkoutCreatorImpl() throws IOException {
        listExercise = LoadSaveData.loadFromResources(PATH_RAW_EXERCISE, Exercise[].class);
    }

    /**
     * Method multiplier that modify the variable for the cardio exercise.
     * 
     * @param exercise the row exercise.
     * 
     * @return the multiplier.
     */
    private double getCardioMultiplierPerExercise(final Exercise exercise) {

        final String nameExercise = exercise.getName();
        if (nameExercise.contains("piscina") || nameExercise.contains("nuoto")) {
            return CARDIO_LOW_DIST; //low distance
        }
        if (nameExercise.contains("ciclismo") || nameExercise.contains("bici")) {
            return CARDIO_HIGH_DIST; //high distance
        }
        if (nameExercise.contains("corsa") || nameExercise.contains("running")) {
            return CARDIO_MID_DIST; //mid distance
        }
        if (nameExercise.contains("mobilità") || nameExercise.contains("stretching")) {
            return STRETCHING_MUL;
        }

        return 1.0; //default
    }

    /**
     * Method multiplier that modify the variable for the strenght exercise.
     * 
     * @param exercise the raw exercise.
     * 
     * @return the multiplier.
     */
    private double getStrenghtMultiplierPerExercise(final Exercise exercise) {
        final String nameExercise = exercise.getName();
        if (nameExercise.contains("bicipiti") || nameExercise.contains("tricipiti") || nameExercise.contains("spalle")) {
            return STRENGTH_LOW_W; //low weight
        }
        if (nameExercise.contains("gambe") || nameExercise.contains("squat") || nameExercise.contains("pressa")) {
            return STRENGTH_HIGH_W; //high weight
        }
        if (nameExercise.contains("panca") || nameExercise.contains("dorso") || nameExercise.contains("rematore")) {
            return STRENGTH_MID_W; //mid weight
        }

        return 1.0; //default
    }

    /**
     * Method multiplier that modify the variable for the minutes cardio exercise.
     * 
     * @param exercise the raw exercise.
     * 
     * @return the multiplier.
     */
    private double getMinutesMultiplier(final Exercise exercise) {

        final String name = exercise.getName().toLowerCase(Locale.ROOT);

        if (name.contains("ciclismo") || name.contains("bici")) {
            return MIN_PER_KM_BIKE;
        } else if (name.contains("nuoto") || name.contains("piscina")) {
            return MIN_PER_KM_SWIM;
        } else if (name.contains("camminata")) {
            return MIN_PER_KM_WALK;
        }

        return MIN_PER_KM_DEFAULT;
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
                daysToGenerate = DAYS_VERY_LOW;
                avgExercises = EXERCISES_VERY_LOW;
                break;
            case LOW:
                daysToGenerate = DAYS_LOW; 
                avgExercises = EXERCISES_LOW;
                break;
            case MODERATE:
                daysToGenerate = DAYS_MODERATE;
                avgExercises = EXERCISES_MODERATE;
                break;
            case HIGH:
                daysToGenerate = DAYS_HIGH;
                avgExercises = EXERCISES_HIGH;
                break;
            case VERY_HIGH:
                daysToGenerate = DAYS_VERY_HIGH;
                avgExercises = EXERCISES_VERY_HIGH;
                break;
        }

        final List<Integer> dataGeneration = new ArrayList<>();
        dataGeneration.add(daysToGenerate);
        dataGeneration.add(avgExercises);
        return dataGeneration;
    }

    /** {@inheritDoc} */
    @Override
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
        final double calculatedTdee = (tdee <= 0) ? TDEE_NORMALIZER : tdee;
        final double tdeeMultiplier = calculatedTdee / TDEE_NORMALIZER;
        double intensityExercise = 1;
        double goalWeightMul = 1.0;
        double goalDistanceMul = 1.0;

        //variable for number of day and exercise a day.
        final List<Integer> dataGenerationExercise = calculateLoopExercise(activityLevel);
        final Integer daysExercise = dataGenerationExercise.get(0);
        final Integer avgExerciseDay = dataGenerationExercise.get(1);

        //variable for data calculation
        int sets = BASE_SETS_STRENGTH;
        int reps = BASE_REPS_STRENGTH;

        final int currentInsity = (int) intensityExercise;
        final int activityBonus = activityLevel.ordinal() / 2; //bonus on the activity of the user

        final LocalDate startDate = LocalDate.now();

        final WorkoutPlan workoutPlan = new WorkoutPlanImpl("Workout plan" + userGoal.toString());

        final String targetGoalName = userGoal.name().toLowerCase(Locale.ROOT); // es: "lose_weight"
        final String targetGoalDesc = userGoal.toString().toLowerCase(Locale.ROOT).replace(" ", "");

        //filter the exercise, on the goal.
        for (final Exercise exercise : listExercise) {
            final String goals = exercise.getExerciseAttitude().toLowerCase(Locale.ROOT).trim();
            if (goals.contains(targetGoalName) || goals.contains(targetGoalDesc)) {
                filteredRawExercise.add(exercise);
            }
        }

        System.out.println("DEBUG: Sto cercando esercizi per: " + userGoal.name() + " (" + userGoal.toString() + ")");

        switch (userGoal) {
            case BUILD_MUSCLE:
                sets = random.nextInt(MUSCLE_MIN_SETS, MUSCLE_MAX_SETS);
                reps = random.nextInt(MUSCLE_MIN_REPS, MUSCLE_MAX_REPS);
                intensityExercise = INTENSITY_HIGH;
                goalWeightMul = MUL_HIGH;
                goalDistanceMul = SAFETY_FACTOR;
                break;

            case MAINTAIN_WEIGHT:
                sets = random.nextInt(MAINTAIN_MIN_SETS, MAINTAIN_MAX_SETS);
                reps = random.nextInt(MAINTAIN_MIN_REPS, MAINTAIN_MAX_REPS);
                intensityExercise = INTENSITY_MID_HIGH;
                goalWeightMul = MUL_MID;
                goalDistanceMul = MUL_MID;
                break;

            case GAIN_WEIGHT:
                sets = random.nextInt(GAIN_MIN_SETS, GAIN_MAX_SETS);
                reps = random.nextInt(GAIN_MIN_REPS, GAIN_MAX_REPS);
                intensityExercise = INTENSITY_LOW; 
                goalWeightMul = MUL_LOW; 
                goalDistanceMul = MUL_LOW;
                break;

            case LOSE_WEIGHT:
                sets = random.nextInt(LOSE_MIN_SETS, LOSE_MAX_SETS);
                reps = random.nextInt(LOSE_MIN_REPS, LOSE_MAX_REPS);
                intensityExercise = INTENSITY_MID_LOW; 
                goalWeightMul = INTENSITY_MID_LOW; 
                goalDistanceMul = MUL_VERY_HIGH;
                break;

        }

        for (int j = 0; j < daysExercise; j++) {
            final String dateNext = startDate.plusDays(j).toString();

            final WorkoutSheet workoutSheet = new WorkoutSheetImpl("Workout Sheet: " + userGoal.toString() + " n." + (j + 1));

            //here because the next day an exercise can be done again
            final List<Exercise> alreadyUsed = new ArrayList<>(); 

            for (int i = 0; i < avgExerciseDay + random.nextInt(0, 2) + currentInsity; i++) {

                if (alreadyUsed.size() >= filteredRawExercise.size()) {
                    break; 
                }

                Exercise rawExercise;

                do {
                    rawExercise = filteredRawExercise.get(random.nextInt(filteredRawExercise.size()));
                } while (alreadyUsed.contains(rawExercise));

                alreadyUsed.add(rawExercise);

                final PlannedExercise plannedExercise;

                if (rawExercise.getExerciseType() == ExerciseType.STRENGTH) {

                    double finalWeight = DEFAULT_WEIGHT + this.random.nextInt(WEIGHT_VARIATION_RANGE)
                        * tdeeMultiplier 
                        * goalWeightMul 
                        * this.getStrenghtMultiplierPerExercise(rawExercise) 
                        * intensityExercise;

                    final int localSets = sets + random.nextInt(-1, 2); 
                    final int localReps = reps + random.nextInt(REPS_REDUCTION_OFFSET, 3);

                    //if the user is under is daily-need it divide the fatique.
                    if (userCheckSafety) {
                        finalWeight *= SAFETY_FACTOR;
                    }

                    finalWeight = Math.round(finalWeight * WEIGHT_ROUND_FACTOR) / WEIGHT_ROUND_FACTOR;

                    finalWeight = Math.min(finalWeight, MAX_WEIGHT_LIMIT);

                    plannedExercise = new StrengthPlannedExerciseImpl(
                        rawExercise, 
                        localSets * 3, //indicative time based on sets * 3 (minutes)
                        Math.max(1, localSets + currentInsity + activityBonus),
                        Math.max(1, localReps + (currentInsity * 2)),
                        finalWeight
                    );
                } else {

                    double finalDistance = DEFAULT_DISTANCE + (this.random.nextDouble() * DISTANCE_VARIATION_RANGE - 1)
                        * tdeeMultiplier
                        * goalDistanceMul
                        * this.getCardioMultiplierPerExercise(rawExercise)
                        * intensityExercise;

                    finalDistance = Math.round(finalDistance * DISTANCE_ROUND_FACTOR) / DISTANCE_ROUND_FACTOR;

                    finalDistance = Math.min(finalDistance, MAX_DISTANCE_LIMIT);

                    final double minPerKm = getMinutesMultiplier(rawExercise);

                    int finalMinutes = (int) Math.round((finalDistance * minPerKm) + random.nextInt(REPS_REDUCTION_OFFSET, 2));

                    //Security check
                    finalMinutes = Math.max(MIN_WORKOUT_MINUTES, finalMinutes);

                    if (userCheckSafety) {
                        finalDistance *= SAFETY_FACTOR;
                    }

                    plannedExercise = new CardioPlannedExerciseImpl(
                        rawExercise,
                        finalMinutes,
                        finalDistance
                    );

                }
                workoutSheet.addExercise(plannedExercise);
            }

            if (!workoutSheet.getWorkoutSheet().isEmpty()) {
                workoutPlan.addWorkSheet(dateNext, workoutSheet);
            }
        }
        return workoutPlan;
    }

}
