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
public class SMTreeTest {

    public SMTreeTest() {
    }

    /**
     * Test of setRootObject method, of class SMTree.
     */
    @Test
    public void setRootObject() {
        T o = new T();
        SMTree<T> instance = new SMTree<T>();
        instance.setRootObject(o);
        assertEquals(instance.getRoot().getObject(),o);
    }

    /**
     * Test of getRoot method, of class SMTree.
     */
    @Test
    public void getRoot() 
    {
        SMTreeNode<T> expResult = new SMTreeNode<T>(new T());
        SMTree<T> instance = new SMTree<T>(expResult);
        SMTreeNode<T> result = instance.getRoot();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRoot method, of class SMTree.
     */
    @Test
    public void setRoot() {
        SMTreeNode<T> val = new SMTreeNode<T>(new T());
        SMTree<T> instance = new SMTree<T>();
        instance.setRoot(val);
        assertEquals(instance.getRoot(), val);
    }

    /**
     * Test of addSubSMTree method, of class SMTree.
     */
    @Test
    public void addSubSMTree_left() {
        SMTree<T> subtree = new SMTree<T>(new SMTreeNode<T>(new T()));
        SMTreeNode<T> where = new SMTreeNode<T>(new T());
        Kinship k = Kinship.LEFTSIBLING;
        SMTree<T> instance = new SMTree<T>(where);
        if(!instance.addSubSMTree(subtree, where, k))
            fail("addSubSMTree devolvió false.");
        assertEquals(where.getPrevious(), subtree.getRoot());
    }
   @Test
    public void addSubSMTree_right() {
        SMTree<T> subtree = new SMTree<T>(new SMTreeNode<T>(new T()));
        SMTreeNode<T> where = new SMTreeNode<T>(new T());
        Kinship k = Kinship.RIGHTSIBLING;
        SMTree<T> instance = new SMTree<T>(where);
        if(!instance.addSubSMTree(subtree, where, k))
            fail("addSubSMTree devolvió false.");
        assertEquals(where.getNext(), subtree.getRoot());
    }
   @Test
    public void addSubSMTree_child() {
        SMTree<T> subtree = new SMTree<T>(new SMTreeNode<T>(new T()));
        SMTreeNode<T> where = new SMTreeNode<T>(new T());
        Kinship k = Kinship.CHILD;
        SMTree<T> instance = new SMTree<T>(where);
        if(!instance.addSubSMTree(subtree, where, k))
            fail("addSubSMTree devolvió false.");
        assertEquals(where.getFirstChild(), subtree.getRoot());
    }

    /**
     * Test of addSMTreeNode method, of class SMTree.
     */
    @Test
    public void addSMTreeNode_left() {
        SMTreeNode<T> n = new SMTreeNode<T>(new T());
        SMTreeNode<T> where = new SMTreeNode<T>(new T());
        Kinship k = Kinship.LEFTSIBLING;
        SMTree<T> instance = new SMTree<T>(where);
        if(!instance.addSMTreeNode(n, where, k))
            fail("addSMTreeNode devolvió false");
        assertEquals(where.getPrevious(), n);
    }
   
    @Test
    public void addSMTreeNode_right() {
        SMTreeNode<T> n = new SMTreeNode<T>(new T());
        SMTreeNode<T> where = new SMTreeNode<T>(new T());
        Kinship k = Kinship.RIGHTSIBLING;
        SMTree<T> instance = new SMTree<T>(where);
        if(!instance.addSMTreeNode(n, where, k))
            fail("addSMTreeNode devolvió false");
        assertEquals(where.getNext(), n);
    } 
    
    @Test
    public void addSMTreeNode_child() {
        SMTreeNode<T> n = new SMTreeNode<T>(new T());
        SMTreeNode<T> where = new SMTreeNode<T>(new T());
        Kinship k = Kinship.CHILD;
        SMTree<T> instance = new SMTree<T>(where);
        if(!instance.addSMTreeNode(n, where, k))
            fail("addSMTreeNode devolvió false");
        assertEquals(where.getFirstChild(), n);
    }

    /**
     * Test of removeSMTreeNode method, of class SMTree.
     */
    @Test
    public void removeSMTreeNode() 
    {
        SMTreeNode<T> n = new SMTreeNode<T>(new T());
        SMTreeNode<T> raiz = new SMTreeNode<T>(new T());
        SMTree<T> instance = new SMTree<T>(raiz);
        for(int i = 0; i < 4; i++)
            instance.addSMTreeNode(new SMTreeNode<T>(new T()), raiz, Kinship.CHILD);
        
        instance.addSMTreeNode(n, raiz, Kinship.CHILD);
        
        for(int i = 0; i < 4; i++)
            instance.addSMTreeNode(new SMTreeNode<T>(new T()), raiz, Kinship.CHILD);
        
        if(!instance.removeFastSMTreeNode(n))
            fail("removeSMTreeNode devolvió false.");
        
        SMTreeNode<T> aux = raiz.getFirstChild();
        while(aux.getNext() != null)
            assertNotSame(n, aux);
    }

    /**
     * Test of substitute method, of class SMTree.
     */
    @Test
    public void substitute() 
    {
        SMTreeNode<T> from = new SMTreeNode<T>(new T());
        SMTreeNode<T> to = new SMTreeNode<T>(new T());
        SMTree<T> n = new SMTree<T>(new SMTreeNode<T>(new T()));
        SMTree instance = new SMTree(from);
        instance.addSMTreeNode(from, to, Kinship.RIGHTSIBLING);
        if(!instance.substitute(from, Enclosure.NOT_ENCLOSED, to, Enclosure.ENCLOSED.NOT_ENCLOSED, n))
            fail("addSMTreeNode devolvió false.");
        fail("¿Parentesis o Corchetes?");
    }

    /**
     * Test of substituteObject method, of class SMTree.
     */
    @Test
    public void substituteObject() {
        System.out.println("substituteObject");
        T from = new T();
        T to = new T();
        T by = new T();
        SMTreeNode<T> frum = new SMTreeNode<T>(from);
        SMTree<T> instance = new SMTree<T>(frum);
        instance.addObject(to, frum, Kinship.RIGHTSIBLING);
        if(!instance.substituteObject(from, Enclosure.NOT_ENCLOSED, to, Enclosure.NOT_ENCLOSED, by))
            fail("el substituteObject devolvió false.");
        fail("¿Parentesis o Corchetes?");
    }

    /**
     * Test of removeObject method, of class SMTree.
     */
    @Test
    public void removeObject() {
        T object = new T();
        SMTreeNode<T> n = new SMTreeNode<T>(object);
        SMTreeNode<T> raiz = new SMTreeNode<T>(new T());
        SMTree<T> instance = new SMTree<T>(raiz);
        for(int i = 0; i < 4; i++)
            instance.addSMTreeNode(new SMTreeNode<T>(new T()), raiz, Kinship.CHILD);
        
        instance.addSMTreeNode(n, raiz, Kinship.CHILD);
        
        for(int i = 0; i < 4; i++)
            instance.addSMTreeNode(new SMTreeNode<T>(new T()), raiz, Kinship.CHILD);
        
        if(!instance.removeObject(object))
            fail("removeSMTreeNode devolvió false.");
        
        SMTreeNode<T> aux = raiz.getFirstChild();
        while(aux.getNext() != null)
            assertNotSame(object, aux.getObject());
    }

    /**
     * Test of addObject method, of class SMTree.
     */
    @Test
    public void addObject() 
    {
        Random random = new Random();
        int r = random.nextInt(50) + 10;
        SMTreeNode<T> where = new SMTreeNode<T>(new T());
        SMTree<T> instance = new SMTree<T>(where);

        for(int i = 1; i < r; i++)
        {
            T o = new T();

            Kinship k;
            if(random.nextBoolean())
                k= Kinship.CHILD;
            else if(random.nextBoolean())
                k = Kinship.LEFTSIBLING;
            else
                k = Kinship.RIGHTSIBLING;
                
            
            assertTrue(instance.addObject(o, where, k));
            assertNotNull(instance.getMapa().get(o));
            
            where = instance.getMapa().get(o);
        }
    }
    
    /**
     * Test of equals method, of class SMTree.
     */
    @Test
    public void equals() {
        Random random = new Random();
        
        int rand = random.nextInt(100) + 50;
        
        SMTreeNode<T> raiz1 = new SMTreeNode<T>(new T());
        SMTreeNode<T> raiz2 = new SMTreeNode<T>(new T());
        SMTree<T> instance2 = new SMTree<T>(raiz1);
        SMTree<T> instance = new SMTree<T>(raiz2);
            
        for(int j = 1; j < rand; j++)
        {
            int max = random.nextInt(100) + 10;

            SMTreeNode<T> aux1;
            SMTreeNode<T> aux2;
            T t;
            for(int i = 0; i < max; i++)
            {
                Kinship k;
                if(random.nextBoolean())
                    k= Kinship.CHILD;
                else if(random.nextBoolean())
                    k = Kinship.LEFTSIBLING;
                else
                    k = Kinship.RIGHTSIBLING;
                
                t = new T();
                aux1 = new SMTreeNode<T>(t);
                aux2 = new SMTreeNode<T>(t);
                instance.addSMTreeNode(aux1, raiz1, k);
                instance2.addSMTreeNode(aux2, raiz2, k);
                if(random.nextBoolean())
                {
                    raiz1 = aux1;
                    raiz2 = aux2;
                }
            }
            
            assertEquals(instance, instance2);
        }


        instance2 = new SMTree<T>(raiz1);
        instance = new SMTree<T>(raiz2);

        int max = random.nextInt(100);

        SMTreeNode<T> aux;
        for(int i = 0; i < max; i++)
        {
            aux = new SMTreeNode<T>(new T());
            instance.addSMTreeNode(aux, raiz1, Kinship.CHILD);
            instance2.addSMTreeNode(aux, raiz2, Kinship.RIGHTSIBLING);
        }

        assertFalse(instance.equals(instance2));

    }

    /**
     * Test of getMapa method, of class SMTree.
     */
    @Test
    public void getMapa() {
        //Caso base:
        SMTreeNode<T> node = new SMTreeNode<T>(new T());
        SMTree<T> instance = new SMTree<T>(node);
        SMIndexStructure<T> mapa = new SMIndexStructure<T>(node);
        assertEquals(mapa, instance.getMapa());
        
        //Caso con un hijo:
        SMTreeNode<T> node2 = new SMTreeNode<T>(new T());
        instance.addSMTreeNode(node2, node, Kinship.CHILD);
        mapa.add(node2);
        assertEquals(mapa, instance.getMapa());
        
        //Caso complejo:
        node2 = new SMTreeNode<T>(new T());
        instance.addSMTreeNode(node2, node, Kinship.LEFTSIBLING);
        mapa.add(node2);        
        node2 = new SMTreeNode<T>(new T());
        instance.addSMTreeNode(node2, node, Kinship.RIGHTSIBLING);
        mapa.add(node2);
        SMTreeNode<T> node3 = new SMTreeNode<T>(new T());
        instance.addSMTreeNode(node3, node2, Kinship.CHILD);
        mapa.add(node3);
        assertEquals(mapa, instance.getMapa());
        
        //Caso con removes:
        instance.removeFastSMTreeNode(node2);
        mapa.remove(node2);
        mapa.remove(node3);
        assertEquals(mapa, instance.getMapa());
    }
}