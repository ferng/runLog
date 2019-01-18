package com.thecrunchycorner.lmax.handlers;

public interface Reader<E> {
    E read(int pos);
}
