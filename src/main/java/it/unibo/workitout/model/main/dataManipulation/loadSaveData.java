package it.unibo.workitout.model.main.dataManipulation;

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
import it.unibo.workitout.model.main.WorkoutUserData;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.impl.WorkoutPlanImpl;
import java.io.File;

public class loadSaveData { 

    //General comment to try save the name og the class, because error. !!MUST BE DALATED THIS COMMENT

    private static final Gson gsonFile = new Gson();

    private static final String APP_DIR = ".workitout";

    /** Public constant for foods file name. */
    public static final String FOODS_FILE = "foods.csv";

    /** Public constant for history file name. */
    public static final String HISTORY_FILE = "history.csv";

    /** Public constant for stats file name. */
    public static final String STATS_FILE = "daily_stats.csv";

    //General method to create the path
    public static String getWorkspacePath() {
        return System.getProperty("user.home") + File.separator + APP_DIR;
    }

    //dinamic method that will be called in the specific class
    public static String createPath(final String nameFile) {
        return getWorkspacePath() + File.separator + nameFile;
    }

    //provate method that check the presence of the folder, if not create it
    private static void checkFolderPresence(String pathData) {
        File file = new File(pathData);
        File parDir = file.getParentFile();
        if(parDir != null && !parDir.exists()) {
            parDir.mkdirs();
        }
    }

    public static <T> List<T> loadSavedDataFrom(String pathData, Class<T[]> typeClass) throws IOException {
        checkFolderPresence(pathData);
        try (FileReader read = new FileReader(pathData)) {
            T[] arrayData = gsonFile.fromJson(read, typeClass);
            return arrayData != null ? new ArrayList<>(Arrays.asList(arrayData)) : new ArrayList<>();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public static <T> void saveDataIn(String pathData, Class<T[]> dataClass) throws IOException {
        checkFolderPresence(pathData);
        try(FileWriter writer = new FileWriter(pathData)) {
            gsonFile.toJson(dataClass, writer);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    //method that load and save the entire workout from the json
    public static WorkoutPlan loadWorkoutPlan(String pathData) {
        checkFolderPresence(pathData);
        try (FileReader read = new FileReader(pathData)) {            
            return gsonFile.fromJson(read, WorkoutPlanImpl.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static void saveWorkoutPlan(String pathData, WorkoutPlan dataPlan) {
        checkFolderPresence(pathData);
        try (FileWriter writer = new FileWriter(pathData)) {
             gsonFile.toJson(dataPlan, writer);
        } catch (IOException e) {
            System.out.println(" " + e.getMessage());
        }
    }

    //two methods that save and get the workoutUserData
    public static void saveWorkoutuserDataIn(final String pathData, final WorkoutUserData workoutUserData) {
        checkFolderPresence(pathData);
        try (FileWriter writer = new FileWriter(pathData)) {
            gsonFile.toJson(workoutUserData, writer );
        } catch (IOException e) {
            System.out.println(" " + e.getMessage());
        }
    }

    public static WorkoutUserData loadWorkoutuserDataIn(String pathData) {
        checkFolderPresence(pathData);
        try (FileReader reader = new FileReader(pathData)) {
            return gsonFile.fromJson(reader, WorkoutUserData.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static void saveUserProfile(String pathData, UserProfile userProfile) {
        checkFolderPresence(pathData);
        try (FileWriter writer = new FileWriter(pathData)) {
            gsonFile.toJson(userProfile, writer);
        } catch (Exception e) {
            return ;
        }
    }

    public static UserProfile loadUserProfile(String pathData) {
        checkFolderPresence(pathData);
        try (FileReader reader = new FileReader(pathData)) {
            return gsonFile.fromJson(reader, UserProfile.class);
        } catch (Exception e) {
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
                new FileReader(pathData, java.nio.charset.StandardCharsets.UTF_8))) {
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
                new FileWriter(pathData, java.nio.charset.StandardCharsets.UTF_8))) {
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
                new FileWriter(pathData, java.nio.charset.StandardCharsets.UTF_8, true))) {
            bw.write(stats);
            bw.newLine();
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to save data in daily stats", e);
        }
    }


    /**
     * method to read the data from the inside jar.
     * 
     * @param <T> geneal method.
     * 
     * @param resourcePath the path of the file.
     * 
     * @param typeClass the type of the class.
     * 
     * @return the general list.
     * 
     */
    public static <T> List<T> loadFromResources(String resourcePath, Class<T[]> typeClass) {

        try (InputStream is = loadSaveData.class.getResourceAsStream(resourcePath);
        
        InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {

            //if the stream is null, show error
            if (is == null) {
                System.err.println("Resource not found: " + resourcePath);
                return new ArrayList<>();
            }

            //load from the json the data
            T[] arrayData = gsonFile.fromJson(reader, typeClass);

            //if data load are not null return the copy of the data modifible, empty othervise
            if (arrayData != null ) {
                return new ArrayList<>(Arrays.asList(arrayData));
            } else {
                return new ArrayList<>();
            }

        } catch (IOException e) {
            System.err.println("Error reading resource: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
