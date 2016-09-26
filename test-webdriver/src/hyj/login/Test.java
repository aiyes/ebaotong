package hyj.login;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		HttpClient hc = new SSLClient();
		//CloseableHttpClient hc = HttpClients.createDefault();
		HttpGet request = new HttpGet();
		request.setURI(new URI("https://webpolicy.sinosafe.com.cn/pcis/core/login/realLogin.jsp"));
		HttpResponse response = hc.execute(request);
		String strResult=EntityUtils.toString(response.getEntity());
		System.out.println(strResult);
		
	}

}
