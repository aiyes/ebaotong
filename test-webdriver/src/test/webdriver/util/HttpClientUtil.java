package test.webdriver.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpClientUtil {
	
	/**
	 * post
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, String> params){
		return post(url,params,"utf-8",null);
	}
	
	/**
	 * post
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, String> params,String encode,String cookies) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;
				
		HttpPost httpost = new HttpPost(url);
		if(cookies!=null) httpost.setHeader("Cookie", cookies);
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
		
		Set<String> keySet = params.keySet();
		for(String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}
		
		try {
			// System.out.println("set utf-8 form entity to httppost");
			httpost.setEntity(new UrlEncodedFormEntity(nvps, encode));			 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		body = invoke(httpclient, httpost);
		return body;
	}
	
	/**
	 * get请求
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;
		
		// System.out.println("create httppost:" + url);
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);
				
		return body;
	}
	
	/**
	 * get请求
	 * @param url
	 * @return
	 */
	public static String get(String url,String cookies) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;
		
		// System.out.println("create httppost:" + url);
		HttpGet get = new HttpGet(url);		
		if(cookies!=null) get.setHeader("Cookie", cookies);
		body = invoke(httpclient, get);
		
		return body;
	}
		
	/**
	 * 调用httpclient
	 * @param httpclient
	 * @param httpost
	 * @return
	 */
	private static String invoke(CloseableHttpClient httpclient,
			HttpUriRequest httpost) {
		
		CloseableHttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);
		
		try {
			response.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return body;
	}

	/**
	 * 解析返回结果
	 * @param response
	 * @return
	 */
	private static String paseResponse(HttpResponse response) {
		// System.out.println("get response from http server..");
		HttpEntity entity = response.getEntity();
		
		// System.out.println("response status: " + response.getStatusLine());
		//String charset = EntityUtils.getContentCharSet(entity);
		// System.out.println(charset);
		
		String body = null;
		try {
			body = EntityUtils.toString(entity);
			//如果entity是流数据则关闭之
			//EntityUtils.consume(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return body;
	}

	/**
	 * 执行请求
	 * @param httpclient
	 * @param httpost
	 * @return
	 */
	private static CloseableHttpResponse sendRequest(CloseableHttpClient httpclient,
			HttpUriRequest httpost) {
		// System.out.println("execute post..."+httpost);
		CloseableHttpResponse  response = null;
		
		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
}
