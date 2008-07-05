package roadrunner.node;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.43392123-E85C-8D5D-0464-7640817C4CF6]
// </editor-fold> 
public class Variable extends Token 
{
    
    public Variable()
    {
        setContent("&VARIABLE;");
    }

    /**
     * Hace matching si el objeto i es una variable o un texto, da igual cual.
     * @param i
     * @return boolean
     */
    public boolean match (Item i) 
    {
        if(i instanceof Text || i instanceof Variable)
            return true;
        return false;
    }
    
    /**
     * Comprueba si es una variable.
     * @param i
     * @return
     */
    @Override
    public boolean equals(Object i)
    {
        return (i instanceof Variable);
    }

    @Override
    public Object clone() 
    {
        return new Variable();
    }

}

