package com.thecrunchycorner.runlog.msgstore;

public interface Store<E> {

    E set(int index, E element);

}
