package hg.demo.member.service.qo.hibernate;

import hg.demo.member.common.domain.model.AuthRole;
import hg.demo.member.common.spi.qo.AuthUserSQO;
import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by admin on 2016/5/20.
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "authUserDAO")
public class AuthUserQO extends BaseHibernateQO<String> {

	/**
	 * 登录查询
	 */
	@QOAttr(name = "loginName",type = QOAttrType.EQ)
	private String login;

	/**
	 * 登录名
	 */
	@QOAttr(name = "loginName", type = QOAttrType.LIKE_ANYWHERE)
	private String loginName;

	/**
	 * 密码
	 */
	@QOAttr(name = "passwd", type = QOAttrType.EQ)
	private String passwd;

	/**
	 * 显示名称
	 */
	@QOAttr(name = "displayName", type = QOAttrType.LIKE_ANYWHERE)
	private String displayName;
    /**
     * 查询唯一显示名
     */
	@QOAttr(name = "displayName", type = QOAttrType.EQ)
	private String display;
	

	/**
	 * 帐号状态 1启用 0禁用
	 */
	@QOAttr(name = "enable", type = QOAttrType.EQ)
	private Short enable;

	/**
	 * 内连接查询角色
	 */
	@QOAttr(name = "authRoleSet", type = QOAttrType.LEFT_JOIN)
	private AuthRoleQO authRoleSet;

	/**
	 * 开始时间
	 */
	@QOAttr(name = "createDate", type = QOAttrType.GE)
	private Date begincreateDate;


	/**
	 * 结束时间
	 */
	@QOAttr(name = "endcreateDate", type = QOAttrType.LE)
	private Date endcreateDate;

	/**
	 * 排序
	 */
	@QOAttr(name = "createDate",type = QOAttrType.ORDER)
	private Short ordercreatetime;

	public static AuthUserQO build(AuthUserSQO sqo) {
		AuthUserQO qo = new AuthUserQO();
		qo.setId(sqo.getId());
		qo.setLoginName(sqo.getLoginName());
		qo.setEnable(sqo.getEnable());
		qo.setDisplayName(sqo.getDisplayName());
		qo.setBegincreateDate(sqo.getBegincreateTime());
		qo.setEndcreateDate(sqo.getEndcreateTime());
      
//		if (StringUtils.isNotBlank(sqo.getRoleid())) {
//			AuthRoleQO authRoleQO = new AuthRoleQO();
//			authRoleQO.setId(sqo.getRoleid());
//		}

		qo.setLimit(sqo.getLimit());

		return qo;
	}
	
	

	public String getDisplay() {
		return display;
	}



	public void setDisplay(String display) {
		this.display = display;
	}



	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Short getEnable() {
		return enable;
	}

	public void setEnable(Short enable) {
		this.enable = enable;
	}

	public AuthRoleQO getAuthRoleSet() {
		return authRoleSet;
	}

	public void setAuthRoleSet(AuthRoleQO authRoleSet) {
		this.authRoleSet = authRoleSet;
	}

	public Date getBegincreateDate() {
		return begincreateDate;
	}

	public void setBegincreateDate(Date begincreateDate) {
		this.begincreateDate = begincreateDate;
	}

	public Date getEndcreateDate() {
		return endcreateDate;
	}

	public void setEndcreateDate(Date endcreateDate) {
		this.endcreateDate = endcreateDate;
	}

	public Short getOrdercreatetime() {
		return ordercreatetime;
	}

	public void setOrdercreatetime(Short ordercreatetime) {
		this.ordercreatetime = ordercreatetime;
	}
}
