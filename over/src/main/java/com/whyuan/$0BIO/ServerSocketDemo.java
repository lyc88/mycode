package com.whyuan.$0BIO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketDemo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(9999);
        System.out.println("------等待客户端连接------");
        Socket socket=serverSocket.accept();//该方法阻塞
        System.out.println("客户端连接进来了："+socket);

        InputStream inputStream=socket.getInputStream();
        System.out.println("客户端说："+inputStream.read());
    }
}
