package roadrunner;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import roadrunner.node.Token;  
import tokenizador.iTokenizedWPIterator; 

public class Sample{
    
    private List<Token> tokens;

    public Sample(String page)
    {
        tokens = new LinkedList<Token>();
        iTokenizedWPIterator tp = new iTokenizedWPIterator(page); //TODO
        
        TokenJuanma tj;
        TokenNuestro t;

        while(tp.hasNext())
        {
            tj = tp.next();
            t = tj.convertirAnuestroToken();
            tokens.add(t);
        }
        
        t = new Token("&EOF;");
        
        tokens.add(t);
    }
    
    public webPageIterator iterator(Class iteratorClass)
    {
        return (webPageIterator) iteratorClass.newInstance();
    }
    
    public Token getToken(int index)
    {
        if(index >= tokens.size())
            throw new IndexOutOfBoundsException("Accediendo a un indice inexistente en el Sample ");
        
        tokens.get(index);
    }
    
    public interface webPageIterator extends ListIterator<Token>
    {
        public void goTo(Token t);
    }
    
    
    public class webPageForwardIterator implements webPageIterator
    {
        private ListIterator<Token> it = tokens.listIterator();
        
        public void goTo(Token t)
        {
            it = tokens.listIterator();
            while(it.hasNext())
            {
                if(t==it.next())
                {
                    it.previous();
                    break;
                }
            }
        }

        public boolean hasNext() {
            return it.hasNext();
        }

        public Token next() {
            return it.next();
        }

        public boolean hasPrevious() {
            return it.hasPrevious();
        }

        public Token previous() {
            return it.previous();
        }

        public int nextIndex() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int previousIndex() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void set(Token arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void add(Token arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    
    }


    public class webPageBackwardIterator implements webPageIterator
    {
        private ListIterator<Token> it = tokens.listIterator();
        
        public void goTo(Token t)
        {
            it = tokens.listIterator();
            while(it.hasNext())
            {
                if(t==it.next())
                {
                    it.next();
                    break;
                }
            }
        }

        public boolean hasNext() {
            return it.hasPrevious();
        }

        public Token next() {
            return it.previous();
        }

        public boolean hasPrevious() {
           return it.hasNext();
        }

        public Token previous() {
           return it.next();
        }

        public int nextIndex() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int previousIndex() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void set(Token arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void add(Token arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
