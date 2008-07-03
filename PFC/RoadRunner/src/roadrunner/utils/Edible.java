/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.utils;

import SMTree.utils.Enclosure;
import roadrunner.iterator.EdibleIterator;
import roadrunner.node.Item;
import roadrunner.node.List;
import roadrunner.node.Token;
import roadrunner.operator.DirectionOperator;

/**
 *
 * @author delawen
 */
public interface Edible 
{

    public Wrapper cloneSubWrapper(Token firstTokenSquare, Token lastTokenSquare, List list);
    public Edible getAsWrapper(Token firstTokenOptional, Enclosure ENCLOSED, Token lastTokenOptional, Enclosure ENCLOSED0, Item i);
    public boolean isWellFormed(Token text, Enclosure ENCLOSED, Token text0, Enclosure NOT_ENCLOSED);
    public EdibleIterator iterator(Class type);
    public Token search(Token token, Token t, int ocurrence, DirectionOperator d);
}
