package slfx.xl.domain.model.line;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *@类功能说明：线路状态
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月11日下午4:51:08
 */
@Embeddable
public class LineStatusInfo {

	/**
	 * 线路状态
	 */
	@Column(name = "STATUS")
	private Integer status;
	
	/*** 未审核 **/
	public static Integer DO_NOT_AUDIT = 1;
	/*** 审核未通过 **/
	public static Integer AUDIT_FAIL = 2;
	/*** 已审核 **/
	public static Integer AUDIT_SUCCESS = 3;
	/*** 已上架 **/
	public static Integer UP = 4;
	/*** 已下架 **/
	public static Integer DOWN = 5;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}