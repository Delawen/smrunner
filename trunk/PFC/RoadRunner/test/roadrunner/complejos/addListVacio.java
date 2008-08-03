/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.complejos;

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
 * @author delawen
 */
public class addListVacio {

    public addListVacio() {
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
        System.out.println("addList sin elementos");
        String[] prueba = new String[3];
        String s = "test/roadrunner/complejos/html/A_sample2.xhtml";
        prueba[0] = s;
        s = "test/roadrunner/complejos/html/A_sample1.xhtml";
        prueba[1] = s;
        s = "test/roadrunner/complejos/html/A_sample3.xhtml";
        prueba[2] = s;
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
    }


    @Test
    public void test2()
    {
        System.out.println("addList sin elementos");
        String[] prueba = new String[3];
        String s = "test/roadrunner/complejos/html/A_sample3.xhtml";
        prueba[0] = s;
        s = "test/roadrunner/complejos/html/A_sample1.xhtml";
        prueba[1] = s;
        s = "test/roadrunner/complejos/html/A_sample2.xhtml";
        prueba[2] = s;
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
    }

}