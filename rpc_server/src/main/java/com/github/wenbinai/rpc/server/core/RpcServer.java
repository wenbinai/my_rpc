package com.github.wenbinai.rpc.server.core;

import com.github.wenbinai.rpc.server.constant.RpcServerConst;
import com.github.wenbinai.rpc.server.handler.RpcServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcServer extends Thread {
    private final int port;

    public RpcServer() {
        this.port = RpcServerConst.DEFAULT_PORT;
    }

    public RpcServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        // 启动服务端
        log.info("RPC 服务开始启动服务端");

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(workerGroup, bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            channel.pipeline().addLast(new RpcServerHandler());
                        }
                    })
                    // 影响的是还没有被accept 取出的连接
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 一段时间内客户端没有响应, 服务端发送一个 ack包, 判断客户端是否还活着
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口, 开始接收进来的连接
            ChannelFuture channelFuture = serverBootstrap.bind(port).syncUninterruptibly();
            log.info("RPC 服务端启动完成，监听【" + port + "】端口");

            channelFuture.channel().closeFuture().syncUninterruptibly();
            log.info("RPC 服务端关闭完成");
        } catch (Exception e) {
            log.error("RPC 服务异常", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
