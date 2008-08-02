package roadrunner.operator;

import roadrunner.utils.Sample;
import roadrunner.utils.StateRepair;
import roadrunner.utils.Mismatch;
import roadrunner.utils.Wrapper;
import roadrunner.utils.Repair;
import roadrunner.*;
import roadrunner.iterator.BackwardTokenIterator;
import roadrunner.iterator.EdibleIterator;
import roadrunner.iterator.ForwardTokenIterator;
import roadrunner.iterator.webPageBackwardIterator;
import roadrunner.iterator.webPageForwardIterator;
import roadrunner.node.Item;
import roadrunner.node.Text;
import roadrunner.node.Token;
import roadrunner.node.Variable;
import roadrunner.utils.Edible;

/**
 *  Class addVariable
 * 
 * Implements the addVariable operation. That means: substitute a Text with a Variable.
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.80EF1EB2-DF19-D615-0B7A-1D3D66A616F8]
// </editor-fold> 
public class AddVariable extends IOperator 
{
    public AddVariable(WebPageOperator where)
    {
        super(where);
    }

    @Override
    public Repair apply(Mismatch m, DirectionOperator d) 
    {
        Repair reparacion = new Repair(m);
        Item n = m.getNode();
        Token t = m.getToken();
        if(!(n instanceof Text) || !(t instanceof Text))
        {
            reparacion.setState(StateRepair.FAILED);
        }
        else
        {
            //finalItem:
            reparacion.setFinalItem(n);
            
            //initialItem:
            reparacion.setInitialItem(n);
            
            //indexSample:
            Edible s = m.getSample();
            EdibleIterator it;
            if(d == DirectionOperator.DOWNWARDS)
            {
                if(s instanceof Sample) 
                    it = s.iterator(webPageForwardIterator.class);
                else
                    it = s.iterator(ForwardTokenIterator.class);
            }
            else
            {
                if(s instanceof Sample) 
                    it = s.iterator(webPageBackwardIterator.class);
                else
                    it = s.iterator(BackwardTokenIterator.class);
            }
            
            it.goTo(t);
            
            reparacion.setIndexSample((Token)it.next());
            
            //setState:
            reparacion.setState(StateRepair.SUCESSFULL);
            
            //reparator:
            reparacion.setReparator(new Wrapper(new Variable()));
        }
            
        return reparacion;
    }


}

