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
        return this == i;
    }
    

    @Override
    public Object clone() 
    {
        return new DOF();
    }
    
    @Override
    public String toString()
    {
        return "&DOF;";
    }
}
