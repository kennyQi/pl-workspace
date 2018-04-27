package hgria.admin.auth;


import hg.common.util.LogFlow;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;
import hg.system.service.SecurityService;
import java.util.Collection;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm {

	private static LogFlow logger = new LogFlow("MyShiroRealm", MyShiroRealm.class, LogFlow.LOG_LEVEL_DEBUG);

	@Autowired
	private SecurityService securityService;

	/**
	 * 获取授权信息
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.output("获取授权信息");
		String loginName = (String) principals.fromRealm(getName()).iterator().next();
		if (loginName != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 查询用户授权信息
			Collection<String> pers = securityService.findUserPerms(loginName);
			Collection<String> rols = securityService.findUserRoles(loginName);
			if (pers != null && !pers.isEmpty()) {
				info.addStringPermissions(pers);
			}
			if (rols != null && !rols.isEmpty()) {
				info.addRoles(rols);
			}
			return info;
		}
		return null;
	}

	/**
	 * 获取认证信息
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		logger.output("获取认证信息");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// 通过表单接收的用户名
		String loginName = token.getUsername();
		if (StringUtils.isNotBlank(loginName)) {
			AuthUser loginUser = securityService.findUserByLoginName(loginName);
			if (loginUser != null) {
				if (SecurityConstants.USER_DISABLE.equals(loginUser.getEnable())) {
					throw new LockedAccountException();
				}
				return new SimpleAuthenticationInfo(loginUser.getLoginName(), loginUser.getPasswd(), getName());
			}
		}
		return null;
	}

}