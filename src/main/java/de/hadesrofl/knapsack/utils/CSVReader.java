package de.hadesrofl.knapsack.utils;

import de.hadesrofl.knapsack.Item;
import de.hadesrofl.knapsack.Knapsack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class CSVReader {
    private static final String DELIMITER = ",";
    public static Knapsack readFile(String file) {
        List<Item> items = new ArrayList<Item>();
        int W = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
            String line;
            int lines = 0;
            while ((line = reader.readLine()) != null) {
                lines++;
                String split[] = line.split(DELIMITER);
                switch(lines){
                    case 1:
                        try{
                            W = Integer.parseInt(split[1].trim());
                        }catch(NumberFormatException e){
                            System.err.println("Bad input, can't parse " + split[1].trim() + " to variable W");
                        }
                        break;
                    case 2:
                        break;
                    default:
                        try{
                            int value = Integer.parseInt(split[0].trim());
                            int weight = Integer.parseInt(split[1].trim());
                            items.add(new Item(value, weight));
                        }catch(NumberFormatException e){
                            System.err.println("Bad input, can't parse " + split[0].trim() + " or " + split[1].trim() + " to an item");
                        }
                }
            }
        } catch (IOException e) {
            System.err.println("Error while reading the input file : " + file);
        }

        return new Knapsack(W, items);
    }
}
