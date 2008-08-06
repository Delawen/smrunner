/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.utils;

import SMTree.SMTree;
import SMTree.SMTreeNode;
import SMTree.utils.Kinship;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import roadrunner.node.Item;
import roadrunner.node.List;
import roadrunner.node.Optional;
import roadrunner.node.Text;
import roadrunner.node.Tuple;
import static org.junit.Assert.*;

/**
 *
 * @author delawen
 */
public class Wrapper2Test {

    public Wrapper2Test() {
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


  //  @Test
    public void simularSample()
    {
        System.out.println("simularSample");

        SMTree<Item> tree = new SMTree<Item>();

        Item raiz =  new Tuple();
        tree.setRootObject(raiz);

        SMTreeNode<Item> lista = new SMTreeNode<Item>(new roadrunner.node.List());
        tree.addSMTreeNode(lista, tree.getRoot(), Kinship.CHILD);

        tree.addObject(new Text("Repeticion"), lista, Kinship.CHILD);
        tree.addObject(new Text(" y más repetición."), lista, Kinship.CHILD);


        SMTreeNode<Item> optional = new SMTreeNode<Item>(new roadrunner.node.Optional());
        tree.addSMTreeNode(optional, tree.getRoot(), Kinship.CHILD);

        tree.addObject(new Text("Esto solo lo leere"), optional, Kinship.CHILD);

        optional = new SMTreeNode<Item>(new Optional());
        tree.addSMTreeNode(optional, tree.getRoot(), Kinship.CHILD);
        tree.addObject(new Text(" a veces."), optional, Kinship.CHILD);

        tree.addObject(new Text(" ¿Funcionara?"), tree.getRoot(), Kinship.CHILD);
        tree.addObject(new Text("Esto no debe leerse, FIN"), tree.getRoot(), Kinship.CHILD);

        Wrapper w = new Wrapper(tree);

        System.out.println(w);

        Sample e1 = w.simularSample(w, w.getTree().getRootObject(), (Item)w.getTree().getNode(raiz).getLastChild().getObject(), true);

        System.out.println(e1);

        Sample e2 = w.simularSample(w, w.getTree().getRootObject(), (Item)w.getTree().getNode(raiz).getLastChild().getObject(), false);

        System.out.println(e2);

    }

    @Test
    public void complejo()
    {
        System.out.println("más complejo");
                SMTree<Item> tree = new SMTree<Item>();

        Item raiz =  new Tuple();
        tree.setRootObject(raiz);

        SMTreeNode<Item> lista = new SMTreeNode<Item>(new roadrunner.node.List());

        tree.addObject(new Text(" Uno"), tree.getRoot(), Kinship.CHILD);

        tree.addSMTreeNode(lista, tree.getRoot(), Kinship.CHILD);
        tree.addObject(new Text(" Dos "), lista, Kinship.CHILD);

        SMTreeNode<Item> lista2 = new SMTreeNode<Item>(new List());
        tree.addSMTreeNode(lista2, lista, Kinship.CHILD);
        tree.addObject(new Text(" Cuatro "), lista2, Kinship.CHILD);

        SMTreeNode<Item> optional = new SMTreeNode<Item>(new roadrunner.node.Optional());
        tree.addSMTreeNode(optional, lista2, Kinship.CHILD);
        tree.addObject(new Text(" Cuatro pero opcional "), optional, Kinship.CHILD);

        optional = new SMTreeNode<Item>(new roadrunner.node.Optional());
        tree.addSMTreeNode(optional, tree.getRoot(), Kinship.CHILD);
        tree.addObject(new Text(" Uno opcional "), optional, Kinship.CHILD);


        tree.addObject(new Text("Esto no debe leerse"), tree.getRoot(), Kinship.CHILD);


        //Una vez tenemos el árbol, lo probamos
        Wrapper w = new Wrapper(tree);

        System.out.println(w);

        Sample e1 = w.simularSample(w, w.getTree().getRootObject(), (Item)w.getTree().getNode(raiz).getLastChild().getObject(), true);

        System.out.println(e1);

        Sample e2 = w.simularSample(w, w.getTree().getRootObject(), (Item)w.getTree().getNode(raiz).getLastChild().getObject(), false);

        System.out.println(e2);

    }


}