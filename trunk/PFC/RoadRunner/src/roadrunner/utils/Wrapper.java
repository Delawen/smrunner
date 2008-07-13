package roadrunner.utils;

import roadrunner.iterator.EdibleIterator;
import SMTree.utils.Enclosure;
import SMTree.utils.Kinship;
import roadrunner.iterator.ForwardTokenIterator;
import roadrunner.iterator.BackwardTokenIterator;
import SMTree.iterator.SMTreeIterator;
import roadrunner.node.Token;
import SMTree.*;
import SMTree.iterator.BackwardIterator;
import SMTree.iterator.ForwardIterator;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import roadrunner.node.*; 
import roadrunner.operator.DirectionOperator;
import Tokenizer.*;
import roadrunner.iterator.webPageBackwardIterator;
import roadrunner.iterator.webPageForwardIterator;
import roadrunner.operator.Operator;

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

    /**
     * Eats a square candidate. 
     * Returns null if it cannot be eaten or the item after the element.
     * @param e Edible to be eaten
     * @param t Where to start eating
     * @param d Direction
     * @return item after the element on the edible e
     */
    public Item eatSquare(Edible e, Item t, DirectionOperator d) 
    {
        //Iteradores de los Edibles:
        EdibleIterator itSquare = null;
        EdibleIterator itEdible = null;
        Mismatch m = null;
        
        /* Segun el recorrido creamos un tipo de iterador */
        if(DirectionOperator.DOWNWARDS == d)
        {
            itSquare = (EdibleIterator) treeWrapper.iterator(ForwardTokenIterator.class);
            if(e instanceof Sample)
                itEdible = e.iterator(webPageForwardIterator.class);
            else
                itEdible = e.iterator(ForwardTokenIterator.class);
        }
        else if(DirectionOperator.UPWARDS == d)
        {
            itSquare = (EdibleIterator) treeWrapper.iterator(BackwardTokenIterator.class);
            if(e instanceof Sample)
                itEdible = e.iterator(webPageBackwardIterator.class);
            else
                itEdible = e.iterator(BackwardTokenIterator.class);
        }
 
        //Nos colocamos para empezar a comer:
        itEdible.goTo(t);
        
        Item itemWrapper = null;
        
        //Para resolver los mismatches:
        Operator op;

        Object edibleToken = null;
        

        /*mientras no me coma entero el elemento*/
        while(itSquare.hasNext())
        {
            //Buscamos el siguiente del sample:
            edibleToken = itEdible.next();
            
            //Si nos ha devuelto un único camino
            if(edibleToken instanceof Token)
            {
                // y no puede ser el siguiente del wrapper
                if(!itSquare.isNext((Token) edibleToken))
                {
                    //creamos el Mismatch:
                    Object next = itSquare.next();
                    if(next instanceof java.util.List) //Varios caminos, escogemos el primero:
                        itemWrapper = ((java.util.List<Token>)next).get(0);
                    else if(next instanceof Token)//Un único camino:
                        itemWrapper = (Token)next;
                    else
                        throw new ClassCastException("El next del wrapper devolvió un tipo extraño.");

                    m = new Mismatch(this, e, itemWrapper, (Token)edibleToken);
                    
                    op = new Operator();
                    Repair r = op.repair(m);
                    if(r.getState() == StateRepair.SUCESSFULL)
                        r.apply();
                    else
                        return null;
                }
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
                        if(itSquare.isNext((Token) ways))
                            encontrado = true;
                    }
                    else if(ways instanceof Item) //Los caminos se bifurcan
                    {
                        //Calculamos las bifurcaciones con un nuevo iterador:
                        EdibleIterator ited;
                        if(DirectionOperator.DOWNWARDS == d)
                            ited = e.iterator(webPageForwardIterator.class);
                        else
                            ited = e.iterator(webPageBackwardIterator.class);
                        
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
                }
                
                //Creamos el Mismatch si no lo ha encontrado:
                if(!encontrado)
                {
                    Object next = itSquare.next();
                    if(next instanceof java.util.List) //Varios caminos, escogemos el primero:
                        itemWrapper = ((java.util.List<Token>)next).get(0);
                    else if(next instanceof Token)//Un único camino:
                        itemWrapper = (Token)next;
                    else
                        throw new ClassCastException("El next del wrapper devolvió un tipo extraño.");

                    m = new Mismatch(this, e, itemWrapper, (Token)edibleToken);                    
                    
                    op = new Operator();
                    Repair r = op.repair(m);
                    if(r.getState() == StateRepair.SUCESSFULL)
                        r.apply();
                    else
                        return null;
                }
            }
        }
       
        /* Si no se ha producido un mismatch pero si el sample o el wrapper se han acabado, 
         * entonces lanzamos otro mismatch
         */
        return (Item) edibleToken;
    }
    
    
    public SMTree<Item> getTree()
    {
        return this.treeWrapper;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.CB10757A-C05C-4F36-13C5-A851167056BD]
    // </editor-fold> 
    public Mismatch eat (Sample s, DirectionOperator d) 
    {
        if(d == DirectionOperator.DOWNWARDS)
            return eat(s, s.getToken(0), treeWrapper.getRootObject(), d);
        else
            return eat(s, s.getToken(s.getNumToken()-1), treeWrapper.getRootObject(), d);  
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
    private boolean isWellFormed (Token from,Enclosure inclusionFrom, Token to,Enclosure inclusionTo, DirectionOperator d) {
        
        if(from==null || to==null)
           throw new NullPointerException("");
        
        // las regiones vacias estaran bien formadas
        if(from==to && (inclusionFrom == Enclosure.NOT_ENCLOSED || inclusionTo == Enclosure.NOT_ENCLOSED))
            return true;
        
        boolean isWellFormed = true;
        Stack<Tag> openTags = new Stack(); 
        SMTreeIterator<Item> it = null;
        
        if(d == DirectionOperator.DOWNWARDS)
            it = treeWrapper.iterator(ForwardTokenIterator.class);
        else if(d == DirectionOperator.UPWARDS)
            it = treeWrapper.iterator(BackwardTokenIterator.class);

        //Si 'to' no esta incluido, no desechamos
        if(Enclosure.NOT_ENCLOSED == inclusionTo)
        {
            it.goTo(to);
            to = (Text)it.previous();
        }
        
        it.goTo(from);
            
        //Si 'from' no esta incluido, no desechamos
        if(Enclosure.NOT_ENCLOSED == inclusionFrom)
            from = (Token) it.nextObject();
        
        if(from==to && !(from instanceof Tag))
            return true;
        else if (from==to)
            return false;
        
        if(d == DirectionOperator.DOWNWARDS)
        {
            Token t;
            do
            {
                t = (Token) it.nextObject();
                if(t instanceof Tag && ((Tag)t).isOpenTag())
                    openTags.push((Tag)t);
                else if (t instanceof Tag && ((Tag)t).isCloseTag() && openTags.firstElement().isOpenTag() && openTags.firstElement().getContent().equals(t.getContent()))
                        openTags.pop();
                else if(!(t instanceof Text) && !(t instanceof Variable))
                    isWellFormed = false;

            } while(it.hasNext() && t!=to && isWellFormed);
        }
        else if(d == DirectionOperator.UPWARDS)
        {
            Token t;
            do
            {
                t = (Token) it.nextObject();
                if(t instanceof Tag && ((Tag)t).isOpenTag())
                    openTags.push((Tag)t);
                else if (t instanceof Tag && ((Tag)t).isCloseTag() && openTags.firstElement().isOpenTag() && openTags.firstElement().getContent().equals(t.getContent()))
                        openTags.pop();
                else if(!(t instanceof Text) && !(t instanceof Variable))
                    isWellFormed = false;

            } while(it.hasNext() && t!=to && isWellFormed);      
        }
            
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
    public Mismatch eat (Edible e, Item t, Item n, DirectionOperator d) 
    {
        if(e == null || t == null || n == null || d == null)
            throw new IllegalArgumentException("eat no puede tener parámetros nulos.");
        
        //Iteradores de los Edibles:
        EdibleIterator itWrapper = null;
        EdibleIterator itSample = null;
        Mismatch m = null;
        
        
        /* Segun el recorrido creamos un tipo de iterador */
        if(DirectionOperator.DOWNWARDS == d)
        {
            itWrapper = (EdibleIterator) treeWrapper.iterator(ForwardTokenIterator.class);
            itSample = e.iterator(webPageForwardIterator.class);
        }
        else if(DirectionOperator.UPWARDS == d)
        {
            itWrapper = (EdibleIterator) treeWrapper.iterator(BackwardTokenIterator.class);
            itSample = e.iterator(webPageBackwardIterator.class);
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
            {
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
                            ited = e.iterator(webPageForwardIterator.class);
                        else
                            ited = e.iterator(webPageBackwardIterator.class);
                        
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
                throw new RuntimeException("El next() muestra comportamientos extraños. Sample: " + edibleToken + "Wrapper: " + itWrapper.next());

        }
       
        /* Si no se ha producido un mismatch pero si el sample o el wrapper se han acabado, 
         * entonces lanzamos otro mismatch
         */
        if(m == null)
        {
            if(itWrapper.hasNext())
            {
                token = (Token)itSample.next();
                if(token instanceof DOF)
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
            else if(itSample.hasNext())
            {
                itemWrapper = (Token)itWrapper.next();
                if(itemWrapper instanceof DOF)
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
        }
        
        return m;
    }
    

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F925FF52-82A2-AFA9-A17C-8A6B6DE5DDAF]
    // </editor-fold> 
    public Token searchWellFormed (Token t, Token from, DirectionOperator d) 
    {
        SMTreeIterator<Item> itWrapper = null;
        
        if(DirectionOperator.DOWNWARDS == d)
            itWrapper = treeWrapper.iterator(ForwardIterator.class);    
        else if(DirectionOperator.UPWARDS == d)
            itWrapper = treeWrapper.iterator(BackwardIterator.class);
        
        if(!itWrapper.goTo(from))
            return null;
        
        Token token=null;

        while(itWrapper.hasNext())
        {
            Item i = (Item) itWrapper.nextObject();
            
            //Sabremos que estamos en una hoja si es un token
            if(i instanceof Token)
            {
                token = (Token) i;
                if(token.match(t))
                {
                    if(isWellFormed(from, Enclosure.ENCLOSED, token, Enclosure.ENCLOSED, d))
                        break;
                    else
                        token = null;
                }
            }
        }
        
        return token;
    }
    
    public EdibleIterator iterator (Class iteratorClass)
    {
        EdibleIterator wi = null;
        try {
            wi = (EdibleIterator) iteratorClass.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(SMTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SMTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        wi.setTree(treeWrapper);
        wi.setRootIterator(treeWrapper.getRoot());
        
        return wi;
    }
    
    public SMTreeIterator<Item> iterator (Class iteratorClass, Item virtualRootWrapper)
    {
        SMTreeIterator<Item> wi = null;
        try {
            wi = (SMTreeIterator<Item>) iteratorClass.newInstance();
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

    public Wrapper cloneSubWrapper(Token firstTokenSquare, Token lastTokenSquare, Item parent) 
    {
        SMTree<Item> treeCloned = null;
        try {
            treeCloned = treeWrapper.cloneSubTree(firstTokenSquare, lastTokenSquare, parent);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Wrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new Wrapper(treeCloned);
    }
}

