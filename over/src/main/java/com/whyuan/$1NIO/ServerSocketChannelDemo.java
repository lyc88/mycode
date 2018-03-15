package com.whyuan.$1NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo {
    public static void main(String[] args) throws IOException {
        //创建服务端通道
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        //设置服务端为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //绑定9999端口
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        //等待客户端连接
        SocketChannel socketChannel = null;
        System.out.println("------等待客户端连接------");
        while(socketChannel==null){
            socketChannel = serverSocketChannel.accept();
        }
        //设置客户端为非阻塞模式
        socketChannel.configureBlocking(false);

        //--读取头信息，获取体信息的长度
        String head = "";
        ByteBuffer temp = ByteBuffer.allocate(1);
        while(!head.endsWith("\r\n")){
            socketChannel.read(temp);
            head += new String(temp.array());
            temp.clear();
        }
        int len = Integer.parseInt(head.substring(0,head.length()-2));
        //--创建指定长度的缓冲区，读取体信息
        ByteBuffer buf = ByteBuffer.allocate(len);
        while(buf.hasRemaining()){
            socketChannel.read(buf);
        }
        //打印信息
        String str = new String(buf.array(),"UTF-8");
        System.out.println(str);

        while(true){}
    }
}
