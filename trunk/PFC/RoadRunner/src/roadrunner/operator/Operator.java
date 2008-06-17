/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.operator;

/**
 *
 * @author santi
 */
public class Operator {
    
    int indexOperator=-1;
    
    public IOperator getNextOperator()
    {
        indexOperator++;
        switch(indexOperator)
        {     
            case 0:
                return new AddVariable();
            case 1:
                return new AddList(WebPageOperator.WRAPPER);
            case 2:
                return new AddList(WebPageOperator.SAMPLE);
            case 3:
                return new AddOptional(WebPageOperator.WRAPPER);
            case 4:
                return new AddOptional(WebPageOperator.SAMPLE);
            default:
                ;
        }
        
        return null;
    }
}
