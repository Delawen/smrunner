package tokenizador;

import java.util.List;
import roadrunner.node.Item; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.23E7CDAA-7F7B-F487-3A75-0CCBCE12C31C]
// </editor-fold> 
public class Token extends Item implements iToken {
    
    public enum Type {OPENTAG,CLOSETAG,TEXT,VARIABLE,EOF};
    public Type type;
    
    public Token(String content, Type t){
        super();
        setContent(content);
        setType(t);    
    }
    
    @Override
    public void setContent(String c){
        
        if(c.startsWith("</") && c.endsWith(">"))
            type = Type.CLOSETAG;
        else if(c.startsWith("<") && c.endsWith(">"))
            type = Type.OPENTAG;
        else if(c.equalsIgnoreCase("&EOF;"))
            type = Type.EOF;
        else if(c.equalsIgnoreCase("&VAR;"))
            type = Type.VARIABLE;
        else
            type = Type.TEXT;          
            
       content = c;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
    public boolean isOpenTag()
    {
        return Type.OPENTAG == type;
    }
    
    public boolean isCloseTag()
    {
        return Type.OPENTAG == type;
    }
    
    public boolean isEOF(){
        return Type.EOF == type;
    }
    
    public boolean isVariable(){
        return Type.VARIABLE == type;
    }
        
    public boolean isText(){
        return Type.TEXT == type;
    }
    
    public boolean isTag()
    {
        return Type.OPENTAG == type || Type.CLOSETAG == type;
    }
    
    /**
     * 
     * This es la etiqueta de apertura de la etiqueta de cierre t
     * 
     * @param t
     * @return
     */
    public boolean isOpenTagOf(Token t)
    {
        return areOpenCloseTags(this, t);
    }
    
    public boolean isCloseTagOf(Token t)
    {
        return areOpenCloseTags(t, this);
    }
    
    
    private boolean areOpenCloseTags(Token open, Token close)
    {
        if(!close.isCloseTag() || !open.isOpenTag())
            return false;
        
        /*
         * El "<" de la etiqueta de apertura aparece al principio y el ">" al final
         * Las etiquetas de apertura tienen como minimo 3 caracteres
         * El "</" de la etiqueta de cierre aparece al principio y el ">" al final
         * Las etiquetas de cierre tienen como minimo 4 caracteres
         */
        if(open.getContent().length()<2 || close.getContent().length()<3)
            return false;
        
        /*
         * En la etiqueta de apertura todo lo que vaya desde "<1234 " hasta el primer espacio sera la etiqueta en si
         * el resto se consideran atributos. Si no encontramos espacio es porque esa etiqueta no tiene atributos
         * 
         */
        int lengthTagName = open.getContent().indexOf(" ")-1; // <blabla at1=".." at2="..">
        
        //caso de etiqueta sin atributos.
        if(lengthTagName<0)
            lengthTagName = open.getContent().length()-2; // <blabla>
        
        /* Indices:
         * Apertura <1234 67 9>
         * Cierre </2345>   
         * */  
        
        return open.getContent().regionMatches(true,1,close.getContent(),2, lengthTagName);    
    }
    
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9F7698EC-F107-2F48-D68A-4CFF9B3D5644]
    // </editor-fold> 
    public boolean match (Item i) {
        
        Token t = (Token) i;
        boolean match = false;
        
        /*
         * Casos:
         * 1. Texto/etiqueta hace match con Texto/Etiqueta solo si tienen exactamente el mismo contenido
         * 2. Si uno de los tokens es Variable y el otro Texto, hacen matching
         * 3. Si ambos son Variables, hacen matching
         * 4. Si estamos ante el EOF en ambos tokes, hace matching
         */ 

        if( (this.isText() && t.isText())
            || (this.isTag() && t.isTag()) )
            match = t.getContent().equals(this.getContent());
        else if( (this.isVariable() || t.isVariable())
                 && (this.isText() || t.isText()))
            match = true;
        else if( (this.isVariable() && t.isVariable()))
            match = true;
        else if(this.isEOF() && t.isEOF())
            match = true;
        
        return match;
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

    @Override
    public boolean equals(Object i) {
        Token t = (Token) i;
        return t.getType()==this.getType() && t.getContent().equals(this.getContent());
    }

}

