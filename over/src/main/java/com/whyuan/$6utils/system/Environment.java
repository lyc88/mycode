package com.whyuan.$6utils.system;

import com.sun.tools.javac.comp.Env;
import com.whyuan.$6utils.datetime.DateTimes;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

//系统环境
public class Environment {
    public static void main(String[] args) {
        System.out.println("系统参数：\r\n"+system());
        System.out.println("JVM输入参数：\r\n"+jvmArgs());
        System.out.println("属性参数：\r\n"+properties());
    }

    private static void put(ArrayList<Entry> l, String k, String v) {
        l.add(new Entry(k, v));
    }

    public static ArrayList system(){
        ArrayList l = new ArrayList();
        put(l, "app.name", System.getProperty("appName", "<NA>"));
        put(l, "app.describe", System.getProperty("appDesc", "<NA>"));
        put(l, "location", System.getProperty("run_dir", "<NA>"));
        put(l, "shell.name", System.getProperty("shellName", "<NA>"));
        put(l, "time", DateTimes.formatNOW());

        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String hostname = InetAddress.getLocalHost().getCanonicalHostName();
            put(l, "host.name",String.format("%s(%s)",hostname,ip));
        } catch (UnknownHostException var2) {
            put(l, "host.name", "<NA>");
        }

        put(l, "java.version", System.getProperty("java.version", "<NA>"));
        put(l, "java.vendor", System.getProperty("java.vendor", "<NA>"));
        put(l, "java.home", System.getProperty("java.home", "<NA>"));
        put(l, "java.io.tmpdir", System.getProperty("java.io.tmpdir", "<NA>"));
        put(l, "java.compiler", System.getProperty("java.compiler", "<NA>"));
        put(l, "os.name", System.getProperty("os.name", "<NA>"));
        put(l, "os.arch", System.getProperty("os.arch", "<NA>"));
        put(l, "os.version", System.getProperty("os.version", "<NA>"));
        put(l, "user.name", System.getProperty("user.name", "<NA>"));
        put(l, "user.home", System.getProperty("user.home", "<NA>"));
        put(l, "user.dir", System.getProperty("user.dir", "<NA>"));
        return l;
    }

    public static ArrayList jvmArgs(){
        ArrayList l = new ArrayList();
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        List<String> jvmArgs = runtimeMXBean.getInputArguments();
        for (String arg : jvmArgs) {
            String[] _arr = arg.split("=");
            put(l,_arr[0],_arr.length>=2?_arr[1]:"");
        }
        return l;
    }

    public static ArrayList properties(){
        ArrayList l = new ArrayList();
        Properties ps = System.getProperties();
        Enumeration e = ps.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String val = ps.getProperty(key);
            put(l, key, val.length() < 256 ? val : val.substring(0, 255));
        }
        return l;
    }

    public static void logEnv(ArrayList<Env> env) {
        Iterator i$ = env.iterator();
        while(i$.hasNext()) {
            Entry e = (Entry)i$.next();
            System.out.println(e.toString());
        }
    }

    public static String envToString(ArrayList<Env> env) {
        final  String ls = System.lineSeparator();
        StringBuffer sb = new StringBuffer();
        Iterator i$ = env.iterator();
        while (i$.hasNext()) {
            Entry e = (Entry) i$.next();
            sb.append(e.toString() +ls);
        }
        return sb.toString();
    }

    public static class Entry {
        private String k;
        private String v;

        public Entry(String k, String v) {
            this.k = k;
            this.v = v;
        }

        public String getKey() {
            return this.k;
        }

        public String getValue() {
            return this.v;
        }

        @Override
        public String toString() {
            return new StringBuffer().append(this.k + "  :  ").append(this.v.length()<256?v:this.v.substring(0,256)).toString();
        }
    }
}
