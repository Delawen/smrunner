package tokenizador;

import roadrunner.node.Token;
import java.util.List;


/**
 *  @author delawen
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.A156608E-7228-8B9E-AD43-72D4A45BCD8C]
// </editor-fold> 
public interface iToken {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.7B731232-9293-3D83-5F28-F16D24820C0F]
    // </editor-fold> 
    public boolean equalType (iToken token);

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D4F38F8C-3102-4350-F696-78DA757F4440]
    // </editor-fold> 
    public List<Token> generalize ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F359229F-AEAD-766E-EA34-8C2CF93555C4]
    // </editor-fold> 
    public int match (String text);

}

