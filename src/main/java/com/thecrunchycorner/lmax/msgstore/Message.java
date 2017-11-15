package com.thecrunchycorner.lmax.msgstore;

import java.util.UUID;

/**
 * Lmax communication is based on messages, although the stores are unconcerned about the type of
 * data being inserted the rest of the application does care.
 */
public class Message {
    private final UUID id = UUID.randomUUID();
    private final Object payload;

    /**
     * Create a new message.
     *
     * @param payload what will the message hold? what will we be processing?
     */
    public Message(final Object payload) {
        this.payload = payload;
    }


    /**
     * Get this message's ID
     *
     * @return a UUID which is used to identify this message
     */
    public final UUID getId() {
        return id;
    }


    /**
     * Get the payload the message holds. It's all about the payload man.
     *
     * @return the payload, well it could be anything at all.
     */
    public final Object getPayload() {
        return payload;
    }
}
