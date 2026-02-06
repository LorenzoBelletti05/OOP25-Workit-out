package it.unibo.workitout.view.food.impl;

import it.unibo.workitout.model.food.api.Food;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom table model to display food items in a JTable.
 */
public final class FoodTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final double KCAL_PER_PROT_CARB = 4.0;
    private static final double KCAL_PER_FAT = 9.0;
    private static final String GRAM_FORMAT = "%.1fg";
    private final String[] columnNames = {"Nome", "Calorie (100g)", "Proteine", "Carbo", "Grassi"};
    private transient List<Food> foods = new ArrayList<>();

    /**
     * Updates the list of foods displayed.
     * 
     * @param foods the new list of food items to display.
     */
    public void setFoods(final List<Food> foods) {
        this.foods = new ArrayList<>(foods);
        fireTableDataChanged();
    }

    /**
     * Returns the food item located at the specified row index.
     * 
     * @param row the row index of the food item to retrieve.
     * @return the Food object at the given row.
     */
    public Food getFoodAt(final int row) {
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
    public String getColumnName(final int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final Food food = foods.get(rowIndex);
        final double kcalBase = food.getKcalPer100g();

        return switch (columnIndex) {
            case 0 -> food.getName();
            case 1 -> kcalBase;
            case 2 -> String.format(GRAM_FORMAT, food.getProteins() * kcalBase / KCAL_PER_PROT_CARB);
            case 3 -> String.format(GRAM_FORMAT, food.getCarbs() * kcalBase / KCAL_PER_PROT_CARB);
            case 4 -> String.format(GRAM_FORMAT, food.getFats() * kcalBase / KCAL_PER_FAT);
            default -> null;
        };
    }
}
