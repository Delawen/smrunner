package roadrunner.utils;

import roadrunner.iterator.EdibleIterator;
import SMTree.utils.Enclosure;
import SMTree.utils.Kinship;
import roadrunner.iterator.ForwardTokenIterator;
import roadrunner.iterator.BackwardTokenIterator;
import SMTree.iterator.SMTreeIterator;
import SMTree.*;
import SMTree.iterator.BackwardIterator;
import SMTree.iterator.ForwardIterator;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import roadrunner.node.*; 
import roadrunner.operator.DirectionOperator;
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
    private EdibleIterator itWrapper;
    private EdibleIterator itSample;

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
    public Item eatOneSquare(Edible e, Item t, DirectionOperator d)
    {
        //Iteradores de los Edibles:
        Mismatch m = null;

        crearIteradorEdible(d, t, e);
        crearIteradorWrapper(d, this.getTree().getRootObject());

        //Para resolver los mismatches:
        Operator op;
        Object edibleToken = null;


        /*mientras no me coma entero el elemento*/
        while(itWrapper.hasNext())
        {
            edibleToken = itSample.nextAll();
            m = compruebaNext(edibleToken, e, d);
            //Si es necesario, aplicamos reparaciones:
            if(m != null)
            {
                op = new Operator();
                Repair repair = op.repair(m);
                if(repair.getState() == StateRepair.SUCESSFULL)
                    repair.apply();
                else
                {
                    //Falló, así que el edibleToken lo ponemos a null
                    edibleToken = null;
                    break;
                }
                itSample.goTo(repair.getIndexSample());
                Item n = repair.getReparator().getTree().getRootObject();
                itWrapper.goTo(n);
                if(n instanceof roadrunner.node.List)
                    ((roadrunner.node.List)n).setAccessed(true);
            }
        }

        if(edibleToken == null)
            return null;

        if(edibleToken instanceof Item)
            return (Item) edibleToken;
        else
            return (Item) ((java.util.List)edibleToken).get(0);
    }
    
    public SMTree<Item> getTree()
    {
        return this.treeWrapper;
    }
    public Mismatch eat (Sample s, DirectionOperator d) 
    {
        if(d == DirectionOperator.DOWNWARDS)
            return eat(s, s.getToken(0), treeWrapper.getRootObject(), d);
        else
            return eat(s, s.getToken(s.getNumToken()-1), treeWrapper.getRootObject(), d);  
    }
    public boolean goTo (Item i) {
        return true;
    }
    public Mismatch eat (Sample s) {
        return eat(s,DirectionOperator.DOWNWARDS);
    }

    private void crearIteradorEdible(DirectionOperator d, Item t, Edible e)
    {

        /* Segun el recorrido creamos un tipo de iterador */
        if(DirectionOperator.DOWNWARDS == d)
        {
            if(e instanceof Sample)
                itSample = e.iterator(webPageForwardIterator.class);
            else
                itSample = e.iterator(ForwardTokenIterator.class);
        }
        else
        {
            if(e instanceof Sample)
                itSample = e.iterator(webPageBackwardIterator.class);
            else
                itSample = e.iterator(BackwardTokenIterator.class);
        }

        //Nos colocamos para empezar a comer:
        itSample.goTo(t);
    }

    private void crearIteradorWrapper(DirectionOperator d, Item n)
    {
        /* Segun el recorrido creamos un tipo de iterador */
        if(DirectionOperator.DOWNWARDS == d)
        {
            itWrapper = (EdibleIterator) treeWrapper.iterator(ForwardTokenIterator.class);
        }
        else
        {
            itWrapper = (EdibleIterator) treeWrapper.iterator(BackwardTokenIterator.class);
        }

        //Nos colocamos para empezar a comer:
        itWrapper.goTo(n);
    }

    private Mismatch compruebaNext(Object edibleToken, Edible e, DirectionOperator d)
    {
        Mismatch m = null;

        //Si nos ha devuelto un único camino
        if(edibleToken instanceof Token)
        {
            // y no puede ser el siguiente del wrapper
            if(!itWrapper.isNext((Token) edibleToken))
                m = crearMismatch(itWrapper.next(), (Token)edibleToken, e, d);
        }
        //Si el edible nos devolvio varios caminos:
        else if(edibleToken instanceof java.util.List)
        {
            //Vamos probando todos los caminos
            boolean encontrado = false;

            //La pila irá guardando los caminos no recorridos:
            Stack<Item> noRecorridos = new Stack<Item>();

            //Si es el principio de una lista ya accedida,
            //para evitar bucles infinitos probamos primero
            //fuera de la lista
            java.util.List<Item> listaItems = (java.util.List)edibleToken;
            Item primerItem = listaItems.get(0);
            Item primerPadre = (Item)((Wrapper)e).treeWrapper.getNode(primerItem).getParent().getObject();

            if(primerPadre instanceof roadrunner.node.List
                    && ((roadrunner.node.List)primerPadre).isAccessed()
                    && listaItems.size() > 1)
            {
                noRecorridos.add(listaItems.get(1));
                noRecorridos.add(listaItems.get(0));
                for(int i = 2; i < listaItems.size(); i++)
                    noRecorridos.add(listaItems.get(i));
            }
            else
                for(Item i : (java.util.List<Item>)edibleToken)
                    noRecorridos.add(i);


            for(Item i : noRecorridos)
            {
                if(itWrapper.isNext(i))
                {
                    encontrado = true;
                    itSample.goTo(i);
                    itSample.next();
                    break;
                }
            }

            //Creamos el Mismatch si no lo ha encontrado:
            if(!encontrado)
                m = crearMismatch(itWrapper.next(), (Token)edibleToken, e, d);
        }
        else
            throw new RuntimeException("El next() muestra comportamientos extraños. Sample: " + edibleToken + "Wrapper: " + itWrapper.next());

        return m;

    }

    private Mismatch crearMismatch(Object next, Token edibleToken, Edible e, DirectionOperator d)
    {
        Item itemWrapper = null;
        if(next instanceof java.util.List) //Varios caminos, escogemos el primero:
            itemWrapper = ((java.util.List<Token>)next).get(0);
        else if(next instanceof Token)//Un único camino:
            itemWrapper = (Token)next;
        else
            throw new ClassCastException("El next del wrapper devolvió un tipo extraño: " + next.getClass().getName());

        return new Mismatch(this, e, itemWrapper, edibleToken, d);

    }
   
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
            to = (Token)it.previous();
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
                else if (t instanceof Tag && ((Tag)t).isCloseTag() && !openTags.empty() && openTags.firstElement().isOpenTag() && openTags.firstElement().getContent().equals(t.getContent()))
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
                else if (t instanceof Tag && ((Tag)t).isCloseTag() && !openTags.empty() && openTags.firstElement().isOpenTag() && openTags.firstElement().getContent().equals(t.getContent()))
                        openTags.pop();
                else if(!(t instanceof Text) && !(t instanceof Variable))
                    isWellFormed = false;

            } while(it.hasNext() && t!=to && isWellFormed);      
        }
            
        if(!openTags.empty())
            isWellFormed=false;
        
        return isWellFormed;
    }

    public boolean substitute (Item from, Enclosure inclusionFrom, Item to, Enclosure inclusionTo, SMTree what) {
        return treeWrapper.substituteObject(from, inclusionFrom, to, inclusionTo, what);
    }
    
     public boolean substitute (Item from, Enclosure inclusionFrom, Item to, Enclosure inclusionTo, Wrapper what) {
        return treeWrapper.substituteObject(from, inclusionFrom, to, inclusionTo, what.treeWrapper);
    }

    public Mismatch eat (Edible e, Item t, Item n, DirectionOperator d) 
    {
        if(e == null || t == null || n == null || d == null)
            throw new IllegalArgumentException("eat no puede tener parámetros nulos.");

        Mismatch m = null;

        //Iteradores de los Edibles:
        crearIteradorWrapper(d, n);
        crearIteradorEdible(d, t, e);
        

        /*mientras no se produzca un mismatch y no me coma entero el sample o el wrapper*/
        while(itSample.hasNext() && itWrapper.hasNext() && m==null)
            m = compruebaNext(itSample.nextAll(), e, d);
       
        /* Si no se ha producido un mismatch pero si el sample o el wrapper se han acabado,
         * no ha funcionado bien
         */
        if(m == null && (itWrapper.hasNext() ||itSample.hasNext()))
           throw new RuntimeException("Se ha liado con los DOF. Probablemente el isNext() está fallando.");
        
        return m;
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

    public Token searchWellFormed(Token t, Enclosure tokenEnclosure, Token from, Enclosure tEnclosure, DirectionOperator d) {
                
        SMTreeIterator<Item> it = null;
        
        if(DirectionOperator.DOWNWARDS == d)
            it = treeWrapper.iterator(ForwardIterator.class);
        else if(DirectionOperator.UPWARDS == d)
            it = treeWrapper.iterator(BackwardIterator.class);
        
        if(!it.goTo(from))
            return null;
        
        Token token=null;

        while(it.hasNext())
        {
            Item i = (Item) it.nextObject();
            
            //Sabremos que estamos en una hoja si es un token
            if(i instanceof Token)
            {
                token = (Token) i;
                if(token.match(t) && isWellFormed(from, tEnclosure, token, tokenEnclosure, d))
                    return token;
            }            
        }
        
        return null;
    }

    @Override
    public String toString()
    {
        String result = "";
        ForwardIterator it = new ForwardIterator();
        it.setTree(treeWrapper);
               
        while(it.hasNext())
        {
            result += it.nextObject().toString();
        }
     return result;  
    }
}

