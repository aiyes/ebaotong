/**
 * 
 */
package hyj.renbao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ebtins.dto.open.CarQuoteInsItemVo;
import com.ebtins.dto.open.CarQuoteReq;
import com.ebtins.dto.open.CarQuoteRes;
import com.ebtins.open.common.util.MathUtil;
import com.ebtins.open.common.util.StringUtil;
import com.ebtins.open.common.util.ValidatorUtil;

import ebtins.smart.proxy.company.renbao.dto.CiInsureDemand;
import ebtins.smart.proxy.company.renbao.dto.PrpCitemKind;
import ebtins.smart.proxy.company.renbao.dto.RenbaoQuoteContent;
import ebtins.smart.proxy.company.renbao.dto.QuoteSave.PrpCfixations;
import ebtins.smart.proxy.company.renbao.dto.QuoteSave.PrpCitemCars;
import ebtins.smart.proxy.company.renbao.dto.QuoteSave.PrpCprofitDetails;
import ebtins.smart.proxy.company.renbao.dto.QuoteSave.PrpCprofitFactors;
import ebtins.smart.proxy.company.renbao.dto.QuoteSave.PrpDdismantleDetails;
import ebtins.smart.proxy.company.renbao.dto.QuoteSave.PrpDpayForPolicies;
import ebtins.smart.proxy.company.renbao.dto.QuoteSave.QueryPayForContent;
import ebtins.smart.proxy.company.renbao.dto.QuoteSave.RefreshPlanContent;
import ebtins.smart.proxy.company.renbao.dto.QuoteSave.RefreshPlanData;
import ebtins.smart.proxy.company.renbao.util.RenbaoClientSSLUtil;
import ebtins.smart.proxy.company.renbao.util.RenbaoUtil;
import hyj.login.RenBaoLoginA;

