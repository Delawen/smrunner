package SMTree.iterator;

import roadrunner.iterator.*;
import SMTree.*;


public abstract class SMTreeIterator<T>
{
        
    protected SMTreeNode<T> lastNode;
    protected SMTree<T> tree;
    private SMTreeNode<T> rootIterator;
        
    public SMTreeIterator()
    {
        super();
        this.tree = null;
        this.rootIterator = null;
    }
    
    public SMTreeIterator(SMTree<T> tree)
    {
        super();
        this.tree = tree;
        this.rootIterator = tree.getRoot();
    }
    
     public boolean goTo (T objeto) 
    {
        SMTreeNode aux = getTree().getNode(objeto);
        if(aux==null)
            return false;
        
        // movemos el puntero al nodo al que queremos ir, pero tenemos que desplazarnos
        // para que el iterador el siguiente elemento que devuelva sea el que especificamos
        lastNode = aux; 
        if(hasPrevious())
        {
            Object o = previousNode();
            T previous;
            if(o instanceof java.util.List)
                previous = ((java.util.List<T>)o).get(0);
            else
                previous = (T)o;
            lastNode = getTree().getNode(previous);
        }
        else
            lastNode = null; // Si no tiene anterior, es porque es el primer elemento a recorrer
        
        return true;
    }
     
 
    public boolean isNext (T o)
    {
        SMTreeNode temp = lastNode;    
        Object next = nextObject();
        boolean result = false;
        if(next instanceof java.util.List)
            for(T n : (java.util.List<T>)next)
                if(n.equals(o))
                    result = true;
        else
            result = next.equals(o);
        
        if(!result)
            lastNode = temp;

        return result;
    }

    public abstract Object nextObject ();
    public abstract SMTreeNode nextNode ();
    public  abstract boolean hasNext ();
    public abstract boolean hasPrevious();
    public abstract SMTreeNode previousNode();
    public abstract Object previousObject();
    public void remove() 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SMTree<T> getTree() {
        return tree;
    }

    public void setTree(SMTree<T> arbol) {
        this.tree = arbol;
        setRootIterator(tree.getRoot());
    }

    public SMTreeNode<T> getRootIterator() {
        return rootIterator;
    }

    public void setRootIterator(SMTreeNode<T> rootIterator) {
        this.rootIterator = rootIterator;
        lastNode = null;
    }
}

