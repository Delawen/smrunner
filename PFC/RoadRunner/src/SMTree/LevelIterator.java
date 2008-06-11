package SMTree;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class LevelIterator<T> extends WrapperIterator<T> 
{ 
    private LinkedList<SMTreeNode<T>> nodes;
    private ListIterator<SMTreeNode<T>> it;
    
    public LevelIterator()
    {
        super();
        nodes = new LinkedList<SMTreeNode<T>>();
    }
    
    private void AmpliturIteratorConstructor()
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
                    s.add(auxChild);
                    while(auxChild!=null && auxChild.getNext()!=null)
                    {
                        auxChild = auxChild.getNext();
                        nodes.add(auxChild);
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
            AmpliturIteratorConstructor();
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T next() {
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
    public T previous() {
        if(it == null)
            AmpliturIteratorConstructor();
        return it.previous().getObject();
    }
}


