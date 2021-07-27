package com.github.wenbinai.rpc.client.core;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.wenbinai.rpc.client.handler.RpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import javax.naming.InitialContext;

public class RpcClient extends Thread {
    private static final Log log = LogFactory.getLog(RpcClient.class);

    private final int port;


    public RpcClient(int port) {
        this.port = port;
    }


    @Override
    public void run() {
        log.info("RPC 服务开启客户端");
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture channelFuture = bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new LoggingHandler(LogLevel.INFO))
                                    .addLast(new RpcClientHandler());
                        }
                    })
                    .connect("localhost", port)
                    .syncUninterruptibly();

            log.info("RPC  服务启动客户端完成,监听端口: " + port);
            channelFuture.channel()
                    .closeFuture()
                    .syncUninterruptibly();
            log.info("RPC 服务开始客户端已关闭");
        } catch (Exception e) {
            log.error("RPC 客户端遇到异常", e);
        } finally {
            workerGroup.shutdownGracefully();
        }

    }
}
