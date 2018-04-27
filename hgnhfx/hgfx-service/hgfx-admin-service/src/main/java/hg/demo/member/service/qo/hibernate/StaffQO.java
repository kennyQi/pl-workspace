package hg.demo.member.service.qo.hibernate;

import hg.demo.member.common.spi.qo.StaffSQO;
import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import org.apache.commons.lang.StringUtils;

/**
 * Created by admin on 2016/5/20.
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "staffDAO")
public class StaffQO extends BaseHibernateQO {
	/**
	 * 员工真实姓名
	 */
	@QOAttr(name = "staffBaseInfo.realName", type = QOAttrType.LIKE_ANYWHERE)
	private String realName;
	/**
	 * 员工手机号
	 */
	@QOAttr(name = "staffBaseInfo.mobile", type = QOAttrType.LIKE_ANYWHERE)
	private String mobile;
	/**
	 * 电子邮箱
	 */
	@QOAttr(name = "staffBaseInfo.email", type = QOAttrType.LIKE_ANYWHERE)
	private String email;

	/**
	 * 电话
	 */
	@QOAttr(name = "staffBaseInfo.tel", type = QOAttrType.LIKE_ANYWHERE)
	private String tel;

	/**
	 * 内连接查询权限用户
	 */
	@QOAttr(name = "authUser", type = QOAttrType.JOIN)
	private AuthUserQO authUserQO;

	/**
	 * 是否获取角色
	 */
	private boolean authRoleSetFetch;

	public static StaffQO build(StaffSQO staffSQO) {
		StaffQO qo = new StaffQO();
		qo.setId(staffSQO.getId());
		qo.setRealName(staffSQO.getRealName());
		qo.setMobile(staffSQO.getMobile());
		qo.setEmail(staffSQO.getEmail());
		qo.setTel(staffSQO.getTel());
		qo.getAuthUserQO().setLoginName(staffSQO.getLoginName());
		qo.getAuthUserQO().setEnable(staffSQO.getEnable());
		qo.getAuthUserQO().setBegincreateDate(staffSQO.getCreateDateBegin());
		qo.getAuthUserQO().setEndcreateDate(staffSQO.getCreateDateEnd());
		qo.getAuthUserQO().setOrdercreatetime((short)-1);
		qo.setAuthRoleSetFetch(staffSQO.isQueryAuthRole());
		if (StringUtils.isNotBlank(staffSQO.getRoleId())) {
			AuthRoleQO authRoleQO = new AuthRoleQO();
			authRoleQO.setId(staffSQO.getRoleId());
			qo.getAuthUserQO().setAuthRoleSet(authRoleQO);
		}

		qo.setLimit(staffSQO.getLimit());
		return qo;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public AuthUserQO getAuthUserQO() {
		if (authUserQO == null)
			authUserQO = new AuthUserQO();
		return authUserQO;
	}

	public void setAuthUserQO(AuthUserQO authUserQO) {
		this.authUserQO = authUserQO;
	}

	public boolean isAuthRoleSetFetch() {
		return authRoleSetFetch;
	}

	public void setAuthRoleSetFetch(boolean authRoleSetFetch) {
		this.authRoleSetFetch = authRoleSetFetch;
	}
}
