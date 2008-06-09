package roadrunner.node;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.43392123-E85C-8D5D-0464-7640817C4CF6]
// </editor-fold> 
public class Text extends Item 
{
    public enum Type {OPENTAG,CLOSETAG,TEXT,EOF};
    public Type type;

    public Text(String text)
    {
        super();
        this.setContent(text);
    }
    /**
     * Comprueba si dos Text hacen matching.
     * 
     * @param i
     * @return boolean
     */
    @Override
    public boolean match (Item i) 
    {
        //Si son dos textos iguales:
        if(this.equals(i))
            return true;
        
        //Si el item i es una variable:
        if(i instanceof Variable)
            return true;
        
        //Si no, no hacen matching:
        return false;
    }
    
        @Override
    public void setContent(String c){
        
        if(c.startsWith("</") && c.endsWith(">"))
            type = Type.CLOSETAG;
        else if(c.startsWith("<") && c.endsWith(">"))
            type = Type.OPENTAG;
        else if(c.equalsIgnoreCase("&EOF;"))
            type = Type.EOF;
        else
            type = Type.TEXT;          
            
       content = c;
    }
    
    /**
     * Comprueba si es un objeto item. Si lo es, comprueba que sea de tipo texto. 
     * Si lo es, comprueba que tengan el mismo texto.
     * 
     * @param i
     * @return boolean
     */
    @Override
    public boolean equals(Object i)
    {
        if(i instanceof Text)
            return ((Text)i).getContent().equals(this.getContent());
        return false;
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
    public boolean isOpenTagOf(Text t)
    {
        return areOpenCloseTags(this, t);
    }
    
    public boolean isCloseTagOf(Text t)
    {
        return areOpenCloseTags(t, this);
    }
    
    
    private boolean areOpenCloseTags(Text open, Text close)
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
}
