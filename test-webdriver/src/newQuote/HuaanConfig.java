package newQuote;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class HuaanConfig {
	
	static Map<String, Object>  dwData;
	
	static Map<String, Object>  kindCodeMap;
	static Map<String, Object>  kindNameMap;
	static Map<String, Object>  rKindNameMap;
	static Map<String, Object>  kindCodeMapReq;//新增加码表转换
	static Map<String, Object>  customerTypeMap;
	static Map<String, Object>  IdCardMap;
	static Map<String, Object>  cvrgCyMap;//第三者责任保险保额
	static Map<String, Object>  rCvrgCyMap;
	static Map<String, Object>  cvrgCy19Map;//车身划痕损失险
	static Map<String, Object>  rCvrgCy19Map;
	static Map<String, Object>  CarVehicleTypeMap;
	
	static final String PATH = "huaan-conf.json";
	
	static {		
		synchronized(HuaanConfig.class){
		  String conf = ConfFileUtil.readConfFile(PATH);
		  JSONObject json = JSON.parseObject(conf);
		  kindCodeMap= json.getJSONObject("kindCodeMap");
		  kindNameMap= json.getJSONObject("kindNameMap");
		  rKindNameMap= json.getJSONObject("rKindNameMap");
		  kindCodeMapReq= json.getJSONObject("kindCodeMapReq");
		  customerTypeMap= json.getJSONObject("customerTypeMap");
		  IdCardMap= json.getJSONObject("IdCardMap");
		  CarVehicleTypeMap= json.getJSONObject("CarVehicleTypeMap");
		  cvrgCyMap= json.getJSONObject("cvrgCyMap");
		  rCvrgCyMap= json.getJSONObject("rCvrgCyMap");
		  cvrgCy19Map= json.getJSONObject("cvrgCy19Map");
		  rCvrgCy19Map= json.getJSONObject("rCvrgCy19Map");
		  dwData= json.getJSONObject("dwData");
		  
		}
	}
	public static Map<String, Object> getCvrgCy19Map() {
		return cvrgCy19Map;
	}
	public static Map<String, Object> getrCvrgCy19Map() {
		return rCvrgCy19Map;
	}
	public static Map<String, Object> getCvrgCyMap() {
		return cvrgCyMap;
	}
	public static Map<String, Object> getrCvrgCyMap() {
		return rCvrgCyMap;
	}
	public static Map<String, Object> getCarVehicleTypeMap() {
		return CarVehicleTypeMap;
	}
	public static Map<String, Object> getKindCodeMap() {
		return kindCodeMap;
	}
	public static Map<String, Object> getrKindCodeMap() {
		return rKindNameMap;
	}
	public static Map<String, Object> getKindNameMap() {
		return kindNameMap;
	}
	public static Map<String, Object> getKindCodeMapReq() {
		return kindCodeMapReq;
	}

	public static Map<String, Object> getDwData() {
		return dwData;
	}
	public static Map<String, Object> getCustomerTypeMap() {
		return customerTypeMap;
	}
	public static Map<String, Object> getIdCardMap() {
		return IdCardMap;
	}
	
	
}
