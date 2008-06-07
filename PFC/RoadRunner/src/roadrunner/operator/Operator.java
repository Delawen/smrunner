/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.operator;

import java.util.LinkedList;

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
                return new AddList();
            case 2:
                return new AddOptional();
            default:
                ;
        }
        
        return null;
    }
}
