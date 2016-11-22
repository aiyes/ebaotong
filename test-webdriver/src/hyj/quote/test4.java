/**
 * 
 */
package hyj.quote;

import static org.junit.Assert.*;

import java.util.regex.Matcher;

import org.junit.Test;

import ebtins.smart.proxy.company.huaan.util.HuaanConfig;
import ebtins.smart.proxy.company.huaan.util.HuaanUtil;

/**
 * @ClassName: test4
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年10月25日 下午5:38:16
 *
 */

public class test4 {
	
	String post1String = HuaanConfig.getDwData().get("dw1Data").toString();
	
	@Test
	public void test() {
		System.out.println(post1String);
			  String reg = "\\{[^\\{]*newValue[^\\}]*\\}";
			  String reg1 = "name:'([^']+)'";
			  Matcher m = HuaanUtil.createMatcher(post1String,reg);
			  while(m.find()){
				  String oldParamObject = m.group(0);
				  String name = HuaanUtil.regString(oldParamObject,reg1,1);
				  String newParamValue = "{name:'"+name+"',newValue:'',bakValue:'',value:''}";
				  post1String = post1String.replace(oldParamObject, newParamValue);
				  //System.out.println(oldParamObject);
				  //System.out.println(newParamValue);
			  }
			  System.out.println(post1String);		  
	
	}

}
