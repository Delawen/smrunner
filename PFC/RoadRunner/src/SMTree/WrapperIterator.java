package SMTree;

import java.util.AbstractList;


public abstract class WrapperIterator<T> implements IteratorStrategy<T>
{
        
    protected AbstractList<SMTreeNode<T>> array;
        
    public WrapperIterator(SMTreeNode<T> nodoInicial)
    {
        super();
        inicializarVector();
        introducirElementos(nodoInicial);
    }
    
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.6C7D0D32-B042-86FF-2B3A-5AD6D127016A]
    // </editor-fold> 
    public boolean isNext (T o) 
    {
        SMTreeNode<T> nodo = next();
        if(o.equals(nodo))
            return true;
        this.array.add(this.array.size(), nodo);
        return false;
    }

    abstract void introducirElementos(SMTreeNode<T> nodoInicial);

    public void remove() 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.245215D2-1215-CC60-AB9E-6019D1E6A4AD]
    // </editor-fold> 
    public SMTreeNode<T> next () 
    {
        SMTreeNode<T> res = this.array.get(0);
        this.array.remove(0);
        return res;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.19EB4119-4E9F-6A37-A53B-97B36533E2E8]
    // </editor-fold> 
    public boolean hasNext () 
    {
        return !this.array.isEmpty();
    }
    

    public boolean goTo (SMTreeNode<T> nodo) 
    {
        introducirElementos(nodo);
        return true;
    }

    abstract void inicializarVector(); 
}

