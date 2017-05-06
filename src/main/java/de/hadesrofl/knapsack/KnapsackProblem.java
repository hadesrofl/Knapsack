package de.hadesrofl.knapsack;

import java.util.List;
import java.util.LinkedList;

/**
 * <p>
 * <strong>last update:</strong> 06.05.2017
 * </p>
 * <strong>Description:</strong>
 * <p>
 * This class represents a KnapsackProblem Problem and calculates an optimal solution for itself using a dynamic
 * programming
 * algorithm. As comparison it can also calculate a solution based on a greedy algorithm
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
public class KnapsackProblem {
    /**
     * The weight of this knapsack
     */
    private final int W;
    /**
     * The set of all possible {@link Item items}
     */
    private final List<Item> items;

    /**
     * (Boa) Constructor
     *
     * @param w     is the weight of the knapsack
     * @param items is the list of items to chose from
     */
    public KnapsackProblem(int w, List<Item> items) {
        this.W = w;
        this.items = items;
    }

    /**
     * Calculates a solution based on a greedy algorithm. The greedy algorithm is a top-down approach.
     * First it calculates a ratio for each item (value/weight) and then takes the items with the best
     * ratio until the sack is (nearly) full
     *
     * @return the calculated solution (value) as an integer
     */
    public int knapsackGreedy() {
        List<Double> ratio = new LinkedList<Double>();
        List<Item> result = new LinkedList<Item>();
        List<Item> items = copyItemList(this.items);
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            double iRatio = item.getValue() / item.getWeight();
            ratio.add(iRatio);
        }
        int usedWeight = 0;
        Item chosenItem;
        while (usedWeight < W && !ratio.isEmpty()) {
            chosenItem = null;
            int index = 0;
            for (int i = 0; i < ratio.size(); i++) {
                if (chosenItem == null) {
                    chosenItem = items.get(i);
                    index = i;
                } else if ((chosenItem.getValue() / chosenItem.getWeight()) < ratio.get(i)) {
                    chosenItem = items.get(i);
                    index = i;
                }
            }
            if (!ratio.isEmpty() && chosenItem != null) {
                ratio.remove(index);
                items.remove(index);
                if (usedWeight + chosenItem.getWeight() <= W) {
                    result.add(chosenItem);
                    usedWeight += chosenItem.getWeight();
                }
            }
        }
        int sum = 0;
        for (Item item : result) {
            sum += item.getValue();
        }
        return sum;
    }

    /**
     * Calculates a solution based on a dynamic programming algorithm.
     * The dynamic programming algorithm is a bottom-up approach.
     * It calculates a matrix, where each row represents an item and each column a weight of the knapsack.
     * Therefore each column represents a subset of our knapsack problem
     * <p>
     * Before entering the value into the matrix, it checks if the current item fits into the weight column
     * and if there is a remaining weight, which can be used to store other items by looking up the
     * appropriate cell of the matrix.
     *
     * @return the calculated solution (value) as an integer
     */
    public int knapsackDP() {
        final int N = items.size();
        int val[] = new int[N];
        int wt[] = new int[N];
        int[][] M = new int[N + 1][W + 1]; //Matrix for bottom-up approach
        // initialize to 0 (the sack could have a weight of 0)
        for (int col = 0; col <= W; col++) {
            M[0][col] = 0;
        }
        // initialize to 0 (there could be no items in our sack)
        for (int row = 0; row <= N; row++) {
            M[row][0] = 0;
            if (row < N) {
                Item item = items.get(row);
                wt[row] = item.getWeight();
                val[row] = item.getValue();
            }
        }
        // for each value check the weight it fits in
        for (int item = 1; item <= N; item++) {
            // check each weight if the item fits in that category
            for (int weight = 1; weight <= W; weight++) {
                if (wt[item - 1] <= weight) {
                    // Check if we the value of this current item and another item, which fits the remaining weight,
                    // is greater than the value without the current item
                    M[item][weight] = Math.max(val[item - 1] + M[item - 1][weight - wt[item - 1]], M[item - 1][weight]);
                } else {
                    // Weight of current item > current weight, just take the last items value for that weight
                    M[item][weight] = M[item - 1][weight];
                }
            }
        }
        return M[N][W];
    }

    /**
     * Private helper function to copy a list of items. Used by {@link #knapsackGreedy()} as the
     * greedy algorithm removes a chosen item from the list of all items to keep indexes in check
     *
     * @param items is the list to copy
     * @return the copied list
     */
    private List<Item> copyItemList(List<Item> items) {
        List<Item> copy = new LinkedList<Item>();
        for (Item item : items) {
            copy.add(new Item(item.getValue(), item.getWeight()));
        }
        return copy;
    }
}