package SMTree;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 

import roadrunner.RoadRunner.ExitLevel;

// #[regen=yes,id=DCE.BDD5EA36-857A-98F5-E253-BFF7C656B696]
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
                    where.setLastChild(n);
                }
                
                //le decimos a 'n' que 'where' es su padre :-O
                n.setParent(where);
                break;
            case RIGHTSIBLING:
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
                
            case LEFTSIBLING:
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
        }
        
        // Y añadimos todos los nodos los descendientes de 'n'         
        
        
        //TODO: crear un iterador que recorrar el a partir de un nodo(lo considere raiz) todos sus descendientes
        //TODO mapa.add(todos los hijos)
        
        return true;
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
        
        /* 
        // Si el nodo a borrar no tiene hijos
        if(n.getLastChild() == null && n.getFirstChild() == null)
        {
            mapa.remove(n);
            n.getPrevious().setNext(n.getNext());
            n.getNext().setPrevious(n.getPrevious());
         
            return true;
        }
         * 
         * */
            
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
    public boolean substitute (SMTreeNode<T> from, Enclosure inclusionFrom, 
            SMTreeNode<T> to, Enclosure inclusionTo, SMTree<T> tree) 
    {
        //TODO: ¿permitimos arbol vacio? puede ser interesante...
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
        
        /**
         * Regiones vacias:
         * 1. (from,to) siendo from==to
         * 2. [from,to) siendo from==to
         * 3. (from,to] siendo from==to
         * 4. from == null
         * 5. to == null
         */
        if( (from==to && (inclusionFrom == Enclosure.NOT_ENCLOSED || inclusionTo == Enclosure.NOT_ENCLOSED))
                || from==null || to==null)
        {
            roadrunner.RoadRunner.debug("Región vacia al sustituir",ExitLevel.WARNING);
            return false;
        }
        
        
        // from y to deben pertenecer al mismo nivel del arbol
        if(from.getParent() != to.getParent())
        {
            roadrunner.RoadRunner.debug("La región a sustituir no forma parte del mismo nivel.",ExitLevel.SLEEPandEXIT);
            return false;
        }
        
        
        /* Borramos [desde,hasta), el hasta no incluido*/
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
        
        addSubSMTree(tree, where, whereKinship);
   
        return true;
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
        return true;
    }


    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.01E67DBC-7BE9-89D2-7807-C5806F837A45]
    // </editor-fold> 
    public IteratorStrategy iterator () {
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.BE3836CA-6B50-60BB-11D2-D9C2EFA03088]
    // </editor-fold> 
    public IteratorStrategy iterator (String type) {
        return null;
    }
    
    @Override
    public SMTree clone() throws CloneNotSupportedException
    {
        SMTree clon = (SMTree)super.clone();
        
        return clon;
    }
    
    public boolean equals(Object o)
    {
        return false;
    }

    public SMIndexStructure getMapa() {
        return mapa;
    }

    public void setMapa(SMIndexStructure mapa) {
        this.mapa = mapa;
    }

}
