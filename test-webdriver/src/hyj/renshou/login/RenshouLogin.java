package hyj.renshou.login;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;

import ebtins.smart.proxy.company.renbao.dto.RelatePeronContent;
import ebtins.smart.proxy.company.renbao.util.RenbaoClientSSLUtil;

public class RenshouLogin {
	public static String SERVER ="10.0.64.141";
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {  
	    SSLContext sc = SSLContext.getInstance("SSL");	  
	    // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法  
	    X509TrustManager trustManager = new X509TrustManager() {  
	        @Override  
	        public void checkClientTrusted(  
	                java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                String paramString) {  
	        }  
	  
	        @Override  
	        public void checkServerTrusted(  
	                java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                String paramString) {  
	        }  
	  
	        @Override  
	        public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
	            return null;  
	        }  
	    };  
	  
	    sc.init(null, new TrustManager[] { trustManager }, null);  
	    return sc;  
	}  

	public static String get(String url,String encoding,String cookies,String referUrl
			,Map<String, String> outCookies) throws KeyManagementException, 
			NoSuchAlgorithmException, ClientProtocolException, IOException {  
		 String body = "";  
		    //采用绕过验证的方式处理https请求  
		    SSLContext sslcontext = createIgnoreVerifySSL();  
		      
		       // 设置协议http和https对应的处理socket链接工厂的对象  
		       Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
		           .register("http", PlainConnectionSocketFactory.INSTANCE)  
		           .register("https", new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER))  
		           .build(); 
			    
		       PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
		       
		       
		       //创建自定义的httpclient对象  
		    CloseableHttpClient client = HttpClients.custom()
		    		.setProxy(new HttpHost(SERVER,808))
		    		.setConnectionManager(connManager).build();  
		      
		    CookieStore cookieStore = new BasicCookieStore();			
			HttpClientContext localContext = HttpClientContext.create();
			localContext.setCookieStore(cookieStore);
			
		    //创建post方式请求对象  
		    HttpGet httpGet = new HttpGet(url);
		    if(cookies!=null) httpGet.setHeader("Cookie", cookies);
		   		  
		    System.out.println("请求地址："+url);  
		    
		    	
		    //设置header信息  
		    //指定报文头【Content-type】、【User-Agent】
		    //httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E)");  
		    httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729)");  
		    httpGet.setHeader("Proxy-Connection","Keep-Alive");
		    httpGet.setHeader("Host","9.0.6.69:7001");
		    httpGet.setHeader("Accept","application/x-ms-application, image/jpeg, application/xaml+xml, image/gif, image/pjpeg, application/x-ms-xbap, */*");
		    httpGet.setHeader("Referer",referUrl);
		    httpGet.setHeader("Accept-Language","zh-CN");
		    	
		    //执行请求操作，并拿到结果（同步阻塞）  
		    CloseableHttpResponse response = client.execute(httpGet,localContext);  
		    
		 // 如果需要输出cookie
		 			if(outCookies!=null){
		 				List<Cookie> cks = cookieStore.getCookies();					
		                 for (int i = 0; i < cks.size(); i++) {
		                 	outCookies.put(cks.get(i).getName(),cks.get(i).getValue());
		                 }
		                 System.out.println("get outCookies："+outCookies);
		 			}
		    //获取结果实体  
		    HttpEntity entity = response.getEntity();  
		    System.out.println("get返回："+response.getStatusLine());
		    if (entity != null) {  
		        //按指定编码转换结果实体为String类型  
		        body = EntityUtils.toString(entity, encoding);
		        //System.out.println("返回："+body);  
		    }  
		    EntityUtils.consume(entity);  
		    //释放链接  
		    response.close();  
		    return body;  
	}  
	
	public static String post(String url, Map<String,String> map,String encoding,String cookies,String referer,
			Map<String, String> outCookies) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {  
		 String body = "";  
		    //采用绕过验证的方式处理https请求  
		    SSLContext sslcontext = createIgnoreVerifySSL();  
		       // 设置协议http和https对应的处理socket链接工厂的对象  
		       Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
		           .register("http", PlainConnectionSocketFactory.INSTANCE)  
		           .register("https", new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER))  
		           .build(); 
			    
		       PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
		       //HttpClients.custom().setConnectionManager(connManager);  
		  
		       //创建自定义的httpclient对象  
		    CloseableHttpClient client = HttpClients.custom()
		    		.setProxy(new HttpHost(SERVER,808))
		    		.setConnectionManager(connManager).build();  
		//      CloseableHttpClient client = HttpClients.createDefault();  
		    
			//System.out.println("cookie testtt"+JSON.toJSONString(((AbstractHttpClient) client).getCookieStore().getCookies()));
		    //创建post方式请求对象  
		    HttpPost httpPost = new HttpPost(url);
		    if(cookies!=null) httpPost.setHeader("Cookie", cookies);
		    if(referer!=null) httpPost.setHeader("Referer", referer);
		      
		    //装填参数  
		    List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
		    if(map!=null){  
		        for (Entry<String, String> entry : map.entrySet()) {  
		            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
		        }  
		    }  
		    //设置参数到请求对象中  
		    httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));  
		  
		    System.out.println("请求地址："+url);  
		    System.out.println("请求参数："+nvps.toString());  
		      
		    //设置header信息  
		    //指定报文头【Content-type】、【User-Agent】  
		    httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");  
		    httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E)");  
		    httpPost.setHeader("Host","9.0.6.69:7001"); 
		    
		    
		    CookieStore cookieStore = new BasicCookieStore();			
			HttpClientContext localContext = HttpClientContext.create();
			localContext.setCookieStore(cookieStore);
		    
		    
		    //执行请求操作，并拿到结果（同步阻塞）  
		    CloseableHttpResponse response = client.execute(httpPost,localContext);  
		    //getResu(response,outCookies);
		    
		 // 如果需要输出cookie
 			if(outCookies!=null){
 				List<Cookie> cks = cookieStore.getCookies();					
                 for (int i = 0; i < cks.size(); i++) {
                 	outCookies.put(cks.get(i).getName(),cks.get(i).getValue());
                 }
                 System.out.println("post outCookies："+outCookies);
 			}
		    //获取结果实体  
		    HttpEntity entity = response.getEntity();  
		    System.out.println("post返回："+response.getStatusLine());
		    if (entity != null) {  
		        //按指定编码转换结果实体为String类型  
		        body = EntityUtils.toString(entity, encoding);
		        //System.out.println("返回："+body);  
		    }  
		    EntityUtils.consume(entity);  
		    //释放链接  
		    response.close();  
		    return body;  
	}  
	
	public static String toCookiesStr(Map<String, String> outCookies){
		StringBuffer sb= new StringBuffer();
		for(Entry<String, String> et :outCookies.entrySet()){
			sb.append(et.getKey()+"="+et.getValue()+";");			
		}
		return sb.substring(0, sb.length()-1);
	}
    public static String login() throws Exception{
    	String url0 = "http://9.0.6.69:7001/prpall/";
    	String url = "http://9.0.6.69:7001/prpall/common/pub/UILogonInput.jsp";
    	String UTF8="utf-8";
    	Map<String, String> outCookies =new HashMap<String, String>();
    	String body1 = get(url,UTF8,null,null,outCookies); 
    	String cookie = toCookiesStr(outCookies);
    	
    	String url4 = "http://9.0.6.69:7001/prpall/UICentralControl?SelfPage=/common/pub/UILogonInput.jsp";
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("sessionUserCode", "");
    	params.put("sessionComCode", "");
    	params.put("sessionUserName", "");
    	params.put("UserCode", "412828199111111010");
    	params.put("Password", "EBT.123456");
    	params.put("ComCode", "4403159003");
    	params.put("RiskCode", "0501");
    	params.put("ClassCode", "");
    	params.put("ClassCodeSelect", "05");
    	params.put("RiskCodeSelect", "0501");
    	params.put("USE0509COM", ",12,");
    	params.put("CILIFESPECIALCITY", ",2102,3302,3502,3702,4402,");
    	params.put("image.x", "114");
    	params.put("image.y", "17");
    	String body4 = post(url4,params, UTF8,cookie,url,outCookies);
    	System.out.println("body4-->"+body4);
    	System.out.println("outCookies-->"+outCookies);
    	return toCookiesStr(outCookies);
    	
    }
    public static void main(String[] args) throws Exception {
    	String cookie = login();
	}
   
  
    
}
