package hyj.login;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestSSL {
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {  
	    SSLContext sc = SSLContext.getInstance("SSLv3");	  
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
	
	public static String get(String url,String encoding,String cookies) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {  
		 String body = "";  
		    //采用绕过验证的方式处理https请求  
		    SSLContext sslcontext = createIgnoreVerifySSL();  
		      
		       // 设置协议http和https对应的处理socket链接工厂的对象  
		       Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
		           .register("http", PlainConnectionSocketFactory.INSTANCE)  
		           .register("https", new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER))  
		           .build(); 
			    
		       PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
		       HttpClients.custom().setConnectionManager(connManager);  
		  
		       //创建自定义的httpclient对象  
		    CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();  
		//      CloseableHttpClient client = HttpClients.createDefault();  
		      
		    //创建post方式请求对象  
		    HttpGet httpGet = new HttpGet(url);
		    if(cookies!=null) httpGet.setHeader("Cookie", cookies);
		   		  
		    System.out.println("请求地址："+url);  
		      
		    //设置header信息  
		    //指定报文头【Content-type】、【User-Agent】
		    httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E)");  
		      
		    //执行请求操作，并拿到结果（同步阻塞）  
		    CloseableHttpResponse response = client.execute(httpGet);  
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
		       HttpClients.custom().setConnectionManager(connManager);  
		  
		       //创建自定义的httpclient对象  
		    CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();  
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
		    httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
		      
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
    public static void main(String[] args) throws Exception {  
    	String UTF8="utf-8";
    	String userCode ="101154139";
        String url = "https://webpolicy.sinosafe.com.cn/pcis/core/login/realLogin.jsp";  
        String body = get(url,"UTF-8",null);  
        org.jsoup.nodes.Document  doc =Jsoup.parse(body);
        Elements el1 =doc.select("form[name=loginForm]");
        Elements el2 =doc.select("input[name=securityCheckURL]");
        String cookies =el1.get(0).attr("action").split(";")[1].toUpperCase()+";quickAppSkin=red;empCde="+userCode+";";
        System.out.println("cookies:"+cookies);
        
        String loginUrl="https://webpolicy.sinosafe.com.cn"+el1.get(0).attr("action");
        
        String codeUrl="https://webpolicy.sinosafe.com.cn/pcis/core/login/generateValiCode.jsp?rc=%s&valiCode=%s";
        String voliCode =System.nanoTime()%9999+"";
        codeUrl= String.format(codeUrl,Math.random()+"",voliCode);
        get(codeUrl,UTF8,cookies);       
        
        Map<String,String> loginParams=new HashMap<String, String>();
        loginParams.put("securityCheckURL", el2.attr("name"));
        loginParams.put("j_username", userCode);
        loginParams.put("j_password", "QIqi456");
        loginParams.put("valiCode", voliCode);
        
        post(loginUrl,loginParams, UTF8,cookies,"https://webpolicy.sinosafe.com.cn/pcis/core/login/realLogin.jsp");
        /*
        get("https://webpolicy.sinosafe.com.cn/pcis//core/main.jsp",UTF8,cookies);                
        Map<String,String> actionParams=new HashMap<String, String>();        
        actionParams.put("ACTION_HANDLE", "perform");
        actionParams.put("ADAPTER_TYPE", "JSON_TYPE");
        actionParams.put("BEAN_HANDLE", "baseAction");
        actionParams.put("BIZ_SYNCH_CONTINUE", "false");
        actionParams.put("CODE_TYPE", "CODE_TYPE");
        actionParams.put("CUST_DATA", "cMacAdd=50:E5:49:2F:1D:B9|00:50:56:C0:00:01|00:50:56:C0:00:08|08:00:27:00:2C:48|08:00:27:00:1C:AB###macDiskCpuId=50:E5:49:2F:1D:B9|00:50:56:C0:00:01|00:50:56:C0:00:08|08:00:27:00:2C:48|08:00:27:00:1C:AB~-2140491293~BFEBFBFF000206A7###empCde=101154139");
        actionParams.put("DW_DATA", "[]");
        actionParams.put("HELPCONTROLMETHOD", "common");
        actionParams.put("SCENE", "UNDEFINED");
        actionParams.put("SERVICE_MOTHOD", "getPermissonBaseInfo");
        actionParams.put("SERVICE_NAME", "quickAppQueryBizAction");
        actionParams.put("SERVICE_TYPE", "ACTION_SERVIC");
        String actionUrl="https://webpolicy.sinosafe.com.cn/pcis//core/actionservice.ai";
        post(actionUrl, actionParams, UTF8, cookies,null);
        
        Map<String,String> userParams=new HashMap<String, String>();
        userParams.put("BROWSERNAME", "Microsoft Internet Explorer");
        userParams.put("BROWSERVERSION", "MSIE8.0");
        userParams.put("DPTCDE", "01980003");
        userParams.put("MACADD", "50:E5:49:2F:1D:B9|00:50:56:C0:00:01|00:50:56:C0:00:08|08:00:27:00:2C:48|08:00:27:00:1C:AB");
        userParams.put("OPTYPE", "LoginSys");
        String userUrl="https://webpolicy.sinosafe.com.cn/pcis//core/actionservice.ai";
        post(userUrl, userParams, UTF8, cookies,null);
        get("https://webpolicy.sinosafe.com.cn/pcis/core/pcis_main.jsp",UTF8,cookies);
        */       
        //查询车型
        Map<String,String> queryModelParams=new HashMap<String, String>();  
        queryModelParams.put("ACTION_HANDLE", "perform");
        queryModelParams.put("BEAN_HANDLE", "baseAction");
        queryModelParams.put("BIZ_SYNCH_CONTINUE", "false");
        queryModelParams.put("BIZ_SYNCH_DESC", "");
        queryModelParams.put("BIZ_SYNCH_LOCK", "");
        queryModelParams.put("BIZ_SYNCH_MODULE_CODE", "");
        queryModelParams.put("BIZ_SYNCH_NO", "");
        queryModelParams.put("CODE_TYPE", "UTF-8");        
        queryModelParams.put("CUST_DATA", "cProdNo=###cDptCde=01980003");
        queryModelParams.put("DW_DATA", "<data><dataObjs type=\"ONE_SELECT\"  dwid=\"dwid0.6654808867067782\" rsCount=\"5\" pageSize=\"8\" pageNo=\"1\" pageCount=\"1\" dwName=\"quickapp.quick_vehicle_DW\" difFlag=\"false\"/><filters colsInOneRow=\"2\" dwName=\"quickapp.quick_vehicle_DW\"><filter isGroupBegin=\"true\" isGroupEnd=\"true\" isHidden=\"false\" isRowBegin=\"true\" name=\"null\" width=\"150\"/><filter checkType=\"Text\" cols=\"1\" dataType=\"STRING\" dateFormat=\"yyyy-MM-dd HH:mm\" defaultValue=\"\" isGroupBegin=\"true\" isGroupEnd=\"true\" isHidden=\"false\" isRowBegin=\"false\" isRowEnd=\"false\" name=\"VehicleId\" operator=\"*\" rows=\"1\" tableName=\"\" title=\"车型代码\" type=\"input\" width=\"190\" issExtValue=\"\"/><filter checkType=\"Text\" cols=\"1\" dataType=\"STRING\" dateFormat=\"yyyy-MM-dd HH:mm\" defaultValue=\"江淮HFC7202EF轿车\" isGroupBegin=\"true\" isGroupEnd=\"true\" isHidden=\"false\" isRowEnd=\"true\" name=\"VehicleName\" operator=\"*\" rows=\"1\" tableName=\"\" title=\"车型名称\" type=\"input\" width=\"190\" issExtValue=\"江淮HFC7202EF轿车\"/><filter isGroupBegin=\"true\" isGroupEnd=\"true\" isHidden=\"false\" isRowBegin=\"true\" name=\"null\" width=\"150\"/><filter checkType=\"Text\" cols=\"1\" dataType=\"STRING\" dateFormat=\"yyyy-MM-dd HH:mm\" defaultValue=\"\" isGroupBegin=\"true\" isGroupEnd=\"true\" isHidden=\"false\" isReadOnly=\"true\" isRowBegin=\"false\" isRowEnd=\"false\" name=\"VINNo\" operator=\"*\" rows=\"1\" tableName=\"\" title=\"识别代码/车架号\" type=\"input\" width=\"190\" issExtValue=\"\"/><filter checkType=\"Text\" cols=\"1\" dataType=\"STRING\" dateFormat=\"yyyy-MM-dd HH:mm\" defaultValue=\"01980003\" isGroupBegin=\"true\" isGroupEnd=\"true\" isHidden=\"false\" isRowEnd=\"true\" name=\"CDptCde\" operator=\"*\" rows=\"1\" tableName=\"承保机构\" title=\"\" type=\"hidden\" width=\"190\"/></filters></data>");
        queryModelParams.put("HELPCONTROLMETHOD", "common");
        queryModelParams.put("SCENE", "UNDEFINED");
        queryModelParams.put("SERVICE_MOTHOD", "queryVehicleList");
        queryModelParams.put("SERVICE_NAME", "quickAppPageBizAction");
        queryModelParams.put("SERVICE_TYPE", "ACTION_SERVIC");
        String qModelUrl="https://webpolicy.sinosafe.com.cn/pcis/actionservice.ai";
        String rString=post(qModelUrl, queryModelParams, UTF8, cookies,"https://webpolicy.sinosafe.com.cn/pcis/policy/universal/quickapp/jsp/open_quick_vehicle.jsp?dptcode=01980003&vhlName=江淮HFC7202EF轿车&vin_no=&prodNo=");
        System.out.println(rString);
        JSONObject jsonR=JSON.parseObject(rString);
        JSONArray arr=(JSONArray) jsonR.get("DATA");
        String htmlC=arr.get(0).toString();
        Document  resDoc =Jsoup.parse(htmlC);
        Elements elList = resDoc.select("dataObj");
        for(Element el:elList){        	
        	System.out.print(el.select("attribute[name=VehicleId]").attr("value")+"\t");
        	System.out.print(el.select("attribute[name=VehicleName]").attr("value")+"\t");
        	System.out.print(el.select("attribute[name=Remark]").attr("value")+"\r\n");
        }
    }  
}
