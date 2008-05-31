/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SMTree;

import java.util.Random;
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
        System.out.println("SMTreeNodeTest");
    }

    /**
     * Test of getNext method, of class SMTreeNode.
     */
    @Test
    public void getAndsetNext() 
    {
        System.out.println("getAndsetNext()");
        SMTreeNode<T> instance = new SMTreeNode<T>(new T());
        SMTreeNode<T> expResult = new SMTreeNode<T>(new T());
        instance.setNext(expResult);
        assertEquals(instance.getNext(), expResult);
    }


    /**
     * Test of getParent method, of class SMTreeNode.
     */
    @Test
    public void getAndsetParent()
    {
        System.out.println("getAndsetParent()");
        SMTreeNode<T> instance = new SMTreeNode<T>(new T());
        SMTreeNode<T> expResult = new SMTreeNode<T>(new T());
        instance.setParent(expResult);
        assertEquals(expResult, instance.getParent());
    }

    /**
     * Test of getPrevious method, of class SMTreeNode.
     */
    @Test
    public void getAndsetPrevious() {
        System.out.println("getAndsetPrevious()");
        SMTreeNode<T> instance = new SMTreeNode<T>(new T());
        SMTreeNode<T> expResult = new SMTreeNode<T>(new T());
        instance.setPrevious(expResult);
        assertEquals(instance.getPrevious(), expResult);
    }

    /**
     * Test of getObject method, of class SMTreeNode.
     */
    @Test
    public void getObject() {
        System.out.println("getObject()");
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
        System.out.println("setObject()");
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
        System.out.println("getAndsetLastChild()");
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
        System.out.println("getAndsetFirstChild()");
        SMTreeNode<T> instance = new SMTreeNode<T>(new T());
        SMTreeNode<T> expResult = new SMTreeNode<T>(new T());
        instance.setFirstChild(expResult);
        assertEquals(expResult, instance.getFirstChild());
    }

    @Test
    public void testClone()
    {
        System.out.println("clone()");
        
        Random random = new Random();
        int rand = random.nextInt(100) + 20;
        for(int i = 0; i < rand; i++)
        {
            
            SMTreeNode<T> instance = new SMTreeNode<T>(new T());
            instance.setFirstChild(new SMTreeNode<T>(new T()));
            instance.setLastChild(new SMTreeNode<T>(new T()));
            instance.setNext(new SMTreeNode<T>(new T()));
            instance.setParent(new SMTreeNode<T>(new T()));
            instance.setPrevious(new SMTreeNode<T>(new T()));
            
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
    }

    /**
     * Test of equals method, of class SMTreeNode.
     */
    @Test
    public void equals() 
    {
        System.out.println("equals()");
        T t = new T();
        SMTreeNode<T> instance2 = new SMTreeNode<T>(t);
        SMTreeNode<T> instance = new SMTreeNode<T>(t);
        assertEquals(instance, instance2);
    }

}
