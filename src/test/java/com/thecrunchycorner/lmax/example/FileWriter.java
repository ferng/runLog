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

public class FileWriter implements Writer<Message> {
    private static Logger LOGGER = LogManager.getLogger(FileWriter.class);

    private BufferedWriter writer;
    private int bufferId;

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

    @Override
    public int getBufferId() {
        return this.bufferId;
    }

    @Override
    public void write(int pos, Message msg) {
        write(msg);
    }

    private void write(Message msg) {
        try {
            String output = (String) msg.getPayload();
            writer.write(output);
            LOGGER.debug("line written: {}", output );
        } catch (Throwable e) {
            LOGGER.error("error writing to file", e);
        }
    }

}
