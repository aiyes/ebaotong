/**
 * 
 */
package hyj.renbao;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.ebtins.dto.open.CarRenewalRes;

import ebtins.smart.proxy.company.renbao.util.RenbaoUtil;

/**
 * @ClassName: Test1
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年11月7日 下午5:53:50
 *
 */

public class Test1 {
	
	@Test
	public void test11() {
		//String reg ="<p[^>]*>([^<]*)</p>";
		String reg = "(?<=<p  id='q'>).*(?=</p>)";
		String str = "<P  id='q'>    123    sdjfsd    jlsdfd</p>";
		Pattern p = Pattern.compile(reg);
	    Matcher m = p.matcher(str);
	    if(m.find()){
	    	str = m.group(0);
		  }
		System.out.println(str);
	}

	@Test
	public void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {
		Map<String,String> maps = new HashMap<String,String>();
		maps.put("startDateBI", "333d");
		maps.put("isRenewal", "6");
		CarRenewalRes res = new CarRenewalRes();
        Object obj = RenbaoUtil.setValueByClazz(res.getClass().getName(), maps);
        CarRenewalRes r = (CarRenewalRes)obj;
        System.out.println(r.getStartDateBI()+" "+r.getIsRenewal());
	}
	@Test
	public void testHtml() throws IOException{
		Document document = Jsoup.parse(new File("E:/testFile/sykind.txt"),"utf-8");
		//Elements eles = document.select("input[name~=prpCitemKindsTemp\\[\\d+\\]\\.kindCode]");
		Elements eles = document.select("input[name=prpCmain.sumPremium1]");
		for(Element el :eles){
			System.out.println(el);
			 /* String value = el.val();
			  System.out.println(el.attr("name"));
			  String attrName = el.attr("name");
			  String index = attrName.substring(attrName.indexOf("[")+1, attrName.indexOf("]"));
			  System.out.println(index)*/;
		  }
		
	}
	/* String reg = "<td[^>]*>(((?!<td)[\\s\\S])*?)</td>\\s*<td((?!<td)[\\s\\S])*?(<(input|select)[\\s\\S]*?name=\"([^\"]+)\"[\\s\\S]*?)</td>";
	//String reg = "<td[\\s\\S]*?<input[^>]*>";
	System.out.println("------正则匹配--------");
	StringUtil.regStrings(body, reg, 0);*/

}
