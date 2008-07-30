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
        
        
        assert (resultNode != null);
        
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
//        boolean hasPrevious=false;
//              
//        if(lastNode != null && lastNode != getRootIterator())
//        {
//            hasPrevious |= lastNode.getPrevious()!=null;
//            hasPrevious |= lastNode.getParent()!=null;
//
//        }
//        
//        return hasPrevious;
        
        return lastNode != null;
        
    }

    @Override
    public SMTreeNode previousNode() {
        SMTreeNode<T> resultNode = lastNode;
        //T result;
        
        if(lastNode==null)
            throw new NullPointerException("");
        
        if(lastNode == getRootIterator())
            lastNode = null;
        else if(lastNode.getPrevious() != null && lastNode.getPrevious().getLastChild() != null)
        {
            lastNode = lastNode.getPrevious();
            do
                lastNode = lastNode.getLastChild();
            while(lastNode.getLastChild()!=null);      
        }
        else if(lastNode.getPrevious()!=null && lastNode.getPrevious().getLastChild() == null)
            lastNode = lastNode.getPrevious();
        else if(lastNode!=getRootIterator() && lastNode.getParent()!=null)
            lastNode = lastNode.getParent();        
        else
            assert(false);
        
        return resultNode;
    }

    @Override
    public Object previousObject() {
        return previousNode().getObject();
    }
    
    @Override
    public String toString()
    {     
        if(lastNode==null)
            return "";
        
        String result = "....";
        ForwardIterator f = new ForwardIterator();
        f.setTree(tree);
        
        f.goTo(lastNode.getObject());
        f.previousNode();
        
        int i = 0;
        while(i<5 && f.hasPrevious())
        {
            if(f.previousNode() != lastNode)
                i++;
            else
                break;
        }
        
        for(int j=0; j<i && f.hasNext(); j++)
        {
            result += "|"+f.nextObject();  
        }
        
        //f.goTo(lastNode.getObject());
        
        result += ":::Previous=["+f.nextObject()+"]::Next->:::";
        
        for(int j=0; j<5 && f.hasNext(); j++)
        {
            result += "|"+f.nextObject();  
        }           
        
        result += "....";   
        
        return result;
    }

}

