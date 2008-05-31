package SMTree;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 

import java.util.ArrayList;
import java.util.List;

// #[regen=yes,id=DCE.4F937AF2-FA3C-57D0-0C39-485D889F11FB]
import java.util.Stack;
// </editor-fold> 
public class ForwardItemIterator<T> extends WrapperIterator<T> 
{ 
    public ForwardItemIterator(SMTreeNode<T> nodoInicial)
    {
        super(nodoInicial);
    }


    void inicializarArray(SMTreeNode<T> nodoInicial) 
    {
        array.clear();
        array.add(nodoInicial);
        recorrerHermanos(nodoInicial.getFirstChild());
    }

    private void recorrerHermanos(SMTreeNode<T> node) 
    {
        while(node != null)
        {
            array.add(node);
            recorrerHermanos(node.getFirstChild());
            node = node.getNext();
        }
    }
}

