/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.utils;

import SMTree.SMTree;
import SMTree.SMTreeNode;
import SMTree.utils.Kinship;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import roadrunner.iterator.EdibleIterator;
import roadrunner.iterator.webPageForwardIterator;
import static org.junit.Assert.*;
import roadrunner.node.*;
import roadrunner.operator.DirectionOperator;

/**
 *
 * @author delawen
 */
public class WrapperTest 
{
    
    private static Wrapper instance;

    private static Wrapper instance2;

    public WrapperTest() 
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception 
    {
        SMTreeNode<Item> raiz = new SMTreeNode<Item>(new Tuple());
        SMTree<Item> tree = new SMTree<Item>(raiz);
        Item lista = new roadrunner.node.List();
        tree.addObject(lista, raiz, Kinship.CHILD);
        SMTreeNode<Item> listaNode = tree.getNode(lista);
            tree.addObject(new Tag("<li>"), listaNode, Kinship.CHILD);
                tree.addObject(new Variable(), listaNode, Kinship.CHILD);
            tree.addObject(new Tag("</li>"), listaNode, Kinship.CHILD);
       instance = new Wrapper(tree);
       
        SMTreeNode<Item> raiz2 = new SMTreeNode<Item>(new Tuple());
        SMTree<Item> tree2 = new SMTree<Item>(raiz2);
        tree2.addObject(new DOF(), raiz2, Kinship.CHILD);
        Item lista2 = new roadrunner.node.List();
        tree2.addObject(lista2, raiz2, Kinship.CHILD);
        SMTreeNode<Item> listaNode2 = tree2.getNode(lista2);
            tree2.addObject(new Tag("<li>"), listaNode2, Kinship.CHILD);
                tree2.addObject(new Variable(), listaNode2, Kinship.CHILD);
            tree2.addObject(new Tag("</li>"), listaNode2, Kinship.CHILD);
        tree2.addObject(new DOF(), raiz2, Kinship.CHILD);
       instance2 = new Wrapper(tree2);
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
        Edible e = new Sample("test/roadrunner/utils/sample1.html");
        EdibleIterator it = (EdibleIterator) e.iterator(webPageForwardIterator.class);
        it.next();
        Item t = (Item) it.next();
        DirectionOperator d = DirectionOperator.DOWNWARDS;
        Item expResult = null;
        while(it.hasNext())
        {
            expResult = (Item) it.next();
            if(expResult.getContent().equals("li") && expResult instanceof Tag && ((Tag)expResult).isCloseTag())
                break;
        }
        assertNotNull(expResult);
        Item result = instance.eatSquare(e, t, d);
        assertEquals(expResult, result);
    }

    /**
     * Test of eat method, of class Wrapper.
     */
    @Test
    public void eat() {
        System.out.println("eat");
        Sample s = new Sample("test/roadrunner/utils/sample2.html");
        DirectionOperator d = DirectionOperator.DOWNWARDS;
        Mismatch result = instance2.eat(s, d);
        assertNull(result);
        d = DirectionOperator.UPWARDS;
        result = instance2.eat(s, d);
        assertNull(result);      
        s = new Sample("test/roadrunner/utils/sample3.html");
        d = DirectionOperator.DOWNWARDS;
        result = instance2.eat(s, d);
        assertNull(result);
        d = DirectionOperator.UPWARDS;
        result = instance2.eat(s, d);
        assertNull(result);
        s = new Sample("test/roadrunner/utils/sample4.html");
        d = DirectionOperator.DOWNWARDS;
        result = instance2.eat(s, d);
        assertNotNull(result);
    }

    /**
     * Test of searchWellFormed method, of class Wrapper.
     */
    @Test
    public void searchWellFormed() 
    {
        System.out.println("searchWellFormed");
        Token from = (Token) instance.getTree().getRoot().getFirstChild().getFirstChild().getObject();
        Token t = new Tag("</li>");
        DirectionOperator d = DirectionOperator.DOWNWARDS;
        Token expResult = (Token) instance.getTree().getRoot().getFirstChild().getLastChild().getObject();
        Token result = instance.searchWellFormed(t, from, d);
        assertEquals(expResult, result);
    }
}