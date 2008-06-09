package roadrunner;

import roadrunner.Mismatch;
import roadrunner.node.Token;
import SMTree.*;
import java.util.Stack;
import roadrunner.node.*; 
import roadrunner.operator.DirectionOperator;
import roadrunner.operator.Operator; 
import tokenizador.*;

/**
 *  @author delawen
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.6DA19461-88AB-136F-23B7-1FB2AC471B20]
// </editor-fold> 
public class Wrapper {
    
    SMTree<Item> treeWrapper;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4C4FEE21-B6CA-E719-277C-03EDB616EFAF]
    // </editor-fold> 
    public Wrapper () {
        super();
        treeWrapper = new SMTree<Item>();
    }
    
    public Wrapper(Item rootItem)
    {
        this();
        treeWrapper.setRootObject(rootItem);
        
    
    }
    
    public Wrapper(Item rootItem,Item firstChildRoot)
    {
        this(rootItem);
        treeWrapper.addObject(firstChildRoot, treeWrapper.getRoot(), Kinship.CHILD);
    }
    
    public Wrapper(SMTree<Item> tree)
    {
        super();
        this.treeWrapper = tree;
    }
    
    
    public SMTree<Item> getTree()
    {
        return this.treeWrapper;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.CB10757A-C05C-4F36-13C5-A851167056BD]
    // </editor-fold> 
    public Mismatch eat (Sample s, DirectionOperator d) {
        return eat(s, s.getToken(0), treeWrapper.getRootObject(), d);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F61617E2-E6A1-70ED-161A-55D8DB23BFB6]
    // </editor-fold> 
    public boolean goTo (Item i) {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.25072B5A-1792-FB27-791C-BE9076EBAA29]
    // </editor-fold> 
    public Mismatch eat (Sample s) {
        return eat(s,DirectionOperator.DOWNWARDS);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E5C3243F-4177-9A70-094F-C76645D7AD05]
    // </editor-fold> 
    public boolean isWellFormed (Text from,Enclosure inclusionFrom, Text to,Enclosure inclusionTo) {
        
        if(from==null || to==null)
           throw new NullPointerException("");
        
        // las regiones vacias estaran bien formadas
        if(from==to && (inclusionFrom == Enclosure.NOT_ENCLOSED || inclusionTo == Enclosure.NOT_ENCLOSED))
            return true;
        
        boolean isWellFormed = true;
        Stack<Text> openTags = new Stack(); 
        WrapperIterator<Item> it = treeWrapper.iterator(new ForwardTokenIterator());

        //Si 'to' no esta incluido, no desechamos
        if(Enclosure.NOT_ENCLOSED == inclusionTo)
        {
            it.goTo(to);
            to = it.previous();
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

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D1F274E3-DD20-5A57-9D69-04D250213967]
    // </editor-fold> 
    public boolean substitute (Item from, Enclosure inclusionFrom, Item to, Enclosure inclusionTo, SMTree what) {
        return treeWrapper.substituteObject(from, inclusionFrom, to, inclusionTo, what);
    }
    
     public boolean substitute (Item from, Enclosure inclusionFrom, Item to, Enclosure inclusionTo, Wrapper what) {
        return treeWrapper.substituteObject(from, inclusionFrom, to, inclusionTo, what.treeWrapper);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9AAF649B-4F9D-8FA4-3FC3-287DCD953037]
    // </editor-fold> 
    public Mismatch eat (Sample s, Token t, Item n, DirectionOperator d) {
               //TODO Este metodo esta mal hasta que decidamos que hacemos con el sample.next()
        
        WrapperIterator<Item> itWrapper = null;
        Sample.webPageIterator itSample = null;
        Mismatch m = null;
        
        
        /* Segun el recorrido creamos un tipo de iterador */
        if(DirectionOperator.DOWNWARDS == d)
        {
            itWrapper = treeWrapper.iterator(new ForwardTokenIterator());
            itSample = s.iterator(Sample.webPageForwardIterator.class);
        }
        else if(DirectionOperator.UPWARDS == d)
        {
            itWrapper = treeWrapper.iterator(new BackwardTokenIterator());
            itSample = s.iterator(Sample.webPageBackwardIterator.class);
        }
 
        itWrapper.goTo(n);
        itSample.goTo(t);
        
        Token tokenSample = null;
        Item itemWrapper = null;

        /*mientras no se produzca un mismatch y no me coma entero el sample o el wrapper*/
        while(itSample.hasNext() && itWrapper.hasNext() && m==null)
        {
            tokenSample = itSample.next();
            
            if(!itWrapper.isNext(tokenSample))
            {
                itemWrapper = itWrapper.next();
                m = new Mismatch(this,s, itemWrapper, tokenSample);     
            }
        }
       
        /* Si no se ha producido un mismatch pero si el sample o el wrapper se han acabado, 
         * entonces lanzamos otro mismatch
         */
        if(itWrapper.hasNext() && m==null && tokenSample instanceof Text && ((Text)tokenSample).isEOF())    
            m = new Mismatch(this,s, itWrapper.next(),tokenSample);
        else if(itSample.hasNext() && m==null && itemWrapper instanceof Text && ((Text)itemWrapper).isEOF())
            m = new Mismatch(this,s, itemWrapper,itSample.next());
        
        return m;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F925FF52-82A2-AFA9-A17C-8A6B6DE5DDAF]
    // </editor-fold> 
    public Token search (Token t, Token from,int occurrence, DirectionOperator d) {
        
        WrapperIterator<Item> itWrapper = null;
        
        if(DirectionOperator.DOWNWARDS == d)
            itWrapper = treeWrapper.iterator(new ForwardTokenIterator());    
        else if(DirectionOperator.UPWARDS == d)
            itWrapper = treeWrapper.iterator(new BackwardTokenIterator());
        
        if(!itWrapper.goTo(from))
            return null;
        
        Token token=null;
        boolean find=false;

        while(itWrapper.hasNext() && !find)
        {
            token = (Token) itWrapper.next();
            
            if(t.equals(token))
                find = true;         
        }
        
        if(!find)
            token=null;
        
        return token;
    }
    
    public Wrapper cloneSubWrapper(Item from)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Wrapper cloneSubWrapper(Item from, Item newParent)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Wrapper cloneSubWrapper(Item from, Item to, Item newParent)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }  
}

