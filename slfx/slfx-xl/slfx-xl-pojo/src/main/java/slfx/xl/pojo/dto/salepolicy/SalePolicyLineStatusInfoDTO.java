package slfx.xl.pojo.dto.salepolicy;

import slfx.xl.pojo.dto.EmbeddDTO;

/**
 * 
 * @类功能说明：政策状态DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月12日下午5:10:19
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class SalePolicyLineStatusInfoDTO extends EmbeddDTO{

	/**
	 * 政策状态
	 */
	private Integer salePolicyLineStatus;

	public Integer getSalePolicyLineStatus() {
		return salePolicyLineStatus;
	}

	public void setSalePolicyLineStatus(Integer salePolicyLineStatus) {
		this.salePolicyLineStatus = salePolicyLineStatus;
	}
}
