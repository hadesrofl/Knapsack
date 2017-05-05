package de.hadesrofl.knapsack;

import de.hadesrofl.knapsack.Item;
import de.hadesrofl.knapsack.Knapsack;
import de.hadesrofl.knapsack.utils.CSVReader;
import de.hadesrofl.knapsack.utils.JsonReader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * <strong>last update:</strong> {DATE}
 * </p>
 * <strong>Description:</strong>
 * <p>
 * <p>
 * </p>
 *
 * @author Rene Kremer
 *         <p>
 *         Copyright (c) {YEAR} by Rene Kremer
 *         </p>
 *         <p>
 *         <strong>License:</strong> none
 *         </p>
 * @version 0.01
 */
public class Launcher {
    public static void main(String[] args) throws Exception {
        Knapsack sack = new Knapsack(0, new LinkedList<Item>());
        boolean error = false;
        switch (args.length) {
            case 1:
                if (args[0].compareTo("--help") == 0) {
                    System.out.println("You have 3 choices to start the program: \n"
                            + "1. pass no arguments ==> generates 10 random items with a value and weight ranging from 1 to 15 and a Knapsack with the maximal weight of 50\n"
                            + "2. pass a file to read from (csv or json), the program will look out for the correct ending e.g. \".csv\" or \".json\". Example files are in the directory \"examples\" \n"
                            + "3. pass as first argument the value of \"n\", as second parameter the max weight of the knapsack \"m\" \n"
                            + "4. pass as first argument the value of \"n\", as second parameter the max weight of the knapsack \"m\" and as third parameter the max value for the generation of value and weight of the items \n"
                            + "All arguments need to be passed as positive integers except if you want to input a file");
                    System.exit(0);
                } else if (args[0].substring(args[0].length() - 5).compareTo(".json") == 0 ||
                        args[0].substring(args[0].length() - 4).compareTo(".csv") == 0) {
                    String type = args[0].split("\\.")[1];
                    if (type.compareTo("json") == 0) {
                        System.out.println("Start reading JSON file " + args[0]);
                        sack = JsonReader.createKnapsack(args[0]);
                    } else if (type.compareTo("csv") == 0) {
                        System.out.println("Start reading CSV file " + args[0]);
                        sack = CSVReader.readFile(args[0]);
                    } else {
                        error = true;
                    }
                } else {
                    error = true;
                }
                break;
            case 2:
                try {
                    int m = Integer.parseInt(args[0]);
                    int n = Integer.parseInt(args[1]);
                    System.out.println("Got m: " + m + " and n: " + n + ".\nStart generating...");
                    sack = generateKnapsack(m, n, 15);
                } catch (NumberFormatException e) {
                    System.err.println("Error while parsing m: " + args[0] + " and n: " + args[1]);
                    error = true;
                }
                break;
            case 3:
                try {
                    int m = Integer.parseInt(args[0]);
                    int n = Integer.parseInt(args[1]);
                    int max = Integer.parseInt(args[2]);
                    System.out.println("Got m: " + m + ", n: " + n + " and a max range for generation: " + max + ".\nStart generating...");
                    sack = generateKnapsack(m, n, max);
                } catch (NumberFormatException e) {
                    System.err.println("Error while parsing m: " + args[0] + ", n: " + args[1] + " and max generator value: " + args[2]);
                    error = true;
                }
            default:
                sack = generateKnapsack(50, 10, 15);
        }
        if (error) System.exit(-1);
        System.out.println("Greedy Solution: " + sack.knapsackGreedy());
        System.out.println("Dynamic Programming: " + sack.knapsackDP());
    }

    private static Knapsack generateKnapsack(int weight, int itemCount, int maxRange) {
        List<Item> items = new ArrayList<Item>();
        for (int i = 0; i < itemCount; i++) {
            int v = 1 + (int) (Math.random() * maxRange);
            int w = 1 + (int) (Math.random() * maxRange);
            Item item = new Item(v, w);
            items.add(item);
            System.out.print("Item " + i + ": " + item);
        }
        System.out.println();
        return new Knapsack(weight, items);
    }
}
