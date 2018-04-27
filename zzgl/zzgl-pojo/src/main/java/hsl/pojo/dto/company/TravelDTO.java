package hsl.pojo.dto.company;

import hsl.pojo.dto.BaseDTO;

import java.util.Date;


/**
 * 差旅管理的DTO
 * @author zhuxy
 *
 */
@SuppressWarnings("serial")
public class TravelDTO extends BaseDTO{
	/**
	 * 订单号
	 */
	private String orderNum;
	/**
	 * 公司名字
	 */
	private String companyName;
	/**
	 * 部门名字
	 */
	private String deptName;
	/**
	 * 职位
	 */
	private String job;
	/**
	 * 成员名字
	 */
	private String memberName;
	/**
	 * 项目类型;
	 */
	private Integer projectType;
	/**
	 * 目的地
	 */
	private String destination;
	/**
	 * 出行日期
	 */
	private Date tarvelDate;
	/**
	 * 金额
	 */
	private Double price;
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Integer getProjectType() {
		return projectType;
	}
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Date getTarvelDate() {
		return tarvelDate;
	}
	public void setTarvelDate(Date tarvelDate) {
		this.tarvelDate = tarvelDate;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

}
