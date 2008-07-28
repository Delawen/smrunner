/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.etiquetasAperturaCierre;

import roadrunner.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import roadrunner.utils.Edible;
import static org.junit.Assert.*;

/**
 *
 * @author santi
 */
public class etiquetasDeAperturaCierre {

    public etiquetasDeAperturaCierre() {
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
        System.out.println("Test para comprobar etiquetas del tipo <img/>");
        String[] prueba = new String[2];
        prueba[0] = "test/roadrunner/etiquetasAperturaCierre/sample1.xhtml";
        prueba[1] = "test/roadrunner/etiquetasAperturaCierre/sample2.xhtml";
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
    }
    
    @Test
    public void test2()
    {
        System.out.println("Test para comprobar etiquetas del tipo <img/>");
        String[] prueba = new String[2];
        prueba[0] = "test/roadrunner/etiquetasAperturaCierre/sample2.xhtml";
        prueba[1] = "test/roadrunner/etiquetasAperturaCierre/sample1.xhtml";
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
    }
}