package de.hadesrofl.knapsack.utils;

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
/**
 * Jimmey - a discord bot written with thanks to Discord4J (https://github.com/austinv11/Discord4J)
 * Copyright (c) 2016 René Kremer
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

 import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileReader;
        import java.io.IOException;
 import java.util.LinkedList;
 import java.util.List;

 import de.hadesrofl.knapsack.Item;
 import de.hadesrofl.knapsack.Knapsack;
 import org.json.JSONException;
 import org.json.JSONObject;

public class JsonReader {
    /**
     * Reads a json file and returns it as JSONObject
     *
     * @param file
     *            is the file to read
     * @return an json object
     */
    public static JSONObject readFile(String file) throws IOException {
        if (file != null && file.compareTo("") != 0)
            return readFile(new File(file));
        else
            throw new IOException("Error while reading json file " + file);
    }

    /**
     * Reads a json file and returns it as JSONObject
     *
     * @param f
     *            is the file to read
     * @return an json object
     */
    public static JSONObject readFile(File f) throws IOException {
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
        JSONObject obj = null;
        if (jsonData.compareTo("") != 0)
            return new JSONObject(jsonData);
        else throw new IOException("Error while reading json file " + f);
    }

    public static Knapsack createKnapsack(String file) {
        try{
            JSONObject obj = JsonReader.readFile(file);
            JSONObject knapsack = obj.getJSONObject("Knapsack");
            JSONObject items = knapsack.getJSONObject("Items");
            List<Item> itemList = new LinkedList<Item>();
            int W = knapsack.getInt("W");
            for (int i = 1; i <= items.length(); i++) {
                JSONObject item = items.getJSONObject(i + "");
                itemList.add(new Item(item.getInt("value"), item.getInt("weight")));
            }
            return new Knapsack(W, itemList);
        }catch(IOException|JSONException e){
            System.err.println("Error while reading json file " + file);
        }
        return new Knapsack(0,new LinkedList<Item>());
    }
}

