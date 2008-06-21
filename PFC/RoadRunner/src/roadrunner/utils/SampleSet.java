package roadrunner.utils;

import java.util.ArrayList; 

/**
 *  Class SampleSet
 */

public class SampleSet {

    
    private ArrayList<Sample> samples;
    private int indice;

    /**
     *  Fields
     *                    Constructors
     */
    
    public SampleSet () 
    {
        this.samples = new ArrayList<Sample>();
        this.indice = 0;
    }

    /**
     *  @return       Sample
     */
    
    public Sample getNextSample () 
    {
        if(this.indice < this.samples.size())
            return this.samples.get(this.indice++);
        else
            return null;
    }
    
    public boolean hasNext()
    {
        return (this.samples.size() > this.indice);
    }

    
    public ArrayList<Sample> getSamples() 
    {
        return this.samples;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.5A7079E4-7EA0-1E98-6AA5-34CA26B0D067]
    // </editor-fold> 
    public void setSamples (ArrayList<Sample> val) 
    {
        this.samples = val;
    }
    
    void addSample(Sample s)
    {
        this.samples.add(s);
    }

}

