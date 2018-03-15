package com.whyuan.$6utils.io;

import java.io.*;
import java.util.Arrays;

public class IOUtils {
	public static byte[] read(String filename) throws IOException {
		return read(new FileInputStream(filename));
	}

	public static byte[] read(File file) throws IOException {
		return read(new FileInputStream(file));
	}

	public static byte[] read(InputStream in) throws IOException {
		byte[] buf = new byte[in.available()];
		in.read(buf);
		in.close();
		return buf;
	}

	public static void write(byte[] buf, String filename) throws IOException {
		write(buf, filename, false);
	}

	public static void append(byte[] buf, String filename) throws IOException {
		write(buf, filename, true);
	}

	private static void write(byte[] buf, String filename, boolean append) throws IOException {
		OutputStream out = new FileOutputStream(filename, append);
		out.write(buf);
		out.close();
	}

	public static String toHexString(byte[] buf) {
		StringBuilder s = new StringBuilder();
		for (byte b : buf) {
			String hex = Integer.toHexString(b & 0xff);
			hex = leftPad(hex, '0', 2);
			s.append(hex).append(" ");
		}
		return s.toString();
	}

	private static String leftPad(String hex, char c, int size) {
		char[] cs = new char[size];
		Arrays.fill(cs, c);
		System.arraycopy(hex.toCharArray(), 0, cs, cs.length - hex.length(), hex.length());
		return new String(cs);
	}

	public static String toBinString(byte[] buf) {
		StringBuilder s = new StringBuilder();
		for (byte b : buf) {
			String hex = Integer.toBinaryString(b & 0xff);
			hex = leftPad(hex, '0', 8);
			s.append(hex).append(" ");
		}
		return s.toString();
	}

	public static void main(String[] args) {
		byte[] ary = { 1, 4, 0, 78 };
		System.out.println(toBinString(ary));
	}

}
