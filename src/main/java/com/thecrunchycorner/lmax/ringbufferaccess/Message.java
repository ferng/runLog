package com.thecrunchycorner.lmax.ringbufferaccess;

import com.thecrunchycorner.lmax.ringbufferaccess.enums.MsgType;

import java.util.UUID;

/**
 * For lmax we use messages to communicate via the buffer.
 */
public class Message {
    private UUID id;
    private MsgType msgType;
    private Object payload;

    public Message(MsgType msgType, Object payload) {
        this.id = UUID.randomUUID();
        this.msgType = msgType;
        this.payload = payload;
    }

    public final UUID getId() {
        return id;
    }

    public final MsgType getType() {
        return msgType;
    }

    public final void setType(MsgType msgType) {
        this.msgType = msgType;
    }

    public final Object getPayload() {
        return payload;
    }

    public final void setPayload(Object payload) {
        this.payload = payload;
    }
}
