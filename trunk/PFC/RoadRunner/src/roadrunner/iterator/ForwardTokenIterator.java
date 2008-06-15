package roadrunner.iterator;

import SMTree.iterator.*;
import SMTree.*;
import java.util.LinkedList;
import roadrunner.node.*;


public class ForwardTokenIterator extends ForwardIterator<Item> implements EdibleIterator
{
    private LinkedList<Item> cache = null;
    private SMTreeNode<Item> next;
    
    
    public ForwardTokenIterator() 
    {
        super();
    }

    @Override
    public Object next()
    {
        //Para cuando hacemos un goTo con varios caminos:
        if(next != null)
        {
            super.lastNode = next;
            next = null;
            cache = null;
            return super.lastNode.getObject();
        }
        
        //Inicialización:
        if(super.lastNode == null && super.getRootIterator()!=null)
            super.lastNode = getRootIterator();

        
        //Limpiamos la cache porque nos vamos a mover:
        cache = null;
        
        Item item = super.lastNode.getObject();
        
        if(item instanceof Optional || item instanceof List)
        {
            LinkedList<Item> resultado = new LinkedList<Item>();
            if(super.lastNode.getFirstChild() == null)
                throw new RuntimeException("El arbol está mal formado, hay un compositeItem sin hijos.");
            resultado.add((Item)super.lastNode.getFirstChild().getObject());
            SMTreeNode<Item> nodo = super.lastNode;
            while(nodo.getNext() == null)
                nodo = nodo.getParent();
            resultado.add((Item)nodo.getNext().getObject());
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
            {
                if(super.lastNode.getParent() == null)
                    return null;
                super.lastNode = super.lastNode.getParent();
            }
            
            super.lastNode = super.lastNode.getNext();
            
            return super.lastNode.getObject();
        }
        
        return null;
    }
    
    @Override
     public boolean goTo (Item objeto) 
    {
        
        next = getTree().getNode(objeto);
        if(next==null)
            return false; 
        
        return true;
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
        
        //Si tenemos cache, la usamos. Si no, sacamos de siguiente()
        if(cache == null)
        {
            Object siguiente = this.next();
            cache = new LinkedList<Item>();
            if(siguiente instanceof Item)
                cache.add((Item)siguiente);
            else
                cache = (java.util.LinkedList<Item>)siguiente;
        }
        
        if(next != null)
        {
            cache.clear();
            cache.add(next.getObject());
        }   
        //Vamos recorriendo todos los posibles siguiente hasta que encuentra uno que haga match
        while(!cache.isEmpty() && k < cache.size())
        {
            //Sacamos un item de la lista
            Item item = cache.remove(k);
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
                    k++;
                }
            //Si el siguiente es una lista o un opcional:
            else if((item instanceof List)||(item instanceof Optional))
            {
                SMTreeNode nodo_backup = super.lastNode;
                LinkedList cache_backup = this.cache;
                goTo(item);
                this.next();
                Object siguiente = this.next();
                this.cache = cache_backup;
                super.lastNode = nodo_backup;
                
                if(siguiente instanceof Token)
                    cache.add(k, (Token)siguiente);
                else if(siguiente instanceof java.util.List)
                    for (Item elem : (java.util.List<Item>)siguiente)
                        cache.add(k, elem);
                        
            }
            //Si sacamos una lista, la metemos en la cache
            else if(item instanceof java.util.List)
            {
                for (Item elem : (java.util.List<Item>)item)
                        cache.add(k, elem);
            }
            
        }
        
        return false;
    }
}

