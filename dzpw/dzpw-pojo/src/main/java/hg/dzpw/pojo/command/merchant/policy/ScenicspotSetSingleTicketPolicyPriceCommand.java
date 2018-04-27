package hg.dzpw.pojo.command.merchant.policy;

import hg.dzpw.pojo.common.DZPWMerchantBaseCommand;
import hg.dzpw.pojo.exception.DZPWException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明：设置独立单票的价格日历
 * @类修改者：
 * @修改日期：2015-2-26下午5:40:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-26下午5:40:37
 */
@SuppressWarnings("serial")
public class ScenicspotSetSingleTicketPolicyPriceCommand extends DZPWMerchantBaseCommand {

	/**
	 * 所属门票政策（价格日历只和独立门票政策关联）
	 */
	private String ticketPolicyId;

	/**
	 * 是否为门票基准价，与经销商订的价格都为false
	 */
	private Boolean standardPrice;

	/**
	 * 对应经销商（为基准价时忽略经销商ID）
	 */
	private String dealerId;

	/**
	 * 价格日历
	 */
	private List<DatePrice> datePrices = new ArrayList<DatePrice>();
	
	/**
	 * @方法功能说明：命令检查
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-5下午3:27:21
	 * @修改内容：
	 * @参数：@throws DZPWException
	 * @return:void
	 * @throws
	 */
	public void check() throws DZPWException {
		
		if (ticketPolicyId == null)
			throw new DZPWException(DZPWException.TICKET_POLICY_ID_IS_NULL,
					"缺少门票政策ID");

		if (datePrices == null)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "价格日历缺少每日价格");
		
		// 检查日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (DatePrice datePrice : datePrices) {
			try {
				sdf.parse(datePrice.getDate());
			} catch (ParseException e) {
				throw new DZPWException(DZPWException.MESSAGE_ONLY, "日期格式错误:" + datePrice.getDate());
			}
		}

		if (StringUtils.isBlank(dealerId)) {
			setStandardPrice(true);
			setDealerId(null);
		} else
			setStandardPrice(false);
	}

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public Boolean getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(Boolean standardPrice) {
		this.standardPrice = standardPrice;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public List<DatePrice> getDatePrices() {
		return datePrices;
	}

	public void setDatePrices(List<DatePrice> datePrices) {
		this.datePrices = datePrices;
	}

}
