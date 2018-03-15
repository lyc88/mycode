package com.whyuan.$1NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * $1NIO：NEW IO或者非阻塞式IO：面向通道Channel,操作缓冲区Buffer
 * 选择器 selector在服务器上的运用：实现一个线程可处理多个通道。
 * @author asus
 *
 */
public class SelectorServerDemo {
    public static void main(String[] args) throws Exception {
        //--定义一个选择器，一般来说全局只有一个
        Selector selc = Selector.open();

        //@创建非阻塞模式通道，相当于多个客户端中的一个
        //创建ssc对象，开启非阻塞模式
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        //指定监听的端口
        ssc.socket().bind(new InetSocketAddress(9999));

        //@将该通道注册在选择器上，并指定操作
        //--为ssc在selc上注册ACCEPT操作
        ssc.register(selc, SelectionKey.OP_ACCEPT);

        //--开始循环进行select操作，处理就绪的sk
        while(true){
            //--@执行selc操作，给每个通道返回一个sk作为其唯一标识。
            //--相当于大喊一声，注册在我身上的sk们，哪一个sk对应的通道已经就绪了当初注册的事件
            int selcCount = selc.select();

            //--如果选择出的sk多余0个，表明有需要被处理的通道
            if(selcCount > 0){
                //--选择出已经就绪的sk们
                Set<SelectionKey> set = selc.selectedKeys();
                //--遍历处理sk对应的通道的事件
                Iterator<SelectionKey> it = set.iterator();
                while(it.hasNext()){
                    //--遍历出每一个就绪的sk
                    SelectionKey sk = it.next();
                    //--根据sk注册的不同，分别处理
                    if(sk.isAcceptable()){//如果是一个ACCEPT操作
                        //--获取sk对应的channel
                        ServerSocketChannel sscx = (ServerSocketChannel) sk.channel();
                        //--接受连接，得到sc
                        SocketChannel sc = sscx.accept();
                        //--开启sc的非阻塞模式
                        sc.configureBlocking(false);
                        //--将sc注册到selc上，关注READ方法
                        sc.register(selc, SelectionKey.OP_READ);
                    }else if(sk.isConnectable()){//如果是一个Connect操作

                    }else if(sk.isReadable()){//如果是一个Read操作
                        //--获取sk对应的通道
                        SocketChannel sc = (SocketChannel) sk.channel();

                        //--获取头信息，获知体的长度
                        ByteBuffer temp = ByteBuffer.allocate(1);
                        String head = "";
                        while(!head.endsWith("\r\n")){
                            sc.read(temp);
                            head += new String(temp.array());
                            temp.clear();
                        }
                        int len = Integer.parseInt(head.substring(0,head.length()-2));

                        //准备缓冲区接受数据
                        ByteBuffer buf = ByteBuffer.allocate(len);
                        while(buf.hasRemaining()){
                            sc.read(buf);
                        }

                        //打印数据
                        String msg = new String(buf.array(),"utf-8");
                        System.out.println("服务器收到了客户端["+sc.socket().getInetAddress().getHostAddress()+"]发来的数据："+msg);

                    }else if(sk.isWritable()){//如果是一个Write操作

                    }else{//其他就报错
                        throw new RuntimeException("NIO操作方式出错！");
                    }
                    //@选择器底层存在三种键集：1已注册的通道键集，2已选择处理的键集，3待删除的键集
                    //--从已选择键集中删除已处理过后的键（若未执该行代码，该通道事件在下一次循环将重复执行）
                    it.remove();
                }

            }
        }
    }
}
