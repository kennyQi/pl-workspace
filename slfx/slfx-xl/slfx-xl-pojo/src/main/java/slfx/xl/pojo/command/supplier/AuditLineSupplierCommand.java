package slfx.xl.pojo.command.supplier;

import hg.common.component.BaseCommand;

/**
 * 
 * 
 *@类功能说明：审核线路供应商
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月5日下午2:42:32
 *
 */
@SuppressWarnings("serial")
public class AuditLineSupplierCommand extends BaseCommand{
	
	/**
	 * ID
	 */
	private String id;
	
	/**
	 * 审核状态
	 */
	private Integer status;
	
	/**
	 * 备注
	 */
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}
