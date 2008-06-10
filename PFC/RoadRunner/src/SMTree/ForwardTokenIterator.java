package SMTree;

import java.util.LinkedList;
import java.util.List;
import roadrunner.node.Item;


public class ForwardTokenIterator extends ForwardItemIterator<Item>
{
    private SMTreeNode<Item> actual;
     
    public ForwardTokenIterator() 
    {
        super();
    }
    
    /**
     * Initializes the iterator (after a void constructor)
     * 
     * @param tree
     * @return boolean
     */
    public boolean initialize(SMTree<Item> tree)
    {
        //Comprueba que era un iterador nuevo
        if(actual != null)
            throw new IllegalArgumentException("The iterator was tampered.");
        
        //Busca el primer token:
        this.actual = tree.getRoot();
        while(this.actual.getFirstChild() != null)
            this.actual = this.actual.getFirstChild();
        
        //Guarda el árbol:
        this.arbol = tree;
        
        return true;
    }

    @Override
    public void inicializarVector() 
    {
        super.inicializarVector();
    }

    /**
     * 
     * @return if there is no posible next item
     */
    @Override
    public boolean hasNext() 
    {
        return (!getPosibleNext().isEmpty());
    }
    
    /**
     * Returns the most probably "next" token (for the Mismatch creation)
     * 
     * @return Item
     */
    @Override
    public Item next()
    {
        List<Item> lista = getPosibleNext();
        if(lista.size() > 0)
            return lista.get(0);
        else
            return null;
    }
    
    /**
     * Checks if the param is included on the list of all the possible next.
     * 
     * @param t
     * @return boolean
     */
    @Override
    public boolean isNext(Item t)
    {
        List<Item> lista = getPosibleNext();
        int indice = lista.indexOf(t);
        if(indice > 0)
        {
            goTo(lista.get(indice));
            return true;
        }    
        else
            return false;
    }
    
    /**
     * Set the pointer on the param item.
     * Returns false if it doesn't find the item on the original tree.
     * 
     * @param objeto
     * @return boolean
     */
    @Override
    
    public boolean goTo (Item objeto) 
    {
        if(this.arbol.getNode(objeto) != null)
            return introducirElementos(search(objeto));
        return false;
    }
    
    
    /**
     * Returns all the posible nexts.
     * 
     * @return List<Item>
     */
    //TODO
    private List<Item> getPosibleNext()
    {
        List<Item> lista = new LinkedList<Item>();
        
        
        return lista;
    }
    
    //Not used functions:
    @Override
    boolean introducirElementos(SMTreeNode<Item> nodoInicial) 
    {
        throw new NotImplementedException();
    }

    
}

