package io.failify.examples.cassandra;


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import io.failify.FailifyRunner;
import io.failify.exceptions.RuntimeEngineException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

import java.sql.*;
import java.util.concurrent.TimeoutException;

public class SampleTest {
    private static final Logger logger = LoggerFactory.getLogger(SampleTest.class);

    protected static FailifyRunner runner;
    protected static final int NUM_OF_NODES = 3;

    @BeforeClass
    public static void before() throws RuntimeEngineException, TimeoutException {
        runner = FailifyHelper.getDeployment(NUM_OF_NODES).start();
        FailifyHelper.waitForCluster(runner, NUM_OF_NODES);
        logger.info("The cluster is UP!");
    }

    @AfterClass
    public static void after() {
        if (runner != null) {
            runner.stop();
        }
    }


    @Test
    public void sampleTest() throws RuntimeEngineException, SQLException, ClassNotFoundException, TimeoutException {
        try (CqlSession session = FailifyHelper.getCQLSession(runner, NUM_OF_NODES)) {
            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();
            System.out.println(row.getString("release_version"));
        }
    }
}
