package SMTree;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// #[regen=yes,id=DCE.4F937AF2-FA3C-57D0-0C39-485D889F11FB]
import java.util.Queue;
import java.util.Stack;
// </editor-fold> 
public class AmplitudIterator<T> extends WrapperIterator<T> 
{ 
    //Se van guardando los hijos de los nodos que recorremos del nivel actual:
    private int indice;
    
    public AmplitudIterator(SMTreeNode<T> nodoInicial)
    {
        super(nodoInicial);
    }

    @Override
    public void inicializarVector() 
    {
        array = new LinkedList<SMTreeNode<T>>();
        this.indice = 0;
    }

    void introducirElementos(SMTreeNode<T> nodoInicial) 
    {
        indice = 0;
        array.clear();
        this.array.add(nodoInicial);
    }

    private void recorrerHijos() 
    {
        //Vamos metiendo en el array todos los nodos del siguiente nivel
        LinkedList<SMTreeNode<T>> provisional = new LinkedList<SMTreeNode<T>>();
        
        if(!this.array.isEmpty())
        {
            for(int i = 0; i < this.array.size(); i++)
            {
                SMTreeNode<T> aux = this.array.get(i).getFirstChild();
                while(aux != null)
                {
                    provisional.add(aux);
                    aux = aux.getNext();
                }
            }
            this.array = provisional;
            this.indice = 0;
        }
    }

    @Override
    public SMTreeNode<T> next () 
    {
        //Si hemos llegado al final del array, es que pasamos al siguiente nivel:
        if(indice == this.array.size())
            recorrerHijos();
        
        //Sacamos el nodo de la posicion índice:
        SMTreeNode<T> res = this.array.get(indice);
        indice++;
        return res;
    }
    
    public boolean hasNext () 
    {
        if(this.array.size() == this.indice)
            recorrerHijos();
        return !this.array.isEmpty();
    }
}

