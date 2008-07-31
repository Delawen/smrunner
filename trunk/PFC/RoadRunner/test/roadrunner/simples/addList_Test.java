/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

// ftp://practica:practica@doctorado.lsi.us.es/DBLP

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
public class addList_Test {

    public addList_Test() {
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
        System.out.println("Test complejo para RoadRunner: dos sample y un addList simple");
        String[] prueba = new String[2];
        String s = "test/roadrunner/simples/html/sample1.html";
        prueba[0] = s;
        s = "test/roadrunner/simples/html/sample3.html";
        prueba[1] = s;
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);

    }
    
    @Test
    public void test2()
    {
        System.out.println("Test complejo para RoadRunner: dos sample y un addList simple");
        String[] prueba = new String[2];
        String s = "test/roadrunner/simples/html/sample3.html";
        prueba[0] = s;
        s = "test/roadrunner/simples/html/sample1.html";
        prueba[1] = s;
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);

    }

}