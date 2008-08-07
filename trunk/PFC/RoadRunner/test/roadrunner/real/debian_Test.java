/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.real;

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
public class debian_Test {

    public debian_Test() {
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
        String[] prueba = { "test/roadrunner/real/html/debian1.xhtml",
                            "test/roadrunner/real/html/debian2.xhtml",
                              "test/roadrunner/real/html/debian3.xhtml"};
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
    }

}