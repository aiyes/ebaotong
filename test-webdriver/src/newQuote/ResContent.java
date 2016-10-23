/**
 * 
 */
package newQuote;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: ResContent
 * @Description: TODO(返回报文封装)
 * @author yejie.huang
 * @date 2016年10月13日 下午1:30:20
 *
 */

public class ResContent implements Serializable {
	private static final long serialVersionUID = -3050823686118236845L;
	
	private String RESULT_TYPE;
	private String RESULT_MSG;
	private List<ReqDwData> WEB_DATA;
	
	public String getRESULT_MSG() {
		return RESULT_MSG;
	}
	public void setRESULT_MSG(String rESULT_MSG) {
		RESULT_MSG = rESULT_MSG;
	}
	public String getRESULT_TYPE() {
		return RESULT_TYPE;
	}
	public void setRESULT_TYPE(String rESULT_TYPE) {
		RESULT_TYPE = rESULT_TYPE;
	}
	public List<ReqDwData> getWEB_DATA() {
		return WEB_DATA;
	}
	public void setWEB_DATA(List<ReqDwData> wEB_DATA) {
		WEB_DATA = wEB_DATA;
	}
	

}
