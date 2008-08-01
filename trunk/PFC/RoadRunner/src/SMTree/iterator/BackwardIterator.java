package SMTree.iterator;

import SMTree.SMTreeNode;

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
    public Object nextObject()     
    {
        return nextNode().getObject();
    }
        
    public SMTreeNode<T> nextNode()
    {
        SMTreeNode<T> resultNode=null;
        
        if(lastNode == getRootIterator())
            throw new IllegalStateException("no hay siguiente...");
        
        if(lastNode == null && getRootIterator()!=null)
        {
            resultNode = getRootIterator();
            while(resultNode.getLastChild()!=null)
                    resultNode = resultNode.getLastChild();
        }
        else if(lastNode.getPrevious() == null)
            resultNode = lastNode.getParent();
        else if(lastNode.getPrevious() != null && lastNode.getPrevious().getLastChild() == null)
            resultNode = lastNode.getPrevious();
        
        else if(lastNode.getPrevious() != null && lastNode.getPrevious().getLastChild() != null) 
        {
            resultNode = lastNode.getPrevious();
            do
                resultNode = resultNode.getLastChild();
            while(resultNode.getLastChild() != null);
        }
        else
            assert(false);
        
        assert(resultNode != null);
        
        lastNode = resultNode;
        return resultNode; 
    }
    
    @Override
    public boolean hasNext() {
        boolean hasNext = lastNode == null && getRootIterator()!=null;
        hasNext |= lastNode!= null && lastNode.getParent() != null;
//        if(lastNode != null && !hasNext)
//        {
//            hasNext |= lastNode.getParent();
//            hasNext |= lastNode.getPrevious() != null;
//            
//            if(!hasNext && lastNode != getRootIterator())
//            {
//                SMTreeNode<T> aux = lastNode;
//                do {
//                    aux = aux.getParent();
//                    hasNext |= aux.getPrevious() != null;
//                } while(hasNext==false && aux!=getRootIterator());
//            }
//        }
//        
        return hasNext;
    }

    @Override
    public boolean hasPrevious() {
//        boolean hasPrevious = lastNode == null && getRootIterator()!=null;
//        if(lastNode != null && !hasPrevious)
//        {
//            hasPrevious |= lastNode.getFirstChild() != null;
//            hasPrevious |= lastNode.getNext() != null && lastNode!=getRootIterator();
//            
//            if(!hasPrevious && lastNode != getRootIterator())
//            {
//                SMTreeNode<T> aux = lastNode;
//                do {
//                    aux = aux.getParent();
//                    hasPrevious |= (aux.getNext() != null);
//                } while(hasPrevious==false);
//            }
//        }
//        
//        return hasPrevious;
//        
        return lastNode != null;
    }

    @Override
    public SMTreeNode previousNode() {
        SMTreeNode<T> resultNode = lastNode;
        
        if(lastNode == null)
            throw new IllegalStateException("no se hizo next antes de previouos");
        else if(lastNode.getFirstChild() != null)
            lastNode  = lastNode.getFirstChild();
        else if(lastNode.getNext() != null)
            lastNode  = lastNode.getNext();
        else if(lastNode != getRootIterator())
        {
            lastNode  = lastNode;
            do
                lastNode  = lastNode .getParent();
            while(lastNode .getNext() == null && lastNode !=getRootIterator());
            if(lastNode ==getRootIterator())
                lastNode  = null;
            else
                lastNode  = lastNode.getNext();
        }
        
        
        assert (lastNode != null);

        return resultNode ; 
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
        
        assert(f.goTo(lastNode.getObject()));
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
        
        //assert(f.goTo(lastNode.getObject()));
        
        result += ":::<-Next::["+f.nextObject()+"]=Previous:::";
        
        for(int j=0; j<5 && f.hasNext(); j++)
        {
            result += "|"+f.nextObject();  
        }           
        
        result += "....";   
        
        return result;
    }
}

