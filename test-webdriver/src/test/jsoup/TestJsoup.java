package test.jsoup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TestJsoup {
public static void main(String[] args)throws Exception {
		
		
		
	}

/**
 * 模拟登陆Iteye
 * 
 * @param userName 用户名
 * @param pwd 密码
 * 
 * **/
public static void login(String userName,String pwd)throws Exception{
	
	//第一次请求
	Connection con=Jsoup.connect("http://www.iteye.com/login");//获取连接
	con.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");//配置模拟浏览器
    Response rs= con.execute();//获取响应
    Document d1=Jsoup.parse(rs.body());//转换为Dom树
	    List<Element> et= d1.select("#login_form");//获取form表单，可以通过查看页面源码代码得知
	    
	   //获取，cooking和表单属性，下面map存放post时的数据 
   Map<String, String> datas=new HashMap<>();
   for(Element e:et.get(0).getAllElements()){
	   if(e.attr("name").equals("name")){
		   e.attr("value", userName);//设置用户名
	   }
	   
	   if(e.attr("name").equals("password")){
		   e.attr("value",pwd); //设置用户密码
	   }
	   
	   if(e.attr("name").length()>0){//排除空值表单属性
			 datas.put(e.attr("name"), e.attr("value"));  
	   }
   }
}

}
