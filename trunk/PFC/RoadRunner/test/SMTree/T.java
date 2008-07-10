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
        
        private T(int id)
        {
            this();
            this.id = id;
        }
        
    @Override
        public String toString()
        {
            return "{" + this.id + "}";
        }
        
        
    @Override
        public T clone()
        {
            T t = new T(this.getId());
            
            return t;
        }
        
        public int getId()
        {
            return id;
        }
        
    @Override
        public boolean equals(Object o)
        {
            return (((T)o).getId() == this.id);
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