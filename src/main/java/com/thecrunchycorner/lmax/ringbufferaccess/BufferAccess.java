package com.thecrunchycorner.lmax.ringbufferaccess;

import java.util.Optional;

public interface BufferAccess<E> {
    public Optional<E> process();
}
