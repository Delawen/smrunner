/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.iterator;

import SMTree.*;
import SMTree.utils.*;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import roadrunner.node.*;

/**
 *
 * @author delawen
 */
public class TokenIteratorTest {

    public TokenIteratorTest() {
    }

    private static SMTree<Item> arbol;
    
    @Before
    public void setUp()  
    {
              
        /*    
         * 
         * 
         * 
         *         /- primer hijo de la raiz     
         *        /-- segundo hijo de la raiz    /- primer hijo de la lista
         * Tuple-|----------------------- Lista-|-- segundo hijo de la lista    /- primer hijo del opcional
         *        \-- cuarto hijo de la raiz     \-- Opcional------------------|
         *                                                                     |
         *                                                                   Lista
         *                                                                    /  \
         *                                                             Variable  segundo elemento de la segunda lista
         * */
        
        arbol = new SMTree<Item>();
        //Raiz: tuple
        SMTreeNode<Item> nodo = new SMTreeNode<Item>(new Tuple());
        arbol.setRoot(nodo);
        
        //Primer nivel: cuatro hijos. Tercer hijo es una lista
        nodo = new SMTreeNode<Item>(new Text("primer hijo de la raiz"));
        arbol.addSMTreeNode(nodo, arbol.getRoot(), Kinship.CHILD);
        
        nodo = new SMTreeNode<Item>(new Text("segundo hijo de la raiz"));
        arbol.addSMTreeNode(nodo, arbol.getRoot(), Kinship.CHILD);
        
        nodo = new SMTreeNode<Item>(new List());
        arbol.addSMTreeNode(nodo, arbol.getRoot(), Kinship.CHILD);
        
        SMTreeNode<Item> nodo2 = new SMTreeNode<Item>(new Text("cuarto hijo de la raiz"));
        arbol.addSMTreeNode(nodo2, arbol.getRoot(), Kinship.CHILD);
        
        //Segundo nivel: hijos de la lista.
        nodo2 = new SMTreeNode<Item>(new Text("primer hijo de la lista"));
        arbol.addSMTreeNode(nodo2, nodo, Kinship.CHILD);
        
        nodo2 = new SMTreeNode<Item>(new Text("segundo hijo de la lista"));
        arbol.addSMTreeNode(nodo2, nodo, Kinship.CHILD);
        
        nodo2 = new SMTreeNode<Item>(new Optional());
        arbol.addSMTreeNode(nodo2, nodo, Kinship.CHILD);
        
        //Tercer nivel: hijos del opcional, hijo de la lista:
        SMTreeNode<Item> nodo3 = new SMTreeNode<Item>(new Text("primer hijo del opcional"));
        arbol.addSMTreeNode(nodo3, nodo2, Kinship.CHILD);
        
        nodo3 = new SMTreeNode<Item>(new List());
        arbol.addSMTreeNode(nodo3, nodo2, Kinship.CHILD);
        
        
        //Cuarto nivel: hijos de la lista, hija del opcional, hijo de la lista
        nodo2 = new SMTreeNode<Item>(new Variable());
        arbol.addSMTreeNode(nodo2, nodo3, Kinship.CHILD);
        
        nodo2 = new SMTreeNode<Item>(new Text("segundo hijo de la segunda lista"));
        arbol.addSMTreeNode(nodo2, nodo3, Kinship.CHILD);
        
        
    } 

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeClass
    public static void setUpClass() throws Exception 
    {
  
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of nextObject method, of class ForwardTokenIterator.
     */

    @Test
    public void next_forward() {
        System.out.println("next forward");
        ForwardTokenIterator it = (ForwardTokenIterator) arbol.iterator(ForwardTokenIterator.class);
        
        //actual irá recorriendo los nodos "manualmente" para comprobar si el iterador
        //devuelve lo que debe.
        SMTreeNode<Item> actual = arbol.getRoot();
        
        //hijos de la raiz
        actual = actual.getFirstChild();
        assertEquals(it.nextObject(), actual.getObject());
        System.out.println("-->" + actual.getObject());
        
        actual = actual.getNext();
        assertEquals(it.nextObject(), actual.getObject());
        System.out.println("-->" + actual.getObject());
        
        //Ahora encuentra la primera lista:        
        //Comprueba que hay un solo camino
        Item item = (Item) it.nextObject();
        actual = actual.getNext();
        assertEquals(item, actual.getFirstChild().getObject());
        System.out.println("-->" + actual.getFirstChild().getObject());
        
        
        //Nos vamos por el primer camino:
        it.goTo((Item)actual.getFirstChild().getObject());
        actual = actual.getFirstChild();
        assertEquals(it.nextObject(), actual.getObject());
        System.out.println("repetimos (goto)-->" + actual.getObject());
        
        actual = actual.getNext();
        assertEquals(it.nextObject(), actual.getObject());
        System.out.println("-->" + actual.getObject());
        
        //Comprueba que hay dos caminos y que son los correctos.
        LinkedList<Item> resultado = (LinkedList<Item>) it.nextAll();
        assertTrue(resultado.size() == 2);
        actual = actual.getNext();
        assertEquals(resultado.getFirst(), actual.getParent().getNext().getObject());
        System.out.println("-->" + actual.getFirstChild().getObject());
        assertEquals(resultado.getLast(), actual.getFirstChild().getObject());
        System.out.println("-->" + actual.getParent().getNext().getObject());
        
        
        //Nos vamos por el primer camino:
        it.goTo((Item)actual.getFirstChild().getObject());
        actual = actual.getFirstChild();
        assertEquals(it.nextObject(), actual.getObject());
        System.out.println("repetimos (goto)-->" + actual.getObject());
        
        //Seguimos por el opcional:
        actual = actual.getNext();
        
        item =  (Item) it.nextObject();
        assertEquals(item, actual.getFirstChild().getObject());
        System.out.println("-->" + actual.getFirstChild().getObject());
          
    }

    
    @Test
    public void next_backwards() {
    
        System.out.println("next backwards");
        BackwardTokenIterator it = (BackwardTokenIterator) arbol.iterator(BackwardTokenIterator.class);
        
        //actual irá recorriendo los nodos "manualmente" para comprobar si el iterador
        //devuelve lo que debe.
        SMTreeNode<Item> actual = arbol.getRoot();
        
        //hijos de la raiz
        actual = actual.getLastChild();
        assertEquals(it.nextObject(), actual.getObject());
        System.out.println("-->" + actual.getObject());
        
        actual = actual.getPrevious();
        LinkedList<Item> resultado = (LinkedList) it.nextAll();
        assertEquals(resultado.size(), 2);
        assertEquals(resultado.getFirst(), actual.getLastChild().getPrevious().getObject());
        System.out.println("-->" + resultado.getFirst());
        assertEquals(resultado.getLast(), actual.getLastChild().getLastChild().getLastChild().getObject());
        
        //Comprueba que hay un unico camino correcto
        Item i = (Item) it.nextObject();
        assertEquals(i, actual.getFirstChild().getObject());
        System.out.println("-->" + actual.getFirstChild().getObject());
        
        //Nos vamos por el primer camino:
        it.goTo((Item)actual.getLastChild().getObject());
        actual = actual.getLastChild();
        assertEquals(it.nextObject(), actual.getObject());
        System.out.println("repetimos (goto)-->" + actual.getObject());
        
        //Comprueba que el opcional tiene dos caminos:
        resultado = (LinkedList<Item>) it.nextAll();
        assertTrue(resultado.size() == 2);
               
    }

    /**
     * 
     * 
     * Test of hasNext method, of class ForwardTokenIterator.
     */
    @Test
    public void hasNext() {
        System.out.println("hasNext");
        
        ForwardTokenIterator it = (ForwardTokenIterator) arbol.iterator(ForwardTokenIterator.class);
        
        it.nextObject();
        assertTrue(it.hasNext());
        
        it.goTo((Item)arbol.getRoot().getLastChild().getObject());
        it.nextObject();
        assertFalse(it.hasNext());
        
    }

    /**
     * Test of isNext method, of class ForwardTokenIterator.
     */
    @Test
    public void isNext() {
        System.out.println("isNext Forward");
        SMTreeNode<Item> actual = arbol.getRoot().getFirstChild().getNext();
        ForwardTokenIterator it = (ForwardTokenIterator) arbol.iterator(ForwardTokenIterator.class);
        actual = actual.getNext();
        
        assertFalse(it.isNext(null));
        it.goTo(actual.getObject());
        assertFalse(it.isNext((Item)arbol.getRoot().getObject()));
        it.goTo(actual.getObject());
        assertTrue(it.isNext((Item)actual.getFirstChild().getObject()));
        it.goTo(actual.getObject());
        assertFalse(it.isNext((Item)actual.getNext().getObject())); //porque aun no hemos entrado
        it.nextObject();
        it.nextObject();
        assertFalse(it.hasNext());
    }

}