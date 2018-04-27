package hsl.admin.auth;

import hg.common.util.MD5HashUtil;
import hg.common.util.MyBeanUtils;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthPerm;
import hg.system.service.SecurityService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 加载权限资源
 * 
 * @author zhurz
 */
public class MyFilterChainDefinitionsLoader implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(MyFilterChainDefinitionsLoader.class);

	@Autowired
	private SecurityService securityService;
	private AbstractShiroFilter shiroFilter;

    private Map<String, NamedFilterList> defaultFilterChains;
	
	@SuppressWarnings("unchecked")
	synchronized private void initDefaultFilterChains() {
		if (defaultFilterChains != null)
			return;
		PathMatchingFilterChainResolver pmfcr = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
		DefaultFilterChainManager dfcm = (DefaultFilterChainManager) pmfcr.getFilterChainManager();
		Map<String, NamedFilterList> map = (Map<String, NamedFilterList>) MyBeanUtils.getFieldValue(dfcm, "filterChains");
		defaultFilterChains = new LinkedHashMap<String, NamedFilterList>();
		defaultFilterChains.putAll(map);
	}
	
	/**
	 * @方法功能说明：重新加载访问资源和权限
	 * @修改者名字：zhurz
	 * @修改时间：2015-9-25下午2:37:15
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	synchronized public void reloadShiroFilterMap() {
		PathMatchingFilterChainResolver pmfcr = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
		DefaultFilterChainManager dfcm = (DefaultFilterChainManager) pmfcr.getFilterChainManager();
		Map<String, NamedFilterList> map = (Map<String, NamedFilterList>) MyBeanUtils.getFieldValue(dfcm, "filterChains");
		map.clear();
		if (defaultFilterChains != null)
			map.putAll(defaultFilterChains);
		loadShiroFilterMap();
	}

	synchronized public void loadShiroFilterMap() {

		logger.info("-------------加载访问资源和权限-------------");

		// map.put("/", "anon");
		// map.put("/member/loginSubmit*", "authc,perms[all]");
		// map.put("/**", "authc");

		try {
			initDefaultFilterChains();
			PathMatchingFilterChainResolver pmfcr = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
			logger.info("getFilterChainResolver:" + pmfcr);
			DefaultFilterChainManager dfcm = (DefaultFilterChainManager) pmfcr.getFilterChainManager();
			logger.info("getFilterChainManager:" + dfcm);
			// -------------加载访问资源和权限-------------
			List<AuthPerm> permAllList = securityService.findAllPerms();
			logger.info("findAllPerms:" + permAllList.size());
			for (AuthPerm perm : permAllList) {
				if (perm != null) {
					if (SecurityConstants.PERM_TYPE_AUTH.equals(perm.getPermType())) {
						dfcm.createChain(perm.getUrl(), "authc,perms[" + MD5HashUtil.toMD5(perm.getUrl()) + "]");
						logger.info(perm.getUrl() + "=" + "authc,perms[" + MD5HashUtil.toMD5(perm.getUrl()) + "]");
					} else if (SecurityConstants.PERM_TYPE_ROLE.equals(perm.getPermType())) {
						dfcm.createChain(perm.getUrl(), "authc,roles[" + perm.getPermRole() + "]");
						logger.info(perm.getUrl() + "=" + "authc,roles[" + perm.getPermRole() + "]");
					} else if (SecurityConstants.PERM_TYPE_LOGIN.equals(perm.getPermType())) {
						dfcm.createChain(perm.getUrl(), "authc");
						logger.info(perm.getUrl() + "=" + "authc");
					}
				}
			}
		} catch (Exception e) {
			HgLogger.getInstance().error(MyFilterChainDefinitionsLoader.class, "zhurz", "加载访问资源和权限异常", e);
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
