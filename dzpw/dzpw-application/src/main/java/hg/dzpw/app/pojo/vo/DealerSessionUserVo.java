package hg.dzpw.app.pojo.vo;

import hg.dzpw.domain.model.dealer.Dealer;

import java.io.Serializable;
/**
 * 
 * @类功能说明：经销商会话session对象
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015-12-4下午4:40:30
 * @版本：
 */
public class DealerSessionUserVo implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 景区ID
	 */
	private String dealerId;

	/**
	 * 景区名称
	 */
	private String dealerName;

	/**
	 * 登录名
	 */
	private String loginName;


	/**
	 * 经销商管理员登录
	 */
	public DealerSessionUserVo(Dealer dealer) {
		setDealerId(dealer.getId());
		setDealerName(dealer.getBaseInfo().getName());
		setLoginName(dealer.getClientInfo().getAdminLoginName());
	}


	public String getDealerId() {
		return dealerId;
	}


	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}


	public String getDealerName() {
		return dealerName;
	}


	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}
