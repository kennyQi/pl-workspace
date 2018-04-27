package hsl.domain.model.sys.wx;

import hsl.pojo.command.config.wx.ModifyWxMenuConfigCommand;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 微信公众号菜单配置
 *
 * @author zhurz
 */
public class WxMenuCoinfig {

	/**
	 * 一级菜单按妞
	 */
	private List<WxMenuItem> button;

	public List<WxMenuItem> getButton() {
		if (button == null)
			button = new ArrayList<WxMenuItem>();
		return button;
	}

	public void setButton(List<WxMenuItem> button) {
		this.button = button;
	}

	// --------------------------------------------------------------------

	private transient Manager manager;

	public Manager manager() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}

	public class Manager {

		private List<WxMenuItem> convertList(List<ModifyWxMenuConfigCommand.Item> itemList) {
			if (CollectionUtils.isEmpty(itemList))
				return null;
			List<WxMenuItem> list = new ArrayList<WxMenuItem>();
			for (ModifyWxMenuConfigCommand.Item item : itemList)
				list.add(convert(item));
			return list;
		}

		private WxMenuItem convert(ModifyWxMenuConfigCommand.Item item) {
			WxMenuItem wxMenuItem = new WxMenuItem();
			wxMenuItem.setName(item.getName());
			if (CollectionUtils.isEmpty(item.getSub_button())) {
				wxMenuItem.setType("view");
				wxMenuItem.setUrl(item.getUrl());
			} else {
				wxMenuItem.setSub_button(convertList(item.getSub_button()));
			}
			return wxMenuItem;
		}
		/**
		 * 修改微信公众号菜单
		 *
		 * @param command
		 */
		public void modify(ModifyWxMenuConfigCommand command) {
			setButton(convertList(command.getMenuItemList()));
		}
	}
}
