package hsl.pojo.dto.coupon;
import hsl.pojo.dto.BaseDTO;
/**
 * @类功能说明：卡券DTO
 * @类修改者：
 * @修改日期：2014年10月15日下午1:51:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：liusong
 * @创建时间：2014年10月15日下午1:51:58
 *
 */
@SuppressWarnings("serial")
public class UserSnapshotDTO extends BaseDTO{
	
    /**
     * 用户登录名
    */
    private String loginName;
    /**
    * 用户真实姓名
    */
    private String realName;
    /**
     * 手机号
     */
     private String mobile;
    /**
     * 邮箱
    */
    private String email;
    
    
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
