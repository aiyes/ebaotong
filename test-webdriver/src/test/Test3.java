/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.ebtins.dto.open.CarLicenseInfoVo;
import com.ebtins.dto.open.CarModelInfoVo;
import com.ebtins.dto.open.CarOrderRelationInfoVo;
import com.ebtins.dto.open.CarOwnerInfoVo;
import com.ebtins.dto.open.CarQuoteInsItemVo;
import com.ebtins.dto.open.CarQuoteReq;
import com.ebtins.dto.open.CarQuoteRes;
import com.ebtins.open.common.constant.CommonConstants;

import hyj.quote.HttpReq;
import newQuote.HuaanConfig;

/**
 * @ClassName: Test3
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年10月22日 下午11:10:33
 *
 */

public class Test3 {
	String post1String = HuaanConfig.getDwData().get("dw1Data").toString();

	@Test
	public void test() throws Exception {
		
		/**
		 * 调用车型查询接口
		 */
	/*	CarModelReq modelReq = new CarModelReq();
		modelReq.setBrandName("宝马");
		modelReq.setCompanyType(CommonConstants.COM_TYPE_HUAAN);
		ResponseEntity<CarModelRes> modelRes = new RestTemplate().postForEntity("http://" + HOST + "/model", modelReq, CarModelRes.class);
		CarModelRes model = modelRes.getBody();
		System.out.println("车型信息-->"+JSON.toJSON(modelRes.getBody()));*/

		
		CarQuoteReq req = new CarQuoteReq();
		//req.setCompanyTypes("");//保险公司类型
		//req.setInsurerIds("");//保险公司编号数组
		req.setLicenseFlag(0);//牌照标志
		req.setIsRenewal(0);//是否续保
		req.setStartDateBI("2016-11-09 00:00:00");//商业险起保日期
		req.setEndDateBI("2017-11-08 23:59:59");//商业险终保日期
		req.setStartDateCI("2016-11-14 00:00:00");//交强险起保日期  --重要，影响结算结果
		req.setEndDateCI("2017-11-13 23:59:59");//交强险终保日期
		req.setRunArea("389001");//行驶区域代码
		//req.setFileList("")//附件列表
		
		//车型信息
		//CarModelInfoVo modelInfo = model.getCarModelList().get(0);
		CarModelInfoVo modelInfo = new CarModelInfoVo();
		req.setCarModelInfo(modelInfo);
		modelInfo.setModelCode("KMD1078GZF");
		modelInfo.setPurchasePrice("191000");//新车购置价--purchasePrice
		modelInfo.setSeatCount("5");//核定载客数--seatCount 座位数
		modelInfo.setExhaustScale("2.494");//排量----exhaustScale 排气量
		modelInfo.setCarVehicleType("1");//车辆种类----carVehicleType 车辆种类:1为客车，2为货车，3为特种车
		
		
		//投保险种明细
		List<CarQuoteInsItemVo> itemList = new ArrayList<CarQuoteInsItemVo>();
		req.setCarQuoteInsItemList(itemList);
		
	   //车损险
		CarQuoteInsItemVo insItem = new CarQuoteInsItemVo();
		itemList.add(insItem);
		insItem.setKindCode("1");//险别代码
		insItem.setInsuredAmount("191000");//每标的保额/限额
		insItem.setDeductibleFlag(0);//不计免赔特约险购买标志
		/*	
		//全车盗抢险
		CarQuoteInsItemVo insItem1 = new CarQuoteInsItemVo();
		itemList.add(insItem1);
		insItem1.setKindCode("5");//险别代码
		insItem1.setInsuredAmount("191000");//每标的保额/限额
		insItem1.setDeductibleFlag(1);//不计免赔特约险购买标志
*/		/*
		//自燃
		CarQuoteInsItemVo insItem2 = new CarQuoteInsItemVo();
		itemList.add(insItem2);
		insItem2.setKindCode("7");//险别代码
		insItem2.setInsuredAmount("191000");//每标的保额/限额
		insItem2.setDeductibleFlag(0);//不计免赔特约险购买标志
*/		
	/*	//第三者责任保险保额
		CarQuoteInsItemVo insItem6 = new CarQuoteInsItemVo();
		itemList.add(insItem6);
		insItem6.setKindCode("2");//险别代码
		insItem6.setInsuredAmount("50000");//每标的保额/限额
		insItem6.setDeductibleFlag(1);//不计免赔特约险购买标志
*/		
		
	/*	//司机座位险
		CarQuoteInsItemVo insItem7 = new CarQuoteInsItemVo();
		itemList.add(insItem7);
		insItem7.setKindCode("3");//险别代码
		insItem7.setInsuredAmount("10000");//每标的保额/限额
		insItem7.setDeductibleFlag(1);//不计免赔特约险购买标志
		
		//乘客座位险
		CarQuoteInsItemVo insItem8 = new CarQuoteInsItemVo();
		itemList.add(insItem8);
		insItem8.setKindCode("4");//险别代码
		insItem8.setInsuredAmount("10000");//每标的保额/限额
		insItem8.setSeatNum("4");
		insItem8.setDeductibleFlag(1);//不计免赔特约险购买标志
*/		
		//车身划痕损失险
		CarQuoteInsItemVo insItem9 = new CarQuoteInsItemVo();
		itemList.add(insItem9);
		insItem9.setKindCode("8");//险别代码
		insItem9.setInsuredAmount("20000");//每标的保额/限额
		insItem9.setDeductibleFlag(1);//不计免赔特约险购买标志
		
		//车身划痕损失险
		CarQuoteInsItemVo insItem10 = new CarQuoteInsItemVo();
		itemList.add(insItem10);
		insItem10.setKindCode("9");//险别代码
		//insItem10.setInsuredAmount("20000");//每标的保额/限额
		insItem10.setDeductibleFlag(0);//不计免赔特约险购买标志
		
		
		
		
		
		
		
		
		
		/*
		//玻璃破粹
		CarQuoteInsItemVo insItem3 = new CarQuoteInsItemVo();
		itemList.add(insItem3);
		insItem3.setKindCode("6");//险别代码
		insItem3.setDeductibleFlag(0);//不计免赔特约险购买标志
		
		CarQuoteInsItemVo insItem4 = new CarQuoteInsItemVo();
		itemList.add(insItem4);
		insItem4.setKindCode("030115");//险别代码
		insItem4.setDeductibleFlag(0);//不计免赔特约险购买标志
		
		
		CarQuoteInsItemVo insItem5 = new CarQuoteInsItemVo();
		itemList.add(insItem5);
		insItem5.setKindCode("030109");//险别代码
		insItem5.setInsuredAmount("20000");//每标的保额/限额
		insItem5.setDeductibleFlag(0);//不计免赔特约险购买标志
*/		
		
		
		
		//行驶证及相关行驶信息
		CarLicenseInfoVo licenInfo = new CarLicenseInfoVo();
		req.setCarLicenseInfo(licenInfo);
		licenInfo.setLicenseNo("粤BX13G7");//号牌号码
		licenInfo.setEngineNo("H357717");//发动机号
		licenInfo.setEnrollDate("2016-10-08");//车辆初登日期
		licenInfo.setVin("LVGBF53K0EG104357");//车架号
		licenInfo.setChgOwnerFlag("");//过户车标志
		licenInfo.setChgOwnerDate("");//过户日期
		licenInfo.setOwnerNature("");//行驶证车主性质
		licenInfo.setCustomerType("");//所有人性质
		licenInfo.setUseNature(1);//车辆使用性质
		licenInfo.setCountryNature("1");//国别性质
		licenInfo.setLicenseType("01");//号牌种类
		licenInfo.setNonLocalFlag(0);//是否外地车
		licenInfo.setCustomerType("01");//所有人性质:： 01-个人，02-机关，03-企业，默认为01
		licenInfo.setChgOwnerFlag("0");//过户车标志
		
		//车主基本信息
		CarOwnerInfoVo ownerInfo = new CarOwnerInfoVo();
		req.setCarOwnerInfo(ownerInfo);
		ownerInfo.setCarOwner("张三");//车主姓名
		ownerInfo.setOwnerIdType("01");//车主证件类型
		ownerInfo.setOwnerIdentifyNumber("450722198910122856");//车主证件号码
		ownerInfo.setOwnerPhone("15296281584");//车主手机号码
		ownerInfo.setOwnerAddr("广东深圳");//车主详细地址信息
		
		//投保人
		CarOrderRelationInfoVo ri1 = new CarOrderRelationInfoVo();
		ri1.setName("张三");
		ri1.setIdType("01");
		ri1.setIdNo("450722198910122856");
		ri1.setTelePhone("15296281584");
		ri1.setAddress("广东深圳");
		req.setCarInsurerInfo(ri1);
		
		//被保险人
		CarOrderRelationInfoVo ri2 = new CarOrderRelationInfoVo();
		ri2.setName("张三");
		ri2.setIdType("01");
		ri2.setIdNo("450722198910122856");
		ri2.setTelePhone("15296281584");
		ri2.setAddress("广东深圳");
		req.setCarAssuredInfo(ri2);
		
		req.setCompanyType(CommonConstants.COM_TYPE_HUAAN);
		System.out.println("------>:"+JSON.toJSONString(HttpReq.quoteReq(post1String, req)));
	}

}
