/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.junit.Test;

import com.ebtins.dto.open.CarOwnerInfoVo;
import com.ebtins.dto.open.CarRenewalRes;

import ebtins.smart.proxy.company.renbao.util.RenbaoConfig;

public class TestConf {

	@Test
	public void test() throws IOException {
		String str = "%B6%AB%B7%E7%C8%D5%B2%FAEQ7204AC%BD%CE%B3%B5";
		String newStr = URLDecoder.decode(str,"gbk");
		System.out.println(newStr);
	}

}
