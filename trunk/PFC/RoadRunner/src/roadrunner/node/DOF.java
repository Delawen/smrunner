package roadrunner.node;

/**
 * Delimiter Of File
 * 
 * 
 * Start and End Token of every single sample.
 * @author delawen
 */
public class DOF extends Token 
{
    public DOF()
    {
        super();
    }
    /**
     * Checks if two tokens match.
     * 
     * @param i
     * @return boolean
     */
    @Override
    public boolean match (Item i) 
    {
        return (i instanceof DOF);
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
        if(o instanceof DOF)
            return new DOF();
        
        throw new ClassCastException();
    }
    
    public String toString()
    {
        return "&DOF;";
    }
}
