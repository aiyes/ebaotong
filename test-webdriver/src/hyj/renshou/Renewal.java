/**
 * 
 */
package hyj.renshou;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.ebtins.dto.open.CarLicenseInfoVo;
import com.ebtins.dto.open.CarModelInfoVo;
import com.ebtins.dto.open.CarOrderRelationInfoVo;
import com.ebtins.dto.open.CarOwnerInfoVo;
import com.ebtins.dto.open.CarQuoteInfoVo;
import com.ebtins.dto.open.CarQuoteInsItemVo;
import com.ebtins.dto.open.CarRenewalReq;
import com.ebtins.dto.open.CarRenewalRes;
import com.ebtins.open.common.constant.CarInsuranceConstant;
import com.ebtins.open.common.constant.CommonConstants;
import com.ebtins.open.common.util.ValidatorUtil;

import ebtins.smart.proxy.company.renbao.util.RenbaoUtil;
import ebtins.smart.proxy.company.renshou.util.RenshouUtil;
import ebtins.smart.proxy.util.Errors;
import huaan.quote.util.StringUtil;
import hyj.renshou.login.RenshouLogin;

/**
 * @ClassName: Renewal
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年11月7日 下午2:05:18
 *
 */

public class Renewal {
	final Log log=LogFactory.getLog(getClass());
	public static void main(String[] args) throws Exception {
		new Renewal().getRenewal(null);
	}
  public  CarRenewalRes getRenewal(CarRenewalReq req) throws Exception{
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	  CarRenewalRes res= new CarRenewalRes();
	  String cookie = RenshouLogin.login();
	  String url1 = "http://9.0.6.69:7001/prpall/common/dm/CarBlackGraylistCheck.jsp?oldOrNewCar=1&oncFlag=1&BIInsureSZ=true&CIINSUREJSV600=false&TrialToProposal=false&ssOrderNo=null&SessionComCode=44030000&ProtocolType=&ProtocolNo=&MotorCadeBusiness=&ProtocolCode=&CalType=&ContinueFlag=&BizNo=&checkPlatForm=true&ProtocolCodeTemp=&CustomerCode=&CustomerType=&IsUpdateMotorProfit=&isAutoAppliFlag=&GroupCode=&SameToApplicant=&SameToInsured=&BIZTYPE_Temp=PROPOSAL&PayTimes=&addCarType=&showCarKind=false&riskcodeG=0511&sellProKey=&insuProKey=&claProKey=";
      String referRenewalUrl = "http://9.0.6.69:7001/prpall/indiv/ci/tbcbpg/UIPrPoEnDemandCarMarkInput.jsp?TaskCode=prpall.a01003002006&usercode=412828199111111010&comcode=4403159003";
	  Map<String, String> params=new HashMap<String, String>();
	  String body="";
	  try {
		params.put("CarMarkNo", "粤BU027T");//粤B6E7G8 粤CWH273 粤BU027T
		params.put("LicenseKindName","小型汽车号牌");
		params.put("licenseKindCode", "02");//默认小型汽车号牌
		params.put("nowDate", sdf.format(new Date()));
		body = RenshouLogin.post(url1, params, "GBK", cookie, referRenewalUrl, null);
		//body = RenshouLogin.get(url1, "GBK", cookie, referRenewalUrl, null);//输入车牌号
		 System.out.println("body-->"+body);
	  } catch (Exception e) {
		e.printStackTrace();
		res.getHeader().setResCode("22000");
		res.getHeader().setResMsg("续保请求错误!");
		return res;
	  }
	  String url2 = "http://9.0.6.69:7001/prpall/common/dm/CarHistoryPolicyList.jsp";
	  String body2 = RenshouLogin.get(url2, "GBK", cookie, url1, null);//下一步
	  System.out.println("body2-->"+body2);
	  String bizNo = parseBody2(body2,res);
	  if(com.ebtins.open.common.util.StringUtil.ObjectToString(res.getHeader().getResCode()).equals(Errors.QRY_RENEWAL_ERROR)) return res;
	  String url3 = "http://9.0.6.69:7001/prpall/0511/tbcbpg/UIPrPoEn0511Input.jsp?EDITTYPE=DIFFCOPY_POLICY&BIZTYPE=PROPOSAL&MinusFlag=null&BizNo="+bizNo+"&strBizNoRen="+bizNo+"&strFlag=1&UnionModifyType=true&sellProKey=&insuProKey=&claProKey=";	
	  String body3 = RenshouLogin.get(url3, "GBK", cookie, url1, null);//数据复用
	  System.out.println("body-->"+body);
	 
	  System.out.println("body3-->"+body3);
	  List<Map<String,String>> items = new ArrayList<Map<String,String>>();
	  Map<String,String> values = getHtmlValues(body3,items);
	  res.setCarInsurerInfo(setInsurePersion(values));
	  res.setCarAssuredInfo(setAssurePersion(values));
	  res.setCarModelInfo(setCarMode(values));
	  res.setCarLicenseInfo(setCarLicenseInfoVo(values));
	  res.setCarOwnerInfo(setCarOwrer(values));
	  res.setLastCarQuoteInfo(setCarQuoteInfoVo(values,items,res));
	  System.out.println("res-->"+JSON.toJSONString(res));
	 return res;
  }
  //多条往年续保信息，取最近日期一条
  public String parseBody2(String html,CarRenewalRes res){
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	  String bizNo = "";
	  Date dateTime = null,tempDate = null;
	  Document doc = Jsoup.parse(html);
	  Elements trs = doc.getElementsByTag("tr");
	  Map<String,String> map = new HashMap<String,String>();
	  if(trs==null||trs.size()<2||doc.title().contains("500")){
		  res.getHeader().setResCode(Errors.QRY_RENEWAL_ERROR);
		  res.getHeader().setResMsg("续保列表信息为空");
		  return bizNo;
	  }
	  for(int i=1,l=trs.size();i<l;i++){
		  try {
			  System.out.println("tr-->"+trs.get(i));
			  Elements tds = trs.get(i).getElementsByTag("td");
			  tempDate = sdf.parse(tds.get(4).text());
			  if(tds.get(6).text().contains("数据复用")&&tds.get(5).text().equals("是")){
				  if("".equals(bizNo)||dateTime.before(tempDate)){
					  bizNo = tds.get(0).text().trim();
					  dateTime = tempDate;
				  }
			  }
		} catch (ParseException e) {
			log.error("error", e);
			res.getHeader().setResCode(Errors.QRY_RENEWAL_ERROR);
			res.getHeader().setResMsg("续保日期格式化出错yyyy-MM-dd");
			return bizNo;
		}
	  }
	  System.out.println("bizNo-->"+bizNo);
	  return bizNo;
  }
  //挖取html数据，由于数据在javascript中，目前只想到用正则匹配方法,运行效率可能会低
 public Map<String,String> getHtmlValues(String html,List<Map<String,String>> items){
	    Map<String,String> values = new HashMap<String,String>();
		StringBuilder reg = new StringBuilder();
		reg.append("fm\\.(");
		//投保人/被保人/车主
		reg.append("prpDcustomerIdvIdentifyType0|prpDcustomerIdvIdentifyType1|CredentialCode");//证件类型
		reg.append("|prpDcustomerIdvIdentifyNumber0|prpDcustomerIdvIdentifyNumber1|CredentialNo");//证件号码
		reg.append("|AppliName|InsuredName|CarOwner");//姓名
		reg.append("|AppliAddress|InsuredAddress|MailingAddress");//地址
		reg.append("|AppliMobile|InsuredMobile|CarOwnerPhoneNo");//移动电话
		reg.append("|AppliPhoneNumber|InsuredPhoneNumber");//固定电话
		reg.append("|AppliCode");//投保人代码
		reg.append("|InsuredSex");//性别
		reg.append("|InsuredAge");//年龄
		reg.append("|AppliEmail|InsuredEmail");//邮箱
		reg.append("|AppliPostCode|InsuredPostCode|CarOwnerCountryCode");//邮编
		//行驶证车辆信息
		reg.append("|LicenseNo|EngineNo|VINNo|BrandName|VehicleCode|EnrollDate");
		reg.append("|ChgOwnerFlag|TransferDate|VehicleOwnerNature|UseNatureCode|CountryNature");
		reg.append("|CarInsuredRelation|LicenseKindCode|OtherNature8");
		//车型信息
		reg.append("|VehicleBrand|SeatCount|CarKindCode|ExhaustScale|WholeWeight");
		reg.append("|VehicleCode|PurchasePrice|CarActualValue|TonCount|VehicleStyleDesc|LoanVehicleFlag");
		//交强险
		reg.append("|KindCodeCI|AmountCountCI|BenchMarkPremiumCountCI|PremiumDisCountCI|SumPremiumCI|ItemKind_StartDateCI|ItemKind_EndDateCI");
		//商业险别
		reg.append("|KindCode|KindName|ItemKindFlag5|Amount|Discount|BenchMarkPremium|Premium");
		
		reg.append(")");
		reg.append("(\\s*(\\[vSuffix\\]))?");
		reg.append("\\.(value|checked)");
		reg.append("\\s*=");
		reg.append("\\s?('([^']+)'|true|false)");//匹配 单引号内容或false ture 字符
		reg.append(";");
		  Matcher matcher = StringUtil.createMatcher(html, reg.toString());
		  Map<String,String> item = null;
		  while(matcher.find()){
			  String key = matcher.group(1)+com.ebtins.open.common.util.StringUtil.ObjectToString(matcher.group(3));
			  String value = matcher.group(5).replace("'", "");
			  if(key.indexOf("vSuffix")>-1){
				  if(item==null||item.containsKey(key)){
					  item = new HashMap<String,String>();
					  items.add(item);
				  }
				  item.put(key, value);
			  }else{
				  values.put(key, value);
			  }
		  }
		System.out.println("getHtmlValues-->"+values);
		return values;
 }
 public CarQuoteInfoVo setCarQuoteInfoVo(Map<String,String> values,List<Map<String,String>> itemList,CarRenewalRes res){
	 CarQuoteInfoVo info = new CarQuoteInfoVo();
	 List<CarQuoteInsItemVo> biItems = setKindItemBi(itemList,res);
	 biItems.add(setKindItemCi(values));
	 info.setCarQuoteInsItemList(biItems);
	 double sumCiPremium = 0.0;
	 String ciPremium = values.get("SumPremiumCI");
	 if(ciPremium!=null&&ValidatorUtil.isNumeric(ciPremium)){
		 sumCiPremium = Double.parseDouble(ciPremium);
	 }
	 info.setSumCiPremium(sumCiPremium);
	 info.setActualValue(values.get("CarActualValue"));
	 info.setCompanyType(CommonConstants.COM_TYPE_RENSHOU);
	 info.setInsurerName("中国人寿");
	 return info;
 }
 //交强险
 public CarQuoteInsItemVo setKindItemCi(Map<String,String> values){
	 CarQuoteInsItemVo item =  new CarQuoteInsItemVo();
	 item.setCategory(0);
	 item.setKindCode("10");
	 item.setKindName(CarInsuranceConstant.getCarInsuranceTypeMap().get("10"));
	 item.setBenchmarkPremium(values.get("BenchMarkPremiumCountCI"));
	 item.setDiscount(values.get("PremiumDisCountCI"));
	 item.setInsuredAmount(values.get("AmountCountCI"));
	 item.setPremium(values.get("SumPremiumCI"));
	 return item;
 }
 //商业险
 public List<CarQuoteInsItemVo> setKindItemBi(List<Map<String,String>> itemList,CarRenewalRes res){
	 System.out.println("itemList-->"+itemList);
	  List<CarQuoteInsItemVo> items = new ArrayList<CarQuoteInsItemVo>();
	  for(Map<String,String> map:itemList){
		  System.out.println("map-->"+map);
		  CarQuoteInsItemVo item =  new CarQuoteInsItemVo();
		  String kindCode = RenshouUtil.getKindCode(map.get("KindCode[vSuffix]"));
		  if(kindCode==null){
			  String msg = res.getHeader().getResMsg();
			  res.getHeader().setResCode(Errors.QRY_RENEWAL_ERROR);
			  res.getHeader().setResMsg(msg+map.get("KindName[vSuffix]")+map.get("KindCode[vSuffix]")+"险种代码不能识别;");
			  continue;
		  }
		  item.setCategory(1);
		  item.setKindCode(kindCode);
		  item.setKindName(CarInsuranceConstant.getCarInsuranceTypeMap().get(kindCode));
		  item.setInsuredAmount(map.get("Amount[vSuffix]"));
		  item.setBenchmarkPremium(map.get("BenchMarkPremium[vSuffix]"));
		  item.setDiscount(map.get("Discount[vSuffix]"));
		  item.setPremium(map.get("Premium[vSuffix]"));
		  int deductibleFlag = 0;
		  String deductible = map.get("ItemKindFlag5[vSuffix]");
		  if(deductible!=null&&deductible.equals("true")){
			  deductibleFlag = 1;
		  }
		  item.setDeductibleFlag(deductibleFlag);
		  items.add(item);
	  }
	  return items;
 }
//行驶证车辆信息
 public CarLicenseInfoVo setCarLicenseInfoVo(Map<String,String> values){
	  CarLicenseInfoVo info = new CarLicenseInfoVo();
	  info.setLicenseNo(values.get("LicenseNo"));
	  info.setEngineNo(values.get("EngineNo"));
	  info.setVin(values.get("VINNo"));
	  info.setBrandName(values.get("BrandName"));
	  info.setModelCode(values.get("VehicleCode"));
	  info.setEnrollDate(values.get("EnrollDate"));
	  info.setChgOwnerFlag(values.get("ChgOwnerFlag"));
	  info.setChgOwnerDate(values.get("TransferDate"));
	  info.setOwnerNature(values.get("VehicleOwnerNature"));//在车主信息取得
	  String userNature = values.get("UseNatureCode");
	  info.setUseNature(0);//待转换
	  info.setCountryNature(values.get("CountryNature"));
	  info.setCustomerType(values.get("CarInsuredRelation"));//在车主信息取得
	  info.setLicenseType(values.get("LicenseKindCode"));
	  String localFlag = values.get("OtherNature8");
	  info.setNonLocalFlag(0);//待转换
	  info.setTaxType(0);
	  info.setLoanFlag(0);
	  info.setRentFlag(0);
	  return info;
 }
//封装车型信息
	public CarModelInfoVo setCarMode(Map<String,String> map){
		CarModelInfoVo model = new CarModelInfoVo();
		//model.setAnalogyModelPrice(map.get("类比车型价格(含税)"));
		//model.setAnalogyModelPriceNotTax(map.get("类比车型价格"));
		model.setBrandName(map.get("BrandName"));//车型名称(品牌型号)  brandId-品牌型号
		//model.setcIModelClass(map.get("商业险名称"));//交强险车型分类
		model.setCarBrand(map.get("VehicleBrand"));//品牌
		model.setCarStyle("");//作废，留空
		model.setCarTypeCode(map.get(""));//行驶证车辆类型
		model.setCarVehicleTypeCode(RenbaoUtil.getCarVehicleTypeCodeBySeat(map.get("SeatCount")));//车辆种类类型 --A012六座以下客车,A022六座至十座以下客车车,
		model.setCarVehicleTypeSubcode("");//车辆种类子类型（特种车独有）
		model.setCarVehicleType(map.get("CarKindCode"));//车辆种类:1为客车，2为货车，3为特种车？
		//model.setCarYear(map.get("上市年份"));//年款
		model.setConfigurationLevel("");
		model.setExhaustScale(map.get("ExhaustScale"));//排气量
		model.setExternalId("");
		//model.setFactory(map.get("生产厂家"));//生产厂家--厂商代码？
		//model.setFamilyName(map.get("车型系列"));//车系名称--车组名称？
		model.setFullWeight(map.get("WholeWeight"));
		model.setModelCode(map.get("VehicleCode"));//车型代码
		model.setModelId(0);
		model.setPurchasePrice(map.get("PurchasePrice"));//新车购置价
		//model.setPurchasePriceNotTax(map.get("新车购置价"));
		//model.setRiskFlag(map.get("风险标识"));
		model.setTonCount(map.get("TonCount"));//吨位数 是不是 额定载质量？待确认
		model.setSeatCount(map.get("SeatCount"));//座位数
		//model.setTransmissionType(map.get("变速器类型"));
		model.setCountryNature(map.get("CountryNature"));//国别性质：01-国产、02-进口、03-合资 ？
		model.setDescription(map.get("VehicleStyleDesc"));
		System.out.println("model-->"+JSON.toJSONString(model));
		return model;
		
	}
//车主
public CarOwnerInfoVo setCarOwrer(Map<String,String> values){
	 CarOwnerInfoVo info = new CarOwnerInfoVo();
	 info.setOwnerIdType(values.get("CredentialCode"));
	 info.setOwnerIdentifyNumber(values.get("CredentialNo"));
	 info.setCarOwner(values.get("CarOwner"));
	 info.setOwnerAddr(values.get("MailingAddress"));
	 info.setOwnerPhone(values.get("CarOwnerPhoneNo"));
	 return info;
}
 //投保人
 public CarOrderRelationInfoVo setInsurePersion(Map<String,String> values){
	  CarOrderRelationInfoVo info = new CarOrderRelationInfoVo();
	  info.setRelationType(1);
	  info.setName(values.get("AppliName"));
	  //info.setSex(0);
	  //info.setAge(0);
	  //info.setBirthday("");
	  info.setIdType(values.get("prpDcustomerIdvIdentifyType0"));
	  info.setIdNo(values.get("prpDcustomerIdvIdentifyNumber0"));
	  info.setMobilePhone(values.get("AppliMobile"));
	  info.setTelePhone(values.get("AppliPhoneNumber"));
	  info.setEmail(values.get("AppliEmail"));
	  info.setAddress(values.get("AppliAddress"));
	  info.setPostCode(values.get("AppliPostCode"));
	  return info;
 }
 //被保人
 public CarOrderRelationInfoVo setAssurePersion(Map<String,String> values){
	  CarOrderRelationInfoVo info = new CarOrderRelationInfoVo();
	  info.setRelationType(2);
	  info.setName(values.get("InsuredName"));
	  //info.setSex(0);
	  //info.setAge();
	  //info.setBirthday("");
	  info.setIdType(values.get("prpDcustomerIdvIdentifyType1"));
	  info.setIdNo(values.get("prpDcustomerIdvIdentifyNumber1"));
	  info.setMobilePhone(values.get("InsuredMobile"));
	  info.setTelePhone(values.get("InsuredPhoneNumber"));
	  info.setEmail(values.get("InsuredEmail"));
	  info.setAddress(values.get("InsuredAddress"));
	  info.setPostCode(values.get("InsuredPostCode"));
	  return info;
}
 
 
  
  
  /**
   * @Description: TODO(封装上年车险保单报价的基本信息)
   * @param carQuoteInfo 初始对象
   * @param body 返回报文html
   * @param category 险种类型 1商业险，0交强险
   * @return CarQuoteInfoVo
   * @author yejie.huang
   * @date 2016年11月10日 上午11:21:47
   */
  public static CarQuoteInfoVo getCarQuoteInfo(CarQuoteInfoVo carQuoteInfo,String body,int category) throws Exception{
	  Map<String,String> relateMap = new HashMap<String,String>();
	  if(category==1){
		  relateMap.put("quoteNo", "");//报价单号
		  relateMap.put("orderId", "");//车险订单Id
		  relateMap.put("insurerId", "");//保险公司id
		  relateMap.put("insurerName", "_人保");//保险公司名称
		  relateMap.put("companyType", "_"+CommonConstants.COM_TYPE_RENBAO);//保险公司类型
		  relateMap.put("quoteStatus", "");//报价状态
		  relateMap.put("sumInsured", "");//总保额----人保没有直接返回，后面手工计算
		  relateMap.put("sumStdPrem", "");//标准保费----人保没有直接返回
		  relateMap.put("sumPayablePrem", "");//应交保费，不含车船税----人保没有直接返回
		  relateMap.put("sumPayAmount", "");//应付金额，应交保费 + 车船税总额
		  relateMap.put("discount", "prpCmain.discount");//折扣
		  relateMap.put("sumBiPremium", "prpCmain.sumPremium");//商业险应付保费
		  relateMap.put("actualValue", "");//车辆实际价值
		  relateMap.put("remark", "");//备注说明
	  }else if(category==0){
		  relateMap.put("sumCiPremium", "prpCitemKindCI.premium");//交强险应付保费  
		  relateMap.put("thisPayTax", "prpCcarShipTax.thisPayTax");//车船税当年应缴
		  relateMap.put("prePayTax", "prpCcarShipTax.prePayTax");//车船税往年补缴
		  relateMap.put("delayPayTax", "prpCcarShipTax.delayPayTax");//车船税滞纳金
		  relateMap.put("sumPayTax", "prpCcarShipTax.sumPayTax");//车船税总缴付税额
	  }
	  setValueByClazz(carQuoteInfo,body,relateMap);
	  return carQuoteInfo;
  }
  
