package hgfx.web.component.health;

import hg.framework.common.web.gate.WebappHealthGateServlet;

/**
 * 应用健康检查
 *
 * @author zhurz
 */
public class MyHealthChecker implements WebappHealthGateServlet.HealthChecker {
	@Override
	public boolean check() {
		return true;
	}
}
