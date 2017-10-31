package com.thecrunchycorner.lmax.storehandler;

import java.util.Optional;

public interface BufferHandler<E> {
    public Optional<E> process();
}
