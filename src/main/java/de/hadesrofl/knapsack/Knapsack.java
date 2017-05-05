package de.hadesrofl.knapsack;

import java.util.List;
import java.util.LinkedList;

public class Knapsack {
    private int m;
    private List<Item> items;

    public Knapsack(int m, List<Item> items){
        this.m = m;
        this.items = items;
    }

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
        while(usedWeight < m && !ratio.isEmpty()){
            chosenItem = null;
            int index = 0;
            for (int i = 0; i < ratio.size(); i++) {
                if (chosenItem == null){
                    chosenItem = items.get(i);
                    index = i;
                }else if((chosenItem.getValue() / chosenItem.getWeight()) < ratio.get(i)){
                    chosenItem = items.get(i);
                    index = i;
                }
            }
            if(!ratio.isEmpty()){
                ratio.remove(index);
                items.remove(index);
                if(usedWeight + chosenItem.getWeight() <= m){
                    result.add(chosenItem);
                    usedWeight += chosenItem.getWeight();
                }
            }
        }
        int sum = 0;
        for(Item item : result){
            sum += item.getValue();
        }
        return sum;
    }
    public int knapsackDP() {
        int n = items.size();
        int val[] = new int[n];
        int wt[] = new int[n];
        int[][] V = new int[n + 1][m + 1]; //Matrix for bottom-up approach
        // intialize to 0 (the sack could have a weight of 0)
        for (int col = 0; col <= m; col++) {
            V[0][col] = 0;
        }
        // initialize to 0 (there could be no items in our sack)
        for (int row = 0; row <= n; row++) {
            V[row][0] = 0;
            if(row < n ){
                Item item = items.get(row);
                wt[row] = item.getWeight();
                val[row] = item.getValue();
            }
        }
        // for each value check the weight it fits in
        for (int item = 1; item <= n; item++) {
            // check each weight if the item fits in that category
            for (int weight = 1; weight <= m; weight++) {
                if (wt[item - 1] <= weight) {
                    // Check if we the value of this current item and another item, which fits the remaining weight,
                    // is greater than the value without the current item
                    V[item][weight] = Math.max(val[item - 1] + V[item - 1][weight - wt[item - 1]], V[item - 1][weight]);
                } else {
                    // Weight of current item > current weight, just take the last items value for that weight
                    V[item][weight] = V[item - 1][weight];
                }
            }
        }
        return V[n][m];
    }

    private List<Item> copyItemList(List<Item> items){
        List<Item> copy = new LinkedList<Item>();
        for(Item item : items){
            copy.add(new Item(item.getValue(), item.getWeight()));
        }
        return copy;
    }
}