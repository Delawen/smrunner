package roadrunner.node;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.43392123-E85C-8D5D-0464-7640817C4CF6]
// </editor-fold> 
public class Text extends Token 
{

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
    public void setContent(String c)
    {
       this.content = c;
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
    

    @Override
    public Object clone() 
    {
        return new Text(((Text)this).getContent());
    }
    
    @Override
    public String toString()
    {
        return this.content;
    }
}
