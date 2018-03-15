package com.whyuan.$6utils.string;
import java.util.Arrays;

public class StringUtils {
	public static void main(String[] args) {
		System.out.println(leftPad("10",3,'#'));
	}

	/**
	 * 数组的左填充
	 * @param str源数组
	 * @param length填充个数
	 * @param ch填充的字符
	 * @return
	 */
	public static String leftPad(String str,int length,char ch){
		char[] chs=new char[length];
		Arrays.fill(chs,ch);
		char[] src=str.toCharArray();
		System.arraycopy(src,0,chs,length-src.length,src.length);
		return new String(chs);
	}
}
