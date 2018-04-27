package hsl.h5.base.weixin;

import com.alibaba.fastjson.JSON;
import hg.log.util.HgLogger;
import hsl.app.component.config.SysProperties;
import hsl.app.service.local.sys.HSLConfigLocalService;
import hsl.domain.model.sys.wx.WxMenuCoinfig;
import hsl.h5.base.utils.WxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信菜单处理器
 *
 * @author zhurz
 */
@Component
public class WxMenuModifyHandler {

	@Autowired
	private HSLConfigLocalService hslConfigLocalService;

	/**
	 * 更新微信菜单
	 */
	public void updateWxMenu() {

		// 获取微信菜单配置
		WxMenuCoinfig wxMenuCoinfig = hslConfigLocalService.getWxMenuCoinfig();

		// 如果已经有配置则删除旧菜单并更新
		if (wxMenuCoinfig != null && wxMenuCoinfig.getButton().size() > 0) {

			String wxMenu = JSON.toJSONString(wxMenuCoinfig);
			HgLogger.getInstance().info("zhurz", "更新微信菜单->" + wxMenu);

			TokenAndExpiresOfWx object = WxUtil.getWxAccessTokenFromWx(SysProperties.getInstance().get("wx_app_id"),
					SysProperties.getInstance().get("wx_app_secret"));
			String accessToken = object.getAccess_token();
			HgLogger.getInstance().info("zhurz", "更新微信菜单->获取微信访问指令：" + accessToken);

			try {
				WxUtil.delCustomMenusToWx(accessToken);
				HgLogger.getInstance().info("zhurz", "更新微信菜单->删除原有微信自定义菜单");
				WxUtil.addCustomMenusToWx(accessToken, wxMenu);
				HgLogger.getInstance().info("zhurz", "更新微信菜单->成功");
			} catch (Exception e) {
				HgLogger.getInstance().error("zhurz", "更新微信菜单->异常" + HgLogger.getStackTrace(e));
			}

		} else {
			HgLogger.getInstance().info("zhurz", "微信菜单未配置");
		}

	}

}
