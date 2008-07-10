package roadrunner.iterator;

import SMTree.SMTreeNode;
import SMTree.iterator.*;


import java.util.LinkedList;
import roadrunner.node.*;

public class BackwardTokenIterator extends BackwardIterator<Item> implements EdibleIterator      
{    
    
    private LinkedList<Item> cache = null;
    private SMTreeNode<Item> next;
    
    public BackwardTokenIterator() 
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
        
        if(item instanceof Optional)
        {
            LinkedList<Item> resultado = new LinkedList<Item>();
            
            //Buscamos el siguiente (next)
            SMTreeNode<Item> nodo = super.lastNode;
            while(nodo.getPrevious() == null && nodo.getParent() != null)
            {
                nodo = nodo.getParent();
                if(nodo.getObject() instanceof List)
                    ((List)nodo.getObject()).setAccessed(true);
            }
            if(nodo.getPrevious() != null)
                resultado.add((Item)nodo.getPrevious().getObject());
            
            //Buscamos el primer hijo (principio del opcional)
            if(super.lastNode.getLastChild() == null)
                throw new RuntimeException("El arbol está mal formado, hay un compositeItem sin hijos.");
            resultado.add((Item)super.lastNode.getLastChild().getObject());

            super.lastNode = nodo;
            return resultado;
        }
        else if(item instanceof List)
        {
            LinkedList<Item> resultado = new LinkedList<Item>();
            if(super.lastNode.getLastChild() == null)
                throw new RuntimeException("El arbol está mal formado, hay un compositeItem sin hijos.");
            resultado.add((Item)super.lastNode.getLastChild().getObject());
            
            //Si es el hijo de donde estamos, entonces es la primera vez que entramos
            if(super.lastNode.getFirstChild().getObject() instanceof List)
                ((List)super.lastNode.getFirstChild().getObject()).setAccessed(false);
            
            if((item instanceof List && ((List)item).isAccessed()) || item instanceof Optional)
            {
                SMTreeNode<Item> nodo = super.lastNode;
                while(nodo.getPrevious() == null)
                {
                    nodo = nodo.getParent();
                    //Estamos subiendo porque no había un hermano, por tanto,
                    //si subimos y encontramos que el padre es una lista 
                    //es porque ya habíamos entrado en ella
                    if(nodo.getObject() instanceof List)
                        ((List)nodo.getObject()).setAccessed(true);
                }
                //Si es el hermano de donde estamos, entonces es la primera vez que entramos
                if(nodo.getNext() != null && (nodo.getNext().getObject() instanceof List))
                    ((List)nodo.getNext().getObject()).setAccessed(false);

                resultado.add((Item)nodo.getPrevious().getObject());
            }
            super.lastNode = super.lastNode.getLastChild();
            return resultado;
        }
        else if(item instanceof Tuple)
        {
            super.lastNode = super.lastNode.getLastChild();
            //Si es el hijo de una tupla es porque es la primera vez que entramos
            if(super.lastNode.getObject() instanceof List)
                ((List)super.lastNode.getObject()).setAccessed(false);

            return super.lastNode.getObject();
        }
        else if(item instanceof Token)
        {
            while(super.lastNode.getPrevious() == null)
            {
                if(super.lastNode.getParent() == null)
                    return null;
                super.lastNode = super.lastNode.getParent();
                
                //Estamos subiendo porque no había un hermano, por tanto...
                //si subimos y encontramos que el padre es una lista 
                //es porque ya habíamos entrado en ella
                if(super.lastNode.getObject() instanceof List)
                    ((List)super.lastNode.getObject()).setAccessed(true);

            }
            //Si es una lista es porque es la primera vez que entramos
            if(super.lastNode.getObject() instanceof List)
                ((List)super.lastNode.getObject()).setAccessed(false);

            super.lastNode = super.lastNode.getPrevious();
            
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
    
    public boolean isPrevious()
    {
        throw new UnsupportedOperationException("Probably you need a ForwardTokenIterator ;)");
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
        {
            super.lastNode = node;
            return true;
        }
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
                    super.lastNode = super.getTree().getNode(item);
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
    
    @Override
    public Object previous()
    {
                
        //Inicialización:
        if(super.lastNode == null && super.getRootIterator()!=null)
            return null;

        
        //Limpiamos la cache porque nos vamos a mover:
        cache = null;
        
        //El next será el nodo actual:
        next = super.lastNode;
        
        Item item = super.lastNode.getObject();
        
       if(item instanceof Optional || item instanceof List)
        {
            LinkedList<Item> resultado = new LinkedList<Item>();
            if(super.lastNode.getFirstChild() == null)
                throw new RuntimeException("El arbol está mal formado, hay un compositeItem sin hijos.");
            resultado.add((Item)super.lastNode.getFirstChild().getObject());
            
            //Si es el hijo de donde estamos, entonces es la primera vez que entramos
            if(super.lastNode.getFirstChild().getObject() instanceof List)
                ((List)super.lastNode.getFirstChild().getObject()).setAccessed(false);
            
            if((item instanceof List && ((List)item).isAccessed()) || item instanceof Optional)
            {
                SMTreeNode<Item> nodo = super.lastNode;

                while(nodo.getNext() == null)
                {
                    nodo = nodo.getParent();
                    //Estamos subiendo porque no había un hermano, por tanto,
                    //si subimos y encontramos que el padre es una lista 
                    //es porque ya habíamos entrado en ella
                    if(nodo.getObject() instanceof List)
                        ((List)nodo.getObject()).setAccessed(true);
                }
                //Si es el hermano de donde estamos, entonces es la primera vez que entramos
                if(nodo.getNext() != null && (nodo.getNext().getObject() instanceof List))
                    ((List)nodo.getNext().getObject()).setAccessed(false);

                resultado.add((Item)nodo.getNext().getObject());
            }
            super.lastNode = super.lastNode.getFirstChild();
            return resultado;
        }
        else if(item instanceof Tuple)
        {
            super.lastNode = super.lastNode.getFirstChild();
            
            //Si es el hijo de una tupla es porque es la primera vez que entramos
            if(super.lastNode.getObject() instanceof List)
                ((List)super.lastNode.getObject()).setAccessed(false);
            return super.lastNode.getObject();
        }
        else if(item instanceof Token)
        {
            //Buscamos el siguiente
            while(super.lastNode.getNext() == null)
            {
                //Si el padre es nulo es que estamos en la raiz
                if(super.lastNode.getParent() == null)
                    return null;
                super.lastNode = super.lastNode.getParent();
               
                //Estamos subiendo porque no había un hermano, por tanto...
                //si subimos y encontramos que el padre es una lista 
                //es porque ya habíamos entrado en ella
                if(super.lastNode.getObject() instanceof List)
                    ((List)super.lastNode.getObject()).setAccessed(true);
            }
            
            //Hemos encontrado el siguiente nodo
            //Si es una lista es porque es la primera vez que entramos
            if(super.lastNode.getObject() instanceof List)
                ((List)super.lastNode.getObject()).setAccessed(false);
            super.lastNode = super.lastNode.getNext();
            
            return super.lastNode.getObject();
        }
        
        return null;
    }


}

