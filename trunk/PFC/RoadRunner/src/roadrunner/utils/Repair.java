package roadrunner.utils;

import roadrunner.*;
import SMTree.utils.Enclosure;
import roadrunner.node.Item; 
import roadrunner.node.Token;

/**
 *  Class Reparator
 */

public class Repair {
    private Token indexSample;

    private Item finalItem;

    private Item initialItem;

    private Wrapper toRepair;

    private Wrapper reparator;

    private StateRepair State;
    
    private Enclosure initialEnclosure;
    
    private Enclosure finalEnclosure;

    
    public Repair (Mismatch m) 
    {
        this.toRepair = m.getWrapper();
        this.State = State.BUILDING;
        this.finalItem = null;
        this.indexSample = m.getToken();
        this.initialItem = m.getNode();
        this.reparator = new Wrapper();
        this.initialEnclosure = Enclosure.ENCLOSED;
        this.finalEnclosure = Enclosure.ENCLOSED;
    }

    public boolean apply () 
    {
        if(this.getState() == State.SUCESSFULL)
            return this.toRepair.substitute(initialItem, initialEnclosure, finalItem, finalEnclosure, this.reparator.treeWrapper);
        else
            return false;
    }
    public StateRepair getState () {
        return State;
    }
    
    public void setState(StateRepair s)
    {
        this.State = s;
    }

    public Token getIndexSample () {
        return indexSample;
    }

    public boolean setIndexSample (Token val) {
        this.indexSample = val;
        return true;
    }
    public Wrapper getReparator () {
        return reparator;
    }

    public boolean setReparator (Wrapper val) {
        this.reparator = val;
        return true;
    }

    public Wrapper getToRepair () {
        return toRepair;
    }

    public boolean setToRepair (Wrapper val) {
        this.toRepair = val;
        return true;
    }

    public boolean setInitialItem (Item newVar) 
    {
        this.initialItem = newVar;
        return true;
    }

    public Item getInitialItem () {
        return this.initialItem;
    }
    
    public boolean setFinalItem (Item newVar) {
        this.finalItem = newVar;
        return true;
    }
    
    public Item getFinalItem () {
        return this.finalItem;
    }

    public Enclosure getInitialEnclosure() {
        return initialEnclosure;
    }

    public void setInitialEnclosure(Enclosure initialEnclosure) {
        this.initialEnclosure = initialEnclosure;
    }

    public Enclosure getFinalEnclosure() {
        return finalEnclosure;
    }

    public void setFinalEnclosure(Enclosure finalEnclosure) {
        this.finalEnclosure = finalEnclosure;
    }
}

