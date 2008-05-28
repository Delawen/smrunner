package SMTree;

import java.util.HashMap;
import java.util.Map; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.E4C9EC8F-3F9B-C977-74A3-467F7EC114A6]
// </editor-fold> 
public class SMIndexStructure<T> 
{

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.CE65FD70-4A46-83CF-5C5D-7BFCCBE9B1D3]
    // </editor-fold> 
    private Map<SMTreeNode, T> index;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.39CFF62F-661A-FF17-EEF1-5EA446564A20]
    // </editor-fold> 
    public SMIndexStructure () {
    }

    SMIndexStructure(SMTreeNode<T> root) 
    {
        super();
        this.index = new HashMap<SMTreeNode, T>();
        this.add(root);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.39A569BB-6010-5CE9-E1AA-14191C8692ED]
    // </editor-fold> 
    public void add (SMTreeNode<T> n) 
    {
        if(this.index == null)
            this.index = new HashMap<SMTreeNode, T>();
        this.index.put(n, n.getObject());
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9F4FE40A-7235-9126-3349-3505A62029CA]
    // </editor-fold> 
    public void remove (T item) 
    {
        if(this.index != null)
            this.index.remove(item);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.820DE30C-34EB-078E-9DA7-41F217F0EFCD]
    // </editor-fold> 
    public SMTreeNode<T> get (T item) 
    {
        if(this.index == null)
            return null;
        return (SMTreeNode<T>) this.index.get(item);
    }
}

