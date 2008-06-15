package SMTree.iterator;

import SMTree.*;

public class BackwardIterator<T> extends SMTreeIterator<T> 
{   
    private SMTreeIterator<T> it;
    
    public BackwardIterator()
    {
        super();
    }
   @Override
    public boolean isNext(T o) 
    {
        return super.isNext(o);
    }

    @Override
    public Object next() {
        SMTreeNode<T> resultNode=null;
        
        if(lastNode == null && getRootIterator()!=null)
            resultNode = getRootIterator();
        else if(lastNode.getLastChild() != null)
            resultNode = lastNode.getLastChild();
        else if(lastNode.getPrevious() != null)
            resultNode = lastNode.getPrevious();
        else if(lastNode != getRootIterator())
        {
            resultNode = lastNode;
            do
                resultNode = resultNode.getParent();
            while(resultNode.getPrevious() == null && resultNode!=getRootIterator());
            if(resultNode==getRootIterator())
                resultNode = null;
            else
                resultNode = resultNode.getPrevious();
        }
        
        
        if(resultNode == null)
            throw new IllegalStateException("¿No se supone que habia next()?");
        
        lastNode = resultNode;
        return resultNode.getObject(); 
    }
    
    @Override
    public boolean hasNext() {
        boolean hasNext = lastNode == null && getRootIterator()!=null;
        if(lastNode != null && !hasNext)
        {
            hasNext |= lastNode.getLastChild() != null;
            hasNext |= lastNode.getPrevious() != null;
            
            if(!hasNext && lastNode != getRootIterator())
            {
                SMTreeNode<T> aux = lastNode;
                do {
                    aux = aux.getParent();
                    hasNext |= aux.getPrevious() != null;
                } while(hasNext==false && aux!=getRootIterator());
            }
        }
        
        return hasNext;
    }

    @Override
    public boolean hasPrevious() {
        boolean hasPrevious=false;
              
        if(lastNode != null && lastNode != getRootIterator())
        {
            hasPrevious |= lastNode.getPrevious()!=null;
            hasPrevious |= lastNode.getParent()!=null;
            
            /** SOBRA???
            if(!hasPrevious && lastNode.getPrevious() != null && lastNode.getPrevious().getFirstChild() != null)
            {
                SMTreeNode<T> aux = lastNode.getPrevious().getFirstChild();
                do {
                    aux = 
                    hasPrevious |= aux != null;
                } while(hasPrevious==false && aux!=null);
            }
             * */

        }
        
        return hasPrevious;
        
    }

    @Override
    public T previous() {
        SMTreeNode<T> resultNode = null;
        T result;
        
        if(lastNode==null)
            throw new NullPointerException("");
        
        if(lastNode == getRootIterator())
            throw new IllegalStateException("¿No se supone que habia previous()?");
        
        if(lastNode.getPrevious() != null && lastNode.getPrevious().getFirstChild() != null)
        {
            resultNode = lastNode.getPrevious();
            do
                resultNode = resultNode.getFirstChild();
            while(resultNode.getFirstChild()!=null);      
        }
        else if(lastNode.getPrevious()!=null && lastNode.getPrevious().getFirstChild() == null)
            resultNode = lastNode.getPrevious();
        else if(lastNode!=getRootIterator() && lastNode.getParent()!=null)
            resultNode = lastNode.getParent();
            
        if(resultNode!=null)
        {
            lastNode = resultNode; 
            result = resultNode.getObject();
        }
        else
            throw new IllegalStateException("¿No se supone que habia previous()?");
        
        return result;
    }
}

