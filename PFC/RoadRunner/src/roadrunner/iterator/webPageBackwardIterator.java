/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package roadrunner.iterator;

import SMTree.SMTree;
import SMTree.SMTreeNode;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import roadrunner.node.Item;
import roadrunner.node.Text;
import roadrunner.node.Token;
import roadrunner.utils.Sample.webPageIterator;

/**
 *
 * @author delawen
 */
public class webPageBackwardIterator implements webPageIterator
{
    private ListIterator<Token> it = null;
    private List<Token> tokens = null;
    private Token lastTokenWebPage = null;

    public webPageBackwardIterator()
    {
        super();
      /*  
        //PRUEBA
        List l = new LinkedList();
        Text a = new Text("wa");
        Text b = new Text("we");
        Text c = new Text("wi");
        Text d = new Text("wo");
        Text e = new Text("wu");
        l.add(a);
        l.add(b);
        l.add(c);
        l.add(d);
        l.add(e);
        ListIterator it1 = l.listIterator();
        
        while(it1.hasPrevious())
        {
            System.out.println(it1.next());
        }
        
        int index = l.lastIndexOf(c);
        
        it1 = l.listIterator(index);
        
        System.out.println("Index c: "+index+"->"+it1.next());
        */
    }
    
    public void init(List<Token> tokens)
    {
        this.it = tokens.listIterator(tokens.size());
        this.tokens = tokens;
    }
    public void goTo(Item t)
    {
//        if(!tokens.contains((Token)t))
//            return false;
//        
//        int index = tokens.lastIndexOf(t);
//        
//        this.it = tokens.listIterator(index+1);
//        
//        return true;

        boolean result=false;
        
        it = tokens.listIterator();
        while(it.hasNext() && !result)
        {
            if(t==it.next())
            {
                if(it.hasNext())
                    lastTokenWebPage = it.next();
                else
                {
                    this.it = tokens.listIterator(tokens.size());
                    lastTokenWebPage = null;
                }
                result = true;
            }
        }
        assert(result == true);

    }

    public boolean hasNext() {
        return it.hasPrevious();
    }

    public Token next() {
        lastTokenWebPage = it.previous();
        return lastTokenWebPage;
    }

    public boolean hasPrevious() {
       return it.hasNext();
    }

    public Token previous() {
        lastTokenWebPage = it.next();
       return lastTokenWebPage;
    }

    public boolean isNext(Item o) 
    {
        Item i = this.next();
        if(i.equals(o))
            return true;
        this.previous();
        return false;
    }

    public void setRootIterator(SMTreeNode<Item> root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTree(SMTree<Item> treeWrapper) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object nextAll()
    {
        return this.next();
    }

    public Object previous(boolean optional) {
        return previous();
    }

    public Object nextObject(boolean optional)
    {
        return nextObject();
    }

    public Object nextObject()
    {
        return next();
    }

    @Override
    public String toString()
    {   
        if(lastTokenWebPage == null)
            return "";
        
        String result = "....";
        ListIterator itaux = tokens.listIterator(tokens.indexOf(lastTokenWebPage));

        for(int i=0; i<5 && itaux.hasPrevious(); i++)
        {
            result += itaux.previous();
        }
        
        itaux = tokens.listIterator(tokens.indexOf(lastTokenWebPage));
        
        result += "<-Next:::Previous->["+itaux.next()+"]:::";
        
        for(int i=0; i<5 && itaux.hasNext(); i++)
        {
            result += itaux.next();  
        }           
        
        result += "....";   
        
        return result;
    }
}