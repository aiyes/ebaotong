/**
 * 
 */
package hyj.model;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: RegParsePostData
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年9月26日 上午9:16:04
 *
 */

public class RegParsePostData {

	/**
	 * @Description: TODO()
	 * @param args
	 * @date 2016年9月26日 上午9:16:04
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static String replacedParam(String originalString,Map<String,String> postParams){
		  String reg = "";
		  for(String paramName : postParams.keySet()){
			  reg = "(\\{)([^\\{]*)"+paramName+"([^\\}]*)(\\})";//匹配 {name: 'Applicant.CAppNme',newValue: '张三',bakValue: '',value: ''}
			  Pattern p = Pattern.compile(reg);
			  Matcher m = p.matcher(originalString);
			  if(m.find()){//匹配报文参数对象
				  String oldParamObject = m.group(0);//获得参数对象,格式  {name: 'Applicant.CAppNme',newValue: '张三',bakValue: '',value: ''}
				  String newParamValue = "newValue:'"+postParams.get(paramName)+"'";//前台传入的参数值,组装成格式  newValue: '新值'
				  String newParamObject = oldParamObject.replaceFirst("(newValue:\\s?')([^']+)(')",newParamValue);//替换原来参数 {name: 'Applicant.CAppNme',newValue: '新值',bakValue: '',value: ''}
				  originalString = originalString.replace(oldParamObject, newParamObject);
			  }
		  }
		  return originalString;
	}
	

}
