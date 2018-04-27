package hsl.pojo.dto.programa;

import hsl.pojo.dto.BaseDTO;

import java.util.Date;
import java.util.List;

/**
 * @类功能说明：栏目DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @部门：技术部
 * @作者：zhaows
 * @创建时间：2015年4月21日
 *
 */
@SuppressWarnings("serial")
public class ProgramaDTO extends BaseDTO{
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
	 /** 创建时间*/
	private Date createDate;
	private List<ProgramaContentDTO>programaContentList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Integer getLocation() {
		return location;
	}
	public void setLocation(Integer location) {
		this.location = location;
	}
	public List<ProgramaContentDTO> getProgramaContentList() {
		return programaContentList;
	}
	public void setProgramaContentList(List<ProgramaContentDTO> programaContentList) {
		this.programaContentList = programaContentList;
	}
	
	
}
