package com.whyuan.$6utils.apachehttp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.pool.PoolStats;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Http协议通信实现类
 * @author gaop
 *
 */

public class HttpCommunication implements ICommunication {
	
	static Log log=LogFactory.getLog(HttpCommunication.class);
	
	private  CloseableHttpClient httpclient;
	
	private  HttpClientBuilder httpBuilder;
	
	private  PoolingHttpClientConnectionManager connManager;
	
	private  RequestConfig requestCfg;
	
	private  HttpRoute tagRoute;

	private  String tagUrl;
	
	private  String requestMethod;
	
	private  String keepAlive="close";
	
	private  int requestTimeOut;
	
	private  int connectTimeOut;
	
	private  int socketTimeOut;
	
	private  int maxConn;
	
	private  String charset;
	
	private  String paramName="msg";
	
	private  Map<String,String> staticHeader;
	
	private  String proxyIp;
	
	private  int proxyPort;
	
	/**
	 * HTTP链接管理类构造函数，每次构造一个通道的HTTPCLIENT对象及管理器
	 * @param RequestTimeOut
	 * @param ConnectTimeOut
	 * @param SocketTimeOut
	 * @param url
	 * @param RequestMethod
	 * @param MaxConn
	 */
	public HttpCommunication(int requestTimeOut,int connectTimeOut,int socketTimeOut,String url,String requestMethod,int maxConn,String keepAlive,String charset,String paramName,Map<String,String> staticHeader){
		this.tagUrl=url;
		
		this.requestMethod=requestMethod;
		
		this.keepAlive=keepAlive;
		
		this.requestTimeOut=requestTimeOut;
		
		this.connectTimeOut=connectTimeOut;
		
		this.socketTimeOut=socketTimeOut;
		
		this.maxConn=maxConn;
		
		this.charset=charset;
		
		this.paramName=paramName;
		
		this.staticHeader=staticHeader;
		
		init();
	}



	/**
	 * HTTP链接管理类构造函数，每次构造一个通道的HTTPCLIENT对象及管理器
	 * @param RequestTimeOut
	 * @param ConnectTimeOut
	 * @param SocketTimeOut
	 * @param url
	 * @param RequestMethod
	 * @param MaxConn
	 */
	public HttpCommunication(int requestTimeOut,int connectTimeOut,int socketTimeOut,String url,String requestMethod,int maxConn,String keepAlive,String charset,String paramName,Map<String,String> staticHeader,String proxyIp,int proxyPort){
		this.tagUrl=url;
		
		this.requestMethod=requestMethod;
		
		this.keepAlive=keepAlive;
		
		this.requestTimeOut=requestTimeOut;
		
		this.connectTimeOut=connectTimeOut;
		
		this.socketTimeOut=socketTimeOut;
		
		this.maxConn=maxConn;
		
		this.charset=charset;
		
		this.paramName=paramName;
		
		this.staticHeader=staticHeader;
		
		this.proxyIp=proxyIp;
		
		this.proxyPort=proxyPort;
		
		init();
	}

