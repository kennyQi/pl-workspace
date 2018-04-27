package hsl.pojo.dto.lineSalesPlan.order;
/**
* @类功能说明：订单联系人
* @类修改者：
* @公司名称： 浙江票量云科技有限公司
* @部门： 技术部
* @作者： chenxy
* @创建时间：  2015-11-28 14:55:51
* @版本： V1.0
*/
public class LSPOrderLinkInfoDTO {
	/**
	 * 联系人姓名
	 */
	private String linkName;
	
	/**
	 * 联系人手机号
	 */
	private String linkMobile;
	
	/**
	 * 联系人邮箱
	 */
	private String email;

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}