  /**
   * @Description: TODO(封装交强险)
   * @param body 返回报文html
   * @return 交强险CarQuoteInsItemVo
   * @author yejie.huang
   * @date 2016年11月10日 上午11:21:47
   */
  public static CarQuoteInsItemVo getkindItemCI(String body) throws Exception{
	  Map<String,String> relateMap = new HashMap<String,String>();
	  relateMap.put("quoteDetailId", "");//车险报价险种明细编号
	  relateMap.put("quoteNo", "");//报价单号
	  relateMap.put("category", "_0");//险种类别: 0为交强险，1为商业险
	  relateMap.put("kindCode", "prpCitemKindCI.kindCode");//险种代码
	  relateMap.put("kindName", "prpCitemKindCI.kindName");//险种名称
	  relateMap.put("insuredAmount", "prpCitemKindCI.amount");//保额/限额
	  relateMap.put("deductibleFlag", "");//购买不计免赔特约险标志
	  relateMap.put("benchmarkPremium", "prpCitemKindCI.benchMarkPremium");//标准保费
	  relateMap.put("discount", "prpCitemKindCI.disCount");//折扣率
	  relateMap.put("commission", "");//合作方推广费
	  relateMap.put("premium", "prpCitemKindCI.premium");//应交保费
	  CarQuoteInsItemVo res = new CarQuoteInsItemVo();
	  setValueByClazz(res,body,relateMap);
	  return res; 
  }
  
