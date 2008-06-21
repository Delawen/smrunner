package roadrunner.utils;

import SMTree.SMTreeNode;
import XMLTokenizer.TokenTypePersistanceException;
import java.net.URISyntaxException;
import roadrunner.iterator.EdibleIterator;
import SMTree.utils.Enclosure;
import SMTree.utils.Kinship;
import SMTree.SMTree;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import roadrunner.node.*;  
import roadrunner.operator.DirectionOperator;
import Tokenizer.*;
import WebModel.URIWebPage;
import XMLTokenizer.TokenTypeHierarchy;
import XMLTokenizer.TokenTypePersistence;
import XMLTokenizer.Tokenizer;
import WebModel.*;
import java.io.File;
import java.util.LinkedList;
import java.io.File;
import Tokenizer.IToken;
import Tokenizer.ITokenizedWebPage;
import WebModel.URIWebPage;
import XMLTokenizer.TokenTypeHierarchy;
import XMLTokenizer.TokenTypePersistence;
import XMLTokenizer.Tokenizer;

public class Sample implements Edible{
    
    private List<Token> tokens;
    private TokenTypePersistence persistance;
    private TokenTypeHierarchy hierarchy;
    
    private Sample()
    {
        try {
            //creamos la jerarquia
            persistance = new TokenTypePersistence("src/tokenizador/RoadRunnerDTD.xml");
            hierarchy = persistance.load();
            this.tokens = new LinkedList<Token>();
        } catch (TokenTypePersistanceException ex) {
            Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Sample(String page)
    {
        this();
        try {

            // Creamos la pagina web
            URIWebPage webpage = new URIWebPage(new File(page).toURI().toString());

            // Creamos el tokenizador
            Tokenizer tokenizer = new Tokenizer(hierarchy);

            // Tokenizo
            ITokenizedWebPage twp = tokenizer.tokenize(webpage);

            for (IToken token : twp) {
                tokens.add((Token) limpiar((XMLTokenizer.Token)token));
            }

            tokens.add(new Text("&EOF;"));
        } catch (TokenizerException ex) {
            Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
        }
         
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
            //Al ser un sample, sabemos que sólo hay un camino:
            token = (Token)((java.util.List<Item>)itSample.next()).get(0);
            
            if(t.equals(token))
                find = true;         
        }
        
        if(!find)
            token=null;
        
        return token;
    }
    
    private Token limpiar(XMLTokenizer.Token t)
    {
        Text resultado = new Text(t.getText());
        
        //TODO limpiar los atributos
        
        return resultado;
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
            to = (Text) ((java.util.List<Item>)it.previous()).get(0);
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
    
    public Wrapper getAsWrapper(Token from, Enclosure inclusionFrom, Token to, Enclosure inclusionTo, Item newParent)
    {
        Wrapper w = null;
        boolean success = false;
        SMTree<Item> tree = null;
        EdibleIterator it = new webPageForwardIterator();
        
        if(inclusionFrom == Enclosure.NOT_ENCLOSED)
        {
            if(!it.goTo(from))
                throw new IllegalStateException("No se ha podido un wrapper del sample: Los indices son erroneos");
            it.next(); // desechamos from
            from = (Token)it.next();
        }
        if(inclusionTo == Enclosure.NOT_ENCLOSED)
        {
            if(!it.goTo(to))
                throw new IllegalStateException("No se ha podido un wrapper del sample: Los indices son erroneos");
            to = (Token)it.previous();
        
        }
        
        /**
         * Regiones vacias:
         * 1. (from,to) siendo from==to
         * 2. [from,to) siendo from==to
         * 3. (from,to] siendo from==to
         * 4. from == null
         * 5. to == null
         */
        if( (from==to && (inclusionFrom == Enclosure.NOT_ENCLOSED || inclusionTo == Enclosure.NOT_ENCLOSED))
                || from==null || to==null)
        {
            return null;
        }
        
        if(!it.goTo(from))
            return null;
        
        tree = new SMTree();
        tree.setRootObject(newParent);
        Token t;
        
        do
        {   
            t = (Token)it.next();
            tree.addObject(t, tree.getRoot() , Kinship.CHILD);
        }  while(it.hasNext() && t!=to);
        
        // TODO esto quiza sobre:
        if(!it.hasNext() && t!=to)
            return null;
        
        //añadimos 'to'
        tree.addObject((Token)it.next(), tree.getRoot() , Kinship.CHILD);
        
        return new Wrapper(tree);
    }
    
    public webPageIterator iterator(Class iteratorClass)
    {
        webPageIterator wpi=null;
        try {
            wpi = (webPageIterator) iteratorClass.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wpi;
    }
    
    public Token getToken(int index)
    {
        if(index >= tokens.size())
            throw new IndexOutOfBoundsException("Accediendo a un indice inexistente en el Sample ");
        
        return tokens.get(index);
    }
    
    public interface webPageIterator extends EdibleIterator
    {
        public boolean goTo(Token t);
    }
    
    
    public class webPageForwardIterator implements webPageIterator
    {
        private ListIterator<Token> it = tokens.listIterator();
        
        public boolean goTo(Token t)
        {
            boolean result=false;
            
            if(!tokens.contains(t))
                return false;
            
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

        public boolean goTo(Item objeto) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isNext(Item o) 
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setRootIterator(SMTreeNode<Item> root) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setTree(SMTree<Item> treeWrapper) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    
    }


    public class webPageBackwardIterator implements webPageIterator
    {
        private ListIterator<Token> it = tokens.listIterator();
        
        public boolean goTo(Token t)
        {
            boolean result=false;
            
            if(!tokens.contains(t))
                return false;
            
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

        public boolean goTo(Item objeto) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isNext(Item o) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setRootIterator(SMTreeNode<Item> root) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setTree(SMTree<Item> treeWrapper) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public Wrapper getAsWrapper(Token firstTokenOptional, Enclosure ENCLOSED, Token lastTokenOptional, Enclosure ENCLOSED0, Optional optional) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
