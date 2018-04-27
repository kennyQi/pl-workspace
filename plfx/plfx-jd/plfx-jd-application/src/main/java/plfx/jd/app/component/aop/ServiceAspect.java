package plfx.jd.app.component.aop;

import hg.common.component.BaseCommand;
import hg.common.component.BaseQo;
import hg.common.page.Pagination;
import hg.log.po.normal.HgLog;
import hg.log.util.HgLogger;

import org.aspectj.lang.ProceedingJoinPoint;

import plfx.api.client.base.slfx.ApiPayload;
import plfx.api.client.base.slfx.ApiResponse;

import com.alibaba.fastjson.JSON;

public class ServiceAspect {

	/**
	 * 是否需要AOP记录日志
	 */
	private boolean monitor = true;
	
	public boolean isMonitor() {
		return monitor;
	}
	
	public void setServiceAopHglog(String monitor) {
		if (monitor == null)
			return;
		this.monitor = monitor.trim().toLowerCase().equals("false") ? false : true;
	}
	
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

		HgLog log = null;
		StringBuilder sb = null;
		
		if (isMonitor()) {

			Object[] objs = pjp.getArgs();
			sb = new StringBuilder("执行service方法-->");
			sb.append(pjp.getTarget().getClass().getName()).append(".");
			sb.append(pjp.getSignature().getName()).append("(");
			for (int i = 0; i < objs.length; i++) {

				if (i != 0)
					sb.append(", ");

				Object obj = objs[i];

				if (obj == null)
					sb.append("null");
				else if (obj instanceof ApiPayload || obj instanceof BaseQo || obj instanceof BaseCommand || obj instanceof Pagination)
					sb.append(JSON.toJSONString(obj));
				else if (obj instanceof Number)
					sb.append(obj);
				else if (obj instanceof CharSequence)
					sb.append("\"").append(obj.toString().replace("\"", "\\\"")).append("\"");
				else
					sb.append("obj");

			}
			sb.append(");");

			// 记录方法执行日志
			try {
				log = HgLogger.getInstance().debug(pjp.getTarget().getClass(),"aop", sb.toString(), "aop");
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}
		
		long timebegin = System.currentTimeMillis();
		Object returnObj = null;
		try {
			return returnObj = pjp.proceed();
		} catch (Exception e) {
			if (sb != null && isMonitor()){
				sb.append("-->异常对象hashCode:").append(e.hashCode());
				HgLogger.getInstance().error(pjp.getTarget().getClass(), "aop", String.format("Service方法执行异常(hashCode:%d)", e.hashCode()), e, "aop 异常");
			}
			throw e;
		} finally {
			if (log != null && sb != null && isMonitor()) {
				long time = System.currentTimeMillis() - timebegin;
				// 记录API返回
				if (returnObj != null && returnObj instanceof ApiResponse) {
					sb.append("-->API返回：");
					sb.append(JSON.toJSONString(returnObj));
				}
				// 追加耗时
				sb.append("-->耗时");
				sb.append(time);
				sb.append("毫秒。");
				log.setContent(sb.toString());
				HgLogger.getInstance().saveLog(log);
			}
		}
	}

}
