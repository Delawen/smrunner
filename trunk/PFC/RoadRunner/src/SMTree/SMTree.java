package SMTree;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
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
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.EC682BB2-DE7A-1448-A23F-DC7E2A17D347]
    // </editor-fold> 
    public boolean addSMTreeNode (SMTreeNode n, SMTreeNode where, Kinship k) {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F23AC1EB-AD5A-6B5E-F74D-5AE361DE4190]
    // </editor-fold> 
    public boolean removeSMTreeNode (SMTreeNode<T> n) 
    {
        //Actualizamos las referencias de siguiente y anterior
        n.getPrevious().setNext(n.getNext());
        n.getNext().setPrevious(n.getPrevious());
        
        //Si el nodo es el primer y/o último hijo de un padre, también hay que actualizar esas referencias
        if(n.getParent().getFirstChild().equals(n))
            n.getParent().setFirstChild(n.getNext());
        if(n.getParent().getLastChild().equals(n))
            n.getParent().setLastChild(n.getPrevious());
        
        //Si tiene hijos, se eliminan todos sus hijos (¿basta con borrarlos del mapa?)
        //Por si acaso, hago recursión:
        SMTreeNode<T> hijo = n.getFirstChild();
        while(hijo != null)
        {
            // CUIDAO!!!
            removeSMTreeNode(hijo);
            hijo = hijo.getNext();
        }
        
        //Eliminamos el nodo del indice
        this.mapa.remove(hijo.getObject());
        
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
        // Si el nodo a borrar no tiene hijos
        if(n.getLastChild() == null && n.getFirstChild() == null)
        {
            mapa.remove(n);
            n.getPrevious().setNext(n.getNext());
            n.getNext().setPrevious(n.getPrevious());
         
            return true;
        }
            
        SMTreeNode nodeAux;
        
        nodeAux = n.getLastChild();
        
        while(n != nodeAux)
        {
            //Si tiene hijos, buscamos a su último hijo
            if(nodeAux.getLastChild() != null) 
                nodeAux = nodeAux.getLastChild();
            // Si es un nodo hoja
            else if(nodeAux.getLastChild() == null && nodeAux.getFirstChild() == null)
            {
                //nodoAux perdio anteriormente a su nodo.next 
                nodeAux.setNext(null);
                
                // borramos a nodoAux del mapa
                mapa.remove(nodeAux);
                
                //si nodoAux no tiene previo, es porque será el hijo más a la izda.
                if(nodeAux.getPrevious() == null)
                {
                    //nos vamos al padre
                    nodeAux = nodeAux.getParent();
                    
                    //dejamos al hijo huerfano
                    nodeAux.getFirstChild().setParent(null);
                    
                    //el padre ha dejado de tener hijos
                    nodeAux.setFirstChild(null);
                    nodeAux.setLastChild(null);
                }
                else
                {
                    //este hijo será huerfano
                    nodeAux.setParent(null); 
                    
                    /*En este momento sólo su hermano anterior tendra una referencia a este nodo*/
                    
                    //nos vamos al hermano
                    nodeAux = nodeAux.getPrevious();
                }
                
                
            }
        }
        
        mapa.remove(n);
        n.getPrevious().setNext(n.getNext());
        n.getNext().setPrevious(n.getPrevious());
         
        return true;
    }
    
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D6BCC3C4-38C4-91FA-BCA0-6B5FE88BA618]
    // </editor-fold> 
    public boolean substitute (SMTreeNode<T> from, SMTreeNode<T> to, SMTree<T> n) 
    {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.024EDDB9-9F2B-3FDF-9CF5-E955ADB3ACBE]
    // </editor-fold> 
    public boolean substituteObject (T from, T to, T by) 
    {
        SMTreeNode<T> f = this.mapa.get(from);
        SMTreeNode<T> t = this.mapa.get(to);
        SMTree<T> b = new SMTree<T>(new SMTreeNode<T>(by));
        return substitute(f, t, b);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A75CE315-A8D3-C755-EC0F-EF80CB8DAFEE]
    // </editor-fold> 
    public boolean removeObject (T o) 
    {
        return removeSMTreeNode(this.mapa.get(o));
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
