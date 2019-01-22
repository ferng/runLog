package com.thecrunchycorner.lmax.handlers;

public interface Reader<E> {
    int getBufferId();

    E read(int pos);
}
