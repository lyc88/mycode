package com.whyuan.$1NIO;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**FileChannel:从FileInputStream里获取文件通道。
 * ：可以从文件制定位置开始读写
 * @author asus
 *
 */
public class FileChannelDemo {
    public static void main(String[] args) throws Exception {
        //从指定位置开始读
		FileInputStream in = new FileInputStream("1.txt");
		FileChannel channel = in.getChannel();
		channel.position(4);

		ByteBuffer temp = ByteBuffer.allocate(3);
		channel.read(temp);

		String str = new String(temp.array());
		System.out.println(str);

		channel.close();
        //从指定位置开始写
		/*FileOutputStream out = new FileOutputStream("1.txt");
		FileChannel channel = out.getChannel();
		channel.position(4);
		channel.write(ByteBuffer.wrap("xyz".getBytes()));
		channel.close();*/

        //从指定位置开始更新，其他位置不变
        /*RandomAccessFile raf = new RandomAccessFile("1.txt", "rw");
        FileChannel channel = raf.getChannel();
        channel.position(4);
        channel.write(ByteBuffer.wrap("xyz".getBytes()));
        channel.close();*/
    }
}
