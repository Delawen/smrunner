package roadrunner.iterator;

import SMTree.iterator.*;
import SMTree.*;
import java.util.LinkedList;
import roadrunner.node.*;


public class BackwardTokenIterator extends BackwardIterator<Item> implements EdibleIterator
{
    /**
     * Sirve para guardar los resultados del isNext por si acaso falla, 
     * no tener que repetir las operaciones otra vez en el siguiente isNext.
     * Se borra cuando se avanza.
     */
    private LinkedList<Item> cache = null;
    
    /**
     * Este item se utiliza sólo cuando se usa un goTo o un previous. 
     * Sirve para distinguir el next exacto cuando hay varios caminos. 
     * Nótese que el camino siempre se calcula desde el nodo anterior,
     * así que cuando hay varios caminos es la única forma de saber 
     * por cual tenemos que ir.
     */
    private SMTreeNode<Item> next;
    
    
    public BackwardTokenIterator() 
    {
        super();
    }

    /**
     * Calcula los posibles caminos siguientes al nodo actual.
     * Sirve tanto para el isNext() como para cuando se llama directamente.
     * 
     * @return siguiente objeto o lista de siguientes caminos
     */
    @Override
    public Object nextObject()
    {
        /**
         * Si tenemos el next, no hace falta calcularlo,
         * ya sabemos por dónde tenemos que ir.
         */
        if(next != null)
        {
            super.lastNode = next;
            next = null;
            cache = null;
            return super.lastNode.getObject();
        }
        
        /**
         * Inicialización.
         * Si es el primer movimiento del iterador.
         * No tenemos nodo anterior, pero tenemos raiz del iterador
         */
        if(super.lastNode == null && super.getRootIterator()!=null)
            super.lastNode = getRootIterator();

        
        /**
         * Limpiamos la cache porque nos vamos a mover
         * (es un next(), no un isNext()).
         */
        cache = null;
        
        /**
         * Calculamos el siguiente en función del último item recorrido:
         */
        Item item = super.lastNode.getObject();
        
        
        /**
         * Este será el caso más común 
         */
        if(item instanceof Token)
        {
            /**
             * Buscamos el siguiente, subiendo al padre si es necesario
             */
            while(super.lastNode.getPrevious() == null)
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
            
            /**
             * Ahora comprobamos si el item que tenemos aquí es un compositeItem (varios caminos)
             */
            if(super.lastNode.getObject() instanceof List || super.lastNode.getObject() instanceof Optional)
            {
                /**
                 * Lo preparamos para los siguientes if
                 */
                item = super.lastNode.getObject();
            }
            else
            {
                /**
                 * Si no era un compositeItem, está claro:
                 */
                super.lastNode = super.lastNode.getPrevious();
                return super.lastNode.getObject();
            }
        }
        
        
        /**
         * Si estamos en un opcional, podemos o introducirnos en el opcional, o devolver el siguiente al opcional.
         * Puede llegar aquí desde el if anterior (un token cuyo siguiente es un compositeItem) como directamente de
         * la llamada del método.
         */
        if(item instanceof Optional)
        {
            /**
             * Por regla general tendremos dos caminos, a no ser que sea el último item hoja del Wrapper.
             */
            LinkedList<Item> resultado = new LinkedList<Item>();
            
            /**
             * Buscamos el siguiente (nextObject)
             */
            
            /**
             * SMTreeNode<Item> nodo nos ayudará a guardar el super.lastNode actual 
             * para poder volver si fuese necesario.
             */
            SMTreeNode<Item> nodo = super.lastNode;
            
            /**
             * Este bucle detecta si el item es el último del opcional (no tiene siguiente)
             * Va subiendo al padre hasta encontrar el camino a seguir hacia delante.
             */
            while(nodo.getPrevious() == null && nodo.getParent() != null)
            {
                nodo = nodo.getParent();
                
                /**
                 * Si nos encontramos una lista por el camino, es que acabamos de salir de ella.
                 */
                if(nodo.getObject() instanceof List)
                    ((List)nodo.getObject()).setAccessed(true);
            }
            
            /**
             * Si hay un next después de subir en el bucle anterior, 
             * es que hay un item después del opcional.
             * Si no lo hubiera es porque es el último item del Wrapper.
             */
            if(nodo.getPrevious() != null)
                resultado.add((Item)nodo.getPrevious().getObject());
            
            /**
             * Buscamos el primer hijo (principio del opcional)
             * Esta comprobación nunca debería saltar si el Wrapper se forma correctamente.
             */
            if(super.lastNode.getLastChild() == null)
                throw new RuntimeException("El arbol está mal formado, hay un compositeItem sin hijos.");
            resultado.add((Item)super.lastNode.getLastChild().getObject());

            /**
             * Vamos hacia delante con el SMTreeNode<Item> nodo que guardamos antes.
             */
            super.lastNode = nodo;
            return resultado;
        }
         /**
         * Si estamos en una lista, podemos o introducirnos en la lista, o devolver el siguiente a la lista.
         */
        else if(item instanceof List)
        {
            /**
             * De nuevo tenemos varios caminos.
             */
            LinkedList<Item> resultado = new LinkedList<Item>();
            
            /**
             * Esta vez empezamos introduciendo el primer hijo de la lista.
             * Esto es para el Mismatch, que es más lógico así.
             */
            if(super.lastNode.getLastChild() == null)
                throw new RuntimeException("El arbol está mal formado, hay un compositeItem sin hijos.");
            resultado.add((Item)super.lastNode.getLastChild().getObject());
            
            /**
             * Si este hijo es una lista, no hemos entrado en este recorrido.
             */
            if(super.lastNode.getLastChild().getObject() instanceof List)
                ((List)super.lastNode.getLastChild().getObject()).setAccessed(false);
            
            /**
             * Si el nodo en el que estamos (item) ya ha sido accedido, podemos saltarnos la lista 
             * hasta el siguiente item.
             */
            if(((List)item).isAccessed())
            {
                /**
                 * Vamos buscando el siguiente, subiendo si hace falta, como antes
                 */
                SMTreeNode<Item> nodo = super.lastNode;

                while(nodo.getPrevious() == null)
                {
                    nodo = nodo.getParent();
                    /**
                     * Estamos subiendo porque no había un hermano, por tanto,
                     * si subimos y encontramos que el padre es una lista 
                     * es porque ya habíamos entrado en ella
                     **/
                    if(nodo.getObject() instanceof List)
                        ((List)nodo.getObject()).setAccessed(true);
                }
                
                /**
                 * Si el siguiente a la lista es otra lista, esta no 
                 * ha sido accedida todavía.
                 */
               if(nodo.getPrevious() != null && (nodo.getPrevious().getObject() instanceof List))
                    ((List)nodo.getPrevious().getObject()).setAccessed(false);

                resultado.add((Item)nodo.getPrevious().getObject());
            }
            
            /**
             * Esta vez vamos hacia delante pero entrando en el hijo.
             */
            super.lastNode = super.lastNode.getLastChild();
            return resultado;
        }
        /**
         * En verdad este caso sólo se debería de dar en el inicio
         */
        else if(item instanceof Tuple)
        {
            /**
             * Vamos al primer hijo
             */
            super.lastNode = super.lastNode.getLastChild();
            
            //Si es el hijo de una tupla es porque es la primera vez que entramos
            if(super.lastNode.getObject() instanceof List)
                ((List)super.lastNode.getObject()).setAccessed(false);
            return super.lastNode.getObject();
        }
        
        /**
         * Aquí ha pasado algo que no debería:
         */
        return null;
    }
    
