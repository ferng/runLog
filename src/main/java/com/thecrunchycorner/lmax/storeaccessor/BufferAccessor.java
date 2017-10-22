package com.thecrunchycorner.lmax.storeaccessor;

import java.util.Optional;

public interface BufferAccessor<E> {
    public Optional<E> process();
}
