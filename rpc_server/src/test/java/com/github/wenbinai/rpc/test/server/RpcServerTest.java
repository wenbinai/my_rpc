package com.github.wenbinai.rpc.test.server;

import com.github.wenbinai.rpc.server.core.RpcServer;
import org.junit.Test;

public class RpcServerTest {
    @Test
    public void test_run() {
        new RpcServer().start();
    }
}
