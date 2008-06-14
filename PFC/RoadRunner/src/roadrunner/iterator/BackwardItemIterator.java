package roadrunner.iterator;

import SMTree.*;
import SMTree.iterator.*;
import roadrunner.node.Item;

public class BackwardItemIterator extends BackwardIterator<Item> implements EdibleIterator 
{       
    public BackwardItemIterator()
    {
        super();
    }

    @Override
    public void setRootIterator(SMTreeNode<Item> root) 
    {
        super.goTo(root.getObject());
    }
}

