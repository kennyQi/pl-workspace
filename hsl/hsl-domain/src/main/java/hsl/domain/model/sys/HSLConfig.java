package hsl.domain.model.sys;

import com.alibaba.fastjson.JSON;
import hg.common.component.BaseModel;
import hsl.domain.model.M;
import hsl.domain.model.sys.wx.WxAutoReplyConfig;
import hsl.domain.model.sys.wx.WxMenuCoinfig;
import hsl.pojo.command.config.wx.ModifyWxAutoReplyConfigCommand;
import hsl.pojo.command.config.wx.ModifyWxMenuConfigCommand;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.util.HSLConstants;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 汇商旅配置
 * 配置KEY为ID
 *
 * @see HSLConstants.HSLConfigKey
 * @author zhurz
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_SYS + "HSL_CONFIG")
public class HSLConfig extends BaseModel implements HSLConstants.HSLConfigKey {

	/**
	 * 配置值
	 */
	@Column(name = "CONFIG_VALUE", columnDefinition = M.TEXT_COLUM)
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	// --------------------------------------------------------------------

	private transient Manager manager;

	public Manager manager() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}

	public class Manager {

		private void checkAndSetKey(String key) {
			if (StringUtils.isBlank(getId())) {
				setId(key);
			}else if (!getId().equals(key)) {
				throw new ShowMessageException("设置KEY失败");
			}
		}

		/**
		 * 修改微信公众号菜单
		 *
		 * @param command
		 */
		public void modifyWxMenuConfig(ModifyWxMenuConfigCommand command) {
			checkAndSetKey(KEY_WX_MENU);
			WxMenuCoinfig config = new WxMenuCoinfig();
			config.manager().modify(command);
			setValue(JSON.toJSONString(config));
		}

		/**
		 * 修改微信自动回复
		 *
		 * @param command
		 */
		public void modifyWxAutoReplyConfig(ModifyWxAutoReplyConfigCommand command) {
			checkAndSetKey(KEY_WX_REPLY);
			WxAutoReplyConfig config = new WxAutoReplyConfig();
			config.manager().modify(command);
			setValue(JSON.toJSONString(config));
		}

		/**
		 * 转换并获取对象
		 *
		 * @param clazz
		 * @param <T>
		 * @return
		 */
		public <T> T parseAndGetValue(Class<T> clazz) {
			if (StringUtils.isBlank(getValue()))
				return null;
			try {
				return JSON.parseObject(getValue(), clazz);
			} catch (Exception e) {
				return null;
			}
		}

	}
}