  /**
   * @Description: TODO(封装商业险列表)
   * @param body 返回报文html
   * @return 商业险列表
   * @author yejie.huang
   * @date 2016年11月10日 上午11:21:47
   */
  public static List<CarQuoteInsItemVo> getkindItemsBI(String body) throws Exception{
	  List<CarQuoteInsItemVo> items = new ArrayList<CarQuoteInsItemVo>();
	  Document  doc =Jsoup.parse(body);
	  Elements eles = doc.select("input[name~=prpCitemKindsTemp\\[\\d+\\]\\.chooseFlag]");
	  for(Element el :eles){
		  String attrName = el.attr("name").trim();
		  if(el.val().trim().equals("1")&&attrName.contains("[")){
			  String index = attrName.substring(attrName.indexOf("[")+1, attrName.indexOf("]"));//截取中括号内索引号
			  CarQuoteInsItemVo item = getkindItemBI(body,index);
			  items.add(item);
		  }
	  }
	  return items;
  }
  /**
   * @Description: TODO(封装商业险对象)
   * @param body 返回报文html
   * @param index 索引号--人保系统返回html险种信息在同一个table里，根据索引号获取每行险种
   * @return 封装对象
   * @author yejie.huang
   * @date 2016年11月10日 上午11:21:47
   */
  public static CarQuoteInsItemVo getkindItemBI(String body,String index) throws Exception{
	  Map<String,String> relateMap = new HashMap<String,String>();
	  relateMap.put("quoteDetailId", "");//车险报价险种明细编号
	  relateMap.put("quoteNo", "");//报价单号
	  relateMap.put("category", "_1");//险种类别: 0为交强险，1为商业险
	  relateMap.put("kindCode", "prpCitemKindsTemp["+index+"].kindCode");//险种代码
	  relateMap.put("kindName", "prpCitemKindsTemp["+index+"].kindName");//险种名称
	  relateMap.put("seatNum", "prpCitemKindsTemp["+index+"].quantity");// 司机（乘客）责任险的座位数
	  relateMap.put("insuredAmount", "prpCitemKindsTemp["+index+"].amount");//保额/限额
	  relateMap.put("deductibleFlag", "specialCheckBox["+index+"]");//购买不计免赔特约险标志
	  relateMap.put("benchmarkPremium", "prpCitemKindsTemp["+index+"].benchMarkPremium");//标准保费
	  relateMap.put("discount", "prpCitemKindsTemp["+index+"].disCount");//折扣率
	  relateMap.put("commission", "");//合作方推广费
	  relateMap.put("premium", "prpCitemKindsTemp["+index+"].premium");//应交保费
	  CarQuoteInsItemVo res = new CarQuoteInsItemVo();
	  setValueByClazz(res,body,relateMap);
	  return res; 
  }
 
