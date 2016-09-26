package hyj.readTxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegParseTxt {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		  String filePath = "E:\\my_txt\\postString.txt";
		  String reg = "(\\{)([^\\{]*)newValue([^\\}]*)(\\})";//匹配 {name: 'Applicant.CAppNme',newValue: '张三',bakValue: '',value: ''}
		  String pString = readTxtFile(filePath);
		  Pattern p = Pattern.compile(reg);
		  Matcher m = p.matcher(pString);
		  while(m.find()){//正则查找报文参数对象
			  String paramObject = m.group(0);//获得参数对象{name: 'Applicant.CAppNme',newValue: '张三',bakValue: '',value: ''}
			  if(!paramObject.contains("newValue: ''")){//去除newValue为''的参数对象
				 String paramName = regReplaceString(paramObject,"'([^']+?)'");//提取参数名Applicant.CAppNme
				 String paramValue = "newValue: '{"+paramName+"}'";
				 String paramValueObject = paramObject.replaceFirst("(newValue: ')([^']+)(')",paramValue );//替换（填充）newValue参数值
				 //System.out.println(value);
				 System.out.println(paramValueObject);
			  }
		  }

	}
	//正则截取字符
	public static void regString(){
		String filePath = "E:\\my_txt\\postString.txt"; 
		String reg = "(\\{)([^\\{]*)newValue([^\\}]*)(\\})";
		String postString = readTxtFile(filePath);
		 //regString(postString,reg);
		 regString1(postString,reg);
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
	//正则提取变量名
		public static void regString1(String str,String reg){
			 String resultString="";
			 Pattern p = Pattern.compile(reg);
			  Matcher m = p.matcher(str);
			  while(m.find()){
				  resultString = m.group(0);
				  String paramName = regReplaceString(resultString,"'([^']+?)'");
				  System.out.println(paramName);
			  }
		}
	
	//正则截取字符
	public static String regReplaceString(String str,String reg){
		 String replacedString="";
		 Pattern p = Pattern.compile(reg);
		  Matcher m = p.matcher(str);
		  if(m.find()){
			  replacedString = m.group(0).replaceAll("'", "");
		  }
		return replacedString;
	}
	//读取txt文件报文
	public static String readTxtFile(String filePath){ 
		 String getString="";
	    try { 
	            String encoding="UTF-8"; 
	            File file=new File(filePath); 
	            if(file.isFile() && file.exists()){ //判断文件是否存在 
	                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式 
	                BufferedReader bufferedReader = new BufferedReader(read); 
	                String lineTxt = null; 
	                while((lineTxt = bufferedReader.readLine()) != null){ 
	                	getString +=lineTxt;
	                    //System.out.println(lineTxt); 
	                    
	                } 
	                read.close(); 
	    }else{ 
	        System.out.println("找不到指定的文件"); 
	    } 
	    } catch (Exception e) { 
	        System.out.println("读取文件内容出错"); 
	        e.printStackTrace(); 
	    } 
	    return getString;
	} 

}
