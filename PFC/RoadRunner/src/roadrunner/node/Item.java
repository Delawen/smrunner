package roadrunner.node;

import roadrunner.Repair; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.58D4B716-AA5D-2E4D-A254-A86DA56B6204]
// </editor-fold> 
public abstract class Item {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A7934DEE-09F4-EFB7-3875-3D4981E5612F]
    // </editor-fold> 
    private String content;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.89D66222-ECC5-B80F-EE49-C4BB9C82A106]
    // </editor-fold> 
    public abstract Repair match (Item i);

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.FADA7940-9BC6-3417-60EC-9E13655ECC8D]
    // </editor-fold> 
    public String getContent () {
        return content;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.5D8CB70A-C7A2-0FBE-BBF7-F4EEDB7E7C7D]
    // </editor-fold> 
    public void setContent (String val) {
        this.content = val;
    }

}

