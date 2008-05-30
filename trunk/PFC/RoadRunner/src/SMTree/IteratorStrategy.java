package SMTree;

import java.util.Iterator;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.97FCA26A-2C71-34F2-6B49-360CAF36102F]
// </editor-fold> 
public interface IteratorStrategy<T> extends Iterator
{

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9CBAB27E-DB77-5A22-806A-6641CA402434]
    // </editor-fold> 
    public SMTreeNode<T> next ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.530847C3-D5FF-A47B-87EC-2ACF19D100C4]
    // </editor-fold> 
    public boolean hasNext ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.2A4E1EC6-85DC-FB22-85E5-4B55B251D6F5]
    // </editor-fold> 
    public boolean goTo (SMTreeNode<T> Unnamed);

}

