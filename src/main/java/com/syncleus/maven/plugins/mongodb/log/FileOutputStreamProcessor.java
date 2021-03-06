/**
 * Copyright: (c) Syncleus, Inc.
 *
 * You may redistribute and modify this source code under the terms and
 * conditions of the Open Source Community License - Type C version 1.0
 * or any later version as published by Syncleus, Inc. at www.syncleus.com.
 * There should be a copy of the license included with this file. If a copy
 * of the license is not included you are granted no right to distribute or
 * otherwise use this file except through a legal and valid license. You
 * should also contact Syncleus, Inc. at the information below if you cannot
 * find a license:
 *
 * Syncleus, Inc.
 * 2604 South 12th Street
 * Philadelphia, PA 19148
 */
/*
 * Derived from APACHE LICENSE version 2.0 source as indicated at
 * https://github.com/joelittlejohn/embedmongo-maven-plugin as of 5/16/2015.
 * Original source was Copyright (c) 2012 Joe Littlejohn
 */
package com.syncleus.maven.plugins.mongodb.log;

import de.flapdoodle.embed.process.io.IStreamProcessor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileOutputStreamProcessor implements IStreamProcessor {

    private static OutputStreamWriter stream;

    private String logFile;
    private String encoding;

    public FileOutputStreamProcessor(final String logFile, final String encoding) {
        setLogFile(logFile);
        setEncoding(encoding);
    }

    @Override
    public synchronized void process(final String block) {
        try {

            if (stream == null) {
                stream = new OutputStreamWriter(new FileOutputStream(logFile), encoding);
            }

            stream.write(block);
            stream.flush();

        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onProcessed() {
        process("\n");
    }

    private void setLogFile(final String logFile) {
        if (logFile == null || logFile.trim().length() == 0) {
            throw new IllegalArgumentException("no logFile given");
        }
        this.logFile = logFile;
    }

    private void setEncoding(final String encoding) {
        if (encoding == null || encoding.trim().length() == 0) {
            throw new IllegalArgumentException("no encoding given");
        }
        this.encoding = encoding;
    }
}
