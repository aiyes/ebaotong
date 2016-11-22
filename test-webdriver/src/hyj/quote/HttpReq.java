
package hyj.quote;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.ebtins.dto.open.CarQuoteInsItemVo;
import com.ebtins.dto.open.CarQuoteReq;
import com.ebtins.dto.open.CarQuoteRes;
import com.ebtins.open.common.constant.CarInsuranceConstant;
import com.ebtins.open.common.util.IDCardUtil;
import com.ebtins.open.common.util.TimeUtil;

import ebtins.smart.proxy.company.huaan.util.HuaanConfig;
import ebtins.smart.proxy.company.huaan.util.HuaanUtil;
import ebtins.smart.proxy.company.huaan.util.HuaanVerifyQuoteReqUtil;
import ebtins.smart.proxy.conf.Constants;
import newQuote.AttributeVo;
import newQuote.DataObjVo;
import newQuote.ReqDwData;
import newQuote.ResContent;

/**
 * @ClassName: HttpReq
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年10月9日 上午10:28:24
 *
 */

public class HttpReq {
	static final String FAIL="11000";
	static final String SUCCESS="0000";
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
		   		  
		    System.out.println("get请求地址："+url);  
		      
		    //设置header信息  
		    //指定报文头【Content-type】、【User-Agent】
		    httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E)");  
		      
		    //执行请求操作，并拿到结果（同步阻塞）  
		    CloseableHttpResponse response = client.execute(httpGet);  
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
		  
		    System.out.println("post请求地址："+url);  
		    System.out.println("post请求参数："+nvps.toString());  
		      
		    //设置header信息  
		    //指定报文头【Content-type】、【User-Agent】  
		    httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");  
		    httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
		      
		    //执行请求操作，并拿到结果（同步阻塞）  
		    CloseableHttpResponse response = client.execute(httpPost);  
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
    public static CarQuoteRes quoteReq(String post1String,CarQuoteReq req) throws Exception {
    	CarQuoteRes res = new CarQuoteRes();
    	Map<String,Object> params = HuaanConfig.getParamMap();
    	post1String = getNewDwData(req,res,post1String,params);
    	if(res.getHeader().getResCode()!=null&&res.getHeader().getResCode().equals(Constants.FAIL)){
    		return res;
    	}
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
        loginParams.put("j_password", "QIqi789");
        loginParams.put("valiCode", voliCode);
        post(loginUrl,loginParams, UTF8,cookies,"https://webpolicy.sinosafe.com.cn/pcis/core/login/realLogin.jsp");
        //查询保费
        Map<String,Object> postStrObj = HuaanConfig.getPostStr();
        String qModelUrl=(String) postStrObj.get("qModelUrl");
        String referer=(String) postStrObj.get("referer");
        String post1_cust_data=(String) postStrObj.get("post1_cust_data");
        String post2_cust_data=(String) postStrObj.get("post2_cust_data");
        
        String post2String = "",msg1="";
        /**
         * 如果只购买交强险，只执行第二、第三次post请求
         */
        if(params.get("Base.CProdNo").toString().equals("0360")){
        	post2String = post1String;
        }else{
        	//post:点击保费计算按钮（第一次post请求）
            System.out.println("请求报文1--->"+post1String);
            String post1Content=post(qModelUrl,UTF8, cookies,referer,post1_cust_data,post1String,"calcPremiumSino");
            System.out.println("返回报文1--->"+post1Content);
            msg1 = HuaanUtil.getResultMsg(post1Content);
            
           //get:点击保费计算后第一次弹出框
           //String get1_string =get((String) postStrObj.get("get1_url"),"UTF-8",cookies);
           
            post2String = HuaanUtil.getNewPostByBackContent(post1Content,2);
        }
        //post:第一次弹出框点击确认后（第二次post请求）   
        System.out.println("请求报文2--->"+post2String);
        String post2Content = post(qModelUrl,UTF8,cookies,referer,post2_cust_data,post2String,"calcPremiumSino");
        System.out.println("返回报文2--->"+post2Content);
        String msg2 = HuaanUtil.getResultMsg(post2Content);
        
        //post：保费计算成功，返回数据（第三次post请求）   
        String post3String = HuaanUtil.getNewPostByBackContent(post2Content,3);
        System.out.println("请求报文3--->"+post3String);
        String post3Content = post(qModelUrl,UTF8,cookies,referer,post2_cust_data,post3String,"sendToILog");
        System.out.println("返回报文3--->"+post3Content);
        //最后一次get请求
        //String get2_string =get((String) postStrObj.get("get2_url"),"UTF-8",cookies);
        
        //post：保存（第四次post请求）  
        String post4String = HuaanUtil.getNewPostByBackContent(post3Content,4);
        String save_cust_data=(String) postStrObj.get("save_cust_data");
        System.out.println("请求报文4--->"+post4String);
        String post4Content = post(qModelUrl,UTF8,cookies,referer,save_cust_data,post4String,"savePlyApp");
        System.out.println("返回报文4--->"+post4Content);
         
        System.out.println("-----------返回报文----------");	
        return  getResData(req,post3Content,msg1+";"+msg2);
       
    }
    /**
     * @Description: TODO(封装报价返回结果)
     * @param carQuoteReq 请求对象
     * @param resContent字符格式报文
     * @return 返回CarQuoteRes对象
     * @author yejie.huang
     * @date 2016年10月11日 下午2:40:46
     */
    public static  CarQuoteRes getResData(CarQuoteReq carQuoteReq,String resContent,String msg){
		CarQuoteRes qRes = getResQuote(resContent,msg);
		qRes.setMerchantOrderId(carQuoteReq.getMerchantOrderId());
		if (qRes.getCarQuoteInfo() == null) {
			CarQuoteInfoVo infoVo = new CarQuoteInfoVo();
			qRes.setCarQuoteInfo(infoVo);
			infoVo.setInsurerId(carQuoteReq.getInsurerId() == null ? "" : carQuoteReq.getInsurerId());
			infoVo.setCompanyType(carQuoteReq.getCompanyType());
			infoVo.setQuoteNo(carQuoteReq.getQuoteNo());
		} else {
			qRes.getCarQuoteInfo().setInsurerId(carQuoteReq.getInsurerId() == null ? "" : carQuoteReq.getInsurerId());
			qRes.getCarQuoteInfo().setCompanyType(carQuoteReq.getCompanyType());
			qRes.getCarQuoteInfo().setQuoteNo(carQuoteReq.getQuoteNo());// 报价编号
		}
		
		qRes.setOrderId(carQuoteReq.getOrderId());// 订单编号
		return qRes;
	}
    /**
     * @Description: TODO(封装CarQuoteRes)
     * @param content报文
     * @return 返回CarQuoteRes对象
     * @author yejie.huang
     * @date 2016年10月18日 下午5:26:31
     */
	public static CarQuoteRes getResQuote(String content,String msg) {
		
		CarQuoteRes res = new CarQuoteRes();
		ResContent resContent = (ResContent) JSON.parseObject(content,ResContent.class);
		if(resContent==null){
			res.getHeader().setResCode(FAIL);
			res.getHeader().setResMsg("解析报错-保险公司异常");
			return res;
		}else if(!resContent.getRESULT_TYPE().equals("SUCCESS")){
			res.getHeader().setResCode(FAIL);
			res.getHeader().setResMsg(msg+";"+resContent.getRESULT_MSG());
			return res;
		}else{
			res.setOrderId("");// 为空
			res.getHeader().setResCode(SUCCESS);
			res.getHeader().setResMsg(resContent.getRESULT_MSG());
			res.setRemark("");

			// 报价失败直接返回
			if (FAIL.equals(res.getHeader().getResCode()))
				return res;

			CarQuoteInfoVo info = new CarQuoteInfoVo();
			res.setCarQuoteInfo(info);

			info.setQuoteNo("");// 暂空
			info.setOrderId("");// 暂空
			info.setInsurerName("");// 暂空
			info.setCompanyType("");// 保险公司
			info.setSumStdPrem("");//标准保费
			info.setSumPayTax("");//车船税总缴付税额
			info.setTaxType("");// 可空
			info.setThisPayTax("0");// 可空
			info.setPrePayTax("0");// 可空
			info.setDelayPayTax("0");// 可空
			info.setSumPayablePrem("");//应交保费，不含车船税

			//info.setSumPayAmount();//应付金额，应交保费 + 车船税总额
			Map<String,String> nameValue = HuaanUtil.getResContentValues(content);
			
			double syPremimum =0.0,syInsured=0.0;
			if(nameValue.get("Base.CProdNo").indexOf("0380")>-1){
				syInsured = Double.parseDouble(nameValue.get("SY_Base.NAmt"));//商业险保额
				syPremimum = Double.parseDouble(nameValue.get("SY_Base.NPrm"));//商业险保费
			}

			double jqPremimum =0.0,jqInsured=0.0,jqTax=0.0;
			if(nameValue.get("Base.CProdNo").indexOf("0360")>-1){
				jqInsured = Double.parseDouble(nameValue.get("JQ_Base.NAmt"));//交强险保额
				jqPremimum = Double.parseDouble(nameValue.get("JQ_Base.NPrm"));//交强险保费
				jqTax = Double.parseDouble(nameValue.get("VsTax.NAggTaxAmt"));//税金合计
			}
			info.setThisPayTax(String.valueOf(jqTax));//车船税当年应缴
			info.setSumPayablePrem(String.valueOf(syPremimum+jqPremimum));//应交保费，不含车船税
			info.setSumPayAmount(syPremimum+jqPremimum+jqTax);//应付金额，应交保费 + 车船税总额
			info.setSumCiPremium(jqPremimum+jqTax);//交强险应付保费(含税)
			info.setSumBiPremium(syPremimum);//商业险应付保费
			info.setSumInsured(String.valueOf(syInsured+jqInsured));//总保额 = 商业+交强
			info.setActualValue(nameValue.get("Vhl.NActualValue"));// 车辆实际价值
			
			List<CarQuoteInsItemVo> insItemList = getInsItemList(content);
			//判断交强险是否有
			if(nameValue.get("Base.CProdNo").indexOf("0360")>-1){
				insItemList.add(getJqItem(nameValue));
			}
			info.setCarQuoteInsItemList(insItemList);//险种报价明细
		}
		return res;
	}
  
    /**
     * @Description: TODO(封装商业险明细列表List<CarQuoteInsItemVo> insItems)
     * @param resContent 返回报文
     * @return 险种明细列表
     * @author yejie.huang
     * @date 2016年10月13日 下午3:01:24
     */
    public static List<CarQuoteInsItemVo> getInsItemList(String resContent){
    	
    	/**
    	 * json字符转换为ResContent对象，获取属性“WEB_DATA”值
    	 */
		ResContent content = (ResContent) JSON.parseObject(resContent,ResContent.class);
		List<ReqDwData> dwDatas = content.getWEB_DATA();
		
		/**
		 * 获取返回报文属性dwName的值为prodDef.vhl.Cvrg_DW对象
		 */
		List<DataObjVo> dataObjVoList=null;
		for(ReqDwData dwData :dwDatas){
			if(dwData.getDwName().equals("prodDef.vhl.Cvrg_DW")){
			  dataObjVoList = dwData.getDataObjVoList();
			  break;
			}
		}
		
		/**
		 * 循环获取报文商业险别信息
		 */
		List<CarQuoteInsItemVo>  insItems= new ArrayList<CarQuoteInsItemVo>();
		for(DataObjVo objVo :dataObjVoList){
			CarQuoteInsItemVo insItemDeduflag0 = new CarQuoteInsItemVo();//计免赔
			CarQuoteInsItemVo insItemDeduflag1 = new CarQuoteInsItemVo();//不计免赔
			getSyItem(objVo,insItemDeduflag0,insItemDeduflag1);
			/**
			 * 由于华安系统返回报文"不计免赔"归为一类险种，需过滤掉,030119为华安系统 不计免赔险种代码
			 */
			if(!insItemDeduflag0.getKindCode().equals("030119")){
				String kindCodeDeduflag0 = (String) HuaanConfig.getrKindCodeMap().get(insItemDeduflag0.getKindCode());//险别代码转换
				insItemDeduflag0.setKindCode(kindCodeDeduflag0);
				insItemDeduflag0.setKindName(CarInsuranceConstant.getCarInsuranceTypeMap().get(kindCodeDeduflag0));//险别名称
				insItems.add(insItemDeduflag0);
				//如果购买了不计免赔
				if(insItemDeduflag1.getDeductibleFlag()==1){
					String kindCodeDeduflag1 = "D"+kindCodeDeduflag0;
					insItemDeduflag1.setKindCode(kindCodeDeduflag1);
					insItemDeduflag1.setKindName(CarInsuranceConstant.getCarInsuranceTypeMap().get(kindCodeDeduflag1));
					insItems.add(insItemDeduflag1);
				}
			}
		}
    	return insItems;
    }
    /**
     * @Description: TODO(封装商业险明细--计免赔和不计免赔)
     * @param objVo 报文
     * @return 商业险明细
     * @author yejie.huang
     * @date 2016年10月26日 下午3:01:05
     */
    public static CarQuoteInsItemVo getSyItem(DataObjVo objVo,CarQuoteInsItemVo insItemDeduflag0,CarQuoteInsItemVo insItemDeduflag1){
    	
    	//计免赔
    	insItemDeduflag0.setDeductibleFlag(0);//计免赔标志 
		insItemDeduflag0.setQuoteNo("");// 可空
		insItemDeduflag0.setCategory(1);//商业险
		insItemDeduflag0.setBenchmarkPremium("");// 标准保费
		insItemDeduflag0.setDiscount("");// 折扣率
		insItemDeduflag0.setCommission("");//合作方推广费
		
		//不计免赔
		insItemDeduflag1.setInsuredAmount("");
		insItemDeduflag1.setQuoteNo("");// 可空
		insItemDeduflag1.setCategory(1);//商业险
		insItemDeduflag1.setBenchmarkPremium("");// 标准保费
		insItemDeduflag1.setDiscount("");// 折扣率
		insItemDeduflag1.setCommission("");//合作方推广费
		
		for(AttributeVo attrVo :objVo.getAttributeVoList()){
			if(attrVo.getName().equals("Cvrg.CCvrgNo")){//险别代码
				insItemDeduflag0.setKindCode(attrVo.getNewValue());
				insItemDeduflag1.setKindCode(attrVo.getNewValue());
			}else if(attrVo.getName().equals("Cvrg.CDductMrk")){//不计免赔标志
				insItemDeduflag1.setDeductibleFlag(attrVo.getNewValue().equals("")?0:Integer.parseInt(attrVo.getNewValue()));
			}else if(attrVo.getName().equals("Cvrg.NNormalFreepay")){//不计免赔保费
				insItemDeduflag1.setPremium(attrVo.getNewValue());
			}else if(attrVo.getName().equals("Cvrg.NNormalPrm")){//折后保费(计免赔)
				insItemDeduflag0.setPremium(attrVo.getNewValue());
			}else if(attrVo.getName().equals("Cvrg.NAmt")){//保额
				insItemDeduflag0.setInsuredAmount(attrVo.getNewValue());
			}else if(attrVo.getName().equals("Cvrg.CYl4")){//商业第三险，保额
			   String value =com.ebtins.open.common.util.StringUtil.ObjectToString(HuaanConfig.getrCvrgCyMap().get(attrVo.getNewValue()));
			   if(!"".equals(value)){
				   insItemDeduflag0.setInsuredAmount(value);
			   }
			}else if(attrVo.getName().equals("Cvrg.CYl9")){//车身划痕损失险，保额
			   String value =com.ebtins.open.common.util.StringUtil.ObjectToString(HuaanConfig.getrCvrgCy19Map().get(attrVo.getNewValue()));
			   if(!"".equals(value)){
				   insItemDeduflag0.setInsuredAmount(value);
				}
			}
			
		}
		return insItemDeduflag0;
    }
    /**
     * @Description: TODO(封装交强险明细)
     * @param nameValues 报文key-value
     * @return 交强险明细
     * @author yejie.huang
     * @date 2016年10月23日 上午11:01:28
     */
    public static CarQuoteInsItemVo getJqItem(Map<String,String> nameValues){
    	CarQuoteInsItemVo insItem = new CarQuoteInsItemVo();
    	insItem.setKindCode("10");
    	insItem.setCategory(0);
    	//insItem.setKindName((String) HuaanConfig.getKindNameMap().get("10"));
    	insItem.setKindName(CarInsuranceConstant.getCarInsuranceTypeMap().get("10"));//险别名称
    	insItem.setInsuredAmount(nameValues.get("JQ_Base.NAmt"));
    	insItem.setPremium(nameValues.get("JQ_Base.NPrm"));
    	return insItem;
    }
    
	public static String getNewDwData(CarQuoteReq req,CarQuoteRes res,String originalDwData,Map<String,Object> postParams){
		
		/*postParams.put("Base.COrigType","0");//原方案续保
		postParams.put("JQ_Base.CRenewMrk","0");//续保标志(交)
		postParams.put("Base.CProdNo","0380");//
		postParams.put("JQ_Base.NAmt","122000.00");//
		postParams.put("JQ_Base.NPrm","0.00");//
		postParams.put("JQ_Base.NPrmTax","0.00");//
		postParams.put("JQ_Base.NTax","0.00");//
		postParams.put("SY_Base.NAmt","0");//
		postParams.put("SY_Base.NPrm","0");//
		postParams.put("SY_Base.NPrmTax","0.00");//
		postParams.put("SY_Base.NTax","0.00");//
		postParams.put("Base.CAmtCur","01");//保额币种
		postParams.put("Base.CPrmCur","01");//保费币种
		postParams.put("Base.CReqType","C");//" ignoreChange=
		postParams.put("Base.CCommonFlag","0");//
		postParams.put("Base.CPlyImageid","0");//保单临时ID
		postParams.put("Base.CEdrImageid","0");//批单临时ID
		postParams.put("Base.CScoverageFlg","1");//是否单独承保车险标志
		postParams.put("Base.CDptCde","01980003");//机构部门
		postParams.put("Base.CBsnsTyp","19002");//业务来源
		postParams.put("Base.CBrkrCde","0101062050");//代理人
		postParams.put("Base.CSlsCde","101154139");//业务员
		postParams.put("JQ_Base.NCommRate","0.04");//手续费比例(交)
		postParams.put("SY_Base.NCommRate","0.4982");//手续费比例(商)
		postParams.put("Base.CLicense","203136000000800");//中介机构代码
		postParams.put("JQ_Base.NPerformance","0.00");//绩效比例(交)
		postParams.put("SY_Base.NPerformance","0.00");//绩效比例(商)
		postParams.put("Base.CAgtAgrNo","B01201600012_0");//合作协议
		postParams.put("Base.CSalegrpCde","0198000301");//销售团队
		postParams.put("Base.CTrueAgtCde","0101062050");//协作人
		postParams.put("Base.CJsFlag","0");//交商同保
		postParams.put("Base.CBsnsSubtyp","199001");//业务类型
		postParams.put("Base.OprtLvl","198001");//业务等级
		postParams.put("Base.CFinTyp","0");//缴费方式
		postParams.put("Base.CWorkDpt","377001");//单位性质
		postParams.put("Base.CCusTyp","927002");//客户类型
		postParams.put("Base.CVipMrk","0");//标志
		postParams.put("Base.CInviteTitle","699003");//招投标业务
		postParams.put("Base.BuisType","925006");//业务线
		postParams.put("Base.CDisptSttlCde","007001");//争议处理
		postParams.put("Base.CAgriMrk","0");//是否涉农
		postParams.put("JQ_Base.IsImage","0");//补传标志(交)
		postParams.put("SY_Base.IsImage","0");//补传标志(商)
		postParams.put("Base.XqfxLvl","928001");//洗钱风险等级
		postParams.put("Base.CKpType","02");//开票种类
		postParams.put("Base.COprTyp","0");//保单生成
		postParams.put("Base.IsNofee","0");//是否不见费
		postParams.put("Base.CFeeFlag","1");//见费出单标志
		
		postParams.put("Vhlowner.CUnitAttrib","377001");//单位性质
		postParams.put("Vhlowner.COwnerCls","376001");//车主类别
		
		postParams.put("Vhl.CPlateColor","1");//车牌颜色
		postParams.put("Vhl.CModelCde","GTM7251GB");//
		postParams.put("Vhl.CModelNme","丰田GTM7251GB轿车");//()
		postParams.put("Vhl.CCarAtr","");//经营属性
		postParams.put("Vhl.CIsTipperFlag","0");//是否自卸车
		postParams.put("Vhl.CIndustryModelCode","BGQGKNUD0006");//行业车型编码
		postParams.put("Vhl.CFleetMrk","0");//车队标志
		postParams.put("Vhl.CLoanVehicleFlag","0");//车贷投保多年
		postParams.put("Vhl.CAmtType","943001");//保额确定类型
		postParams.put("Vhl.CInqType","645001");//折旧率
		postParams.put("Vhl.NDespRate","386001");//(
		postParams.put("Vhl.CInspectorCde","305005002");//验车情况
		postParams.put("Vhl.CInspectorNme","101001222");//验车人
		postParams.put("Vhl.CInspectTm","2016-10-08");//验车时间
		postParams.put("Vhl.CCheckResult","305012006");//验车结果
		postParams.put("Vhl.CInspectRec","的");//验车记录
		postParams.put("Vhl.CCarYear","2011-12");//上市年份
		postParams.put("Vhl.CNewVhlFlag","0");//新旧车标志
		postParams.put("Vhl.CNewEnergyFlag","0");//新能源车辆标志
		postParams.put("Vhl.CIfhkcarFlag","0");//是否粤港两地车
		postParams.put("Vhl.CRemark","手自一体 豪华版 国Ⅳ");//备注
		postParams.put("Vhl.NVhlTonage","1490");//整备质量
		postParams.put("Vhl.NNodamageYears","0");//跨省首年投保未出险证明年数
		postParams.put("Vhl.CFrmNoUnusualMrk","0");//车架号异常标志
		postParams.put("Vhl.CVhlQueryFla","1");//车辆查询标志
		postParams.put("Vhl.CFamilyKind","");//
		
		postParams.put("Insured.NSeqNo","1");//客户编号
		postParams.put("Insured.CInsuredCde","032814984");//被保险人编码
		postParams.put("Insured.CVhlInsuredRel","378001");//类别
		
		postParams.put("Applicant.CAppCde","032814984");//投保人代码
		postParams.put("Applicant.CTfiAppTyp","378001");//类别
		postParams.put("Applicant.CCountry","193001");//国籍
*/		
		//--------------CarOrderRelationInfoVo--投保人信息-----------
		//orderId
		//relationType 关系人类型 1-投保人;2-被保人;3-收件人
		postParams.put("Applicant.CAppNme",req.getCarInsurerInfo().getName());//姓名
		//sex
		//age
		//birthday
		String insureIdType =com.ebtins.open.common.util.StringUtil.ObjectToString(HuaanConfig.getIdCardMap().get(req.getCarInsurerInfo().getIdType()));
		if("".equals(insureIdType)){
			res.getHeader().setResCode(Constants.FAIL);
			res.getHeader().setResMsg(req.getCarInsurerInfo().getIdType()+":投保人证件类型不能识别!");
		}
		postParams.put("Applicant.CCertfCls",insureIdType);//证件类型 --idType
		postParams.put("Applicant.CCertfCde",req.getCarInsurerInfo().getIdNo());//证件号码--idNo
		postParams.put("Applicant.CTel",req.getCarInsurerInfo().getTelePhone());//电话 telePhone/mobilePhone
		//email
		postParams.put("Applicant.CClntAddr",req.getCarInsurerInfo().getAddress());//地址--address
		//postCode
		
		//--------------CarOrderRelationInfoVo--被保人信息------------
		//orderId
		//relationType 关系人类型 1-投保人;2-被保人;3-收件人
		String assuredIdType =com.ebtins.open.common.util.StringUtil.ObjectToString(HuaanConfig.getIdCardMap().get(req.getCarAssuredInfo().getIdType()));
		if("".equals(assuredIdType)){
			res.getHeader().setResCode(Constants.FAIL);
			res.getHeader().setResMsg(req.getCarAssuredInfo().getIdType()+":被保人证件类型不能识别!");
		}
		postParams.put("Insured.CInsuredNme",req.getCarAssuredInfo().getName());//姓名
		postParams.put("Insured.CSex",HuaanUtil.getGenderByIdCard(req.getCarAssuredInfo().getIdNo(),req.getCarAssuredInfo().getIdType()));//性别
		postParams.put("Insured.NAge",req.getCarAssuredInfo().getAge());//年龄
		//birthday
		postParams.put("Insured.CCertfCls",assuredIdType);//证件类型
		postParams.put("Insured.CCertfCde",req.getCarAssuredInfo().getIdNo());//证件号码
		postParams.put("Insured.CTel",req.getCarAssuredInfo().getTelePhone());//电话---telePhone/mobilePhone
		//email
		postParams.put("Insured.CClntAddr",req.getCarAssuredInfo().getAddress());//地址
		//postCode
		
		//影响交强结果参数
		/*postParams.put("Timmer.JTotalDays","365");//共   --不影响交强计算结果
		postParams.put("Timmer.JNRatioCoef","1.0");//短期费率系数
		
		postParams.put("Timmer.BTotalDays","365");//共
		postParams.put("Timmer.BNRatioCoef","1.0");//短期费率系数
		postParams.put("Pay.NTms","1");//
		postParams.put("JQ_Pay.NPayablePrm","0");//
		postParams.put("SY_Pay.NPayablePrm","0");//
*/		
		
		//----------------------CarModelInfoVo 车型查询-车型信息---------------------
		postParams.put("Vhl.CBrandId",req.getCarModelInfo().getModelCode());// modelCode车型代码/厂牌车型
		//brandName 车型名称(品牌型号)
		//factory 生产厂家
		//carBrand 品牌 
		//carYear 年款
		//familyName 车系名称
		//cIModelClass 交强险车型分类
		postParams.put("Vhl.NNewPurchaseValue",req.getCarModelInfo().getPurchasePrice());//新车购置价--purchasePrice
		postParams.put("Vhl.NActualValue",req.getCarModelInfo().getPurchasePrice());//实际价值
		//purchasePriceNotTax  新车购置价(不含税)
		//analogyModelPrice  类比车型价格(含税)
		//analogyModelPriceNotTax 类比车型价格(不含税)
		//riskFlag 车型风险类型
		//tonCount 吨位数
		postParams.put("Vhl.NSeatNum",req.getCarModelInfo().getSeatCount());//核定载客数--seatCount 座位数
		postParams.put("Vhl.NDisplacement",req.getCarModelInfo().getExhaustScale());//排量----exhaustScale 排气量
		//fullWeight 整备质量
		postParams.put("Vhl.CProdPlace",req.getCarLicenseInfo().getCountryNature());//车辆产地--carStyle国别性质：01-国产、02-进口、03-合资
		//postParams.put("Vhl.CYl4",HuaanUtil.getCYl4(req.getCarLicenseInfo().getVin()));//玻璃类型  根据 车辆产地和车架号 
		postParams.put("Vhl.CYl4","303011001");
		//transmissionType 变速箱类型: 如自动档、手动档
		//configurationLevel 配置版本级别: 如经济型、豪华型
		//carTypeCode  行驶证车辆类型
		String carType =com.ebtins.open.common.util.StringUtil.ObjectToString(HuaanConfig.getCarVehicleTypeMap().get(req.getCarModelInfo().getCarVehicleType()));
		if("".equals(carType)){
			res.getHeader().setResCode(Constants.FAIL);
			res.getHeader().setResMsg(req.getCarModelInfo().getCarVehicleType()+":车辆种类不能识别!");
		}
		postParams.put("Vhl.CVhlSubTyp",carType);//车辆种类----carVehicleType 车辆种类:1为客车，2为货车，3为特种车
		String carTypeCode =com.ebtins.open.common.util.StringUtil.ObjectToString(HuaanConfig.getCarTypeCode().get(req.getCarModelInfo().getCarTypeCode()));
		if("".equals(carTypeCode)){
			res.getHeader().setResCode(Constants.FAIL);
			res.getHeader().setResMsg(req.getCarModelInfo().getCarTypeCode()+":车辆类型代码不能识别!");
		}
		//postParams.put("Vhl.CVhlTyp",carTypeCode);//车辆类型---carVehicleTypeCode 车辆类型  6座以下 6-10座，6座以上
		postParams.put("Vhl.CVhlTyp",344001);
		//carVehicleTypeSubcode 车辆种类子类型（特种车独有）
		//countryNature 国别性质：01-国产、02-进口、03-合资
		//modelId 内部车型编号，数据库主键ID
		//externalId 第三方车型编号，如乐宝吧车型ID
		
		
		//-----------CarQuoteReq---------------
		//orderId 车险订单单号（内部用）
	    //quoteNo 车险报价单号（内部用
		//quoteNos 车险报价单号数组（报价重试接口使用）
		//merchantId 商户编号
		//insurerId 保险公司编号
		//insurerIds 保险公司编号数组(网聚内部用)
		//companyType 保险公司类型
		//companyTypes 保险公司类型数组
		//merchantOrderId 合作方订单编号, 必需是唯一编号
		//licenseFlag 牌照标志，1-已上牌，0-新车未上牌，默认为1
		postParams.put("SY_Base.CRenewMrk","0");//续保标志(商) --是否续保isRenewal： 1是，0否，默认为0
		postParams.put("Timmer.BTInsrncBgnTm",req.getStartDateBI());//保险起止期--商业险起保日期
		postParams.put("Timmer.BTInsrncEndTm",req.getEndDateBI());//
		postParams.put("Timmer.JTInsrncBgnTm",req.getStartDateCI());//保险起止期--交强险起保日期
		postParams.put("Timmer.JTInsrncEndTm",req.getEndDateCI());//
		
		//关键日期参数  不可缺
		String currentTime = TimeUtil.toString(new Date());
		postParams.put("Timmer.TIssueTm",currentTime);//签单日期
		postParams.put("Timmer.TAppTm",currentTime);//投保日期  
		postParams.put("Timmer.TOprTm",currentTime);//录单日期
		//runArea 行驶区域（城市），如深圳
		postParams.put("SY_PrmCoef.CRunArea",req.getRunAreaCode());//行驶区域--行驶区域代码
		//insureCity 投保区域（城市）
		
		//-------车主基本信息 CarOwnerInfoVo---------
		postParams.put("Vhlowner.COwnerNme",req.getCarOwnerInfo().getCarOwner());//姓名
		postParams.put("Vhlowner.CTel",req.getCarOwnerInfo().getOwnerPhone());//电话
		String idType =com.ebtins.open.common.util.StringUtil.ObjectToString(HuaanConfig.getIdCardMap().get(req.getCarOwnerInfo().getOwnerIdType()));
		if("".equals(idType)){
			res.getHeader().setResCode(Constants.FAIL);
			res.getHeader().setResMsg(req.getCarOwnerInfo().getOwnerIdType()+":车主证件类型不能识别!");
		}
		postParams.put("Vhlowner.CCertfCls",idType);//证件类型  01-身份证， 驾驶证-02, 军人证-03，护照-04，临时身份证-05，港澳通行证-06，台湾通行证-07 21-组织机构代码 22-税务登记证  23-营业执照（三证合一）  24-其他证件
		postParams.put("Vhlowner.CCertfCde",req.getCarOwnerInfo().getOwnerIdentifyNumber());//证件号码
		postParams.put("Vhlowner.CClntAddress",req.getCarOwnerInfo().getOwnerAddr());//地址
		postParams.put("Vhlowner.CDrvSex",HuaanUtil.getGenderByIdCard(req.getCarOwnerInfo().getOwnerIdentifyNumber(),req.getCarOwnerInfo().getOwnerIdType()));//车主性别
		int age = HuaanUtil.getAgeByIdCard(req.getCarOwnerInfo().getOwnerIdType(),req.getCarOwnerInfo().getOwnerIdentifyNumber(),req.getCarOwnerInfo().getOwnerAge());
		if(0==age){
			res.getHeader().setResCode(Constants.FAIL);
			res.getHeader().setResMsg("车主证件类型非身份证需请输入年龄!");
		}
		postParams.put("Vhlowner.NDrvownerAge",age);//车主年龄
		//ownerBirthday 车主出生日期，日期格式为yyyy-mm-dd
		
		//-------行驶证及相关行驶信息 CarLicenseInfoVo------
		postParams.put("Vhl.CPlateNo",req.getCarLicenseInfo().getLicenseNo());//号牌号码
		postParams.put("Vhl.CEngNo",req.getCarLicenseInfo().getEngineNo());//发动机号
		postParams.put("Vhl.CFrmNo",req.getCarLicenseInfo().getVin());//车架号
		//brandName 车型名称(品牌型号)
		postParams.put("Vhl.CFstRegYm",req.getCarLicenseInfo().getEnrollDate());//?注册日期 --车辆初登日期
		postParams.put("Vhl.CDevice1Mrk",req.getCarLicenseInfo().getChgOwnerFlag());//?是否过户投保 --- chgOwnerFlag过户车标志,0-否；1-是，默认为0
		//chgOwnerDate 过户日期，默认为""
		//ownerNature 行驶证车主性质:01-个人;02-单位，默认为01
		String userNature =com.ebtins.open.common.util.StringUtil.ObjectToString(HuaanConfig.getUseNatureMap().get(String.valueOf(req.getCarLicenseInfo().getUseNature())));
		if("".equals(userNature)){
			res.getHeader().setResCode(Constants.FAIL);
			res.getHeader().setResMsg(req.getCarLicenseInfo().getUseNature()+":车辆使用性质不能识别!");
		}
		postParams.put("Vhl.CUsageCde",userNature);//使用性质--投保查询====>平台返回错误:示范条款保单承保时车辆使用性质不允许上传---useNature 车辆使用性质,1运营，2非运营，默认为2
		//车辆产地--国别性质：01-国产、02-进口、03-合资(车型信息有此属性)
		String customerType =com.ebtins.open.common.util.StringUtil.ObjectToString(HuaanConfig.getCustomerTypeMap().get(req.getCarLicenseInfo().getCustomerType()));
		if("".equals(customerType)){
			res.getHeader().setResCode(Constants.FAIL);
			res.getHeader().setResMsg(req.getCarLicenseInfo().getCustomerType()+":车辆所有人性质不能识别!");
		}
		postParams.put("Vhl.CUseAtr3",customerType);//所属性质---customerType 所有人性质:： 01-个人，02-机关，03-企业，默认为01
		postParams.put("Vhl.CPlateTyp",req.getCarLicenseInfo().getLicenseType());//号牌种类 --号牌种类：01为大型车，02为小型车，16为教练汽车，22为临时行驶车，默认为02
		postParams.put("Vhl.CEcdemicMrk",req.getCarLicenseInfo().getNonLocalFlag());//是否外地车 --- 外地车标志，1-外地车，0-本地车，默认为0
		//taxType 车船税交税类型  1.缴税   2.减税  3.免税   4.完税，默认为1
		//loanFlag 是否贷款车: 1，是 0，否，默认为0
		//rentFlag 是否租赁车: 1，是 0，否，默认为0
		
	/*	postParams.put("SY_PrmCoef.NTgtFld1","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld2","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld3","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld4","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld23","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld5","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld10","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld7","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld14","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld15","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld18","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld11","0.00");//减免保费
		postParams.put("SY_PrmCoef.CLossRatio","1.0");//系数
		postParams.put("SY_PrmCoef.CMtcMng","1.0");//车队管理系数
		postParams.put("SY_PrmCoef.NTgtFld25","1.0");//广州金交会专用系数
		postParams.put("SY_PrmCoef.NTgtFld19","1.00");//平均年行驶里程系数(北京)
		postParams.put("SY_PrmCoef.NTgtFld17","0");//停驶天数
		postParams.put("SY_PrmCoef.NTgtFld22","1.00");//交通违法记录系数(深圳)
		postParams.put("SY_PrmCoef.NTgtFld21","1.00");//赔款记录系数(深圳)
		postParams.put("SY_PrmCoef.NMulRdr","1.0");//多险别投保优惠系数
		postParams.put("SY_PrmCoef.NTgtFld13","1.00");//选用系数合计*****
		postParams.put("Timmer.COprNm","游嘉琦");//录单人
		postParams.put("Timmer.COprCde","101154139");//
*/		
		String scale =com.ebtins.open.common.util.StringUtil.nullToString(req.getCarModelInfo().getExhaustScale());
		String seat = com.ebtins.open.common.util.StringUtil.nullToString(req.getCarModelInfo().getSeatCount());
		postParams.put("VsTax.CTaxdptVhltyp",HuaanUtil.getVhltypeBySeat(seat,scale));//车船税车辆分类
		/**
		 * 判断是否购买交强险，0380_0360：商业险和交强险，0380：交强险
		 *//*
		for(CarQuoteInsItemVo insItem:req.getCarQuoteInsItemList()){
			if(insItem.getCategory()==0){
				postParams.put("Base.CProdNo","0380_0360");//
				break;
			}else{
				postParams.put("Base.CProdNo","0380");//
			}
		}*/
		postParams.put("Base.CProdNo",HuaanUtil.getProNo(req.getCarQuoteInsItemList()));//交强、商业险标识
		
		//------------------交强信息-------------------------------------
		postParams.put("Vhl.CPlateNo","粤BX13G1");
		postParams.put("Vhl.CFrmNo","LVGBF53K0EG104351");
		/*postParams.put("VsTax.CAbateMrk","922001");//减免税标志
		postParams.put("VsTax.CPaytaxTyp","0");//
		postParams.put("VsTax.CVhlCategory","K11");//车船税车辆类型
		postParams.put("VsTax.NOverdueAmt","0.0");//滞纳金(元)
		postParams.put("VsTax.NLastYear","0.0");//往年补缴金额
		postParams.put("VsTax.CEnegyKind","0");//能源种类
		postParams.put("VsTax.NAggTaxAmt","0");//税金合计
		postParams.put("CvrgJQ.CCvrgNo","0357");//交强险险别
*/		postParams.put("VsTax.CTaxpayerId",req.getCarOwnerInfo().getOwnerIdentifyNumber());
		postParams.put("VsTax.NCurbWt",req.getCarModelInfo().getFullWeight());//整备质量
		//-------------------------------------------------------
		
        String newString = HuaanUtil.replacedParam(originalDwData, postParams).replace("undefined", "");
        
        String cvrgDwObjstr = HuaanConfig.getDwData().get("cvrgDwObj").toString();
        String cvrg119 = HuaanConfig.getDwData().get("cvrg119").toString();
        //newString = replaceCrvg(newString,cvrgDwObjstr,cvrg119,req.getCarQuoteInsItemList());
        
        List<ReqDwData> dwDatas = JSON.parseArray(newString, ReqDwData.class);
        for(ReqDwData dw:dwDatas){
        	if(dw.getDwName().equals("prodDef.vhl.Cvrg_DW")){
        		dw.setDataObjVoList(replaceCrvg1(res,cvrgDwObjstr,cvrg119,req.getCarQuoteInsItemList()));
        	}
        }
        
        newString = JSON.toJSONString(dwDatas);
        
        return newString;
	}
	/**
	 * @Description: TODO(替换请求报文险种明细)
	 * @param cvrgDwObjstr 单项险种明细报文模板
	 * @param cvrg119 不计免赔报文模板
	 * @param insItems 请求险种明细
	 * @return 按报文格式组装的险种明细
	 * @author yejie.huang
	 * @date 2016年10月25日 上午11:26:13
	 */
	public static List<DataObjVo> replaceCrvg1(CarQuoteRes res,String cvrgDwObjstr,String cvrg119,List<CarQuoteInsItemVo> insItems){
		DataObjVo objVo119 = JSON.parseObject(cvrg119, DataObjVo.class);
		List<DataObjVo> objVos = new ArrayList<DataObjVo>();
		int indexNo=0;
		boolean flag=true;
		for(CarQuoteInsItemVo insItem : insItems){
			//险种验证
			HuaanVerifyQuoteReqUtil.verifyItem(insItem,res);
			//10交强险跳过
			if(insItem.getKindCode().equals("10"))
				continue;
			/**
			 * 当险种里有一项购买了不计免赔，添加不计免赔报文
			 */
			if(flag&&insItem.getDeductibleFlag()==1){
				objVos.add(objVo119);
				flag = false;
			}
			
			DataObjVo objVo = JSON.parseObject(cvrgDwObjstr, DataObjVo.class);
			indexNo+=1;
			objVo = setObjVoValue(res,objVo,insItem,indexNo);
			objVos.add(objVo);
		}
		return objVos;
	}
	/**
	 * @Description: TODO(设置DataObjVo objVo险种明细值)
	 * @param objVo 险种报文对象
	 * @param insItem 险种对象
	 * @param indexNo 自定编号
	 * @return 赋值后的objVo对象
	 * @author yejie.huang
	 * @date 2016年10月20日 下午3:16:12
	 */
	public static DataObjVo setObjVoValue(CarQuoteRes res,DataObjVo objVo,CarQuoteInsItemVo insItem,int indexNo){
		objVo.setIndex(indexNo);
		List<AttributeVo> attVos = objVo.getAttributeVoList();
		for(AttributeVo attvo :attVos){
			if(attvo.getName().equals("Cvrg.NSeqNo")){//序号
				attvo.setNewValue(String.valueOf(indexNo));
			}else if(attvo.getName().equals("Cvrg.CCvrgNo")){//险种代码
				String value =com.ebtins.open.common.util.StringUtil.ObjectToString(HuaanConfig.getKindCodeMap().get(insItem.getKindCode()));
				attvo.setNewValue(value);
			}else if(attvo.getName().equals("Cvrg.NAmt")){//险别保额/限额
				attvo.setNewValue(insItem.getInsuredAmount());
			}else if(attvo.getName().equals("Cvrg.CDductMrk")){//不计免赔特约险购买标志
				attvo.setNewValue(String.valueOf(insItem.getDeductibleFlag()));
			}else if(attvo.getName().equals("Cvrg.CYl4")){//商业第三责责任险保额
				String value =com.ebtins.open.common.util.StringUtil.ObjectToString(HuaanConfig.getCvrgCyMap().get(insItem.getInsuredAmount()));
				attvo.setNewValue(value);
			}else if(attvo.getName().equals("Cvrg.NYl3")){//座位数
			    String value =com.ebtins.open.common.util.StringUtil.nullToString(insItem.getSeatNum());
				attvo.setNewValue("".equals(value)?"1":value);
			}else if(attvo.getName().equals("Cvrg.CYl9")){//车身划痕损失险保额
				String value =com.ebtins.open.common.util.StringUtil.ObjectToString(HuaanConfig.getCvrgCy19Map().get(insItem.getInsuredAmount()));
				attvo.setNewValue(value);
			}else if(attvo.getName().equals("Cvrg.NRate")){//指定专修厂险 费率
				String value ="100";//暂时默认100
				attvo.setNewValue(value);
			}else if(attvo.getName().equals("Cvrg.NYl11")){//修理期间费用补偿险 最高  xx元/天
				String value ="100";//暂时默认100
				attvo.setNewValue(value);
			}else if(attvo.getName().equals("Cvrg.NYl4")){//修理期间费用补偿险  天
				String value ="10";//暂时默认10
				attvo.setNewValue(value);
			}
		}
		return objVo;
	}
	
}
