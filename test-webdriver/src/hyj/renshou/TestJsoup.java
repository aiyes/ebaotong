package hyj.renshou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ebtins.smart.proxy.util.ConfFileUtil;
import huaan.quote.util.StringUtil;


public class TestJsoup {
	/**
	 * 投保人/被保人/车主：
	 * 证件类型：prpDcustomerIdvIdentifyType0|prpDcustomerIdvIdentifyType1|CredentialCode
	 * 证件号码：prpDcustomerIdvIdentifyNumber0|prpDcustomerIdvIdentifyNumber1|CredentialNo
	 * 姓名：AppliName|InsuredName|CarOwner
	 * 地址：AppliAddress|InsuredAddress|MailingAddress
	 * 固定电话：AppliPhoneNumber|InsuredPhoneNumber
	 * 移动电话：InsuredMobile|InsuredMobile|CarOwnerPhoneNo
	 * 投保人代码：AppliCode|
	 * 性别：|InsuredSex
	 * 年龄：|InsuredAge
	 * 邮箱：AppliEmail|InsuredEmail
	 * 邮编：AppliPostCode|InsuredPostCode|CarOwnerCountryCode
	 * 
	 */
	public static void main(String[] args)throws Exception {
		String html = readTxtFile("C:/Users/Administrator/Desktop/renshou/html/renewal.html");
		//String html = new ReadHtml().getRenewal();
		Map<String,String> values = new HashMap<String,String>();
		List<Map<String,String>> items = new ArrayList< Map<String,String>>();
		StringBuilder relatePersons = new StringBuilder();
		relatePersons.append("\\s*fm\\.(");
		//投保人/被保人/车主
		relatePersons.append("prpDcustomerIdvIdentifyType0|prpDcustomerIdvIdentifyType1|CredentialCode");//证件类型
		relatePersons.append("|prpDcustomerIdvIdentifyNumber0|prpDcustomerIdvIdentifyNumber1|CredentialNo");//证件号码
		relatePersons.append("|AppliName|InsuredName|CarOwner");//姓名
		relatePersons.append("|AppliAddress|InsuredAddress|MailingAddress");//地址
		relatePersons.append("|AppliPhoneNumber|InsuredPhoneNumber");//固定电话
		relatePersons.append("|InsuredMobile|InsuredMobile|CarOwnerPhoneNo");//移动电话
		relatePersons.append("|AppliCode");//投保人代码
		relatePersons.append("|InsuredSex");//性别
		relatePersons.append("|InsuredAge");//年龄
		relatePersons.append("|AppliEmail|InsuredEmail");//邮箱
		relatePersons.append("|AppliPostCode|InsuredPostCode|CarOwnerCountryCode");//邮编
		relatePersons.append("|KindCode|KindName|ItemKindFlag5|Amount|Discount|BenchMarkPremium|Premium");
		relatePersons.append(")");
		relatePersons.append("(\\s*(\\[vSuffix\\]))?");
		relatePersons.append("\\.(value|checked)");
		relatePersons.append("\\s*=");
		relatePersons.append("\\s?('([^']+)'|true|false)");
		relatePersons.append(";");
		  Matcher matcher = StringUtil.createMatcher(html, relatePersons.toString());
		  Map<String,String> item = null;
		  while(matcher.find()){
			  String key = matcher.group(1)+com.ebtins.open.common.util.StringUtil.ObjectToString(matcher.group(3));
			  String value = matcher.group(5).replace("'", "");
			  if(key.indexOf("vSuffix")>-1){
				  if(item==null||item.containsKey(key)){
					  item = new HashMap<String,String>();
					  items.add(item);
				  }
				  item.put(key, value);
			  }else{
				  values.put(key, value);
			  }
			  System.out.println(key+"--"+value);
		  }
		System.out.println(values);
		System.out.println(items);
	}
	

public static String readTxtFile(String filePath){ 
	 String getString="";
   try { 
           String encoding="UTF-8"; 
           File file=new File(filePath); 
           if(file.isFile() && file.exists()){ //判断文件是否存在 
               InputStreamReader read = new InputStreamReader( 
               new FileInputStream(file),encoding);//考虑到编码格式 
               BufferedReader bufferedReader = new BufferedReader(read); 
               String lineTxt = null; 
               while((lineTxt = bufferedReader.readLine()) != null){ 
               	getString +=lineTxt;
               } 
               read.close(); 
   }else{ 
       System.out.println("找不到指定的文件"); 
   } 
   } catch (Exception e) { 
       System.out.println("读取文件内容出错"); 
       e.printStackTrace(); 
   } 
   return getString;
} 
 

}
