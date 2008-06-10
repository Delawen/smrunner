package roadrunner.operator;

import SMTree.BackwardItemIterator;
import SMTree.Enclosure;
import SMTree.ForwardItemIterator;
import SMTree.WrapperIterator;
import roadrunner.Mismatch; 
import roadrunner.Repair; 
import roadrunner.Sample;
import roadrunner.StateRepair;
import roadrunner.Wrapper;
import roadrunner.node.Item;
import roadrunner.node.Optional;
import roadrunner.node.Text;
import roadrunner.node.Token;

/**
 *  Class addHook
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.E210968F-FE0F-DA54-A32E-77F957F292B1]
// </editor-fold> 
public class AddOptional extends IOperator {

    @Override
    public Repair apply(Mismatch m, DirectionOperator d, WebPageOperator where) {
       
        /**
         * ¿HOOK NUNCA PUEDE TENER REPARACIONES INTERNAS?
         * 
         */
       
        Sample s = m.getSample();
        Wrapper w = m.getWrapper();
        Token t = m.getToken();
        Item n = m.getNode();
        WrapperIterator<Item> itW = null;
        Sample.webPageIterator itS = null;
        
        Repair rep = new Repair(m);
        rep.setState(StateRepair.BUILDING);
        
        Token firstTokenOptional=null, lastTokenOptional=null;
        
        if(d == DirectionOperator.DOWNWARDS)
        {
            itW = w.iterator(ForwardItemIterator.class);
            itS = s.iterator(Sample.webPageForwardIterator.class);
        }
        else if(d == DirectionOperator.UPWARDS)
        {
            itW = w.iterator(BackwardItemIterator.class);
            itS = s.iterator(Sample.webPageBackwardIterator.class);
        }
        
        if(where == WebPageOperator.WRAPPER)
        {
            int ocurrence = 0;
            boolean finded = true;
            
            while(finded)
            {
                // Buscamos el token que aparecera justo detra de la supuesta opcionalidad
                lastTokenOptional =  w.search(t, (Token) n, ocurrence, d);

                // Si no lo hemos encontrado paramos de buscar
                // porque no se puede crear reparacion con addoptional en el wrapper
                if(lastTokenOptional == null)
                {
                    finded = false;
                }
                // Si lo hemos encontrado tenemos que ver si es una porcion
                // de codigo bien formada, sino seguimos buscando la siguiente ocurrencia
                else if(!w.isWellFormed( (Text)n, Enclosure.ENCLOSED, (Text)lastTokenOptional, Enclosure.NOT_ENCLOSED))
                     ocurrence++;
                else
                {
                    finded = true;
                    // y ahora si que nos quedamos con el token ultimo de la opcionalidad
                    itW.goTo(lastTokenOptional);
                    lastTokenOptional = (Token) itW.previous();
                    firstTokenOptional = (Token) n;
                }
            }
            
            if(!finded)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }
            
            
            // Creamos el nuevo WrapperReparator

            //TODO : Revisar la logica que tiene esto porque algo no me convence...
            
            /** Queremos evitar convertir un elemento de una lista en un hook
             *      W                       S
             *      <b>1</b>                <b>1</b>
             *      m::<b>:: 2</b>          m::<a>:: 1<a/>
             *      <a>1</a>
             */
            
            itS.goTo(t);
            Token tokenInmediatelyBeforeT = itS.previous();
            itS.goTo(t);
            Token tokenInmediatelyAfterT = itS.next();
            
            if(!lastTokenOptional.equals(tokenInmediatelyBeforeT) && 
                    !firstTokenOptional.equals(tokenInmediatelyAfterT))
            {
                // falla: estabamos ante una lista.
                rep.setState(StateRepair.FAILED);
                return rep;
            }

            Wrapper wrapperReparator = new Wrapper(
                    w.cloneSubWrapper(firstTokenOptional, lastTokenOptional , new Optional()));

            rep.setReparator(wrapperReparator);
            rep.setFinalItem(lastTokenOptional);
            rep.setState(StateRepair.SUCESSFULL);
            rep.setIndexSample(null); // TODO no se que se pone aquis
        }
        else if(where == WebPageOperator.SAMPLE)
        {
         ;
        }
    
        return rep;     
    }
}

