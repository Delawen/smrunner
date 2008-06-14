package roadrunner.operator;

import roadrunner.utils.Mismatch; 
import roadrunner.utils.Repair; 

/**
 *  Class operator
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.CDF02508-804E-A612-F272-6F8022CF284C]
// </editor-fold> 
public abstract class IOperator {

    /**
     *  Fields
     *                    Constructors
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.87AA7B19-6297-70CB-9BAA-4097A1AB6047]
    // </editor-fold> 
    public IOperator () {
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
    public abstract Repair apply (Mismatch m, DirectionOperator d, WebPageOperator where);


}

