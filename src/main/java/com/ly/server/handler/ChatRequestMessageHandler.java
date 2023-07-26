package com.ly.server.handler;

import com.ly.message.ChatRequestMessage;
import com.ly.message.ChatResponseMessage;
import com.ly.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ChatRequestMessage chatRequestMessage) throws Exception {
        String to = chatRequestMessage.getTo();
        Channel toChannel = SessionFactory.getSession().getChannel(to);
        //在线
        if(toChannel!=null){
            toChannel.writeAndFlush(new ChatResponseMessage(chatRequestMessage.getFrom(),chatRequestMessage.getContent()));
        }else {
            channelHandlerContext.writeAndFlush(new ChatResponseMessage(false,"target user is not online"));
        }

    }
}
