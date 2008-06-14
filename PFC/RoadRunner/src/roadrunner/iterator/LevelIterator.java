package roadrunner.iterator;

import SMTree.*;
import SMTree.iterator.SMTreeIterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import roadrunner.node.Item;

public class LevelIterator extends SMTreeIterator<Item> implements EdibleIterator
{ 
    private LinkedList<SMTreeNode<Item>> nodes;
    private ListIterator<SMTreeNode<Item>> it;
    
    public LevelIterator()
    {
        super();
        nodes = new LinkedList<SMTreeNode<Item>>();
    }
    
    private void AmpliturIteratorConstructor()
    {
        Queue<SMTreeNode<Item>> s = new LinkedBlockingDeque();
        
        if(getRootIterator()!=null)
        {
            nodes.add(getRootIterator());

            s.add(getRootIterator());
            SMTreeNode aux=null, auxChild=null;
            while(!s.isEmpty())
            {
                aux = s.remove();
                if(aux.getFirstChild()!=null)
                {              
                    auxChild = aux.getFirstChild();
                    nodes.add(auxChild);
                    if(auxChild.getLastChild()!=null)
                        s.add(auxChild);
                    while(auxChild!=null && auxChild.getNext()!=null)
                    {
                        auxChild = auxChild.getNext();
                        nodes.add(auxChild);
                        if(auxChild.getLastChild()!=null)
                            s.add(auxChild);
                    }
                }
            }
        }
        
        it = nodes.listIterator();
    }

    @Override
    public boolean isNext(Item o) {
        if(it == null)
            AmpliturIteratorConstructor();
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Item next() {
        if(it == null)
            AmpliturIteratorConstructor();
        return it.next().getObject();
    }

    @Override
    public boolean hasNext() {
        if(it == null)
            AmpliturIteratorConstructor();
        return it.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        if(it == null)
            AmpliturIteratorConstructor();
        return it.hasPrevious();
    }

    @Override
    public Item previous() {
        if(it == null)
            AmpliturIteratorConstructor();
        return it.previous().getObject();
    }

}

