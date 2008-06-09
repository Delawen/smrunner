package roadrunner;

import SMTree.Enclosure;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import roadrunner.node.Text;
import roadrunner.node.Token;  
import roadrunner.operator.DirectionOperator;
import tokenizador.iTokenizedWPIterator; 

public class Sample{
    
    private List<Text> tokens;

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
    
    public Token search (Token t, Token from, int occurrence, DirectionOperator d) {
        
        Sample.webPageIterator itSample = null;
        
        if(DirectionOperator.DOWNWARDS == d)
            itSample = iterator(Sample.webPageForwardIterator.class);      
        else if(DirectionOperator.UPWARDS == d)
            itSample = iterator(Sample.webPageForwardIterator.class);
        
        if(!itSample.goTo(from))
            return null;
        
        Token token=null;
        boolean find=false;

        while(itSample.hasNext() && !find)
        {
            token = itSample.next();
            
            if(t.equals(token))
                find = true;         
        }
        
        if(!find)
            token=null;
        
        return token;
    }
    
    public boolean isWellFormed (Text from, Enclosure inclusionFrom, Text to, Enclosure inclusionTo) {
        
        if(from==null || to==null)
           throw new NullPointerException("");
        
        // las regiones vacias estaran bien formadas
        if(from==to && (inclusionFrom == Enclosure.NOT_ENCLOSED || inclusionTo == Enclosure.NOT_ENCLOSED))
            return true;
        
        boolean isWellFormed = true;
        Stack<Text> openTags = new Stack(); 
        Sample.webPageIterator it = iterator(Sample.webPageForwardIterator.class);

        //Si 'to' no esta incluido, no desechamos
        if(Enclosure.NOT_ENCLOSED == inclusionTo)
        {
            it.goTo(to);
            to = (Text) it.previous();
        }
        
        it.goTo(from);
            
        //Si 'from' no esta incluido, no desechamos
        if(Enclosure.NOT_ENCLOSED == inclusionFrom)
            it.next();
        
        if(from==to && !from.isTag())
            return true;
        else if (from==to)
            return false;
        
        Text t;
        do
        {
            t = (Text)it.next();
            if(t.isOpenTag())
                openTags.push(t);
            else if (t.isCloseTag() && openTags.firstElement().isOpenTagOf(t))
                    openTags.pop();
            else
                isWellFormed = false;
            
        } while(it.hasNext() && t!=to && isWellFormed);
        
        if(!openTags.empty())
            isWellFormed=false;
        
        return isWellFormed;
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
        public boolean goTo(Token t);
    }
    
    
    public class webPageForwardIterator implements webPageIterator
    {
        private ListIterator<Text> it = tokens.listIterator();
        
        public boolean goTo(Token t)
        {
            boolean result=false;
            it = tokens.listIterator();
            while(it.hasNext() && !result)
            {
                if(t==it.next())
                {
                    it.previous();
                    result = true;
                }
            }
            return result;
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
        private ListIterator<Text> it = tokens.listIterator();
        
        public boolean goTo(Token t)
        {
            boolean result=false;
            it = tokens.listIterator();
            while(it.hasNext() && !result)
            {
                if(t==it.next())
                {
                    it.next();
                    result = true;
                }
            }
            return result;
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
