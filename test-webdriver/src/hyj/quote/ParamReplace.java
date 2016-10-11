/**
 * 
 */
package hyj.quote;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import huaan.quote.util.StringUtil;

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
		postParams.put("Vhlowner.COwnerNme","张三");//姓名
		postParams.put("Vhlowner.CUnitAttrib","377001");//单位性质
		postParams.put("Vhlowner.COwnerCls","376001");//车主类别
		postParams.put("Vhlowner.CCertfCls","120001");//证件类型
		postParams.put("Vhlowner.CCertfCde","450722198910122856");//证件号码
		postParams.put("Vhlowner.CTel","15296281584");//电话
		postParams.put("Vhlowner.CClntAddress","广东深圳");//地址
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
		postParams.put("Vhl.CFrmNo","LVGBF53K0EG104355");//车架号
		postParams.put("Vhl.CEngNo","H357719");//发动机号
		postParams.put("Vhl.CPlateNo","粤BX13G2");//号牌号码
		postParams.put("Vhl.CPlateTyp","01");//号牌种类
		postParams.put("Vhl.CPlateColor","1");//车牌颜色
		postParams.put("Vhl.CBrandId","KMD1078GZF");//车型代码
		postParams.put("Vhl.CModelCde","GTM7251GB");//
		postParams.put("Vhl.CModelNme","丰田GTM7251GB轿车");//()
		postParams.put("Vhl.NSeatNum","5");//核定载客数
		postParams.put("Vhl.NDisplacement","2.494");//排量
		postParams.put("Vhl.CUsageCde","309003");//使用性质
		postParams.put("Vhl.CUseAtr3","343002");//所属性质
		postParams.put("Vhl.CCarAtr","384001");//经营属性
		postParams.put("Vhl.CVhlSubTyp","308003");//车辆种类
		postParams.put("Vhl.CVhlTyp","");//车辆类型
		postParams.put("Vhl.CRegVhlTyp","K32");//车辆类型描述
		postParams.put("Vhl.CIsTipperFlag","0");//是否自卸车
		postParams.put("Vhl.CFstRegYm","2016-10-08");//注册日期
		postParams.put("Vhl.NNewPurchaseValue","1191000");//新车购置价
		postParams.put("Vhl.NActualValue","191000");//实际价值
		postParams.put("Vhl.CIndustryModelCode","BGQGKNUD0006");//行业车型编码
		postParams.put("Vhl.CFleetMrk","0");//车队标志
		postParams.put("Vhl.CLoanVehicleFlag","1");//车贷投保多年
		postParams.put("Vhl.CDevice1Mrk","0");//是否过户投保
		postParams.put("Vhl.CYl4","303011002");//玻璃类型
		postParams.put("Vhl.CProdPlace","1");//车辆产地
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
		postParams.put("SY_PrmCoef.CRunArea","389001");//行驶区域
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
		postParams.put("Timmer.JTInsrncBgnTm","2016-10-09 00:00:00");//保险起止期
		postParams.put("Timmer.JTInsrncEndTm","2017-10-08 23:59:59");//
		postParams.put("Timmer.JTotalDays","365");//共
		postParams.put("Timmer.JNRatioCoef","1.0");//短期费率系数
		postParams.put("Timmer.BTInsrncBgnTm","2016-11-09 00:00:00");//保险起止期
		postParams.put("Timmer.BTInsrncEndTm","2017-10-08 23:59:59");//
		postParams.put("Timmer.BTotalDays","365");//共
		postParams.put("Timmer.BNRatioCoef","1.0");//短期费率系数
		postParams.put("Timmer.TAppTm","2016-11-08");//投保日期
		postParams.put("Timmer.TOprTm","2016-10-08");//录单日期
		postParams.put("Timmer.TIssueTm","2016-10-08");//签单日期
		postParams.put("Pay.NTms","1");//
		postParams.put("JQ_Pay.NPayablePrm","0");//
		postParams.put("SY_Pay.NPayablePrm","0");//

        String newString = replacedParam(originalDwData, postParams);
        return newString;
	}
	/**
	 * @Description: TODO(替换报文参数值)
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

}
