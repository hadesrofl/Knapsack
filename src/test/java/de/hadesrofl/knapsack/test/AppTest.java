package de.hadesrofl.knapsack.test;

import de.hadesrofl.knapsack.Item;
import de.hadesrofl.knapsack.KnapsackProblem;
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
 * <p>
 * <strong>last update:</strong> 06.05.2017
 * </p>
 * <strong>Description:</strong>
 * <p>
 * Test class for the KnapsackProblemSolver
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
public class AppTest extends TestCase {
    @Rule
    /**
     * Used to check for exceptions
     */
    public final ExpectedException exception = ExpectedException.none();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * The test suite starting this test class
     *
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * What happens with an empty Knapsack?
     */
    public void testZeroInput() {
        KnapsackProblem k = new KnapsackProblem(0, new LinkedList<Item>());
        assertEquals(0, k.knapsackDP());
    }

    /**
     * Check the following problem:
     *
     * <p>Item A: Value = 50, Weight = 5</p>
     * <p>Item B: Value = 140, Weight = 20</p>
     * <p>Item C: Value = 60, Weight = 10</p>
     * <p>Weight of Knapsack = 30</p>
     *
     * <p>Optimal (<strong>Dynamic Programming</strong>) Solution: Item B + C. Value = 200, Weight = 30</p>
     * <p><strong>Greedy:</strong></p>
     * <p>Ratios:</p>
     * <ul>
     * <li>Item A = 10</li>
     * <li>Item B = 7</li>
     * <li>Item C = 6</li>
     * </ul>
     * Greedy Solution: Value = 190, Weight = 25.
     */
    public void test3Values() {
        List<Item> items = new LinkedList<Item>();
        items.add(new Item(50, 5));
        items.add(new Item(140, 20));
        items.add(new Item(60, 10));
        KnapsackProblem k = new KnapsackProblem(30, items);
        assertEquals(190, k.knapsackGreedy());
        assertEquals(200, k.knapsackDP());
    }

    /**
     * Test the {@link JsonReader} with a json file representing the mentioned problem in {@link #test3Values()}
     */
    public void testJSONInput() {
        KnapsackProblem k = JsonReader.createKnapsack("examples/input.json");
        assertEquals(190, k.knapsackGreedy());
        assertEquals(200, k.knapsackDP());
    }

    /**
     * Test an an error on reading a json file (file does not exist)
     */
    public void testNoJSONFile() {
        exception.expect(IOException.class);
        KnapsackProblem k = JsonReader.createKnapsack("examples/input");
    }

    /**
     * Test a malformed json file
     */
    public void testMalformedJSON() {
        exception.expect(JSONException.class);
        KnapsackProblem k = JsonReader.createKnapsack("examples/malformed_input.json");
    }

    /**
     * Check the following problem:
     * <p>Item A: Value = 40, Weight = 4</p>
     * <p>Item B: Value = 30, Weight = 6</p>
     * <p>Item C: Value = 10, Weight = 5</p>
     * <p>Item D: Value = 50, Weight = 3</p>
     * <p>Weight of Knapsack = 10</p>
     * <p>Optimal (<strong>Dynamic Programming</strong>) Solution: Item A + D. Value = 90, Weight = 7</p>
     * <p><strong>Greedy:</strong></p>
     * <p>Ratios:</p>
     * <ul>
     * <li>Item A = 10</li>
     * <li>Item B = 5</li>
     * <li>Item C = 2</li>
     * <li>Item D = 16</li>
     * </ul>
     * Greedy Solution: Value = 90, Weight = 7.
     */
    public void testCSVInput() {
        KnapsackProblem k = CSVReader.createKnapsack("examples/input.csv");
        assertEquals(90, k.knapsackGreedy());
        assertEquals(90, k.knapsackDP());
    }

    /**
     * Test an an error on reading a csv file (file does not exist)
     */
    public void testNoCSVFile() {
        exception.expect(IOException.class);
        KnapsackProblem k = CSVReader.createKnapsack("examples/input");
    }

    /**
     * Test a malformed csv file
     */
    public void testMalformedCSV() {
        exception.expect(NumberFormatException.class);
        KnapsackProblem k = CSVReader.createKnapsack("examples/malformed_input.csv");
    }

}
