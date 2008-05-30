package SMTree;

import java.util.Stack;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.FA70D6F8-D651-1EA9-1171-F7195020302E]
// </editor-fold> 
public class BackwardItemIterator<T> extends WrapperIterator<T> 
{   
    private Stack<SMTreeNode<T>> array;

    
    public BackwardItemIterator(SMTreeNode<T> nodoInicial)
    {
        super(nodoInicial);
    }


    void inicializarArray(SMTreeNode<T> nodoInicial) 
    {
        this.array = new Stack<SMTreeNode<T>>();
        this.array.add(nodoInicial);
        recorrerHermanos(nodoInicial.getLastChild());
    }

    private void recorrerHermanos(SMTreeNode<T> node) 
    {
        while(node != null)
        {
            this.array.add(node);
            recorrerHermanos(node.getLastChild());
            node = node.getPrevious();
        }
    }


}

