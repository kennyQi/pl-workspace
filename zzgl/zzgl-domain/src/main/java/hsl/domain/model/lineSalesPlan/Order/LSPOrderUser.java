package hsl.domain.model.lineSalesPlan.order;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
/**
* @类功能说明：订单的下单用户信息
* @类修改者：
* @公司名称： 浙江票量云科技有限公司
* @部门： 技术部
* @作者： chenxy
* @创建时间：  2015-11-28 14:59:19
* @版本： V1.0
*/
@Embeddable
public class LSPOrderUser implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 下单用户ID
	 */
	@Column(name = "USER_ID",length = 64)
	private String userId;
	/**
	 * 下单用户名
	 */
	@Column(name = "LOGIN_NAME",length = 64)
	private String loginName;
	/**
	 * 下单用户手机号
	 */
	@Column(name = "MOBILE",length = 64)
	private String mobile;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
