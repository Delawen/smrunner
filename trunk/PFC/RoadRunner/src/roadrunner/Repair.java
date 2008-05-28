package roadrunner;

import roadrunner.node.Item; 

/**
 *  Class Reparator
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.F6F4E641-5285-CDA4-51E7-851FD43DDF02]
// </editor-fold> 
public class Repair {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5B7BE72A-6E68-D633-2696-9863FFE14726]
    // </editor-fold> 
    private int indexSample;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.709BBBCC-469F-1E73-D246-CA2EC5B65DCA]
    // </editor-fold> 
    private Item finalItem;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B5BB2F2F-0B18-0478-6ABB-B06AECFBFE2C]
    // </editor-fold> 
    private Item initialItem;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.55AE72F6-4C3C-C530-9E7B-27C4D8BA6C1C]
    // </editor-fold> 
    private Repair innerReparations;

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
    public Repair () {
    }

    /**
     *  @return       Item
     *                       @param        item
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.17049E1D-56BE-8651-8071-C93CB6101A5A]
    // </editor-fold> 
    public Item apply () {
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.46E63F25-89E2-0803-1B93-57CDFA0309D6]
    // </editor-fold> 
    public StateRepair getState () {
        return State;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.6FFA022A-5713-ABD7-4D04-29305EE409BF]
    // </editor-fold> 
    public boolean setState (StateRepair val) {
        this.State = val;
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.F8C5773A-3751-7A4B-0815-C91D288CE10D]
    // </editor-fold> 
    public int getIndexSample () {
        return indexSample;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.2E3AE83B-3308-8319-5462-98D5283330FA]
    // </editor-fold> 
    public boolean setIndexSample (int val) {
        this.indexSample = val;
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.87911518-58E4-2FCF-5886-CA5ED9B31BE8]
    // </editor-fold> 
    public Repair getInnerReparations () {
        return innerReparations;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.84994080-E1F2-8BD7-3EE1-BAEC06ACBA2B]
    // </editor-fold> 
    public boolean setInnerReparations (Repair val) {
        this.innerReparations = val;
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
    private boolean setInitialItem (Item newVar) {
        return true;
    }

    /**
     *  Get the value of initialItem
     *                       @return the value of initialItem
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4DE02864-0651-C152-EA19-C84F0661A4EF]
    // </editor-fold> 
    private Item getInitialItem () {
        return null;
    }

    /**
     *  Set the value of finalItem
     *                       @param newVar the new value of finalItem
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A612CF07-2E67-D0AE-AF61-225418511B49]
    // </editor-fold> 
    private boolean setFinalItem (Item newVar) {
        return true;
    }

    /**
     *  Get the value of finalItem
     *                       @return the value of finalItem
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3E292062-99DF-C5E1-97FE-BF6BFE7CE86F]
    // </editor-fold> 
    private Item getFinalItem () {
        return null;
    }

    /**
     *  Set the value of reparacion
     *                       @param newVar the new value of reparacion
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.032345DD-827C-141D-F3F7-0EF1EF04133C]
    // </editor-fold> 
    private boolean setItem (Item newVar) {
        return true;
    }

    /**
     *  Get the value of reparacion
     *                       @return the value of reparacion
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.B392E422-8B1C-E2FA-01F3-870D92AA403D]
    // </editor-fold> 
    private Item getItem () {
        return initialItem;
    }

}

