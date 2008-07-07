package SMTree;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 

import SMTree.SMIndexStructure;
import SMTree.utils.Enclosure;
import SMTree.utils.Kinship;
import SMTree.iterator.ForwardIterator;
import SMTree.iterator.SMTreeIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import roadrunner.RoadRunner.ExitLevel;

// </editor-fold> 
public class SMTree<T> implements Cloneable{

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E261DF56-4B2B-0EA1-3702-1E88F41ED5A2]
    // </editor-fold> 
    private SMTreeNode<T> root;
    private SMIndexStructure mapa;
    
    public SMTree ()
    {
        this.mapa = new SMIndexStructure();
    
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.83F2AB07-B68C-2345-5D68-D860DA28ACDD]
    // </editor-fold> 
    public SMTree (SMTreeNode root) 
    {
        super();
        this.root = root;
        this.mapa = new SMIndexStructure(root);
    }
    
    public SMTree (T rootObject)
    {
        super();
        this.root = new SMTreeNode<T>(rootObject);
        this.mapa = new SMIndexStructure(this.root);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.AE7C2899-18A2-2421-2B1E-F759FB35FB7D]
    // </editor-fold> 
    public void setRootObject(T o) 
    {
        if(root == null)
            root = new SMTreeNode<T>(o);
        else
        { 
            //borramos del mapa la anterior raiz.
            mapa.remove(root.getObject());
            root.setObject(o);
        }
                 
        //actualizamos el mapa
        mapa.add(root);
    
    }
    
    public T getRootObject()
    {
        return this.root.getObject();
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.DD54EFD1-598F-73DE-644E-F324B2283F77]
    // </editor-fold> 
    public SMTreeNode getRoot () {
        return root;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.0F12C6C0-0E3C-D31C-8C24-A027D7896501]
    // </editor-fold> 
    public void setRoot (SMTreeNode val) {
        if(val ==null)
            throw new NullPointerException("El valor que se intentó introducir como raiz es nulo.");
        
        if(root != null)
            mapa.remove(root.getObject());
        this.root = val;
        mapa.add(val);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5291916E-6233-D758-53C9-932DAB3F937D]
    // </editor-fold> 
    public boolean addSubSMTree (SMTree subtree, SMTreeNode where, Kinship k) {
        
        if(subtree == null || where == null || k == null)
            throw new NullPointerException("");
        
        addSMTreeNode(subtree.getRoot(), where, k);
        
        return true;
    }

    
    /**
     * Añade el nodo 'n'y todos sus descendientes en la posición 'k' del nodo 'where'
     * 
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.EC682BB2-DE7A-1448-A23F-DC7E2A17D347]
    // </editor-fold> 
    public boolean addSMTreeNode (SMTreeNode n, SMTreeNode where, Kinship k) {
        
        if(n == null || where == null || k == null)
            throw new NullPointerException("");
        
        if(where == root && (k == Kinship.LEFTSIBLING || k == Kinship.RIGHTSIBLING ))
        {
            roadrunner.RoadRunner.debug("Intentando añadirle un hermano a la raiz", ExitLevel.SLEEPandEXIT);
            return false;
        }
        
        if(n.getObject() == null)
            roadrunner.RoadRunner.debug("Se esta añadiendo un nodo con contenido vacio al arbol",ExitLevel.SLEEPandCONTINUE);
        
        //intentamos añadir el nodo al mapa
        if(!mapa.add(n))
        {
            roadrunner.RoadRunner.debug("El nodo que se intenta añadir ya existe en el arbol",ExitLevel.SLEEPandCONTINUE);
            return false;
        }
        
        switch(k)
        {
            case CHILD:
                //where sera el padre
                
                //si no tiene hijos entonces 'n' sera tanto el primer como el ultimo hijo
                if(where.getFirstChild() == null)
                {
                    where.setFirstChild(n);
                    where.setLastChild(n);
                }
                //si tiene hijos lo añadimos al final
                else
                {
                    where.getLastChild().setNext(n);
                    n.setPrevious(where.getLastChild());
                    where.setLastChild(n);
                }
                
                //le decimos a 'n' que 'where' es su padre :-O
                n.setParent(where);
                break;
            case RIGHTSIBLING:
                //where sera el hermano izquierdo de 'n'
                
                /**
                 * Casos:
                 * 1. No tiene hermano derecho: Cuidado con actualizar el lastchild del padre
                 * 2. Si tiene hermano derecho
                 * 
                 */
                
                //Caso 1:
                if(where.getNext() == null)
                {
                    //actualizamos relaciones padre-hijo
                    n.setParent(where.getParent());
                    where.getParent().setLastChild(n);
                    
                    //relaciones entre 'n' y 'where'
                    where.setNext(n);
                    n.setPrevious(where);
                }
                //Caso 2:
                else
                {
                    //actualizamos el nuevo nodo
                    n.setPrevious(where);
                    n.setNext(where.getNext());
                    
                    //actualizamos nodos adyacentes al nuevo
                    where.getNext().setPrevious(n);
                    where.setNext(n);
                    
                    //le asignamos padre a 'n'         
                    n.setParent(where.getParent());
                }
                break;   
            case LEFTSIBLING:
                //where sera el hermano derecho de 'n'
                /**
                 * Casos:
                 * 1. No tiene hermano izquierdo: ojo con actualizar firstchild del padre
                 * 2. Si tiene hermano izquierdo
                 * 
                 */
                
                //Caso 1:
                if(where.getPrevious() == null)
                {
                    //actualizamos relaciones padre-hijo
                    n.setParent(where.getParent());
                    where.getParent().setFirstChild(n);
                    
                    //relaciones entre 'n' y 'where'
                    where.setPrevious(n);
                    n.setNext(where);
                }
                //Caso 2:
                else
                {
                    //actualizamos el nuevo nodo
                    n.setPrevious(where.getPrevious());
                    n.setNext(where);
                    
                    //actualizamos nodos adyacentes al nuevo
                    where.getPrevious().setNext(n);
                    where.setPrevious(n);
                    
                    //le asignamos padre a 'n'         
                    n.setParent(where.getParent());
                }
                break;
        }
        
        // Y añadimos todos los nodos los descendientes de 'n'         
        
           //TODO: recorrer solo el subarbol
           SMTreeIterator<T> it = this.iterator(ForwardIterator.class);

            //it.goTo(n);

            boolean success = true;

            while(it.hasNext() && success)
            {
                success = mapa.add(getNode((T)it.next()));
            }

            return success;
        }
    
    
    /**
     * @param n
     * @return
     * 
     * Este metodo se irá al último nodo hoja (la hoja más a la derecha)
     * e irá eliminando todos los nodos que colgaban de n
     */
    public boolean removeFastSMTreeNode(SMTreeNode<T> n)
    {
        
        if(n == null)
            throw new NullPointerException("");
            
        SMTreeNode nodeAux = n.getLastChild();
        
        while(n != nodeAux && nodeAux != null)
        {
            //Si tiene hijos, buscamos a su último hijo
            if(nodeAux.getLastChild() != null) 
                nodeAux = nodeAux.getLastChild();
            // Si es un nodo hoja
            else if(nodeAux.getLastChild() == null && nodeAux.getFirstChild() == null)
            {
                
                // borramos a nodoAux del mapa
                mapa.remove(nodeAux);
                //eliminamos tambien su contenido
                nodeAux.setObject(null);
                
                //si nodoAux no tiene previo, es porque será el hijo más a la izda.
                if(nodeAux.getPrevious() == null)
                {
                    //nos vamos al padre
                    nodeAux = nodeAux.getParent();
                    
                    //dejamos al hijo huerfano...
                    nodeAux.getFirstChild().setParent(null);
                    //... y su hermano siguiente, si es que lo tenia ¿QUITAR?
                    nodeAux.getFirstChild().setNext(null);
                    
                    //el padre ha dejado de tener hijos
                    nodeAux.setFirstChild(null);
                    nodeAux.setLastChild(null);
                }
                else
                {
                    //este hijo será huerfano, pero no aún sus hermanos anteriores
                    nodeAux.setParent(null); 
                    //dejará de tener siguiente, aunque ya no deberia de tenerlo ¿QUITAR?
                    nodeAux.setNext(null);
                    
                    //nos vamos al nodo anterior
                    nodeAux = nodeAux.getPrevious();
                    
                    //el nodo anterior termina de rematar a su siguiente
                    
                    nodeAux.getNext().setPrevious(null);
                    //y el siguiente pierde la referencia a su nodo anterior lo dará por muerto 
                    nodeAux.setNext(null);
                }
                
                
            }
        }
        
        if(n.getPrevious() != null)
            n.getPrevious().setNext(n.getNext());
        if(n.getNext() != null)
            n.getNext().setPrevious(n.getPrevious());
        
        // Si 'n' era el primer hijo de su padre     
        if( n.getParent().getFirstChild() == n)
        {
            if(n.getNext() != null)
               n.getParent().setFirstChild(n.getNext());
            else
            {
                n.getParent().setFirstChild(null);
                n.getParent().setLastChild(null);
            }      
        }
        
        // Si 'n' era el ultimo hijo de su padre
        if( n.getParent().getLastChild() == n)
        {
            if(n.getPrevious() != null)
               n.getParent().setLastChild(n.getPrevious());
            else
            {
                n.getParent().setFirstChild(null);
                n.getParent().setLastChild(null);
            }      
        }
        //finiquitamos 'n'
        
        mapa.remove(n);
        n.setNext(null);
        n.setPrevious(null);
        n.setParent(null);
        n.setObject(null);
         
        return true;
    }
    
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D6BCC3C4-38C4-91FA-BCA0-6B5FE88BA618]
    // </editor-fold> 
    public boolean substitute 
            (SMTreeNode<T> from, Enclosure inclusionFrom, 
            SMTreeNode<T> to, Enclosure inclusionTo, SMTree<T> tree) 
    {
        if(from == null || inclusionFrom == null || inclusionTo == null
                || to == null)
            throw new NullPointerException("");
            
            
        /*
         * La idea es borrar toda la region que va [desde,hasta]
         * ambos extremos inclusive 
         */
        if(inclusionFrom == Enclosure.NOT_ENCLOSED)
            from = from.getNext();
        if(inclusionTo == Enclosure.NOT_ENCLOSED)
             to = to.getPrevious();
        
        // from y to deben pertenecer al mismo nivel del arbol
        int nivelFrom = level(from);
        int nivelTo = level(to);
        
        if(from.getParent() != to.getParent())
        {
            if(nivelFrom < nivelTo)
            {
                while(from.getParent() != to.getParent() 
                        && nivelFrom != nivelTo 
                        && from.getParent().getFirstChild() == from)
                {
                    from = from.getParent();
                    nivelFrom--;
                }
                
            }
            else if(nivelFrom > nivelTo)
            {
                while(from.getParent() != to.getParent() 
                        && nivelFrom != nivelTo 
                        && to.getParent().getFirstChild() == to)
                {
                    to = to.getParent();
                    nivelTo--;
                }
                
            }

            if(from.getParent() != to.getParent())
            {
                roadrunner.RoadRunner.debug("La región a sustituir no forma parte del mismo nivel.",ExitLevel.SLEEPandEXIT);
                return false;
            }
        }

        /* Borramos [desde,hasta), el hasta no incluido*/
        //Si from y to son el mismo, se queda igual
        SMTreeNode<T> nextFrom;
        while(from != to)
        {
            nextFrom = from.getNext();
            removeFastSMTreeNode(from);
            from = nextFrom;
        }
        
        
        /**
         * tres casos para 'to':
         * 1. 'to' tiene más elementos a la derecha : * * * from...to * * *
         * 2. 'to' no tiene más elementos a la derecha pero si a la izquierda : * * * from...to
         * 3. 'to' no tiene más elementos ni a la izquierda ni a la derecha: from...to
         */
        
        //Caso 1
        SMTreeNode<T> where;
        Kinship whereKinship;
        
        if(to.getNext() != null)
        {
            //Insertaremos a la izquierda del elemento siguiente a 'to'
            where = to.getNext();
            whereKinship = Kinship.LEFTSIBLING;
        }     
        //Caso 2
        else if(to.getPrevious() != null)
        {
            //Insertaremos a la derecha del elemento anterior a 'to'
            where = to.getPrevious();
            whereKinship = Kinship.RIGHTSIBLING;
        }
        //Caso 3
        else 
        {
            where = to.getParent();
            whereKinship = Kinship.CHILD;
        }
        
        //Borramos 'to'
        removeFastSMTreeNode(to);
        
        return addSubSMTree(tree, where, whereKinship);
    }
    
    private int level(SMTreeNode n)
    {
        int level = 0;
        while((n = n.getParent()) != null)
            level++;
        return level;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.024EDDB9-9F2B-3FDF-9CF5-E955ADB3ACBE]
    // </editor-fold> 
    public boolean substituteObject (T from, Enclosure inclusionFrom,
            T to, Enclosure inclusionTo, T by) 
    {
        SMTreeNode<T> f = this.mapa.get(from);
        SMTreeNode<T> t = this.mapa.get(to);
        SMTree<T> b = new SMTree<T>(new SMTreeNode<T>(by));
        return substitute(f,inclusionFrom, t,inclusionTo,b);
    }

    
    public boolean substituteObject (T from, Enclosure inclusionFrom,
            T to, Enclosure inclusionTo, SMTree<T> byTree) 
    {
        SMTreeNode<T> f = this.mapa.get(from);
        SMTreeNode<T> t = this.mapa.get(to);
        return substitute(f,inclusionFrom, t,inclusionTo,byTree);
    }
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A75CE315-A8D3-C755-EC0F-EF80CB8DAFEE]
    // </editor-fold> 
    public boolean removeObject (T o) 
    {
        return removeFastSMTreeNode(this.mapa.get(o));
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D0E849E0-57CC-9D85-CDEF-525A262F1DC8]
    // </editor-fold> 
    public boolean addObject (T o, SMTreeNode where, Kinship k) {
        
        if(o == null || where == null || k == null)
            throw new NullPointerException("");
        
        if(mapa.containsObject(o))
        {
            roadrunner.RoadRunner.debug("Intentando añadir un objeto al arbol que ya existe", ExitLevel.CONTINUE);
            return false;
        }
        
        return addSMTreeNode(new SMTreeNode(o), where, k);
    }
 
    public SMTreeIterator<T> iterator (Class iteratorClass)
    {
        SMTreeIterator<T> wi = null;
        try {
            wi = (SMTreeIterator<T>) iteratorClass.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(SMTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SMTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        wi.setTree(this);
        wi.setRootIterator(getRoot());
        
        return wi;
    }
    
    public SMTreeIterator<T> iterator (Class iteratorClass, SMTreeNode virtualRoot) 
    {
        if(virtualRoot==null)
            throw new NullPointerException("");
        SMTreeIterator<T> wi =null;
        try {
            wi = (SMTreeIterator<T>) iteratorClass.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(SMTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SMTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        wi.setTree(this);
        wi.setRootIterator(virtualRoot);
        
        return wi;
    }

    
    //TODO SMTree Clone()
    @Override
    public SMTree clone() throws CloneNotSupportedException
    {
        SMTree clon = (SMTree)super.clone();
        
        return clon;
    }
    
    
    //TODO SMTree Equals()
    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof SMTree))
            return false;
   
        SMTreeIterator<T> itThis = this.iterator(ForwardIterator.class);
        SMTreeIterator<T> itObject = ((SMTree<T>) o).iterator(ForwardIterator.class);
        
        while(itThis.hasNext() && itObject.hasNext())
            if(! itThis.next().equals(itObject.next()))
                return false;
        
        if(itThis.hasNext() || itObject.hasNext())
            return false;
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.root != null ? this.root.hashCode() : 0);
        hash = 79 * hash + (this.mapa != null ? this.mapa.hashCode() : 0);
        return hash;
    }

    public SMIndexStructure getMapa() {
        return mapa;
    }

    public void setMapa(SMIndexStructure mapa) {
        this.mapa = mapa;
    }
    
    public SMTreeNode<T> getNode(T objeto)
    {
        return this.getMapa().get(objeto);
    }
    
    @Override
    public String toString() 
    {
        return "buh!";
    }

    
    public SMTree cloneSubTree(T from)
    {
       if(from==null)
            throw new NullPointerException("");
          
    throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public SMTree cloneSubTree(SMTreeNode from)
    {
       if(from==null)
            throw new NullPointerException("");
          
    throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public SMTree cloneSubTree(T from,T newParent) throws CloneNotSupportedException
    {
        if(from==null || newParent==null)
            throw new NullPointerException("");
        
        return cloneSubTree(mapa.get(from), new SMTreeNode(newParent));
    }
    
    public SMTree cloneSubTree(SMTreeNode from,SMTreeNode newParent) throws CloneNotSupportedException
    {
        if(from==null || newParent==null)
            throw new NullPointerException("");
        
        //Index para el arbol clonado
        SMIndexStructure indexClone = new SMIndexStructure();
      
        // Mapa auxiliar para clonar sin necesidad de recursion
        Map<SMTreeNode, SMTreeNode> mapClone= new HashMap<SMTreeNode, SMTreeNode>();
        
        //TODO : vendria bien un iterador del arbol que devolviera Nodos y no Objetos 
        ForwardIterator<T> it = new ForwardIterator<T>();
        it.setRootIterator(from);
        T auxObj;
        SMTreeNode auxNode, auxNodeClone;
        while(it.hasNext())
        {
            auxObj = (T) it.next();
            auxNode = mapa.get(auxObj); // TODO esto es un poco redundante, mejor hacer el iterador...
            auxNodeClone = auxNode.clone();
            mapClone.put(auxNode,auxNodeClone );
            indexClone.add(auxNodeClone);
        }
        
        Iterator itMap = mapClone.entrySet().iterator();
        SMTreeNode neighbor;
        while (itMap.hasNext()) 
        {
            Map.Entry entryNode = (Map.Entry)itMap.next();
            
            //auxNode es el nodo original
            auxNode = (SMTreeNode) entryNode.getKey();
            //auxNodeClone es el nodo parcialmente clonado
            auxNodeClone = (SMTreeNode) entryNode.getValue();
            /** hacemos que las referencias a los vecinos(parientes) apunten a los nuevos clonados**/
            if(auxNode.getParent()!=null)
            {
                neighbor = mapClone.get(auxNode.getParent());
                auxNodeClone.setParent(neighbor);             
            }
            if(auxNode.getFirstChild()!=null)
            {
                neighbor = mapClone.get(auxNode.getFirstChild());
                auxNodeClone.setFirstChild(neighbor);             
            }
            if(auxNode.getLastChild()!=null)
            {
                neighbor = mapClone.get(auxNode.getLastChild());
                auxNodeClone.setLastChild(neighbor);             
            }
            if(auxNode.getNext()!=null)
            {
                neighbor = mapClone.get(auxNode.getNext());
                auxNodeClone.setNext(neighbor);             
            }
            if(auxNode.getPrevious()!=null)
            {
                neighbor = mapClone.get(auxNode.getPrevious());
                auxNodeClone.setPrevious(neighbor);             
            }
        }
        
        // Asociamos nueva raiz con el resto del arbol clonado       
        SMTreeNode rootTreeClone = new SMTreeNode(newParent.getObject());          
        rootTreeClone.setFirstChild(mapClone.get(from));
        rootTreeClone.setLastChild(mapClone.get(from));
        
        indexClone.add(rootTreeClone);
        
        // y creamos finalmente el arbol clonado:
        SMTree treeClone = new SMTree();      
        treeClone.setRoot(rootTreeClone);
        treeClone.setMapa(indexClone);
        
        return treeClone;
    }
    
    public SMTree cloneSubTree(T from,T to,T newParent)
    {
        if(from==null || to==null || newParent==null)
            throw new NullPointerException("");
        
        return cloneSubTree(mapa.get(from),mapa.get(to), new SMTreeNode(newParent));
    }
    
    public SMTree cloneSubTree(SMTreeNode from, SMTreeNode to, SMTreeNode newParent)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
