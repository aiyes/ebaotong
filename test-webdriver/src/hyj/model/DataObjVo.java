package hyj.model;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: DataObjVo
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年9月23日 上午10:10:04
 *
 */
public class DataObjVo implements Serializable {

	private static final long serialVersionUID = 3598936481011547738L;
	
	private Integer index;
	
	private Boolean selected;
	
	private String status;
	
	private List<AttributeVo> attributeVoList;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}


	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<AttributeVo> getAttributeVoList() {
		return attributeVoList;
	}

	public void setAttributeVoList(List<AttributeVo> attributeVoList) {
		this.attributeVoList = attributeVoList;
	}
	
	
	

}
