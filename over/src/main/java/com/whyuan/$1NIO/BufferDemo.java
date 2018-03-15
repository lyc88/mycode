package com.whyuan.$1NIO;

import java.nio.ByteBuffer;

public class BufferDemo {
    public static void main(String[] args) {
        //声明一个1K的缓冲区
        ByteBuffer byteBuffer1=ByteBuffer.allocate(1024);
        //声明一个有数据的缓冲区
        ByteBuffer byteBuffer2=ByteBuffer.wrap("abcdefg".getBytes());

        //写
        byteBuffer1.put("abc".getBytes());

        //读
        while (byteBuffer1.hasRemaining()){
            byte b1= byteBuffer1.get();
            System.out.println(b1);
        }

        //反转缓冲区：相当于将limit=position，然后将position=0 mark=-1
       /* byteBuffer1.limit(byteBuffer1.position());
        byteBuffer1.position(0);*/
        byteBuffer1.flip();

        //重绕缓冲区：position=0  mark=-1
        byteBuffer1.rewind();

        //清空缓冲区limir=capacity,position=0 mark=-1
        byteBuffer1.clear();

    }
}
