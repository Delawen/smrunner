package roadrunner.node;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.43392123-E85C-8D5D-0464-7640817C4CF6]
// </editor-fold> 
public class EOF extends Token 
{
    public EOF()
    {
        super();
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
        return (i instanceof EOF);
    }
    
    @Override
    public boolean equals(Object i)
    {
        if(i instanceof Item)
            return match((Item)i);
        else
            return false;
    }
    

    @Override
    public Object clone(Object o) 
    {
        if(o instanceof EOF)
            return new EOF();
        
        throw new ClassCastException();
    }
    
    public String toString()
    {
        return "&EOF;";
    }
}
