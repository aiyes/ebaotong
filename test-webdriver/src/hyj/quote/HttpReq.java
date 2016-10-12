
package hyj.quote;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;

import javax.net.ssl.SSLContext;
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
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebtins.dto.open.CarQuoteInfoVo;
import com.ebtins.dto.open.CarQuoteRes;

import huaan.quote.util.StringUtil;

/**
 * @ClassName: HttpReq
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年10月9日 上午10:28:24
 *
 */

public class HttpReq {
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
		   		  
		    //System.out.println("get请求地址："+url);  
		      
		    //设置header信息  
		    //指定报文头【Content-type】、【User-Agent】
		    httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E)");  
		      
		    //执行请求操作，并拿到结果（同步阻塞）  
		    CloseableHttpResponse response = client.execute(httpGet);  
		    //获取结果实体  
		    HttpEntity entity = response.getEntity();  
		    //System.out.println("get返回："+response.getStatusLine());
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
		  
		    //System.out.println("post请求地址："+url);  
		    //System.out.println("post请求参数："+nvps.toString());  
		      
		    //设置header信息  
		    //指定报文头【Content-type】、【User-Agent】  
		    httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");  
		    httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
		      
		    //执行请求操作，并拿到结果（同步阻塞）  
		    CloseableHttpResponse response = client.execute(httpPost);  
		    //获取结果实体  
		    HttpEntity entity = response.getEntity();  
		    //System.out.println("post返回："+response.getStatusLine());
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
	/**
	 * @Description: TODO(重写post方法)
	 * @author yejie.huang
	 * @date 2016年10月9日 上午10:31:35
	 */
	public static String post(String url,String encoding,String cookies,String referer,String cust_data,String dw_data,String method) throws Exception{
		   	 Map<String,String> queryModelParams=new HashMap<String, String>();  
		        queryModelParams.put("ACTION_HANDLE", "perform");
		        queryModelParams.put("ADAPTER_TYPE", "JSON_TYPE");
		        queryModelParams.put("BEAN_HANDLE", "baseAction");
		        queryModelParams.put("BIZ_SYNCH_CONTINUE", "false");
		        queryModelParams.put("CODE_TYPE","CODE_TYPE");        
		        queryModelParams.put("CUST_DATA", cust_data);
		        queryModelParams.put("DW_DATA", dw_data);
		        queryModelParams.put("HELPCONTROLMETHOD", "common");
		        queryModelParams.put("SCENE", "UNDEFINED");
		        queryModelParams.put("SERVICE_MOTHOD", method);
		        queryModelParams.put("SERVICE_NAME", "quickAppBaseBizAction");
		        queryModelParams.put("SERVICE_TYPE", "ACTION_SERVIC");
		        return post(url, queryModelParams,encoding, cookies,referer);
    }
	/**
	 * @Description: TODO(点击保费计算按钮，此过程共经过3个post请求，最后返回报价)
	 * @param post1String
	 * @throws Exception
	 * @author yejie.huang
	 * @date 2016年10月9日 上午10:37:47
	 */
    public static String quoteReq(String post1String,String post2String) throws Exception {  
    	String UTF8="utf-8";
    	String userCode ="101154139";
        String url = "https://webpolicy.sinosafe.com.cn/pcis/core/login/realLogin.jsp";  
        String body = get(url,"UTF-8",null);  
        org.jsoup.nodes.Document  doc =Jsoup.parse(body);
        Elements el1 =doc.select("form[name=loginForm]");
        Elements el2 =doc.select("input[name=securityCheckURL]");
        String cookies =el1.get(0).attr("action").split(";")[1].toUpperCase()+";quickAppSkin=red;empCde="+userCode+";";
        //System.out.println("cookies:"+cookies);
        
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
        //查询保费
        Map<String,String> queryModelParams=new HashMap<String, String>();  
        String qModelUrl="https://webpolicy.sinosafe.com.cn/pcis/policy/universal/quickapp/actionservice.ai";
        String referer = "https://webpolicy.sinosafe.com.cn/pcis/policy/universal/quickapp/vhl_quick_app_newcommercial.jsp?dptCde=01980003&dptNm=%E5%85%AC%E5%8F%B8%E4%B8%9A%E5%8A%A1%E9%83%A8&scene=PLY_APP_NEW_SCENE&prodNo=&ctrlInfo=&typeFrom=null&__param_data=<?xml%20version='1.0'?><databag><dwdatabag/><paradatabag/></databag>";
        String post1_cust_data = "isPlateCalcVsTax=1###sy_vhlflat=###jq_vhlflat=###isSubmit=undefined###dptCde=01###syCRenewMrk0";
        String post2_cust_data = "isPlateCalcVsTax=1###sy_vhlflat=###jq_vhlflat=###isSubmit=undefined###dptCde=01###syCRenewMrk0###appQryCheckResultSY=true###isAfterInsuranceInq=1";
        
        //post:点击保费计算按钮
         String post1Content=post(qModelUrl,UTF8, cookies,referer,post1_cust_data,post1String,"calcPremiumSino");
         System.out.println(post1Content);
         getResultMsg(post1Content);
         
        //get:点击保费计算后第一次弹出框
        String get1_string =get("https://webpolicy.sinosafe.com.cn/pcis/jsp/panel/repeat_insure_info_newC.jsp","UTF-8",cookies);
        
        //替换 dwName: 'prodDef.vhl.Cvrg_DW'内容 和其他参数
        //post2String = repCvrgAndParam(post2String,post1Content,getPost1ContentParamValue(post1Content));
        //post:第一次弹出框点击确认后post
        String post2Content = post(qModelUrl,UTF8,cookies,referer,post2_cust_data,getNewPostByBackContent(post1Content),"calcPremiumSino");
        getResultMsg(post2Content);
        
        //post：保费计算成功，返回数据
        //post3_dw_data = repCvrgAndParam(post3_dw_data,post2Content,getPost2ContentParamValue(post2Content));
        String post3Content = post(qModelUrl,UTF8,cookies,referer,post2_cust_data,getNewPostByBackContent(post2Content),"sendToILog");
       
        //最后一次get请求
        //String get2_string =get("https://webpolicy.sinosafe.com.cn/pcis/policy/universal/quickapp/jsp/open_quick_spec_for_auto.jsp?","UTF-8",cookies);
      
        //System.out.println("-----------返回报文----------");	
       
        resData(post3Content);
        
        return post1Content;
       
    }
    /**
     * @Description: TODO(根据返回报文，截取/替换相应字符，转换成符合下次提交的报文)
     * @param content返回报文
     * @return 可供下一次提交的符合规则的报文
     * @author yejie.huang
     * @date 2016年10月11日 上午10:37:28
     */
    public static String getNewPostByBackContent(String content){
		String newsStr = content.replace("\"syReinsureItemList\":\"", "\"syReinsureItemList\":")//去除syReinsureItemList属性值后面左双引号，否则无法转换成json
								.replace("\",\"question\"", ",\"question\"")//去除syReinsureItemList属性右双引号
								.replace("\\", "")//去除\
								.replaceAll(",\"text\":\"[^\"]*\"", "")//去除 ,"text": ""
								.replaceAll("\\{[^\\{]*SY_Base\\.CConnectFlag[^\\}]*\\}", "{name:'SY_Base.CConnectFlag',newValue:'1',bakValue:'',value:''}");//置SY_Base.CConnectFla的newValue值为1
		JSONObject jo = JSON.parseObject(newsStr);
		String newPostStr = jo.getString("WEB_DATA");    	
		return newPostStr;
    }
    /**
     * @Description: TODO(获取报文状态信息)
     * @param content返回报文
     * @return 状态消息
     * @author yejie.huang
     * @date 2016年10月11日 下午2:39:32
     */
    public static String getResultMsg(String content){
    	String reg = "[\"|'|\\\"]RESULT_MSG[\"|'|\\\"]:[\"|'|\\\"]([^'\"\\\"]+)[\"|'|\\\"]";
    	String msg = StringUtil.regString(content,reg, 1);
    	System.out.println(msg);
    	return msg;
    }
    /**
     * @Description: TODO(报价返回结果组装)
     * @param resContent返回报文
     * @author yejie.huang
     * @date 2016年10月11日 下午2:40:46
     */
    public static void resData(String resContent){
    	CarQuoteRes qRes = new CarQuoteRes();
    	qRes.setMerchantOrderId("");
    	qRes.setStatus("");
    	qRes.setRemark("");
    	CarQuoteInfoVo info = new CarQuoteInfoVo();
    	qRes.setCarQuoteInfo(info);
    	info.setCompanyType("HUAAN");
    	info.setInsurerName("华安保险");
    	Map<String,String> params = new HashMap<String,String>();
		List<String> resParams = new ArrayList<String>();
		resParams.add("SY_Base.NPrm");//商业总保费
		resParams.add("SY_Base.NAmt");//商业总保额
		resParams.add("Vhl.NActualValue");//车辆实际价值
		resParams.add("Base.CProdNo");//订单号，待确认
		resParams.add("SY_Vhl.CQryCde");//商业险报价单号，待确认
		for(String resParam:resParams){
			String reg = "\\{[^\\{]*"+resParam+"[^\\}]*\\}";
		    Matcher m = StringUtil.createMatcher(resContent,reg);
		    if(m.find()){
		    	String valueObject = m.group(0);
		    	String value = StringUtil.regString(valueObject,"\"value\":\\s?\"([^\"]+?)\"",1);//提取newValue的值
		    	params.put(resParam, value);
		    	System.out.println(resParam+":"+value);
		    }
			
		}
		for(String name:params.keySet()){
			if(name.equals("SY_Base.NAmt")){
				info.setSumInsured(params.get(name));
			}else if(name.equals("Vhl.NActualValue")){
				info.setActualValue(params.get(name));
			}else if(name.equals("Base.CProdNo")){
				qRes.setOrderId(params.get(name));
				info.setOrderId(params.get(name));
			}else if(name.equals("SY_Vhl.CQryCde")){
				info.setQuoteNo(params.get(name));
			}else if(name.equals("SY_Base.NPrm")){
				info.setSumBiPremium(Double.parseDouble(params.get(name)));
			}
		}
		String json = JSON.toJSONString(qRes);
		System.out.println(json);
	}
    /**
     * @Description: TODO(根据参数名查找post1报文，获取参数名的值)
     * @param content post1返回报文
     * @return 返回  参数名=参数值
     * @author yejie.huang
     * @date 2016年10月10日 下午2:49:24
     */
    public static Map<String,String> getPost1ContentParamValue(String content){
    	List<String> params = new ArrayList<String>();
    	params.add("SY_Vhl.CQryCde");
    	params.add("Pay.CProdNo");
    	params.add("SY_PrmCoef.CTgtfld15");
    	params.add("SY_PrmCoef.NTgtfld100");
    	return getContentParamValue(content,params);
    	
    }
    /**
     * @Description: TODO(根据参数名查找post2报文，获取参数名的值)
     * @param content post2返回报文
     * @return 返回  参数名=参数值
     * @author yejie.huang
     * @date 2016年10月10日 下午2:49:24
     */
    public static Map<String,String> getPost2ContentParamValue(String content){
    	List<String> params = new ArrayList<String>();
    	params.add("SY_Base.NAmt");
    	params.add("Pay.CPayorNme");
    	params.add("Pay.CPayorCde");
    	return getContentParamValue(content,params);
    	
    }
    /**
     * @Description: TODO(根据post1返回报文截取参数值用于post2提交)
     * @param content post1返回报文
     * @param params 指定需要查找的参数
     * @return返回  参数名=参数值
     * @author yejie.huang
     * @date 2016年10月9日 下午1:43:45
     */
    public static Map<String,String> getContentParamValue(String content,List<String> params){
    	Map<String,String> map = new HashMap<String,String>();
    	for(String paramName:params){
        	String reg = "\\{[^\\{]*"+paramName+"\",\"value\":\"([^\"}]+)\"[^\\}]*\\}";
        	Matcher m = StringUtil.createMatcher(content,reg);
    		if(m.find()){
    			String value = m.group(1);
    			map.put(paramName, value);
    		}
    	}
		return map;
    }
    /**
     * 
     * @Description: TODO(替换post2提交的cvrg内容和其他参数值)
     * @param post2_dw_data 原post2报文
     * @param post1Content  post1返回的报文
     * @param params  参数名=参数值
     * @return 替换后的报文
     * @author yejie.huang
     * @date 2016年10月9日 下午5:24:38
     */
    public static String repCvrgAndParam(String post2_dw_data,String post1Content,Map<String,String> params){
    	
    	  /**
    	   * 截取psot1返回报文dwName: 'prodDef.vhl.Cvrg_DW'内容
    	   */
    	  String reg = "(\"dwName\":\"prodDef\\.vhl\\.Cvrg_DW\".*?),\"attrControlList\"";
		  Matcher m = StringUtil.createMatcher(post1Content,reg);
		  String post1ContentCvrg="";
		  if(m.find()){
			  post1ContentCvrg = m.group(1);
			  post1ContentCvrg = post1ContentCvrg.substring(post1ContentCvrg.indexOf("\"dataObjVoList\""));
			  //去掉,"text":"" 另外后面补上}
			  post1ContentCvrg = post1ContentCvrg.replaceAll(",\"text\":\"[^\"]*\"", "")+"}";
		  }
		  
		  /**
    	   * 截取post2提交报文的dwName: 'prodDef.vhl.Cvrg_DW'内容
    	   */
		  String reg1 = "(\\{isFilter:'undefined',dwType:'GRID_CVRG'.*?),\\{isFilter";
		  Matcher m1 = StringUtil.createMatcher(post2_dw_data,reg1);
		  String post2Cvrg = "";
		  if(m1.find()){
			  post2Cvrg = m1.group(1);
			  post2Cvrg = post2Cvrg.substring(post2Cvrg.indexOf("dataObjVoList"));
		  }
		  
		  /**
    	   * 替换Cvrg
    	   */
		  post2_dw_data = post2_dw_data.replace(post2Cvrg, post1ContentCvrg);
		  /**
    	   * 替换其他指定参数
    	   */
		  post2_dw_data = ParamReplace.replacedParam(post2_dw_data, params);
		  //post2_dw_data = ParamReplace.getNewDwData(post2_dw_data, params);
		  
	return post2_dw_data;	  
    }
   
}
