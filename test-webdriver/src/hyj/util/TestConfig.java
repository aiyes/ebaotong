package hyj.util;

import java.util.Map;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TestConfig {
	
	static Map<String, Object>  dwData;
	
	
	static final String PATH = "car-quote-conf.json";
	
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


	
}
