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

import com.alibaba.fastjson.JSON;
import com.ebtins.dto.open.CarModelInfoVo;
import com.ebtins.dto.open.CarModelReq;
import com.ebtins.dto.open.CarModelRes;

import ebtins.smart.proxy.company.renbao.dto.RenbaoCarModelContent;
import ebtins.smart.proxy.company.renbao.dto.RenbaoCarModelData;
import huaan.quote.util.StringUtil;
import hyj.login.RenBaoLoginA;

/**
 * @ClassName: QueryModel
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年11月10日 下午2:37:01
 *
 */

public class QueryModel {
	
	public static CarModelRes queryCarModel(CarModelReq req) throws Exception{
		CarModelRes res = new CarModelRes();
		String cookie = RenBaoLoginA.login();
        String modelUrl="http://10.134.130.208:8000/prpall/vehicle/vehicleQuery.do?pageSize=50&pageNo=1";
        String modelRefUrl="http://10.134.130.208:8000/prpall/vehicle/prepareQuery.do?riskCode=DAA";
        Map<String, String> params=new HashMap<String, String>();
  	    String brandName = com.ebtins.open.common.util.StringUtil.ObjectToString(req.getBrandName()).trim();//车型名称
  	    String vehicleId = com.ebtins.open.common.util.StringUtil.ObjectToString(req.getExt().get("vehicleId")).trim();//车型编码
  	    String vehicleBrandName = com.ebtins.open.common.util.StringUtil.ObjectToString(req.getExt().get("vehicleBrandName")).trim();//车辆品牌名称
  	    String brandId = com.ebtins.open.common.util.StringUtil.ObjectToString(req.getExt().get("brandId")).trim();//车辆品牌型号
  	    String searchCode = com.ebtins.open.common.util.StringUtil.ObjectToString(req.getExt().get("searchCode")).trim();//快速查询码
  	    String vehicleAlias = com.ebtins.open.common.util.StringUtil.ObjectToString(req.getExt().get("vehicleAlias")).trim();//车型别名
  	    if("".equals(brandName)&&"".equals(brandId)&&"".equals(vehicleBrandName)&&
  	    		"".equals(searchCode)&&"".equals(vehicleAlias)&&"".equals(vehicleId)){
  	    	res.getHeader().setResCode("10001");
  	    	res.getHeader().setResMsg("查询条件为空！");
  	    	return res;
  	    }
		params.put("brandName",brandName);
		params.put("TCVehicleVO.vehicleName",brandName);
		params.put("TCVehicleVO.vehicleId",vehicleId);
		params.put("TCVehicleVO.brandId",brandId);
		params.put("TCVehicleVO.brandName",vehicleBrandName);
		params.put("TCVehicleVO.searchCode",searchCode);
		params.put("TCVehicleVO.vehicleAlias",vehicleAlias);
		params.put("carShipTaxPlatFormFlag", "");
		params.put("riskCode", "DAA");
		params.put("comCode", "44030700");
		params.put("pageNo_", "1");
		params.put("pageSize_", "50");
		params.put("jumpToPage", "1");
		params.put("pm_vehicle_switch", "");
		params.put("quotationFlag", "");
		params.put("taxFlag", "");
		params.put("totalRecords_", "");
		String body="";
		try {
			body = RenBaoLoginA.post(modelUrl, params,"GBK",cookie,modelRefUrl);
		} catch (Exception e) {
			e.printStackTrace();
			res.getHeader().setResCode("10001");
  	    	res.getHeader().setResMsg("查询请求错误！");
  	    	return res;
		}
		List<CarModelInfoVo> models = new ArrayList<CarModelInfoVo>();
		RenbaoCarModelContent content = JSON.parseObject(body,RenbaoCarModelContent.class);
		if("0".equals(content.getTotalRecords())){
			res.getHeader().setResCode("10000");
  	    	res.getHeader().setResMsg("无相关车型信息！");
  	    	return res;
		}
		for(RenbaoCarModelData data :content.getData()){
			CarModelInfoVo model = new CarModelInfoVo();
			model.setModelCode(data.getVehicleId());//车型代码
			model.setBrandName(data.getVehicleName());//车型名称(品牌型号)  brandId-品牌型号
			model.setFactory(data.getVehicleMakerId());//生产厂家--厂商代码？
			model.setCarBrand(data.getBrandName());//品牌
			model.setCarYear(data.getVehicleYear());//年款
			model.setFamilyName(data.getGroupName());//车系名称--车组名称？
			model.setcIModelClass(data.getVehicleClassPicc());//交强险车型分类
			model.setPurchasePrice(data.getPriceT());//新车购置价
			model.setSeatCount(data.getVehicleSeat());//座位数
			model.setExhaustScale(data.getVehicleExhaust());//排气量
			model.setCarVehicleType(data.getVehicleClass());//车辆种类:1为客车，2为货车，3为特种车？
			model.setCountryNature(data.getVehicleType());//国别性质：01-国产、02-进口、03-合资 ？
			models.add(model);
		}
		res.setCarModelList(models);
		res.getHeader().setResCode("0000");
		res.getHeader().setResMsg("成功");
		System.out.println("res-->"+JSON.toJSONString(res));
		return res;
		/*Map<String,String> relateMap = new HashMap<String,String>();
		relateMap.put("modelCode", "vehicleId");//车型代码
		relateMap.put("brandName", "vehicleName");//车型名称(品牌型号)  brandId-品牌型号
		relateMap.put("factory", "vehicleMakerId");//生产厂家--厂商代码？
		relateMap.put("carBrand", "brandName");//品牌
		relateMap.put("carYear", "vehicleYear");// 年款
		relateMap.put("familyName", "groupName");//车系名称--车组名称？
		relateMap.put("cIModelClass", "vehicleClassPicc");//交强险车型分类
		relateMap.put("purchasePrice", "priceT");//新车购置价
		relateMap.put("purchasePriceNotTax", "");//新车购置价(不含税)
		relateMap.put("analogyModelPrice", "");//类比车型价格(含税)
		relateMap.put("analogyModelPriceNotTax", "");//类比车型价格(不含税)
		relateMap.put("riskFlag", "");//车型风险类型
		relateMap.put("tonCount", "");//吨位数
		relateMap.put("seatCount", "vehicleSeat");//座位数
		relateMap.put("exhaustScale", "vehicleExhaust");//排气量
		relateMap.put("fullWeight", "vehicleTonnage");//整备质量--核定载质量？
		relateMap.put("carStyle", "");//国别性质
		relateMap.put("transmissionType", "");//变速箱类型: 如自动档、手动档
		relateMap.put("configurationLevel", "");//配置版本级别: 如经济型、豪华型
		relateMap.put("carTypeCode", "");// 行驶证车辆类型
		relateMap.put("carVehicleType", "vehicleClass");//车辆种类:1为客车，2为货车，3为特种车？
		relateMap.put("carVehicleTypeCode", "");//车辆种类类型
		relateMap.put("countryNature", "vehicleType");//国别性质：01-国产、02-进口、03-合资 ？
		relateMap.put("externalId", "");//第三方车型编号，如乐宝吧车型ID
		relateMap.put("description", "");//车型简要描述，需要按一定的顺序进行拼接
		relateMap.put("remark", "");//备注
		*/
	}

}
