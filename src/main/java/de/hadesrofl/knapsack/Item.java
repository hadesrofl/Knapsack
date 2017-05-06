package de.hadesrofl.knapsack;

/**
 * <p>
 * <strong>last update:</strong> 06.05.2017
 * </p>
 * <strong>Description:</strong>
 * <p>
 * This class represents an item for {@link KnapsackProblem}
 * </p>
 *
 * @author Rene Kremer
 *         <p>
 *         Copyright (c) 2017 by Rene Kremer
 *         </p>
 *         <p>
 *         <strong>License:</strong> none
 *         </p>
 * @version 1.0
 */
public class Item {
    /**
     * value of the item
     */
    private int value;
    /**
     * weight of the item
     */
    private int weight;

    /**
     * Constructor of an item object
     *
     * @param value  is the value of this item
     * @param weigth is the weight of this item
     */
    public Item(int value, int weigth) {
        this.value = value;
        this.weight = weigth;
    }

    /**
     * Gets the value
     *
     * @return the value of this item
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets the weight
     *
     * @return the weight of this item
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Represents this item as string
     *
     * @return a string representation of this item
     */
    public String toString() {
        return "Value: " + value + ", Weight: " + weight + "\n";
    }


}