  /**
   * @Description: TODO(组装投保人、被保人、车主对象)
   * @param body 返回报文html
   * @param res CarRenewalRes对象
   * @author yejie.huang
   * @date 2016年11月10日 上午11:33:37
   */
  public static void getInsureAndOwner(String body,CarRenewalRes res) throws Exception{
	  Document  doc =Jsoup.parse(body);
	  Elements eles = doc.select("input[name~=prpCinsureds\\[\\d+\\]\\.insuredFlag]");
	  for(Element el :eles){
		  String attrValue = el.attr("value").trim(),attrName = el.attr("name").trim();
		  if(attrName.contains("[")&&attrValue!=null&&attrValue.length()>2){
			  String index = attrName.substring(attrName.indexOf("[")+1, attrName.indexOf("]"));//中括号内索引号
			  if("1".equals(attrValue.substring(0,1)))//投保人
			      res.setCarInsurerInfo(getRelatePersion(body,index,1));
			  if("1".equals(attrValue.substring(1,2)))//被保人
			     res.setCarAssuredInfo(getRelatePersion(body,index,2));
			  if("1".equals(attrValue.substring(2,3)))//车主
				  res.setCarOwnerInfo(getCarOwner(body,index));
		  }
	  }
	  
  }
  /**
   * @Description: TODO(封装被保人信息)
   * @param body 返回报文html
   * @param index 索引号--人保系统返回html（车主、投保人、被保人）信息在同一个table里，根据索引号获取对人
   * @param relationType 关系人类型 1-投保人;2-被保人
   * @return 封装对象
   * @author yejie.huang
   * @date 2016年11月10日 上午11:21:47
   */
  public static CarOrderRelationInfoVo getRelatePersion(String body,String index,int relationType) throws Exception{
	  Map<String,String> relateMap = new HashMap<String,String>();
	  relateMap.put("relationType", "_"+String.valueOf(relationType));//关系人类型 1-投保人;2-被保人;3-收件人
	  relateMap.put("name", "prpCinsureds["+index+"].insuredName");//姓名
	  relateMap.put("sex", "prpCinsureds["+index+"].sex");//性别 1-男,2-女
	  relateMap.put("age", "prpCinsureds["+index+"].age");// 年龄
	  relateMap.put("birthday", "");//出生日期
	  relateMap.put("idType", "prpCinsureds["+index+"].identifyType");//证件类型
	  relateMap.put("idNo", "prpCinsureds["+index+"].identifyNumber");//证件号码
	  relateMap.put("telePhone", "prpCinsureds["+index+"].phoneNumber");//固定电话
	  relateMap.put("mobilePhone", "prpCinsureds["+index+"].mobile");//手机号码
	  relateMap.put("email", "");//邮箱
	  relateMap.put("address", "prpCinsureds["+index+"].insuredAddress");//居住地址
	  relateMap.put("postCode", "prpCinsureds["+index+"].postCode");//邮政编码
	  CarOrderRelationInfoVo res = new CarOrderRelationInfoVo();
	  setValueByClazz(res,body,relateMap);
	  return res;
  }
  /**
   * @Description: TODO(封装车主信息)
   * @param body 返回报文html
   * @param index 索引号--人保系统返回html（车主、投保人、被保人）信息在同一个table里，根据索引号获取对应行
   * @return 封装对象
   * @author yejie.huang
   * @date 2016年11月10日 上午11:21:47
   */
  public static CarOwnerInfoVo getCarOwner(String body,String index) throws Exception{
		  Map<String,String> relateMap = new HashMap<String,String>();
		  relateMap.put("carOwner", "prpCinsureds["+index+"].insuredName");//车主姓名
		  relateMap.put("ownerPhone", "prpCinsureds["+index+"].mobile");// 车主手机号码
		  relateMap.put("ownerIdType", "prpCinsureds["+index+"].identifyType");//车主证件类型
		  relateMap.put("ownerIdentifyNumber", "prpCinsureds["+index+"].identifyNumber");//车主证件号码
		  relateMap.put("ownerAddr", "prpCinsureds["+index+"].insuredAddress");//车主详细地址信息
		  relateMap.put("ownerSex", "prpCinsureds["+index+"].sex");//车主姓别,1为男，2为女
		  relateMap.put("ownerAge", "prpCinsureds["+index+"].age");//车主年龄
		  relateMap.put("ownerBirthday", "");//车主出生日期，日期格式为yyyy-mm-dd
		  CarOwnerInfoVo res = new CarOwnerInfoVo();
		  setValueByClazz(res,body,relateMap);
		  return res;
	  }
  /**
   * @Description: TODO(封装行驶证车辆信息)
   * @param body 返回报文html
   * @return 封装对象
   * @author yejie.huang
   * @date 2016年11月10日 上午11:21:47
   */
  public static CarLicenseInfoVo getCar(String body) throws Exception{
	  Map<String,String> relateMap = new HashMap<String,String>();
	  relateMap.put("licenseNo", "prpCitemCar.licenseNo");
	  relateMap.put("engineNo", "prpCitemCar.engineNo");//发动机号
	  relateMap.put("vin", "prpCitemCar.vinNo");//车辆识别码VIN，即车架号
	  relateMap.put("brandName", "");// 车型名称(品牌型号)
	  relateMap.put("modelCode", "");//车型代码
	  relateMap.put("enrollDate", "prpCitemCar.enrollDate");//车辆初登日期
	  relateMap.put("chgOwnerFlag", "prpCitemCar.transferVehicleFlag");//过户车标志,0-否；1-是，默认为0
	  relateMap.put("chgOwnerDate", "prpCitemCar.transferDate");//
	  relateMap.put("ownerNature", "");//行驶证车主性质:01-个人;02-单位，默认为01
	  relateMap.put("useNature", "prpCitemCar.useNatureCode");// 车辆使用性质,1运营，2非运营，默认为2
	  relateMap.put("countryNature", "prpCitemCar.countryNature");//国别性质：01-国产、02-进口、03-合资
	  relateMap.put("customerType", "");//所有人性质:： 01-个人，02-机关，03-企业，默认为01
	  relateMap.put("licenseType", "LicenseTypeDes");//号牌种类：01为大型车，02为小型车，16为教练汽车，22为临时行驶车，默认为02
	  relateMap.put("nonLocalFlag", "prpCitemCar.noNlocalFlag");//外地车标志，1-外地车，0-本地车，默认为0
	  relateMap.put("taxType", "");//车船税交税类型  1.缴税   2.减税  3.免税   4.完税，默认为1
	  relateMap.put("loanFlag", "");//是否贷款车: 1，是 0，否，默认为0
	  relateMap.put("rentFlag", "");//是否租赁车: 1，是 0，否，默认为0
	  CarLicenseInfoVo res = new CarLicenseInfoVo();
	  setValueByClazz(res,body,relateMap);
	  return res; 
  }
  
