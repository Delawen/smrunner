/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SMTree;
import SMTree.utils.Enclosure;
import SMTree.utils.Kinship;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author delawen
 */
public class SMTreeTest {

    public SMTreeTest() {
        System.out.println("SMTreeTest");
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
        
        if(!instance.removeSMTreeNode(n))
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
            
            assertEquals(from.getNext(), n.getRoot());
            assertEquals(to.getPrevious(), n.getRoot());
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

            if(!instance.substituteObject(from, Enclosure.NOT_ENCLOSED, to, Enclosure.ENCLOSED.NOT_ENCLOSED, what))
                fail("addSMTreeNode devolvió false.");
            
            assertEquals(instance.getMapa().get(from).getNext().getObject(), what);
            assertEquals(instance.getMapa().get(to).getPrevious().getObject(), what);
            assertEquals(instance.getMapa().get(what).getParent(), raiz);
            
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
        int rand = random.nextInt(100) + 20;
        for(int j = 0; j < rand; j++)
        {
            SMTreeNode<T> where = new SMTreeNode<T>(new T());
            SMTreeNode<T> raiz = new SMTreeNode<T>(new T());
            SMTree<T> instance = new SMTree<T>(raiz);
            instance.addSMTreeNode(where, raiz, Kinship.CHILD);

            int r = random.nextInt(100)+20;
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

                if(random.nextBoolean())
                    where = instance.getMapa().get(o);
            }
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
        SMTree<T> instance2 = new SMTree<T>(raiz2);
        SMTree<T> instance = new SMTree<T>(raiz1);
            
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
                if(instance2.getRoot()==raiz2 || instance.getRoot()==raiz1 || random.nextBoolean())
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
                if(random.nextBoolean() || instance2.getRoot()==raiz2 || instance.getRoot()==raiz1)
                {
                    raiz1 = aux1;
                    raiz2 = aux2;
                }
            }
            assertEquals(instance, instance2);
        }


        instance2 = new SMTree<T>(raiz2);
        instance = new SMTree<T>(raiz1);

        int max = random.nextInt(100);

        SMTreeNode<T> aux;
        for(int i = 0; i < max; i++)
        {
            aux = new SMTreeNode<T>(new T());
            instance.addSMTreeNode(aux, raiz1, Kinship.CHILD);
            if(instance2.getRoot()!=raiz2)
                instance2.addSMTreeNode(aux, raiz2, Kinship.RIGHTSIBLING);
            else 
                instance2.addSMTreeNode(aux, raiz2, Kinship.CHILD);
        }

        assertFalse(instance.equals(instance2));

    }
    
    @Test
    public void testComplejo()
    {
        System.out.println("testComplejo()");
        System.out.println(">>Creación de un árbol simple");

        Random r = new Random();
        int vueltas = r.nextInt(100) + 20;

        SMTree<T> arbol = new SMTree<T>(new SMTreeNode<T>(new T()));

        SMTreeNode<T> nodo1 = new SMTreeNode<T>(new T());
        SMTreeNode<T> nodo2 = new SMTreeNode<T>(new T());
        SMTreeNode<T> nodo3 = new SMTreeNode<T>(new T());
        SMTreeNode<T> nodo4 = new SMTreeNode<T>(new T());
        SMTreeNode<T> nodo5 = new SMTreeNode<T>(new T());

        arbol.addSMTreeNode(nodo1, arbol.getRoot(), Kinship.CHILD);
        arbol.addSMTreeNode(nodo2, arbol.getRoot(), Kinship.CHILD);
        arbol.addSMTreeNode(nodo3, arbol.getRoot(), Kinship.CHILD);
        arbol.addSMTreeNode(nodo4, arbol.getRoot(), Kinship.CHILD);
        arbol.addSMTreeNode(nodo5, arbol.getRoot(), Kinship.CHILD);

        SMTreeNode<T> aux = nodo1;

        while (aux != null) {
            if (aux.getParent() != arbol.getRoot()) {
                fail("El padre del hijo de la raíz debería ser la raíz.");
            }
            aux = aux.getNext();
        }
        aux = nodo1;

        System.out.println(">>Generando un árbol complejo");
        for (int i = 0; i < vueltas; i++) {
            SMTreeNode<T> aux2 = new SMTreeNode<T>(new T());
            Kinship k;
            if (r.nextBoolean()) {
                k = Kinship.CHILD;
            } else if (r.nextBoolean()) {
                k = Kinship.LEFTSIBLING;
            } else {
                k = Kinship.RIGHTSIBLING;
            }
            arbol.addSMTreeNode(aux2, aux, k);

            //Actualizamos aleatoriamente los nodoX
            switch (r.nextInt(8)) {
                case 0:
                    nodo1 = aux2;
                    break;
                case 2:
                    nodo3 = aux2;
                    break;
                case 4:
                    nodo5 = aux2;
                    break;
                default:
                    break;
            }

            //Actualizamos aleatoriamente el aux:
            switch (r.nextInt(7)) {
                case 0:
                    aux = nodo1;
                    break;
                case 1:
                    aux = nodo2;
                    break;
                case 2:
                    aux = nodo3;
                    break;
                case 3:
                    aux = nodo4;
                    break;
                case 4:
                    aux = nodo5;
                    break;
                default:
                    break;
            }
        }
        SMTree<T> clon = null;
        try{
            clon = arbol.clone();
            if(!clon.equals(arbol))
                fail("O el clon o el equals no funcionan");
        } catch (CloneNotSupportedException ex) 
        {
            Logger.getLogger(SMTreeTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("No permitía la clonación :/");
        }

     System.out.println(">>Sustituciones:");
     System.out.println(">>>>Sustitucion de Arbol");
     
     SMTree<T> sustitucion = new SMTree<T>(new SMTreeNode<T>(new T()));
     sustitucion.addObject(new T(), sustitucion.getRoot(), Kinship.CHILD);
     
     arbol.substitute(nodo2, Enclosure.NOT_ENCLOSED, nodo4, Enclosure.NOT_ENCLOSED, sustitucion);
     assertNotNull(arbol.getMapa().get(nodo2.getObject()));
     assertNotNull(arbol.getMapa().get(nodo4.getObject()));
     assertNotNull(arbol.getMapa().get(sustitucion.getRoot().getObject()));
     assertNotNull(arbol.getMapa().get(sustitucion.getRoot().getFirstChild().getObject()));
     
     System.out.println(">>>>Sustitucion de Objeto");
     
     T t = new T();
     
     arbol.substituteObject(nodo2.getObject(), Enclosure.ENCLOSED, nodo4.getObject(), Enclosure.ENCLOSED, t);
     assertNull(arbol.getMapa().get(nodo2.getObject()));
     assertNull(arbol.getMapa().get(nodo4.getObject()));
     assertNotNull(arbol.getMapa().get(t));
     assertTrue(arbol.getMapa().get(t).getParent().equals(arbol.getRoot()));
     
     System.out.println(">>Eliminaciones");
     System.out.println(">>>>Eliminacion de Arboles");
     arbol.removeSMTreeNode(arbol.getMapa().get(t));
     assertNull(arbol.getMapa().get(t));
     nodo2 = clon.getRoot().getFirstChild().getNext();
     clon.removeSMTreeNode(nodo2);
     assertNull(arbol.getMapa().get(nodo2.getObject()));
     
     System.out.println(">>>>Eliminación de Objetos");
     nodo4 = clon.getRoot().getLastChild();
     clon.removeObject(nodo4.getObject());
     assertNull(arbol.getMapa().get(nodo4.getObject()));
    }
}