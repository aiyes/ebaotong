/**
 * 
 */
package thread;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @ClassName: Mythread
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年12月30日 下午3:35:05
 *
 */

public class Mythread implements Callable<String>{
	private String name;

    public Mythread(String name){
    	this.name = name;
    }
	@Override
	public String call() throws Exception {
		int sleepTime = new Random().nextInt(1000);
		Thread.sleep(sleepTime);
		String str = name +" sleep:"+sleepTime;
		System.out.println(str);
		return str;
	}
	

}
