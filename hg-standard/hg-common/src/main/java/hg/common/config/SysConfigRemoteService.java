package hg.common.config;

import hg.common.model.system.SysConfig;
import hg.common.page.Pagination;

import java.util.Map;

/**
 * 系统配置服务
 * @author zhurz
 */
public interface SysConfigRemoteService {

	/** 根据ID获取配置信息 */
	public SysConfig getSysConfigById(String id);
	
	/** 根据配置名获取配置信息 */
	public SysConfig getSysConfigByConfName(String confName);
	
	/** 插入配置 */
	public Integer insertSysConfig(SysConfig sysConfig);
	
	/** 更新配置 */
	public Integer updateSysConfig(SysConfig sysConfig);
	
	/** 根据ID删除配置 */
	public Integer deleteSysConfigById(String id);
	
	/** 根据配置名删除配置 */
	public Integer deleteSysConfigByConfName(String confName);

	/** 获取所有可用配置信息  confName:SysConfig  */
	public Map<String, SysConfig> getAllEnableSysConfig();
	
	/** 系统配置分页查找 */
	public Pagination findSysConfig(Pagination paging);
	
	/** 重新从数据库里取所有可用配置并更新至缓存 */
	public void reloadAllSysConfig();
	
}
