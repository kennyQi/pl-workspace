package hsl.h5.base.init;

import hg.log.util.HgLogger;
import hg.system.common.init.InitBean;
import hsl.h5.base.weixin.WxMenuModifyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhurz
 */
@Component
public class WxMenuInit implements InitBean {

	@Autowired
	private WxMenuModifyHandler handler;

	@Override
	public void springContextStartedRun() throws Exception {
		HgLogger.getInstance().info("zhurz", "更新微信菜单开始");
		handler.updateWxMenu();
		HgLogger.getInstance().info("zhurz", "更新微信菜单完毕");
	}
}
