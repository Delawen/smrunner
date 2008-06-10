package tokenizador;

import roadrunner.node.Token;


/**
 *  @author delawen
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.5242FBB7-E58C-C3DB-F2FC-C8B76765100A]
// </editor-fold> 
public interface iTokenizedWPIterator {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.77193F82-EB1C-EE22-78B1-F38E2B42834D]
    // </editor-fold> 
    public boolean hasNext ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.7D8643FB-ACF8-14C2-3AFE-3454A51C82C0]
    // </editor-fold> 
    public boolean hasPrevious ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D910E543-D766-82DF-4897-548EDAAC6D37]
    // </editor-fold> 
    public Token next ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.48DBA1B6-D6C4-4343-E3DD-6A46DCAA00F7]
    // </editor-fold> 
    public Token previous ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B75482A0-45DE-2167-6C8C-6B793FFC6BD6]
    // </editor-fold> 
    public int nextIndex ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D2A5961C-74E1-4FAA-0C76-CB0F69E22C44]
    // </editor-fold> 
    public int previousIndex ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5F7C7128-9CB4-7707-5810-6A6138C5F1F8]
    // </editor-fold> 
    public void goBefore (int index);

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.0BE1C1BB-B5BA-F586-AD11-7A94CF42A29A]
    // </editor-fold> 
    public void goAfter (int index);

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.457A664B-E1F5-02A2-DCAF-9C9EEB5C5228]
    // </editor-fold> 
    public void goStart ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.67B204FD-9501-DF2B-C203-653069DB601B]
    // </editor-fold> 
    public void goEnd ();

}

