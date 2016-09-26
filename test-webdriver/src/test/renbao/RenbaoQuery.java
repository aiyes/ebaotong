package test.renbao;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RenbaoQuery {
	public static void main(String[] args) throws InterruptedException {
		//System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
		//WebDriver driver = new ChromeDriver(); 
		//WebDriver driver = new HtmlUnitDriver(true); 
		//WebDriver driver = new FirefoxDriver(); 
		WebDriver driver = new InternetExplorerDriver(); 
		
        //driver.get("https://10.134.130.208:8888/casserver/login?service=http%3A%2F%2F10.134.130.208%3A8000%2Fprpall%2Fbusiness%2FquickProposal.do%3FbizType%3DPROPOSAL%26editType%3DNEW%26is4S%3DY");
		driver.get("http://10.134.130.208:8000/prpall?calogin");
		driver.get("javascript:document.getElementById('overridelink').click();");
		
		//System.out.println("Page title is: " + driver.getTitle());

		driver.findElement(By.id("username1")).sendKeys("1299010766");
		driver.findElement(By.id("password1")).sendKeys("b123456");
		driver.findElement(By.id("button")).click();
		
		//driver.get("http://10.134.130.208:8000/prpall?calogin");
		//driver.get("javascript:document.getElementById('overridelink').click();");
		
		WebElement mainFrame =driver.findElement(By.name("main"));
		driver.switchTo().frame(mainFrame);
		driver.findElement(By.xpath("//*[@id='tdMenu']/div/form/table/tbody/tr[1]/td/table[5]/tbody/tr[1]/td")).click();
		driver.findElement(By.xpath("//*[@id='menu520208']/tbody/tr[5]/td/a")).click();
		 
		WebElement pageFrame =driver.findElement(By.name("page"));
		driver.switchTo().frame(pageFrame);
		driver.findElement(By.id("prpCrenewalVo.licenseNo")).sendKeys("粤BZ96R0");
		Select cartype = new Select(driver.findElement(By.id("prpCrenewalVo.licenseType")));
		cartype.selectByIndex(2);
		
		driver.findElement(By.id("insured_btn_Save")).click();
		
		//WebElement tbody=driver.findElement(By.xpath("//*[@id='yui-dt-table0']/tbody[2]"));
		//driver.findElement(By.id("yui-dt0-bdrow0"));
		
		WebElement tbody = (new WebDriverWait(driver, 10))
				.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(By.xpath("//*[@id='yui-dt-table0']/tbody[2]"));
					}
				});
		
		new WebDriverWait(driver, 10).until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.id("yui-dt0-bdrow0"));
			}
		});
		
		List<WebElement> rows =tbody.findElements(By.tagName("tr"));	
		
    	System.err.println(rows.size());
	    //打印出所有单元格的数据
	    for (WebElement row : rows) { 
	    	//得到当前tr里td的集合
	        List<WebElement> cols =  row.findElements(By.tagName("td")); 
	        for (WebElement col : cols) {
	        	try {
					if(col.getText()!=null){
						System.out.print("\t"+col.getText());//得到td里的文本
					}else{
						WebElement elText = col.findElement(By.tagName("input"));
						System.out.print("\t"+elText.getAttribute("value"));//得到td里的文本
					}
				} catch (NoSuchElementException e) {
					e.printStackTrace();
				}
	        }
	        System.out.println();
	    }
	    System.out.println("END");
		//driver.quit();  
	}
}