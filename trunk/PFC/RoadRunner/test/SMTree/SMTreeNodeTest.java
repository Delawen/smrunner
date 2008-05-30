/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SMTree;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author delawen
 */
public class SMTreeNodeTest {

    public SMTreeNodeTest() {
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
     * Test of getNext method, of class SMTreeNode.
     */
    @Test
    public void getAndsetNext() 
    {
        SMTreeNode<T> instance = new SMTreeNode<T>(new T());
        SMTreeNode<T> expResult = new SMTreeNode<T>(new T());
        instance.setNext(expResult);
        assertEquals(instance.getNext(), expResult);
        assertEquals(expResult.getPrevious(), instance);
    }


    /**
     * Test of getParent method, of class SMTreeNode.
     */
    @Test
    public void getAndsetParent()
    {
        SMTreeNode<T> instance = new SMTreeNode<T>(new T());
        SMTreeNode<T> expResult = new SMTreeNode<T>(new T());
        instance.setParent(expResult);
        assertEquals(expResult, instance.getParent());
        assertEquals(expResult.getFirstChild(), instance);
        assertEquals(expResult.getLastChild(), instance);
    }

    /**
     * Test of getPrevious method, of class SMTreeNode.
     */
    @Test
    public void getAndsetPrevious() {
        SMTreeNode<T> instance = new SMTreeNode<T>(new T());
        SMTreeNode<T> expResult = new SMTreeNode<T>(new T());
        instance.setPrevious(expResult);
        assertEquals(instance.getPrevious(), expResult);
        assertEquals(expResult.getNext(), instance);
    }

    /**
     * Test of getObject method, of class SMTreeNode.
     */
    @Test
    public void getObject() {
        T expResult = new T();
        SMTreeNode<T> instance = new SMTreeNode<T>(expResult);
        
        assertEquals(expResult, instance.getObject());
    }

    /**
     * Test of setObject method, of class SMTreeNode.
     */
    @Test
    public void setObject() 
    {
        T val = new T();
        SMTreeNode<T> instance = new SMTreeNode<T>(new T());
        instance.setObject(val);
        assertEquals(val, instance.getObject());
    }

    /**
     * Test of getLastChild method, of class SMTreeNode.
     */
    @Test
    public void getAndsetLastChild() 
    {
        SMTreeNode<T> instance = new SMTreeNode<T>(new T());
        SMTreeNode<T> expResult = new SMTreeNode<T>(new T());
        instance.setLastChild(expResult);
        assertEquals(expResult, instance.getLastChild());
    }

    /**
     * Test of getFirstChild method, of class SMTreeNode.
     */
    @Test
    public void getAndsetFirstChild() 
    {
        SMTreeNode<T> instance = new SMTreeNode<T>(new T());
        SMTreeNode<T> expResult = new SMTreeNode<T>(new T());
        instance.setFirstChild(expResult);
        assertEquals(expResult, instance.getFirstChild());
    }

    @Test
    public void testClone()
    {
        SMTreeNode<T> instance = new SMTreeNode<T>(new T());
        try
        {
            SMTreeNode<T> instance2 = instance.clone();
            assertEquals(instance, instance2);
        }
        catch(Exception e)
        {
            fail("Exception: " + e);
        }
    }

    /**
     * Test of equals method, of class SMTreeNode.
     */
    @Test
    public void equals() 
    {
        T t = new T();
        SMTreeNode<T> instance2 = new SMTreeNode<T>(t);
        SMTreeNode<T> instance = new SMTreeNode<T>(t);
        assertEquals(instance, instance2);
    }

}
