package com.github.wenbinai.rpc.test.client;

import com.github.wenbinai.rpc.client.core.RpcClient;

public class RpcClientTest {
    public static void main(String[] args) {
        new RpcClient(9627).run();
    }
}
