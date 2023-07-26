package com.ly.server.handler;

import com.ly.message.LoginRequestMessage;
import com.ly.message.LoginResponseMessage;
import com.ly.server.service.UserServiceFactory;
import com.ly.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestMessage loginRequestMessage) throws Exception {
        String username = loginRequestMessage.getUsername();
        String password = loginRequestMessage.getPassword();
        boolean login = UserServiceFactory.getUserService().login(username, password);
        LoginResponseMessage loginResponseMessage;
        if (login) {
            SessionFactory.getSession().bind(channelHandlerContext.channel(), username);
            loginResponseMessage = new LoginResponseMessage(true, "login success");
        } else {
            loginResponseMessage = new LoginResponseMessage(false, "login failed");
        }
        channelHandlerContext.writeAndFlush(loginResponseMessage);
    }
}
