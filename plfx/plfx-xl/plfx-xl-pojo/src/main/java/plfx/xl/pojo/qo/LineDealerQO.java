package plfx.xl.pojo.qo;

import java.util.Date;
import hg.common.component.BaseQo;

/**
 * @类功能说明：经销商QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：
 * @创建时间：2014年12月2日下午5:55:34
 * @版本：V1.0
 */
public class LineDealerQO extends BaseQo{
	private static final long serialVersionUID = -8867152941708217110L;
	/**
	 * 经销商名称
	 */
	private String name;
	/**
	 * 经销商代码
	 */
	private String code;
	
	/**
	 * 开始生效时间
	 */
	private Date beginTime;
	
	/**
	 * 结束生效时间
	 */
	private Date endTime;
	
	/**
	 * 排序条件
	 */
	private Boolean createDateAsc;
	
	/**
	 * 禁用启用状态
	 */
	private String status;

	public LineDealerQO(){
		super();
	}
	public LineDealerQO(String id){
		super();
		setId(id);
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}
	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}