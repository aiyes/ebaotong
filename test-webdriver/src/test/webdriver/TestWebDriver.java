package test.webdriver;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import test.webdriver.util.HttpClientUtil;

public class TestWebDriver {
	
	public static void main(String[] args) throws Exception {

		doWebDriver();
		//doJsoup();
	
	}
	
	/*public static void getDetailElement(WebDriver driver,String ggCarModelModelCode){
	 * 
		String urlString="http://218.17.200.230:9002/platform/processGgCarModel.do?actionType=view&ggCarModelModelCode=%s&chooseType=carModelQuery";
		urlString=String.format(urlString, ggCarModelModelCode);
		driver.get(urlString);
		System.out.println(driver.findElement(By.name("ggCarModelFactory")).getText());
		System.out.println(driver.findElement(By.name("ggCarModelAnalogyModelPrice")).getText());
		System.out.println(driver.findElement(By.name("ggCarModelAnalogyModelPriceNotTax")).getText());
		System.out.println(driver.findElement(By.name("ggCarModelCarBrand")).getText());
		driver.navigate().back();
		
	}*/
	
	public static void doJsoup() throws Exception{
		//System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
		//WebDriver driver = new ChromeDriver(); 
		//WebDriver driver = new HtmlUnitDriver(true);
		//WebDriver driver = new HtmlUnitDriver(true);
		Connection con=Jsoup.connect("http://218.17.200.230:9001/index.jsp");//获取连接
		Response rs= con.execute();//获取响应
		Document d1=Jsoup.parse(rs.body());//转换为Dom树

		
		Map<String, String> form=new HashMap<>();
		form.put("username", "0201005095");
		form.put("password", "123456");
		form.put("lt", d1.select("input[name=lt]").first().attr("value"));
		form.put("_eventId", "submit");
		
		Document d2=Jsoup.connect("http://218.17.200.230:9004/casserver/login?service=http%3A%2F%2F218.17.200.230%2Fj_acegi_security_check")
				.cookies(rs.cookies())
				.data(form).post();

		System.out.println(d2.html());
		Elements el2 =d2.getElementsByTag("frameset");
		
		if(el2!=null && el2.size()>0 ){
			Map<String, String> data=new HashMap<>();
			data.put("chooseType", "carModelQuery");
			data.put("businessType", "Proposal");
			data.put("GgCarModelModelCName", "江淮HFC7202EF轿车");
			data.put("GgCarModelCountryCode", "0");
			
			Document d3=Jsoup.connect("http://218.17.200.230:9002/prpall/processProposal.do?actionType=queryMotorModelCode&chooseType=carModelQuery&riskCode=null&companyCode=null")
					.cookies(rs.cookies())
					.data(data)
					.post();
			System.out.println(d3.html());
		}
		
	}
	public static void doWebDriver() throws Exception{
		
		RemoteWebDriver driver = new ChromeDriver(); 
		driver.get("http://218.17.200.230:9001/index.jsp");
		// 用下面代码也可以实现

		// 获取 网页的 title
		System.out.println("1 Page title is: " + driver.getTitle());

		driver.findElement(By.xpath("//*[@id='username']")).sendKeys("0201005095");
		driver.findElement(By.id("password")).sendKeys("123456");
		driver.findElement(By.name("imageField")).click();
		
		Thread.sleep(2000L);
		Alert alert = driver.switchTo().alert();  
		alert.accept();
		
		
		Set<Cookie> allCookies = driver.manage().getCookies();
		for (Cookie loadedCookie : allCookies) {
		    System.out.println(String.format("%s -> %s", loadedCookie.getName(), loadedCookie.getValue()));
		}
		System.out.println(driver.getSessionId());
		
		driver.get("http://218.17.200.230:9002/prpall/modules/itemmotor/EditItemMotorModelCodeQueryInput.jsp?chooseType=carModelQuery&businessType=Proposal");
		
		//driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		
		WebElement carModeButton = (new WebDriverWait(driver, 10))
				.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(By.name("GgCarModelModelCName"));
					}
				});
		
		carModeButton.sendKeys("江淮HFC7202EF轿车");
		driver.findElement(By.name("buttonQuery")).click();
		
		
		WebElement iframeElement=driver.findElement(By.name("QueryResultFrame"));
		driver.switchTo().frame(iframeElement);
		
		WebElement tb = (new WebDriverWait(driver, 10))
				.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(By.id("resultTable"));
					}
				});
				
		WebElement tbody=tb.findElement(By.tagName("tbody"));
		List<WebElement> rows =tbody.findElements(By.tagName("tr"));
		//List<WebElement> rows = driver.findElements(By.xpath("//*[@id='resultTable']/tbody/tr"));
    	System.err.println(rows.size());
	    //打印出所有单元格的数据
	    for (WebElement row : rows) { 
	    	//得到当前tr里td的集合
	        List<WebElement> cols =  row.findElements(By.tagName("td"));
	        WebElement elText=cols.get(2).findElement(By.tagName("input"));
	        System.out.print("\t"+elText.getAttribute("value"));//得到td里的文本
	        
	        try {
	        	Map<String, String> cookies=new HashMap<>();

	    		Set<Cookie> localCookies = driver.manage().getCookies();
	    		for (Cookie lc : localCookies) {	
	    			cookies.put(lc.getName(), lc.getValue());
	    		}	
	        	parseDetailByJsoupElement(cookies,elText.getAttribute("value"));
			} catch (Exception e) {
				e.printStackTrace();
			}
	        System.out.println();
	    }
	    System.out.println("END");
	}
	
	public static void parseDetailElement(RemoteWebDriver driver,String ggCarModelModelCode) throws Exception{
		StringBuffer cookiesBuffer=new StringBuffer();
		Set<Cookie> allCookies = driver.manage().getCookies();
		for (Cookie lc : allCookies) {	
			cookiesBuffer.append(lc.getName()+"="+lc.getValue()+";");
		}		
		//System.out.println("cookies:"+cookiesBuffer.toString()); 
		
		String urlString="http://218.17.200.230:9002/platform/processGgCarModel.do?actionType=view&ggCarModelModelCode=%s&chooseType=carModelQuery";
		urlString=String.format(urlString, ggCarModelModelCode);
		String htmlString =HttpClientUtil.get(urlString,cookiesBuffer.toString());
		
		org.jsoup.nodes.Document  doc =Jsoup.parse(htmlString);
		Elements el1 =doc.select("input[name=ggCarModelModelCode]");
		System.out.println(el1.get(0).attr("value"));
		Elements el2 =doc.select("input[name=ggCarModelShortHandCode]");
		System.out.println(el2.get(0).attr("value"));
		Elements el3 =doc.select("input[name=ggCarModelGuildModelName]");
		System.out.println(el3.get(0).attr("value"));
		
	}
	
	public static void parseDetailByJsoupElement(Map<String, String> cookies,String ggCarModelModelCode) throws Exception{
		String urlString="http://218.17.200.230:9002/platform/processGgCarModel.do?actionType=view&ggCarModelModelCode=%s&chooseType=carModelQuery";
		urlString=String.format(urlString, ggCarModelModelCode);
		
		Document doc=Jsoup.connect(urlString).cookies(cookies).get();
		
		Elements el1 =doc.select("input[name=ggCarModelModelCode]");
		System.out.println(el1.get(0).attr("value"));
		Elements el2 =doc.select("input[name=ggCarModelShortHandCode]");
		System.out.println(el2.get(0).attr("value"));
		Elements el3 =doc.select("input[name=ggCarModelGuildModelName]");
		System.out.println(el3.get(0).attr("value"));
		
	}
	
	
}
