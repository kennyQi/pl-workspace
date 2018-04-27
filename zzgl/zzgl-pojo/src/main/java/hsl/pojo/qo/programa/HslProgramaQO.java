package hsl.pojo.qo.programa;

import java.util.Date;

import hg.common.component.BaseQo;
@SuppressWarnings("serial")
public class HslProgramaQO extends BaseQo{
	/**栏目名称*/
	private String name;
	/**栏目位置*/
	private Integer location=0;
	public static final Integer homePage=1;//首页
	public static final Integer line_home= 2;//线路首页
	/**栏目状态*/
	private Integer status=0;
	public static final Integer ALL=0;//全部
	public static final Integer SHOW = 1;//显示
	public static final Integer NOT_SHOW = 2;//隐藏
	 /**创建时间*/
	private Date createDate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLocation() {
		return location;
	}
	public void setLocation(Integer location) {
		this.location = location;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
