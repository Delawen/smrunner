package roadrunner.operator;

import SMTree.iterator.BackwardIterator;
import SMTree.utils.Enclosure;
import SMTree.iterator.ForwardIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import roadrunner.iterator.BackwardTokenIterator;
import roadrunner.iterator.EdibleIterator;
import roadrunner.iterator.ForwardTokenIterator;
import roadrunner.iterator.webPageBackwardIterator;
import roadrunner.iterator.webPageForwardIterator;
import roadrunner.utils.Mismatch; 
import roadrunner.utils.Repair; 
import roadrunner.utils.Sample;
import roadrunner.utils.StateRepair;
import roadrunner.utils.Wrapper;
import roadrunner.node.Item;
import roadrunner.node.Optional;
import roadrunner.node.Text;
import roadrunner.node.Token;
import roadrunner.utils.Edible;

/**
 *  Class addHook
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.E210968F-FE0F-DA54-A32E-77F957F292B1]
// </editor-fold> 
public class AddOptional extends IOperator 
{

    AddOptional(WebPageOperator where) 
    {
        super(where);
    }

    @Override
    public Repair apply(Mismatch m, DirectionOperator d) {
       
        /**
         * Hook sólo puede tener un tipo de reparaciones internas: las del search.
         */
       
        Edible s = m.getSample();
        Wrapper w = m.getWrapper();
        Token t = m.getToken();
        Item n = m.getNode();
        EdibleIterator itW = null;
        EdibleIterator itS = null;
        
        Repair rep = new Repair(m);
        rep.setState(StateRepair.BUILDING);
        
        Token firstTokenOptional=null, lastTokenOptional=null;
        
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

            // Buscamos el token que aparecera justo detras de la supuesta opcionalidad
            lastTokenOptional =  w.searchWellFormed(t, Enclosure.NOT_ENCLOSED, (Token) n, Enclosure.ENCLOSED, d);

            // Si no lo hemos encontrado entonces
            //  no se puede crear reparacion con addoptional en el wrapper
            if(lastTokenOptional == null)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }
            
            // lastTokenOptional contiene al nodo posterior, asi que nos quedamos con el ultimo de la opcionalidad
            assert(itW.goTo(lastTokenOptional));
            lastTokenOptional = (Token) itW.previous(true);
            firstTokenOptional = (Token) n;
            
            // comprobamos que no estamos ante una lista          
            assert(itS.goTo(t));
            Token tokenInmediatelyBeforeT = (Token) itS.previous(true);
            assert(itS.goTo(t));
            Token tokenInmediatelyAfterT = (Token) itS.next();
            
            if(lastTokenOptional.match(tokenInmediatelyBeforeT)) // || 
                    //!firstTokenOptional.match(tokenInmediatelyAfterT)) //TODO asegurarse de que hace esta condicion de abajo
            {
                // falla: estabamos ante una lista.
                rep.setState(StateRepair.FAILED);
                return rep;
            }

            //colocamos los parámetros de la reparación
            
            rep.setInitialItem(firstTokenOptional);
            rep.setFinalItem(lastTokenOptional);
            rep.setState(StateRepair.SUCESSFULL);
            rep.setIndexSample(t);

            
//            if(DirectionOperator.UPWARDS == d)
//            {
//                Token temp = lastTokenOptional;
//                lastTokenOptional = firstTokenOptional;
//                firstTokenOptional = temp;
//            }
            
            Wrapper wrapperReparator = null;
            try {
                wrapperReparator = new Wrapper(w.cloneSubWrapper(firstTokenOptional, lastTokenOptional, new Optional(), d));
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(AddOptional.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            rep.setReparator(wrapperReparator);
            
        }
        else if(where == WebPageOperator.SAMPLE)
        {     
 
            // Buscamos el token que aparecera justo detra de la supuesta opcionalidad
            lastTokenOptional =  s.searchWellFormed((Token) n, Enclosure.NOT_ENCLOSED, t, Enclosure.ENCLOSED, d);

            // Si no lo hemos encontrado paramos de buscar
            // porque no se puede crear reparacion con addoptional en el wrapper
            if(lastTokenOptional == null)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }

            // y ahora si que nos quedamos con el token ultimo de la opcionalidad
            assert(itS.goTo(lastTokenOptional));
            lastTokenOptional = (Token) itS.previous(true);
            firstTokenOptional = (Token) t;
            
            assert(itS.goTo(t));
            Token tokenInmediatelyBeforeT = (Token) itS.previous(true);
            assert(itS.goTo(t));
            
            if(lastTokenOptional.match(tokenInmediatelyBeforeT) /*|| 
                     firstTokenOptional.match(tokenInmediatelyAfterT)*/ ) //TODO asegurarse de que hace esta condicion de abajo
            {
                // falla: estabamos ante una lista.
                rep.setState(StateRepair.FAILED);
                return rep;
            }
            

            assert(itW.goTo(n));
            rep.setInitialItem((Item) itW.previous(true));
            rep.setInitialEnclosure(Enclosure.NOT_ENCLOSED);
            rep.setFinalItem(n);
            rep.setFinalEnclosure(Enclosure.NOT_ENCLOSED);
            rep.setState(StateRepair.SUCESSFULL);
            assert(itS.goTo(lastTokenOptional));
            itS.next();
            rep.setIndexSample((Token) itS.next());
            
//            if(DirectionOperator.UPWARDS == d)
//            {
//                Token temp = lastTokenOptional;
//                lastTokenOptional = firstTokenOptional;
//                firstTokenOptional = temp;
//            }
            
            Wrapper wrapperReparator = s.cloneSubWrapper(firstTokenOptional, lastTokenOptional, new Optional(), d);
      
            rep.setReparator(wrapperReparator);   
        }
    
        return rep;     
    }
}

