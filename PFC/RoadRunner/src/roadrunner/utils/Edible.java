/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.utils;

import roadrunner.iterator.EdibleIterator;

/**
 *
 * @author delawen
 */
public interface Edible 
{
    public EdibleIterator iterator(Class type);
}
