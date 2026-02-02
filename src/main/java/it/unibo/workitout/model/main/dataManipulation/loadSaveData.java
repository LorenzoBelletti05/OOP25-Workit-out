package it.unibo.workitout.model.main.dataManipulation;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import it.unibo.workitout.model.main.WorkoutUserData;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.impl.WorkoutPlanImpl;
import java.io.File;

public class LoadSaveData {    

    private static final Gson gsonFile = new Gson();

    private static final String APP_DIR = ".workitout";

    //General method to create the path
    public static String getWorkspacePath() {
        return System.getProperty("user.home") + File.separator + APP_DIR;
    }

    //dinamic method that will be called in the specific class
    public static String createPath(final String nameFile) {
        return getWorkspacePath() + File.separator + nameFile;
    }

    

    public static <T> List<T> loadSavedDataFrom(String pathData, Class<T[]> typeClass) throws IOException {
        try (FileReader read = new FileReader(pathData)) {
            T[] arrayData = gsonFile.fromJson(read, typeClass);
            return arrayData != null ? new ArrayList<>(Arrays.asList(arrayData)) : new ArrayList<>();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public static <T> void saveDataIn(String pathData, Class<T[]> dataClass) throws IOException {
        try(FileWriter writer = new FileWriter(pathData)) {
            gsonFile.toJson(dataClass, writer);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }


    //method that load and save the entire workout from the json
    public static WorkoutPlan loadWorkoutPlan(String pathData) {
        try (FileReader read = new FileReader(pathData)) {            
            return gsonFile.fromJson(read, WorkoutPlanImpl.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static void saveWorkoutPlan(String pathData, WorkoutPlan dataPlan) {
        try (FileWriter writer = new FileWriter(pathData)) {            
             gsonFile.toJson(dataPlan, writer);
        } catch (IOException e) {
            e.getMessage();
        }
    }


    //two methods that save and get the workoutUserData
    public static void saveWorkoutuserDataIn(String pathData, WorkoutUserData workoutUserData) {
        try (FileWriter writer = new FileWriter(pathData)) {            
            gsonFile.toJson(workoutUserData, writer );
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static WorkoutUserData loadWorkoutuserDataIn(String pathData) {
        try (FileReader reader = new FileReader(pathData)) {            
            return gsonFile.fromJson(reader, WorkoutUserData.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static void saveUserProfile(String pathData, UserProfile userProfile) {
        try (FileWriter writer = new FileWriter(pathData)) {
            gsonFile.toJson(userProfile, writer);
        } catch (Exception e) {
            return ;
        }
    }

    public static UserProfile loadUserProfile(String pathData) {
        try (FileReader reader = new FileReader(pathData)) {
            return gsonFile.fromJson(reader, UserProfile.class);
        } catch (Exception e) {
            return null;
        }
    }

}
