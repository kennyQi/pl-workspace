package slfx.xl.pojo.command.line;

import hg.common.component.BaseCommand;
import slfx.xl.pojo.command.line.dto.LineBaseInfoRequestDTO;
import slfx.xl.pojo.command.line.dto.LinePayInfoRequestDTO;

/**
 *@类功能说明：新增线路command
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月8日上午8:55:49
 */
@SuppressWarnings("serial")
public class CreateLineCommand extends BaseCommand{

	
	/**
	 * 线路基本信息
	 */
	private LineBaseInfoRequestDTO baseInfoDTO;
	
	/**
	 * 线路支付信息
	 */
	private LinePayInfoRequestDTO payInfoDTO;
	
	/**
	 * 供应商
	 */
	private String lineSupplierID;

	
	public LineBaseInfoRequestDTO getBaseInfoDTO() {
		return baseInfoDTO;
	}

	public void setBaseInfoDTO(LineBaseInfoRequestDTO baseInfoDTO) {
		this.baseInfoDTO = baseInfoDTO;
	}

	public LinePayInfoRequestDTO getPayInfoDTO() {
		return payInfoDTO;
	}

	public void setPayInfoDTO(LinePayInfoRequestDTO payInfoDTO) {
		this.payInfoDTO = payInfoDTO;
	}

	public String getLineSupplierID() {
		return lineSupplierID;
	}

	public void setLineSupplierID(String lineSupplierID) {
		this.lineSupplierID = lineSupplierID;
	}

	
	

	
	
	
	
	
}
