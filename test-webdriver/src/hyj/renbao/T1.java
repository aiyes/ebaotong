/**
 * 
 */
package hyj.renbao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: T1
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年11月11日 上午10:30:16
 *
 */

public class T1 {
	 public static void main(String[] args) {
			String content="<p style=\"margin:0px;\">    <img  src=\"http://127.0.0.1/img/1.png\">     </p>";
			 String reg2 = "(?s)(<p[^>]*>)(.*?)(</p>)";
			Pattern compile = Pattern.compile(reg2);
			Matcher matcher = compile.matcher(content);
			if(matcher.find()){
				
				String replace = content.replaceAll(reg2, "$1"+"ddd");
				System.out.println(replace);
			}
		}

}
