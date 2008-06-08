package roadrunner;

import roadrunner.*;
import roadrunner.Mismatch;
import SMTree.Enclosure;
import roadrunner.node.Item; 
import roadrunner.node.Token;

/**
 *  Class Reparator
 */

public class Repair {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5B7BE72A-6E68-D633-2696-9863FFE14726]
    // </editor-fold> 
    private Token indexSample;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.709BBBCC-469F-1E73-D246-CA2EC5B65DCA]
    // </editor-fold> 
    private Item finalItem;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B5BB2F2F-0B18-0478-6ABB-B06AECFBFE2C]
    // </editor-fold> 
    private Item initialItem;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.DDED8D46-033F-2822-1A4E-EC6AD7CC35B8]
    // </editor-fold> 
    private Wrapper toRepair;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E2968921-F897-CEFF-87FC-8D750697EE7B]
    // </editor-fold> 
    private Wrapper reparator;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A5388C9D-5470-5DD2-325E-8E0EC5BA1D98]
    // </editor-fold> 
    private StateRepair State;

    /**
     *  Constructors
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A9690EB5-2028-42B2-52EE-2E4436C67C8A]
    // </editor-fold> 
    public Repair (Mismatch m) 
    {
        this.toRepair = m.getWrapper();
        this.State = State.BUILDING;
        this.finalItem = null;
        this.indexSample = m.getToken();
        this.initialItem = m.getNode();
        this.reparator = new Wrapper();
    }

    /**
     *  @return       Item
     *                       @param        item
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.17049E1D-56BE-8651-8071-C93CB6101A5A]
    // </editor-fold> 
    public boolean apply () 
    {
        if(this.getState() == State.SUCESSFULL)
            return this.toRepair.substitute(initialItem, Enclosure.ENCLOSED, finalItem, Enclosure.ENCLOSED, this.reparator.treeWrapper);
        else
            return false;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.46E63F25-89E2-0803-1B93-57CDFA0309D6]
    // </editor-fold> 
    public StateRepair getState () {
        return State;
    }
    
    public void setState(StateRepair s)
    {
        this.State = s;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.F8C5773A-3751-7A4B-0815-C91D288CE10D]
    // </editor-fold> 
    public Token getIndexSample () {
        return indexSample;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.2E3AE83B-3308-8319-5462-98D5283330FA]
    // </editor-fold> 
    public boolean setIndexSample (Token val) {
        this.indexSample = val;
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.30659621-872B-9A6B-4386-9309AB76B862]
    // </editor-fold> 
    public Wrapper getReparator () {
        return reparator;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.D3E33851-FBCA-27B3-3F59-11AA9E72E376]
    // </editor-fold> 
    public boolean setReparator (Wrapper val) {
        this.reparator = val;
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.79B574CC-C2B5-1CF1-1FC3-C1BE8DE5FD9C]
    // </editor-fold> 
    public Wrapper getToRepair () {
        return toRepair;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.FEE1E648-50B0-2DC4-1558-6BF369ED54FE]
    // </editor-fold> 
    public boolean setToRepair (Wrapper val) {
        this.toRepair = val;
        return true;
    }

    /**
     *  Set the value of initialItem
     *                       @param newVar the new value of initialItem
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.0D08D228-D9B8-BC87-26A0-583C37874397]
    // </editor-fold> 
    public boolean setInitialItem (Item newVar) 
    {
        this.initialItem = newVar;
        return true;
    }

    /**
     *  Get the value of initialItem
     *                       @return the value of initialItem
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4DE02864-0651-C152-EA19-C84F0661A4EF]
    // </editor-fold> 
    public Item getInitialItem () {
        return this.initialItem;
    }

    /**
     *  Set the value of finalItem
     *                       @param newVar the new value of finalItem
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A612CF07-2E67-D0AE-AF61-225418511B49]
    // </editor-fold> 
    public boolean setFinalItem (Item newVar) {
        this.finalItem = newVar;
        return true;
    }

    /**
     *  Get the value of finalItem
     *                       @return the value of finalItem
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3E292062-99DF-C5E1-97FE-BF6BFE7CE86F]
    // </editor-fold> 
    public Item getFinalItem () {
        return this.finalItem;
    }
}

