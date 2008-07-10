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
     * Test of next method, of class ForwardTokenIterator.
     */

    @Test
    public void next_forward() {
        System.out.println("next forward");
        SMTreeIterator<Item> it = arbol.iterator(ForwardTokenIterator.class);
        
        //actual irá recorriendo los nodos "manualmente" para comprobar si el iterador
        //devuelve lo que debe.
        SMTreeNode<Item> actual = arbol.getRoot();
        
        //hijos de la raiz
        actual = actual.getFirstChild();
        assertEquals(it.next(), actual.getObject());
        System.out.println("-->" + actual.getObject());
        
        actual = actual.getNext();
        assertEquals(it.next(), actual.getObject());
        System.out.println("-->" + actual.getObject());
        
        //Ahora encuentra la primera lista:
        Item i = (Item) it.next();
        System.out.println("-->" + i);
        assertTrue(i instanceof List);
        
        //Comprueba que hay un solo camino
        LinkedList<Item> resultado = (LinkedList<Item>) it.next();
        assertTrue(resultado.size() == 1);
        actual = actual.getNext();
        assertEquals(resultado.getFirst(), actual.getFirstChild().getObject());
        System.out.println("-->" + actual.getFirstChild().getObject());
        
        
        //Nos vamos por el primer camino:
        it.goTo((Item)actual.getFirstChild().getObject());
        actual = actual.getFirstChild();
        assertEquals(it.next(), actual.getObject());
        System.out.println("repetimos (goto)-->" + actual.getObject());
        
        actual = actual.getNext();
        assertEquals(it.next(), actual.getObject());
        System.out.println("-->" + actual.getObject());
        
        i = (Item) it.next();
        assertTrue(i instanceof Optional);
        System.out.println("-->" + i);
        
        //Comprueba que hay dos caminos y que son los correctos.
        resultado = (LinkedList<Item>) it.next();
        assertTrue(resultado.size() == 2);
        actual = actual.getNext();
        assertEquals(resultado.getFirst(), actual.getParent().getNext().getObject());
        System.out.println("-->" + actual.getParent().getNext().getObject());
        assertEquals(resultado.getLast(), actual.getFirstChild().getObject());
        System.out.println("-->" + actual.getFirstChild().getObject());
        
        
        //Nos vamos por el primer camino:
        it.goTo((Item)actual.getFirstChild().getObject());
        actual = actual.getFirstChild();
        assertEquals(it.next(), actual.getObject());
        System.out.println("repetimos (goto)-->" + actual.getObject());
        
        //Seguimos por el opcional:
        actual = actual.getNext();
        i = (Item) it.next();
        assertTrue(i instanceof List);
        System.out.println("-->" + i);
        
        resultado = (LinkedList<Item>) it.next();
        assertTrue(resultado.size() == 1);
        assertEquals(resultado.getFirst(), actual.getFirstChild().getObject());
        System.out.println("-->" + actual.getFirstChild().getObject());
          
    }

    
    @Test
    public void next_backwards() {
    
        System.out.println("next backwards");
        SMTreeIterator<Item> it = arbol.iterator(BackwardTokenIterator.class);
        
        //actual irá recorriendo los nodos "manualmente" para comprobar si el iterador
        //devuelve lo que debe.
        SMTreeNode<Item> actual = arbol.getRoot();
        
        //hijos de la raiz
        actual = actual.getLastChild();
        assertEquals(it.next(), actual.getObject());
        System.out.println("-->" + actual.getObject());
        
        actual = actual.getPrevious();
        Item i = (Item) it.next();
        assertEquals(i, actual.getObject());
        System.out.println("-->" + actual.getObject());
        
        //Ahora encuentra la primera lista:
        assertTrue(i instanceof List);
        
        //Comprueba que hay un unico camino correcto
        LinkedList<Item> resultado = (LinkedList<Item>) it.next();
        assertTrue(resultado.size() == 1);
        assertEquals(resultado.getFirst(), actual.getLastChild().getObject());
        System.out.println("-->" + actual.getLastChild().getObject());
        
        //Nos vamos por el primer camino:
        it.goTo((Item)actual.getLastChild().getObject());
        actual = actual.getLastChild();
        assertEquals(it.next(), actual.getObject());
        System.out.println("repetimos (goto)-->" + actual.getObject());
        
        //Comprueba que el opcional tiene dos caminos:
        resultado = (LinkedList<Item>) it.next();
        assertTrue(resultado.size() == 2);
        assertEquals(resultado.getLast(), actual.getLastChild().getObject());
        System.out.println("-->" + actual.getLastChild().getObject());
        assertEquals(resultado.getFirst(), actual.getPrevious().getObject());
        System.out.println("-->" + actual.getPrevious().getObject());
        
        //Nos vamos por el primer camino:
        it.goTo((Item)actual.getLastChild().getObject());
        actual = actual.getLastChild();
        i = (Item) it.next();
        assertTrue(i instanceof List);
        assertEquals(i, actual.getObject());
        System.out.println("repetimos (goto)-->" + actual.getObject());
        
        //Comprobamos que tambien la segunda lista tiene un único camino:
        resultado = (LinkedList<Item>) it.next();
        assertTrue(resultado.size() == 1);
        assertEquals(resultado.getFirst(), actual.getLastChild().getObject());
        System.out.println("-->" + actual.getLastChild().getObject());
        
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
        System.out.println("isNext Forward");
        SMTreeNode<Item> actual = arbol.getRoot().getFirstChild().getNext();
        SMTreeIterator<Item> it = arbol.iterator(ForwardTokenIterator.class);
        actual = actual.getNext();
        
        assertFalse(it.isNext(null));
        it.goTo(actual.getObject());
        assertFalse(it.isNext((Item)arbol.getRoot().getObject()));
        it.goTo(actual.getObject());
        assertTrue(it.isNext((Item)actual.getFirstChild().getObject()));
        it.goTo(actual.getObject());
        assertFalse(it.isNext((Item)actual.getNext().getObject())); //porque aun no hemos entrado
        it.next();
        it.next();
        assertTrue(it.isNext((Item)actual.getNext().getObject())); //ya si hemos entrado
    }

}