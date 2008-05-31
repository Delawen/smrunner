/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SMTree;
import java.util.Random;
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
        System.out.println("setRootObject()");
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
        System.out.println("getRootObject()");
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
        System.out.println("setRoot()");
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
                
        System.out.println("addSubSMTree_left()");
        SMTreeNode<T> raiz = new SMTreeNode<T>(new T());
        SMTree<T> instance = new SMTree<T>(raiz);
        
        SMTreeNode<T> padre = new SMTreeNode<T>(new T());
        SMTreeNode<T> where = new SMTreeNode<T>(new T());
        instance.addSubSMTree(new SMTree<T>(where), padre, Kinship.CHILD);
        
        SMTree<T> subtree = new SMTree<T>(new SMTreeNode<T>(new T()));
 
        Kinship k = Kinship.LEFTSIBLING;
        if(!instance.addSubSMTree(subtree, where, k))
            fail("addSubSMTree devolvió false.");
        assertEquals(where.getPrevious(), subtree.getRoot());
    }
   @Test
    public void addSubSMTree_right() {
        
        System.out.println("addSubSMTree_right()");
        SMTreeNode<T> raiz = new SMTreeNode<T>(new T());
        SMTree<T> instance = new SMTree<T>(raiz);
        
        SMTreeNode<T> padre = new SMTreeNode<T>(new T());
        SMTreeNode<T> where = new SMTreeNode<T>(new T());
        instance.addSubSMTree(new SMTree<T>(where), padre, Kinship.CHILD);
        
        SMTree<T> subtree = new SMTree<T>(new SMTreeNode<T>(new T()));
 
        Kinship k = Kinship.RIGHTSIBLING;
        if(!instance.addSubSMTree(subtree, where, k))
            fail("addSubSMTree devolvió false.");
        assertEquals(where.getNext(), subtree.getRoot());
    }
   
   @Test
    public void addSubSMTree_child() {
       
        System.out.println("addSubSMTree_child()");
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
        
        System.out.println("addSMTreeNode_left()");
        SMTreeNode<T> padre = new SMTreeNode<T>(new T());
        SMTreeNode<T> where = new SMTreeNode<T>(new T());
        
        SMTree<T> instance = new SMTree<T>(padre);
        instance.addSMTreeNode(where, padre, Kinship.CHILD);
        
        Kinship k = Kinship.LEFTSIBLING;
        SMTreeNode<T> n = new SMTreeNode<T>(new T());
        if(!instance.addSMTreeNode(n, where, k))
            fail("addSMTreeNode devolvió false");
        assertEquals(where.getPrevious(), n);
    }
   
    @Test
    public void addSMTreeNode_right() {
        System.out.println("addSMTreeNode_right()");
        SMTreeNode<T> n = new SMTreeNode<T>(new T());
        
        SMTreeNode<T> padre = new SMTreeNode<T>(new T());
        SMTreeNode<T> where = new SMTreeNode<T>(new T());
        
        SMTree<T> instance = new SMTree<T>(padre);
        instance.addSMTreeNode(where, padre, Kinship.CHILD);
        
        Kinship k = Kinship.RIGHTSIBLING;
        if(!instance.addSMTreeNode(n, where, k))
            fail("addSMTreeNode devolvió false");
        assertEquals(where.getNext(), n);
    } 
    
    @Test
    public void addSMTreeNode_child() {
        
        System.out.println("addSMTreeNode_child()");
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
        
        System.out.println("removeSMTreeNode()");
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
        
        while(aux != null)
        {
            assertNotSame(n, aux);
            aux = aux.getNext();
        }
    }

    /**
     * Test of substitute method, of class SMTree.
     */
    @Test
    public void substitute() 
    {
        System.out.println("substitute()");
        Random random = new Random();
        
        int num_pruebas = random.nextInt(100) + 20;
        
        for(int i = 0; i < num_pruebas; i++)
        {
            SMTreeNode<T> raiz = new SMTreeNode<T>(new T());
            SMTreeNode<T> from;
            SMTreeNode<T> to;
            
            SMTree<T> n = new SMTree<T>(new SMTreeNode<T>(new T()));
            
            SMTree instance = new SMTree(raiz);

            SMTreeNode<T> aux = new SMTreeNode<T>(new T());
            from = aux;
            
            int max = random.nextInt(50) + 10;
            for(int j = 0; j < max; j++)
            {
                instance.addSMTreeNode(aux, raiz, Kinship.CHILD);
                if(random.nextBoolean())
                    from = aux;
                aux = new SMTreeNode<T>(new T());
            }
            to = aux;
            max = random.nextInt(50) + 10;
            for(int j = 0; j < max; j++)
            {
                instance.addSMTreeNode(aux, raiz, Kinship.CHILD);
                if(random.nextBoolean())
                    to = aux;
                aux = new SMTreeNode<T>(new T());
            }

            if(!instance.substitute(from, Enclosure.NOT_ENCLOSED, to, Enclosure.ENCLOSED.NOT_ENCLOSED, n))
                fail("addSMTreeNode devolvió false.");
            
            assertEquals(from.getNext(), n);
            assertEquals(to.getPrevious(), n);
            assertEquals(n.getRoot().getParent(), raiz);
            
        }
        
    }

    /**
     * Test of substituteObject method, of class SMTree.
     */
    @Test
    public void substituteObject() {
        
        System.out.println("substituteObject()");
        Random random = new Random();
        
        int num_pruebas = random.nextInt(100) + 20;
        
        for(int i = 0; i < num_pruebas; i++)
        {
            SMTreeNode<T> raiz = new SMTreeNode<T>(new T());
            T from;
            T to;
            T what = new T();
            
            SMTree<T> n = new SMTree<T>(new SMTreeNode<T>(what));
            
            SMTree instance = new SMTree(raiz);

            SMTreeNode<T> aux = new SMTreeNode<T>(new T());
            from = aux.getObject();
            
            int max = random.nextInt(50) + 10;
            for(int j = 0; j < max; j++)
            {
                instance.addSMTreeNode(aux, raiz, Kinship.CHILD);
                if(random.nextBoolean())
                    from = aux.getObject();
                aux = new SMTreeNode<T>(new T());
            }
            to = aux.getObject();
            max = random.nextInt(50) + 10;
            for(int j = 0; j < max; j++)
            {
                instance.addSMTreeNode(aux, raiz, Kinship.CHILD);
                if(random.nextBoolean())
                    to = aux.getObject();
                aux = new SMTreeNode<T>(new T());
            }

            if(!instance.substituteObject(from, Enclosure.NOT_ENCLOSED, to, Enclosure.ENCLOSED.NOT_ENCLOSED, n))
                fail("addSMTreeNode devolvió false.");
            
            assertEquals(instance.getMapa().get(from).getNext(), n);
            assertEquals(instance.getMapa().get(to).getPrevious(), n);
            assertEquals(n.getRoot().getParent(), raiz);
            
        }
    }

    /**
     * Test of removeObject method, of class SMTree.
     */
    @Test
    public void removeObject() {
        
        System.out.println("removeObject()");
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
        
        while(aux != null)
        {
            assertNotSame(object, aux.getObject());
            aux = aux.getNext();
        }
    }

    /**
     * Test of addObject method, of class SMTree.
     */
    @Test
    public void addObject() 
    {
        
        System.out.println("addObject()");
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
        
        System.out.println("equals()");
        Random random = new Random();
        
        int rand = random.nextInt(100) + 50;
        
        SMTreeNode<T> raiz1 = new SMTreeNode<T>(new T());
        SMTreeNode<T> raiz2 = new SMTreeNode<T>(new T());
        SMTree<T> instance2 = new SMTree<T>(raiz1);
        SMTree<T> instance = new SMTree<T>(raiz2);
            
        for(int j = 1; j < rand; j++)
        {
            System.out.println(j);
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
}