package hg.framework.common.base.log;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * JDK日志追加记录初始化（用于日志记录汇总）
 *
 * @author zhurz
 */
@Component
public class JDKLoggerInit implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		// jul to slf4j
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
	}

}
