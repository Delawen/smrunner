package roadrunner.node;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.58D4B716-AA5D-2E4D-A254-A86DA56B6204]
// </editor-fold> 
public abstract class Item implements Cloneable{

    /**
     * Contenido del item
     */
    protected String content;

    /**
     * Comprueba si los items hacen matching
     * 
     * @param i Item
     * @return boolean
     */
    public abstract boolean match (Item i);

    /**
     * Devuelve el contenido del item
     * 
     * @return String
     */
    public String getContent () {
        return content;
    }

    /**
     * Da valor al contenido del item.
     * 
     * @param String val
     */
    public void setContent (String val) {
        this.content = val;
    }
        
    /**
     * Devuelve el contenido del item.
     * 
     * @return String
     */
    @Override
    public String toString()
    {
        return this.content;
    }
    
    /**
     * Comprueba si i es un item. 
     * @param i
     * @return boolean
     */
    @Override
    public abstract boolean equals(Object i);
    public abstract Object clone();

}

