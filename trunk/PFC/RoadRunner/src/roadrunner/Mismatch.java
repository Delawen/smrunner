package roadrunner;

import roadrunner.node.Item; 
import roadrunner.node.Token; 

/**
 *  Class Mismatch
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.20DF5F52-8F6C-FCD4-C3D0-4A5B9D83EB61]
// </editor-fold> 
class Mismatch {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.28507AA1-1063-5C8C-265A-2D0DB2EA68EB]
    // </editor-fold> 
    private Wrapper w;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.8095D1B7-84F0-B1B5-1180-1620A5FEC76A]
    // </editor-fold> 
    private Sample s;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4E508E60-4BE8-AA2B-BE12-40260A7219A5]
    // </editor-fold> 
    private Token token;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.6A751045-25EC-614F-CE2F-C0DF77F64B7A]
    // </editor-fold> 
    private Item node;

    /**
     *  Constructors
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.427658C6-1191-36DF-4083-89350220184A]
    // </editor-fold> 
    private Mismatch () 
    {
        super();
    }

    Mismatch(Wrapper w, Sample s, Item next, Token token) 
    {
        this();
        this.w = w;
        this.s = s;
        this.node = next;
        this.token = token;
    }

    /**
     *  Set the value of w
     *                       @param newVar the new value of w
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.01E56D69-3DAD-DE81-75E0-FB76D0A099C5]
    // </editor-fold> 
    void setWrapper (Wrapper w) 
    {
        this.w = w;
    }

    /**
     *  Get the value of w
     *                       @return the value of w
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3FD7D9D7-97AD-9969-B595-53B779E574DD]
    // </editor-fold> 
    Wrapper getWrapper () 
    {
        return w;
    }

    /**
     *  Set the value of s
     *                       @param newVar the new value of s
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.7B4CA25A-4E79-CC3F-458D-7A41DBE32CE3]
    // </editor-fold> 
    void setSample (Sample s) {
        this.s = s;
    }

    /**
     *  Get the value of s
     *                       @return the value of s
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.78553C79-1CE9-3A81-EC12-22E9D141DEFF]
    // </editor-fold> 
    Sample getSample () {
        return s;
    }

    /**
     *  Set the value of t
     *                       @param newVar the new value of t
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.95675F08-D761-DE9C-604C-0DACAB276FA6]
    // </editor-fold> 
    void setToken (Token t) {
        this.token = t;
    }

    /**
     *  Get the value of t
     *                       @return the value of t
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5E28F9F9-EF3E-4AC4-A404-1EFA3E5E543F]
    // </editor-fold> 
    Token getToken () {
        return token;
    }

    /**
     *  Set the value of n
     *                       @param newVar the new value of n
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5E6C5B63-F927-5965-9A31-6F456AA31841]
    // </editor-fold> 
    void setNode (Item node) 
    {
        this.node = node;
    }

    /**
     *  Get the value of n
     *                       @return the value of n
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.2289E4A1-6F63-0060-5153-EAE1B2F29A29]
    // </editor-fold> 
    Item getNode () {
        return node;
    }

}

