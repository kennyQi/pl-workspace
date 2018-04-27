package hsl.web.util;
import hsl.app.component.config.SysProperties;
import org.springframework.web.context.ContextLoader;
/**
 * 系统公用工具包
 * @author chenxy
 */
public class CommonUtil {
	/**
	 * 获取webapp路径
	 * @return
	 */
	public static String getWebAppPath() {
		Boolean isRoot = Boolean.parseBoolean(SysProperties.getInstance().get("root"));
		String contextPath=ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath();
        String port = SysProperties.getInstance().get("port");
        String system = "http://" +SysProperties.getInstance().get("host") + ("80".equals(port) ? "" : ":" + port);
        if (!isRoot) {
        	system += contextPath;
        }
        return system;
	}
}
