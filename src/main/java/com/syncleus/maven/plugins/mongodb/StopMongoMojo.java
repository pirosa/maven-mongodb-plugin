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
package com.syncleus.maven.plugins.mongodb;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import de.flapdoodle.embed.mongo.MongodProcess;

/**
 * When invoked, this goal stops an instance of mojo that was started by this
 * plugin.
 * 
 * @goal stop
 * @phase post-integration-test
 */
public class StopMongoMojo extends AbstractMojo {

    /**
     * @parameter expression="${mongodb.skip}" default-value="false"
     */
    private boolean skip;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            return;
        }
        
        MongodProcess mongod = (MongodProcess)getPluginContext().get(StartMongoMojo
            .MONGOD_CONTEXT_PROPERTY_NAME);

        if (mongod != null) {
            mongod.stop();
        } else {
            throw new MojoFailureException("No mongod process found, it appears embedmongo:start was not called");
        }
    }

}