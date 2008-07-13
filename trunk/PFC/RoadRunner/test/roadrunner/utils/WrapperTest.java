/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.utils;

import SMTree.SMTree;
import SMTree.utils.Enclosure;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import roadrunner.iterator.EdibleIterator;
import roadrunner.node.Item;
import roadrunner.node.Token;
import roadrunner.operator.DirectionOperator;

/**
 *
 * @author delawen
 */
public class WrapperTest 
{

    public WrapperTest() 
    {
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

    /**
     * Test of eatSquare method, of class Wrapper.
     */
    @Test
    public void eatSquare() {
        System.out.println("eatSquare");
        Edible e = null;
        Item t = null;
        DirectionOperator d = null;
        Wrapper instance = new Wrapper();
        Item expResult = null;
        Item result = instance.eatSquare(e, t, d);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTree method, of class Wrapper.
     */
    @Test
    public void getTree() {
        System.out.println("getTree");
        Wrapper instance = new Wrapper();
        SMTree<Item> expResult = null;
        SMTree<Item> result = instance.getTree();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of eat method, of class Wrapper.
     */
    @Test
    public void eat() {
        System.out.println("eat");
        Sample s = null;
        DirectionOperator d = null;
        Wrapper instance = new Wrapper();
        Mismatch expResult = null;
        Mismatch result = instance.eat(s, d);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of goTo method, of class Wrapper.
     */
    @Test
    public void goTo() {
        System.out.println("goTo");
        Item i = null;
        Wrapper instance = new Wrapper();
        boolean expResult = false;
        boolean result = instance.goTo(i);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of isWellFormed method, of class Wrapper.
     */
    @Test
    public void isWellFormed() {
        System.out.println("isWellFormed");
        Token from = null;
        Enclosure inclusionFrom = null;
        Token to = null;
        Enclosure inclusionTo = null;
        Wrapper instance = new Wrapper();
        boolean expResult = false;
        boolean result = instance.isWellFormed(from, inclusionFrom, to, inclusionTo);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of substitute method, of class Wrapper.
     */
    @Test
    public void substitute() {
        System.out.println("substitute");
        Item from = null;
        Enclosure inclusionFrom = null;
        Item to = null;
        Enclosure inclusionTo = null;
        SMTree what = null;
        Wrapper instance = new Wrapper();
        boolean expResult = false;
        boolean result = instance.substitute(from, inclusionFrom, to, inclusionTo, what);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchWellFormed method, of class Wrapper.
     */
    @Test
    public void searchWellFormed() {
        System.out.println("searchWellFormed");
        Token t = null;
        Token from = null;
        DirectionOperator d = null;
        Wrapper instance = new Wrapper();
        Token expResult = null;
        Token result = instance.searchWellFormed(t, from, d);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of iterator method, of class Wrapper.
     */
    @Test
    public void iterator() {
        System.out.println("iterator");
        Class iteratorClass = null;
        Wrapper instance = new Wrapper();
        EdibleIterator expResult = null;
        EdibleIterator result = instance.iterator(iteratorClass);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of cloneSubWrapper method, of class Wrapper.
     */
    @Test
    public void cloneSubWrapper() {
        System.out.println("cloneSubWrapper");
        Token firstTokenSquare = null;
        Token lastTokenSquare = null;
        Item parent = null;
        Wrapper instance = new Wrapper();
        Wrapper expResult = null;
        Wrapper result = instance.cloneSubWrapper(firstTokenSquare, lastTokenSquare, parent);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}