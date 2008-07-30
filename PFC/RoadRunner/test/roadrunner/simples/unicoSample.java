/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.simples;

import roadrunner.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import roadrunner.utils.Edible;

/**
 *
 * @author delawen
 */
public class unicoSample {

    public unicoSample() {
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
        System.out.println("Test simple para RoadRunner: un solo sample");
        String[] prueba = new String[1];
        String s = "test/roadrunner/simples/html/sample1.html";
        prueba[0] = s;
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);

    }
}