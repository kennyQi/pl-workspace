package plfx.mp.pojo.dto.scenicspot;

import java.io.Serializable;

/**
 * @类功能说明：同程景点主题
 * @类修改者：
 * @修改日期：2014-9-15下午1:51:47
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-9-15下午1:51:47
 */
public class TCScenicSpotsThemeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 景点主题ID
	 */
	private String themeId;

	/**
	 * 景点主题名称
	 */
	private String themeName;

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

}
