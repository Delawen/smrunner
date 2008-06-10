package SMTree;

public class BackwardItemIterator<T> extends WrapperIterator<T> 
{   
    private WrapperIterator<T> it;
    
    public BackwardItemIterator()
    {
        super();
    }

    @Override
    public boolean isNext(T o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T next() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasPrevious() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T previous() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

