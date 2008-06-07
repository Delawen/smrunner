package tokenizador;

import roadrunner.node.Token;


/**
 *  @author delawen
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.505F348B-B794-B04E-2905-3263FD6867A2]
// </editor-fold> 
public interface TokenizedWebPage {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3A780DB0-61D2-5176-5F06-6AB3A953367B]
    // </editor-fold> 
    public Token get (int index);

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9636E0D8-335C-99FC-836A-23EB3A5846C6]
    // </editor-fold> 
    public int size ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.92E60726-194B-F4D1-659D-4D5B2DB1E89A]
    // </editor-fold> 
    public iTokenizedWPIterator startIterator ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D622E6F1-6F9F-043C-A812-C0A60B0D4728]
    // </editor-fold> 
    public iTokenizedWPIterator endIterator ();

}

