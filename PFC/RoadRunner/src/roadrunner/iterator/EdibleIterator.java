/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.iterator;

import SMTree.*;
import roadrunner.node.Item;

/**
 *
 * @author delawen
 */

//El iterador comestible :D
public interface EdibleIterator
{
    public boolean goTo (Item objeto);
    public boolean isNext (Item o);
    public Object next();
    public Object previous();
    public boolean hasNext();
    public void setRootIterator(SMTreeNode<Item> root);
    public void setTree(SMTree<Item> treeWrapper);
}
