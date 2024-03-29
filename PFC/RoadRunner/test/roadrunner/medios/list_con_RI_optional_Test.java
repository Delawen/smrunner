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
import roadrunner.iterator.EdibleIterator;
import roadrunner.iterator.ForwardTokenIterator;
import roadrunner.node.Token;
import roadrunner.utils.Edible;
import roadrunner.utils.Wrapper;
import static org.junit.Assert.*;

/**
 *
 * @author delawen
 */
public class list_con_RI_optional_Test {

    public list_con_RI_optional_Test() {
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
        System.out.println("addList con addOptional interno");
        String[] prueba = {
                "test/roadrunner/medios/html/basico.html",
                "test/roadrunner/medios/html/test2.html"};
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<html>(<div>HTML Básico(<b>Esto introduce un addOptional</b>)?</div>)+</html>", w.toString());
    }
    
    @Test
    public void test2()
    {
        System.out.println("addList con addOptional interno");
        String[] prueba = {
                "test/roadrunner/medios/html/test2.html",
                "test/roadrunner/medios/html/basico.html"};
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<html>(<div>HTML Básico(<b>Esto introduce un addOptional</b>)?</div>)+</html>", w.toString());
    }
    

}