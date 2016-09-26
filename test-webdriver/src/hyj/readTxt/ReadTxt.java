/**
 * 
 */
package hyj.readTxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: ReadTxt
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年9月24日 下午3:46:27
 *
 */

public class ReadTxt {

public static String readTxtFile(String filePath){ 
	 String getString="";
    try { 
            String encoding="UTF-8"; 
            File file=new File(filePath); 
            if(file.isFile() && file.exists()){ //判断文件是否存在 
                InputStreamReader read = new InputStreamReader( 
                new FileInputStream(file),encoding);//考虑到编码格式 
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
  
public static void main(String argv[]){ 
	
    String filePath = "E:\\my_txt\\postString.txt"; 
    String pString = readTxtFile(filePath);
    List<Integer> indexs = new ArrayList<Integer>();
    int poit = 0,index=0;
    int end = pString.lastIndexOf("newValue");
    while(true){
    	poit = pString.indexOf("newValue", index);
    	indexs.add(poit);
    	index = poit+1;
    	if(poit==end) break;
    }
    System.out.println(indexs.size());
    int startNum,endNum;
    for(int i :indexs){
    	startNum = i-1;
    	endNum = i+1;
    	while(pString.charAt(startNum) != '{'){
    		startNum = startNum -1;
    	}
    	while(pString.charAt(endNum) != '}'){
    		endNum = endNum +1;
    	}
    	System.out.println(pString.substring(startNum, endNum+1));
    }
    
   // System.out.println(indexs.size());
}

}