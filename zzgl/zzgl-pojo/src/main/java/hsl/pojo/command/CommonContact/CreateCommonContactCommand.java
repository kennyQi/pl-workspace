package hsl.pojo.command.CommonContact;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：创建常用联系人command
 * @类修改者：
 * @修改日期：2015年9月6日上午10:36:14
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年9月6日上午10:36:14
 */
public class CreateCommonContactCommand extends BaseCommand{
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 证件类型
	 */
	private Integer cardType;
	/**
	 * 证件号码
	 */
	private String cardNo;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * 用户id
	 */
	private String userId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCardType() {
		return cardType;
	}
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
