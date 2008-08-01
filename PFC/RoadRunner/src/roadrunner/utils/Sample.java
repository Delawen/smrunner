package roadrunner.utils;

import SMTree.SMTreeNode;
import XMLTokenizer.TokenTypePersistanceException;
import java.net.URISyntaxException;
import roadrunner.iterator.EdibleIterator;
import SMTree.utils.Enclosure;
import SMTree.utils.Kinship;
import SMTree.SMTree;
import java.util.List;
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
import java.util.Iterator;
import roadrunner.iterator.webPageBackwardIterator;
import roadrunner.iterator.webPageForwardIterator;

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

            tokens.add(new DOF());
            // Creamos la pagina web
            URIWebPage webpage = new URIWebPage(new File(page).toURI().toString());

            // Creamos el tokenizador
            Tokenizer tokenizer = new Tokenizer(hierarchy);

            // Tokenizo
            ITokenizedWebPage twp = tokenizer.tokenize(webpage);

            for (IToken token : twp) 
            {
                XMLTokenizer.Token t = (XMLTokenizer.Token) token;
                t = new XMLTokenizer.Token(quitarBlanco(t.getText()), t.getTokenType(), t.getLength(), t.getPosition());
                if(!t.getText().replaceAll(" ", "").equals(""))
                    tokens.add((Token) limpiar(t));
            }

            tokens.add(new DOF());
        } catch (TokenizerException ex) {
            Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }

    
    private Token limpiar(XMLTokenizer.Token t)
    {
        Token resultado;
        if(t.getTokenType().getName().equals("TAG"))
            resultado = new Tag(t.getText());
        else
            resultado = new Text(t.getText());
        
        return resultado;
    }
    
    public Wrapper getAsWrapper() 
    {
        SMTree<Item> tree = new SMTree<Item>(new SMTreeNode(new Tuple()));
        for(Item i : this.tokens)
            tree.addObject(i, tree.getRoot(), Kinship.CHILD);
        return new Wrapper(tree);
    }
    public Wrapper getAsWrapper(Token from, Enclosure inclusionFrom, Token to, Enclosure inclusionTo, Item newParent)
    {
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
        
        if(!it.hasNext() && t!=to)
            return null;
        
        //añadimos 'to'
        tree.addObject((Token)it.next(), tree.getRoot() , Kinship.CHILD);
        
        return new Wrapper(tree);
    }

    private String quitarBlanco(String text)
    {
        String cadenas[] = {System.getProperty("line.separator"), "\r", "\t", "\n"};

        for(String cadena:cadenas)
            text = text.replaceAll(cadena, "");

        return text;

    }
    
    public interface webPageIterator extends EdibleIterator
    {
        public void init(List<Token> list);
    }
    
    public EdibleIterator iterator(Class iteratorClass)
    {
        webPageIterator wpi=null;
        try
        {
            wpi = (webPageIterator) iteratorClass.newInstance();
            wpi.init(tokens);
        } catch (InstantiationException ex) {
            Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (EdibleIterator) wpi;
    }
    
    public Token getToken(int index)
    {
        if(index >= tokens.size())
            throw new IndexOutOfBoundsException("Accediendo a un indice inexistente en el Sample ");
        
        return tokens.get(index);
    }
    
 public Wrapper cloneSubWrapper(Token firstTokenSquare, Token lastTokenSquare, Item parent, DirectionOperator d)
    {
        if(d == DirectionOperator.UPWARDS)
        {
            Token temp = firstTokenSquare;
            firstTokenSquare = lastTokenSquare;
            lastTokenSquare = temp;
        }
        Item root; 
        SMTree<Item> treeCloned = null;
        SMTreeNode<Item> rootNode;
        
        if(!tokens.contains(firstTokenSquare) || !tokens.contains(lastTokenSquare))
            throw new IllegalArgumentException("Los delimitadores no existen");
        
        try {
            root = (Item) parent.clone();
            treeCloned = new SMTree<Item>(root);
            rootNode = treeCloned.getNode(root);

            Iterator<Token> it = tokens.iterator();
            Item i = null;
            while (it.hasNext() && firstTokenSquare != i)
                i = it.next();
            
            treeCloned.addObject(i, rootNode, Kinship.CHILD);

            while (it.hasNext() && i != lastTokenSquare) {                
                i = it.next();
                treeCloned.addObject(i, rootNode, Kinship.CHILD);
            }
            
            if(firstTokenSquare != lastTokenSquare)
                treeCloned.addObject(i, rootNode, Kinship.CHILD);
            
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Wrapper(treeCloned);
    }
    
    public int getNumToken()
    {
        return tokens.size();
    }

    public Token searchWellFormed(Token t, Enclosure tokenEnclosure, Token from, Enclosure tEnclosure, DirectionOperator d) {
                EdibleIterator itSample = null;
        
        if(DirectionOperator.DOWNWARDS == d)
            itSample = iterator(webPageForwardIterator.class);      
        else if(DirectionOperator.UPWARDS == d)
            itSample = iterator(webPageBackwardIterator.class);
        
        if(!itSample.goTo(from))
            return null;
        
        Token token=null;

        while(itSample.hasNext())
        {
            //Al ser un sample, sabemos que sólo hay un camino:
            token = (Token)itSample.next();
            
            if(t.match(token) && isWellFormed(from, tEnclosure, token, tokenEnclosure, d))
                return token;
        }
        return null;
    }
    
    public boolean isWellFormed (Token from, Enclosure inclusionFrom, Token to, Enclosure inclusionTo, DirectionOperator d) {
        
        if(from==null || to==null)
           throw new NullPointerException("");
        
        // las regiones vacias estaran bien formadas
        if(from==to && (inclusionFrom == Enclosure.NOT_ENCLOSED || inclusionTo == Enclosure.NOT_ENCLOSED))
            return true;
        
        boolean isWellFormed = true;
        Stack<Tag> openTags = new Stack(); 
        EdibleIterator it = null;
        
        if(d == DirectionOperator.DOWNWARDS)
            it = iterator(webPageForwardIterator.class);
        else if( d == DirectionOperator.UPWARDS)
            it = iterator(webPageBackwardIterator.class);

        //Si 'to' no esta incluido, no desechamos
        if(Enclosure.NOT_ENCLOSED == inclusionTo)
        {
            assert(it.goTo(to));
            to = (Token) it.previous();
        }
        
        assert(it.goTo(from));
            
        //Si 'from' no esta incluido, no desechamos
        if(Enclosure.NOT_ENCLOSED == inclusionFrom)
            from = (Token) it.next();
        
        //si miramos solo si un token esta bien formado y dicho token no es una etiqueta
        if(from==to && (!(from instanceof Tag)))
            return true;
        //si miramos solo si un token esta bien formado y dicho token es una etiqueta del tipo <.../>
        else if (from==to && ((Tag)from).isOpenTag() && ((Tag)from).isOpenTag())
            return true;
        else if(from==to)
            return false;
        
        if(d == DirectionOperator.DOWNWARDS)
        {
            Token t;
            do
            {
                t = (Token) it.next();
                if(t instanceof Tag && ((Tag)t).isOpenTag() && ((Tag)t).isCloseTag())
                    continue;
                else if(t instanceof Tag && ((Tag)t).isOpenTag())
                    openTags.push((Tag)t);
                else if (t instanceof Tag && ((Tag)t).isCloseTag() && !openTags.empty() && openTags.lastElement().isOpenTag() && openTags.lastElement().getContent().equals(t.getContent()))
                        openTags.pop();
                else if(!(t instanceof Text) && !(t instanceof Variable))
                    isWellFormed = false;

            } while(it.hasNext() && t!=to && isWellFormed);   
        }
        else if( d == DirectionOperator.UPWARDS)       
        {
            Token t;
            do
            {
                t = (Token) it.next();
                if(t instanceof Tag && ((Tag)t).isOpenTag() && ((Tag)t).isCloseTag())
                    continue;
                else if(t instanceof Tag && ((Tag)t).isCloseTag())
                    openTags.push((Tag)t);
                else if (t instanceof Tag && ((Tag)t).isOpenTag()  && !openTags.empty() && openTags.lastElement().isCloseTag() && openTags.lastElement().getContent().equals(t.getContent()))
                        openTags.pop();
                else if(!(t instanceof Text) && !(t instanceof Variable))
                    isWellFormed = false;

            } while(it.hasNext() && t!=to && isWellFormed);
        }
           
        if(!openTags.empty())
            isWellFormed=false;
        
        return isWellFormed; 
    }
}
