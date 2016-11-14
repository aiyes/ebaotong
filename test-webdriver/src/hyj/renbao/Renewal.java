/**
 * 
 */
package hyj.renbao;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.ebtins.dto.open.CarAssuredInfoDTO;
import com.ebtins.dto.open.CarInsurerInfoDTO;
import com.ebtins.dto.open.CarLicenseInfoVo;
import com.ebtins.dto.open.CarOwnerInfoVo;
import com.ebtins.dto.open.CarQuoteInfoVo;
import com.ebtins.dto.open.CarQuoteInsItemVo;
import com.ebtins.dto.open.CarRenewalReq;
import com.ebtins.dto.open.CarRenewalRes;
import com.ebtins.open.common.constant.CommonConstants;
import com.ebtins.open.common.util.ValidatorUtil;

import ebtins.smart.proxy.company.renbao.dto.RenbaoRenewalContent;
import ebtins.smart.proxy.company.renbao.dto.RenbaoRenewalData;
import ebtins.smart.proxy.company.renbao.util.RenbaoUtil;
import huaan.quote.util.StringUtil;
import hyj.login.RenBaoLoginA;

/**
 * @ClassName: Renewal
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年11月7日 下午2:05:18
 *
 */

public class Renewal {
  public static CarRenewalRes getRenewal(CarRenewalReq req) throws Exception{
	  String cookie = RenBaoLoginA.login();
	  String queryRenewalUrl = "http://10.134.130.208:8000/prpall/business/selectRenewal.do?pageSize=10&pageNo=1";
	  String referRenewalUrl = "http://10.134.130.208:8000/prpall/business/prepareRenewal.do?bizType=PROPOSAL&editType=RENEWAL";
	  Map<String, String> params=new HashMap<String, String>();
	  params.put("prpCrenewalVo.engineNo","");//发动机号
	  params.put("prpCrenewalVo.frameNo","");//车架号
	  params.put("prpCrenewalVo.licenseNo",req.getLicenseNo());//号牌号码
	  params.put("prpCrenewalVo.licenseType","02");//号牌类型 02-小型汽车
	  params.put("prpCrenewalVo.vinNo", "");//Vin好号
	  params.put("prpCrenewalVo.policyNo", "");//上年保单号
	  params.put("riskCode", "DAA");//DAA--机动车商业险
	  String body =RenBaoLoginA.post(queryRenewalUrl, params,"GBK",cookie,referRenewalUrl);
	  System.out.println("1119body-->"+body);
	  System.out.println("bbb__>"+body);
	  RenbaoRenewalContent renewalContent = JSON.parseObject(body, RenbaoRenewalContent.class);
	  String BIpolicyNo = "",CIpolicyNo =	"";
	  for(RenbaoRenewalData data :renewalContent.getData()){
		  if(data.getLastDamagedBI()!=null){
			  BIpolicyNo = data.getPolicyNo();
		  }else if(data.getLastDamagedCI()!=null){
			  CIpolicyNo = data.getPolicyNo();
		  }
			  
	  }
	  CarRenewalRes res= new CarRenewalRes();
	  CarQuoteInfoVo carQuoteInfo = new CarQuoteInfoVo();
	  List<CarQuoteInsItemVo> items = new ArrayList<CarQuoteInsItemVo>(); 
	  carQuoteInfo.setCarQuoteInsItemList(items);
	  res.setLastCarQuoteInfo(carQuoteInfo);
	  reqRenewal(res,cookie,CIpolicyNo,0);
	  reqRenewal(res,cookie,BIpolicyNo,1);
	  return countSum(res);
  }
  public static CarRenewalRes reqRenewal(CarRenewalRes res,String cookie,String policyNo,int category) throws Exception{
	  String UTF8 = "utf-8";
	  Map<String, String> outCookies =new HashMap<String, String>();
	  String renewalUrl = "http://10.134.130.208:8000/prpall/business/browsePolicyNo.do?bizNo="+policyNo;
	  String renewalBody =RenBaoLoginA.get(renewalUrl,UTF8,cookie,null,outCookies);
	  getBIRenewal(res,renewalBody,category);
	  
	  //商业险
	  String action = category==1?"showCitemKind.do":"showCitemKindCI.do";
	  String itemKindUrl = "http://10.134.130.208:8000/prpall/business/"+action+"?editType=SHOW_POLICY&bizType=POLICY&bizNo="+policyNo+"&riskCode=DAA&minusFlag=&contractNo=%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20&comCode=44030716&originQuery=&proposalNo=TDAA201544030000056456&rnd614=Mon%20Nov%207%2013:44:34%20UTC+0800%202016";
	  String itemKinddbody =RenBaoLoginA.get(itemKindUrl,UTF8,cookie,renewalUrl,outCookies);
	  getCarQuoteInfo(res.getLastCarQuoteInfo(),itemKinddbody,category);
	  System.out.println("itemKinddbody-->"+itemKinddbody);
	  /**
	   * category==1商业险，category==0交强险
	   */
	  if(category==1){
		  res.getLastCarQuoteInfo().getCarQuoteInsItemList().addAll(getkindItemsBI(itemKinddbody));
	  }else if(category==0){
		  res.getLastCarQuoteInfo().getCarQuoteInsItemList().add(getkindItemCI(itemKinddbody));
	  }
	  
	  if(res.getCarLicenseInfo()==null){
		  //驶证及相关行驶信息
		  String carUrl = "http://10.134.130.208:8000/prpall/business/showCitemCar.do?editType=SHOW_POLICY&bizType=POLICY&bizNo="+policyNo+"&riskCode=DAA&minusFlag=&contractNo=%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20&comCode=44030716&originQuery=&proposalNo=TDAA201544030000056456&rnd268=Mon%20Nov%207%2013:44:21%20UTC+0800%202016";
		  String carbody =RenBaoLoginA.get(carUrl,UTF8,cookie,renewalUrl,outCookies);
		  CarLicenseInfoVo carLicenseInfo = getCar(carbody);
		  res.setCarLicenseInfo(carLicenseInfo);
		  
		  //投保人、被保人、车主
		  String insureUrl = "http://10.134.130.208:8000/prpall/business/showCinsured.do?editType=SHOW_POLICY&bizType=POLICY&bizNo="+policyNo+"&riskCode=DAA&minusFlag=&contractNo=%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20&comCode=44030716&originQuery=&proposalNo=TDAA201544030000056456&rnd215=Mon%20Nov%207%2013:44:25%20UTC+0800%202016";
		  String nsurebody =RenBaoLoginA.get(insureUrl,UTF8,cookie,renewalUrl,outCookies);
		  getInsureAndOwner(nsurebody,res);
	  }
	  return res;
  }
  
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
  
//交强险
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
  
