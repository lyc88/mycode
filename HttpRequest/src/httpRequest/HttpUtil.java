package httpRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpUtil {

    public static String sendPost(String url, String Params) throws IOException {
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        String response = "";
        try {
            URL httpUrl = null; //HTTP URL类 用这个类来创建连接
            //创建URL
            httpUrl = new URL(url);
            //建立连接
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setUseCaches(false);//设置不要缓存
            conn.setInstanceFollowRedirects(true);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //POST请求向服务器写入数据
            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(Params);
            out.flush();
            //读取响应
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                response += lines;
            }
            reader.close();
            // 断开连接
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return response;
    }
    //===============================post测试：http://localhost:8888/index  requestBody:str===================================
    public static void main(String[] args) throws Exception {
        Scanner console=new Scanner(System.in);
        while (true){
            System.out.println("Enter your param：");
            String str=console.next();
            System.out.println(sendPost("http://localhost:8888/index",str));
        }
    }
}
