package com.bwin.ignite.test;

import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by dreddy on 12/13/2016.
 */

public class ServerTest {

    @Test
    public void serverStartUpTest() throws InterruptedException {
            new ServerHelper().ensureClusterSize(1);
    }

}
