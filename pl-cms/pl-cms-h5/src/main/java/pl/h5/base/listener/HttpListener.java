package pl.h5.base.listener;
import pl.h5.base.utils.HttpTracker;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
/**
 * HttpProtocal监听器
 * @author 胡永伟
 */
public class HttpListener implements ServletRequestListener {

	public void requestInitialized(ServletRequestEvent sre) {
		HttpTracker.setRequest(
				(HttpServletRequest) sre.getServletRequest());
	}

	public void requestDestroyed(ServletRequestEvent sre) {
		HttpTracker.removeRequest();
	}

}
