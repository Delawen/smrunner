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
    private LinkedList<SMTreeNode<T>> hijosActuales;
    
    public AmplitudIterator(SMTreeNode<T> nodoInicial)
    {
        super(nodoInicial);
    }

    @Override
    public void inicializarVector() 
    {
        array = new LinkedList<SMTreeNode<T>>();
        hijosActuales = new LinkedList<SMTreeNode<T>>();
    }

    void introducirElementos(SMTreeNode<T> nodoInicial) 
    {
        array.clear();
        this.array.add(nodoInicial);
        agregarHijos(nodoInicial);
    }

    private void agregarHijos(SMTreeNode<T> nodo) 
    {
        SMTreeNode<T> aux = nodo.getFirstChild();
        while (aux != null) {
            this.hijosActuales.add(aux);
            aux = aux.getNext();
        }
    }

    private void recorrerHijos() 
    {
        int i = 0;
        SMTreeNode<T> aux = this.hijosActuales.get(i);
        while(aux != null)
        {
            array.add(aux);
            agregarHijos(aux);
            aux = this.array.get(i);
            i++;
        }
    }

    @Override
    public SMTreeNode<T> next () 
    {
        if(this.array.isEmpty())
            recorrerHijos();
        return super.next();
    }
    
}

