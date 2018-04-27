package hg.fx.spi.qo;

import hg.framework.common.base.BaseSPIQO;
/**
 * 南航文件查询sqo
 * @author zqq
 * @date 2016-7-7上午10:10:03
 * @since
 */
public class CZFileSQO  extends BaseSPIQO{

	/**  */
	private static final long serialVersionUID = 1L;
	private String id;
	private String status;
	private String fileName;
	public String startDate;
	public String endDate;
	public String type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
