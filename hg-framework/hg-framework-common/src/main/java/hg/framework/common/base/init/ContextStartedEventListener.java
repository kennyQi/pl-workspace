package hg.framework.common.base.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Map.Entry;

/**
 * SPRING初始化完毕后的监听器
 *
 * @author zhurz
 */
@Component
public class ContextStartedEventListener implements ApplicationListener<ContextRefreshedEvent> {

	/**
	 * 运行标识
	 */
	private boolean runFlag;
	
	@Override
	public synchronized void onApplicationEvent(ContextRefreshedEvent event) {
		
		if (runFlag) return;
		runFlag = true;

		final Map<String, InitBean> map = event.getApplicationContext().getBeansOfType(InitBean.class);

		new Thread() {
			@Override
			public void run() {
				for (Entry<String, InitBean> entry : map.entrySet()) {
					try {
						InitBean initBean = entry.getValue();
						initBean.springContextStartedRun();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

}
