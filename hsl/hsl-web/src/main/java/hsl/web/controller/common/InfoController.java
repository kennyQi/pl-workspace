package hsl.web.controller.common;

import hg.common.util.DateUtil;
import hsl.app.common.util.AppInfoUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;

/**
 * 服务器信息
 *
 * @author zhurz
 * @since 1.7.1
 */
@Controller
public class InfoController {

	/**
	 * JVM启动时间
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/jvm-start-date")
	public String jvmStartDate() {
		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		String version = AppInfoUtils.getVersion();
		return String.format("%s start on %s", version, DateUtil.formatDateTime(new Date(bean.getStartTime()), "yyyy-MM-dd HH:mm:ss"));
	}

}
