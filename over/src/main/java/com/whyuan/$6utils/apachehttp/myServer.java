package com.whyuan.$6utils.apachehttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

//模拟服务端
public class myServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        Socket socket = null;

        System.out.println("等待客户端连接中......");
        socket= ss.accept();
        // 读取客户端数据
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        StringBuffer buffer=new StringBuffer();
        String clientInputStr = null;
        while ((clientInputStr=input.readLine())!=null){
            buffer.append(clientInputStr);
        }
        //input.close();
        // 处理客户端数据
        System.out.println("客户端发过来的内容:" + buffer.toString());



    }
}
