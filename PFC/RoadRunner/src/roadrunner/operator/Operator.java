package roadrunner.operator;

import roadrunner.Mismatch; 
import roadrunner.Repair; 

/**
 *  Class operator
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.CDF02508-804E-A612-F272-6F8022CF284C]
// </editor-fold> 
public abstract class Operator {

    /**
     *  Fields
     *                    Constructors
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.87AA7B19-6297-70CB-9BAA-4097A1AB6047]
    // </editor-fold> 
    public Operator () {
    }

    /**
     *  @return       Reparator
     *                       @param        m
     *                       @param        direccion
     *                       @param        wrapper Indica si la operación se hace en el Wrapper o en el
     *                       Sample
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F680F6DB-231A-2148-BEF4-8FC45E7A2EA8]
    // </editor-fold> 
    public abstract Repair apply (Mismatch m, Operator.Direction d, Operator.Webpage where);

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.30C91251-22F4-3291-782D-2959D6643725]
    // </editor-fold> 
    public class Unnamed extends Operator {

        /**
         *  @return Reparator
         *     @param  m
         *     @param  direccion
         *     @param  wrapper Indica si la operación se hace en el Wrapper o en el
         *     Sample
         */
        // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
        // #[regen=yes,id=DCE.4130C2F1-B7F7-91FA-9033-61C1CEED4CD5]
        // </editor-fold> 
        public Repair apply (Mismatch m, Operator.Direction d, Operator.Webpage where) {
            return null;
        }

    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A7F26EE8-FE80-40F9-F6C5-E11338B4AFAC]
    // </editor-fold> 
    public enum Direction {

        // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
        // #[regen=yes,id=DCE.B253E1B3-4797-0F65-8682-94948F3992D8]
        // </editor-fold> 
        UPWARDS,

        // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
        // #[regen=yes,id=DCE.9B064AC3-288A-3636-2493-B8DBDAC17F0E]
        // </editor-fold> 
        DOWNWARDS;


    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D18590BA-22AE-2249-EC73-4EE6E1A4F75B]
    // </editor-fold> 
    public enum Webpage {

        // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
        // #[regen=yes,id=DCE.E9B84587-EC3A-3CCB-AB89-2D4E6FE2AF92]
        // </editor-fold> 
        WRAPPER,

        // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
        // #[regen=yes,id=DCE.94CE3887-7DF9-38BD-F9FB-E70EE24DA48D]
        // </editor-fold> 
        SAMPLE;


    }

}

