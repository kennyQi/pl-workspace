package hg.dzpw.dealer.client.api.v1.response;

import hg.dzpw.dealer.client.common.ApiPageResponse;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyDTO;

import java.util.List;

/**
 * @类功能说明：门票政策查询结果
 * @类修改者：
 * @修改日期：2014-11-24上午11:00:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-24上午11:00:10
 */
@SuppressWarnings("serial")
public class TicketPolicyResponse extends ApiPageResponse {

	/** 缺少必填参数 */
	public static final String RESULT_REQUIRED_PARAM = "-1";

	/**
	 * 门票政策列表
	 */
	private List<TicketPolicyDTO> ticketPolicies;
	
	public List<TicketPolicyDTO> getTicketPolicies() {
		return ticketPolicies;
	}

	public void setTicketPolicies(List<TicketPolicyDTO> ticketPolicies) {
		this.ticketPolicies = ticketPolicies;
	}

}
