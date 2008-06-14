package roadrunner.iterator;

import SMTree.iterator.*;
import SMTree.*;
import roadrunner.node.Item;


public class ForwardTokenIterator extends ForwardItemIterator implements EdibleIterator
{
    private SMTreeNode<Item> actual;
     
    public ForwardTokenIterator() 
    {
        super();
    }

    public boolean goTo(Item objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isNext(Item o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasNext() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

