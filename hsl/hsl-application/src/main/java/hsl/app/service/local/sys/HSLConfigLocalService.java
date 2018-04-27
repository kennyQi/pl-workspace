package hsl.app.service.local.sys;

import hg.common.component.BaseAmqpMessage;
import hg.common.component.BaseServiceImpl;
import hsl.app.dao.sys.HSLConfigDAO;
import hsl.domain.model.sys.HSLConfig;
import hsl.domain.model.sys.wx.WxAutoReplyConfig;
import hsl.domain.model.sys.wx.WxMenuCoinfig;
import hsl.pojo.command.config.wx.ModifyWxAutoReplyConfigCommand;
import hsl.pojo.command.config.wx.ModifyWxMenuConfigCommand;
import hsl.pojo.qo.sys.HSLConfigQO;
import hsl.pojo.util.HSLConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 汇商旅配置服务
 *
 * @author zhurz
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HSLConfigLocalService extends BaseServiceImpl<HSLConfig, HSLConfigQO, HSLConfigDAO> {

	@Autowired
	private HSLConfigDAO dao;
	@Autowired
	private RabbitTemplate template;

	@Override
	protected HSLConfigDAO getDao() {
		return dao;
	}

	/**
	 * 获取微信自动回复配置
	 *
	 * @return
	 */
	public WxAutoReplyConfig getWxAutoReplyConfig() {
		HSLConfig hslConfig = getDao().get(HSLConstants.HSLConfigKey.KEY_WX_REPLY);
		if (hslConfig != null)
			return hslConfig.manager().parseAndGetValue(WxAutoReplyConfig.class);
		return null;
	}

	/**
	 * 获取微信菜单配置
	 *
	 * @return
	 */
	public WxMenuCoinfig getWxMenuCoinfig() {
		HSLConfig hslConfig = getDao().get(HSLConstants.HSLConfigKey.KEY_WX_MENU);
		if (hslConfig != null)
			return hslConfig.manager().parseAndGetValue(WxMenuCoinfig.class);
		return null;
	}

	/**
	 * 修改微信公众号菜单
	 *
	 * @param command
	 */
	public void modifyWxMenuConfig(ModifyWxMenuConfigCommand command) {
		HSLConfig config = getDao().get(HSLConstants.HSLConfigKey.KEY_WX_MENU);
		if (config == null)
			config = new HSLConfig();
		config.manager().modifyWxMenuConfig(command);
		getDao().saveOrUpdate(config);
		template.convertAndSend("hslh5.WxMenuModify", new BaseAmqpMessage<String>(0, "WxMenuModify"));
	}

	/**
	 * 修改微信自动回复
	 *
	 * @param command
	 */
	public void modifyWxAutoReplyConfig(ModifyWxAutoReplyConfigCommand command) {
		HSLConfig config = getDao().get(HSLConstants.HSLConfigKey.KEY_WX_REPLY);
		if (config == null)
			config = new HSLConfig();
		config.manager().modifyWxAutoReplyConfig(command);
		getDao().saveOrUpdate(config);
	}

}
