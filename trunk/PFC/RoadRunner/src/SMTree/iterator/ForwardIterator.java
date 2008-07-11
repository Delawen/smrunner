package SMTree.iterator;

import SMTree.*;

public class ForwardIterator<T> extends SMTreeIterator<T> 
{ 
    
    public ForwardIterator()
    {
        super();
    }

    @Override
    public boolean isNext(T o) 
    {
        return super.isNext(o);
    }
    
    @Override
    public Object nextObject()
    {    
        return nextNode().getObject();
    }

    public Object next() 
    {
        return this.nextObject();
    }
        
    public SMTreeNode<T> nextNode()
    {
       
        SMTreeNode<T> resultNode=null;
        
        if(lastNode == null && getRootIterator()!=null)
            resultNode = getRootIterator();
        else if(lastNode.getFirstChild() != null)
            resultNode = lastNode.getFirstChild();
        else if(lastNode.getNext() != null)
            resultNode = lastNode.getNext();
        else if(lastNode != getRootIterator())
        {
            resultNode = lastNode;
            do
                resultNode = resultNode.getParent();
            while(resultNode.getNext() == null && resultNode!=getRootIterator());
            if(resultNode==getRootIterator())
                resultNode = null;
            else
                resultNode = resultNode.getNext();
        }
        
        
        if(resultNode == null)
            throw new IllegalStateException("¿No se supone que habia next()?");
        
        lastNode = resultNode;
        return resultNode; 
    }
    
    @Override
    public boolean hasNext() {
        boolean hasNext = lastNode == null && getRootIterator()!=null;
        if(lastNode != null && !hasNext)
        {
            hasNext |= lastNode.getFirstChild() != null;
            hasNext |= lastNode.getNext() != null && lastNode!=getRootIterator();
            
            if(!hasNext && lastNode != getRootIterator())
            {
                SMTreeNode<T> aux = lastNode;
                do {
                    aux = aux.getParent();
                    hasNext |= (aux.getNext() != null && aux != getRootIterator());
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
            if(!hasPrevious && lastNode.getPrevious() != null && lastNode.getPrevious().getLastChild() != null)
            {
                SMTreeNode<T> aux = lastNode.getPrevious().getLastChild();
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
    public Object previous() {
        SMTreeNode<T> resultNode = null;
        T result;
        
        if(lastNode==null)
            throw new NullPointerException("");
        
        if(lastNode == getRootIterator())
            throw new IllegalStateException("¿No se supone que habia previous()?");
        
        if(lastNode.getPrevious() != null && lastNode.getPrevious().getLastChild() != null)
        {
            resultNode = lastNode.getPrevious();
            do
                resultNode = resultNode.getLastChild();
            while(resultNode.getLastChild()!=null);      
        }
        else if(lastNode.getPrevious()!=null && lastNode.getPrevious().getLastChild() == null)
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

