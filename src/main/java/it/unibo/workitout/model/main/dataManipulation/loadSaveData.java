package it.unibo.workitout.model.main.dataManipulation;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import it.unibo.workitout.model.main.WorkoutUserData;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.model.workout.impl.WorkoutPlanImpl;

public class loadSaveData {

    private static final Gson gsonFile = new Gson();

    public static <T> List<T> getSavedDataFrom(String pathData, Class<T[]> typeClass) throws IOException {
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

    public static WorkoutUserData getWorkoutuserDataIn(String pathData) {
        try (FileReader reader = new FileReader(pathData)) {            
            return gsonFile.fromJson(reader, WorkoutUserData.class);
        } catch (IOException e) {
            return null;
        }
    }

}
