package roadrunner.operator;

import SMTree.iterator.BackwardIterator;
import SMTree.iterator.ForwardIterator;
import SMTree.utils.Enclosure;
import roadrunner.iterator.EdibleIterator;
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
            itS = s.iterator(Sample.webPageForwardIterator.class);
        }
        else if(d == DirectionOperator.UPWARDS)
        {
            itW = w.iterator(BackwardIterator.class);
            itS = s.iterator(Sample.webPageBackwardIterator.class);
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
            Wrapper squareW = ((Wrapper)w).cloneSubWrapper(firstTokenSquare, lastTokenSquare, new List());     
                    
            Mismatch m1;
            boolean isList = false;
            do
            {
                //TODO: como le digo a squareW que se coma a Square?
                m1 = squareW.eat((Sample) s);
                if(m1 != null && m1.getNode() != null) //TODO forme parte de uno de los elementos de la opcionalidad)
                {
                    Operator op = new Operator();
                    IOperator nextOperator = op.getNextOperator();
                    Repair repAux=null;
                    while(nextOperator != null)
                    {
                        if(d == DirectionOperator.DOWNWARDS)
                            repAux = nextOperator.apply(m1, DirectionOperator.UPWARDS);
                        else
                            repAux = nextOperator.apply(m1, DirectionOperator.DOWNWARDS);

                        if(repAux.getState() == StateRepair.SUCESSFULL)
                        {
                            repAux.apply();
                            break;
                        }
                        repAux = null;
                    }

                    if(repAux == null){
                        rep.setState(StateRepair.FAILED);
                        return rep;              
                    }
                }
            
            }while(isList && m1.getNode() != null);// forme parte de uno de los elementos de la opcionalidad);

            
        }
        else if(where == WebPageOperator.SAMPLE)
        {     
           
        }
    
        return rep;     
    }

}