  /**
   * @Description: TODO(封装res对象)
   * @param res CarRenewalRes
   * @param body 返回报文html
   * @param category 险种类型 1商业险，0交强险
   * @return 封装对象
   * @author yejie.huang
   * @date 2016年11月10日 上午11:21:47
   */
  public static void getBIRenewal(CarRenewalRes res,String body,int category) throws Exception{
	  Map<String,String> relateMap = new HashMap<String,String>();
	  if(category==1){
		  relateMap.put("startDateBI", "prpCmain.startDate");
		  relateMap.put("endDateBI", "prpCmain.endDate");
	  }else if(category==0){
		  relateMap.put("startDateCI", "prpCmainCI.startDate");
		  relateMap.put("endDateCI", "prpCmainCI.endDate");
	  }
	  setValueByClazz(res,body,relateMap);
  }
  /**
   * @Description: TODO(封装任意类型对象)
   * @param obj 需要封装的对象
   * @param body 返回报文（html）
   * @param relateMap key为内部com.ebtins.dto.open.*的Model属性，Value为外部抓取报文（html--input[name=）参数名
   * @return 返回封装对象
   * @author yejie.huang
   * @date 2016年11月8日 下午3:28:36
   */
  public static Object setValueByClazz(Object obj,String body,Map<String,String> relateMap) throws Exception{
	  Document  doc =Jsoup.parse(body);
	  /**
	   * 获取html input标签value值
	   */
	  for(String key:relateMap.keySet()){
		  String paramName = relateMap.get(key);
		  String inputValue = "";
		  if(!"".equals(paramName)){
			  /**
			   * 特殊情况处理:1、如果是复选框，取input标签checked属性值；2、如果是下划线开头(自定义值)，直接取下划线后面值
			   * 其他情况取input标签value属性值
			   */
			  if(paramName.contains("specialCheckBox")&&doc.select("input[name="+paramName+"]").attr("checked").equals("checked")){
				  inputValue = "1";
			  }else if(paramName.startsWith("_")){
				  inputValue = paramName.substring(1);
			  }else{
				  inputValue =doc.select("input[name="+paramName+"]").attr("value");
			  }
		  }
		  relateMap.put(key, inputValue);
	  }
	  RenbaoUtil.setValueByClazz(obj, relateMap);
	  System.out.println("obj---->"+JSON.toJSONString(obj));
	  return obj;
	  
  }
  /**
   * @Description: TODO(计算总保额、总标准保费、总应交保费，人保系统没有直接返回总和)
   * @param res 续保返回对象
   * @author yejie.huang
   * @date 2016年11月10日 上午9:39:20
   */
  public static  CarRenewalRes countSum(CarRenewalRes res){
	  double sumInsured=0.0,sumStdPrem=0.0;
	  for(CarQuoteInsItemVo item : res.getLastCarQuoteInfo().getCarQuoteInsItemList()){
		  if(ValidatorUtil.isNumeric(item.getInsuredAmount()))
			  sumInsured += Double.parseDouble(item.getInsuredAmount());
		  if(ValidatorUtil.isNumeric(item.getBenchmarkPremium()))
			  sumStdPrem += Double.parseDouble(item.getBenchmarkPremium());
	  }
	  res.getLastCarQuoteInfo().setSumInsured(String.valueOf(sumInsured));
	  res.getLastCarQuoteInfo().setSumStdPrem(String.valueOf(sumStdPrem));
	  double sumPayAmount = res.getLastCarQuoteInfo().getSumBiPremium()+res.getLastCarQuoteInfo().getSumCiPremium();
	  res.getLastCarQuoteInfo().setSumPayAmount(sumPayAmount);
	  return res;
  }
}
