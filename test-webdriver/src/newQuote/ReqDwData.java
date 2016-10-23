package newQuote;

import java.io.Serializable;
import java.util.List;
/**
 * @ClassName: DwData
 * @Description: TODO()
 * @author yejie.huang
 * @date 2016年9月23日 上午10:02:23
 *
 */
public class ReqDwData implements Serializable {

	
	private static final long serialVersionUID = 5222694668336563L;
	
	private Boolean isFilter1;
	
	private Boolean isFilter;
	
	private String dwType;
	
	private String dwName;
	
	private Integer rsCount;
	
	private Integer pageSize;
	
	private Integer pageNo;
	
	private Integer pageCount;
	
	private Integer maxCount;
	
	private Boolean toAddFlag;
	
	private List filterMapList;
	
	private List<DataObjVo> dataObjVoList;

	public Boolean getIsFilter() {
		return isFilter;
	}

	public void setIsFilter(Boolean isFilter) {
		this.isFilter = isFilter;
	}

	public String getDwType() {
		return dwType;
	}

	public void setDwType(String dwType) {
		this.dwType = dwType;
	}

	public String getDwName() {
		return dwName;
	}

	public void setDwName(String dwName) {
		this.dwName = dwName;
	}

	public Integer getRsCount() {
		return rsCount;
	}

	public void setRsCount(Integer rsCount) {
		this.rsCount = rsCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	public Boolean getToAddFlag() {
		return toAddFlag;
	}

	public void setToAddFlag(Boolean toAddFlag) {
		this.toAddFlag = toAddFlag;
	}

	public List getFilterMapList() {
		return filterMapList;
	}

	public void setFilterMapList(List filterMapList) {
		this.filterMapList = filterMapList;
	}

	public List<DataObjVo> getDataObjVoList() {
		return dataObjVoList;
	}

	public void setDataObjVoList(List<DataObjVo> dataObjVoList) {
		this.dataObjVoList = dataObjVoList;
	}
	
	

}
