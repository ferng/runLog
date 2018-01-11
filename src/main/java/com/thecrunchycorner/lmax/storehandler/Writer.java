package com.thecrunchycorner.lmax.storehandler;

public interface Writer<E> {
    public void write(int pos, E msg);
}
