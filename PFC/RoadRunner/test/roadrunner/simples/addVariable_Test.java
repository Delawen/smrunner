/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.simples;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import roadrunner.RoadRunner;
import roadrunner.utils.Edible;
import static org.junit.Assert.*;

/**
 *
 * @author santi
 */
public class addVariable_Test {

    public addVariable_Test() {
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
    public void test1()
    {
        System.out.println("Test complejo para RoadRunner: dos sample y un addVariable");
        String[] prueba = {
                "test/roadrunner/simples/html/sample1.html",
                "test/roadrunner/simples/html/sample2.html"
        };
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        
        assertEquals("<body><p>&VARIABLE;</p></body>",w.toString());

    }
    @Test
    public void test2()
    {
        System.out.println("Test complejo para RoadRunner: dos sample y un addVariable");
        String[] prueba = {
                "test/roadrunner/simples/html/sample2.html",
                "test/roadrunner/simples/html/sample1.html"
        };
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        
        assertEquals("<body><p>&VARIABLE;</p></body>",w.toString());
    }

}