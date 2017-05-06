package de.hadesrofl.knapsack;

import de.hadesrofl.knapsack.utils.CSVReader;
import de.hadesrofl.knapsack.utils.JsonReader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * <strong>last update:</strong> 06.05.2017
 * </p>
 * <strong>Description:</strong>
 * <p>
 * This class starts the knapsack-solver.
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
public class Launcher {
    /**
     * Is the used approach dynamic programming?
     */
    static boolean dp;
    /**
     * Does some error occure while checking given arguments?
     */
    static boolean error;

    /**
     * Main Method
     *
     * @param args is used as described in --help
     */
    public static void main(String[] args) {
        KnapsackProblem sack = new KnapsackProblem(0, new LinkedList<Item>());
        switch (args.length) {
            case 1:
                if (args[0].compareTo("--help") == 0) {
                    System.out.println("You have 5 choices to start the program: \n"
                            + "1. pass no arguments ==> generates 10 random items with a value and weight ranging " +
                            "from 1 to 15 and a KnapsackProblem with the maximal weight of 50 (solved with a dp " +
                            "approach)\n"
                            + "2. pass as first argument the approach (greedy or dp) to generate 10 random items with" +
                            " a value and weight ranging from 1 to 15 and a KnapsackProblem with the maximal weight " +
                            "of 50 \n" +
                            "Example command call: \"java -jar Knapsack-1.0.jar greedy\"\n"
                            + "3. pass as first argument the approach (greedy or dp) and as second argument a file to" +
                            " read from (csv or json), the program will look out for the correct ending e.g. \".csv\" or \".json\". " +
                            "Example files are in the directory \"examples\" \n" +
                            "Example command call: \"java -jar Knapsack-1.0.jar greedy examples/input.json\" \n"
                            + "4. pass as first argument the approach (greedy or dp), as second argument the " +
                            "value of \"n\" and as third parameter the max weight of the knapsack \"m\" \n" +
                            "Example command call: \"java -jar Knapsack-1.0.jar dp 10 30\" \n"
                            + "5. pass as first argument the approach (greedy or dp), as second argument the " +
                            "value of \"n\", as third parameter the max weight of the knapsack \"m\" " +
                            "and as fourth parameter the max value for the generation of value and weight of the " +
                            "items \n" +
                            "Example command call: \"java -jar Knapsack-1.0.jar dp 10 30 8\" \n"
                            + "All arguments need to be passed as positive integers except if you want to input a file");
                    System.exit(0);
                } else {
                    checkApproach(args[0]);
                    if(!error){
                        sack = generateKnapsack(50, 10, 15);
                    }
                }
                break;
            case 2:
                checkApproach(args[0]);
                if (!error) {
                    if (args[1].substring(args[1].length() - 5).compareTo(".json") == 0 ||
                            args[1].substring(args[1].length() - 4).compareTo(".csv") == 0) {
                        String type = args[1].split("\\.")[1];
                        if (type.compareTo("json") == 0) {
                            System.out.println("Start reading JSON file " + args[1]);
                            sack = JsonReader.createKnapsack(args[1]);
                        } else if (type.compareTo("csv") == 0) {
                            System.out.println("Start reading CSV file " + args[1]);
                            sack = CSVReader.createKnapsack(args[1]);
                        } else {
                            error = true;
                        }
                    }
                }
                break;
            case 3:
                checkApproach(args[0]);
                if (!error) {
                    try {
                        int n = Integer.parseInt(args[1]);
                        int m = Integer.parseInt(args[2]);
                        System.out.println("Got m: " + m + " and n: " + n + ".\nStart generating...");
                        sack = generateKnapsack(m, n, 15);
                    } catch (NumberFormatException e) {
                        System.err.println("Error while parsing m: " + args[1] + " and n: " + args[2]);
                        error = true;
                    }
                }
                break;
            case 4:
                checkApproach(args[0]);
                if (!error) {
                    try {
                        int n = Integer.parseInt(args[1]);
                        int m = Integer.parseInt(args[2]);
                        int max = Integer.parseInt(args[3]);
                        System.out.println("Got m: " + m + ", n: " + n + " and a max range for generation: " + max + ".\nStart generating...");
                        sack = generateKnapsack(m, n, max);
                    } catch (NumberFormatException e) {
                        System.err.println("Error while parsing m: " + args[1] + ", n: " + args[2] + " and max generator " +
                                "value: " + args[3]);
                        error = true;
                    }
                }
                break;
            default:
                Launcher.dp = true;
                Launcher.error = false;
                sack = generateKnapsack(50, 10, 15);
        }
        if (error) {
            System.err.println("Syntax error. Please check --help");
            System.exit(-1);
        }
        if (dp) {
            long begin = System.currentTimeMillis();
            System.out.println("Dynamic Programming: " + sack.knapsackDP());
            long end = System.currentTimeMillis();
            System.out.println("Time needed to calculate: " + (end - begin) + " ms");
        } else {
            long begin = System.currentTimeMillis();
            System.out.println("Greedy Solution: " + sack.knapsackGreedy());
            long end = System.currentTimeMillis();
            System.out.println("Time needed to calculate: " + (end - begin) + " ms");
        }
    }

    /**
     * Private helper function to generate a knapsack with random items
     *
     * @param weight    is the weight of the knapsack
     * @param itemCount is the number of items
     * @param maxRange  is the max value for the field {@link Item#value} and {@link Item#weight}
     * @return a random knapsack problem
     */
    private static KnapsackProblem generateKnapsack(int weight, int itemCount, int maxRange) {
        List<Item> items = new ArrayList<Item>();
        for (int i = 0; i < itemCount; i++) {
            int v = 1 + (int) (Math.random() * maxRange);
            int w = 1 + (int) (Math.random() * maxRange);
            Item item = new Item(v, w);
            items.add(item);
            System.out.print("Item " + i + ": " + item);
        }
        System.out.println();
        return new KnapsackProblem(weight, items);
    }

    /**
     * Private helper function to check if the approach mentioned in the given arguments is dp or greedy. If it isn't
     * one of those error flag will be set
     *
     * @param input is the input of the args parameter for the used approach (dp or greedy)
     */
    private static void checkApproach(String input) {
        if (input.compareTo("greedy") == 0) {
            dp = false;
        } else if (input.compareTo("dp") == 0) {
            dp = true;
        } else {
            error = true;
        }
    }
}
