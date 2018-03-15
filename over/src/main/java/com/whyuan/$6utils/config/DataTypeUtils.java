package com.whyuan.$6utils.config;

/**
 * 判断字符串是否为整型Integer
 */
public class DataTypeUtils {
    public static boolean isInteger(String str) {
	if (str == null) {
            return false;
	}
	int length = str.length();
	if (length == 0) {
            return false;
	}
	int i = 0;
	if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
	}
	for (; i < length; i++) {
            char c = str.charAt(i);
            //字符比较大小：在ASCII码表中，根据码值由小到大的排列顺序是：控制符，数字符，大写英文字母，小写英文字母
            if (c <= '/' || c >= ':') {
                return false;
            }
	}
	return true;
    }


//------------------------------测试类-------------------------------------------
    public static void main(String[] args) {
        String string="12";
        System.out.println(isInteger(string));
    }
}
