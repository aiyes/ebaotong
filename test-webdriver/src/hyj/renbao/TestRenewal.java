/**
 * 
 */
package hyj.renbao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.ebtins.dto.open.CarModelReq;
import com.ebtins.dto.open.CarModelRes;
import com.ebtins.dto.open.CarRenewalReq;
import com.ebtins.dto.open.CarRenewalRes;

/**
 * @ClassName: TestRenewal
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年11月9日 上午10:08:09
 *
 */

public class TestRenewal {
	
	@Test
	public void testQueryCarModel() throws Exception {
		CarModelReq req = new CarModelReq();
		req.setBrandName("别克SGM6511GL8旅行车");
		CarModelRes res = QueryModel.queryCarModel(req);
		System.out.println("CarModelRes-->"+JSON.toJSONString(res));
	}

	@Test
	public void testCarRenewal() throws Exception {
        Renewal rn = new Renewal();
		CarRenewalReq req = new CarRenewalReq();
		req.setLicenseNo("粤B032ZD"); //粤BMD926  粤BRU212 粤B032ZD
		CarRenewalRes res = rn.getRenewal(req);
		System.out.println("CarRenewalRes-->"+JSON.toJSONString(res));
	}

}
