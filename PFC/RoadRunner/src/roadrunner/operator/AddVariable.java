package roadrunner.operator;

import SMTree.SMTree;
import roadrunner.*;
import roadrunner.node.Item;
import roadrunner.node.Text;
import roadrunner.node.Token;
import roadrunner.node.Token.Type;

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

    @Override
    public Repair apply(Mismatch m, DirectionOperator d, WebPageOperator where) 
    {
        Repair reparacion = new Repair(m);
        Item n = m.getNode();
        Token t = m.getToken();
        if(! (n instanceof Text) && (t.isText()))
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
            Sample s = m.getSample();
            Sample.webPageIterator it;
            if(d == DirectionOperator.DOWNWARDS)
                it = s.iterator(Sample.webPageForwardIterator.class);
            else
                it = s.iterator(Sample.webPageBackwardIterator.class);
            
            it.goTo(t);
            
            reparacion.setIndexSample(it.next());
            
            //setState:
            reparacion.setState(StateRepair.SUCESSFULL);
            
            //reparator:
            Token newToken = new Token("");
            newToken.setType(Type.VARIABLE);
            reparacion.setReparator(new Wrapper(new SMTree<Item>(newToken)));
        }
            
        return reparacion;
    }


}

