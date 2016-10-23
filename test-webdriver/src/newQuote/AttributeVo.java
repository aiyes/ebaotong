/**
 * 
 */
package newQuote;

import java.io.Serializable;

/**
 * @ClassName: AttributeVo
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年9月23日 上午9:35:03
 *
 */
public class AttributeVo implements Serializable {

		private static final long serialVersionUID = -307311955995760869L;
		
		private String name;
		
		private String newValue;
		
		private String bakValue;
		
		private String value;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNewValue() {
			return newValue;
		}

		public void setNewValue(String newValue) {
			this.newValue = newValue;
		}

		public String getBakValue() {
			return bakValue;
		}

		public void setBakValue(String bakValue) {
			this.bakValue = bakValue;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
}
