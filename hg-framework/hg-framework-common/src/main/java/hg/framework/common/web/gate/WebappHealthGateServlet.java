package hg.framework.common.web.gate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ServiceLoader;

/**
 * 平台健康检查入口
 * <pre>
 * 	在resources下建立META-INF/services/hg.framework.common.web.gate.WebappHealthGateServlet$HealthChecker文件，
 * 	内容格式为此{@link HealthChecker 接口}的实现类全名（包括包名），多个实现类换行写。
 * </pre>
 *
 * @author zhurz
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/gate/current-health")
public class WebappHealthGateServlet extends HttpServlet {

	/**
	 * 健康检查者
	 */
	public interface HealthChecker {

		/**
		 * 是否健康
		 *
		 * @return true=健康 false=不健康
		 */
		boolean check();
	}

	/**
	 * 监控检查者实例
	 */
	private ServiceLoader<HealthChecker> healthCheckers;

	/**
	 * 是否健康
	 *
	 * @return 1=健康 0=不健康
	 */
	private int checkHealth() {
		synchronized (this) {
			if (healthCheckers == null)
				healthCheckers = ServiceLoader.load(HealthChecker.class);
		}
		for (HealthChecker healthChecker : healthCheckers) {
			try {
				if (!healthChecker.check())
					return 0;
			} catch (Exception e) {
				return 0;
			}
		}
		return 1;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		writer.print(checkHealth());
		writer.close();
	}

}
