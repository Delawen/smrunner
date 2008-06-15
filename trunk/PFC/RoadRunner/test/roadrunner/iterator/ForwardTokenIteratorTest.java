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

    private SMTree<Item> arbol;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() 
    {
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
        nodo3 = new SMTreeNode<Item>(new Variable());
        arbol.addSMTreeNode(nodo3, nodo2, Kinship.CHILD);
        
        nodo2 = new SMTreeNode<Item>(new Text("segundo hijo de la lista"));
        arbol.addSMTreeNode(nodo2, nodo, Kinship.CHILD);
        
        
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
        //it.goTo((Item)actual.getFirstChild().getObject());
        actual = actual.getFirstChild().getNext();
        assertEquals(it.next(), actual.getObject());
        
        
    }

    /**
     * 
     * 
     * Test of hasNext method, of class ForwardTokenIterator.
     */
    @Test
    public void hasNext() {
        System.out.println("hasNext");
        ForwardTokenIterator instance = new ForwardTokenIterator();
        boolean expResult = false;
        boolean result = instance.hasNext();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isNext method, of class ForwardTokenIterator.
     */
    @Test
    public void isNext() {
        System.out.println("isNext");
        Item i = null;
        ForwardTokenIterator instance = new ForwardTokenIterator();
        boolean expResult = false;
        boolean result = instance.isNext(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}