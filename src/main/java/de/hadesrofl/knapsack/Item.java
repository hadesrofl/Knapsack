package de.hadesrofl.knapsack;

/**
 * Created by rene on 05.05.17.
 */
public class Item {
    private int value;
    private int weight;

    public Item (int value, int weigth){
        this.value = value;
        this.weight = weigth;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String toString(){
        return "Value: " + value + ", Weight: " + weight + "\n";
    }




}
