package com.thecrunchycorner.lmax.ringbufferaccess;

import com.thecrunchycorner.lmax.ringbufferaccess.enums.MsgType;

import java.util.UUID;

/**
 * lmax communication is based on messages, although the stores are unconcerned about the type of data being inserted
 * the rest of the application does care.
 */
public class Message {
    private final UUID id = UUID.randomUUID();
    private final MsgType msgType;
    private final Object payload;

    /**
     * Create a new message
     * @param msgType specifies the type of message this is as defined by {@link com.thecrunchycorner.lmax.ringbufferaccess.enums.MsgType}
     * @param payload what will we be processing?
     */
    public Message(final MsgType msgType, final Object payload) {
        this.msgType = msgType;
        this.payload = payload;
    }

    public final UUID getId() {
        return id;
    }

    public final MsgType getMsgType() {
        return msgType;
    }

    public final Object getPayload() {
        return payload;
    }
}
