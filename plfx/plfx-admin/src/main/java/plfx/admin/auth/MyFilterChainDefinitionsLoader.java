package plfx.admin.auth;

import hg.common.util.LogFlow;
import hg.common.util.MD5HashUtil;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthPerm;
import hg.system.service.SecurityService;

import java.util.List;

import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 加载权限资源
 * 
 * @author zhurz
 */
public class MyFilterChainDefinitionsLoader implements Runnable {

	private static LogFlow logger = new LogFlow("MyFilterChainDefinitionsLoader", MyFilterChainDefinitionsLoader.class, LogFlow.LOG_LEVEL_DEBUG);

	@Autowired
	private SecurityService securityService;
	private AbstractShiroFilter shiroFilter;

	synchronized public void loadShiroFilterMap() {

		logger.record("-------------加载访问资源和权限-------------");

		// map.put("/", "anon");
		// map.put("/member/loginSubmit*", "authc,perms[all]");
		// map.put("/**", "authc");

		try {
			PathMatchingFilterChainResolver pmfcr = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
			logger.record("getFilterChainResolver:" + pmfcr);
			DefaultFilterChainManager dfcm = (DefaultFilterChainManager) pmfcr.getFilterChainManager();
			logger.record("getFilterChainManager:" + dfcm);
			// -------------加载访问资源和权限-------------
			List<AuthPerm> permAllList = securityService.findAllPerms();
			logger.record("findAllPerms:" + permAllList.size());
			for (AuthPerm perm : permAllList) {
				if (perm != null) {
					if (SecurityConstants.PERM_TYPE_AUTH.equals(perm.getPermType())) {
						dfcm.createChain(perm.getUrl(), "authc,perms[" + MD5HashUtil.toMD5(perm.getUrl()) + "]");
						logger.record(perm.getUrl() + "=" + "authc,perms[" + MD5HashUtil.toMD5(perm.getUrl()) + "]");
					} else if (SecurityConstants.PERM_TYPE_ROLE.equals(perm.getPermType())) {
						dfcm.createChain(perm.getUrl(), "authc,roles[" + perm.getPermRole() + "]");
						logger.record(perm.getUrl() + "=" + "authc,roles[" + perm.getPermRole() + "]");
					} else if (SecurityConstants.PERM_TYPE_LOGIN.equals(perm.getPermType())) {
						dfcm.createChain(perm.getUrl(), "authc");
						logger.record(perm.getUrl() + "=" + "authc");
					}
				}
			}
			logger.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AbstractShiroFilter getShiroFilter() {
		return shiroFilter;
	}

	public void setShiroFilter(AbstractShiroFilter shiroFilter) {
		this.shiroFilter = shiroFilter;
	}

	@Override
	public void run() {
		loadShiroFilterMap();
	}

	public void initLoad() {
		// 另开一个线程，防止SERVER未启动时卡在加载方法这。
		Thread thread = new Thread(this);
		thread.start();
	}
}
