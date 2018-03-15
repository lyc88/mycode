package com.whyuan.$6utils.javahttp;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 *Https请求
 */
public class HTTPS {
    static final Logger logger = LoggerFactory.getLogger(HTTPS.class);

    public static final String get(String url) throws IOException {
        return get(url, "UTF-8");
    }

    public static final String get(String url, String charset) throws IOException {
        URL oracle = new URL(url);
        URLConnection connection = oracle.openConnection();
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);

        logger.debug("Http request Url={}, Charset={}", url, charset);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset != null ? Charset.forName(charset) : Charset.forName("UTF-8")));
        StringBuffer result = new StringBuffer();
        String inputLine = null;
        while ((inputLine = in.readLine()) != null) {
            result.append(inputLine);
        }
        in.close();
        return result.toString();
    }

    public static final String post(String url, String body) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        return post(url, body, "UTF-8");
    }

    public static final String post(String url, String body, String charset) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        if (url.startsWith("https://")) {
            return httpsPost(url, body, charset);
        }

        URL oracle = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) oracle.openConnection();
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Charset", charset);
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);
        connection.connect();

        BufferedReader ready = null;
        DataOutputStream dop = null;
        StringBuffer result = new StringBuffer();
        try {
            dop = new DataOutputStream(connection.getOutputStream());
            dop.writeBytes(body);
            dop.flush();

            logger.debug("Http request Url={}, Charset={}", url, charset);

            ready = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset != null ? Charset.forName(charset) : Charset.forName("UTF-8")));
            String inputLine = null;
            while ((inputLine = ready.readLine()) != null) {
                result.append(inputLine);
            }
        } finally {
            if (dop != null) {
                dop.close();
            }

            if (ready != null) {
                ready.close();
            }

        }
        return result.toString();
    }

    public static final String post(String url, String body, int retry) throws NoSuchAlgorithmException, KeyManagementException {
        try {
            return post(url, body, "UTF-8");
        } catch (IOException e) {
            //三次重试
            if (retry >= 3) {
                logger.error("requst retry= " + retry + " , body=" + body, e);
                return null;
            }
            logger.warn("requst retry=" + retry);
            return post(url, body, ++retry);
        }
    }

    //https
    public static final String httpsPost(String url, String body, String charset) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        URL oracle = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) oracle.openConnection();
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Charset", charset);
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);

        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[] {new X509TrustManager(){
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }}, new SecureRandom());
        SSLContext.setDefault(ctx);
        connection.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        connection.setSSLSocketFactory(ctx.getSocketFactory());

        connection.connect();

        BufferedReader ready = null;
        DataOutputStream dop = null;
        StringBuffer result = new StringBuffer();
        try {
            dop = new DataOutputStream(connection.getOutputStream());
            dop.writeBytes(body);
            dop.flush();

            logger.debug("Http request Url={}, Charset={}", url, charset);

            ready = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset != null ? Charset.forName(charset) : Charset.forName("UTF-8")));
            String inputLine = null;
            while ((inputLine = ready.readLine()) != null) {
                result.append(inputLine);
            }
        } finally {
            if (dop != null) {
                dop.close();
            }

            if (ready != null) {
                ready.close();
            }

        }
        return result.toString();
    }


    private static final Map<String, String> paramMap(String url, String charset) {
        HashMap<String, String> params = new HashMap<>();

        int flag = url.indexOf("?");
        if (flag != -1) {
            String $ = url.substring(flag + 1);
            String[] ps = $.split("&");
            for (String p : ps) {
                String[] keyval = p.split("=");
                if (keyval.length == 2 && !Strings.isNullOrEmpty(keyval[1])) {
                    try {
                        params.put(keyval[0], URLDecoder.decode(keyval[1], charset));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return params;
    }

    private static final String paramUrl(Map<String, String> params, String charset) {
        StringBuffer url = new StringBuffer();
        for (String key : params.keySet()) {
            try {
                url.append(key).append("=").append(URLEncoder.encode(params.get(key), charset)).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return url.toString();
    }


    public static final String post(String url) throws NoSuchAlgorithmException, KeyManagementException {
        return post(url, false);
    }

    public static final String post(String url, boolean print) throws KeyManagementException, NoSuchAlgorithmException {
        if (Strings.isNullOrEmpty(url)) {
            return null;
        }

        Map<String, String> params = paramMap(url, "UTF-8");
        if (print) {
            for (String key : params.keySet()) {
                logger.info(key + "=" + params.get(key));
            }
        }

        return post(host(url), paramUrl(params, "UTF-8"), 0);
    }

    public static final String host(String url) {
        int s = url.indexOf("?");
        if (s == -1) {
            return url;
        }
        return url.substring(0, s);
    }
}
