package hyj.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TestConfig {
	
	static Map<String, Object>  dwData;
	static String  txData;
	
	
	static final String PATH = "car-quote-conf.json";
	static final String PATH1 = "post2Dwdata.txt";
	
	static {		
		synchronized(TestConfig.class){
		  String conf = ConfFileUtil.readConfFile(PATH);
		  JSONObject json = JSON.parseObject(conf);
		  dwData= json.getJSONObject("dwData");
		  
		}
	}

	public static Map<String, Object> getDwData() {
		return dwData;
	}
	public static String getTxData() {
		return txData;
	}
	
	static {		
		synchronized(TestConfig.class){
		  String conf = ConfFileUtil.readConfFile(PATH1);
		  JSONObject json = JSON.parseObject(conf);
		  txData = json.getString("dw2Data");
		  
		}
	}
	
	

	
}
