package hyj.readTxt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetHtmlInputText {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "<input  name='YEAR' id='YEAR' text='mini-combobox' style='width:110px;' data='year' value='<%=nian%>'/>";
		String reg = "(<input[^<>]*?\\s)text=(['\"]?)(.*?)(['\"]?)(\\s.*?)?>";
		regString(str,reg);

	}

	//正则截取字符
	public static void regString(String str,String reg){
		 String resultString="";
		 Pattern p = Pattern.compile(reg);
		  Matcher m = p.matcher(str);
		  while(m.find()){
			  resultString = m.group(0);
			  System.out.println(resultString);
		  }
	}

}
