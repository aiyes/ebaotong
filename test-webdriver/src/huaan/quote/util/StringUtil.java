/**
 * 
 */
package huaan.quote.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: StringUtil
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年9月26日 下午12:00:53
 *
 */

public class StringUtil {
	public static String urlDeCode(String encodeStr,String encode){
		String deString = "";
		try {
			 deString = URLDecoder.decode(encodeStr,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deString;
	}

	public static String urlEnCode(String encodeStr,String encode){
		String enString = "";
		try {
			enString = URLEncoder.encode(encodeStr,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enString;
	}
	
	public static Matcher createMatcher(String matchStr,String reg){
		Pattern p = Pattern.compile(reg);
	    Matcher m = p.matcher(matchStr);
	    return m;
	}
	public static String regString(String str,String reg,int groupNum){
		  String resultString="";
		  Matcher m = createMatcher(str,reg);
		  if(m.find()){
			  resultString = m.group(groupNum);
		  }
		 return resultString;
	}
	
	public static String regStrings(String str,String reg,int groupNum){
		  str = str.replaceAll("<input[^<]*hidden[^>]*>", "");
		  String resultString="";
		  Matcher m = createMatcher(str,reg);
		  int count =0;
		  while(m.find()){
			  //resultString = m.group(groupNum);
			  resultString =  m.group(1)+ m.group(6);
			  System.out.println(resultString.replaceAll("\\s", ""));
			  count+=1;
		  }
		  System.out.println("共："+count);
		 return resultString;
	}



}