	public boolean init() {
		try{
			RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
			ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
			registryBuilder.register("http", plainSF);
			//指定信任密钥存储对象和连接套接字工厂
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, new TrustStrategy(){
					 public boolean isTrusted(X509Certificate[] chain,String authType) throws CertificateException {
				     return true;}
					 }).build();
			LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			registryBuilder.register("https", sslSF);
			
			Registry<ConnectionSocketFactory> registry = registryBuilder.build();
			
			this.connManager=new PoolingHttpClientConnectionManager(registry);
			
			this.connManager.setMaxTotal(this.maxConn);
			this.tagRoute=new HttpRoute(new HttpHost(tagUrl));
			this.connManager.setMaxPerRoute(tagRoute, this.maxConn);
			
			if(this.proxyIp!=null&&this.proxyIp!=""){
				HttpHost proxy=new HttpHost(this.proxyIp,this.proxyPort,"http");
				this.requestCfg=RequestConfig.custom().setProxy(proxy).setConnectionRequestTimeout(this.requestTimeOut).setConnectTimeout(this.connectTimeOut).setSocketTimeout(this.socketTimeOut).build();
			}else{
				this.requestCfg=RequestConfig.custom().setConnectionRequestTimeout(this.requestTimeOut).setConnectTimeout(this.connectTimeOut).setSocketTimeout(this.socketTimeOut).build();
			}
			this.httpBuilder= HttpClientBuilder.create().setConnectionManager(this.connManager);
			this.httpclient=httpBuilder.build();
			
			return true;
		} catch (KeyStoreException e) {
			log.error("httpclient 创建异常");
			return false;
		} catch (KeyManagementException e) {
			log.error("httpclient 创建异常");
			return false;
		} catch (NoSuchAlgorithmException e) {
			log.error("httpclient 创建异常");
			return false;
		}catch(Exception e){
			log.error("httpclient 创建异常");
			return false;
		}
	}

	public String communication(Object sendMsg) throws Exception{
		CloseableHttpResponse response=null;
		String returnMsg=null;
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		
		//可能需要对sendMsg执行encoding操作
		//将发生信息放入POST表单
		try{
			if(this.requestMethod.equals("post")){ 
				HttpPost post=this.getHttpPost();
				//消息头设置
				post.setHeader("Connection", this.keepAlive);
				for(String key:this.staticHeader.keySet()){
					post.setHeader(key,this.staticHeader.get(key));
				}
				//消息体发送的信息支持String,Map
				if(sendMsg instanceof String){
					String sendmsg=sendMsg.toString();
					//判断消息是否有名字(标记)
					if("none".equals(this.paramName)){
						StringEntity myEntity = new StringEntity(sendmsg, ContentType.APPLICATION_XML.withCharset(this.charset));
						post.setEntity(myEntity);
					}else{
						formparams.add(new BasicNameValuePair(this.paramName, sendmsg));
						UrlEncodedFormEntity uefEntity;
						uefEntity = new UrlEncodedFormEntity(formparams, charset);
						post.setEntity(uefEntity);
					}
				}else if(sendMsg instanceof Map){
					Map<String,Object> sendmsg=(Map<String,Object>)sendMsg;
					for(String key:sendmsg.keySet()){
						formparams.add(new BasicNameValuePair(key,sendmsg.get(key).toString()));
					}
					UrlEncodedFormEntity uefEntity;
					uefEntity = new UrlEncodedFormEntity(formparams, charset);
					post.setEntity(uefEntity);
				}
				//需对应写入相关HTTP头信息
				//httppost.addHeader("", "");
					
				log.debug("http comm " + post.getURI());
	            response = this.httpclient.execute(post);
		      }else if(this.requestMethod.equals("get")){
		    	  StringBuffer getmsg=new StringBuffer("?");
		    	  if(sendMsg instanceof String){
		    		String sendmsg=sendMsg.toString();
		    		formparams.add(new BasicNameValuePair(this.paramName, sendmsg));
		   			getmsg.append(URLEncodedUtils.format(formparams,charset));
		    	  }else if(sendMsg instanceof Map){
		    		Map<String,Object> sendmsg=(Map<String,Object>)sendMsg;
					for(String key:sendmsg.keySet()){
						formparams.add(new BasicNameValuePair(key,sendmsg.get(key).toString()));
					}
					getmsg.append(URLEncodedUtils.format(formparams,charset));	
		    	}
				HttpGet get=this.getHttpGet(getmsg.toString());
					
				get.setHeader("Connection", this.keepAlive);
				for(String key:this.staticHeader.keySet()){
					get.setHeader(key,this.staticHeader.get(key));
				}
				log.debug("http comm " + get.getURI());
				response = httpclient.execute(get);
		      }	else{
		    	  throw new BusinessException("HttpCommunication234", "请求类型未设置");
		      }

		      //response处理
			  int resStatus=response.getStatusLine().getStatusCode();
		            
		      if (resStatus == HttpStatus.SC_OK) {
		              // get result data
		         	HttpEntity  entity = response.getEntity();  
			        if (entity != null) {  
			        returnMsg=EntityUtils.toString(entity, charset);
			        log.warn("Response content: " + returnMsg);
			        }  
		      } else {
		              log.error(this.tagUrl + ": resStatus is " + resStatus);
		              throw new BusinessException("HttpCommunication160",this.tagUrl+":http请求状态码"+resStatus);
		        }
	              
			}catch (ClientProtocolException e) {  
				log.error(e.getMessage(), e);
				throw new BusinessException("HttpCommunication166", "通信协议异常:"+e.getMessage());
	        } catch (UnsupportedEncodingException e) {  
	        	log.error(e.getMessage(), e);
				throw new BusinessException("HttpCommunication169", "不可支持的字符集:"+e.getMessage());
	        } catch (ConnectionPoolTimeoutException e) {
	            log.error(e.getMessage(), e);
	            throw new BusinessException("HttpCommunication173", "链接等待超时:"+e.getMessage());
	         }catch (ConnectTimeoutException e){
	        	log.error(e.getMessage(), e);
				throw new BusinessException("HttpCommunication175", "请求超时:"+e.getMessage());
	        } catch (SocketTimeoutException e){
	        	log.error(e.getMessage(), e);
				throw new BusinessException("HttpCommunication178", "响应超时:"+e.getMessage());
	        } catch (IOException e) {
	        	log.error(e.getMessage(), e);
				throw new BusinessException("HttpCommunication181", "IO异常:"+e.getMessage());
	        } catch (Exception e){
	        	log.error(e.getMessage(), e);
				throw new BusinessException("HttpCommunication184", "其他异常:"+e.getMessage());
	        }

		log.debug(formatStats(this.tagRoute));
		return returnMsg;
	}
	
	
