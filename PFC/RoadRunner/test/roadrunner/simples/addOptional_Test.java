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
public class addOptional_Test {

    public addOptional_Test() {
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
        System.out.println("Test complejo para RoadRunner: dos sample y un addOptional simple (Optional en Wrapper)");
        String[] prueba = {
                "test/roadrunner/simples/html/sample1.html",
                "test/roadrunner/simples/html/sample4.html"};
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<body><p>Esto es otro parrafo</p>(<b>Esto en cambio es opcional</b><i>Hola</i>)?</body>",w.toString());
    }
    
    @Test
    public void test2()
    {
        System.out.println("Test complejo para RoadRunner: dos sample y un addOptional simple (Optional en Sample)");
        String[] prueba = {
                "test/roadrunner/simples/html/sample4.html",
                "test/roadrunner/simples/html/sample1.html"};
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<body><p>Esto es otro parrafo</p>(<b>Esto en cambio es opcional</b><i>Hola</i>)?</body>",w.toString());

    }

}