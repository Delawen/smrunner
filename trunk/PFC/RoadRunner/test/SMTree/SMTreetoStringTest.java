/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SMTree;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author delawen
 */
public class SMTreetoStringTest 
{
    private SMTree<String> arbol;

    public SMTreetoStringTest() 
    {
    }
    
    @Test
    public void prueba()
    {
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
        System.out.println("SMTree toString() Test");
        System.out.println(arbol.toString());
    }

}