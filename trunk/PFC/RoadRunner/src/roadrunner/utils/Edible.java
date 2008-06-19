/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.utils;

import SMTree.utils.Enclosure;
import roadrunner.iterator.EdibleIterator;
import roadrunner.node.List;
import roadrunner.node.Optional;
import roadrunner.node.Text;
import roadrunner.node.Token;
import roadrunner.operator.DirectionOperator;

/**
 *
 * @author delawen
 */
public interface Edible 
{

    public Edible getAsWrapper(Token firstTokenOptional, Enclosure ENCLOSED, Token lastTokenOptional, Enclosure ENCLOSED0, Optional optional);
    public boolean isWellFormed(Text text, Enclosure ENCLOSED, Text text0, Enclosure NOT_ENCLOSED);
    public EdibleIterator iterator(Class type);
    public Token search(Token token, Token t, int ocurrence, DirectionOperator d);
}
