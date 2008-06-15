/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.iterator;

import SMTree.*;
import SMTree.iterator.SMTreeIterator;
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
public class ForwardTokenIteratorTest {

    public ForwardTokenIteratorTest() {
    }

    private static SMTree<Item> arbol;
    
    @BeforeClass
    public static void setUpClass() throws Exception 
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

    @Before
    public void setUp() 
    {
  
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of next method, of class ForwardTokenIterator.
     */
    @Test
    public void next() {
        System.out.println("next");
        SMTreeIterator<Item> it = arbol.iterator(ForwardTokenIterator.class);
        
        //actual irá recorriendo los nodos "manualmente" para comprobar si el iterador
        //devuelve lo que debe.
        SMTreeNode<Item> actual = arbol.getRoot();
        
        //hijos de la raiz
        actual = actual.getFirstChild();
        assertEquals(it.next(), actual.getObject());
        
        actual = actual.getNext();
        assertEquals(it.next(), actual.getObject());
        
        //Ahora encuentra la primera lista:
        Item i = (Item) it.next();
        assert(i instanceof List);
        
        //Comprueba que hay dos caminos y que son correctos
        LinkedList<Item> resultado = (LinkedList<Item>) it.next();
        assert(resultado.size() == 2);
        actual = actual.getNext();
        assertEquals(resultado.getFirst(), actual.getFirstChild().getObject());
        assertEquals(resultado.getLast(), actual.getNext().getObject());
        
        //Nos vamos por el primer camino:
        it.goTo((Item)actual.getFirstChild().getObject());
        actual = actual.getFirstChild();
        assertEquals(it.next(), actual.getObject());
        
        actual = actual.getNext();
        assertEquals(it.next(), actual.getObject());
        
        i = (Item) it.next();
        assert(i instanceof Optional);
        
        //Comprueba que hay dos caminos y que son correctos
        resultado = (LinkedList<Item>) it.next();
        assert(resultado.size() == 2);
        actual = actual.getNext();
        assertEquals(resultado.getFirst(), actual.getFirstChild().getObject());
        assertEquals(resultado.getLast(), actual.getParent().getNext().getObject());
        
        //Nos vamos por el primer camino:
        it.goTo((Item)actual.getFirstChild().getObject());
        actual = actual.getFirstChild();
        assertEquals(it.next(), actual.getObject());
        
        //Seguimos por el opcional:
        actual = actual.getNext();
        i = (Item) it.next();
        assert(i instanceof List);
        
        resultado = (LinkedList<Item>) it.next();
        assert(resultado.size() == 2);
        assertEquals(resultado.getFirst(), actual.getFirstChild().getObject());
        actual = arbol.getRoot();
        assertEquals(resultado.getLast(), actual.getLastChild().getObject());
        
    }

    /**
     * 
     * 
     * Test of hasNext method, of class ForwardTokenIterator.
     */
    @Test
    public void hasNext() {
        System.out.println("hasNext");
        
        SMTreeIterator<Item> it = arbol.iterator(ForwardTokenIterator.class);
        
        it.next();
        assertTrue(it.hasNext());
        
        it.goTo((Item)arbol.getRoot().getLastChild().getObject());
        it.next();
        assertFalse(it.hasNext());
        
    }

    /**
     * Test of isNext method, of class ForwardTokenIterator.
     */
    @Test
    public void isNext() {
        System.out.println("isNext");
        SMTreeNode<Item> actual = arbol.getRoot().getFirstChild().getNext();
        SMTreeIterator<Item> it = arbol.iterator(ForwardTokenIterator.class);
        actual = actual.getNext();
        
        assertFalse(it.isNext(null));
        it.goTo(actual.getObject());
        assertFalse(it.isNext((Item)arbol.getRoot().getObject()));
        it.goTo(actual.getObject());
        assertTrue(it.isNext((Item)actual.getFirstChild().getObject()));
        it.goTo(actual.getObject());
        assertTrue(it.isNext((Item)actual.getNext().getObject()));
    }

}