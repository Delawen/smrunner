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
public class SMIndexStructureTest {

    public SMIndexStructureTest() {
    }

    /**
     * Test of add method, of class SMIndexStructure.
     */
    @Test
    public void addAndGet() {
        T t = new T();
        SMTreeNode<T> n = new SMTreeNode<T>(t);
        SMIndexStructure<T> instance = new SMIndexStructure<T>();
        instance.add(n);
        assertEquals(instance.get(t), n);
        
        Random r = new Random();
        int rand = r.nextInt(50);
        
        for(int j = 1; j < rand; j++)
        {
            int k = r.nextInt(100);
            for(int i = 1; i < k; i++)
                 instance.add(new SMTreeNode<T>(new T()));

            T t2 = new T();
            SMTreeNode<T> n2 = new SMTreeNode<T>(t2);
            instance.add(n2);

            k = r.nextInt(100);
            for(int i = 1; i < k; i++)
                 instance.add(new SMTreeNode<T>(new T()));

            assertEquals(instance.get(t2), n2);
        }
    }

    /**
     * Test of remove method, of class SMIndexStructure.
     */
    @Test
    public void remove() 
    {
        T t = new T();
        SMIndexStructure<T> instance = new SMIndexStructure<T>();
        
        Random r = new Random();
        int rand = r.nextInt(50);
        
        for(int j = 1; j < rand; j++)
        {
            int k = r.nextInt(100);
            for(int i = 1; i < k; i++)
                 instance.add(new SMTreeNode<T>(new T()));

            instance.add(new SMTreeNode<T>(t));

            k = r.nextInt(100);
            for(int i = 1; i < k; i++)
                 instance.add(new SMTreeNode<T>(new T()));

            instance.remove(t);

            assertNull(instance.get(t));
        }
    }
}