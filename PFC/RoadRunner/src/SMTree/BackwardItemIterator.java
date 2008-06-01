package SMTree;

import java.util.Stack;


public class BackwardItemIterator<T> extends WrapperIterator<T> 
{   
    public BackwardItemIterator(SMTreeNode<T> nodoInicial)
    {
        super(nodoInicial);
    }


    void introducirElementos(SMTreeNode<T> nodoInicial) 
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


    @Override
    public void inicializarVector() 
    {
        array = new Stack<SMTreeNode<T>>();
    }

}

