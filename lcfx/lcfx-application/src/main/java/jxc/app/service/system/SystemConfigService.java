package jxc.app.service.system;

import hg.common.component.BaseQo;
import hg.common.component.BaseServiceImpl;
import hg.pojo.command.ModifySystemConfigCommand;
import jxc.app.dao.system.SystemConfigDao;
import jxc.app.util.JxcLogger;
import jxc.domain.model.system.SystemConfig;
import jxc.domain.model.system.SystemConfigSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class SystemConfigService extends BaseServiceImpl<SystemConfig, BaseQo, SystemConfigDao> {
	@Autowired
	private SystemConfigDao systemConfigDao;

	@Override
	protected SystemConfigDao getDao() {
		return systemConfigDao;
	}

	@Autowired
	private JxcLogger logger;

	public SystemConfigSet querySystemConfig() {
		SystemConfigSet systemConfigSet = new SystemConfigSet();

		SystemConfig systemConfig = get(SystemConfigSet.KEY_PURCHASER_INFO_LINKMAN_NAME);
		if (systemConfig != null) {
			systemConfigSet.setLinkmanName(systemConfig.getConfigValue());
		}
		systemConfig = get(SystemConfigSet.KEY_PURCHASER_INFO_PHONE);
		if (systemConfig != null) {
			systemConfigSet.setPhone(systemConfig.getConfigValue());
		}
		systemConfig = get(SystemConfigSet.KEY_PURCHASER_INFO_FAX);
		if (systemConfig != null) {
			systemConfigSet.setFax(systemConfig.getConfigValue());
		}
		systemConfig = get(SystemConfigSet.KEY_PURCHASER_INFO_CONTACT_ADDRESS);
		if (systemConfig != null) {
			systemConfigSet.setContactAddress(systemConfig.getConfigValue());
		}

		return systemConfigSet;

	}

	public void modifySystemConfig(ModifySystemConfigCommand command) {

		SystemConfig systemConfig = get(SystemConfigSet.KEY_PURCHASER_INFO_LINKMAN_NAME);
		if (systemConfig == null) {
			systemConfig = new SystemConfig();
			systemConfig.modifySystemConfig(SystemConfigSet.KEY_PURCHASER_INFO_LINKMAN_NAME, command.getLinkmanName());
			save(systemConfig);
		} else {
			systemConfig.modifySystemConfig(SystemConfigSet.KEY_PURCHASER_INFO_LINKMAN_NAME, command.getLinkmanName());
			update(systemConfig);
		}

		systemConfig = null;
		systemConfig = get(SystemConfigSet.KEY_PURCHASER_INFO_PHONE);
		if (systemConfig == null) {
			systemConfig = new SystemConfig();
			systemConfig.modifySystemConfig(SystemConfigSet.KEY_PURCHASER_INFO_PHONE, command.getPhone());
			save(systemConfig);
		} else {
			systemConfig.modifySystemConfig(SystemConfigSet.KEY_PURCHASER_INFO_PHONE, command.getPhone());
			update(systemConfig);
		}

		systemConfig = null;
		systemConfig = get(SystemConfigSet.KEY_PURCHASER_INFO_FAX);
		if (systemConfig == null) {
			systemConfig = new SystemConfig();
			systemConfig.modifySystemConfig(SystemConfigSet.KEY_PURCHASER_INFO_FAX, command.getFax());
			save(systemConfig);
		} else {
			systemConfig.modifySystemConfig(SystemConfigSet.KEY_PURCHASER_INFO_FAX, command.getFax());
			update(systemConfig);
		}

		systemConfig = null;
		systemConfig = get(SystemConfigSet.KEY_PURCHASER_INFO_CONTACT_ADDRESS);
		if (systemConfig == null) {
			systemConfig = new SystemConfig();
			systemConfig.modifySystemConfig(SystemConfigSet.KEY_PURCHASER_INFO_CONTACT_ADDRESS, command.getContactAddress());
			save(systemConfig);
		} else {
			systemConfig.modifySystemConfig(SystemConfigSet.KEY_PURCHASER_INFO_CONTACT_ADDRESS, command.getContactAddress());
			update(systemConfig);
		}

		logger.simpleDebuglog(command, this.getClass(), JSON.toJSONString(command));

	}

}
