/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.medios;

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
public class DOF_variable_Test {

    public DOF_variable_Test() {
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
        System.out.println("DOF's");
        String[] prueba = { "test/roadrunner/medios/html/DOF_variable1.xhtml",
                            "test/roadrunner/medios/html/DOF_variable2.xhtml"};
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("&VARIABLE;",w.toString());
    }

}