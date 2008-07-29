package roadrunner.operator;

import SMTree.utils.Enclosure;
import roadrunner.iterator.BackwardTokenIterator;
import roadrunner.iterator.EdibleIterator;
import roadrunner.iterator.ForwardTokenIterator;
import roadrunner.iterator.webPageBackwardIterator;
import roadrunner.iterator.webPageForwardIterator;
import roadrunner.node.Item;
import roadrunner.node.List;
import roadrunner.node.Text;
import roadrunner.node.Token;
import roadrunner.node.Tuple;
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
            itW = w.iterator(ForwardTokenIterator.class);
            if(s instanceof Sample) 
                itS = s.iterator(webPageForwardIterator.class);
            else
                itS = s.iterator(ForwardTokenIterator.class);
        }
        else if(d == DirectionOperator.UPWARDS)
        {
            itW = w.iterator(BackwardTokenIterator.class);
            if(s instanceof Sample) 
                itS = s.iterator(webPageBackwardIterator.class);
            else
                itS = s.iterator(BackwardTokenIterator.class);
        }
        
        if(super.where == WebPageOperator.WRAPPER)
        {
            //buscamos squareW
            
            firstTokenSquare = (Token) n;
            firstTokenList= firstTokenSquare;
            
            itS.goTo(t);
            lastDelim = (Token) itS.previous();
            
   
            lastTokenSquare =  w.searchWellFormed(lastDelim, Enclosure.ENCLOSED, firstTokenSquare, Enclosure.ENCLOSED, d);

            if(lastTokenSquare == null || lastTokenSquare == firstTokenSquare)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }

            //Si hemos llegado aquí es porque hemos encontrado una ocurrencia 
            //que delimita un código bien formado:
            lastTokenList = lastTokenSquare;

            
            // Ya tenemos definido la zona de squareW, ahora le creamos un wrapper        
            Wrapper squareW = w.cloneSubWrapper(firstTokenSquare, lastTokenSquare, new Tuple(), d);
                             
            // Comemos hacia arriba
            Item whereEat; 
            itW.goTo(firstTokenList);
            whereEat = (Item) itW.previous();
            
            Item whatEaten = null;
            if(d == DirectionOperator.DOWNWARDS)
                whatEaten = squareW.eatOneSquare(w, whereEat, DirectionOperator.UPWARDS);
            else
                whatEaten = squareW.eatOneSquare(w, whereEat, DirectionOperator.DOWNWARDS);
            
            
            // para asegurarnos de que es un plus al menos tengo que comerme
            // un elemento de la lista, sino falla la reparacion:
            if(whatEaten == null)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }

            Item whatEatenTemp = null;
            while( whatEaten != null)
            {
                firstTokenList = (Token) whatEaten;
                itW.goTo((Item) whatEaten);
                whereEat = (Item) itW.previous();
                whatEatenTemp = whatEaten;
                if(d == DirectionOperator.DOWNWARDS)
                    whatEaten = squareW.eatOneSquare(w, whereEat, DirectionOperator.UPWARDS);
                else
                    whatEaten = squareW.eatOneSquare(w, whereEat, DirectionOperator.DOWNWARDS);
                if(whatEaten == whatEatenTemp)
                    break;
            }
                
           // Ahora comemos hacia abajo
           itW.goTo(lastTokenList);
           itW.next();
           whereEat = (Item) itW.next();
           whatEaten = squareW.eatOneSquare(w, whereEat, d);
            
            while( whatEaten != null)
            {
                lastTokenList = (Token) whatEaten;
                itW.goTo((Item) whatEaten);
                whereEat = (Item) itW.next();
                whatEatenTemp = whatEaten;
                whatEaten = squareW.eatOneSquare(w, whereEat, d);
                if(whatEaten == whatEatenTemp)
                    return null;
            }
           
            squareW.getTree().setRootObject(new List());

            if(DirectionOperator.UPWARDS == d)
            {
                Token temp = lastTokenList;
                lastTokenList = firstTokenList;
                firstTokenList = temp;
            }

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
            
            lastTokenSquare =  s.searchWellFormed(lastDelim,Enclosure.ENCLOSED, firstTokenSquare, Enclosure.ENCLOSED, d);

            if(lastTokenSquare == null || lastTokenSquare == firstTokenSquare)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }
                        
            // Ya tenemos definido la zona de squareS, ahora le creamos un wrapper        
            Wrapper squareS = s.cloneSubWrapper(firstTokenSquare, lastTokenSquare, new Tuple(), d);
                             
            // Comemos hacia arriba en el wrapper
            Item whereEat;   
            itW.goTo(n);
            whereEat = lastTokenList;
            Item whatEaten;

            if(d == DirectionOperator.DOWNWARDS)
                whatEaten = squareS.eatOneSquare(w, whereEat, DirectionOperator.UPWARDS);
            else
                whatEaten = squareS.eatOneSquare(w, whereEat, DirectionOperator.DOWNWARDS);
            
            // para asegurarnos de que es un plus al menos tengo que comerme
            // un elemento de la lista, sino falla la reparacion:
            if(whatEaten == null)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }

            Item whatEatenTemp = null;
            while(whatEaten != null)
            {
                firstTokenList = (Token) whatEaten;
                itW.goTo(whatEaten);
                whereEat =  (Item) itW.previous();
                whatEatenTemp = whatEaten;
                if(d == DirectionOperator.DOWNWARDS)
                    whatEaten = squareS.eatOneSquare(w, whereEat, DirectionOperator.UPWARDS);
                else
                    whatEaten = squareS.eatOneSquare(w, whereEat, DirectionOperator.DOWNWARDS);
                if(whatEaten == whatEatenTemp)
                    break;
            }
                
           // Ahora comemos hacia abajo en el sample
           itS.goTo(lastTokenSquare);
           itS.next();
           whereEat = (Item) itS.next();
           whatEaten = squareS.eatOneSquare(s, whereEat, d);
           Item lastEatenSuccess = (Item) whatEaten;
            
            while(true)
            {
                itS.goTo((Item) whatEaten);
                whereEat = (Item) itS.next();
                whatEatenTemp = whatEaten;
                whatEaten = squareS.eatOneSquare(s, whereEat, d);
                if(whatEaten == null || whatEaten == whatEatenTemp)
                    break;
                lastEatenSuccess =  (Item) whatEaten;
            }
           
            squareS.getTree().setRootObject(new List());


            if(DirectionOperator.UPWARDS == d)
            {
                Token temp = lastTokenList;
                lastTokenList = firstTokenList;
                firstTokenList = temp;
            }

            rep.setReparator(squareS);
            rep.setInitialItem(firstTokenList);
            rep.setFinalItem(lastTokenList);
            rep.setState(StateRepair.SUCESSFULL);
            rep.setToRepair((Wrapper) w);
            itS.goTo((Item) lastEatenSuccess);
            itS.next();
            rep.setIndexSample((Token) itS.next());
           
        }
    
        return rep;     
    }

}

