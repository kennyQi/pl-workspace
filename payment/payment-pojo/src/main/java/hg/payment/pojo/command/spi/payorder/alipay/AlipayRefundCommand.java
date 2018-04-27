package hg.payment.pojo.command.spi.payorder.alipay;

import hg.common.component.BaseCommand;
import hg.payment.pojo.command.dto.AlipayRefundRequestDTO;

import java.util.List;


/**
 * 
 * 
 *@类功能说明：支付宝订单退款
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月26日下午5:52:52
 *
 */
public class AlipayRefundCommand extends BaseCommand{
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * 合作商户唯一ID
	 */
	private String partner;

	/**
	 * 支付宝商户唯一密钥
	 */
	private String keys;
	
	/**
	 * 退款列表
	 */
	private List<AlipayRefundRequestDTO> alipayRefundRequestDTOList;


	public String getPartner() {
		return partner;
	}


	public void setPartner(String partner) {
		this.partner = partner;
	}


	public String getKeys() {
		return keys;
	}


	public void setKeys(String keys) {
		this.keys = keys;
	}


	public List<AlipayRefundRequestDTO> getAlipayRefundRequestDTOList() {
		return alipayRefundRequestDTOList;
	}


	public void setAlipayRefundRequestDTOList(
			List<AlipayRefundRequestDTO> alipayRefundRequestDTOList) {
		this.alipayRefundRequestDTOList = alipayRefundRequestDTOList;
	}


	


	
	

}
