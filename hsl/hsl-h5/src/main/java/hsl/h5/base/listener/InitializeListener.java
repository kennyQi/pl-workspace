package hsl.h5.base.listener;

import hg.common.util.JsonUtil;
import hg.log.util.HgLogger;
import hsl.app.component.config.SysProperties;
import hsl.h5.base.utils.IOUtil;
import hsl.h5.base.utils.WxUtil;
import hsl.h5.base.weixin.ButtonOfWxCustomMenu;
import hsl.h5.base.weixin.TokenAndExpiresOfWx;
import hsl.h5.base.weixin.WxCustomMenu;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 初始化监听
 * @author 胡永伟
 */
public class InitializeListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {

		if ("127.0.0.1".equals(SysProperties.getInstance().get("host"))) {
			HgLogger.getInstance().info("zhurz", "host为127.0.0.1将跳过微信菜单初始化");
			return;
		}

		HgLogger.getInstance().info("chenxy", "商旅平台初始化加载...");
		TokenAndExpiresOfWx object = WxUtil.getWxAccessTokenFromWx(SysProperties.getInstance().get("wx_app_id"), SysProperties.getInstance().get("wx_app_secret"));
		String accessToken = object.getAccess_token();
		HgLogger.getInstance().info("chenxy", "获取微信访问指令：" + accessToken);
		try {
			WxUtil.delCustomMenusToWx(accessToken);
			HgLogger.getInstance().info("chenxy", "删除原有微信自定义菜单...");
			String wxMenu = getWxMenuJson(sce);
			WxUtil.addCustomMenusToWx(accessToken, wxMenu);
			HgLogger.getInstance().info("chenxy", "重新加载微信自定义菜单...>>" + wxMenu);
		} catch (Exception e) {
			HgLogger.getInstance().error("chenxy", "商旅平台初始化失败>>>>" + HgLogger.getStackTrace(e));
			throw new RuntimeException(e);
		}
		HgLogger.getInstance().info("chenxy", "商旅平台初始化完成...");
	}
	
	@SuppressWarnings("unchecked")
	private String getWxMenuJson(ServletContextEvent sce) {
		InputStream in = null;
		try {
			in = this.getClass().getClassLoader().getResourceAsStream("wxmenu.xml");
			SAXReader saxReader = new SAXReader();
	        Document document = saxReader.read(in);
	        Element root = document.getRootElement();
	        List<Element> list = root.elements();
	        ButtonOfWxCustomMenu buttonOfWxCustomMenu = new ButtonOfWxCustomMenu();
	        List<WxCustomMenu> wxCustomMenus = new ArrayList<WxCustomMenu>();
	        Boolean isRoot = Boolean.parseBoolean(SysProperties.getInstance().get("root"));
	        String proj = sce.getServletContext().getContextPath();
	        String port = SysProperties.getInstance().get("port");
	        String system = "http://" + SysProperties.getInstance().get("host") + ("80".equals(port) ? "" : ":" + port);
	        if (!isRoot) {
	        	system += proj;
	        }
			for(int i = 0; list != null && i < list.size(); i++) {
				WxCustomMenu wxCustomMenu = new WxCustomMenu();
				Element e = list.get(i);
				if ("p_menu".equalsIgnoreCase(e.getName())) {
					String sub = e.attributeValue("sub");
					String name = e.attributeValue("name");
					wxCustomMenu.setName(name);
					if (Boolean.parseBoolean(sub)) {
						List<Element> subs = e.elements();
						List<WxCustomMenu> subMenus = new ArrayList<WxCustomMenu>();
						for (int j = 0; subs != null && j < subs.size(); j++) {
							Element se = subs.get(j);
							if ("s_menu".equalsIgnoreCase(se.getName())) {
								WxCustomMenu subMenu = new WxCustomMenu();
								subMenu.setName(se.attributeValue("name"));
								String stype = se.attributeValue("type");
								subMenu.setType(stype);
								if ("view".equals(stype)) {
									String url = se.attributeValue("url");
									url = url.replace("${system}", system);
									subMenu.setUrl(url);
								} else if ("click".equals(stype)) {
									subMenu.setKey(se.attributeValue("key"));
								}
								subMenus.add(subMenu);
							}
						}
						wxCustomMenu.setSub_button(subMenus);
					} else {
						String type = e.attributeValue("type");
						wxCustomMenu.setType(type);
						if ("view".equals(type)) {
							String url = e.attributeValue("url");
							url = url.replace("${system}", system);
							wxCustomMenu.setUrl(url);
						} else if ("click".equals(type)) {
							wxCustomMenu.setKey(e.attributeValue("key"));
						}
					}
				}
				wxCustomMenus.add(wxCustomMenu);
				buttonOfWxCustomMenu.setButton(wxCustomMenus);
			}
			return JsonUtil.parseObject(buttonOfWxCustomMenu, false);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtil.close(in);
		}
	}
	
	public void contextDestroyed(ServletContextEvent sce) {}

}
