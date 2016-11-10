/**
 * 
 */
package hyj.renbao;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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

}
