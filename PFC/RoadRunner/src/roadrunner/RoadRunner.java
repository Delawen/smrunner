package roadrunner;

import java.util.ArrayList;
import roadrunner.node.Item;
import roadrunner.operator.DirectionOperator;
import roadrunner.operator.Operator;
import roadrunner.utils.*;


/**
 *  Class RoadRunner
 */
public class RoadRunner {

    private Wrapper wrapper = null;
    private SampleSet sampleSet = null;

    /**
     *  Constructors
     */
    public RoadRunner () 
    {
        this(null, new SampleSet());
    }
    
    public RoadRunner(Wrapper wrapper)
    {
        this(wrapper, new SampleSet());
    }
    
    public RoadRunner(SampleSet sampleSet)
    {
        this(null, sampleSet);
    }
    
    public RoadRunner(Wrapper wrapper, SampleSet sampleSet)
    {
        super();
        this.sampleSet = sampleSet;
        this.wrapper = wrapper;
    }
    
    public RoadRunner(String[] samples)
    {
        this();
        ArrayList<Sample> list = new ArrayList<Sample>();
        
        for(String s : samples)
        {
            Sample sample = new Sample(s);
            list.add(sample);
        }
        this.sampleSet.setSamples(list);
    }

    /**
     * Process a sampleset building the wrapper
     * 
     *  @return       Wrapper
     *  @param        sampleSet
     */
    public Wrapper process (SampleSet sampleSet) 
    {
        if(sampleSet == null)
            return null;
        
        //Procesamos uno a uno todos los sample:
        while(sampleSet.hasNext())
        {
            process(sampleSet.getNextSample());
        }
        
        //Devolvemos el wrapper
        return this.wrapper;
    }

    public Wrapper process () 
    {
        //Procesamos uno a uno todos los sample:
        while(this.sampleSet.hasNext())
        {
            process(this.sampleSet.getNextSample());
        }
        
        //Devolvemos el wrapper
        return this.wrapper;
    }

    
    private void process(Sample sample) 
    {
        Mismatch m = null;
        
        //Si el wrapper es nulo es porque es el primer sample
        if(this.wrapper == null)
            this.wrapper = sample.getAsWrapper();
        else
        {
            Item t = sample.getToken(0);
            Item n = this.wrapper.getTree().getRootObject();
            while(true) //Procesamos todo el sample
            {
                m = wrapper.eat(sample, t, n, DirectionOperator.DOWNWARDS);

                //Si al comer se ha provocado un Mismatch:
                if(m != null)
                {
                    Operator op = new Operator();
                    Repair reparacion = op.repair(m);
                    if(reparacion.getState() == StateRepair.SUCESSFULL)
                        reparacion.apply();
                    else
                        throw new RuntimeException("I couldn't repair a mismatch.");
                    t = reparacion.getIndexSample();
                    n = reparacion.getReparator().getTree().getRootObject();
                    if(n instanceof roadrunner.node.List)
                        ((roadrunner.node.List)n).setAccessed(true);
                }
                else break; //Hemos terminado
            }
        }
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

