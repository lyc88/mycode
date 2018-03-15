package com.whyuan.$1NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**DatagramChannel
 * 用来实现UDP通信:无连接，不可靠，速度快
 */
public class DatagramChannelClientDemo {
	public static void main(String[] args) throws Exception {
		DatagramChannel dc = DatagramChannel.open();
		dc.send(ByteBuffer.wrap("hello udp~".getBytes()), new InetSocketAddress("127.0.0.1",8899));
	}
}