    /**
     * Se mueve de forma que el siguiente next() será este item
     * @param objeto
     * @return
     */
    @Override
     public boolean goTo (Item objeto) 
    {
        
        next = getTree().getNode(objeto);
        if(next==null)
            return false; 
        
        return true;
    }

    
    /**
     * Comprueba si el siguiente objeto será nulo.
     * En cualquier caso, no se mueve.
     * @return
     */
    @Override
    public boolean hasNext()
    {
        SMTreeNode<Item> node = super.lastNode;
        SMTreeNode<Item> nodeNext = next;
        
        if(this.nextObject() == null)
        {
            super.lastNode = node;
            next = nodeNext;
            return false;
        }
        else
        {
            super.lastNode = node;
            next = nodeNext;
            return true;
        }
    }
    
    /**
     * Comprueba si en la lista de siguientes nodos está el item pasado como parámetro
     * @param i
     * @return
     */
    @Override
    public boolean isNext(Item i)
    {
        int k = 0;
        
        /**
         * temporal sirve para volver atrás si el isNext() resulta fallido
         */
        SMTreeNode temporal = super.lastNode;
        
        //Si tenemos cache, la usamos. Si no, lo sacamos de next()
        if(cache == null)
        {
            Object siguiente = this.nextObject();
            cache = new LinkedList<Item>();
            if(siguiente instanceof Item)
                cache.add((Item)siguiente);
            else
                cache = (java.util.LinkedList<Item>)siguiente;
        }
        
        /**
         * Si tenemos un next claro, no hay que darle más vueltas:
         */
        if(next != null)
        {
            cache.clear();
            cache.add(next.getObject());
        }   
        
        /**
         * Vamos recorriendo todos los posibles siguiente hasta que encuentra uno que haga match.
         * k sirve para mantenerlos ordenados, sin perder ni un solo camino
         * por si el isNext() resulta fallido, tener la caché.
         */
        while(!cache.isEmpty() && k < cache.size())
        {
            //Sacamos un item de la lista
            Item item = cache.remove(k);
            
            //Si sacamos un token, intentamos hacer matching
            if(item instanceof Token)
            {
                if(item.match(i))
                {
                    //Hemos hecho matching, avanzamos y borramos la caché.
                    super.lastNode = super.getTree().getNode(item);
                    cache = null;
                    return true;
                }
                else
                {
                //Volvemos a guardarlo en la caché, por si hace falta en otra llamada
                    cache.add(k, item);
                    k++;
                }
            }
            
            /**
             * Si el siguiente es una lista o un opcional, calculamos los siguientes a esa lista u opcional
             */
            else if((item instanceof List)||(item instanceof Optional))
            {
                /**
                 * nodos por si hay que volver atrás
                 */
                SMTreeNode nodo_backup = super.lastNode;
                LinkedList cache_backup = this.cache;
                
                /**
                 * vamos hasta el item siguiente
                 */
                goTo(item);
                
                /**
                 * Sacamos la lista de siguientes.
                 */
                this.nextObject();
                Object siguiente = this.nextObject();
                
                /**
                 * Deshacemos los cambios
                 */
                this.cache = cache_backup;
                super.lastNode = nodo_backup;
                
                /**
                 * Metemos el resultado en la caché para las próximas vueltas.
                 * Se tienen que meter en el mismo sitio por donde vamos, para que sea lo 
                 * siguiente a comprobar.
                 */
                if(siguiente instanceof Item)
                    cache.add(k, (Token)siguiente);
                else if(siguiente instanceof java.util.List)
                    for (Item elem : (java.util.List<Item>)siguiente)
                        cache.add(k, elem);
                        
            }
            //Si es una tupla, buscamos su primer hijo y lo metemos en la caché para estudiarlo
            else if(item instanceof Tuple)
            {
                Item siguiente = (Item) this.tree.getNode(item).getLastChild().getObject();
                cache.add(k, siguiente);
            }
            
            //Si sacamos una lista de caminos, los metemos uno a uno en la caché.
            else if(item instanceof java.util.List)
            {
                for (Item elem : (java.util.List<Item>)item)
                        cache.add(k, elem);
            }
            
        }
        
        /**
         * Deshacemos los cambios.
         */
        super.lastNode = temporal;
        /**
         * Si llegamos aquí es porque no hemos conseguido encajarlo en ninguna parte.
         */
        return false;
    }
    
