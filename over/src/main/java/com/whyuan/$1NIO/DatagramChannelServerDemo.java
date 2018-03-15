package com.whyuan.$1NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelServerDemo {
	public static void main(String[] args) throws Exception {
		DatagramChannel dc = DatagramChannel.open();
		dc.socket().bind(new InetSocketAddress(8899));
		ByteBuffer buf = ByteBuffer.allocate(1024);
		dc.receive(buf);
		
		String str = new String(buf.array(),0,buf.position());
		System.out.println(str);
	}
}
