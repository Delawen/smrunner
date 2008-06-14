package roadrunner.utils;

import java.util.ArrayList; 

/**
 *  Class SampleSet
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.C0042C8B-E96D-AC6C-675A-63EA52738B5D]
// </editor-fold> 
public class SampleSet {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5641DB21-58F1-D770-83AE-34E574F5FBA8]
    // </editor-fold> 
    private ArrayList<Sample> samples;

    /**
     *  Fields
     *                    Constructors
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.FBC9FB0F-F5BA-AF61-98CB-661381B62113]
    // </editor-fold> 
    public SampleSet () {
    }

    /**
     *  @return       Sample
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.280620B0-9E48-3484-CA90-5B5E60364DC1]
    // </editor-fold> 
    public Sample getNextSample () {
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.CAE4629D-C9F4-7099-74FF-2E42020D7963]
    // </editor-fold> 
    public ArrayList<Sample> getSample () {
        return samples;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.5A7079E4-7EA0-1E98-6AA5-34CA26B0D067]
    // </editor-fold> 
    public void setSample (ArrayList<Sample> val) {
        this.samples = val;
    }

}

