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
		req.setBrandName("别克SGM6511GL8旅行车");//车型名称
		//req.getExt().put("vehicleId", "KLAACI0001");//车型编码
		//req.getExt().put("vehicleBrandName", "雪佛兰");//车辆品牌名称
		//req.getExt().put("brandId", "XFA");//车辆品牌型号
		//req.getExt().put("searchCode", "33");//快速查询码
		//req.getExt().put("vehicleAlias", "纽约人3.5i轿车 ");//车型别名
		CarModelRes res = QueryModel.queryCarModel(req);
		System.out.println("CarModelRes-->"+JSON.toJSONString(res));
	}

	@Test
	public void testCarRenewal() throws Exception {
         Renewal rn = new Renewal();
		 CarRenewalReq req = new CarRenewalReq();
		  req.setLicenseNo("粤B032ZD"); //粤BMD926  粤BRU212 粤B032ZD
		 //req.getExt().put("engineNo", "2");//发动机号
		 //req.getExt().put("frameNo", "2");//车架号
		 //req.getExt().put("licenseType", "33");//号牌类型 02-小型汽车
		 //req.getExt().put("vinNo", "33");//Vin号
		 //req.getExt().put("policyNo", "dd");//上年保单号
	
		CarRenewalRes res = rn.getRenewal(req);
		System.out.println("CarRenewalRes-->"+JSON.toJSONString(res));
	}
}
