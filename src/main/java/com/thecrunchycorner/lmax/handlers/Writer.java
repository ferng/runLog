package com.thecrunchycorner.lmax.handlers;

public interface Writer<E> {
    int getBufferId();

    void write(int pos, E msg) throws IllegalArgumentException;
}
