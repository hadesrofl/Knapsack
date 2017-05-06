package de.hadesrofl.knapsack.utils;

import de.hadesrofl.knapsack.Item;
import de.hadesrofl.knapsack.KnapsackProblem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>
 * <strong>last update:</strong> 06.05.2017
 * </p>
 * <strong>Description:</strong>
 * <p>
 * This class allows to read CSV files
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
public class CSVReader {
    /**
     * Delimiter used in csv. No other delimiter is allowed!
     */
    private static final String DELIMITER = ",";

    /**
     * Reads a file and creates a {@link KnapsackProblem} out of it
     *
     * @param file the input csv to read and create a KnapsackProblem from
     * @return a KnapsackProblem. If an error occures while reading the file an empty knapsack will be returned
     */
    public static KnapsackProblem createKnapsack(String file) {
        List<Item> items = new ArrayList<Item>();
        int W = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
            String line;
            int lines = 0;
            while ((line = reader.readLine()) != null) {
                lines++;
                String split[] = line.split(DELIMITER);
                switch (lines) {
                    case 1:
                        try {
                            W = Integer.parseInt(split[1].trim());
                        } catch (NumberFormatException e) {
                            System.err.println("Bad input, can't parse " + split[1].trim() + " to variable W");
                        }
                        break;
                    case 2:
                        break;
                    default:
                        try {
                            int value = Integer.parseInt(split[0].trim());
                            int weight = Integer.parseInt(split[1].trim());
                            items.add(new Item(value, weight));
                        } catch (NumberFormatException e) {
                            System.err.println("Bad input, can't parse " + split[0].trim() + " or " + split[1].trim() + " to an item");
                        }
                }
            }
        } catch (IOException e) {
            System.err.println("Error while reading the input file : " + file);
        }

        return new KnapsackProblem(W, items);
    }
}
