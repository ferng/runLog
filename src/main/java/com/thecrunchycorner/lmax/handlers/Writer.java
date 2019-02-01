package com.thecrunchycorner.lmax.handlers;

import com.thecrunchycorner.lmax.buffer.Message;

public interface Writer {
    int getBufferId();

    void write(int pos, Message msg);
}
