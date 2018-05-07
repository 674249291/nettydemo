package com.xj.learning.netty.helloDemo.client;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: XIONGJUN
 * @Date: 2018/5/5 16:39
 * @Description: 客户端
 */
public class Client {

    public static void main(String[] args) {
        //客户端
        ClientBootstrap clientBootstrap = new ClientBootstrap();

        ExecutorService boos = Executors.newCachedThreadPool();
        ExecutorService work = Executors.newCachedThreadPool();

        //设置客户端工厂类
        clientBootstrap.setFactory(new NioClientSocketChannelFactory(boos, work));

        clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline channelPipeline = Channels.pipeline();
                channelPipeline.addLast("decoder", new StringDecoder());
                channelPipeline.addLast("encoder", new StringEncoder());
                channelPipeline.addLast("hiHandler", new HiHandler());
                return channelPipeline;
            }
        });

        ChannelFuture channelFuture = clientBootstrap.connect(new InetSocketAddress("127.0.0.1", 9999));
        Channel channel = channelFuture.getChannel();

        System.out.println("client start !");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("请输入：");
            channel.write(scanner.next());
        }
    }
}
