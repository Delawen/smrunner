package roadrunner.node;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.A0B1DB53-CDCE-00AB-46DE-4EC402488622]
// </editor-fold> 
public class List extends CompositeItem 
{
    private boolean accessed = false;
    
    public List()
    {
        setContent("+");
    }

    public boolean isAccessed() {
        return accessed;
    }

    public void setAccessed(boolean accessed) {
        this.accessed = accessed;
    }
}

