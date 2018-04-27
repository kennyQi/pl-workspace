package hsl.domain.model.sys.wx;

import java.util.List;

/**
 * 微信菜单按钮
 *
 * @author zhurz
 */
public class WxMenuItem {

	/**
	 * 类型，没有子按钮的都为view，用于URL跳转
	 */
	private String type;

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
	private List<WxMenuItem> sub_button;

	public List<WxMenuItem> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<WxMenuItem> sub_button) {
		this.sub_button = sub_button;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
