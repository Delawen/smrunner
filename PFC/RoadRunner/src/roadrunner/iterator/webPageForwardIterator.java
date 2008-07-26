/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.iterator;

import SMTree.SMTree;
import SMTree.SMTreeNode;
import java.util.List;
import java.util.ListIterator;
import roadrunner.node.Item;
import roadrunner.node.Token;
import roadrunner.utils.Sample.webPageIterator;

/**
 *
 * @author delawen
 */
public class webPageForwardIterator implements webPageIterator
{
    private ListIterator<Token> it = null;
    private List<Token> tokens = null;

    public webPageForwardIterator()
    {
        super();
    }
    
    public void init(List<Token> tokens)
    {
        this.it = tokens.listIterator();
        this.tokens = tokens;
    }

    public boolean goTo(Item t)
    {
//        if(!tokens.contains((Token)t))
//            return false;
//       
//        int index = tokens.lastIndexOf(t);
//        
//        this.it = tokens.listIterator(index);
//        
//        return true;
  

        boolean result=false;
        it = tokens.listIterator();
        while(it.hasNext() && !result)
        {
            if(t==it.next())
            {
                it.previous();
                result = true;
            }
        }
        return result;

    }

    public boolean hasNext() {
        return it.hasNext();
    }

    public Token next() {
        return it.next();
    }

    public boolean hasPrevious() {
        return it.hasPrevious();
    }

    public Token previous() {
        return it.previous();
    }

    public boolean isNext(Item o) 
    {
        Item i = it.next();
        if(i.equals(o))
            return true;
        it.previous();
        return false;
    }

    public void setRootIterator(SMTreeNode<Item> root) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTree(SMTree<Item> treeWrapper) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object nextAll() {
        return this.next();
    }

    public Object previous(boolean optional) {
        return previous();
    }

    public Object nextObject(boolean optional)
    {
        return nextObject();
    }

    public Object nextObject()
    {
        return next();
    }

}