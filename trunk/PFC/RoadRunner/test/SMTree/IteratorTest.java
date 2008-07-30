/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SMTree;

import SMTree.iterator.BackwardIterator;
import SMTree.iterator.ForwardIterator;
import SMTree.utils.Kinship;
import SMTree.iterator.SMTreeIterator;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import SMTree.iterator.LevelIterator;
import java.util.Iterator;
import java.util.ListIterator;


/**
 *
 * @author delawen
 */
public class IteratorTest 
{
    private SMTree<String> arbol;

    public IteratorTest() 
    {
        System.out.println("Iterator");
    }

        SMTreeNode<String> node1 = new SMTreeNode<String>("1");
        
        SMTreeNode<String> node1_1 = new SMTreeNode<String>("1.1");    
        SMTreeNode<String> node1_2 = new SMTreeNode<String>("1.2");    
        SMTreeNode<String> node1_3 = new SMTreeNode<String>("1.3");        
        SMTreeNode<String> node1_4 = new SMTreeNode<String>("1.4"); 
        
        SMTreeNode<String> node1_1_1 = new SMTreeNode<String>("1.1.1"); 
        SMTreeNode<String> node1_1_2 = new SMTreeNode<String>("1.1.2");
        
        SMTreeNode<String> node1_4_1 = new SMTreeNode<String>("1.4.1"); 
        SMTreeNode<String> node1_4_2 = new SMTreeNode<String>("1.4.2");
        
        SMTreeNode<String> node1_1_2_1 = new SMTreeNode<String>("1.1.2.1");
        SMTreeNode<String> node1_1_2_2 = new SMTreeNode<String>("1.1.2.2");
        
    @Before
    public void setUp() 
    {
        arbol = new SMTree<String>(node1);
        
        arbol.addSMTreeNode(node1_1, arbol.getRoot(), Kinship.CHILD);
        arbol.addSMTreeNode(node1_2, arbol.getRoot(), Kinship.CHILD);
        arbol.addSMTreeNode(node1_3, arbol.getRoot(), Kinship.CHILD);
        arbol.addSMTreeNode(node1_4, arbol.getRoot(), Kinship.CHILD);
        
        arbol.addSMTreeNode(node1_1_1, node1_1, Kinship.CHILD);
        arbol.addSMTreeNode(node1_1_2, node1_1, Kinship.CHILD);
        
        arbol.addSMTreeNode(node1_4_1, node1_4, Kinship.CHILD);
        arbol.addSMTreeNode(node1_4_2,  node1_4, Kinship.CHILD);
        
        arbol.addSMTreeNode(node1_1_2_1, node1_1_2, Kinship.CHILD);
        arbol.addSMTreeNode(node1_1_2_2, node1_1_2, Kinship.CHILD);
        
    }

    @After
    public void tearDown() {
    }

    
    @Test
    public void amplitudIterator() throws Exception
    {
        System.out.println("LevelIterator");
        
        //Creamos manualmente el orden:
        LinkedList<SMTreeNode<String>> orden = new LinkedList<SMTreeNode<String>>();
        orden.add(node1);
        orden.add(node1_1);
        orden.add(node1_2);
        orden.add(node1_3);
        orden.add(node1_4);
        orden.add(node1_1_1);
        orden.add(node1_1_2);
        orden.add(node1_4_1);
        orden.add(node1_4_2);
        orden.add(node1_1_2_1);
        orden.add(node1_1_2_2);
        
        ListIterator itorden = orden.listIterator();
        
        //Recorremos en amplitud y comprobamos:
        SMTreeIterator<String> it = arbol.iterator(LevelIterator.class);
        while(it.hasNext() && itorden.hasNext())
        {
            String next = (String)it.nextObject();
            String otro = (String) ((SMTreeNode<String>)itorden.next()).getObject();
            assertEquals(next, otro);
        }
        
        assertFalse(it.hasNext());
        assertFalse(itorden.hasNext());
        
        // Y ahora lo probamos al reves
        itorden = orden.listIterator(orden.size());
        
        //Recorremos en amplitud y comprobamos:
        it = arbol.iterator(LevelIterator.class);
        
        while(it.hasNext())
             it.nextObject();
        
        while(it.hasPrevious() && itorden.hasPrevious())
        {
            String next = (String)it.previousObject();
            String otro = (String) ((SMTreeNode<String>)itorden.previous()).getObject();
            assertEquals(next, otro);
        }
        
        assertFalse(it.hasPrevious());
        assertFalse(itorden.hasPrevious());
    }
    
