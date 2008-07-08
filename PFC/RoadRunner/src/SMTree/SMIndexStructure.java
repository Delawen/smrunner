package SMTree;

import SMTree.*;
import java.util.HashMap;
import java.util.Map; 
import roadrunner.RoadRunner;


public class SMIndexStructure<T> implements Cloneable
{

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.CE65FD70-4A46-83CF-5C5D-7BFCCBE9B1D3]
    // </editor-fold> 
    private Map<T, SMTreeNode> index;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.39CFF62F-661A-FF17-EEF1-5EA446564A20]
    // </editor-fold> 
    public SMIndexStructure () {  
        super();
        this.index = new HashMap<T, SMTreeNode>();
    }

    SMIndexStructure(SMTreeNode<T> root) 
    {
        this();
        this.add(root);
    }
    
    public Map<T, SMTreeNode> getIndex()
    {
        return index;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.39A569BB-6010-5CE9-E1AA-14191C8692ED]
    // </editor-fold> 
    public boolean add (SMTreeNode<T> n)
    {
        if(index == null || n == null)
            throw new NullPointerException("");
          
        // Si el nodo (valor) no esta contenido:
        if(!index.containsValue(n))
            index.put(n.getObject(), n);
        // Si el nodo (valor) ya existe en el Map, pero con Nodo.Object distinto
        else if( index.get(n.getObject()) != n)
        {
            RoadRunner.debug("SMIndexStructure.Add: El nodo que quiero insertar [n:"+n+",o:"+n.getObject()+"] en el Map ya existe y" +
                    " [n:"+n+",o:"+index.get(n.getObject())+"]tiene un valor distinto del que quiero insertar",RoadRunner.ExitLevel.CONTINUE);
            return false;
        }
        //El nodo existe ya en el Mapa, con el mismo Nodo.Object, por tanto no hacemos nada
        return true;
        
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9F4FE40A-7235-9126-3349-3505A62029CA]
    // </editor-fold> 
    public void remove (T item) 
    {
        if(this.index == null)
            throw new NullPointerException("El indice del arbol es nulo");
        
        index.remove(item);
        
    }
    
    public void remove(SMTreeNode<T> node)
    {
        index.remove(node.getObject());  
    }
    

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.820DE30C-34EB-078E-9DA7-41F217F0EFCD]
    // </editor-fold> 
    public SMTreeNode<T> get (T item) 
    {
        if(this.index == null)
            throw new NullPointerException("");
        
        return (SMTreeNode<T>) this.index.get(item);
    }
    
    public boolean containsNode(SMTreeNode<T> n)
    {
        return index.containsValue(n);
    }
    
    public boolean containsObject(T o)
    {
        return index.containsKey(o);
    }

    void add(SMIndexStructure mapa) {    
        index.putAll(mapa.getIndex());     
    }
}

