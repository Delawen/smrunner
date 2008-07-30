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
public class todos {

    public todos() {
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
    public void test5()
    {
        System.out.println("¡¡Todo junto!!");
        String[] prueba = new String[5];
        String s = "test/roadrunner/simples/html/sample1.html";
        prueba[0] = s;
        s = "test/roadrunner/simples/html/sample2.html";
        prueba[1] = s;
        s = "test/roadrunner/simples/html/sample3.html";
        prueba[2] = s;
        s = "test/roadrunner/simples/html/sample4.html";
        prueba[3] = s;
        s = "test/roadrunner/simples/html/sample5.html";
        prueba[4] = s;
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);

    }

}