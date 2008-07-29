/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.utils;

import SMTree.utils.Enclosure;
import roadrunner.iterator.EdibleIterator;
import roadrunner.node.Item;
import roadrunner.node.Token;
import roadrunner.operator.DirectionOperator;

/**
 *
 * @author delawen
 */
public interface Edible 
{

    public Wrapper cloneSubWrapper(Token firstTokenSquare, Token lastTokenSquare, Item item, DirectionOperator d);
    public EdibleIterator iterator(Class type);
    public Token searchWellFormed(Token token, Enclosure tokenEnclosure, Token t, Enclosure tEnclosure, DirectionOperator d);
}
