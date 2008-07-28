package roadrunner.node;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// #[regen=yes,id=DCE.43392123-E85C-8D5D-0464-7640817C4CF6]
// </editor-fold> 
public class Tag extends Token 
{

    public enum Type {OPEN,CLOSE, OPEN_AND_CLOSE};
    private Type type;
    private Map atributos;

    public Tag(String text)
    {
        super();
        this.atributos = new HashMap<String, String>();
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
        //TODO de momento no tienen en cuenta los atributos
        //Si son dos etiquetas del mismo tipo y el mismo contenido
        if(i instanceof Tag)
            if(((Tag)i).getType() == this.type)
                if(((Tag)i).getContent().equals(this.content))
                    return true;
                
        //Si no, no hacen matching:
        return false;
    }
    
        @Override
    public void setContent(String c){
        
        if(c.startsWith("</") && c.endsWith(">"))
        {
            this.type = Type.CLOSE;
            save(c.substring(2, c.length() - 1));
        }
        else if(c.startsWith("<") && c.endsWith("/>"))
        {
            this.type = Type.OPEN_AND_CLOSE;
            save(c.substring(1, c.length() - 2));
        }
        else if(c.startsWith("<") && c.endsWith(">"))
        {
            this.type = Type.OPEN;
            save(c.substring(1, c.length() - 1));
        }
        else 
            throw new RuntimeException("El contenido no es una etiqueta");
            
    }
        
        
    private void save(String contenido) 
    {
        String[] elems = contenido.split(" ");
        
        this.content = elems[0];
        
        for(int i = 1; i < elems.length; i++)
        {
            String elem = elems[i];
            
            int indice = elem.indexOf("=");
            if(indice < 0)
                throw new RuntimeException("El atributo no estaba en formato XHTML.");
            
            this.atributos.put(elem.substring(0, indice), elem.substring(indice +1));
        }
    }
    
    /**
     *
     * 
     * 
     * @param i
     * @return boolean
     */
    @Override
    public boolean equals(Object i)
    {
        if(i instanceof Tag)
            return (((Tag)i).getContent().equals(this.getContent())
                    && this.getType() == ((Tag)i).getType());
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
        return Type.OPEN == type || Type.OPEN_AND_CLOSE == type;
    }
    
    public boolean isCloseTag()
    {
        return Type.CLOSE == type || Type.OPEN_AND_CLOSE == type;
    }
    
   
    @Override
    public Object clone() 
    {
        Tag clon;
        if(this.isOpenTag())
            clon = new Tag("<" + getContent() + ">");
        else
            clon = new Tag("</" + getContent() + ">");
        return clon;
    }
    
    @Override
    public String toString()
    {
        String resultado = "<";
        
        if(this.isCloseTag() && !this.isOpenTag())
            resultado += "/";
        
        resultado += this.content;
        
        Iterator<String> it = this.atributos.keySet().iterator();
        while(it.hasNext())
        {
            String key = it.next();
            
            resultado += " " + key + "=" + this.atributos.get(key);
        }
        
        if(this.isCloseTag() && this.isOpenTag())
            resultado += "/>";
        else
            resultado += ">";
        
        return resultado;
    }
}
