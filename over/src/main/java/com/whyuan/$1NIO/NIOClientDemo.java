package com.whyuan.$1NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *一个通道在选择器上同时注册多个事件：连接，读，写
 * @author asus
 *
 */
public class NIOClientDemo {
    public static void main(String[] args) throws Exception {
        //创建一个选择器，通常是全局唯一的
        Selector selc = Selector.open();

        //创建sc对象
        SocketChannel sc = SocketChannel.open();
        //开启非阻塞模式
        sc.configureBlocking(false);
        //将ss注册到selc上关注CONNECT事件 READ事件 WRITE事件
        sc.register(selc, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        //开始连接
        sc.connect(new InetSocketAddress("127.0.0.1",9999));

        //--开始循环进行select操作，处理就绪的sk
        while(true){
            //--执行selc操作
            //--相当于大喊一声，注册在我身上的sk们，哪一个sk对应的通道已经就绪了当初注册的事件
            int selcCount = selc.select();

            if(selcCount > 0){
                //--获取已经就绪的sk们
                Set<SelectionKey> set = selc.selectedKeys();
                //--遍历处理sk
                Iterator<SelectionKey> it = set.iterator();
                while(it.hasNext()){
                    //--获取遍历到的键
                    SelectionKey sk = it.next();
                    //--获取键对应的通道
                    SocketChannel scx = (SocketChannel) sk.channel();

                    if(sk.isConnectable()){//是一个CONNECT操作
                        //--完成连接
                        scx.finishConnect();
                    }else if(sk.isWritable()){//是一个WRITE操作
                        //--待发送的数据
                        String str = "你好 nio~ 今天天气不错，萌萌哒~~~";
                        //--处理协议
                        String sendStr = str.getBytes("utf-8").length +"\r\n" + str;

                        //--发送数据
                        ByteBuffer buf = ByteBuffer.wrap(sendStr.getBytes("utf-8"));
                        while(buf.hasRemaining()){
                            scx.write(buf);
                        }
                        //--取消WRITE注册:当前的键值与write值取反
                        scx.register(selc, sk.interestOps() & ~SelectionKey.OP_WRITE);
                    }else if(sk.isReadable()){//如果是一个Read操作
                        //获取头信息
                        ByteBuffer temp = ByteBuffer.allocate(1);
                        String head = "";
                        while(!head.endsWith("\r\n")){
                            scx.read(temp);
                            head += new String(temp.array());
                            temp.clear();
                        }
                        int len = Integer.parseInt(head.substring(0,head.length()-2));

                        //准备缓冲区接受数据
                        ByteBuffer buf = ByteBuffer.allocate(len);
                        while(buf.hasRemaining()){
                            scx.read(buf);
                        }

                        //打印数据
                        String msg = new String(buf.array(),"utf-8");
                        System.out.println("收到了服务器发回 的消息："+msg);
                    }
                }

                //--从已选择键集中删除已经处理完成的键
                it.remove();
            }
        }
    }
}
