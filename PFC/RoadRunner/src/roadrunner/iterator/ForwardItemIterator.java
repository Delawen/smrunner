package roadrunner.iterator;

import SMTree.*;
import SMTree.iterator.ForwardIterator;
import roadrunner.node.Item;

public class ForwardItemIterator extends ForwardIterator<Item> implements EdibleIterator
{ 
    
    public ForwardItemIterator()
    {
        super();
    }
        
    @Override
    public void setRootIterator(SMTreeNode<Item> root) 
    {
        super.goTo(root.getObject());
    }
}

