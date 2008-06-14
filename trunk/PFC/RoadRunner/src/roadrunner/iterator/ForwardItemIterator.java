package roadrunner.iterator;

import SMTree.*;
import SMTree.iterator.SMTreeIterator;
import roadrunner.node.Item;

public class ForwardItemIterator extends SMTreeIterator<Item> implements EdibleIterator
{ 
    
    public ForwardItemIterator()
    {
        super();
    }

    @Override
    public boolean isNext(Item o) {
        SMTreeNode temp = lastNode;    
        boolean result = next().equals(o);
        
        if(!result)
            lastNode = temp;

        return result;
    }

    @Override
    public Item next() {
        SMTreeNode<Item> resultNode=null;
        
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
        return resultNode.getObject(); 
    }
    @Override
    public boolean hasNext() {
        boolean hasNext = lastNode == null && getRootIterator()!=null;
        if(lastNode != null && !hasNext)
        {
            hasNext |= lastNode.getFirstChild() != null;
            hasNext |= lastNode.getNext() != null;
            
            if(!hasNext && lastNode != getRootIterator())
            {
                SMTreeNode<Item> aux = lastNode;
                do {
                    aux = aux.getParent();
                    hasNext |= aux.getNext() != null;
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
                SMTreeNode<Item> aux = lastNode.getPrevious().getLastChild();
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
    public Item previous() {
        SMTreeNode<Item> resultNode = null;
        Item result;
        
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

