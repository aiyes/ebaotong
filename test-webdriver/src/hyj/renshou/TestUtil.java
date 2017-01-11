/**
 * 
 */
package hyj.renshou;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.ebtins.dto.open.CarOrderRelationInfoVo;
import com.ebtins.dto.open.CarQuoteInfoVo;
import com.ebtins.dto.open.CarRenewalRes;

import huaan.quote.util.StringUtil;

/**
 * @ClassName: TestUtil
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年12月19日 下午2:01:16
 *
 */

public class TestUtil {

	@Test
	public void generateCode() throws Exception{
		String ss = "http://9.0.6.69:7001/prpall/common/dm/CarBlackGraylistCheck.jsp?oldOrNewCar=1&oncFlag=1&CarMarkNo=%D4%C1CWH273&licenseKindCode=02&LicenseKindName=%D0%A1%D0%CD%C6%FB%B3%B5%BA%C5%C5%C6&BIInsureSZ=true&CIINSUREJSV600=false&TrialToProposal=false&ssOrderNo=null&SessionComCode=44030000&ProtocolType=&ProtocolNo=&MotorCadeBusiness=&ProtocolCode=&CalType=&ContinueFlag=&BizNo=&checkPlatForm=true&ProtocolCodeTemp=&CustomerCode=&CustomerType=&IsUpdateMotorProfit=&isAutoAppliFlag=&GroupCode=&SameToApplicant=&SameToInsured=&BIZTYPE_Temp=PROPOSAL&PayTimes=&addCarType=&showCarKind=false&nowDate=2016-12-20&riskcodeG=0511&sellProKey=&insuProKey=&claProKey=";
		  String url3 = "http://9.0.6.69:7001/prpall/0511/tbcbpg/UIPrPoEn0511Input.jsp?EDITTYPE=DIFFCOPY_POLICY&BIZTYPE=PROPOSAL&MinusFlag=null&BizNo=805012015440307004621&strBizNoRen=805012015440307004621&strFlag=1&UnionModifyType=true&sellProKey=&insuProKey=&claProKey=";	
		  String referer2 = "http://9.0.6.69:7001/prpall/common/dm/CarBlackGraylistCheck.jsp?oldOrNewCar=1&oncFlag=1&CarMarkNo=%D4%C1CWH273&licenseKindCode=02&LicenseKindName=%D0%A1%D0%CD%C6%FB%B3%B5%BA%C5%C5%C6&BIInsureSZ=true&CIINSUREJSV600=false&TrialToProposal=false&ssOrderNo=null&SessionComCode=44030000&ProtocolType=&ProtocolNo=&MotorCadeBusiness=&ProtocolCode=&CalType=&ContinueFlag=&BizNo=&checkPlatForm=true&ProtocolCodeTemp=&CustomerCode=&CustomerType=&IsUpdateMotorProfit=&isAutoAppliFlag=&GroupCode=&SameToApplicant=&SameToInsured=&BIZTYPE_Temp=PROPOSAL&PayTimes=&addCarType=&showCarKind=false&nowDate=2016-12-20&riskcodeG=0511&sellProKey=&insuProKey=&claProKey=";
		  String readStr = TestJsoup.readTxtFile("C:/Users/Administrator/Desktop/renshou/caldata.txt");
		  //String newstr = StringUtil.urlDeCode(readStr, "gbk");
		String[] params = readStr.split("&");
		int count =0;
		for(String param:params){
			String[] pa = param.split("=");
			if(pa.length==1){
				System.out.println("queryPayForParams.put(\""+pa[0]+"\",\"\");");
				//System.out.println("\""+pa[0]+"\":\"\",");
			}else{
				System.out.println("queryPayForParams.put(\""+pa[0]+"\",\""+pa[1]+"\");");
				//System.out.println("\""+pa[0]+"\":\""+pa[1]+"\",");
			}
			count+=1;
		}
		System.out.println("共："+count);
	}
	@Test
	public void testEncode(){
		String readStr = TestJsoup.readTxtFile("C:/Users/Administrator/Desktop/renshou/caldata.txt");
		String refer = "http://9.0.6.69:7001/prpall/common/tb/UIProposalInput.jsp?OldEditType=NEW&CarLicenseNo=ç²¤B58952&CarLicenseKindCode=02&oldOrNewCar=1&frameNoTemp=null&engineNoTemp=null&strOutcarflag=0";
		String s = StringUtil.urlDeCode(refer, "GBK").replace("&", "\n");
		System.out.println(s);
	}
	@Test
	public void parseCalHtml(){
		  String readStr = TestJsoup.readTxtFile("C:/Users/Administrator/Desktop/renshou/calRes.html");
		  List<Map<String,String>> items = new ArrayList<Map<String,String>>();
		  Renewal re = new Renewal();
		  Map<String,String> values = re.getHtmlValues(readStr,items);
		  CarRenewalRes res= new CarRenewalRes();
		  CarQuoteInfoVo res1 = re.setCarQuoteInfoVo(values,items,res);
		 /* res.setCarInsurerInfo(setInsurePersion(values));
		  res.setCarAssuredInfo(setAssurePersion(values));
		  res.setCarModelInfo(setCarMode(values));
		  res.setCarLicenseInfo(setCarLicenseInfoVo(values));
		  res.setCarOwnerInfo(setCarOwrer(values));
		  res.setLastCarQuoteInfo(setCarQuoteInfoVo(values,items,res));*/
		  System.out.println("res-->"+JSON.toJSONString(res1));
	}

}
