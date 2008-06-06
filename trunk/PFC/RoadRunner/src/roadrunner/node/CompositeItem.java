package roadrunner.node;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.347F322C-6009-0182-4FAC-E69415380E6C]
// </editor-fold> 
public class CompositeItem extends Item 
{
    /**
     * Comprueba si los items hacen matching.
     * 
     * @param i
     * @return boolean
     */
    @Override
    public boolean match (Item i) 
    {
        return this.equals(i);
    }
    
    /**
     * Comprueba si los items son iguales.
     * 
     * @param i
     * @return boolean
     */
    @Override
    public boolean equals(Object i)
    {
        if(!(i instanceof CompositeItem))
            return false;
        return ((Item)i).getContent().equals(this.getContent());
    }
}

