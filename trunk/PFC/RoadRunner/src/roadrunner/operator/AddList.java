package roadrunner.operator;

import SMTree.iterator.BackwardIterator;
import SMTree.iterator.ForwardIterator;
import SMTree.utils.Enclosure;
import roadrunner.iterator.EdibleIterator;
import roadrunner.iterator.webPageBackwardIterator;
import roadrunner.iterator.webPageForwardIterator;
import roadrunner.node.Item;
import roadrunner.node.List;
import roadrunner.node.Text;
import roadrunner.node.Token;
import roadrunner.utils.Edible;
import roadrunner.utils.Mismatch; 
import roadrunner.utils.Repair; 
import roadrunner.utils.Sample;
import roadrunner.utils.StateRepair;
import roadrunner.utils.Wrapper;

/**
 *  Class addList
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.31ACF038-1A60-CDE1-AF3D-8E99E0215D79]
// </editor-fold> 
public class AddList extends IOperator 
{
    AddList(WebPageOperator where) 
    {
        super(where);  
    }

    @Override
    public Repair apply(Mismatch m, DirectionOperator d) 
    {
        Edible s = m.getSample();
        Edible w = m.getWrapper();
        Item t = m.getToken();
        Item n = m.getNode();
        EdibleIterator itW = null;
        EdibleIterator itS = null;
        
        Repair rep = new Repair(m);
        
        Token firstTokenList=null, lastTokenList=null;
        Token firstTokenSquare=null, lastTokenSquare=null;
        Token lastDelim=null;
        
        if(d == DirectionOperator.DOWNWARDS)
        {
            itW = w.iterator(ForwardIterator.class);
            itS = s.iterator(webPageForwardIterator.class);
        }
        else if(d == DirectionOperator.UPWARDS)
        {
            itW = w.iterator(BackwardIterator.class);
            itS = s.iterator(webPageBackwardIterator.class);
        }
        
        if(super.where == WebPageOperator.WRAPPER)
        {
            //buscamos squareW
            
            firstTokenSquare = (Token) n;
            firstTokenList= firstTokenSquare;
            
            itS.goTo(t);
            lastDelim = (Token) itS.previous();
            
            int ocurrence = 0;
            boolean searching = true;        
            while(searching)
            {
                lastTokenSquare =  w.search(lastDelim, firstTokenSquare, ocurrence, d);
                
                if(lastTokenSquare == null)
                {
                    searching = false;
                }
                else if(!w.isWellFormed( (Text) firstTokenSquare, Enclosure.ENCLOSED, (Text) lastTokenSquare, Enclosure.ENCLOSED))
                     ocurrence++;
                else
                {
                    //Si hemos llegado aquí es porque hemos encontrado una ocurrencia 
                    //que delimita un código bien formado:
                    searching = false;

                    lastTokenList = lastTokenSquare;
                }
            }
            
            
            if(lastTokenSquare == null)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }
            
            // Ya tenemos definido la zona de squareW, ahora le creamos un wrapper        
            Wrapper squareW = w.cloneSubWrapper(firstTokenSquare, lastTokenSquare, new List());     
                             
            // Comemos hacia arriba
            Item whereEat;   
            itW.goTo(firstTokenList);
            whereEat = (Item) itW.previous();
            Object whatEaten = squareW.eatSquare(w, whereEat, DirectionOperator.UPWARDS);
            
            
            // para asegurarnos de que es un plus al menos tengo que comerme
            // un elemento de la lista, sino falla la reparacion:
            if(whatEaten instanceof Mismatch)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }
            
            while( whatEaten instanceof Item)
            {
                firstTokenList = (Token) whatEaten;
                itW.goTo((Item) whatEaten);
                whereEat = (Item) itW.previous();
                whatEaten = squareW.eatSquare(w, whereEat, DirectionOperator.UPWARDS);
            }
            
            if (whatEaten instanceof Mismatch == false)
                throw new IllegalStateException("eatSquare() devolvio null");
                
           // Ahora comemos hacia abajo
           itW.goTo(lastTokenList);
           whereEat = (Item) itW.next();
           whatEaten = squareW.eatSquare(w, whereEat, DirectionOperator.DOWNWARDS);
            
            while( whatEaten instanceof Item)
            {
                lastTokenList = (Token) whatEaten;
                itW.goTo((Item) whatEaten);
                whereEat = (Item) itW.next();
                whatEaten = squareW.eatSquare(w, whereEat, DirectionOperator.DOWNWARDS);
            }
            
            if (whatEaten instanceof Mismatch == false)
                throw new IllegalStateException("eatSquare() devolvio null");
           
            rep.setReparator(squareW);
            rep.setInitialItem(firstTokenList);
            rep.setFinalItem(lastTokenList);
            rep.setState(StateRepair.SUCESSFULL);
            rep.setToRepair((Wrapper) w);
            rep.setIndexSample((Token) t);
            
        }
        else if(where == WebPageOperator.SAMPLE)
        {
            //buscamos squareS        
            firstTokenSquare = (Token) t;
            itW.goTo(n);
            lastTokenList = (Token) itW.previous();
            
            itS.goTo(t);
            lastDelim = (Token) itS.previous();
            
            int ocurrence = 0;
            boolean searching = true;        
            while(searching)
            {
                lastTokenSquare =  s.search(lastDelim, firstTokenSquare, ocurrence, d);
                
                if(lastTokenSquare == null)
                {
                    searching = false;
                }
                else if(!w.isWellFormed( (Text) firstTokenSquare, Enclosure.ENCLOSED, (Text) lastTokenSquare, Enclosure.ENCLOSED))
                     ocurrence++;
                else
                {
                    //Si hemos llegado aquí es porque hemos encontrado una ocurrencia 
                    //que delimita un código bien formado:
                    searching = false;

                    lastTokenList = lastTokenSquare;
                }
            }
            
            
            if(lastTokenSquare == null)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }
            
            // Ya tenemos definido la zona de squareS, ahora le creamos un wrapper        
            Wrapper squareS = s.cloneSubWrapper(firstTokenSquare, lastTokenSquare, new List());     
                             
            // Comemos hacia arriba en el wrapper
            Item whereEat;   
            itW.goTo(n);
            whereEat = lastTokenList;
            Object whatEaten = squareS.eatSquare(s, whereEat, DirectionOperator.UPWARDS);
            
            
            // para asegurarnos de que es un plus al menos tengo que comerme
            // un elemento de la lista, sino falla la reparacion:
            if(whatEaten instanceof Mismatch)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }
            
            while( whatEaten instanceof Item)
            {
                firstTokenList = (Token) whatEaten;
                itW.goTo((Item) whatEaten);
                whereEat = (Item) itW.previous();
                whatEaten = squareS.eatSquare(s, whereEat, DirectionOperator.UPWARDS);
            }
            
            if (whatEaten instanceof Mismatch == false)
                throw new IllegalStateException("eatSquare() devolvio null");
                
           // Ahora comemos hacia abajo en el sample
           itS.goTo(lastTokenSquare);
           whereEat = (Item) itS.next();
           whatEaten = squareS.eatSquare(s, whereEat, DirectionOperator.DOWNWARDS);
            
            while( whatEaten instanceof Item)
            {
                itS.goTo((Item) whatEaten);
                whereEat = (Item) itS.next();
                whatEaten = squareS.eatSquare(s, whereEat, DirectionOperator.DOWNWARDS);
            }
            
            if (whatEaten instanceof Mismatch == false)
                throw new IllegalStateException("eatSquare() devolvio null");
           
            rep.setReparator(squareS);
            rep.setInitialItem(firstTokenList);
            rep.setFinalItem(lastTokenList);
            rep.setState(StateRepair.SUCESSFULL);
            rep.setToRepair((Wrapper) w);
            itS.goTo((Item) whereEat);
            rep.setIndexSample((Token) itS.next());
           
        }
    
        return rep;     
    }

}

