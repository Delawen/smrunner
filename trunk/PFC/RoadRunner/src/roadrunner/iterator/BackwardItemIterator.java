package roadrunner.iterator;

import SMTree.*;
import SMTree.iterator.SMTreeIterator;
import roadrunner.node.Item;

public class BackwardItemIterator extends SMTreeIterator<Item> implements EdibleIterator 
{   
    private SMTreeIterator<Item> it;
    
    public BackwardItemIterator()
    {
        super();
    }

    public boolean isNext(Item o) {
        SMTreeNode temp = lastNode;    
        boolean result = next().equals(o);
        
        if(!result)
            lastNode = temp;

        return result;
    }

    @Override
    public Item next() {
        
        SMTreeNode<Item> resultNode = lastNode;
        
        if(lastNode==null && getRootIterator()!=null)
        {
            resultNode = getRootIterator();
            while(resultNode.getLastChild()!=null)
                resultNode = resultNode.getLastChild();
        }
        else if(lastNode == getRootIterator())
            throw new IllegalStateException("¿No se supone que habia next()?");
        else if(lastNode.getPrevious() != null && lastNode.getPrevious().getLastChild()==null)
        {
            resultNode = lastNode.getPrevious();
        }
        else if(lastNode.getPrevious() != null && lastNode.getPrevious().getLastChild()!=null)
        {
            resultNode = lastNode.getPrevious();
            while(resultNode.getLastChild()!=null)
                resultNode = resultNode.getLastChild();
        }
        else if(lastNode.getParent() != null)
        {
            resultNode = lastNode.getParent();
        }         
        else if(resultNode == null)
            throw new IllegalStateException("¿No se supone que habia next()?");
        
        lastNode = resultNode;
        
        return resultNode.getObject();
    }

    @Override
    public boolean hasNext() {
        boolean hasNext = lastNode==null && getRootIterator()!=null;
        if(lastNode != null && !hasNext)
        {
            hasNext |= lastNode != getRootIterator();
            hasNext |= lastNode.getPrevious() != null;
            hasNext |= lastNode.getParent() != null;
        }
        return hasNext;
    }

    @Override
    public boolean hasPrevious() {
        boolean hasNext = false;
        if(lastNode != null)
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
    public Item previous() {
        
        if(lastNode == null)
            throw new IllegalStateException("Previous() sin haber hecho next() antes");
        
        SMTreeNode<Item> resultNode=null;
        
        if(lastNode.getFirstChild() != null)
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

    public boolean goTo(Item objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

