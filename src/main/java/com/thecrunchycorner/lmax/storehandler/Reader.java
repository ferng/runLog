package com.thecrunchycorner.lmax.storehandler;

public interface Reader<E> {
    public E read(int pos);
}
