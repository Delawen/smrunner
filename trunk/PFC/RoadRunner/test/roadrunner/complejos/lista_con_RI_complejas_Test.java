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
public class lista_con_RI_complejas_Test {

    public lista_con_RI_complejas_Test() {
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
        String[] prueba = {
                "test/roadrunner/complejos/html/A_sample2.xhtml",
                "test/roadrunner/complejos/html/A_sample1.xhtml",
                "test/roadrunner/complejos/html/A_sample4.xhtml"};
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<body><ul>(<li>&VARIABLE;(<b>&VARIABLE;</b>)?(<i>cursiva</i>)?</li>)+</ul></body>", w.toString());
    }
    @Test
    public void test2()
    {
        System.out.println("addList sin elementos");
        String[] prueba = {
                "test/roadrunner/complejos/html/A_sample4.xhtml",
                "test/roadrunner/complejos/html/A_sample1.xhtml",
                "test/roadrunner/complejos/html/A_sample2.xhtml"};
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<body><ul>(<li>&VARIABLE;(<b>&VARIABLE;</b>)?(<i>cursiva</i>)?</li>)+</ul></body>", w.toString());
    }

        @Test
    public void test3()
    {
        System.out.println("addList sin elementos");
        String[] prueba = {
                "test/roadrunner/complejos/html/A_sample1.xhtml",
                "test/roadrunner/complejos/html/A_sample4.xhtml",
                "test/roadrunner/complejos/html/A_sample2.xhtml"};
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<body><ul>(<li>&VARIABLE;(<b>&VARIABLE;</b>)?(<i>cursiva</i>)?</li>)+</ul></body>", w.toString());
    }
        @Test
    public void test4()
    {
        System.out.println("addList sin elementos");
        String[] prueba = {
                "test/roadrunner/complejos/html/A_sample2.xhtml",
                "test/roadrunner/complejos/html/A_sample4.xhtml",
                "test/roadrunner/complejos/html/A_sample1.xhtml"};
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<body><ul>(<li>&VARIABLE;(<b>&VARIABLE;</b>)?(<i>cursiva</i>)?</li>)+</ul></body>", w.toString());
    }
        @Test
    public void test5()
    {
        System.out.println("t5");
        String[] prueba = {
                    "test/roadrunner/complejos/html/A_sample3.xhtml",
                "test/roadrunner/complejos/html/A_sample4.xhtml",
                "test/roadrunner/complejos/html/A_sample1.xhtml"};
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<body><ul>" +
                "(<li>A(<b>negrita2</b>)?</li>" +
                "<li>B(<i>cursiva</i>)?</li>" +
                "<li>C<b>negrita</b></li>" +
                "<li>D<b>otra negrita</b></li>" +
                "<li>E</li>)?" +
                "</ul></body>", w.toString());
    }

       @Test
    public void test6()
    {
        System.out.println("addList sin elementos");
        String[] prueba = {
                "test/roadrunner/complejos/html/A_sample3.xhtml",
                "test/roadrunner/complejos/html/A_sample4.xhtml",
                "test/roadrunner/complejos/html/A_sample1.xhtml",
                "test/roadrunner/complejos/html/A_sample2.xhtml"};
        RoadRunner rr = new RoadRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<body><ul>((<li>&VARIABLE;(<b>&VARIABLE;</b>)?(<i>cursiva</i>)?</li>)+)?</ul></body>",w.toString());
    }

}