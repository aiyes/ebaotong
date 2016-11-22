package hyj.login;

import java.net.URI;

import javax.xml.bind.JAXBException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

import ebtins.smart.proxy.company.renbao.dto.RenbaoRenewalContent;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		RenbaoRenewalContent renewalContent = null;
		renewalContent.getData();
	}
	public static String get(){
		String str = "33";
		try {
			int i = 10/0;
		} catch (Exception e) {
			str = "44";
			return str;
		}
		str = "55";
		return str;
	}

}
