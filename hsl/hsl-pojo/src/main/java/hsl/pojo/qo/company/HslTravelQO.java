package hsl.pojo.qo.company;
import hg.common.component.BaseQo;
/**
 * 差旅管理的QO
 * @author zhuxy
 *
 */
@SuppressWarnings("serial")
public class HslTravelQO extends BaseQo{
	/**
	 * 公司id
	 */
	private String companyId;
	/**
	 * 部门id
	 */
	private String departmentId;
	/**
	 * 成员id
	 */
	private String memberId;
	/**
	 * 项目类型(0:全部;1:机票;2:门票;3：易行机票)
	 */
	private int projectType;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束事件
	 */
	private String endTime;
	/**
	 * 页面大小
	 */
	private Integer pageSize = 5;
	/**
	 * 页码
	 */
	private Integer page = 1;
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Integer getProjectType() {
		return projectType;
	}
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
