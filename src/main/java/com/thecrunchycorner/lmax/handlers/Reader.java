package com.thecrunchycorner.lmax.handlers;

import com.thecrunchycorner.lmax.buffer.Message;

public interface Reader<E extends Message> {
    int getBufferId();

    E read(int pos);
}
