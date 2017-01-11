/**
 * 
 */
package thread;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName: ExeService
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年12月30日 下午3:41:11
 *
 */

public class ExeService {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String str = new ExeService().testcomple();
		System.out.println("zuihou-->"+str);
	}
	public String testcomple() throws InterruptedException, ExecutionException{
		ExecutorService pool = Executors.newFixedThreadPool(5);
		CompletionService<String> cservice = new ExecutorCompletionService<String>(pool);
		for(int i=0;i<5;i++){
			cservice.submit(new Mythread("mytthread "+i));
		}
		String str = "";
		//for(int i=0;i<1;i++){
			Future<String> future = cservice.take();
			str = future.get();
			if(!str.equals(""))
				return str;
		//}
		pool.shutdown();
		return "没有";
	}

}
