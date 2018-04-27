package hg.payment.pojo.command.admin;

import hg.common.component.BaseCommand;
import hg.payment.pojo.command.admin.dto.ModifyRefundDTO;

import java.util.List;

/**
 * 
 * 
 *@类功能说明：订单退款信息修改
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月27日下午1:51:16
 *
 */
public class ModifyAlipayRefundCommand extends BaseCommand{
	
	
	private static final long serialVersionUID = 1L;


	/**
	 * 修改退款信息基本数据
	 */
	private List<ModifyRefundDTO> modifyRefundDTOList;
	

	/**
	 * 支付宝退款批次号
	 */
	private String refundBatchNo;



	public String getRefundBatchNo() {
		return refundBatchNo;
	}


	public void setRefundBatchNo(String refundBatchNo) {
		this.refundBatchNo = refundBatchNo;
	}


	public List<ModifyRefundDTO> getModifyRefundDTOList() {
		return modifyRefundDTOList;
	}


	public void setModifyRefundDTOList(List<ModifyRefundDTO> modifyRefundDTOList) {
		this.modifyRefundDTOList = modifyRefundDTOList;
	}
	
	
	
	
	

}
