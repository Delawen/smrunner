package SMTree;

import java.util.ListIterator;


public abstract class WrapperIterator<T> implements ListIterator<T>
{
        
    protected SMTreeNode<T> lastNode;
    private SMTree<T> tree;
    private SMTreeNode<T> rootIterator;
        
    public WrapperIterator()
    {
        super();
        this.tree = null;
        this.rootIterator = null;
    }
    
    public WrapperIterator(SMTree<T> tree)
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
            lastNode = getTree().getNode(previous());
        else
            lastNode = null; // Si no tiene anterior, es porque es el primer elemento a recorrer
        
        return true;
    }
     
 
    public abstract boolean isNext (T o);

    public abstract T next ();
    public  abstract boolean hasNext ();
    public abstract boolean hasPrevious();
    public abstract T previous();

    public int nextIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int previousIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void set(T arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void add(T arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void remove() 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SMTree<T> getTree() {
        return tree;
    }

    public void setTree(SMTree<T> arbol) {
        this.tree = arbol;
    }

    public SMTreeNode<T> getRootIterator() {
        return rootIterator;
    }

    public void setRootIterator(SMTreeNode<T> rootIterator) {
        this.rootIterator = rootIterator;
    }
}

