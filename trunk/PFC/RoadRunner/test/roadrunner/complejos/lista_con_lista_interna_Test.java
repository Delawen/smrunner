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
public class lista_con_lista_interna_Test {

    public lista_con_lista_interna_Test() {
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
        System.out.println("addList con RI simples");
        String[] prueba = new String[2];
        String s = "test/roadrunner/complejos/html/A_sample4.xhtml";
        prueba[0] = s;
        s = "test/roadrunner/complejos/html/A_sample5.xhtml";
        prueba[1] = s;
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
    }
}