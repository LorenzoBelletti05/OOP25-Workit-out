package it.unibo.workitout.model.main.datamanipulation;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.unibo.workitout.model.main.WorkoutUserData;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.impl.WorkoutPlanImpl;

/**
 * Class for loading and saving data.
 */
public final class LoadSaveData { 

    /** Public constant for foods file name. */
    public static final String FOODS_FILE = "foods.csv";

    /** Public constant for history file name. */
    public static final String HISTORY_FILE = "history.csv";

    /** Public constant for stats file name. */
    public static final String STATS_FILE = "daily_stats.csv";

    /** Base path of the application. */
    private static final String APP_DIR = ".workitout";

    private static final Gson GSON = new Gson();

    private LoadSaveData() {
    }

    /**
     * Gets the workspace path.
     * 
     * @return the path string
     */
    public static String getWorkspacePath() {
        return System.getProperty("user.home") + File.separator + APP_DIR;
    }

    /**
     * Create full path for a file in the workspace.
     * 
     * @param fileName name of the file
     * @return path string
     */
    public static String createPath(final String fileName) {
        return getWorkspacePath() + File.separator + fileName;
    }

    private static void checkFolderPresence(final String pathData) {
        final File file = new File(pathData);
        final File parDir = file.getParentFile();
        if (parDir != null && !parDir.exists() && !parDir.mkdirs()) {
            throw new IllegalStateException("Could not create directory: " + parDir.getAbsolutePath());
        }
    }

    /**
     * Load list of objects from a JSON.
     * 
     * @param <T> Objects.
     * @param pathData path.
     * @param typeClass class of arrays.
     * @return list of objects/null.
     * @throws IOException IO error.
     */
    public static <T> List<T> loadSavedDataFrom(final String pathData, final Class<T[]> typeClass) throws IOException {
        checkFolderPresence(pathData);
        try (FileReader read = new FileReader(pathData, StandardCharsets.UTF_8)) {
            final T[] arrayData = GSON.fromJson(read, typeClass);
            if (arrayData != null) {
                return new ArrayList<>(Arrays.asList(arrayData));
            }
            return new ArrayList<>();
        } catch (final JsonSyntaxException e) {
            throw new IOException("Error JSON from " + pathData, e);
        }
    }

    /**
     * Save list of objects to a JSON.
     * 
     * @param <T> Objects.
     * @param pathData path.
     * @param dataClass objects.
     * @throws IOException IO error.
     */
    public static <T> void saveDataIn(final String pathData, final Class<T[]> dataClass) throws IOException {
        checkFolderPresence(pathData);
        try (FileWriter writer = new FileWriter(pathData, StandardCharsets.UTF_8)) {
            GSON.toJson(dataClass, writer);
        } catch (final IOException e) {
            throw new IOException("Error saving data to " + pathData, e);
        }
    }

