/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SMTree;

/**
 *
 * @author delawen
 */
  public class T
    {
        private int id;
        
        public T()
        {
            super();
            id = Contador.getId();
        }
        
        public int getId()
        {
            return id;
        }
        
        public boolean equals(T o)
        {
            return (o.getId() == this.id);
        }
   private static class Contador
    {
        private static int id;
        
        private Contador()
        {
            id = 0;
        }
        
        static protected int getId()
        {
            return id++;
        }
    }
   }