package com.thecrunchycorner.lmax.handlers;

import com.thecrunchycorner.lmax.buffer.Message;

public interface Reader {
    int getBufferId();

    Message read(int pos);
}
