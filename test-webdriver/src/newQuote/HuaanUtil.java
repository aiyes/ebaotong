/**
 * 
 */
package newQuote;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ebtins.open.common.util.IDCardUtil;

/**
 * @ClassName: HuaanUtil
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年10月20日 上午11:36:31
 *
 */

public class HuaanUtil {
	
	/** 中国公民身份证号码最小长度。 */  
    public static final int CHINA_ID_MIN_LENGTH = 15;  
  
    /** 中国公民身份证号码最大长度。 */  
    public static final int CHINA_ID_MAX_LENGTH = 18;  
    
	/** 
     * 根据身份编号获取性别 
     *  
     * @param idCard 
     *            身份编号 
     * @return 性别(M-男，F-女，N-未知) 
     */  
    public static String getGenderByIdCard(String idCard) {  
        String sGender = "";  
        if (idCard.length() == CHINA_ID_MIN_LENGTH) {  
            idCard = IDCardUtil.conver15CardTo18(idCard);  
        }  
        String sCardNum = idCard.substring(16, 17);  
        if (Integer.parseInt(sCardNum) % 2 != 0) {  
            sGender = "106001"; //男 
        } else {  
            sGender = "106002"; //女
        }  
        return sGender;  
    }  
    /**
     * @Description: TODO(根据座位数和排气量得出车辆种类)
     * @param seat 座位数
     * @param scale 排气量
     * @return 车船税车辆种类
     * @author yejie.huang
     * @date 2016年10月20日 下午5:01:40
     */
    public static String getVhltypeBySeat(String seat,String scale){
    	String CTaxdptVhltyp = "";
    	if("".equals(scale)){
    		scale = "2";
    	}
    	int NSeatNum = Integer.parseInt(seat);
    	float NDisplacement = Float.parseFloat(scale);
    	if(NSeatNum <= 9 && NDisplacement <= 1)//核定座位数＜＝9且排量＜＝1时
		{
			CTaxdptVhltyp =  "921015";//1.0升（含）以下的乘用车
		}
		else if(NSeatNum <= 9 && NDisplacement > 1 && NDisplacement <= 1.6)//核定座位数＜＝9，且1＜排量＜＝1.6时
		{
			CTaxdptVhltyp =  "921016";//1.0升以上至1.6升(含)的乘用车
		}
		else if(NSeatNum <= 9 && NDisplacement > 1.6 && NDisplacement <= 2.0)//核定座位数＜＝9，且1.6＜排量＜＝2.0时
		{
			CTaxdptVhltyp =  "921017";//1.6升以上至2.0升(含)的乘用车
		}
		else if(NSeatNum <= 9 && NDisplacement > 2.0 && NDisplacement <= 2.5)//核定座位数＜＝9，且2.0＜排量＜＝2.5时
		{
			CTaxdptVhltyp =  "921018";//2.0升以上至2.5升(含)的乘用车
		}
		else if(NSeatNum <= 9 && NDisplacement > 2.5 && NDisplacement <= 3.0)//核定座位数＜＝9，且2.5＜排量＜＝3.0时
		{
			CTaxdptVhltyp =  "921019";//2.5升以上至3.0升(含)的乘用车
		}
		else if(NSeatNum <= 9 && NDisplacement > 3.0 && NDisplacement <= 4.0)//核定座位数＜＝9，且3.0＜排量＜＝4.0时
		{
			CTaxdptVhltyp =  "921020";//3.0升以上至4.0升(含)的乘用车
		}
		else if(NSeatNum <= 9 && NDisplacement > 4.0)//核定座位数＜＝9，且排量>4.时
		{
			CTaxdptVhltyp =  "921021";//核定座位数＜＝9，且排量>4.时
		}
    	
    	if(1==2){// #12410: 海南大型客车定义问题
			if(NSeatNum > 9 && NSeatNum <= 20)//9＜核定载客数＜20时
			{
				CTaxdptVhltyp =  "921002";//车船税车辆分类自动选"中型客车"
			}
			else if(NSeatNum > 20)//核定载客数＝＞20时,包括电车
			{
				CTaxdptVhltyp =  "921001";//车船税车辆分类自动选"大型客车"
			}
		}else{
			if(NSeatNum > 9 && NSeatNum < 20)//9＜核定载客数＜20时
			{
				CTaxdptVhltyp =  "921002";//车船税车辆分类自动选"中型客车"
			}
			else if(NSeatNum >= 20)//核定载客数＝＞20时,包括电车
			{
				CTaxdptVhltyp =  "921001";//车船税车辆分类自动选"大型客车"
			}
		}
    	return CTaxdptVhltyp;
    }
  
    /**
	 * @Description: TODO(Create the MatcherObject)
	 * @param matchStr--The strings to be matched
	 * @param reg--The expression to be compiled
	 * @return Matcher Object
	 * @author yejie.huang
	 * @date 2016年9月29日 下午2:50:26
	 */
	public static Matcher createMatcher(String matchStr, String reg) {
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(matchStr);
		return m;
	}
    /**
     * @Description: TODO(Replace the str by reg,return the matched group)
     * @param groupNum--the index of a capturing group in this matcher's pattern
     * @return the matched group str
     * @author yejie.huang
     * @date 2016年9月29日 下午2:53:19
     */
	public static String regString(String str, String reg, int groupNum) {
		String resultString = "";
		Matcher m = createMatcher(str, reg);
		if (m.find()) {
			resultString = m.group(groupNum);
		}
		return resultString;
	}
    

}
