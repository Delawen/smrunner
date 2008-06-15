package roadrunner.iterator;

import SMTree.iterator.*;
import SMTree.*;
import java.util.LinkedList;
import roadrunner.node.*;


public class ForwardTokenIterator extends ForwardIterator<Item> implements EdibleIterator
{
    LinkedList<Item> cache = null;
    
    public ForwardTokenIterator() 
    {
        super();
    }

    @Override
    public Object next()
    {
        //Inicialización:
        if(super.lastNode == null && super.getRootIterator()!=null)
            super.lastNode = getRootIterator();

        
        //Limpiamos la cache porque nos vamos a mover:
        cache = null;
        
        Item item = super.lastNode.getObject();
        
        if(item instanceof Optional || item instanceof List)
        {
            LinkedList<Item> resultado = new LinkedList<Item>();
            resultado.add((Item)super.lastNode.getFirstChild().getObject());
            resultado.add((Item)super.lastNode.getNext().getObject());
            super.lastNode = super.lastNode.getFirstChild();
            return resultado;
        }
        else if(item instanceof Tuple)
        {
            super.lastNode = super.lastNode.getFirstChild();
            return super.lastNode.getObject();
        }
        else if(item instanceof Token)
        {
            while(super.lastNode.getNext() == null)
                super.lastNode = super.lastNode.getParent();
            
            super.lastNode = super.lastNode.getNext();
            
            return super.lastNode.getObject();
        }
        
        return null;
    }
    
    @Override
    public boolean hasNext()
    {
        SMTreeNode<Item> node = super.lastNode;
        
        if(this.next() == null)
        {
            super.lastNode = node;
            return false;
        }
        else
            return true;
    }
    
    @Override
    public boolean isNext(Item i)
    {
        int k = 0;
        
        //Si tenemos cache, la usamos. Si no, sacamos de next()
        if(cache == null)
        {
            cache = new LinkedList<Item>();
            Object next = this.next();
            if(next instanceof Item)
                cache.add((Item)next);
            else
                cache = (java.util.LinkedList<Item>)next;
        }
            
        //Vamos recorriendo todos los posibles next hasta que encuentra uno que haga match
        while(!cache.isEmpty())
        {
            //Sacamos un item de la lista
            Item item = cache.remove(k);
            k++;
            //Si sacamos un token, intentamos hacer matching
            if(item instanceof Token)
                if(item.match(i))
                {
                    //Hemos hecho matching, avanzamos y borramos la caché.
                    super.lastNode = super.getTree().getNode(i);
                    cache = null;
                    return true;
                }
                else
                {
                //Volvemos a guardarlo en la caché, por si hace falta
                    cache.add(k, item);
                    return false;
                }
            //Si sacamos una lista, la metemos en la cache
            else if(item instanceof java.util.List)
            {
                SMTreeNode nodo = super.lastNode;
                Object next = this.next();
                if(next instanceof Token)
                    cache.add(k, (Token)next);
                else if(next instanceof java.util.List)
                    for (Item elem : (java.util.List<Item>)next)
                        cache.add(k, elem);
                        
                super.lastNode = nodo;
            }
        }
        
        return false;
    }
}

