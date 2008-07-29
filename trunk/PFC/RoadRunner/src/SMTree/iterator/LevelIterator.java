package SMTree.iterator;

import SMTree.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class LevelIterator<T> extends SMTreeIterator<T> 
{ 
    private LinkedList<SMTreeNode<T>> nodes;
    private ListIterator<SMTreeNode<T>> it;
    
    public LevelIterator()
    {
        super();
        nodes = new LinkedList<SMTreeNode<T>>();
    }
    
    private void LevelIteratorConstructor()
    {
        Queue<SMTreeNode<T>> s = new LinkedBlockingDeque();
        
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
    public boolean isNext(T o) {
        if(it == null)
            LevelIteratorConstructor();
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T nextObject() {
        if(it == null)
            LevelIteratorConstructor();
        return it.next().getObject();
    }
    
    @Override
    public SMTreeNode<T> nextNode() {
        if(it == null)
            LevelIteratorConstructor();
        return it.next();
    }

    @Override
    public boolean hasNext() {
        if(it == null)
            LevelIteratorConstructor();
        return it.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        if(it == null)
            LevelIteratorConstructor();
        return it.hasPrevious();
    }

    @Override
    public SMTreeNode previousNode() {
        if(it == null)
            LevelIteratorConstructor();
        return it.previous();
    }

    @Override
    public Object previousObject() {
        return previousNode().getObject();
    }
}


