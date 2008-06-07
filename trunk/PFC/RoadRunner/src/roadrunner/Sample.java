package roadrunner;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import roadrunner.node.Token; 
import tokenizador.TokenizedWebPage; 
import tokenizador.iTokenizedWPIterator; 

/**
 *  #[regen=yes,id=DCE.CBE0A245-4CF7-9D51-BA67-F984227CC7AF]
 *  </editor-fold>
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.CB19DE7C-E508-EC15-7F09-5611D1809B4F]
// </editor-fold> 
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
    
    public Iterator iterator(){
        return new webPageBackwardIterator();
        return new webPageForwardIterator();
    }
    
    public Token getToken(int index)
    {
        if(index >= tokens.size())
            throw new IndexOutOfBoundsException("Accediendo a un indice inexistente en el Sample ");
        
        tokens.get(index);
    }
    
    
    public class webPageForwardIterator implements ListIterator
    {
        private ListIterator it = tokens.listIterator();
        
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

        public Object next() {
            return it.next();
        }

        public boolean hasPrevious() {
            return it.hasPrevious();
        }

        public Object previous() {
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

        public void set(Object arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void add(Object arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    
    
    }


    public class webPageBackwardIterator implements ListIterator
    {
        private ListIterator it = tokens.listIterator();
        
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

        public Object next() {
            return it.previous();
        }

        public boolean hasPrevious() {
           return it.hasNext();
        }

        public Object previous() {
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

        public void set(Object arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void add(Object arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    
    
    }
}
