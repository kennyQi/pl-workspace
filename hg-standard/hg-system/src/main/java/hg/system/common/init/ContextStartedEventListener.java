package hg.system.common.init;

import hg.common.util.SpringContextUtil;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @类功能说明：SPRING初始化完毕后的监听器
 * @类修改者：
 * @修改日期：2015-4-8上午10:11:54
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-8上午10:11:54
 */
@Component
public class ContextStartedEventListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private SpringContextUtil contextUtil;
	
	private boolean runFlag;
	
	@Override
	public synchronized void onApplicationEvent(ContextRefreshedEvent event) {
		
		if (runFlag) return;
		runFlag = true;
		
		contextUtil.setApplicationContext(event.getApplicationContext());

		final Map<String, InitBean> map = event.getApplicationContext()
				.getBeansOfType(InitBean.class);

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