public class QuoteSave {/*
  *//**
   *  执行4次post请求。
   *  1、queryPayFor.do请求;
   *  2、refreshPlanByTime.do请求;
   *  3、checkBeforeSave.do请求--校验
   *  4、insert.do请求。--保存
   *  第1次请求参数:保费计算请求参数+保费计算返回内容作参数
   *  第2次请求参数:第1次请求参数+第1次请求返回内容作参数,
   *  第3次请求参数:关系人信息、行驶证车辆、投保时间、保费，校验是否已有相同内容保存)
   *  第4次请求参数:第2次请求参数+第2次请求返回内容作参数
   *//*
  public Map<String,String> save(Map<String,String> params,Map<String,String> sumCiParams,Map<String,String> checkbfSaveParams,RenbaoQuoteContent resContent,CarQuoteRes res,String referer,String cookie,String randomProposalNo) throws Exception{
	   
	   Map<String,String> queryPayForParams = getQueryPayPamas(params,resContent,res);
	   
	   String checkTimeUrl = "http://10.134.130.208:8000/prpall/business/checkEngageTime.do?startDateBi=2016-10-30&startHourBi=0&startDateCi=2016-09-30&startHourCi=0&bizType=PROPOSAL";
	   String checkAgentUrl = "http://10.134.130.208:8000/prpall/agreement/checkAgentType.do";
	   String queryPayForUrl = "http://10.134.130.208:8000/prpall/business/queryPayFor.do?agreementNo=XY4400000001&riskCode=DAA&comCode=44030716&chgCostRate=1";
	   String refreshPlanUrl = "http://10.134.130.208:8000/prpall/business/refreshPlanByTimes.do";
	   String checkBfSaveUrl = "http://10.134.130.208:8000/prpall/business/checkBeforeSave.do";
	   String saveUrl = "http://10.134.130.208:8000/prpall/business/insert.do";
	   
	   String checkBody = RenBaoLoginA.get(checkTimeUrl, "GBK", cookie, referer, null);
	   System.out.println("checkBody-->"+checkBody);
	   String checkAgentBody = RenBaoLoginA.post(checkAgentUrl,checkAgentParams, "GBK", cookie, referer);
	   System.out.println("checkAgentBody-->"+checkAgentBody);
	  
	   String queryPayForBody = RenBaoLoginA.post(queryPayForUrl,queryPayForParams,"GBK",cookie,referer);
	   System.out.println("queryPayForBody-->"+queryPayForBody);
	   
	   setRefreshPlanPamas(queryPayForBody,queryPayForParams);//添加参数（参数来源于queryPayFor.do请求返回内容）
	   setTaxParams(queryPayForParams,res,resContent);//添加车船税参数
	   //String refreshBody = RenBaoLoginA.post(refreshPlanUrl,RefreshByTime.getRefreshByTimeParmas(""),"GBK",cookie,referer);
	   String refreshBody = RenBaoLoginA.post(refreshPlanUrl,queryPayForParams,"GBK",cookie,referer);
	   System.out.println("refreshBody-->"+refreshBody);
	  
	   String checkBfSaveBody = RenBaoLoginA.post(checkBfSaveUrl,checkbfSaveParams,"GBK",cookie,referer);
	   System.out.println("checkBfSaveBody-->"+checkBfSaveBody);
	   
	   setSaveParams(refreshBody,queryPayForParams);//添加缴费计划参数（参数来源于refreshPlanByTime.do请求返回内容）
	   queryPayForParams.putAll(sumCiParams);////添加商业险总保费、总净保费、总税额参数
	   queryPayForParams.put("prpCmain.riskCode", "DAA");
	   queryPayForParams.put("BIdemandNo",resContent.getData().get(0).getBiInsuredemandVoList().get(0).getCiInsureDemandDAA().getDemandNo());//商业险投保单查询码
	   //queryPayForParams.put("", value)
	   String saveBody = RenBaoLoginA.post(saveUrl,queryPayForParams,"GBK",cookie,referer);
	   System.out.println("saveBody-->"+saveBody);
	   
	  return queryPayForParams;
	   
  }
  public void setTaxParams(Map<String,String> queryPayForParams,CarQuoteRes res,RenbaoQuoteContent resContent){
	   queryPayForParams.put("prpCcarShipTax.taxPayerCode",queryPayForParams.get("prpCinsureds[3].insuredCode"));
	   queryPayForParams.put("prpCcarShipTax.taxPayerIdentNo",queryPayForParams.get("prpCinsureds[3].identifyNumber"));//暂定车主为纳税人
	   queryPayForParams.put("prpCcarShipTax.taxPayerNumber",queryPayForParams.get("prpCinsureds[3].identifyNumber"));
	   queryPayForParams.put("prpCcarShipTax.taxPayerName",queryPayForParams.get("prpCinsureds[3].insuredName"));
	   queryPayForParams.put("prpCcarShipTax.taxUnitAmount",resContent.getData().get(0).getCiInsureVOList().get(0).getCiInsureTax().getCiInsureAnnualTaxes().get(0).getAnnualTaxAmount());
	   queryPayForParams.put("prpCcarShipTax.thisPayTax",res.getCarQuoteInfo().getThisPayTax());
	   queryPayForParams.put("prpCcarShipTax.sumPayTax",res.getCarQuoteInfo().getSumPayTax());
	   queryPayForParams.put("prpCcarShipTax.taxType","1");
	   queryPayForParams.put("prpCcarShipTax.calculateMode","C2");
	   queryPayForParams.put("prpCcarShipTax.carKindCode","A01");
	   queryPayForParams.put("prpCcarShipTax.model","B11");
	   queryPayForParams.put("prpCcarShipTax.carLotEquQuality","1505");
	   queryPayForParams.put("prpCcarShipTax.id.itemNo","1");
	   queryPayForParams.put("prpCcarShipTax.taxPayerNature","3");//纳税人类型 3自然人
	   queryPayForParams.put("prpCcarShipTax.taxUnit","辆/年");
	   queryPayForParams.put("prpCcarShipTax.taxAbateType","1");
	   queryPayForParams.put("prpCcarShipTax.payStartDate","");//本次缴税起期
	   queryPayForParams.put("prpCcarShipTax.payEndDate","");//本次缴税止期
	   queryPayForParams.put("prpCcarShipTax.prePayTax","0");
	   queryPayForParams.put("prpCcarShipTax.delayPayTax","0");
	   queryPayForParams.put("prpCcarShipTax.isType02Lice","N");
	   queryPayForParams.put("prpCcarShipTax.finesTax","0.00");
	   queryPayForParams.put("prpCcarShipTax.isHangOnWhether","N");
	   queryPayForParams.put("prpCcarShipTax.identifyTypeTax","01");
	   queryPayForParams.put("prpCcarShipTax.carKindCodeTax","A01");//车辆类型代码
	   queryPayForParams.put("prpCcarShipTax.licenseTypeTax","02");//车辆号牌种类代码 02小型汽车号牌
  }
  public Map<String,String> getCheckAgentParams(){
	  Map<String,String> checkAgentParams = new HashMap<String,String>();
	   checkAgentParams.put("agentCode", "440321100061");
	   checkAgentParams.put("businessNature", "2");
	   checkAgentParams.put("validDate", "2016-11-29");
	   checkAgentParams.put("comCode", "44030716");
	   checkAgentParams.put("riskCode", "DAA,DZA");
	  return checkAgentParams;
  }
  public void setSaveParams(String refreshBody,Map<String,String> params) throws Exception{
	  RefreshPlanContent refreshPlanContent = JSON.parseObject(refreshBody,RefreshPlanContent.class);
	   List<RefreshPlanData> refreshData = refreshPlanContent.getData();
	   int refreshIndex =0;
	   for(RefreshPlanData refresh :refreshData){
		   RenbaoUtil.setMapValueByClazz(params,refresh, "prpCplanTemps["+refreshIndex+"].","add");
		   params.put("prpCplanTemps["+refreshIndex+"].currency", "CNY");
		   refreshIndex +=1;
	   }
  }
  //解决商业险缴费计划与商业险总保费不一致不一致问题
  public void setBiCiSumParams(Map<String,String> queryPayForParams,CarQuoteRes res){
	  queryPayForParams.put("sumAmountBI", res.getCarQuoteInfo().getSumInsured());
	  queryPayForParams.put("sumPremiumChgFlag","1");
	  queryPayForParams.put("prpCmain.sumPremium1",String.valueOf(res.getCarQuoteInfo().getSumPayAmount()));
	  queryPayForParams.put("sumPayTax1",res.getCarQuoteInfo().getSumPayTax());
	  queryPayForParams.put("sumAmountBI","");
	  queryPayForParams.put("sumAmountBI","");
	  queryPayForParams.put("sumAmountBI","");
	  
  }
  public void setRefreshPlanPamas(String queryPayForBody,Map<String,String> params) throws Exception{
	   QueryPayForContent queryPayForContent = JSON.parseObject(queryPayForBody,QueryPayForContent.class);
	   List<PrpDdismantleDetails>  prpDdismantleDetails = queryPayForContent.getData().get(0).getPrpDdismantleDetails();
	   int dismantleIndex =0;
	   System.out.println("queryPayForBody-->"+queryPayForBody);
	   System.out.println("prpDdismantleDetails-->"+JSON.toJSONString(prpDdismantleDetails));
	   for(PrpDdismantleDetails dismantle :prpDdismantleDetails){
		   RenbaoUtil.setMapValueByClazz(params,dismantle, "prpDdismantleDetails["+dismantleIndex+"].","add");
		   dismantleIndex +=1;
	   }
	   
	   List<PrpDpayForPolicies> prpDpayForPolicies = queryPayForContent.getData().get(0).getPrpDpayForPolicies();
	   for(int i=0,l=prpDpayForPolicies.size();i<l;i++){
		   params.put("prpCcommissionsTemp["+i+"].costType",prpDpayForPolicies.get(i).getCostType());
		   params.put("prpCcommissionsTemp["+i+"].riskCode",prpDpayForPolicies.get(i).getRiskCode());
		   params.put("prpCcommissionsTemp["+i+"].adjustFlag",prpDpayForPolicies.get(i).getAdjustFlag());
		   params.put("prpCcommissionsTemp["+i+"].costRate",prpDpayForPolicies.get(i).getCostRate());
		   params.put("prpCcommissionsTemp["+i+"].costRateUpper",prpDpayForPolicies.get(i).getCostRateUpper());
		   params.put("prpCcommissionsTemp["+i+"].agreementNo",prpDpayForPolicies.get(i).getId().getAgreementNo());
		   params.put("prpCcommissionsTemp["+i+"].configCode",prpDpayForPolicies.get(i).getId().getConfigCode());
		   params.put("prpCcommissionsTemp["+i+"].coinsRate","");
		   params.put("prpCcommissionsTemp["+i+"].costFee","");
		   params.put("prpCcommissionsTemp["+i+"].auditRate","");
		   params.put("prpCcommissionsTemp["+i+"].sumPremium","");
	   }
	  
  }
  public Map<String,String> getQueryPayPamas(Map<String,String> params,RenbaoQuoteContent resContent,CarQuoteRes res) throws Exception{
	  
	   PrpCfixations prpCfixations = resContent.getData().get(0).getBiInsuredemandVoList().get(0).getPrpCfixations().get(0);
	   PrpCfixations prpCfixationsCi = resContent.getData().get(0).getCiInsureVOList().get(0).getPrpCfixations().get(0);
	   PrpCitemCars prpCitemCars = resContent.getData().get(0).getBiInsuredemandVoList().get(0).getPrpCitemCars().get(0);
	   List<PrpCitemKind> prpCitemKindsBi = resContent.getData().get(0).getBiInsuredemandVoList().get(0).getPrpCitemKinds();
	   PrpCitemKind prpCitemKindsCi = resContent.getData().get(0).getCiInsureVOList().get(0).getPrpCitemKinds().get(0);
	   RenbaoUtil.setMapValueByClazz(params,prpCitemKindsCi, "prpCitemKindCI.","update");
	   RenbaoUtil.setMapValueByClazz(params,prpCfixations, "prpCfixationTemp.","add");
	   RenbaoUtil.setMapValueByClazz(params,prpCfixationsCi, "prpCfixationCITemp.","add");
	   RenbaoUtil.setMapValueByClazz(params,prpCitemCars, "prpCitemCar.","update");
	   String off3 = params.get("prpCitemCar.coefficient3");
	   double offNum = ValidatorUtil.isNumeric(off3)?MathUtil.getOneDecimal(Double.parseDouble(off3)):0;
	   params.put("prpCitemCar.coefficient3",String.valueOf(offNum));
	   CiInsureDemand ciInsureDemand = resContent.getData().get(0).getCiInsureVOList().get(0).getCiInsureDemand();
	   RenbaoUtil.setMapValueByClazz(params,ciInsureDemand, "ciInsureDemand.","update");
	   int profitIndex = 0;
	   for(PrpCitemKind item:prpCitemKindsBi){
		   String kindCode = item.getKindCode();
		   String itemPrefix = getPrefixbyCode(params,kindCode);
		   RenbaoUtil.setMapValueByClazz(params,item, itemPrefix,"update");
		   List<PrpCprofitDetails> prpCprofitDetails = item.getPrpCprofits().get(0).getPrpCprofitDetails();
		   for(PrpCprofitDetails profit:prpCprofitDetails){
			   RenbaoUtil.setMapValueByClazz(params,profit, "prpCprofitDetailsTemp["+profitIndex+"].","add");
			   String chooseFlatKey = "prpCprofitDetailsTemp["+profitIndex+"].chooseFlag";
			   params.put(chooseFlatKey,StringUtil.ObjectToString(params.get(chooseFlatKey)).equals("1")?"on":"");//prpCprofitDetailsTemp[1].chooseFlag=on转换为1
			   profitIndex = profitIndex +1;
		   }
	   }
	   List<PrpCprofitFactors> prpCprofitFactors = resContent.getData().get(0).getBiInsuredemandVoList().get(0).getPrpCprofitFactors();
	   int profitFactorIndex = 0;
	   for(PrpCprofitFactors profitFactor:prpCprofitFactors){//prpCprofitFactorsTemp[i].xxx
		   RenbaoUtil.setMapValueByClazz(params,profitFactor, "prpCprofitFactorsTemp["+profitFactorIndex+"].","add");
		   String chooseFlatKey = "prpCprofitFactorsTemp["+profitFactorIndex+"].chooseFlag";
		   params.put(chooseFlatKey,StringUtil.ObjectToString(params.get(chooseFlatKey)).equals("1")?"on":"");//prpCprofitFactorsTemp[1].chooseFlag=on转换为1
		   profitFactorIndex = profitFactorIndex +1;
	   }
	   //--特殊处理参数---
	   CarQuoteInsItemVo  ciItem = null;
	   for(CarQuoteInsItemVo item:res.getCarQuoteInfo().getCarQuoteInsItemList()){
		   if(item.getCategory()==0){
			   ciItem = item;
			   break;
		   }
	   }
	   params.put("prpCitemKindCI.premium", ciItem.getPremium());
	   params.put("prpCitemKindCI.unitAmount",ciItem.getInsuredAmount());
	   params.put("prpCitemKindCI.benchMarkPremium",ciItem.getBenchmarkPremium());
	  
	   return params;
 }
  
 public String getPrefixbyCode(Map<String,String> params,String kindCode){
	 String prefix = "";
	 for(String key:params.keySet()){
		 if(kindCode.equals(params.get(key))&&key.indexOf(".")>-1){
			 prefix = key.substring(0,key.indexOf(".")+1);
		 }
	 }
	 return prefix;
 }
*/}
