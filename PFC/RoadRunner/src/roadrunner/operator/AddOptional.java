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
            itS = s.iterator(webPageForwardIterator.class);
        }
        else if(d == DirectionOperator.UPWARDS)
        {
            itW = w.iterator(BackwardTokenIterator.class);
            itS = s.iterator(webPageBackwardIterator.class);
        }
        
        if(super.where == WebPageOperator.WRAPPER)
        {
            int ocurrence = 0;
            while(true)
            {
                // Buscamos el token que aparecera justo detra de la supuesta opcionalidad
                lastTokenOptional =  w.search(t, (Token) n, ocurrence, d);

                // Si no lo hemos encontrado paramos de buscar
                // porque no se puede crear reparacion con addoptional en el wrapper
                if(lastTokenOptional == null)
                {
                    rep.setState(StateRepair.FAILED);
                    return rep;
                }
                // Si hemos encontrado uno, tenemos que ver si es una porcion
                // de codigo bien formada
               //Si no está bien formado, seguimos buscando la siguiente ocurrencia
                else if(!w.isWellFormed( (Text)n, Enclosure.ENCLOSED, (Text)lastTokenOptional, Enclosure.NOT_ENCLOSED))
                     ocurrence++;
                else
                {
                    //Si hemos llegado aquí es porque hemos encontrado una ocurrencia 
                    //que delimita un código bien formado

                    // y ahora si que nos quedamos con el token ultimo de la opcionalidad
                    itW.goTo(lastTokenOptional);
                    
                    lastTokenOptional = (Token) itW.previous();
                    firstTokenOptional = (Token) n;
                    break;
                }
            }
            
            // comprobamos que no estamos ante una lista          
            itS.goTo(t);
            Token tokenInmediatelyBeforeT = (Token) itS.previous();
            itS.goTo(t);
            Token tokenInmediatelyAfterT = (Token) itS.next();
            
            if(!lastTokenOptional.equals(tokenInmediatelyBeforeT) && 
                    !firstTokenOptional.equals(tokenInmediatelyAfterT))
            {
                // falla: estabamos ante una lista.
                rep.setState(StateRepair.FAILED);
                return rep;
            }

            Wrapper wrapperReparator = null;
            try {
                wrapperReparator = new Wrapper(w.cloneSubWrapper(firstTokenOptional, lastTokenOptional, new Optional()));
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(AddOptional.class.getName()).log(Level.SEVERE, null, ex);
            }

            //colocamos los parámetros de la reparación
            rep.setReparator(wrapperReparator);
            rep.setInitialItem(firstTokenOptional);
            rep.setFinalItem(lastTokenOptional);
            rep.setState(StateRepair.SUCESSFULL);
            rep.setIndexSample(t); 
            
        }
        else if(where == WebPageOperator.SAMPLE)
        {     
            int ocurrence = 0;
            while(true)
            {
                // Buscamos el token que aparecera justo detra de la supuesta opcionalidad
                lastTokenOptional =  s.search((Token) n, t, ocurrence, d);

                // Si no lo hemos encontrado paramos de buscar
                // porque no se puede crear reparacion con addoptional en el wrapper
                if(lastTokenOptional == null)
                {
                    rep.setState(StateRepair.FAILED);
                    return rep;
                }
                // Si lo hemos encontrado tenemos que ver si es una porcion
                // de codigo bien formada, sino seguimos buscando la siguiente ocurrencia
                else if(!s.isWellFormed( (Text) t, Enclosure.ENCLOSED, (Text) lastTokenOptional, Enclosure.NOT_ENCLOSED))
                     ocurrence++;
                else
                {
                    // y ahora si que nos quedamos con el token ultimo de la opcionalidad
                    itS.goTo(lastTokenOptional);
                    lastTokenOptional = (Token) itS.previous();
                    firstTokenOptional = (Token) t;
                    break;
                }
            }
            
            itS.goTo(t);
            Token tokenInmediatelyBeforeT = (Token) itS.previous();
            itS.goTo(t);
            Token tokenInmediatelyAfterT = (Token) itS.next();
            
            if(!lastTokenOptional.equals(tokenInmediatelyBeforeT) && 
                    !firstTokenOptional.equals(tokenInmediatelyAfterT))
            {
                // falla: estabamos ante una lista.
                rep.setState(StateRepair.FAILED);
                return rep;
            }
            
            //TODO : hacer que la reparacion sea ( ) y no []
            Wrapper wrapperReparator = s.cloneSubWrapper(firstTokenOptional, lastTokenOptional, new Optional());
            
            rep.setReparator(wrapperReparator);
            rep.setInitialItem(n);
            rep.setFinalItem(n);
            rep.setState(StateRepair.SUCESSFULL);
            itS.goTo(lastTokenOptional);
            rep.setIndexSample((Token) itS.next()); 
        }
    
        return rep;     
    }
}

