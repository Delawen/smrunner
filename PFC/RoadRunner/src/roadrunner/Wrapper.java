package roadrunner;

import SMTree.*;
import java.util.Stack;
import roadrunner.node.*; 
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

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.CB10757A-C05C-4F36-13C5-A851167056BD]
    // </editor-fold> 
    public Mismatch eat (Sample s, Operator.Direction d) {
        return eat(s, s.get(0), treeWrapper.getRootObject(), d);
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
        return eat(s,Operator.Direction.DOWNWARDS);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E5C3243F-4177-9A70-094F-C76645D7AD05]
    // </editor-fold> 
    public boolean isWellFormed (Token from, Token to) {
        
        if(from==null || to==null)
           throw new NullPointerException("");
        
        if(from==to && !from.isTag())
            return true;
        else if (from==to)
            return false;
        
        boolean isWellFormed = true;
        
        Stack<Token> openTags = new Stack();
        
        WrapperIterator<Item> it = treeWrapper.iterator(new ForwardTokenIterator());

        it.goTo(from);
        Token t;
        do
        {
            t = it.next();
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
    public Mismatch eat (Sample s, Token t, Item n, Operator.Direction d) {
               //TODO Este metodo esta mal hasta que decidamos que hacemos con el sample.next()
        
        WrapperIterator<Item> itWrapper;
        iTokenizedWPIterator itSample;
        Mismatch m;
        
        
        /* Segun el recorrido creamos un tipo de iterador */
        if(Operator.Direction.DOWNWARDS == d)
        {
            itWrapper = treeWrapper.iterator(new ForwardTokenIterator());
            //TODO
            itSample = s.startIterator();
        }
        else if(Operator.Direction.UPWARDS == d)
        {
            itWrapper = treeWrapper.iterator(new BackwardTokenIterator());
            //TODO
            itSample = s.startIterator();
        }
 
        itWrapper.goTo(n);
        itSample.goTo(t);

        /*mientras no se produzca un mismatch y no me coma entero el sample o el wrapper*/
        while(itSample.hasNext() && itWrapper.hasNext() && m==null)
        {
            Token tokenSample = itSample.next();
            
            if(!itWrapper.isNext(tokenSample))
                m = new Mismatch(this,s, itWrapper.next(), tokenSample);     
        }
       
        /* Si no se ha producido un mismatch pero si el sample o el wrapper se han acabado, 
         * entonces lanzamos otro mismatch
         */
        if(itWrapper.hasNext() && m==null)    
            m = new Mismatch(this,s, itWrapper.next(),itSample.EOF);
        else if(itSample.hasNext() && m==null)
            m = new Mismatch(this,s, itWrapper.EOF,itSample.next());
        
        return m;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.2CC0A58C-7557-67C2-0019-AB130B76E324]
    // </editor-fold> 
    public SMTree search (Item i, Sample S, Operator.Direction d) {
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F925FF52-82A2-AFA9-A17C-8A6B6DE5DDAF]
    // </editor-fold> 
    public SMTree search (Item i, Wrapper w, Operator.Direction d) {
        return null;
    }

}

