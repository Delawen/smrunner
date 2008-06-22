/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import roadrunner.iterator.EdibleIterator;
import roadrunner.iterator.ForwardTokenIterator;
import roadrunner.node.Token;
import roadrunner.utils.Wrapper;
import static org.junit.Assert.*;

/**
 *
 * @author delawen
 */
public class RoadRunnerTest {

    public RoadRunnerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void test()
    {
        System.out.println("Test simple para RoadRunner: un solo sample");
        String[] prueba = new String[1];
        String s = "test/roadrunner/sample1.html";
        prueba[0] = s;
        RoadRunner rr = new RoadRunner(prueba);
        Wrapper w = rr.process();
        EdibleIterator it = w.iterator(ForwardTokenIterator.class);
        while(it.hasNext())
        {
            Token i = (Token) it.next();
            System.out.println(i.toString());
        }

    }
    
    @Test
    public void test2()
    {
        System.out.println("Test complejo para RoadRunner: varios sample");
        String[] prueba = new String[2];
        String s = "test/roadrunner/sample1.html";
        prueba[0] = s;
        s = "test/roadrunner/sample2.html";
        prueba[1] = s;
        RoadRunner rr = new RoadRunner(prueba);
        Wrapper w = rr.process();
        EdibleIterator it = w.iterator(ForwardTokenIterator.class);
        while(it.hasNext())
            System.out.println(it.next());

    }

}