package hsl.pojo.command.config.wx;

import hg.common.component.BaseCommand;

import java.util.List;

/**
 * 修改微信菜单配置
 *
 * @author zhurz
 */
@SuppressWarnings("serial")
public class ModifyWxMenuConfigCommand extends BaseCommand {

	/**
	 * 菜单项
	 */
	private	List<Item> menuItemList;

	public List<Item> getMenuItemList() {
		return menuItemList;
	}

	public void setMenuItemList(List<Item> menuItemList) {
		this.menuItemList = menuItemList;
	}

	/**
	 * 微信菜单按钮
	 *
	 * @author zhurz
	 */
	public static class Item {

		/**
		 * 菜单标题，不超过16个字节，子菜单不超过40个字节
		 * 一级菜单最多4个汉字，二级菜单最多7个汉字
		 */
		private String name;

		/**
		 * 网页链接，用户点击菜单可打开链接，不超过256字节
		 */
		private String url;

		/**
		 * 二级菜单数组，个数应为1~5个
		 */
		private List<Item> sub_button;

		public List<Item> getSub_button() {
			return sub_button;
		}

		public void setSub_button(List<Item> sub_button) {
			this.sub_button = sub_button;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

}
