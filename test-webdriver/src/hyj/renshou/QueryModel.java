/**
 * 
 */
package hyj.renshou;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.ebtins.dto.open.CarModelInfoVo;
import com.ebtins.dto.open.CarModelReq;
import com.ebtins.dto.open.CarModelRes;
import com.ebtins.open.common.util.StringUtil;

import hyj.renshou.login.RenshouLogin;

/**
 * @ClassName: QueryModel
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年11月10日 下午2:37:01
 *
 */

public class QueryModel {
	public static void main(String[] args) throws Exception {
		new QueryModel().queryCarModel(null);
	}
	public CarModelRes queryCarModel(CarModelReq req) throws Exception{
		CarModelRes res = new CarModelRes();
		String cookie = RenshouLogin.login();
        String modelUrl="http://9.0.6.69:7001/prpall/common/pub/UIModelCodeQueryInputJY.jsp";
        String detailModelUrl = "http://9.0.6.69:7001/prpall/common/pub/UICarModelDetailJY.jsp";
        Map<String, String> queryPayForParams=new HashMap<String, String>();
        queryPayForParams.put("RiskCode","0511");
        queryPayForParams.put("ComCode","44031573");
        queryPayForParams.put("StandardName","五菱LZW6407BAF客车");
        queryPayForParams.put("StandardNameTemp","五菱LZW6407BAF客车");
        queryPayForParams.put("SearchCode","");
        queryPayForParams.put("SearchCodeTemp","");
        queryPayForParams.put("CompanyName","");
        queryPayForParams.put("strCompanyNameTemp","");
        queryPayForParams.put("BrandName","");
        queryPayForParams.put("BrandNameTemp","");
        queryPayForParams.put("FamilyName","");
        queryPayForParams.put("FamilyNameTemp","");
        queryPayForParams.put("VINCode","");
        queryPayForParams.put("VINCodeTemp","");
        queryPayForParams.put("RBCode","");
        queryPayForParams.put("RBCodeTemp","");
        queryPayForParams.put("ImportFlag","-1");
        queryPayForParams.put("ImportFlagTemp","-1");
        queryPayForParams.put("VehicleClass","-1");
        queryPayForParams.put("VehicleClassTemp","-1");
        queryPayForParams.put("PageNum","1");
        queryPayForParams.put("PageCount","1");
        queryPayForParams.put("SpuerWherePart","1");
        queryPayForParams.put("Personal","1");
		String body="";
		try {
			body = RenshouLogin.post(modelUrl, queryPayForParams, "GBK", cookie, modelUrl, null);
		} catch (Exception e) {
			e.printStackTrace();
			res.getHeader().setResCode("10001");
  	    	res.getHeader().setResMsg("查询车型列表信息出错！");
  	    	return res;
		}
		Document doc = Jsoup.parse(body);
		Elements eles = doc.getElementsByAttributeValue("name", "CarModelDetail");
		List<CarModelInfoVo> models = new ArrayList<CarModelInfoVo>();
		for(Element e :eles){
			String clickStr = e.attr("onclick");
			if(clickStr.indexOf("'")>-1){
				try {
					String price = clickStr.substring(clickStr.indexOf("'")+1, clickStr.lastIndexOf("'"));
					String model = e.attr("description");
					String url = detailModelUrl+"?ModelCode="+model+"&PurChasePrice="+price;
					String detailHtml = RenshouLogin.get(url, "GBK", cookie, null, null);
					System.out.println("detailHtml-->"+detailHtml);
					models.add(setCarMode(parseHtml(detailHtml)));
				} catch (KeyManagementException | NoSuchAlgorithmException | IOException e1) {
					e1.printStackTrace();
					res.getHeader().setResCode("10001");
		  	    	res.getHeader().setResMsg("查询车型详细信息出错！");
		  	    	return res;
				}
			}
		}
		res.setCarModelList(models);
		res.getHeader().setResCode("0000");
		res.getHeader().setResMsg("成功");
		System.out.println(JSON.toJSONString(res));
		return res;
	}
	//获取车型信息详细页面td标签内容
	public Map<String,String> parseHtml(String html){
		Map<String,String> map = new HashMap<String,String>();
		Document doc =  Jsoup.parse(html);
		Elements els = doc.getElementsByAttributeValue("class", "itemLink1");
		for(Element el:els){
			String tdText = el.text();
			System.out.println("tdText-->"+tdText);
			if(tdText!=null&tdText.indexOf("：")>-1){
				int index = tdText.indexOf("：");
				map.put(tdText.substring(0,index), tdText.substring(index).replace("：", "").trim());
			}
		}
		System.out.println("map-->"+map);
		return map;
	}
	//封装车型信息
	public CarModelInfoVo setCarMode(Map<String,String> map){
		CarModelInfoVo model = new CarModelInfoVo();
		model.setAnalogyModelPrice(map.get("类比车型价格(含税)"));
		model.setAnalogyModelPriceNotTax(map.get("类比车型价格"));
		model.setBrandName(map.get("车型名称"));//车型名称(品牌型号)  brandId-品牌型号
		model.setcIModelClass(map.get("商业险名称"));//交强险车型分类
		model.setCarBrand(map.get("品牌"));//品牌
		model.setCarStyle("");//作废，留空
		
		model.setCarTypeCode(map.get(""));//行驶证车辆类型
		model.setCarVehicleTypeCode(getCarVehicleTypeCodeBySeat(map.get("额定载客人数")));//车辆种类类型 --A012六座以下客车,A022六座至十座以下客车车,
		model.setCarVehicleTypeSubcode("");//车辆种类子类型（特种车独有）
		model.setCarVehicleType(map.get("车辆种类"));//车辆种类:1为客车，2为货车，3为特种车？
		
		model.setCarYear(map.get("上市年份"));//年款
		model.setConfigurationLevel("");
		model.setExhaustScale(map.get("排气量"));//排气量
		model.setExternalId("");
		model.setFactory(map.get("生产厂家"));//生产厂家--厂商代码？
		model.setFamilyName(map.get("车型系列"));//车系名称--车组名称？
		model.setFullWeight(map.get("最大整备质量(千克)"));
		model.setModelCode(map.get("车型代码"));//车型代码
		model.setModelId(0);
		model.setPurchasePrice(map.get("新车购置价(含税)"));//新车购置价
		model.setPurchasePriceNotTax(map.get("新车购置价"));
		model.setRiskFlag(map.get("风险标识"));
		model.setTonCount(map.get("额定载质量(吨)"));//吨位数 是不是 额定载质量？待确认
		model.setSeatCount(map.get("额定载客人数"));//座位数
		model.setTransmissionType(map.get("变速器类型"));
		model.setCountryNature("");//国别性质：01-国产、02-进口、03-合资 ？
		System.out.println("model-->"+JSON.toJSONString(model));
		return model;
		
	}
	//根据座位返回CarVehicleTypeCode
	public String getCarVehicleTypeCodeBySeat(String seat){
		seat = StringUtil.ObjectToString(seat);
		String vehicleTypeCode="";
		if(!seat.trim().matches("\\d+"))
			return vehicleTypeCode;
		int seatNum = Integer.parseInt(seat);
		if(seatNum<6){
			vehicleTypeCode = "A012";
		}else if(seatNum>5&&seatNum<10){
			vehicleTypeCode = "A022";
		}else if(seatNum>9&&seatNum<20){
			vehicleTypeCode = "A032";
		}else if(seatNum>19&&seatNum<36){
			vehicleTypeCode = "A042";
		}else if(seatNum>35){
			vehicleTypeCode = "A052";
		}
		return vehicleTypeCode;
	}

	}		
		

