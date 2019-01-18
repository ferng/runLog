package com.thecrunchycorner.lmax.handlers;

public interface Writer<E> {
    void write(int pos, E msg) throws IllegalArgumentException;
}