//	private void doComm(List<NameValuePair> formparams){
//		CloseableHttpResponse response=null;
//		String returnMsg=null;
//		try{
//			if(this.requestMethod.equals("post")){
//				HttpPost post=this.getHttpPost();
//				post.setHeader("Connection", this.keepAlive);
//				for(String key:this.staticHeader.keySet()){
//					post.setHeader(key,this.staticHeader.get(key));
//				}
//				log.debug("http comm " + post.getURI());
//				response = this.httpclient.execute(post);
//			}else if(this.requestMethod.equals("get")){
//				StringBuffer getmsg=new StringBuffer("?");
//				getmsg.append(URLEncodedUtils.format(formparams,charset));
//				HttpGet get=this.getHttpGet(getmsg.toString());
//				get.setHeader("Connection", this.keepAlive);
//				for(String key:this.staticHeader.keySet()){
//					get.setHeader(key,this.staticHeader.get(key));
//				}
//				log.debug("http comm " + get.getURI()); 
//				response = httpclient.execute(get);
//
//			}
//			int resStatus=response.getStatusLine().getStatusCode();
//	            
//	        if (resStatus == HttpStatus.SC_OK) {
//	              // get result data
//	           HttpEntity  entity = response.getEntity();  
//		       if (entity != null) {  
//		           returnMsg=EntityUtils.toString(entity, charset);
//		           log.warn("Response content: " + returnMsg);
//		       }  
//	        } else {
//	           log.error(this.tagUrl + ": resStatus is " + resStatus);
//	           throw new BusinessException("HttpCommunication160",this.tagUrl+":http请求状态码"+resStatus);
//	        }
//		  
//		}catch (ClientProtocolException e) {  
//			log.error(e.getMessage(), e);
//			throw new  BusinessException("HttpCommunication213", "通信协议异常:"+e.getMessage());  
//        } catch (UnsupportedEncodingException e) {  
//        	log.error(e.getMessage(), e);
//			throw new  BusinessException("HttpCommunication216", "不可支持的字符集:"+e.getMessage());
//        } catch (ConnectionPoolTimeoutException e) {
//            log.error(e.getMessage(), e);
//            throw new BusinessException("HttpCommunication219", "链接等待超时:"+e.getMessage());
//         }catch (ConnectTimeoutException e){
//        	log.error(e.getMessage(), e);
//			throw new  BusinessException("HttpCommunication222", "请求超时:"+e.getMessage()); 
//        } catch (SocketTimeoutException e){
//        	log.error(e.getMessage(), e);
//			throw new  BusinessException("HttpCommunication225", "响应超时:"+e.getMessage());
//        } catch (IOException e) {
//        	log.error(e.getMessage(), e);
//			throw new  BusinessException("HttpCommunication228", "IO异常:"+e.getMessage()); 
//        } catch (Exception e){
//        	log.error(e.getMessage(), e);
//			throw new  BusinessException("HttpCommunication231", "其他异常:"+e.getMessage());
//        }
//		log.debug(formatStats(this.tagRoute));
//		return returnMsg;
//	}
	
	/**
	 * 根据报文及通道，获取对应通道httpclinetManager，执行请求
	 */
	
	private HttpGet getHttpGet(String msg){
		HttpGet httpGet=new HttpGet(this.tagUrl+msg);
		httpGet.setConfig(this.requestCfg);
		return httpGet;
	}
	
	private HttpPost getHttpPost(){
		HttpPost httpPost=new HttpPost(this.tagUrl);
		httpPost.setConfig(this.requestCfg);
		return httpPost;
	}
	

	
	public CloseableHttpClient getHttpClient()
	{
		return this.httpclient;
	}
	
	private String formatStats(final HttpRoute route) {
	        StringBuilder buf = new StringBuilder();
	        PoolStats totals = connManager.getTotalStats();
	        PoolStats stats = connManager.getStats(route);
	        buf.append("[total kept alive: ").append(totals.getAvailable()).append("; ");
	        buf.append("route allocated: ").append(stats.getLeased() + stats.getAvailable());
	        buf.append(" of ").append(stats.getMax()).append("; ");
	        buf.append("total allocated: ").append(totals.getLeased() + totals.getAvailable());
	        buf.append(" of ").append(totals.getMax()).append("]");
	        return buf.toString();
	    }



	public Map<String,Object> getState() {
		// TODO Auto-generated method stub
		PoolStats totals = connManager.getTotalStats();
		int allocated=totals.getLeased()+totals.getAvailable();
		Map<String,Object> state=new HashMap<String,Object>();
		state.put(CURRENT_CONN_COUNT, allocated);
		state.put(MAX_CONN_COUNT, totals.getMax()); 
		return state;
	}



	public String watchSelf() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	public String getTagUrl() {
		return tagUrl;
	}



	public void setTagUrl(String tagUrl) {
		this.tagUrl = tagUrl;
	}



	public String getRequestMethod() {
		return requestMethod;
	}



	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}



	public String getKeepAlive() {
		return keepAlive;
	}



	public void setKeepAlive(String keepAlive) {
		this.keepAlive = keepAlive;
	}



	public int getRequestTimeOut() {
		return requestTimeOut;
	}



	public void setRequestTimeOut(int requestTimeOut) {
		this.requestTimeOut = requestTimeOut;
	}



	public int getConnectTimeOut() {
		return connectTimeOut;
	}



	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}



	public int getSocketTimeOut() {
		return socketTimeOut;
	}



	public void setSocketTimeOut(int socketTimeOut) {
		this.socketTimeOut = socketTimeOut;
	}



	public int getMaxConn() {
		return maxConn;
	}



	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}
 
	
	public String getParamName() {
		return paramName;
	}



	public void setParamName(String paramName) {
		this.paramName = paramName;
	}



	public Map<String,String> getStaticHeader() {
		return staticHeader;
	}



	public void setStaticHeader(Map<String,String> staticHeader) {
		this.staticHeader = staticHeader;
	}
	
	
	private static class TrustAnyHostnameVerifier implements HostnameVerifier  
	{  
	    public boolean verify(String hostname, SSLSession session)  
	    {  
	        return true;  
	    }  
	} 
	
}