    /**
     * Load WorkoutPlan from JSON.
     * 
     * @param pathData path.
     * @return plan/null.
     */
    public static WorkoutPlan loadWorkoutPlan(final String pathData) {
        checkFolderPresence(pathData);
        try (FileReader read = new FileReader(pathData, StandardCharsets.UTF_8)) {
            return GSON.fromJson(read, WorkoutPlanImpl.class);
        } catch (final IOException | JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * Save WorkoutPlan to JSON.
     * 
     * @param pathData path.
     * @param dataPlan plan for workout.
     * @throws IOException IO error.
     */
    public static void saveWorkoutPlan(final String pathData, final WorkoutPlan dataPlan) throws IOException {
        checkFolderPresence(pathData);
        try (FileWriter writer = new FileWriter(pathData, StandardCharsets.UTF_8)) {
            GSON.toJson(dataPlan, writer);
            System.out.println("DEBUG: Scrittura completata con successo!");
        } catch (Throwable t) {
            System.err.println("ERRORE FATALE DURANTE IL SALVATAGGIO");
            t.printStackTrace(); 
            throw new IOException("Errore GSON: " + t.getMessage(), t);
        }
    }

    /**
     * Save workout user data.
     * 
     * @param pathData path.
     * @param workoutUserData data.
     * @throws IOException IO error.
     */
    public static void saveWorkoutuserDataIn(final String pathData, final WorkoutUserData workoutUserData) throws IOException {
        checkFolderPresence(pathData);
        try (FileWriter writer = new FileWriter(pathData, StandardCharsets.UTF_8)) {
            GSON.toJson(workoutUserData, writer);
        } catch (final IOException e) {
            throw new IOException("Error saving workout user data: " + e.getMessage(), e);
        }
    }

    /**
     * Load workout user data.
     * 
     * @param pathData path.
     * @return data/null.
     */
    public static WorkoutUserData loadWorkoutuserDataIn(final String pathData) {
        checkFolderPresence(pathData);
        try (FileReader reader = new FileReader(pathData, StandardCharsets.UTF_8)) {
            return GSON.fromJson(reader, WorkoutUserData.class);
        } catch (final IOException | JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * Save user profile.
     * 
     * @param pathData path.
     * @param userProfile profile.
     * @throws IOException IO error.
     */
    public static void saveUserProfile(final String pathData, final UserProfile userProfile) throws IOException {
        checkFolderPresence(pathData);
        try (FileWriter writer = new FileWriter(pathData, StandardCharsets.UTF_8)) {
            GSON.toJson(userProfile, writer);
        } catch (final IOException e) {
            throw new IOException("Error saving user profile: " + e.getMessage(), e);
        }
    }

    /**
     * Load user profile.
     * 
     * @param pathData path.
     * @return profile/null.
     */
    public static UserProfile loadUserProfile(final String pathData) {
        checkFolderPresence(pathData);
        try (FileReader reader = new FileReader(pathData, StandardCharsets.UTF_8)) {
            return GSON.fromJson(reader, UserProfile.class);
        } catch (final IOException | JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * Loads a CSV or text file as a list of strings.
     * 
     * @param pathData the path to the file.
     * @return a list of lines.
     */
    public static List<String> loadCsvFile(final String pathData) {
        checkFolderPresence(pathData);
        final List<String> lines = new ArrayList<>();
        final File file = new File(pathData);

        if (!file.exists()) {
            return lines;
        }

        try (java.io.BufferedReader br = new java.io.BufferedReader(
                new FileReader(pathData, StandardCharsets.UTF_8))) {
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
        } catch (final IOException e) {
            return new ArrayList<>();
        }
        return lines;
    }

    /**
     * Saves a list of string into a CSV or text file.
     * 
     * @param pathData the path to the file.
     * @param lines the lines to save.
     */
    public static void saveCsvFile(final String pathData, final List<String> lines) {
        checkFolderPresence(pathData);
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(
                new FileWriter(pathData, StandardCharsets.UTF_8))) {
            for (final String line : lines) {
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to save data in history", e);
        }
    }

    /**
     * Saves daily totals for nutrition.
     * 
     * @param pathData the path to the file.
     * @param stats a string in format "date,kcal,proteins,carbs,fats".
     */
    public static void saveDailyStats(final String pathData, final String stats) {
        checkFolderPresence(pathData);
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(
                new FileWriter(pathData, StandardCharsets.UTF_8, true))) {
            bw.write(stats);
            bw.newLine();
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to save data in daily stats", e);
        }
    }

    /**
     * Load data from resources.
     * 
     * @param <T> type of data.
     * @param resourcePath path of the file.
     * @param typeClass class of the array.
     * @return list of loaded data.
     */
    public static <T> List<T> loadFromResources(final String resourcePath, final Class<T[]> typeClass) {

        try (InputStream is = LoadSaveData.class.getResourceAsStream(resourcePath);
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {

            //if the stream is null, show error
            if (is == null || reader == null) {
                throw new IllegalStateException("Resource not found: " + resourcePath);
            }

            //load from the json the data
            final T[] arrayData = GSON.fromJson(reader, typeClass);

            //if data load are not null return the copy of the data modifible, empty othervise
            if (arrayData != null) {
                return new ArrayList<>(Arrays.asList(arrayData));
            } else {
                return new ArrayList<>();
            }

        } catch (final IOException | JsonSyntaxException e) {
            throw new IllegalStateException("Error reading resource: " + e.getMessage(), e);
        }
    }
}
