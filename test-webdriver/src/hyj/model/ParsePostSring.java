/**
 * 
 */
package hyj.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: ParsePostSring
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年9月23日 上午10:18:19
 *
 */

public class ParsePostSring {
	/**
	 * @Description: TODO(获取替换参数的报文)
	 * @param originalDwData原始报文
	 * @return替换参数的报文
	 * @date 2016年9月27日 上午10:01:44
	 */
	public static String getNewDwData(String originalDwData){
		Map<String,String> postParams = new HashMap<String,String>();
	/*	//渠道信息
		postParams.put("Base.CSlsCde", "101154139");//业务员
		
		//基本信息
		postParams.put("Base.CBsnsSubtyp", "199001");//业务类型
		postParams.put("Base.OprtLvl", "198001");//业务等级
		postParams.put("Base.CFinTyp", "0");//缴费方式
		postParams.put("Base.CKpType", "02");//开票种类
		
		//客户信息
		postParams.put("Vhlowner.COwnerNme", "张扬");//姓名/名称
		postParams.put("Vhlowner.CUnitAttrib", "377001");//单位性质
		postParams.put("Vhlowner.COwnerCls", "376002");//车主类别
		postParams.put("Vhlowner.CCertfCls", "120001");//证件类型
		postParams.put("Vhlowner.CCertfCde", "450722198910122856");//证件号码
		postParams.put("Vhlowner.CTel", "15296281584");//电话
		postParams.put("Vhlowner.CClntAddress", "广西南宁");//地址
		postParams.put("Vhlowner.CDrvSex", "106001");//车主性别
		postParams.put("Vhlowner.NDrvownerAge", "199001");//车主年龄
		
		//车辆信息
		postParams.put("Vhl.CFrmNo", "LVGBF53K0EG104355");//1、	车架号
		postParams.put("Vhl.CEngNo", "H357719");//2、	发动机号
		postParams.put("Vhl.CPlateNo", "B946FX");//3、	号牌号码
		postParams.put("Vhl.CPlateTyp", "01");//4、	号牌种类
		postParams.put("Vhl.CBrandId", "KMD1102GZF");//5、	厂牌车型
		postParams.put("Vhl.NSeatNum", "5");//6、	核定载客数
		postParams.put("Vhl.NDisplacement", "2.494");//7、	排量
		postParams.put("Vhl.CUsageCde", "309006");//8、	使用性质
		postParams.put("Vhl.CUseAtr3", "343004");//9、	所属性质
		postParams.put("Vhl.CVhlTyp", "344001");//10、	车辆类型
		postParams.put("Vhl.CFstRegYm", "2016-09-22");//11、	注册日期
		postParams.put("Vhl.CLoanVehicleFlag", "0");//12、	车辆投保多年
		postParams.put("Vhl.CDevice1Mrk", "0");//13、	是否过户投保
		postParams.put("Vhl.CProdPlace", "0");//14、	车辆产地
		postParams.put("Vhl.CInqType", "645001");//15、	折旧率（车型）
		postParams.put("Vhl.NDespRate", "386001");//16、	折旧率（率）
		postParams.put("Vhl.CInspectorCde", "305005002");//17、	验车情况
		postParams.put("Vhl.CInspectorNme", "101002422");//18、	验车人
		postParams.put("Vhl.CInspectTm", "2016-09-21");//19、	验车时间
		postParams.put("Vhl.CCheckResult", "305012006");//20、	已验车
		postParams.put("${Vhl.CInspectRec}", "的");//21、	验车记录
*/		
		//自动生成
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
		postParams.put("Base.CReqType","C");//
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
		postParams.put("Vhlowner.NDrvownerAge","26");//车主年龄
		postParams.put("Insured.NSeqNo","1");//客户编号
		postParams.put("Insured.CInsuredNme","");//姓名
		postParams.put("Insured.CInsuredCde","032814984");//被保险人编码
		postParams.put("Insured.CVhlInsuredRel","378001");//类别
		postParams.put("Insured.CCertfCls","120001");//证件类型
		postParams.put("Insured.CCertfCde","");//证件号码
		postParams.put("Insured.CTel","15296281584");//电话
		postParams.put("Insured.CClntAddr","");//地址
		postParams.put("Insured.CSex","");//性别
		postParams.put("Insured.NAge","26");//年龄
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
		postParams.put("Vhl.CRegVhlTyp","K11");//车辆类型描述
		postParams.put("Vhl.CIsTipperFlag","0");//是否自卸车
		postParams.put("Vhl.CFstRegYm","2016-09-24");//注册日期
		postParams.put("Vhl.NNewPurchaseValue","191000");//新车购置价
		postParams.put("Vhl.NActualValue","191000");//实际价值
		postParams.put("Vhl.CIndustryModelCode","BGQGKNUD0006");//行业车型编码
		postParams.put("Vhl.CFleetMrk","0");//车队标志
		postParams.put("Vhl.CLoanVehicleFlag","0");//车贷投保多年
		postParams.put("Vhl.CDevice1Mrk","0");//是否过户投保
		postParams.put("Vhl.CYl4","303011001");//玻璃类型
		postParams.put("Vhl.CYl4","303011001");//
		postParams.put("Vhl.CProdPlace","0");//车辆产地
		postParams.put("Vhl.CAmtType","943001");//保额确定类型
		postParams.put("Vhl.CInqType","645001");//折旧率
		postParams.put("Vhl.NDespRate","386004");//(
		postParams.put("Vhl.CInspectorCde","305005002");//验车情况
		postParams.put("Vhl.CInspectorNme","101001222");//验车人
		postParams.put("Vhl.CInspectTm","2016-09-24");//验车时间
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
		postParams.put("SY_PrmCoef.NTgtFld1","1.0");//约定驾驶员:系数
		postParams.put("SY_PrmCoef.NTgtFld2","1.0");//驾驶人年龄系数
		postParams.put("SY_PrmCoef.NTgtFld3","1.0");//驾驶人性别:系数
		postParams.put("SY_PrmCoef.NTgtFld4","1.0");//驾驶人驾龄:系数
		postParams.put("SY_PrmCoef.NTgtFld23","1.0");//承保数量:系数
		postParams.put("SY_PrmCoef.CRunArea","389001");//行驶区域
		postParams.put("SY_PrmCoef.NTgtFld5","1.0");//行驶区域:系数
		postParams.put("SY_PrmCoef.NTgtFld10","1.0");//平均年行驶里程(公里):系数
		postParams.put("SY_PrmCoef.NTgtFld7","1.0");//投保年度:系数
		postParams.put("SY_PrmCoef.NTgtFld14","1.0");//以往保险年度索赔记录:系数
		postParams.put("SY_PrmCoef.NTgtFld15","1.0");//特殊风险车型:系数
		postParams.put("SY_PrmCoef.NTgtFld18","1.0");//车损险绝对免赔额:系数
		postParams.put("SY_PrmCoef.NTgtFld11","0.00");//减免保费
		postParams.put("SY_PrmCoef.CLossRatio","1.0");//经验/预期赔付范围:系数
		postParams.put("SY_PrmCoef.CMtcMng","1.0");//车队管理系数
		postParams.put("SY_PrmCoef.NTgtFld25","1.0");//广州金交会专用系数
		postParams.put("SY_PrmCoef.NTgtFld19","1.00");//平均年行驶里程系数(北京)
		postParams.put("SY_PrmCoef.NTgtFld17","0");//停驶天数（北京专用）
		postParams.put("SY_PrmCoef.NTgtFld22","1.00");//交通违法记录系数(深圳)
		postParams.put("SY_PrmCoef.NTgtFld21","1.00");//赔款记录系数(深圳)
		postParams.put("SY_PrmCoef.NMulRdr","1.0");//多险别投保优惠系数
		postParams.put("SY_PrmCoef.NTgtFld13","1.00");//选用系数合计
		postParams.put("Timmer.COprNm","游嘉琦");//录单人
		postParams.put("Timmer.COprCde","101154139");//
		postParams.put("Timmer.JTInsrncBgnTm","2016-10-27 00:00:00");//保险起止期
		postParams.put("Timmer.JTInsrncEndTm","2017-09-24 23:59:59");//
		postParams.put("Timmer.JTotalDays","365");//共
		postParams.put("Timmer.JNRatioCoef","1.0");//短期费率系数
		postParams.put("Timmer.BTInsrncBgnTm","2016-10-27 00:00:00");//保险起止期
		postParams.put("Timmer.BTInsrncEndTm","2017-09-24 23:59:59");//
		postParams.put("Timmer.BTotalDays","365");//共
		postParams.put("Timmer.BNRatioCoef","1.0");//短期费率系数
		postParams.put("Timmer.TAppTm","2016-09-24");//投保日期
		postParams.put("Timmer.TOprTm","2016-09-24");//录单日期
		postParams.put("Timmer.TIssueTm","2016-09-24");//签单日期
		postParams.put("Pay.NTms","1");//
		postParams.put("JQ_Pay.NPayablePrm","0");//
		postParams.put("SY_Pay.NPayablePrm","0");//

	    // String originalString = "[{isFilter:'false',dwType:'ONLY_DATA',dwName:'prodDef.vhl.Applicant_DW',rsCount:'1',pageSize:'10',pageNo:'1',pageCount:'0',maxCount:'1000',toAddFlag:'false',filterMapList:[],dataObjVoList:[{index:'1',selected:'true',status:'INSERTED',attributeVoList:[{name:'Applicant.CAppNo',newValue:'',bakValue:'',value:''},{name:'Applicant.CCrtCde',newValue:'',bakValue:'',value:''},{name:'Applicant.TCrtTm',newValue:'',bakValue:'',value:''},{name:'Applicant.CUpdCde',newValue:'',bakValue:'',value:''},{name:'Applicant.TUpdTm',newValue:'',bakValue:'',value:''},{name:'Applicant.CMachineCde',newValue:'',bakValue:'',value:''},{name:'Applicant.CNation',newValue:'',bakValue:'',value:''},{name:'Applicant.CBirthDate',newValue:'',bakValue:'',value:''},{name:'Applicant.CIssuer',newValue:'',bakValue:'',value:''},{name:'Applicant.CCertiStartDate',newValue:'',bakValue:'',value:''},{name:'Applicant.CCertiEndDate',newValue:'',bakValue:'',value:''},{name:'Applicant.CAppNme',newValue:'张三',bakValue:'',value:''},{name:'Applicant.CAppCde',newValue:'032814984',bakValue:'',value:''},{name:'Applicant.CTfiAppTyp',newValue:'378001',bakValue:'378001',value:''},{name:'Applicant.CCertfCls',newValue:'120001',bakValue:'',value:''},{name:'Applicant.CCertfCde',newValue:'450722198910122856',bakValue:'450722198910122856',value:'450722198910122856'},{name:'Applicant.CCountry',newValue:'193001',bakValue:'',value:''},{name:'Applicant.CTel',newValue:'15296281584',bakValue:'15296281584',value:'15296281584'},{name:'Applicant.CAppEmail',newValue:'',bakValue:'',value:''},{name:'Applicant.CClntAddr',newValue:'广东深圳',bakValue:'广东深圳',value:'广东深圳'},{name:'Applicant.CZipCde',newValue:'',bakValue:'',value:''},{name:'Applicant.CSex',newValue:'',bakValue:'',value:''}]}]},{isFilter:'false',dwType:'ONLY_DATA',dwName:'prodDef.vhl.Base_DW',rsCount:'1',pageSize:'10',pageNo:'1',pageCount:'0',maxCount:'1000',toAddFlag:'false',filterMapList:[],dataObjVoList:[{index:'1',selected:'true',status:'INSERTED',attributeVoList:[{name:'Base.C_JOIN_NO',newValue:'',bakValue:'',value:''},{name:'Base.C_PLY_NO',newValue:'',bakValue:'',value:''},{name:'Base.COrigType',newValue:'0',bakValue:'',value:''},{name:'JQ_Base.CRenewMrk',newValue:'0',bakValue:'',value:''},{name:'JQ_Base.COrigPlyNo',newValue:'',bakValue:'',value:''},{name:'JQ_Base.COrigNum',newValue:'',bakValue:'',value:''},{name:'SY_Base.CRenewMrk',newValue:'0',bakValue:'',value:''},{name:'SY_Base.COrigPlyNo',newValue:'',bakValue:'',value:''},{name:'SY_Base.COrigNum',newValue:'',bakValue:'',value:''},{name:'JQ_Base.CPlyImageId',newValue:'',bakValue:'',value:''},{name:'SY_Base.CPlyImageId',newValue:'',bakValue:'',value:''},{name:'JQ_Base.CTmpNo',newValue:'',bakValue:'',value:''},{name:'SY_Base.CTmpNo',newValue:'',bakValue:'',value:''},{name:'Base.CProdNo',newValue:'0380',bakValue:'',value:''},{name:'Base.CBsnsNew',newValue:'',bakValue:'',value:''},{name:'JQ_Base.NAmt',newValue:'122000.00',bakValue:'',value:''},{name:'JQ_Base.NPrm',newValue:'0.00',bakValue:'',value:''},{name:'JQ_Base.NPrmTax',newValue:'0.00',bakValue:'',value:''},{name:'JQ_Base.NTax',newValue:'0.00',bakValue:'',value:''},{name:'SY_Base.NAmt',newValue:'0',bakValue:'0.00',value:''},{name:'SY_Base.NPrm',newValue:'0',bakValue:'0.00',value:''},{name:'SY_Base.NPrmTax',newValue:'0.00',bakValue:'',value:''},{name:'SY_Base.NTax',newValue:'0.00',bakValue:'',value:''},{name:'SY_Base.SpecialAgreement',newValue:'',bakValue:'',value:''},{name:'JQ_Base.SpecialAgreement',newValue:'',bakValue:'',value:''},{name:'Base.CAmtCur',newValue:'01',bakValue:'',value:''},{name:'Base.CPrmCur',newValue:'01',bakValue:'',value:''},{name:'Base.CAppTyp',newValue:'',bakValue:'',value:''},{name:'Base.CCrtCde',newValue:'',bakValue:'',value:''},{name:'Base.TCrtTm',newValue:'',bakValue:'',value:''},{name:'Base.CUpdCde',newValue:'',bakValue:'',value:''},{name:'Base.TUpdTm',newValue:'',bakValue:'',value:''},{name:'Base.CUdrCde',newValue:'',bakValue:'',value:''},{name:'Base.CReqType',newValue:'C',bakValue:'C',value:''},{name:'Base.CForceUndrMark',newValue:'',bakValue:'',value:''},{name:'SY_Base.CILogUndrMark',newValue:'',bakValue:'',value:''},{name:'JQ_Base.CILogUndrMark',newValue:'',bakValue:'',value:''},{name:'Base.CUdrDptCde',newValue:'',bakValue:'',value:''},{name:'Base.CCommonFlag',newValue:'0',bakValue:'',value:''},{name:'Base.CPlyImageid',newValue:'0',bakValue:'',value:''},{name:'Base.CEdrImageid',newValue:'0',bakValue:'',value:''},{name:'Base.NAgtLmt',newValue:'',bakValue:'',value:''},{name:'Base.CScoverageFlg',newValue:'1',bakValue:'',value:''},{name:'Base.TelewrtSalegrp',newValue:'',bakValue:'',value:''},{name:'JQ_Base.CAppType',newValue:'',bakValue:'',value:''},{name:'SY_Base.CAppType',newValue:'',bakValue:'',value:''},{name:'JQ_Base.NExtendFee',newValue:'',bakValue:'',value:''},{name:'SY_Base.NExtendFee',newValue:'',bakValue:'',value:''},{name:'Base.CDptCde',newValue:'01980003',bakValue:'',value:''},{name:'Base.CBsnsTyp',newValue:'19002',bakValue:'',value:''},{name:'Base.CBrkrCde',newValue:'0101062050',bakValue:'',value:''},{name:'Base.CSlsCde',newValue:'101154139',bakValue:'',value:''},{name:'JQ_Base.NCommRate',newValue:'0.04',bakValue:'0.00',value:''},{name:'SY_Base.NCommRate',newValue:'0.4982',bakValue:'0.00',value:''},{name:'Base.UsbKey',newValue:'',bakValue:'',value:''},{name:'Base.CChaNme',newValue:'',bakValue:'',value:''},{name:'Base.CLicense',newValue:'203136000000800',bakValue:'',value:''},{name:'Base.CQualification',newValue:'',bakValue:'',value:''},{name:'Base.ApproveCode',newValue:'',bakValue:'',value:''},{name:'Base.VisitService',newValue:'',bakValue:'',value:''},{name:'Base.SlsPer2',newValue:'',bakValue:'',value:''},{name:'Base.CSalesTel',newValue:'',bakValue:'',value:''},{name:'Base.CSlsPer',newValue:'',bakValue:'',value:''},{name:'Base.WorkCertif',newValue:'',bakValue:'',value:''},{name:'JQ_Base.NPerformance',newValue:'0.00',bakValue:'',value:''},{name:'SY_Base.NPerformance',newValue:'0.00',bakValue:'',value:''},{name:'Base.CAgtAgrNo',newValue:'B01201600012_0',bakValue:'B01201600012_0',value:''},{name:'Base.CSalegrpCde',newValue:'0198000301',bakValue:'',value:''},{name:'Base.CTrueAgtCde',newValue:'0101062050',bakValue:'',value:''},{name:'Base.HandPer',newValue:'',bakValue:'',value:''},{name:'Base.RelatTel',newValue:'',bakValue:'',value:''},{name:'Base.CExsaleName',newValue:'',bakValue:'',value:''},{name:'Base.ComputerIp',newValue:'',bakValue:'',value:''},{name:'',newValue:'',bakValue:'',value:''},{name:'Base.Qdlx',newValue:'',bakValue:'',value:''},{name:'Base.CApproScl',newValue:'',bakValue:'',value:''},{name:'Base.ApproveName',newValue:'',bakValue:'',value:''},{name:'Base.CJsFlag',newValue:'0',bakValue:'0',value:''},{name:'Base.CBsnsSubtyp',newValue:'199001',bakValue:'',value:''},{name:'Base.OprtLvl',newValue:'198001',bakValue:'',value:''},{name:'Base.CFinTyp',newValue:'0',bakValue:'',value:''},{name:'Base.CWorkDpt',newValue:'377001',bakValue:'',value:''},{name:'Base.CCusTyp',newValue:'927002',bakValue:'927001',value:'927001'},{name:'Base.CVipMrk',newValue:'0',bakValue:'',value:''},{name:'Base.CInviteTitle',newValue:'699003',bakValue:'',value:''},{name:'Base.BuisType',newValue:'925006',bakValue:'',value:''},{name:'Base.CDisptSttlCde',newValue:'007001',bakValue:'',value:''},{name:'Base.CAgriMrk',newValue:'0',bakValue:'',value:''},{name:'Base.CFinanceYwtyp',newValue:'',bakValue:'',value:''},{name:'Base.CDisptSttlOrg',newValue:'',bakValue:'',value:''},{name:'Base.CNyqz',newValue:'',bakValue:'',value:''},{name:'JQ_Base.IsImage',newValue:'0',bakValue:'',value:''},{name:'JQ_Base.ImageState',newValue:'',bakValue:'',value:''},{name:'SY_Base.IsImage',newValue:'0',bakValue:'',value:''},{name:'SY_Base.ImageState',newValue:'',bakValue:'',value:''},{name:'Base.XqfxLvl',newValue:'928001',bakValue:'',value:''},{name:'Base.CRelPlyNo',newValue:'',bakValue:'',value:''},{name:'Base.CGroupPly',newValue:'',bakValue:'',value:''},{name:'Base.CKpType',newValue:'02',bakValue:'',value:''},{name:'Base.CAppBankName',newValue:'',bakValue:'',value:''},{name:'Base.CAppAcctNo',newValue:'',bakValue:'',value:''},{name:'Base.COprTyp',newValue:'0',bakValue:'',value:''},{name:'JQ_Base.CPlyNo',newValue:'',bakValue:'',value:''},{name:'SY_Base.CPlyNo',newValue:'',bakValue:'',value:''},{name:'Base.CRemark',newValue:'',bakValue:'',value:''},{name:'JQ_Base.CAppNo',newValue:'',bakValue:'',value:''},{name:'SY_Base.CAppNo',newValue:'',bakValue:'',value:''},{name:'Base.CResvTxt2',newValue:'0',bakValue:'',value:''},{name:'Base.CPrnNo',newValue:'',bakValue:'',value:''},{name:'Base.CRiFacMrk',newValue:'',bakValue:'',value:''},{name:'Base.CImgId',newValue:'',bakValue:'',value:''},{name:'Base.IsNofee',newValue:'0',bakValue:'',value:''},{name:'Base.NofeeReason',newValue:'',bakValue:'',value:''},{name:'JQ_Base.IcPrimary',newValue:'',bakValue:'',value:''},{name:'SY_Base.IcPrimary',newValue:'',bakValue:'',value:''},{name:'Base.FsywFlag',newValue:'',bakValue:'',value:''},{name:'Base.CFeeFlag',newValue:'1',bakValue:'',value:''},{name:'Base.CBizCde',newValue:'',bakValue:'',value:''},{name:'Base.CBizNo',newValue:'',bakValue:'',value:''},{name:'Base.CCustType',newValue:'',bakValue:'',value:''},{name:'Base.CRiskLvlCde',newValue:'',bakValue:'',value:''},{name:'Base.CFinconfirm_Flag',newValue:'',bakValue:'',value:''},{name:'SY_Base.CConnectFlag',newValue:'',bakValue:'',value:''},{name:'JQ_Base.CConnectFlag',newValue:'',bakValue:'',value:''},{name:'Base.CInwdMrk',newValue:'',bakValue:'',value:''},{name:'Base.CSlsTel',newValue:'',bakValue:'',value:''},{name:'Base.CBsnsAgent',newValue:'',bakValue:'',value:''},{name:'Base.CFinanceDpt',newValue:'',bakValue:'',value:''},{name:'Insured.CInsuredNme2',newValue:'',bakValue:'',value:''},{name:'Insured.type',newValue:'',bakValue:'',value:''},{name:'Insured.CCertfCde2',newValue:'',bakValue:'',value:''},{name:'Insured.CClntAddr2',newValue:'',bakValue:'',value:''},{name:'Insured.CSex2',newValue:'',bakValue:'',value:''},{name:'Insured.CIssuer2',newValue:'',bakValue:'',value:''},{name:'Insured.CNation2',newValue:'',bakValue:'',value:''},{name:'Insured.CCertiStartDate2',newValue:'',bakValue:'',value:''},{name:'Insured.CCertiEndDate2',newValue:'',bakValue:'',value:''}]}]},{isFilter:'false',dwType:'ONLY_DATA',dwName:'prodDef.vhl.Vhl_DW',rsCount:'1',pageSize:'10',pageNo:'1',pageCount:'0',maxCount:'1000',toAddFlag:'false',filterMapList:[],dataObjVoList:[{index:'1',selected:'true',status:'INSERTED',attributeVoList:[{name:'Vhl.CCrtCde',newValue:'',bakValue:'',value:''},{name:'Vhl.TCrtTm',newValue:'',bakValue:'',value:''},{name:'Vhl.CUpdCde',newValue:'',bakValue:'',value:''},{name:'Vhl.TUpdTm',newValue:'',bakValue:'',value:''},{name:'bigCFrmNo',newValue:'',bakValue:'',value:''},{name:'Vhl.CFrmNo',newValue:'LVGBF53K0EG104355',bakValue:'LVGBF53K0EG104355',value:'LVGBF53K0EG104355'},{name:'Vhl.CEngNo',newValue:'H357719',bakValue:'',value:''},{name:'JQ_Vhl.CFuelType',newValue:'',bakValue:'',value:''},{name:'Vhl.CPlateNo',newValue:'粤BX13G2',bakValue:'',value:'粤B'},{name:'Vhl.CPlateTyp',newValue:'01',bakValue:'',value:''},{name:'Vhl.CPlateColor',newValue:'1',bakValue:'0',value:''},{name:'Vhl.CBrandId',newValue:'KMD1078GZF',bakValue:'',value:''},{name:'Vhl.VehicleId',newValue:'',bakValue:'',value:''},{name:'Vhl.CModelCde',newValue:'GTM7251GB',bakValue:'丰田GTM7251GB',value:''},{name:'Vhl.CModelNme',newValue:'丰田GTM7251GB轿车',bakValue:'',value:''},{name:'Vhl.CVhlTypeCde',newValue:'',bakValue:'',value:''},{name:'Vhl.NSeatNum',newValue:'5',bakValue:'',value:''},{name:'Vhl.NTonage',newValue:'',bakValue:'',value:''},{name:'Vhl.NDisplacement',newValue:'2.494',bakValue:'',value:''},{name:'Vhl.CUsageCde',newValue:'309003',bakValue:'',value:''},{name:'Vhl.CUseAtr3',newValue:'343002',bakValue:'',value:'343002'},{name:'Vhl.CCarAtr',newValue:'384001',bakValue:'',value:''},{name:'Vhl.CVhlSubTyp',newValue:'308003',bakValue:'',value:''},{name:'Vhl.CVhlTyp',newValue:'344001',bakValue:'',value:''},{name:'Vhl.CRegVhlTyp',newValue:'K11',bakValue:'',value:''},{name:'Vhl.CVhlStyle',newValue:'',bakValue:'',value:''},{name:'Vhl.CIsTipperFlag',newValue:'0',bakValue:'0',value:''},{name:'Vhl.CFstRegYm',newValue:'2016-09-24',bakValue:'',value:''},{name:'Vhl.NNewPurchaseValue',newValue:'191000',bakValue:'0.00',value:''},{name:'Vhl.NActualValue',newValue:'191000',bakValue:'191000',value:'191000'},{name:'Vhl.CBodyColor',newValue:'',bakValue:'',value:''},{name:'Vhl.CTfiSpecialMrk',newValue:'',bakValue:'',value:''},{name:'Vhl.CBrndPrintNme',newValue:'',bakValue:'',value:''},{name:'Vhl.CIndustryModelCode',newValue:'BGQGKNUD0006',bakValue:'',value:''},{name:'Vhl.CAptArea',newValue:'',bakValue:'',value:''},{name:'JQ_Vhl.CCertificateType',newValue:'',bakValue:'',value:''},{name:'JQ_Vhl.TCertificateDate',newValue:'',bakValue:'',value:''},{name:'JQ_Vhl.CCertificateNo',newValue:'',bakValue:'',value:''},{name:'JQ_Vhl.CYl5',newValue:'',bakValue:'',value:''},{name:'JQ_Vhl.NPowerRate',newValue:'',bakValue:'',value:''},{name:'Vhl.CFleetMrk',newValue:'0',bakValue:'',value:''},{name:'Vhl.CVhlPkgNO',newValue:'',bakValue:'',value:''},{name:'Vhl.CLoanVehicleFlag',newValue:'0',bakValue:'',value:''},{name:'Vhl.CDevice1Mrk',newValue:'0',bakValue:'',value:''},{name:'Vhl.TTransferTm',newValue:'',bakValue:'',value:''},{name:'Vhl.NYl3',newValue:'',bakValue:'',value:''},{name:'Vhl.CYl4',newValue:'303011001',bakValue:'303011001',value:''},{name:'Vhl.CProdPlace',newValue:'0',bakValue:'',value:''},{name:'Vhl.CFamilyKind',newValue:'',bakValue:'',value:''},{name:'Vhl.CAmtType',newValue:'943001',bakValue:'',value:''},{name:'Vhl.CInqType',newValue:'645001',bakValue:'',value:''},{name:'Vhl.NDespRate',newValue:'386004',bakValue:'',value:''},{name:'Vhl.CInspectorCde',newValue:'305005002',bakValue:'',value:''},{name:'Vhl.CInspectorNme',newValue:'101001222',bakValue:'',value:''},{name:'Vhl.CInspectTm',newValue:'2016-09-24',bakValue:'',value:''},{name:'Vhl.CCheckResult',newValue:'305012006',bakValue:'',value:''},{name:'Vhl.TResvTm2',newValue:'',bakValue:'',value:''},{name:'Vhl.CInspectRec',newValue:'的',bakValue:'',value:''},{name:'JQ_Vhl.CQryCde',newValue:'',bakValue:'',value:''},{name:'SY_Vhl.CQryCde',newValue:'',bakValue:'',value:''},{name:'Vhl.CCarYear',newValue:'2011-12',bakValue:'',value:''},{name:'Vhl.CBldYear',newValue:'',bakValue:'',value:''},{name:'Vhl.CNewVhlFlag',newValue:'0',bakValue:'0',value:'0'},{name:'Vhl.CNewEnergyFlag',newValue:'0',bakValue:'0',value:''},{name:'Vhl.CMfgYear',newValue:'',bakValue:'',value:''},{name:'Vhl.CIfhkcarFlag',newValue:'0',bakValue:'',value:''},{name:'Vhl.CEcdemicMrk',newValue:'0',bakValue:'',value:''},{name:'SY_Vhl.CYl5',newValue:'',bakValue:'',value:''},{name:'Vhl.CRemark',newValue:'手自一体 豪华版 国Ⅳ',bakValue:'',value:''},{name:'Vhl.NVhlTonage',newValue:'1490',bakValue:'',value:''},{name:'JQ_Vhl.CBookingCde',newValue:'',bakValue:'',value:''},{name:'JQ_Vhl.CAffirmCde',newValue:'',bakValue:'',value:''},{name:'SY_Vhl.CBookingCde',newValue:'',bakValue:'',value:''},{name:'SY_Vhl.CAffirmCde',newValue:'',bakValue:'',value:''},{name:'Vhl.NNodamageYears',newValue:'0',bakValue:'',value:''},{name:'Vhl.CFrmNoUnusualMrk',newValue:'0',bakValue:'',value:''},{name:'Vhl.CVhlQueryFla',newValue:'1',bakValue:'',value:''},{name:'SY_Vhl.CUseYear',newValue:'',bakValue:'',value:''},{name:'JQ_Vhl.CUseYear',newValue:'',bakValue:'',value:''},{name:'Vhl.CResvTxt11',newValue:'',bakValue:'',value:''},{name:'Vhl.CRiskFlag',newValue:'',bakValue:'',value:''},{name:'',newValue:'',bakValue:'',value:''}]}]},{isFilter:'false',dwType:'ONLY_DATA',dwName:'prodDef.vhl.Pay_DW',rsCount:'1',pageSize:'10',pageNo:'1',pageCount:'0',maxCount:'1000',toAddFlag:'false',filterMapList:[],dataObjVoList:[{index:'1',selected:'true',status:'INSERTED',attributeVoList:[{name:'SY_Pay.CPkId',newValue:'',bakValue:'',value:''},{name:'SY_Pay.CRowId',newValue:'',bakValue:'',value:''},{name:'JQ_Pay.CPkId',newValue:'',bakValue:'',value:''},{name:'JQ_Pay.CRowId',newValue:'',bakValue:'',value:''},{name:'Pay.CCrtCde',newValue:'',bakValue:'',value:''},{name:'Pay.TCrtTm',newValue:'',bakValue:'',value:''},{name:'Pay.CUpdCde',newValue:'',bakValue:'',value:''},{name:'Pay.TUpdTm',newValue:'',bakValue:'',value:''},{name:'SY_Pay.TUpdTm',newValue:'',bakValue:'',value:''},{name:'JQ_Pay.TUpdTm',newValue:'',bakValue:'',value:''},{name:'Pay.NTms',newValue:'1',bakValue:'',value:''},{name:'Pay.CPayorCde',newValue:'',bakValue:'',value:''},{name:'Pay.CProdNo',newValue:'',bakValue:'',value:''},{name:'Pay.CPayorNme',newValue:'',bakValue:'',value:''},{name:'JQ_Pay.NPayablePrm',newValue:'0',bakValue:'',value:''},{name:'SY_Pay.NPayablePrm',newValue:'0',bakValue:'',value:''},{name:'Pay.TPayBgnTm',newValue:'',bakValue:'',value:''},{name:'Pay.TPayEndTm',newValue:'',bakValue:'',value:''}]}]},{isFilter:'false',dwType:'ONLY_DATA',dwName:'prodDef.vhl.Insured_DW',rsCount:'1',pageSize:'10',pageNo:'1',pageCount:'0',maxCount:'1000',toAddFlag:'false',filterMapList:[],dataObjVoList:[{index:'1',selected:'true',status:'INSERTED',attributeVoList:[{name:'Insured.CPkId',newValue:'',bakValue:'',value:''},{name:'Insured.NSeqNo',newValue:'1',bakValue:'',value:''},{name:'Insured.CCrtCde',newValue:'',bakValue:'',value:''},{name:'Insured.TCrtTm',newValue:'',bakValue:'',value:''},{name:'Insured.CUpdCde',newValue:'',bakValue:'',value:''},{name:'Insured.TUpdTm',newValue:'',bakValue:'',value:''},{name:'Insured.CMachineCde',newValue:'',bakValue:'',value:''},{name:'Insured.CNation',newValue:'',bakValue:'',value:''},{name:'Insured.CBirthDate',newValue:'',bakValue:'',value:''},{name:'Insured.CIssuer',newValue:'',bakValue:'',value:''},{name:'Insured.CCertiStartDate',newValue:'',bakValue:'',value:''},{name:'Insured.CCertiEndDate',newValue:'',bakValue:'',value:''},{name:'Insured.CInsuredNme',newValue:'张三',bakValue:'',value:''},{name:'Insured.CInsuredCde',newValue:'032814984',bakValue:'',value:''},{name:'Insured.CVhlInsuredRel',newValue:'378001',bakValue:'378001',value:''},{name:'Insured.CInsureEmail',newValue:'',bakValue:'',value:''},{name:'Insured.CCertfCls',newValue:'120001',bakValue:'',value:''},{name:'Insured.CCertfCde',newValue:'450722198910122856',bakValue:'450722198910122856',value:'450722198910122856'},{name:'Insured.CTel',newValue:'15296281584',bakValue:'15296281584',value:'15296281584'},{name:'Insured.CClntAddr',newValue:'广东深圳',bakValue:'广东深圳',value:'广东深圳'},{name:'Insured.CZipCde',newValue:'',bakValue:'',value:''},{name:'Insured.CSex',newValue:'106001',bakValue:'106001',value:''},{name:'Insured.NAge',newValue:'26',bakValue:'26',value:''}]}]},{isFilter:'false',dwType:'ONLY_DATA',dwName:'prodDef.vhl.Vhlowner_DW',rsCount:'1',pageSize:'10',pageNo:'1',pageCount:'0',maxCount:'1000',toAddFlag:'false',filterMapList:[],dataObjVoList:[{index:'1',selected:'true',status:'INSERTED',attributeVoList:[{name:'Vhlowner.CAppCde',newValue:'',bakValue:'',value:''},{name:'Vhlowner.CCrtCde',newValue:'',bakValue:'',value:''},{name:'Vhlowner.TCrtTm',newValue:'',bakValue:'',value:''},{name:'Vhlowner.CUpdCde',newValue:'',bakValue:'',value:''},{name:'Vhlowner.TUpdTm',newValue:'',bakValue:'',value:''},{name:'Vhlowner.CMachineCde',newValue:'',bakValue:'',value:''},{name:'Vhlowner.CNation',newValue:'',bakValue:'',value:''},{name:'Vhlowner.CBirthDate',newValue:'',bakValue:'',value:''},{name:'Vhlowner.CIssuer',newValue:'',bakValue:'',value:''},{name:'Vhlowner.CCertiStartDate',newValue:'',bakValue:'',value:''},{name:'Vhlowner.CCertiEndDate',newValue:'',bakValue:'',value:''},{name:'Vhlowner.COwnerNme',newValue:'张三',bakValue:'',value:''},{name:'Vhlowner.CUnitAttrib',newValue:'377001',bakValue:'',value:''},{name:'Vhlowner.COwnerCls',newValue:'376001',bakValue:'376002',value:''},{name:'Vhlowner.CCertfCls',newValue:'120001',bakValue:'',value:''},{name:'Vhlowner.CCertfCde',newValue:'450722198910122856',bakValue:'',value:''},{name:'Vhlowner.CTel',newValue:'15296281584',bakValue:'',value:''},{name:'Vhlowner.CClntAddress',newValue:'广东深圳',bakValue:'',value:''},{name:'Vhlowner.CZipCode',newValue:'',bakValue:'',value:''},{name:'Vhlowner.CDrvSex',newValue:'106001',bakValue:'106001',value:''},{name:'Vhlowner.NDrvownerAge',newValue:'26',bakValue:'26',value:''},{name:'Vhlowner.NDrvAge',newValue:'',bakValue:'',value:''},{name:'Vhlowner.BusiContactName',newValue:'',bakValue:'',value:''},{name:'Vhlowner.BusiContactPhone',newValue:'',bakValue:'',value:''}]}]},{isFilter:'false',dwType:'GRID_EDIT',dwName:'prodDef.vhl.VhlDrv_DW',rsCount:'1',pageSize:'10',pageNo:'1',pageCount:'0',maxCount:'1000',toAddFlag:'false',filterMapList:[],dataObjVoList:[]},{isFilter:'false',dwType:'GRID_CVRG',dwName:'prodDef.vhl.Cvrg_DW',rsCount:'1',pageSize:'10',pageNo:'1',pageCount:'0',maxCount:'1000',toAddFlag:'false',filterMapList:[],dataObjVoList:[{index:'1',selected:'true',status:'INSERTED',attributeVoList:[{name:'Cvrg.CYl13',newValue:'0',bakValue:'',value:''},{name:'Cvrg.CYl10',newValue:'',bakValue:'',value:''},{name:'Cvrg.CYl4',newValue:'',bakValue:'',value:''},{name:'Cvrg.CYl8',newValue:'',bakValue:'',value:''},{name:'Cvrg.CYl9',newValue:'',bakValue:'',value:''},{name:'Cvrg.NYl11',newValue:'',bakValue:'',value:''},{name:'Cvrg.NYl3',newValue:'',bakValue:'',value:''},{name:'Cvrg.NYl12',newValue:'0',bakValue:'',value:''},{name:'Cvrg.NYl4',newValue:'',bakValue:'',value:''},{name:'Cvrg.NSeqNo',newValue:'1',bakValue:'',value:''},{name:'Cvrg.CPkId',newValue:'',bakValue:'',value:''},{name:'Cvrg.CCvrgNo',newValue:'030101',bakValue:'',value:''},{name:'Cvrg.NAmt',newValue:'191000',bakValue:'',value:''},{name:'Cvrg.CDductMrk',newValue:'1',bakValue:'',value:''},{name:'Cvrg.NBasePrm',newValue:'',bakValue:'',value:''},{name:'Cvrg.NPrm',newValue:'',bakValue:'',value:''},{name:'Cvrg.NPerAmt',newValue:'',bakValue:'',value:''},{name:'Cvrg.NLiabDaysLmt',newValue:'',bakValue:'',value:''},{name:'Cvrg.NIndemLmt',newValue:'',bakValue:'',value:''},{name:'Cvrg.NRate',newValue:'',bakValue:'',value:''},{name:'Cvrg.CRowId',newValue:'',bakValue:'',value:''},{name:'Cvrg.CCrtCde',newValue:'',bakValue:'',value:''},{name:'Cvrg.TCrtTm',newValue:'',bakValue:'',value:''},{name:'Cvrg.CUpdCde',newValue:'',bakValue:'',value:''},{name:'Cvrg.TUpdTm',newValue:'',bakValue:'',value:''},{name:'Cvrg.TBgnTm',newValue:'',bakValue:'',value:''},{name:'Cvrg.NDisCoef',newValue:'',bakValue:'',value:''},{name:'Cvrg.CResvTxt12',newValue:'',bakValue:'',value:''},{name:'Cvrg.CIndemLmtLvl',newValue:'',bakValue:'',value:''},{name:'Cvrg.NDductRate',newValue:'',bakValue:'',value:''},{name:'Cvrg.CBulletPrfGlass',newValue:'',bakValue:'',value:''},{name:'Cvrg.CYl15',newValue:'',bakValue:'',value:''},{name:'Cvrg.CRemark',newValue:'',bakValue:'',value:''}]},{index:'19',selected:'true',status:'INSERTED',attributeVoList:[{name:'Cvrg.CYl13',newValue:'',bakValue:'',value:''},{name:'Cvrg.CYl10',newValue:'',bakValue:'',value:''},{name:'Cvrg.CYl4',newValue:'',bakValue:'',value:''},{name:'Cvrg.CYl8',newValue:'',bakValue:'',value:''},{name:'Cvrg.CYl9',newValue:'',bakValue:'',value:''},{name:'Cvrg.NYl11',newValue:'',bakValue:'',value:''},{name:'Cvrg.NYl3',newValue:'',bakValue:'',value:''},{name:'Cvrg.NYl12',newValue:'',bakValue:'',value:''},{name:'Cvrg.NYl4',newValue:'',bakValue:'',value:''},{name:'Cvrg.NSeqNo',newValue:'19',bakValue:'',value:''},{name:'Cvrg.CPkId',newValue:'',bakValue:'',value:''},{name:'Cvrg.CCvrgNo',newValue:'030119',bakValue:'',value:''},{name:'Cvrg.NAmt',newValue:'',bakValue:'',value:''},{name:'Cvrg.CDductMrk',newValue:'',bakValue:'',value:''},{name:'Cvrg.NBasePrm',newValue:'',bakValue:'',value:''},{name:'Cvrg.NPrm',newValue:'0',bakValue:'',value:''},{name:'Cvrg.NPerAmt',newValue:'',bakValue:'',value:''},{name:'Cvrg.NLiabDaysLmt',newValue:'',bakValue:'',value:''},{name:'Cvrg.NIndemLmt',newValue:'',bakValue:'',value:''},{name:'Cvrg.NRate',newValue:'',bakValue:'',value:''},{name:'Cvrg.CRowId',newValue:'',bakValue:'',value:''},{name:'Cvrg.CCrtCde',newValue:'',bakValue:'',value:''},{name:'Cvrg.TCrtTm',newValue:'',bakValue:'',value:''},{name:'Cvrg.CUpdCde',newValue:'',bakValue:'',value:''},{name:'Cvrg.TUpdTm',newValue:'',bakValue:'',value:''},{name:'Cvrg.TBgnTm',newValue:'',bakValue:'',value:''},{name:'Cvrg.NDisCoef',newValue:'',bakValue:'',value:''},{name:'Cvrg.CResvTxt12',newValue:'',bakValue:'',value:''},{name:'Cvrg.CIndemLmtLvl',newValue:'',bakValue:'',value:''},{name:'Cvrg.NDductRate',newValue:'',bakValue:'',value:''},{name:'Cvrg.CBulletPrfGlass',newValue:'',bakValue:'',value:''},{name:'Cvrg.CYl15',newValue:'',bakValue:'',value:''},{name:'Cvrg.CRemark',newValue:'',bakValue:'',value:''}]}]},{isFilter:'false',dwType:'ONLY_DATA',dwName:'prodDef.vhl.PrmCoef_DW',rsCount:'1',pageSize:'10',pageNo:'1',pageCount:'0',maxCount:'1000',toAddFlag:'false',filterMapList:[],dataObjVoList:[{index:'1',selected:'true',status:'INSERTED',attributeVoList:[{name:'SY_PrmCoef.CAppNo',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.CPlyNo',newValue:'',bakValue:'',value:''},{name:'PrmCoef.CCrtCde',newValue:'',bakValue:'',value:''},{name:'PrmCoef.TCrtTm',newValue:'',bakValue:'',value:''},{name:'PrmCoef.CUpdCde',newValue:'',bakValue:'',value:''},{name:'PrmCoef.TUpdTm',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.CAppDrv',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld1',newValue:'1.0',bakValue:'1.0',value:'1.0'},{name:'SY_PrmCoef.CDrvAge',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld2',newValue:'1.0',bakValue:'1.0',value:'1.0'},{name:'SY_PrmCoef.CDrvSex',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld3',newValue:'1.0',bakValue:'1.0',value:'1.0'},{name:'SY_PrmCoef.CDrvDrvage',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld4',newValue:'1.0',bakValue:'1.0',value:'1.0'},{name:'SY_PrmCoef.CPlyQty',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld23',newValue:'1.0',bakValue:'1.0',value:'1.0'},{name:'SY_PrmCoef.CRunArea',newValue:'389001',bakValue:'389001',value:'389001'},{name:'SY_PrmCoef.NTgtFld5',newValue:'1.0',bakValue:'1.0',value:'1.0'},{name:'SY_PrmCoef.CYearRunMil',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld10',newValue:'1.0',bakValue:'1.0',value:'1.0'},{name:'SY_PrmCoef.CCusLoy',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld7',newValue:'1.0',bakValue:'1.0',value:'1.0'},{name:'SY_PrmCoef.CAgoClmRec',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld14',newValue:'1.0',bakValue:'1.0',value:'1.0'},{name:'SY_PrmCoef.CTgtFld11',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld15',newValue:'1.0',bakValue:'1.0',value:'1.0'},{name:'SY_PrmCoef.CTgtFld14',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld18',newValue:'1.0',bakValue:'1.0',value:'1.0'},{name:'SY_PrmCoef.NTgtFld11',newValue:'0.00',bakValue:'0.00',value:'0.00'},{name:'SY_PrmCoef.CTgtFld8',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.CLossRatio',newValue:'1.0',bakValue:'1',value:'1'},{name:'SY_PrmCoef.CMtcMng',newValue:'1.0',bakValue:'1',value:'1'},{name:'SY_PrmCoef.NTgtFld25',newValue:'1.0',bakValue:'1.0',value:'1.0'},{name:'SY_PrmCoef.NTgtFld19',newValue:'1.00',bakValue:'1.00',value:'1.00'},{name:'SY_PrmCoef.NTgtFld17',newValue:'0',bakValue:'0',value:'0'},{name:'SY_PrmCoef.CTgtFld28',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld22',newValue:'1.00',bakValue:'1.00',value:'1.00'},{name:'SY_PrmCoef.CTgtFld27',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld21',newValue:'1.00',bakValue:'1.00',value:'1.00'},{name:'SY_PrmCoef.CTgtfld15',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld100',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.CTrafIrr',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld9',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NMulRdr',newValue:'1.0',bakValue:'1.0',value:'1.0'},{name:'SY_PrmCoef.CTgtfld101',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld101',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld110',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.CTgtfld102',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld102',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.CTgtfld103',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld103',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.CTgtfld104',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld104',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.CTgtfld107',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld107',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.CTgtfld106',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld106',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.CTgtfld105',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld105',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.CTgtfld111',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld111',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.CTgtfld112',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld112',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld108',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld109',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.CTgtfld114',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld114',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.CTgtfld115',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtfld115',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld26',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld13',newValue:'1.00',bakValue:'1.00',value:'1.00'},{name:'SY_PrmCoef.NSuggestTgtfld12',newValue:'',bakValue:'',value:''},{name:'SY_PrmCoef.NTgtFld12',newValue:'',bakValue:'',value:''}]}]},{isFilter:'false',dwType:'ONLY_DATA',dwName:'prodDef.vhl.TgtObj_DW',rsCount:'1',pageSize:'10',pageNo:'1',pageCount:'0',maxCount:'1000',toAddFlag:'false',filterMapList:[],dataObjVoList:[{index:'1',selected:'true',status:'INSERTED',attributeVoList:[{name:'TgtObj.CTgtObjFld2',newValue:'',bakValue:'',value:''},{name:'TgtObj.CTgtObjFld1',newValue:'',bakValue:'',value:''},{name:'TgtObj.TTgtObjFld1',newValue:'',bakValue:'',value:''},{name:'TgtObj.TTgtObjFld2',newValue:'',bakValue:'',value:''}]}]},{isFilter:'false',dwType:'ONLY_DATA',dwName:'prodDef.vhl.Timmer_DW',rsCount:'1',pageSize:'10',pageNo:'1',pageCount:'0',maxCount:'1000',toAddFlag:'false',filterMapList:[],dataObjVoList:[{index:'1',selected:'true',status:'INSERTED',attributeVoList:[{name:'Timmer.COprNm',newValue:'游嘉琦',bakValue:'',value:''},{name:'Timmer.COprCde',newValue:'101154139',bakValue:'',value:''},{name:'Timmer.JCImmeffMrk',newValue:'',bakValue:'',value:''},{name:'Timmer.JTInsrncBgnTm',newValue:'2016-09-25 00:00:00',bakValue:'2016-09-25 00:00:00',value:''},{name:'Timmer.JTInsrncEndTm',newValue:'2017-09-24 23:59:59',bakValue:'2017-09-24 23:59:59',value:''},{name:'Timmer.JTotalDays',newValue:'365',bakValue:'',value:''},{name:'Timmer.JNRatioCoef',newValue:'1.0',bakValue:'1.0',value:''},{name:'Timmer.JShortinsureReason',newValue:'',bakValue:'',value:''},{name:'Timmer.BCImmeffMrk',newValue:'',bakValue:'',value:''},{name:'Timmer.BTInsrncBgnTm',newValue:'2016-09-25 00:00:00',bakValue:'2016-09-25 00:00:00',value:'2016-09-25 00:00:00'},{name:'Timmer.BTInsrncEndTm',newValue:'2017-09-24 23:59:59',bakValue:'2017-09-24 23:59:59',value:'2017-09-24 23:59:59'},{name:'Timmer.BTotalDays',newValue:'365',bakValue:'365',value:'365'},{name:'Timmer.BNRatioCoef',newValue:'1.0',bakValue:'1.0',value:'1.0'},{name:'Timmer.BShortinsureReason',newValue:'',bakValue:'',value:''},{name:'Timmer.TAppTm',newValue:'2016-09-24',bakValue:'2016-09-24',value:''},{name:'Timmer.TOprTm',newValue:'2016-09-24',bakValue:'2016-09-24',value:''},{name:'Timmer.TIssueTm',newValue:'2016-09-24',bakValue:'2016-09-24',value:''},{name:'TgtObjJQ.CTgtObjFld2',newValue:'',bakValue:'',value:''},{name:'TgtObjJQ.CTgtObjFld1',newValue:'',bakValue:'',value:''},{name:'TgtObjJQ.TTgtObjFld1',newValue:'',bakValue:'',value:''},{name:'TgtObjJQ.TTgtObjFld2',newValue:'',bakValue:'',value:''},{name:'TgtObjJQ.TSignTm',newValue:'',bakValue:'',value:''},{name:'TgtObj.CTgtObjFld2',newValue:'',bakValue:'',value:''},{name:'TgtObj.CTgtObjFld1',newValue:'',bakValue:'',value:''},{name:'TgtObj.TTgtObjFld1',newValue:'',bakValue:'',value:''},{name:'TgtObj.TTgtObjFld2',newValue:'',bakValue:'',value:''},{name:'JQ_Timmer.JCUnfixSpc',newValue:'',bakValue:'',value:''},{name:'SY_Timmer.BCUnfixSpc',newValue:'',bakValue:'',value:''}]}]}]";
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
			  String reg = "(\\{)([^\\{]*)"+paramName+"([^\\}]*)(\\})";//匹配 {name: 'Applicant.CAppNme',newValue: '张三',bakValue: '',value: ''}
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
	
	public static void resData(String resContent){
		String resString = "";
		List<String> resParams = new ArrayList<String>();
		resParams.add("SY_Base.NPrm");//商业总保额
		resParams.add("Vhl.NActualValue");//车辆实际价值
		for(String resParam:resParams){
			String reg = "\\{[^\\{]*"+resParam+"[^\\}]*\\}";
		    Matcher m = createMatcher(resContent,reg);
		    if(m.find()){
		    	String valueObject = m.group(0);
		    	String value = regString(valueObject,"\"value\":\\s?\"([^\"]+?)\"",1);//提取newValue的值
		    	System.out.println(resParam+":"+value);
		    }
			
		}
	}
	
	public static Matcher createMatcher(String matchStr,String reg){
		Pattern p = Pattern.compile(reg);
	    Matcher m = p.matcher(matchStr);
	    return m;
	}
	public static String regString(String str,String reg,int groupNum){
		  String resultString="";
		  Matcher m = createMatcher(str,reg);
		  if(m.find()){
			  resultString = m.group(groupNum);
		  }
		 return resultString;
	}
	

}
