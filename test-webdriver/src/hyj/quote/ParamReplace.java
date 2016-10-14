/**
 * 
 */
package hyj.quote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import com.alibaba.fastjson.JSON;
import com.ebtins.dto.open.CarLicenseInfoVo;
import com.ebtins.dto.open.CarModelInfoVo;
import com.ebtins.dto.open.CarOwnerInfoVo;
import com.ebtins.dto.open.CarQuoteInsItemVo;
import com.ebtins.dto.open.CarQuoteReq;

import huaan.quote.util.StringUtil;
import hyj.model.AttributeVo;
import hyj.model.DataObjVo;
import hyj.model.DwData;
import hyj.util.TestConfig;

/**
 * @ClassName: ParamReplace
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年10月9日 上午10:21:54
 *
 */

public class ParamReplace {
	/**
	 * @Description: TODO(获取替换参数的报文)
	 * @param originalDwData原始报文
	 * @return替换参数的报文
	 * @date 2016年9月27日 上午10:01:44
	 */
	public static String getNewDwData(String originalDwData,Map<String,String> postParams){
		CarQuoteReq req = getReqInfo();
		
		postParams.put("Base.COrigType","0");//原方案续保
		postParams.put("JQ_Base.CRenewMrk","0");//续保标志(交)
		postParams.put("SY_Base.CRenewMrk","0");//续保标志(商)
		postParams.put("Base.CProdNo","0380");//
		postParams.put("JQ_Base.NAmt","122000.00");//
		postParams.put("JQ_Base.NPrm","0.00");//
		postParams.put("JQ_Base.NPrmTax","0.00");//
		postParams.put("JQ_Base.NTax","0.00");//
		postParams.put("SY_Base.NAmt","0");//
		postParams.put("SY_Base.NPrm","0");//
		postParams.put("SY_Base.NPrmTax","0.00");//
		postParams.put("SY_Base.NTax","0.00");//
		postParams.put("Base.CAmtCur","01");//保额币种
		postParams.put("Base.CPrmCur","01");//保费币种
		postParams.put("Base.CReqType","C");//" ignoreChange=
		postParams.put("Base.CCommonFlag","0");//
		postParams.put("Base.CPlyImageid","0");//保单临时ID
		postParams.put("Base.CEdrImageid","0");//批单临时ID
		postParams.put("Base.CScoverageFlg","1");//是否单独承保车险标志
		postParams.put("Base.CDptCde","01980003");//机构部门
		postParams.put("Base.CBsnsTyp","19002");//业务来源
		postParams.put("Base.CBrkrCde","0101062050");//代理人
		postParams.put("Base.CSlsCde","101154139");//业务员
		postParams.put("JQ_Base.NCommRate","0.04");//手续费比例(交)
		postParams.put("SY_Base.NCommRate","0.4982");//手续费比例(商)
		postParams.put("Base.CLicense","203136000000800");//中介机构代码
		postParams.put("JQ_Base.NPerformance","0.00");//绩效比例(交)
		postParams.put("SY_Base.NPerformance","0.00");//绩效比例(商)
		postParams.put("Base.CAgtAgrNo","B01201600012_0");//合作协议
		postParams.put("Base.CSalegrpCde","0198000301");//销售团队
		postParams.put("Base.CTrueAgtCde","0101062050");//协作人
		postParams.put("Base.CJsFlag","0");//交商同保
		postParams.put("Base.CBsnsSubtyp","199001");//业务类型
		postParams.put("Base.OprtLvl","198001");//业务等级
		postParams.put("Base.CFinTyp","0");//缴费方式
		postParams.put("Base.CWorkDpt","377001");//单位性质
		postParams.put("Base.CCusTyp","927002");//客户类型
		postParams.put("Base.CVipMrk","0");//标志
		postParams.put("Base.CInviteTitle","699003");//招投标业务
		postParams.put("Base.BuisType","925006");//业务线
		postParams.put("Base.CDisptSttlCde","007001");//争议处理
		postParams.put("Base.CAgriMrk","0");//是否涉农
		postParams.put("JQ_Base.IsImage","0");//补传标志(交)
		postParams.put("SY_Base.IsImage","0");//补传标志(商)
		postParams.put("Base.XqfxLvl","928001");//洗钱风险等级
		postParams.put("Base.CKpType","02");//开票种类
		postParams.put("Base.COprTyp","0");//保单生成
		postParams.put("Base.IsNofee","0");//是否不见费
		postParams.put("Base.CFeeFlag","1");//见费出单标志
		
		postParams.put("Vhlowner.CUnitAttrib","377001");//单位性质
		postParams.put("Vhlowner.COwnerCls","376001");//车主类别
		postParams.put("Vhlowner.CDrvSex","106001");//车主性别
		postParams.put("Vhlowner.NDrvownerAge","27");//车主年龄
		postParams.put("Insured.NSeqNo","1");//客户编号
		postParams.put("Insured.CInsuredNme","");//姓名
		postParams.put("Insured.CInsuredCde","032814984");//被保险人编码
		postParams.put("Insured.CVhlInsuredRel","378001");//类别
		postParams.put("Insured.CCertfCls","120001");//证件类型
		postParams.put("Insured.CCertfCde","");//证件号码
		postParams.put("Insured.CTel","15296281584");//电话
		postParams.put("Insured.CClntAddr","");//地址
		postParams.put("Insured.CSex","");//性别
		postParams.put("Insured.NAge","27");//年龄
		postParams.put("Applicant.CAppNme","张三");//姓名
		postParams.put("Applicant.CAppCde","032814984");//投保人代码
		postParams.put("Applicant.CTfiAppTyp","378001");//类别
		postParams.put("Applicant.CCertfCls","120001");//证件类型
		postParams.put("Applicant.CCertfCde","450722198910122856");//证件号码
		postParams.put("Applicant.CCountry","193001");//国籍
		postParams.put("Applicant.CTel","15296281584");//电话
		postParams.put("Applicant.CClntAddr","广东深圳");//地址
		
		postParams.put("Vhl.CPlateColor","1");//车牌颜色
		postParams.put("Vhl.CBrandId","KMD1078GZF");// 车型代码/厂牌车型
		postParams.put("Vhl.CModelCde","GTM7251GB");//
		postParams.put("Vhl.CModelNme","丰田GTM7251GB轿车");//()
		postParams.put("Vhl.NSeatNum","5");//核定载客数
		postParams.put("Vhl.NDisplacement","2.494");//排量
		//postParams.put("Vhl.CUsageCde","309003");//使用性质--投保查询====>平台返回错误:示范条款保单承保时车辆使用性质不允许上传
		postParams.put("Vhl.CUseAtr3","343002");//所属性质
		postParams.put("Vhl.CCarAtr","384001");//经营属性
		postParams.put("Vhl.CVhlSubTyp","308003");//车辆种类
		postParams.put("Vhl.CVhlTyp","");//车辆类型
		postParams.put("Vhl.CRegVhlTyp","K32");//车辆类型描述
		postParams.put("Vhl.CIsTipperFlag","0");//是否自卸车
		postParams.put("Vhl.NNewPurchaseValue","1191000");//新车购置价
		postParams.put("Vhl.NActualValue","191000");//实际价值
		postParams.put("Vhl.CIndustryModelCode","BGQGKNUD0006");//行业车型编码
		postParams.put("Vhl.CFleetMrk","0");//车队标志
		postParams.put("Vhl.CLoanVehicleFlag","1");//车贷投保多年
		postParams.put("Vhl.CDevice1Mrk","0");//是否过户投保
		postParams.put("Vhl.CYl4","303011002");//玻璃类型
		postParams.put("Vhl.CAmtType","943003");//保额确定类型
		postParams.put("Vhl.CInqType","645002");//折旧率
		postParams.put("Vhl.NDespRate","386004");//(
		postParams.put("Vhl.CInspectorCde","305005002");//验车情况
		postParams.put("Vhl.CInspectorNme","101001222");//验车人
		postParams.put("Vhl.CInspectTm","2016-10-08");//验车时间
		postParams.put("Vhl.CCheckResult","305012006");//验车结果
		postParams.put("Vhl.CInspectRec","的");//验车记录
		postParams.put("Vhl.CCarYear","2011-12");//上市年份
		postParams.put("Vhl.CNewVhlFlag","0");//新旧车标志
		postParams.put("Vhl.CNewEnergyFlag","0");//新能源车辆标志
		postParams.put("Vhl.CIfhkcarFlag","0");//是否粤港两地车
		postParams.put("Vhl.CEcdemicMrk","0");//是否外地车
		postParams.put("Vhl.CRemark","手自一体 豪华版 国Ⅳ");//备注
		postParams.put("Vhl.NVhlTonage","1490");//整备质量
		postParams.put("Vhl.NNodamageYears","0");//跨省首年投保未出险证明年数
		postParams.put("Vhl.CFrmNoUnusualMrk","0");//车架号异常标志
		postParams.put("Vhl.CVhlQueryFla","1");//车辆查询标志
		
		
		postParams.put("SY_PrmCoef.NTgtFld1","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld2","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld3","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld4","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld23","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld5","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld10","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld7","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld14","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld15","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld18","1.0");//系数
		postParams.put("SY_PrmCoef.NTgtFld11","0.00");//减免保费
		postParams.put("SY_PrmCoef.CLossRatio","1.0");//系数
		postParams.put("SY_PrmCoef.CMtcMng","1.0");//车队管理系数
		postParams.put("SY_PrmCoef.NTgtFld25","1.0");//广州金交会专用系数
		postParams.put("SY_PrmCoef.NTgtFld19","1.00");//平均年行驶里程系数(北京)
		postParams.put("SY_PrmCoef.NTgtFld17","0");//停驶天数
		postParams.put("SY_PrmCoef.NTgtFld22","1.00");//交通违法记录系数(深圳)
		postParams.put("SY_PrmCoef.NTgtFld21","1.00");//赔款记录系数(深圳)
		postParams.put("SY_PrmCoef.NMulRdr","0.95");//多险别投保优惠系数
		postParams.put("SY_PrmCoef.NTgtFld13","1.00");//选用系数合计
		postParams.put("Timmer.COprNm","游嘉琦");//录单人
		postParams.put("Timmer.COprCde","101154139");//
		
		
		//影响交强结果参数
		postParams.put("Timmer.JTInsrncBgnTm",req.getStartDateCI());//保险起止期
		postParams.put("Timmer.JTInsrncEndTm",req.getEndDateCI());//
		postParams.put("Timmer.JTotalDays","365");//共   --不影响交强计算结果
		postParams.put("Timmer.JNRatioCoef","1.0");//短期费率系数
		postParams.put("Timmer.BTInsrncBgnTm",req.getStartDateBI());//保险起止期
		postParams.put("Timmer.BTInsrncEndTm",req.getEndDateBI());//
		postParams.put("SY_PrmCoef.CRunArea",req.getRunAreaCode());//行驶区域--行驶区域代码
		
		postParams.put("Timmer.BTotalDays","365");//共
		postParams.put("Timmer.BNRatioCoef","1.0");//短期费率系数
		postParams.put("Timmer.TAppTm","2016-11-08");//投保日期
		postParams.put("Timmer.TOprTm","2016-10-08");//录单日期
		postParams.put("Timmer.TIssueTm","2016-10-08");//签单日期
		postParams.put("Pay.NTms","1");//
		postParams.put("JQ_Pay.NPayablePrm","0");//
		postParams.put("SY_Pay.NPayablePrm","0");//
		
		//车主基本信息
		postParams.put("Vhlowner.COwnerNme",req.getCarOwnerInfo().getCarOwner());//姓名
		postParams.put("Vhlowner.CCertfCls",req.getCarOwnerInfo().getOwnerIdType());//证件类型
		postParams.put("Vhlowner.CCertfCde",req.getCarOwnerInfo().getOwnerIdentifyNumber());//证件号码
		postParams.put("Vhlowner.CTel",req.getCarOwnerInfo().getOwnerPhone());//电话
		postParams.put("Vhlowner.CClntAddress",req.getCarOwnerInfo().getOwnerAddr());//地址
		
		//行驶证及相关行驶信息
		postParams.put("Vhl.CPlateNo",req.getCarLicenseInfo().getLicenseNo());//号牌号码
		postParams.put("Vhl.CEngNo",req.getCarLicenseInfo().getEngineNo());//发动机号
		postParams.put("Vhl.CFstRegYm",req.getCarLicenseInfo().getEnrollDate());//注册日期 --车辆初登日期
		postParams.put("Vhl.CFrmNo",req.getCarLicenseInfo().getVin());//车架号
		//postParams.put("Vhl.CFrmNo",req.getCarLicenseInfo().getVin());//过户车标志
		//postParams.put("Vhl.CFrmNo",req.getCarLicenseInfo().getVin());//过户日期
		//postParams.put("Vhl.CFrmNo",req.getCarLicenseInfo().getVin());//行驶证车主性质
		//postParams.put("Vhl.CFrmNo",req.getCarLicenseInfo().getVin());//所有人性质
		//postParams.put("Vhl.CFrmNo",req.getCarLicenseInfo().getVin());//车辆使用性质
		postParams.put("Vhl.CProdPlace",req.getCarLicenseInfo().getCountryNature());//车辆产地--国别性质
		postParams.put("Vhl.CPlateTyp",req.getCarLicenseInfo().getLicenseType());//号牌种类
		
		postParams.put("Base.CProdNo","0380_0360");//

        String newString = replacedParam(originalDwData, postParams).replace("undefined", "").replace("UNDEFINED", "");
        
        String cvrgDwObjstr = TestConfig.getDwData().get("cvrgDwObj").toString();
        String cvrg119 = TestConfig.getDwData().get("cvrg119").toString();
        //newString = replaceCrvg(newString,cvrgDwObjstr,cvrg119,req.getCarQuoteInsItemList());
        
        List<DwData> dwDatas = JSON.parseArray(newString, DwData.class);
        System.out.println("newstr1__"+newString);
        for(DwData dw:dwDatas){
        	if(dw.getDwName().equals("prodDef.vhl.Cvrg_DW")){
        		dw.setDataObjVoList(replaceCrvg1(cvrgDwObjstr,cvrg119,req.getCarQuoteInsItemList()));
        	}
        }
        
        newString = JSON.toJSONString(dwDatas);
        
        System.out.println("newstr2__"+newString);
        return newString;
	}
	/**
	 * @Description: TODO(替换报文参数值,除dwName: 'prodDef.vhl.Cvrg_DW'的cvrg前缀参数外)
	 * @param originalString 原始报文
	 * @param postParams 参数
	 * @return 替换后的报文
	 * @date 2016年9月26日 上午9:44:12
	 */
	public static String replacedParam(String originalString,Map<String,String> postParams){
		  for(String paramName : postParams.keySet()){
			  //匹配 {name: 'Applicant.CAppNme',newValue: '张三',bakValue: '',value: ''}
			  String reg = "\\{[^\\{]*"+paramName+"[^\\}]*\\}";
			  Matcher m = StringUtil.createMatcher(originalString,reg);
			  if(m.find()){
				  //获得参数对象,格式  {name: 'Applicant.CAppNme',newValue: '张三',bakValue: '',value: ''}
				  String oldParamObject = m.group(0);
				  //前台传入的参数值,组装成格式  newValue: '新值'
				  String newParamValue = "newValue:'"+postParams.get(paramName)+"'";
				  //替换原来参数 {name: 'Applicant.CAppNme',newValue: '新值',bakValue: '',value: ''}
				  String newParamObject = oldParamObject.replaceFirst("(newValue:\\s?')([^']+)(')",newParamValue);
				  originalString = originalString.replace(oldParamObject, newParamObject);
			  }
		  }
		  return originalString;
	}
	/**
	 * @Description: TODO(替换报文参数值,cvrg部分)
	 * @param dwDataTemplate 参数模板，模板含有$prodDef.vhl.Cvrg_DW字符，便于定位替换
	 * @param cvrgDwObjstr 替换$prodDef.vhl.Cvrg_DW的字符
	 * @param cvrg119 不计免赔率字符，当险种里有一项购买了不计免赔，报文需含此字符
	 * @param insItems 投保险种明细
	 * @return 替换后的报文
	 * @author yejie.huang
	 * @date 2016年10月13日 上午10:53:57
	 */
	public static String replaceCrvg(String dwDataTemplate,String cvrgDwObjstr,String cvrg119,List<CarQuoteInsItemVo> insItems){
		int indexNo=0;
		boolean flag=true;
		StringBuffer sb = new StringBuffer();
		for(CarQuoteInsItemVo insItem : insItems){
			
			/**
			 * 当险种里有一项购买了不计免赔，加入此字符
			 */
			if(flag&&insItem.getDeductibleFlag()==1){
				sb.append(cvrg119).append(",");
				flag = false;
			}
			
			/**
			 * 替换insItems值，险别代码、保额、不计免赔特约险购买标志
			 */
			indexNo+=1;
			String cvrg = cvrgDwObjstr.replace("index:'1'", "index:'"+indexNo+"'")
					 .replaceAll("\\{[^\\{]*'Cvrg\\.NSeqNo'[^\\}]*\\}", "{name:'Cvrg.NSeqNo',newValue:'"+indexNo+"',bakValue:'',value:''}")
					 .replaceAll("\\{[^\\{]*'Cvrg\\.CCvrgNo'[^\\}]*\\}", "{name:'Cvrg.CCvrgNo',newValue:'"+insItem.getKindCode()+"',bakValue:'',value:''}")//险别代码
			         .replaceAll("\\{[^\\{]*'Cvrg\\.NAmt'[^\\}]*\\}", "{name:'Cvrg.NAmt',newValue:'"+insItem.getInsuredAmount()+"',bakValue:'',value:''}")//险别保额/限额
			         .replaceAll("\\{[^\\{]*'Cvrg\\.CDductMrk'[^\\}]*\\}", "{name:'Cvrg.CDductMrk',newValue:'"+insItem.getDeductibleFlag()+"',bakValue:'',value:''}");//不计免赔特约险购买标志
		   sb.append(cvrg).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		String newDwData = dwDataTemplate.replace("$prodDef.vhl.Cvrg_DW",sb.toString());
		
		/**
		 * 交强替换
		 */
		if(10==1){
			String jqCvrg = TestConfig.getDwData().get("jqCvrg").toString();
			newDwData = newDwData.replace("$prodDef.vhl.CvrgJQ_DW",jqCvrg);
		}else{
			newDwData = newDwData.replace("$prodDef.vhl.CvrgJQ_DW","");
		}
		return newDwData;
	}
	
	public static List<DataObjVo> replaceCrvg1(String cvrgDwObjstr,String cvrg119,List<CarQuoteInsItemVo> insItems){
		DataObjVo objVo119 = JSON.parseObject(cvrg119, DataObjVo.class);
		
		List<DataObjVo> objVos = new ArrayList<DataObjVo>();
		int indexNo=0;
		boolean flag=true;
		for(CarQuoteInsItemVo insItem : insItems){
			
			/**
			 * 当险种里有一项购买了不计免赔
			 */
			if(flag&&insItem.getDeductibleFlag()==1){
				objVos.add(objVo119);
				flag = false;
			}
			
			indexNo+=1;
			DataObjVo objVo = JSON.parseObject(cvrgDwObjstr, DataObjVo.class);
			objVo.setIndex(indexNo);
			List<AttributeVo> attVos = objVo.getAttributeVoList();;
			for(AttributeVo attvo :attVos){
				if(attvo.getName().equals("Cvrg.NSeqNo")){
					attvo.setNewValue(String.valueOf(indexNo));//序号
				}else if(attvo.getName().equals("Cvrg.CCvrgNo")){
					attvo.setNewValue(insItem.getKindCode());//险别代码
				}else if(attvo.getName().equals("Cvrg.NAmt")){
					attvo.setNewValue(insItem.getInsuredAmount());//险别保额/限额
				}else if(attvo.getName().equals("Cvrg.CDductMrk")){
					attvo.setNewValue(String.valueOf(insItem.getDeductibleFlag()));//不计免赔特约险购买标志
				}
			}
			objVos.add(objVo);
		}
		System.out.println("000"+JSON.toJSONString(objVos));
		return objVos;
	}
	
	public static CarQuoteReq getReqInfo(){
		CarQuoteReq req = new CarQuoteReq();
		req.setCompanyType("");
		//req.setCompanyTypes("");//保险公司类型
		//req.setInsurerIds("");//保险公司编号数组
		req.setLicenseFlag(0);//牌照标志
		req.setIsRenewal(0);//是否续保
		req.setStartDateBI("2016-11-09 00:00:00");//商业险起保日期
		req.setEndDateBI("2017-10-08 23:59:59");//商业险终保日期
		req.setStartDateCI("2016-10-14 00:00:00");//交强险起保日期  --重要，影响结算结果
		req.setEndDateCI("2017-10-13 23:59:59");//交强险终保日期
		req.setRunArea("389001");//行驶区域代码
		//req.setFileList("")//附件列表
		
		//车型信息
		CarModelInfoVo modelInfo = new CarModelInfoVo();
		req.setCarModelInfo(modelInfo);
		
		
		//投保险种明细
		List<CarQuoteInsItemVo> itemList = new ArrayList<CarQuoteInsItemVo>();
		CarQuoteInsItemVo insItem = new CarQuoteInsItemVo();
		req.setCarQuoteInsItemList(itemList);
		itemList.add(insItem);
		insItem.setKindCode("030101");//险别代码
		insItem.setInsuredAmount("191000");//每标的保额/限额
		insItem.setDeductibleFlag(1);//不计免赔特约险购买标志
		
		//全车盗抢险
	/*	CarQuoteInsItemVo insItem1 = new CarQuoteInsItemVo();
		itemList.add(insItem1);
		insItem1.setKindCode("030103");//险别代码
		insItem1.setInsuredAmount("191000");//每标的保额/限额
		insItem1.setDeductibleFlag(0);//不计免赔特约险购买标志
*/		
		/*CarQuoteInsItemVo insItem2 = new CarQuoteInsItemVo();
		itemList.add(insItem2);
		insItem2.setKindCode("030108");//险别代码
		insItem2.setInsuredAmount("191000");//每标的保额/限额
		insItem2.setDeductibleFlag(0);//不计免赔特约险购买标志
*/		
		/*CarQuoteInsItemVo insItem3 = new CarQuoteInsItemVo();
		itemList.add(insItem3);
		insItem3.setKindCode("030107");//险别代码
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
		
		//车主基本信息
		CarOwnerInfoVo ownerInfo = new CarOwnerInfoVo();
		req.setCarOwnerInfo(ownerInfo);
		ownerInfo.setCarOwner("张三");//车主姓名
		ownerInfo.setOwnerIdType("120001");//车主证件类型
		ownerInfo.setOwnerIdentifyNumber("450722198910122856");//车主证件号码
		ownerInfo.setOwnerPhone("15296281584");//车主手机号码
		ownerInfo.setOwnerAddr("广东深圳");//车主详细地址信息
		
		return req;
	}

}
