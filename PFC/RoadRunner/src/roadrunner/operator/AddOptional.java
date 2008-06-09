package roadrunner.operator;

import SMTree.Enclosure;
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
        
        
        Repair rep = new Repair(m);
        rep.setState(StateRepair.BUILDING);
        
        if(where == WebPageOperator.WRAPPER)
        {
            int ocurrence = 0;
            boolean finded = true;
            Token lastTokenOptional;
            
            while(finded)
            {
                // Buscamos el ultimo token que forma la opcionalidad en el wrapper
                lastTokenOptional =  w.search(t, (Token) n, ocurrence, DirectionOperator.DOWNWARDS);

                // Si no lo hemos encontrado paramos de buscar
                // porque no se puede crear reparacion con addoptional en el wrapper
                if(lastTokenOptional == null)
                {
                    finded = false;
                }
                // Si lo hemos encontrado tenemos que ver si es una porcion
                // de codigo bien formada, sino seguimos buscnado la siguiente ocurrencia
                else if(!w.isWellFormed( (Text)n, Enclosure.ENCLOSED, (Text)lastTokenOptional, Enclosure.NOT_ENCLOSED))
                     ocurrence++;
                else
                    finded = true;
            }
            
            if(!finded)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }
            // Creamos el nuevo WrapperReparator

            //TODO : Hacer lo de que el ultimo token del square no sea blablabla

            Wrapper wrapperReparator = new Wrapper(
                    w.cloneSubWrapper(n, lastTokenOptional.previous() , new Optional()));

            rep.setReparator(wrapperReparator);
            rep.setFinalItem(lastTokenOptional.previous());
            rep.setState(StateRepair.SUCESSFULL);
            rep.setIndexSample(....); // no se que se pone aquis
        }
        else if(where == WebPageOperator.SAMPLE)
        {
         ;
        }
     
        return rep;     
    }
}