    @Test
    public void backwardItemIterator() throws Exception
    {
        System.out.println("backwardItemIterator");
        
        //Creamos manualmente el orden:
        LinkedList<SMTreeNode<String>> orden = new LinkedList<SMTreeNode<String>>();
   /*     
        orden.add(node1);
        orden.add(node1_4);
        orden.add(node1_4_2);
        orden.add(node1_4_1);
        orden.add(node1_3);
        orden.add(node1_2);
        orden.add(node1_1);
        orden.add(node1_1_2);
        orden.add(node1_1_2_2);
        orden.add(node1_1_2_1);
        orden.add(node1_1_1);
        
*/  
        
        orden.add(node1_4_2);
        orden.add(node1_4_1);
        orden.add(node1_4);
        orden.add(node1_3);
        
        orden.add(node1_2);  
        
        orden.add(node1_1_2_2);
        orden.add(node1_1_2_1);
        orden.add(node1_1_2);
        orden.add(node1_1_1);  
        orden.add(node1_1); 
        orden.add(node1);
        
        
        ListIterator itorden = orden.listIterator();
        
        //Recorremos en amplitud y comprobamos:
        SMTreeIterator<String> it = arbol.iterator(BackwardIterator.class);
        while(it.hasNext() && itorden.hasNext())
        {
            it.toString();
            String next = (String)it.nextObject();
            String otro = (String) ((SMTreeNode<String>)itorden.next()).getObject();
            assertEquals(next, otro);
        }
        
        
        assertFalse(it.hasNext());
        assertFalse(itorden.hasNext());
        
        // Y ahora lo probamos al reves
        itorden = orden.listIterator(orden.size());
        
        //Recorremos en amplitud y comprobamos:
        it = arbol.iterator(BackwardIterator.class);
        while(it.hasNext())
             it.nextObject();
        while(it.hasPrevious() && itorden.hasPrevious())
        {
            it.toString();
            String next = (String)it.previousObject();
            String otro = (String) ((SMTreeNode<String>)itorden.previous()).getObject();
            assertEquals(next, otro);
        }
        
        assertFalse(it.hasPrevious());
        assertFalse(itorden.hasPrevious());
    }

    @Test
    public void forwardItemIterator() throws Exception
    {
        System.out.println("forwardItemIterator");
                
        //Creamos manualmente el orden:
        LinkedList<SMTreeNode<String>> orden = new LinkedList<SMTreeNode<String>>();
        orden.add(node1);
        orden.add(node1_1);
        orden.add(node1_1_1);
        orden.add(node1_1_2);
        orden.add(node1_1_2_1);
        orden.add(node1_1_2_2);
        orden.add(node1_2);
        orden.add(node1_3);
        orden.add(node1_4);
        orden.add(node1_4_1);
        orden.add(node1_4_2);
        
        ListIterator itorden = orden.listIterator();
        
        //Recorremos en amplitud y comprobamos:
        SMTreeIterator<String> it = arbol.iterator(ForwardIterator.class);
        while(it.hasNext() && itorden.hasNext())
        {
            it.toString();
            String next = (String)it.nextObject();
            String otro = (String) ((SMTreeNode<String>)itorden.next()).getObject();
            assertEquals(next, otro);
        }
        
        assertFalse(it.hasNext());
        assertFalse(itorden.hasNext());
        
        // Y ahora lo probamos al reves
        itorden = orden.listIterator(orden.size());
        
        //Recorremos en amplitud y comprobamos:
        it = arbol.iterator(ForwardIterator.class);
        //colocamos al final el iterador:
        while(it.hasNext())
             it.nextObject();
        
        while(it.hasPrevious() && itorden.hasPrevious())
        {
            it.toString();
            String next = (String)it.previousObject();
            String otro = (String) ((SMTreeNode<String>)itorden.previous()).getObject();
            assertEquals(next, otro);
        }
        assertFalse(it.hasPrevious());
        assertFalse(itorden.hasPrevious());
    }
}
