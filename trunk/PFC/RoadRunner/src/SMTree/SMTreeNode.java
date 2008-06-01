package SMTree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.FDBCF282-0CF6-60DB-9939-F35F4F109953]
// </editor-fold> 
public class SMTreeNode<T> implements Cloneable{

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F2BDB032-1BF2-F740-4A0F-B6C59E8C0E12]
    // </editor-fold> 
    private SMTreeNode parent = null;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.11568A38-ECA0-535B-42DD-C74F5F067B3F]
    // </editor-fold> 
    private SMTreeNode next = null;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3A5C6D89-B390-A73C-1F0D-B4740906244B]
    // </editor-fold> 
    private SMTreeNode previous = null;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9DA75598-FFDE-4940-6B66-01CB1DB3B3D1]
    // </editor-fold> 
    private SMTreeNode firstChild = null;
    
    private SMTreeNode lastChild = null;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.10BFAFC1-1956-2F7A-137A-38BB75A81DB5]
    // </editor-fold> 
    private T object;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E1CE02DE-51A9-6F58-26C2-BA501BEDDA96]
    // </editor-fold> 
    public SMTreeNode (T o) 
    {
        super();
        this.object = o;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.D8EE3F52-33B1-55FB-F65E-CBFE457DD40F]
    // </editor-fold> 
    public SMTreeNode getNext () {
        return next;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.191680D0-220B-D002-F434-91A154FEC95C]
    // </editor-fold> 
    public void setNext (SMTreeNode val) {
        this.next = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.E56966CF-AA94-2407-2DB7-A7969E95BD2B]
    // </editor-fold> 
    public SMTreeNode getParent () {
        return parent;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.0EBE5426-1BAA-6743-47C8-66783400D1D1]
    // </editor-fold> 
    public void setParent (SMTreeNode val) {
        this.parent = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.6DFD33D0-4A23-E81A-584C-B835713765BC]
    // </editor-fold> 
    public SMTreeNode getPrevious () {
        return previous;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.6B560452-C1CB-27E0-AF9E-92B9BB9F1B84]
    // </editor-fold> 
    public void setPrevious (SMTreeNode val) {
        this.previous = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.87BEAA81-A8EE-82D9-4314-44434A4BE071]
    // </editor-fold> 
    public T getObject () {
        return object;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.C0CEDECD-4979-F117-C2CB-39A93275A8E7]
    // </editor-fold> 
    public void setObject (T val) {
        this.object = val;
    }

    public SMTreeNode getLastChild() {
        return lastChild;
    }

    public void setLastChild(SMTreeNode lastChild) {
        this.lastChild = lastChild;
    }

    public SMTreeNode getFirstChild() {
        return firstChild;
    }

    public void setFirstChild(SMTreeNode firstChild) {
        this.firstChild = firstChild;
    }
    
    

    //Clonación de Nodos:
    @Override
    public SMTreeNode clone() throws CloneNotSupportedException
    {
        
        SMTreeNode clon = (SMTreeNode)super.clone();
        clon.setObject(getClone(clon.getObject()));
        return clon;
    }
    
    /**
     * Devuelve un clon del objeto recibido como parametro
     */
    private Object getClone(Object o) throws CloneNotSupportedException {
            Object clon = null;
            try {
                    Method method = o.getClass().getMethod("clone", null);
                    clon = method.invoke(o, null);
            } catch (IllegalAccessException ex1) {
                    throw new CloneNotSupportedException(
                                    "El metodo clone no es publico (" + ex1.toString() + ")");
            } catch (NoSuchMethodException ex2) {
                    throw new CloneNotSupportedException(
                                    "El objeto no implementa la interfaz Cloneable ("
                                                    + ex2.toString() + ")");
            } catch (InvocationTargetException ex3) {
                    throw new CloneNotSupportedException(
                                    "Error inesperado al llamar al metodo clone ("
                                                    + ex3.toString() + ")");
            }
            return clon;
    }

    @Override
    public int hashCode() {
        int hash = 2;
        hash = 13 * hash + (this.parent != null ? this.parent.hashCode() : 0);
        hash = 13 * hash + (this.object != null ? this.object.hashCode() : 0);
        return hash;
    }
    
    //Comprueba si las raíces de los dos nodos son iguales
    //Pero no comprueba las referencias, ¿mejor por hashcode?
    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof SMTreeNode))
            return false;
        
        SMTreeNode node = (SMTreeNode)o;
        if(!node.getObject().equals(this.getObject())
                || node.firstChild != this.firstChild
                || node.lastChild != this.lastChild
                || node.next != this.next
                || node.previous != this.previous
                || node.parent != this.parent)
            return false;
        return true;
    }
    
    @Override
    public String toString()
    {
        return "[Node: " + this.getObject().toString() + "]";
    }
}

