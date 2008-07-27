/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
public class addOptionaladdListTest {

    public addOptionaladdListTest() {
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
        System.out.println("addList con addVariable interno");
        String[] prueba = new String[2];
        String s = "test/roadrunner/ejemplosComplejos/basico.html";
        prueba[0] = s;
        s = "test/roadrunner/ejemplosComplejos/test3.html";
        prueba[1] = s;
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);

    }
    

}