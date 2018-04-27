package hsl.pojo.qo.CommonContact;
import hg.common.component.BaseQo;
/**
 * @类功能说明：常用联系人QO
 * @类修改者：
 * @修改日期：2015年9月6日上午10:11:13
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年9月6日上午10:11:13
 */
public class CommonContactQO extends BaseQo{
	private static final long serialVersionUID = 1L;
	private String userId;
	private String name;
	private String cardNo;
	private String mobile;
	private Integer type;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	
}
