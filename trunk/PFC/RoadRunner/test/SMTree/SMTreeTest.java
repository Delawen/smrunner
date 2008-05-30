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
        
        if(!instance.removeSMTreeNode(n))
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
        if(!instance.substitute(from, to, n))
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
        if(!instance.substituteObject(from, to, by))
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
    public void addObject() {
        System.out.println("addObject");
        T o = null;
        SMTreeNode<T> where = null;
        Kinship k = null;
        SMTree instance = new SMTree();
        boolean expResult = false;
        boolean result = instance.addObject(o, where, k);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of iterator method, of class SMTree.
     */
    @Test
    public void iterator() {
        System.out.println("iterator");
        SMTree instance = new SMTree();
        IteratorStrategy expResult = null;
        IteratorStrategy result = instance.iterator();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class SMTree.
     */
    @Test
    public void equals() {
        SMTreeNode<T> raiz1 = new SMTreeNode<T>(new T());
        SMTreeNode<T> raiz2 = new SMTreeNode<T>(new T());
        
        SMTree<T> instance2 = new SMTree<T>(raiz1);
        SMTree<T> instance = new SMTree<T>(raiz2);
        
        Random random = new Random();
        int k = random.nextInt(100);
        
        
        SMTreeNode<T> aux;
        for(int i = 0; i < k; i++)
        {
            aux = new SMTreeNode<T>(new T());
            instance.addSMTreeNode(aux, raiz1, Kinship.CHILD);
            instance2.addSMTreeNode(aux, raiz2, Kinship.CHILD);
        }
        
        assertEquals(instance, instance2);
    }

    /**
     * Test of getMapa method, of class SMTree.
     */
    @Test
    public void getMapa() {
        System.out.println("getMapa");
        SMTree instance = new SMTree();
        SMIndexStructure<T> expResult = null;
        SMIndexStructure<T> result = instance.getMapa();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMapa method, of class SMTree.
     */
    @Test
    public void setMapa() {
        System.out.println("setMapa");
        SMIndexStructure<T> mapa = null;
        SMTree instance = new SMTree();
        instance.setMapa(mapa);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    class T
    {
        private int id;
        
        public T()
        {
            super();
            id = Contador.getId();
        }
        
        public int getId()
        {
            return id;
        }
        
        public boolean equals(T o)
        {
            return (o.getId() == this.id);
        }
    }
    
    static class Contador
    {
        private static int id;
        
        private Contador()
        {
            id = 0;
        }
        
        static protected int getId()
        {
            return id++;
        }
    }

}