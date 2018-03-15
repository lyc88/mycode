package com.whyuan.$6utils.string;
import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//删除字符串中的<!-- -->
//方法：空格替换
public class RegexUtil {

    /**
     * 匹配注释标签<!-- -->
     */
    private static Pattern pt_annotate = null;
    /**
     * 匹配标签<!-- -->
     */
    private static Pattern pt_htmltag = null;

    private static Pattern getAnnotatePattern() {
        if (pt_annotate == null) {
            pt_annotate = Pattern.compile("(?s)<!\\-\\-.*?\\-\\->");
        }
        return pt_annotate;
    }

    private static Pattern getHtmlTagPattern() {
        if (pt_htmltag == null) {
            pt_htmltag = Pattern.compile("(?i)<(?=/|[a-z]|!\\-\\-)[^>]*>");
        }
        return pt_htmltag;
    }

    /**
     * 删除html注释标签<!-- -->
     *
     * @param content
     * @return
     */
    public static String removeAnnotation(String content) {
        return remove(content, getAnnotatePattern());
    }

    /**
     * 删除html标签
     *
     * @param content
     * @return 删除html标签后的文本
     */
    public static String removeHtmlTag(String content) {
        return remove(content, getHtmlTagPattern());
    }

    /**
     * @param content
     * @param p       Pattern 匹配待删除的文本
     * @return 删除p匹配的文本
     */
    public static String remove(String content, Pattern p) {
        if (Strings.isNullOrEmpty(content)) {
            return content;
        }
        Matcher m = p.matcher(content);
        return m.replaceAll("");
    }

    public static void main(String[] args) {
        String content="<!-- 标签内容-->正文";
        System.out.println(RegexUtil.remove(content,getHtmlTagPattern()));
    }
}
