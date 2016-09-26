package hyj.readTxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegParseHtml {

	public static void main(String[] args) {
		  List<String> params = getParamNames();//报文newValue不为空的参数名
		  List<String> s2 = new ArrayList<String>();
		  String filePath = "E:\\my_txt\\form_quote.htm";//华安保费计算表单htm
		  //String reg = "<\\s?input([^>]*)>";
		  String reg = "(<label.*?)?<input([^>]*)>";
		  String pString = readTxtFile(filePath).replaceAll("<!--.*?-->", "");//获得html，带有注释的去除
		  Pattern p = Pattern.compile(reg);
		  Matcher m = p.matcher(pString);
			  while(m.find()){
				  String paramObject = m.group(0);//获取html标签
				  //System.out.println(paramObject);
				  for(String paramName :params){//根据报文参数名查找html文件对应的内容
					  if(paramObject.contains("\""+paramName+"\"")){
							 String paramText = regString(paramObject,"title=\"([^']+?)\"",1);
							 if(paramText.trim().equals(""))
								 //paramText = regString(beforSubString(paramText,paramObject,pString),"([\u4e00-\u9fa5]+)");
								 paramText = regString(paramObject,"([\\(|\\)|\u4e00-\u9fa5]+)",0);//匹配汉字或括号
						     String key = "\""+paramName+"\"";
							 String value ="\""+getTxtValueByParamName(paramName)+"\"";
							 //System.out.println(getTxtValueByParamName(paramName));
							 String javaCode = "postParams.put("+key+","+value+");//"+ paramText;
							 System.out.println(javaCode);
							 s2.add(paramName);
							 
						  }
				  }
			  }
			 System.out.println("html找不到的参数："+test(s2,params));
	}
	//集合比较
	public static List<String> test(List<String> s1,List<String> s2){
		for(String s:s1){
			s2.remove(s);
		}
		return s2;
	}
		public static String regString(String str,String reg,int groupNum){
			 String resultString="";
			 Pattern p = Pattern.compile(reg);
			  Matcher m = p.matcher(str);
			  if(m.find()){
				  resultString = m.group(groupNum);
			  }
			 return resultString;
		}
	//根据参数名获取报文参数值
	public static String getTxtValueByParamName(String paramName){
		 String filePath = "E:\\my_txt\\postString.txt";
		 String reg = "(\\{)([^\\{]*)"+paramName+"([^\\}]*)(\\})";
		 String value = "";
		 String pString = readTxtFile(filePath);
		  Pattern p = Pattern.compile(reg);
		  Matcher m = p.matcher(pString);
		  if(m.find()){//正则查找报文参数对象
			     String paramObject = m.group(0);//获得参数对象{name: 'Applicant.CAppNme',newValue: '张三',bakValue: '',value: ''}
			     value = regString(paramObject,"newValue:\\s?'([^']+?)'",1);//提取newValue的值
		  }
		 return value;
	}
	/**
	 * @Description: TODO(获取报文参数对象属性newValue不为空的参数名)
	 * @param paramName 参数列
	 * @return参数名集合
	 * @date 2016年9月25日 下午4:42:48
	 */
	public static List<String> getParamNames(){
		 String filePath = "E:\\my_txt\\postString.txt";
		 String reg = "(\\{)([^\\{]*)newValue([^\\}]*)(\\})";//匹配 {name: 'Applicant.CAppNme',newValue: '张三',bakValue: '',value: ''}
		 String pString = readTxtFile(filePath);
		 Pattern p = Pattern.compile(reg);
		 Matcher m = p.matcher(pString);
		 List<String> params = new ArrayList<String>();
		 while(m.find()){//正则查找报文参数对象
			  String paramObject = m.group(0);//获得参数对象{name: 'Applicant.CAppNme',newValue: '张三',bakValue: '',value: ''}
			  if(!paramObject.contains("newValue: ''")&&!paramObject.contains("newValue:''")){//去除newValue为''的参数对象
				 String paramName = regString(paramObject,"'([^']+?)'",1);//提取参数名Applicant.CAppNme
				 params.add(paramName);
				 //System.out.println(paramName);
			  }
		  }
		 System.out.println("paramsList-->"+params.size());
		 return params;
	}	
	
	
/*	//正则截取字符
	public static String regReplaceString(String str,String reg,int groupNum){
		 String replacedString="";
		 Pattern p = Pattern.compile(reg);
		  Matcher m = p.matcher(str);
		  if(m.find()){
			  replacedString = m.group(groupNum);
		  }
		return replacedString;
	}*/
	
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
