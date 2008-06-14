/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner;

import roadrunner.node.Item;

/**
 *
 * @author delawen
 */
public interface EdibleIterator
{
    public boolean goTo (Item objeto);
    public boolean isNext (Item o);
    public Object next();
    public Object previous();
    public boolean hasNext();
}
