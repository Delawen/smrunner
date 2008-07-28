package roadrunner.node;

public abstract class Token extends Item implements Cloneable{

    public abstract boolean match (Item i);

    public abstract boolean equals(Object i);
}
