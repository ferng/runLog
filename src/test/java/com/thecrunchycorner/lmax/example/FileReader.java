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

public class FileReader implements Reader<Message> {
    private static Logger LOGGER = LogManager.getLogger(FileReader.class);

    private BufferedReader reader;
    private int bufferId;


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

    @Override
    public int getBufferId() {
        return this.bufferId;
    }

    @Override
    public Message read(int pos) {
        return read();
    }

    private Message read() {
        Message line = null;
        try {
            line = new Message(reader.readLine());
            LOGGER.debug("line read: {}", line.getPayload());
        } catch (IOException e) {
            LOGGER.error("error reading file {}", e);
        }
        return line;
    }


}
