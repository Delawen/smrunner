package tokenizador;

import java.util.List;
import roadrunner.node.Item; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.23E7CDAA-7F7B-F487-3A75-0CCBCE12C31C]
// </editor-fold> 
public class Token extends Item implements iToken {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9F7698EC-F107-2F48-D68A-4CFF9B3D5644]
    // </editor-fold> 
    public boolean match (Item i) {
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.FCB8ECD9-3091-F9E3-ACBD-7632050A729B]
    // </editor-fold> 
    public boolean equalType (iToken token) {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.7BFD433B-B7D1-D4F9-1267-C92D86D1B4C9]
    // </editor-fold> 
    public List<Token> generalize () {
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.DFAF83DC-A672-968D-62F2-377D5BD61943]
    // </editor-fold> 
    public int match (String text) {
        return 0;
    }

}

