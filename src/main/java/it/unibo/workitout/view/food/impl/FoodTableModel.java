package it.unibo.workitout.view.food.impl;

import it.unibo.workitout.model.food.api.Food;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class FoodTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Nome", "Calorie (100g)", "Proteine", "Carbo", "Grassi"};
    private List<Food> foods = new ArrayList<>();

    public void setFoods(List<Food> foods) {
        this.foods = new ArrayList<>(foods);
        fireTableDataChanged();
    }

    public Food getFoodAt(int row) {
        return foods.get(row);
    }

    @Override
    public int getRowCount() {
        return foods.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Food food = foods.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> food.getName();
            case 1 -> food.getKcalPer100g();
            case 2 -> food.getProteins() + "g";
            case 3 -> food.getCarbs() + "g";
            case 4 -> food.getFats() + "g";
            default -> null;
        };
    }
}
