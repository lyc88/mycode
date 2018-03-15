package com.whyuan.$0BIO;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketDemo {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket();
        socket.connect(new InetSocketAddress("localhost",9999));

        OutputStream outputStream=socket.getOutputStream();
        for(int i=1;i<=Integer.MAX_VALUE;i++){
            outputStream.write("我爱你".getBytes());
            System.out.println("说第"+i+"次我爱你");
        }
    }
}
