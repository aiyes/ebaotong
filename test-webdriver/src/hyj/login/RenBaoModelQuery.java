package hyj.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebtins.dto.open.CarModelInfoVo;
import com.ebtins.dto.open.CarModelRes;

import test.webdriver.util.HttpClientUtil;

public class RenBaoModelQuery {
	public static void main(String[] args) throws Exception {
		/*String cookies=RenBaoLogin.login();
		queryModel(cookies);*/
	}
	
	
	public static void queryModel(String cookies){
		String GBK="GBK";
		String qUrl="http://10.134.130.208:8000/prpall/vehicle/vehicleQuery.do?pageSize=10&pageNo=1";
		Map<String, String> params=new HashMap<String, String>();
		params.put("brandName", "");
		params.put("carShipTaxPlatFormFlag", "");
		params.put("comCode", "44030700");
		params.put("jumpToPage", "1");
		params.put("pageNo_", "1");
		params.put("pageSize_", "20");
		params.put("pm_vehicle_switch", "");
		params.put("quotationFlag", "");
		params.put("riskCode", "DAA");
		params.put("taxFlag", "");
		params.put("TCVehicleVO.brandId", "");
		params.put("TCVehicleVO.brandName", "");
		params.put("TCVehicleVO.searchCode", "");
		params.put("TCVehicleVO.vehicleAlias", "");
		params.put("TCVehicleVO.vehicleId", "");
		params.put("TCVehicleVO.vehicleName", "*WP1AA2A2*");
		params.put("totalRecords_", "");
		
		String resultStr =HttpClientUtil.post(qUrl, params,GBK,cookies);
		JSONObject jsonResult=JSON.parseObject(resultStr);
		JSONArray jsonArr=(JSONArray) jsonResult.get("data");
		
		CarModelRes res=new CarModelRes();
		List<CarModelInfoVo> modelList =new ArrayList<CarModelInfoVo>();
		res.setCarModelList(modelList);
		
		for(Object r :jsonArr){
			JSONObject jr=(JSONObject) r;
			System.out.println(jr.get("vehicleName"));
			System.out.println(jr.get("vehicleId"));
			System.out.println(jr.get("priceT"));
			System.out.println(jr.get("priceTr"));
			
		}
		
	}
}
