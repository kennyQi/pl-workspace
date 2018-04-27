package hsl.domain.model.user.account;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.account.HoldUserSnapshotCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 卡券持有用户快照
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月15日上午8:56:08
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日上午8:56:08
 * 
 */
@Entity
@Table(name = M.TABLE_PREFIX_HSL_ACCOUNT + "USER_SNAPSHOT")
public class HoldUserSnapshot extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户标识
	 */
	@Column(name = "USERID", length = 64)
	private String userId;
	/**
	 * 用户登录名
	 */
	@Column(name = "LOGINNAME", length = 64)
	private String loginName;
	/**
	 * 用户真实姓名
	 */
	@Column(name = "REALNAME", length = 64)
	private String realName;
	/**
	 * 手机号
	 */
	@Column(name = "MOBILE", length = 64)
	private String mobile;
	/**
	 * 邮箱
	 */
	@Column(name = "EMAIL", length = 64)
	private String email;

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
	/**
	 * 用户充值
	 */
	public HoldUserSnapshot recharge(HoldUserSnapshotCommand command){
		HoldUserSnapshot holdUserSnapshot=new HoldUserSnapshot();
		holdUserSnapshot.setId(UUIDGenerator.getUUID());
		holdUserSnapshot.setLoginName(command.getLoginName());
		holdUserSnapshot.setEmail(command.getEmail());
		holdUserSnapshot.setMobile(command.getMobile());
		holdUserSnapshot.setRealName(command.getRealName());
		holdUserSnapshot.setUserId(command.getUserId());
		return holdUserSnapshot;
	}
	/**
	 * 保存用户
	 */
	public void create(HoldUserSnapshotCommand command){
		this.setId(UUIDGenerator.getUUID());
		this.setUserId(command.getUserId());
		this.setEmail(command.getEmail());
		this.setLoginName(command.getLoginName());
		this.setMobile(command.getMobile());
		this.setRealName(command.getRealName());
	}
}