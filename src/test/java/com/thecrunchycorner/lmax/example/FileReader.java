package com.thecrunchycorner.lmax.example;

import com.thecrunchycorner.lmax.buffer.Message;
import com.thecrunchycorner.lmax.handlers.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Simple file reader which gets the input for the whole workflow from source.dat in this
 * directory
 */
public class FileReader implements Reader {
    private static Logger LOGGER = LogManager.getLogger(FileReader.class);

    private BufferedReader reader;
    private int bufferId;


    /**
     * Constructor
     *
     * @param bufferId the if that this buffer works off of, used to internally group processes
     * and properties for position calculations
     */
    FileReader(int bufferId) {
        try {
            Path path = Paths.get("src", "test", "java",
                    "com", "thecrunchycorner", "lmax", "example",
                    "source.dat").toAbsolutePath();
            LOGGER.debug(path);
            reader = Files.newBufferedReader(path);
            this.bufferId = bufferId;
        } catch (IOException e) {
            LOGGER.error("source file is missing, {}", e);
        }
    }


    /**
     * Get this the id for the buffer this works with
     *
     * @return the id
     */
    @Override
    public int getBufferId() {
        return this.bufferId;
    }


    /**
     *
     * @param pos the position on the buffer to read from. not really applicable
     * to a file so it can actually be anything
     * @return the message we just read
     */
    @Override
    public Message read(int pos) {
        return read();
    }


    private Message read() {
        Message line = null;
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            LOGGER.info("Thread terminated {}", e);
        }
        try {
            line = new Message(reader.readLine());
            LOGGER.debug("line read: {}", line.getPayload());
        } catch (IOException e) {
            LOGGER.error("error reading file {}", e);
        }
        return line;
    }
}
