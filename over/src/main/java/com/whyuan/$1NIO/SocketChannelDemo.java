package com.whyuan.$1NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        //创建客户端对象
        SocketChannel socketChannel=SocketChannel.open();
        //开启非阻塞模式
        socketChannel.configureBlocking(false);
        //连接服务端
        socketChannel.connect(new InetSocketAddress("localhost",9999));
        if(!socketChannel.finishConnect()){
            Thread.sleep(10);
        }
        String string="hello nio~ i am whyuan";

        //准备数据，格式为 数据长度+\r\n+数据内容
        StringBuffer sb = new StringBuffer();
        sb.append(string.length()+"\r\n");
        sb.append(string);
        String sendStr = sb.toString();

        //发送数据
        ByteBuffer buf = ByteBuffer.wrap(sendStr.getBytes());
        while(buf.hasRemaining()){
            socketChannel.write(buf);
        }

        while(true){}

    }
}