    /**
     * Es como el next, pero hacia atrás.
     * @return
     */
    @Override
    public Object previous()
    {
        
        //Inicialización:
        if(super.lastNode == null && super.getRootIterator()!=null)
            return null;

        //Limpiamos la cache porque nos vamos a mover:
        cache = null;
        
        //El nextObject será el nodo en el que estamos ahora:
        next = super.lastNode;
        
        Item item = super.lastNode.getObject();
        
        if(item instanceof Optional || item instanceof List)
        {
            LinkedList<Item> resultado = new LinkedList<Item>();
            if(super.lastNode.getFirstChild() == null)
                throw new RuntimeException("El arbol está mal formado, hay un compositeItem sin hijos.");
            resultado.add((Item)super.lastNode.getFirstChild().getObject());
            
            //Si es el hijo de donde estamos, entonces es la primera vez que entramos
            if(super.lastNode.getLastChild().getObject() instanceof List)
                ((List)super.lastNode.getLastChild().getObject()).setAccessed(false);
            
                        
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
                if(nodo.getPrevious() != null && (nodo.getPrevious().getObject() instanceof List))
                    ((List)nodo.getPrevious().getObject()).setAccessed(false);

                resultado.add((Item)nodo.getNext().getObject());
            super.lastNode = super.lastNode.getFirstChild();
            }
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
            while(super.lastNode.getNext() == null)
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
            
            if(super.lastNode.getObject() instanceof List || super.lastNode.getObject() instanceof Optional)
            {
                java.util.List<Item> resultado = new LinkedList<Item>();
                resultado.add((Item)super.lastNode.getNext().getObject());
                resultado.add((Item)super.lastNode.getFirstChild().getObject());
                super.lastNode = super.lastNode.getNext();
                return resultado;
            }
            
            super.lastNode = super.lastNode.getNext();
            return super.lastNode.getObject();
        }
        
        return null;
    }
    
    public boolean isPrevious()
    {
        throw new UnsupportedOperationException("Probably you need a BackwardTokenIterator ;)");
    }

    @Override
    public Object next() 
    {
        return this.nextObject();
    }

}