  //商业险列表
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
			      res.setCarInsurerInfo(getInsre(body,index));
			  if("1".equals(attrValue.substring(1,2)))//被保人
			     res.setCarAssuredInfo(getAssure(body,index));
			  if("1".equals(attrValue.substring(2,3)))//车主
				  res.setCarOwnerInfo(getCarOwner(body,index));
		  }
	  }
	  
  }
  /**
   * @Description: TODO(封装投保人信息)
   * @param body 返回报文html
   * @param index 索引号--人保系统返回html（车主、投保人、被保人）信息在同一个table里，根据索引号获取对应行
   * @return 封装对象
   * @author yejie.huang
   * @date 2016年11月10日 上午11:21:47
   */
  public static CarInsurerInfoDTO getInsre(String body,String index) throws Exception{
	  Map<String,String> relateMap = new HashMap<String,String>();
	  relateMap.put("insName", "prpCinsureds["+index+"].insuredName");//投保人姓名
	  relateMap.put("insSex", "prpCinsureds["+index+"].sex");//投保人性别
	  relateMap.put("insAge", "prpCinsureds["+index+"].age");// 投保人年龄
	  relateMap.put("insBirthDay", "");//投保人出生日期
	  relateMap.put("insIdType", "prpCinsureds["+index+"].identifyType");//投保人证件类型
	  relateMap.put("insIdNo", "prpCinsureds["+index+"].identifyNumber");//投保人证件号码
	  relateMap.put("insFixedPhone", "prpCinsureds["+index+"].phoneNumber");//投保人固定电话
	  relateMap.put("insMobile", "prpCinsureds["+index+"].mobile");//投保人手机号码
	  relateMap.put("insEmail", "");//投保人邮箱
	  relateMap.put("insLiveAddress", "prpCinsureds["+index+"].insuredAddress");//投保人居住地址
	  relateMap.put("insPostCode", "prpCinsureds["+index+"].postCode");//投保人邮政编码
	  CarInsurerInfoDTO res = new CarInsurerInfoDTO();
	  setValueByClazz(res,body,relateMap);
	  return res;
  }
  /**
   * @Description: TODO(封装被保人信息)
   * @param body 返回报文html
   * @param index 索引号--人保系统返回html（车主、投保人、被保人）信息在同一个table里，根据索引号获取对应行
   * @return 封装对象
   * @author yejie.huang
   * @date 2016年11月10日 上午11:21:47
   */
  public static CarAssuredInfoDTO getAssure(String body,String index) throws Exception{
	  Map<String,String> relateMap = new HashMap<String,String>();
	  relateMap.put("assName", "prpCinsureds["+index+"].insuredName");//被保人姓名
	  relateMap.put("assSex", "prpCinsureds["+index+"].sex");//被保人性别
	  relateMap.put("assAge", "prpCinsureds["+index+"].age");// 被保人年龄
	  relateMap.put("assBirthDay", "");//被保人出生日期
	  relateMap.put("assIdType", "prpCinsureds["+index+"].identifyType");//被保人证件类型
	  relateMap.put("assIdNo", "prpCinsureds["+index+"].identifyNumber");//被保人证件号码
	  relateMap.put("assFixedPhone", "prpCinsureds["+index+"].phoneNumber");//被保人固定电话
	  relateMap.put("assMobile", "prpCinsureds["+index+"].mobile");//被保人手机号码
	  relateMap.put("assEmail", "");//被保人邮箱
	  relateMap.put("assLiveAddress", "prpCinsureds["+index+"].insuredAddress");//被保人居住地址
	  relateMap.put("assPostCode", "prpCinsureds["+index+"].postCode");//被保人邮政编码
	  CarAssuredInfoDTO res = new CarAssuredInfoDTO();
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
