package com.thecrunchycorner.lmax.example;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import com.thecrunchycorner.lmax.buffer.Message;
import com.thecrunchycorner.lmax.handlers.Writer;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Simple file writer which sends the output of the whole workflow to destination.dat in this
 * directory
 */
public class FileWriter implements Writer {
    private static Logger LOGGER = LogManager.getLogger(FileWriter.class);

    private BufferedWriter writer;
    private int bufferId;


    /**
     * Constructor
     *
     * @param bufferId the if that this buffer works off of, used to internally group processes
     * and properties for position calculations
     */
    FileWriter(int bufferId) {
        try {
            Path path = Paths.get("src", "test", "java",
                    "com", "thecrunchycorner", "lmax", "example",
                    "destination.dat").toAbsolutePath();
            writer = Files.newBufferedWriter(path, CREATE, WRITE);
            this.bufferId = bufferId;
        } catch (IOException e) {
            LOGGER.error("couldn't create destination file", e);
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
     * @param pos what position in the buffer do we want to write data to, not really applicable
     * to a file so it can actually be anything
     * @param msg what's the message to to write to this file?
     */
    @Override
    public void write(int pos, Message msg) {
        write(msg);
    }


    private void write(Message msg) {
        try {
            String output = (String) msg.getPayload();
            writer.write(output);
            writer.flush();
            LOGGER.debug("line written: {}", output);
        } catch (Throwable e) {
            LOGGER.error("error writing to file", e);
        }
    }

}
