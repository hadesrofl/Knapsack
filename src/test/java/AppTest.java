import de.hadesrofl.knapsack.Item;
import de.hadesrofl.knapsack.Knapsack;
import de.hadesrofl.knapsack.utils.CSVReader;
import de.hadesrofl.knapsack.utils.JsonReader;
import org.json.JSONException;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * <p>
 * <strong>last update:</strong> 27.11.2016
 * </p>
 * <strong>Description:</strong>
 * <p>
 * Test class with test cases for jimmey
 * </p>
 *
 * @author Rene Kremer
 *         <p>
 *         Copyright (c) 2016 by Rene Kremer
 *         </p>
 *         <p>
 *         <strong>License:</strong> GPL-2.0
 *         </p>
 * @version 0.25
 */
public class AppTest extends TestCase {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    public void testZeroInput(){
        Knapsack k = new Knapsack(0, new LinkedList<Item>());
        assertEquals(0, k.knapsackDP());
    }

    public void test3Values(){
        List<Item> items = new LinkedList<Item>();
        items.add(new Item(50, 5));
        items.add(new Item(140, 20));
        items.add(new Item(60, 10));
        Knapsack k = new Knapsack(30, items);
        assertEquals(190, k.knapsackGreedy());
        assertEquals(200, k.knapsackDP());
    }

    public void testJSONInput(){
        Knapsack k = JsonReader.createKnapsack("examples/input.json");
        assertEquals(190, k.knapsackGreedy());
        assertEquals(200, k.knapsackDP());
    }

    public void testWrongJSON(){
        exception.expect(IOException.class);
        Knapsack k = JsonReader.createKnapsack("examples/input");
    }

    public void testMalformedJSON(){
        exception.expect(JSONException.class);
        Knapsack k = JsonReader.createKnapsack("examples/malformed_input.json");
    }

    public void testCSVInput(){
        Knapsack k = CSVReader.readFile("examples/input.csv");
        assertEquals(90, k.knapsackGreedy());
        assertEquals(90, k.knapsackDP());
    }

    public void testWrongFile(){
        exception.expect(IOException.class);
        Knapsack k = CSVReader.readFile("examples/input");
    }

    public void testMalformedCSV(){
        exception.expect(NumberFormatException.class);
        Knapsack k = CSVReader.readFile("examples/malformed_input.csv");
    }

}
