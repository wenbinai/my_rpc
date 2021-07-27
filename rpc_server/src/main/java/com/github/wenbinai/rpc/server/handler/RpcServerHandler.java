package com.github.wenbinai.rpc.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcServerHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//        final String id = ctx.channel().id().asLongText();
//        log.info("[Server] channel read start: {}", id);
//
//        // 1. 接受客户端请求
//        RpcRequest rpcRequest = (RpcRequest) msg;
//        final String seqId = rpcRequest.seqId();
//        log.info("[Server] receive seqId: {} request: {}", seqId, rpcRequest);
//        // 2. 设置请求信息和超时时间
//        invokeManager.addRequest(rpcRequest.seqId(), rpcRequest.timeout());
//
//        // 3. 回写到 client 端
//        //3.1 获取结果
//        RpcResponse rpcResponse = this.handleRpcRequest(rpcRequest);
//        //3.2 回写结果
//        final CallTypeEnum callType = rpcRequest.callType();
//        if (CallTypeEnum.SYNC.equals(callType)) {
//            ctx.writeAndFlush(rpcResponse);
//        } else {
//            log.info("[Server] seqId: {} callType: {} ignore write back.", seqId, callType);
//        }
//        log.info("[Server] seqId: {} response {}", seqId, rpcResponse);
//        // 3.3 移除对应的信息，便于优雅关闭
//        invokeManager.removeReqAndResp(seqId);
    }
}
