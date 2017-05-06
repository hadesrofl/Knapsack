package de.hadesrofl.knapsack.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import de.hadesrofl.knapsack.Item;
import de.hadesrofl.knapsack.KnapsackProblem;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>
 * <strong>last update:</strong> 06.05.2017
 * </p>
 * <strong>Description:</strong>
 * <p>
 * This class allows to read JSON files
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
public class JsonReader {
    /**
     * Creates a {@link KnapsackProblem} out of a json file. Returns an empty knapsack if an error occurs
     *
     * @param file is the json file to read from
     * @return an non empty KnapsackProblem on success, otherwise an empty Knapsack
     */
    public static KnapsackProblem createKnapsack(String file) {
        try {
            JSONObject obj = JsonReader.readFile(file);
            JSONObject knapsack = obj.getJSONObject("Knapsack");
            JSONObject items = knapsack.getJSONObject("Items");
            List<Item> itemList = new LinkedList<Item>();
            int W = knapsack.getInt("W");
            for (int i = 1; i <= items.length(); i++) {
                JSONObject item = items.getJSONObject(i + "");
                itemList.add(new Item(item.getInt("value"), item.getInt("weight")));
            }
            return new KnapsackProblem(W, itemList);
        } catch (IOException | JSONException e) {
            System.err.println("Error while reading json file " + file);
        }
        return new KnapsackProblem(0, new LinkedList<Item>());
    }

    /**
     * Reads a json file and returns it as JSONObject
     *
     * @param file is the file to read
     * @return an json object
     * @throws IOException if an error occured while reading the json file
     */
    private static JSONObject readFile(String file) throws IOException {
        if (file != null && file.compareTo("") != 0)
            return readFile(new File(file));
        else
            throw new IOException("Error while reading json file " + file);
    }

    /**
     * Reads a json file and returns it as JSONObject
     *
     * @param f is the file to read
     * @return an json object
     * @throws IOException if an error occured while reading the json file
     */
    private static JSONObject readFile(File f) throws IOException {
        String jsonData = "";
        BufferedReader br = null;
        try {
            String line;
            br = new BufferedReader(new FileReader(f));
            while ((line = br.readLine()) != null) {
                jsonData += line + "\n";
            }
        } catch (IOException e) {
            System.err.println("Can't read json file!");
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                System.err.println("Can't close buffered reader!");
            }
        }
        if (jsonData.compareTo("") != 0)
            return new JSONObject(jsonData);
        else throw new IOException("Error while reading json file " + f);
    }
}

