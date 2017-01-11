/**
 * 
 */
package hyj.quote;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.junit.Test;

import com.ebtins.dto.open.CarQuoteInsItemVo;

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
		 List<CarQuoteInsItemVo> list = new ArrayList<CarQuoteInsItemVo>();
		CarQuoteInsItemVo item1=new CarQuoteInsItemVo();
		item1.setKindCode("1"); 
		item1.setInsuredAmount("139356.8"); // 保额/限额
		item1.setDeductibleFlag(1); // 0-不计（无不计免赔）;1-计(有不计免赔)	
		item1.setCategory(0);
		list.add(item1);
		for(CarQuoteInsItemVo  item:list){
			if(item.getKindCode()!=null&&!"10".equals(item.getKindCode())&&item.getCategory()==0){
				System.out.println(222);
				 break;
			}
		}
		/*System.out.println(post1String);
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
	*/
	}

}
