/** 
 * 
 */
package hyj.readTxt;

import java.util.MissingFormatArgumentException;

/**
 * @ClassName: tt
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年9月25日 下午6:31:32
 *
 */

public class tt {
	public static void main(String[] args) {
		String html="55522adfdfdfa2222asdfsdf";
		String object = "2222";
		String text = "";
		System.out.println(beforSubString(text,object,html));
		
	}
	public static String beforSubString(String text,String object,String html){
		String result = "";
		if(text==null||text==""){
			System.out.println("---");
			//System.out.println("begin"+beginIndex("2a",object,html));
			System.out.println("end"+html.indexOf(object));
			result = html.substring(beginIndex("2a",object,html), html.indexOf(object));
		}
		return result+object;
	}
	
    public static int  beginIndex(String str,String object,String html){
    	int ObjectIndex = html.indexOf(object);
    	int index  = ObjectIndex-1;
    	String subStr = html.substring(1,1);
    	System.out.println(subStr);
    	while(true){
    		if(!subStr.contains(str)){
    			index -=1;
    			subStr = html.substring(index,ObjectIndex);
    		}else{
    			return index;
    		}
    	}
    }

}
