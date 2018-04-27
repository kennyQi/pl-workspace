package hsl.h5.base.listener;

import hg.common.component.BaseAmqpMessage;
import hg.log.util.HgLogger;
import hsl.h5.base.weixin.WxMenuModifyHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * H5消息监听器
 *
 * @author zhurz
 */
public class MessageListener {

	@Autowired
	private WxMenuModifyHandler handler;

	private Lock lock = new ReentrantLock();

	public void listen(Object message) {

		if (!(message instanceof BaseAmqpMessage))
			return;
		BaseAmqpMessage msg = (BaseAmqpMessage) message;

		if (!"WxMenuModify".equals(msg.getContent()))
			return;

		try {
			HgLogger.getInstance().info("zhurz", "更新微信菜单");
			// 尝试加锁
			if (lock.tryLock(1, TimeUnit.MINUTES)) {
				// 更新微信菜单
				handler.updateWxMenu();
			}
		} catch (InterruptedException e) {
			HgLogger.getInstance().error("zhurz", "更新微信菜单失败->" + HgLogger.getStackTrace(e));
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}

}
