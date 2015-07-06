package com.thecrunchycorner.runlog.ringbufferprocessor;

import com.thecrunchycorner.runlog.ringbufferaccess.Message;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.MsgType;

import java.util.Observable;
import java.util.Observer;

public class ObserverProcessor implements Observer {
    private Message msg = new Message(MsgType.NULL_MSG, 0);

    public final Message getMsg() {
        return msg;
    }

    public final void update(Observable o, Object arg) {
        msg = (Message)arg;
    }

}
