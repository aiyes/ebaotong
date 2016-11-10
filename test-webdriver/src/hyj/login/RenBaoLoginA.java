package hyj.login;

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
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;

public class RenBaoLoginA {
	public static String SERVER ="10.0.62.165";
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {  
	    SSLContext sc = SSLContext.getInstance("TLSv1");	  
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
		    httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E)");  
		    httpGet.setHeader("Proxy-Connection","Keep-Alive");
		    httpGet.setHeader("Host","10.134.130.208");
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
		                 System.out.println("outCookies："+outCookies);
		 			}
		    //获取结果实体  
		    HttpEntity entity = response.getEntity();  
		    System.out.println("返回："+response.getStatusLine());
		    
		    Header[] headers = response.getAllHeaders();
		    System.out.println(JSON.toJSONString(headers));
		    System.out.println(headers.length);
		    String jsseionid = "";
		    for(Header h :headers){
		    	System.out.println(JSON.toJSONString(h.getElements()));
		    	for(HeaderElement el : h.getElements()){
		    		System.out.println(el.getValue());
		    	}
		    		
		    }
		    System.out.println("jsseionid-->"+jsseionid);
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
	
	
	
	public static String post(String url, Map<String,String> map,String encoding,String cookies,String referer) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {  
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
		    httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729)");  
		    httpPost.setHeader("Host","10.134.130.208:8888"); 
		    //执行请求操作，并拿到结果（同步阻塞）  
		    CloseableHttpResponse response = client.execute(httpPost);  
		    //获取结果实体  
		    HttpEntity entity = response.getEntity();  
		    System.out.println("返回："+response.getStatusLine());
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
    public static void main(String[] args) throws Exception {
    	//String url = "http://10.134.130.208/portal/index.jsp?calogin";
    	String url = "http://10.134.130.208:8000/prpall/?calogin";
    	String UTF8="utf-8";
    	Map<String, String> outCookies =new HashMap<String, String>();
    	String body1 = get(url,UTF8,null,null,outCookies); 
    	//System.out.println(body1);
    	String userCode ="1299010766";
        org.jsoup.nodes.Document  doc =Jsoup.parse(body1);
        Elements el1 =doc.select("form[id=fm]");
        Elements el2 =doc.select("input[name=lt]");
        String jsessionid = el1.get(0).attr("action").split(";")[1];
        jsessionid = jsessionid.substring(jsessionid.indexOf("=")+1, jsessionid.indexOf("?"));
        String cookies ="prpall="+userCode+";"+"JSESSIONID="+jsessionid+";";
        
        Map<String,String> loginParams=new HashMap<String, String>();
        loginParams.put("username", userCode);
        loginParams.put("password", "hrl955188");
        loginParams.put("loginMethod", "nameAndPwd");
        
        loginParams.put("rememberFlag", "0");
        loginParams.put("lt",el2.attr("value"));
        loginParams.put("key", "yes");
        loginParams.put("_eventId", "submit");
        loginParams.put("userMac", "50:E5:49:2F:1D:B9");
        loginParams.put("button.x", "44");
        loginParams.put("button.y", "6");
        loginParams.put("errorKey", "null");
        
        String loginUrl="https://10.134.130.208:8888"+el1.get(0).attr("action");
        String body2 = post(loginUrl,loginParams, UTF8,cookies,url);
        System.out.println("-------------------body2---------");
        System.out.println(body2);
    	
    	
        String ticketText = Jsoup.parse(body2).select("a").text();
        String ticket = ticketText.substring(ticketText.lastIndexOf("=")+1);
        
        
        System.out.println(body2);
        System.out.println("cookies-->"+cookies);
        System.out.println("loginUrl-->"+loginUrl);
        
        String homePageUrl = "http://10.134.130.208:8000/prpall/index.jsp?calogin&ticket="+ticket;
        //String homePageUrl = "http://10.134.130.208:8000/prpall/menu/showMenu.do?systemCode=prpall&userCode=99355911";
        System.out.println("homePageUrl-->"+homePageUrl);
        outCookies =new HashMap<String, String>();
        String body3 = get(homePageUrl,UTF8,"JSESSIONID="+jsessionid+";",null,outCookies);
        System.out.println("首页--》"+body3);
        //System.out.println(el1.get(0).attr("action").split(";")[1]);
        //System.out.println("jsessionid-->"+jsessionid);
        
        String modelCookie ="JSESSIONID="+jsessionid+";"+toCookiesStr(outCookies);
        
        String modelUrl="http://10.134.130.208:8000/prpall/vehicle/vehicleQuery.do?pageSize=10&pageNo=1";
        String modelRefUrl="http://10.134.130.208:8000/prpall/vehicle/prepareQuery.do?brandName=别克SGM6511GL8旅行车&riskCode=DAA";
        
        //String modelCookie="JSESSIONID=Qm4bYcfWvTxJDtzDQ3GB9WJx4Lw5s5LMmGpvh449QRbQwMsg1NBL!-2025148054; BOCINS_prpall_Cookie=m6b1YcfJMZnynstzQYLxQydVvClq9q967s7xhwQSv2DntrRJL6L5!558893426";        
        Map<String, String> params=new HashMap<String, String>();
		params.put("brandName", "别克SGM6511GL8旅行车");
		params.put("carShipTaxPlatFormFlag", "");
		params.put("comCode", "44030700");
		params.put("jumpToPage", "1");
		params.put("pageNo_", "1");
		params.put("pageSize_", "20");
		params.put("pm_vehicle_switch", "");
		params.put("quotationFlag", "");
		params.put("riskCode", "DAA");
		params.put("taxFlag", "");
		params.put("TCVehicleVO.brandId", "");
		params.put("TCVehicleVO.brandName", "");
		params.put("TCVehicleVO.searchCode", "");
		params.put("TCVehicleVO.vehicleAlias", "");
		params.put("TCVehicleVO.vehicleId", "");
		params.put("TCVehicleVO.vehicleName", "别克SGM6511GL8旅行车");
		params.put("totalRecords_", "");
		String body =post(modelUrl, params,"GBK",modelCookie,modelRefUrl);
		System.out.println(body);
		
		/*String itemKindRefer = "http://10.134.130.208:8000/prpall/business/browsePolicyNo.do?bizNo=PDAA201544030000039763";
		//String itemKindUrl = "http://10.134.130.208:8000/prpall/business/showCitemKind.do?editType=SHOW_POLICY&bizType=POLICY&bizNo=PDAA201544030000039763&riskCode=DAA&minusFlag=&contractNo=&comCode=44030716&originQuery=&proposalNo=TDAA201544030000056414&rnd614=Mon Nov 7 13:44:34 UTC+0800 2016";
		String itemKindUrl = "http://10.134.130.208:8000/prpall/business/showCitemKind.do?editType=SHOW_POLICY&bizType=POLICY&bizNo=PDAA201544030000039763&riskCode=DAA&minusFlag=&contractNo=%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20&comCode=44030716&originQuery=&proposalNo=TDAA201544030000056414&rnd614=Mon%20Nov%207%2013:44:34%20UTC+0800%202016";
		String itemKinddbody =get(itemKindUrl,UTF8,modelCookie,itemKindRefer,outCookies);
		System.out.println(itemKinddbody);*/
    } 
    public static String login() throws Exception{
    	String url = "http://10.134.130.208:8000/prpall/?calogin";
    	String UTF8="utf-8";
    	Map<String, String> outCookies =new HashMap<String, String>();
    	String body1 = get(url,UTF8,null,null,outCookies); 
    	String userCode ="1299010766";
        org.jsoup.nodes.Document  doc =Jsoup.parse(body1);
        Elements el1 =doc.select("form[id=fm]");
        Elements el2 =doc.select("input[name=lt]");
        String jsessionid = el1.get(0).attr("action").split(";")[1];
        jsessionid = jsessionid.substring(jsessionid.indexOf("=")+1, jsessionid.indexOf("?"));
        String cookies ="prpall="+userCode+";"+"JSESSIONID="+jsessionid+";";
        
        Map<String,String> loginParams=new HashMap<String, String>();
        loginParams.put("username", userCode);
        loginParams.put("password", "hrl955188");
        loginParams.put("loginMethod", "nameAndPwd");
        loginParams.put("rememberFlag", "0");
        loginParams.put("lt",el2.attr("value"));
        loginParams.put("key", "yes");
        loginParams.put("_eventId", "submit");
        loginParams.put("userMac", "50:E5:49:2F:1D:B9");
        loginParams.put("button.x", "44");
        loginParams.put("button.y", "6");
        loginParams.put("errorKey", "null");
        
        String loginUrl="https://10.134.130.208:8888"+el1.get(0).attr("action");
        String body2 = post(loginUrl,loginParams, UTF8,cookies,url);
        String ticketText = Jsoup.parse(body2).select("a").text();
        String ticket = ticketText.substring(ticketText.lastIndexOf("=")+1);
   
        String homePageUrl = "http://10.134.130.208:8000/prpall/index.jsp?calogin&ticket="+ticket;
        outCookies =new HashMap<String, String>();
        get(homePageUrl,UTF8,"JSESSIONID="+jsessionid+";",null,outCookies);
        String modelCookie ="JSESSIONID="+jsessionid+";"+toCookiesStr(outCookies);
        return modelCookie;
    }
}
