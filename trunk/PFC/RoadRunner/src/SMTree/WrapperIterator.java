package SMTree;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
import java.util.Iterator;
// #[regen=yes,id=DCE.566CA952-367C-1D55-D669-947718A197AB]
import java.util.Stack;
// </editor-fold> 
public abstract class WrapperIterator<T> implements IteratorStrategy<T>
{

    protected Stack<SMTreeNode<T>> array;
        
    public WrapperIterator(SMTreeNode<T> nodoInicial)
    {
        super();
        array = new Stack<SMTreeNode<T>>();
        inicializarArray(nodoInicial);
    }
    
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.6C7D0D32-B042-86FF-2B3A-5AD6D127016A]
    // </editor-fold> 
    public boolean isNext (T o) 
    {
        SMTreeNode<T> nodo = next();
        if(o.equals(nodo))
            return true;
        this.array.add(nodo);
        return false;
    }

    abstract void inicializarArray(SMTreeNode<T> nodoInicial);

    public void remove() 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.245215D2-1215-CC60-AB9E-6019D1E6A4AD]
    // </editor-fold> 
    public SMTreeNode<T> next () 
    {
        return this.array.pop();
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.19EB4119-4E9F-6A37-A53B-97B36533E2E8]
    // </editor-fold> 
    public boolean hasNext () 
    {
        return !this.array.empty();
    }
    

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.83C74261-AEF1-2AFD-05D3-C7582DC2B24A]
    // </editor-fold> 
    public boolean goTo (SMTreeNode<T> nodo) 
    {
        if(this.array.search(nodo) == -1)
            return false; 
            
        inicializarArray(nodo);
        return true;
    }
}

