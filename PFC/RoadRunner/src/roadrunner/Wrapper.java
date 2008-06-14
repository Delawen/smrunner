package roadrunner;

import roadrunner.Mismatch;
import roadrunner.node.Token;
import SMTree.*;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Wrapper implements Edible{
    
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

    public Wrapper(Wrapper w) throws CloneNotSupportedException {
        treeWrapper = w.getTree().clone();
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
        WrapperIterator<Item> it = treeWrapper.iterator(ForwardTokenIterator.class);

        //Si 'to' no esta incluido, no desechamos
        if(Enclosure.NOT_ENCLOSED == inclusionTo)
        {
            it.goTo(to);
            to = (Text)it.previous();
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
    public Mismatch eat (Edible e, Token t, Item n, DirectionOperator d) 
    {
        //Iteradores de los Edibles:
        EdibleIterator itWrapper = null;
        EdibleIterator itSample = null;
        Mismatch m = null;
        
        
        /* Segun el recorrido creamos un tipo de iterador */
        if(DirectionOperator.DOWNWARDS == d)
        {
            itWrapper = treeWrapper.iterator(ForwardTokenIterator.class);
            itSample = e.iterator(Sample.webPageForwardIterator.class);
        }
        else if(DirectionOperator.UPWARDS == d)
        {
            itWrapper = treeWrapper.iterator(BackwardTokenIterator.class);
            itSample = e.iterator(Sample.webPageBackwardIterator.class);
        }
 
        //Nos colocamos para empezar a comer:
        itWrapper.goTo(n);
        itSample.goTo(t);
        
        Item itemWrapper = null;
        Token token = null;

        /*mientras no se produzca un mismatch y no me coma entero el sample o el wrapper*/
        while(itSample.hasNext() && itWrapper.hasNext() && m==null)
        {
            //Buscamos el siguiente del sample:
            Object edibleToken = itSample.next();
            
            //Si nos ha devuelto un único camino
            if(edibleToken instanceof Token)
                // y no puede ser el siguiente del wrapper
                if(!itWrapper.isNext((Token) edibleToken))
                {
                    //creamos el Mismatch:
                    Object next = itWrapper.next();
                    if(next instanceof java.util.List) //Varios caminos, escogemos el primero:
                        itemWrapper = ((java.util.List<Token>)next).get(0);
                    else if(next instanceof Token)//Un único camino:
                        itemWrapper = (Token)next;
                    else
                        throw new ClassCastException("El next del wrapper devolvió un tipo extraño.");

                    m = new Mismatch(this, e, itemWrapper, (Token)edibleToken);
                }
            else if(edibleToken instanceof java.util.List)//Vamos probando todos los caminos
            {
                //La pila irá guardando los caminos no recorridos:
                Stack<Item> stack = new Stack<Item>();
                for(Item i : (java.util.List<Item>)edibleToken)
                    stack.add(i);
                
                //Mientras la pila esté llena y no haga match, vamos probando uno a uno los caminos:
                boolean encontrado = false;
                while(!stack.empty() && !encontrado)
                {
                    Item ways = stack.pop();
                    
                    //Si es un token, comprobamos si hace matching:
                    if(ways instanceof Token)
                    {
                        if(itWrapper.isNext((Token) ways))
                            encontrado = true;
                    }
                    else if(ways instanceof Item) //Los caminos se bifurcan
                    {
                        //Calculamos las bifurcaciones con un nuevo iterador:
                        EdibleIterator ited;
                        if(DirectionOperator.DOWNWARDS == d)
                            ited = e.iterator(Sample.webPageForwardIterator.class);
                        else
                            ited = e.iterator(Sample.webPageBackwardIterator.class);
                        
                        ited.goTo((Item)ways);
                        Object caminos = ited.next();
                        
                        //Añadimos las bifurcaciones
                        if(caminos instanceof Item)
                            stack.add((Item)caminos);
                        else if(caminos instanceof java.util.List)
                            for(Item camino : (java.util.List<Item>)caminos)
                                stack.add(camino);
                        else
                            throw new ClassCastException("El next del wrapper ha devuelto un tipo extraño.");
                    }
                    else
                        throw new ClassCastException("El next del sample ha devuelto un tipo extraño.");
                }
                
                //Creamos el Mismatch si no lo ha encontrado:
                if(!encontrado)
                {
                    Object next = itWrapper.next();
                    if(next instanceof java.util.List) //Varios caminos, escogemos el primero:
                        itemWrapper = ((java.util.List<Token>)next).get(0);
                    else if(next instanceof Token)//Un único camino:
                        itemWrapper = (Token)next;
                    else
                        throw new ClassCastException("El next del wrapper devolvió un tipo extraño.");

                    m = new Mismatch(this, e, itemWrapper, (Token)edibleToken);
                }
            }
            else
                throw new ClassCastException("La función next devolvió un tipo extraño.");
        }
       
        /* Si no se ha producido un mismatch pero si el sample o el wrapper se han acabado, 
         * entonces lanzamos otro mismatch
         */
        if(itWrapper.hasNext() && m==null)
        {
            token = (Token)itSample.next();
            if(token instanceof Text && ((Text)token).isEOF())
            {
                Object items = itWrapper.next();
                if(items instanceof Item)
                    itemWrapper = (Item)items;
                else
                    itemWrapper = ((java.util.List<Item>)items).get(0);

                m = new Mismatch(this, e, itemWrapper, token);
            }
            else
                throw new RuntimeException("El sample terminó y no era un EOF.");
        }
        else if(itSample.hasNext() && m==null)
        {
            itemWrapper = (Token)itWrapper.next();
            if(itemWrapper instanceof Text && ((Text)itemWrapper).isEOF())
            {
                Object items = itSample.next();
                if(items instanceof Token)
                    token = (Token)items;
                else
                    token = (Token)((java.util.List<Item>)items).get(0);
                m = new Mismatch(this, e, itemWrapper,token);
            }
            else
                throw new RuntimeException("El wrapper terminó y no era un EOF.");
        }
        
        return m;
    }
    

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F925FF52-82A2-AFA9-A17C-8A6B6DE5DDAF]
    // </editor-fold> 
    public Token search (Token t, Token from,int occurrence, DirectionOperator d) {
        
        WrapperIterator<Item> itWrapper = null;
        
        if(DirectionOperator.DOWNWARDS == d)
            itWrapper = treeWrapper.iterator(ForwardTokenIterator.class);    
        else if(DirectionOperator.UPWARDS == d)
            itWrapper = treeWrapper.iterator(BackwardTokenIterator.class);
        
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
    
    public WrapperIterator<Item> iterator (Class iteratorClass)
    {
        WrapperIterator<Item> wi = null;
        try {
            wi = (WrapperIterator<Item>) iteratorClass.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(SMTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SMTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        wi.setTree(treeWrapper);
        wi.setRootIterator(treeWrapper.getRoot());
        
        return wi;
    }
    
    public WrapperIterator<Item> iterator (Class iteratorClass, Item virtualRootWrapper)
    {
        WrapperIterator<Item> wi = null;
        try {
            wi = (WrapperIterator<Item>) iteratorClass.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(SMTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SMTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SMTreeNode<Item> virtualRootNode = treeWrapper.getNode(virtualRootWrapper);
        if(virtualRootNode==null)
            throw new NullPointerException("La raiz virtual para recorrer el wrapper no existe");
        
        wi.setTree(treeWrapper);
        wi.setRootIterator(virtualRootNode);
        
        return wi;
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

