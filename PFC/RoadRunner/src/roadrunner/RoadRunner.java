package roadrunner;

import roadrunner.utils.SampleSet;
import roadrunner.utils.Sample;
import roadrunner.utils.Wrapper;


/**
 *  Class RoadRunner
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.42DD8AC2-F41F-370E-7E16-E0A2ED7AECB8]
// </editor-fold> 
public class RoadRunner {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D164D4D0-84E1-3533-B62C-9B31203173F9]
    // </editor-fold> 
    private Wrapper wrapper;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.28A47C6B-CD45-3E33-1C49-746DD1E29CDC]
    // </editor-fold> 
    private SampleSet SampleSet;

    /**
     *  Constructors
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.ECE8C196-D3D5-C214-8CBB-46C4B31BB705]
    // </editor-fold> 
    public RoadRunner () {
    }

    /**
     *  @return       Wrapper
     *                       @param        ss
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4001CAAC-86EE-617A-A827-1AB9623774A4]
    // </editor-fold> 
    public Wrapper process (SampleSet ss) {
        return null;
    }

    /**
     *  Set the value of wrapper
     *                       @param newVar the new value of wrapper
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.CC2F9EE0-D26B-BF33-F73E-5E82847A6C78]
    // </editor-fold> 
    private void setWrapper (Wrapper newVar) {
    }

    /**
     *  Get the value of wrapper
     *                       @return the value of wrapper
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.AB71CA95-1184-902F-2519-6C05C8DD1EE4]
    // </editor-fold> 
    private Wrapper getWrapper () {
        return null;
    }

    /**
     *  Set the value of Sample
     *                       @param newVar the new value of Sample
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.24223CF4-D5BC-EB6A-5F72-F9BD3E75B152]
    // </editor-fold> 
    private void setSample (Sample newVar) {
    }

    /**
     *  Get the value of Sample
     *                       @return the value of Sample
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5C1D1C06-3CFE-F427-F5B9-20929E12C794]
    // </editor-fold> 
    private Sample getSample () {
        return null;
    }

    /**
     *  Set the value of SampleSet
     *                       @param newVar the new value of SampleSet
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4159A21B-0CC9-DFF6-74CF-C8E2A51D1395]
    // </editor-fold> 
    private void setSampleSet (SampleSet newVar) {
    }

    /**
     *  Get the value of SampleSet
     *                       @return the value of SampleSet
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.99321F85-CC17-98AB-0011-CCA00B47CED1]
    // </editor-fold> 
    private SampleSet getSampleSet () {
        return null;
    }

    /*
     *  @return       Wrapper
     *                       @param        s
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1561F94F-C3B9-2FEE-DFBA-E72655F2F459]
    // </editor-fold> 
    private Wrapper process (Sample s) {
        return null;
    }
    
    public enum ExitLevel {WARNING,NOTHING,CONTINUE, EXIT, SLEEPandCONTINUE, SLEEPandEXIT}; 
    public static void debug(String message,  ExitLevel e)
    {
        switch(e)
        {
            case EXIT:
                System.err.println("Error & exit: "+message);
                System.exit(-1);
                break;
            case NOTHING:
                break;
            case WARNING:
                System.err.println("Warning: "+message);
                break;
            case CONTINUE:
                System.err.println("Error & continue: "+message);
                break;
            case SLEEPandCONTINUE:
                System.err.println("Error & sleep and continue: "+ message);
                try{ Thread.sleep(5);} catch (Exception ex) {}
                break;
            case SLEEPandEXIT:
                System.err.println("Error & sleep and exit: "+ message);
                try{ Thread.sleep(5);} catch (Exception ex) {}
                System.exit(-1);
                break;
        }
    }

}

