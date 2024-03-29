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
    public void goTo (Item objeto);
    public boolean isNext (Item o);
    public Object nextAll();
    public Item next();
    public Object previous();
    public Object previous(boolean optional);
    public Object nextObject(boolean optional);
    public Object nextObject();
    public boolean hasNext();
    public void setRootIterator(SMTreeNode<Item> root);
    public void setTree(SMTree<Item> treeWrapper);
}
