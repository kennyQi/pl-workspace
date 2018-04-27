package hg.log.util;

import hg.common.util.SpringContextUtil;
import hg.common.util.SysProperties;
import hg.log.po.normal.HgLog;
import hg.log.repository.LogRepository;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class HgLogger {

	private String projectId;

	private String envId;

	private LogRepository logRepository;

	private static HgLogger hgLogger;

	private final static Logger logger = LoggerFactory.getLogger(HgLogger.class);

	/**
	 * 日志队列最大数量
	 */
	private int hgLogQueueMaxNum = 2000;

	/**
	 * 日志任务线程池
	 */
	private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

	/**
	 * @类功能说明：日志保存任务
	 * @类修改者：
	 * @修改日期：2014-10-13下午5:26:30
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：zhurz
	 * @创建时间：2014-10-13下午5:26:30
	 */
	public static class HgLogSaveTask implements Runnable {

		private HgLog hgLog;
		private LogRepository logRepository;

		public HgLogSaveTask(HgLog hgLog, LogRepository logRepository) {
			this.logRepository = logRepository;
			this.hgLog = hgLog;
		}

		@Override
		public void run() {
			try {
				logRepository.save(hgLog);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * @方法功能说明：异步保存日志
	 * @修改者名字：zhurz
	 * @修改时间：2014-10-13下午5:26:19
	 * @修改内容：
	 * @参数：@param log
	 * @return:void
	 * @throws
	 */
	public void saveLog(HgLog log) {
		synchronized (executor) {
			if (executor.getQueue().size() >= hgLogQueueMaxNum) {
				logger.warn("日志队列已满，无法继续接收保存请求。");
				return;
			}
			executor.submit(new HgLogSaveTask(log, logRepository));
		}
	}

	public static HgLogger getInstance() {
		if (null == hgLogger) {
			ApplicationContext context = SpringContextUtil.getApplicationContext();
			if (context == null)
				throw new RuntimeException("SPRING环境尚未初始化");
			try {
				// SPRING配置文件里已经声明过的日志
				hgLogger = context.getBean(HgLogger.class);
			} catch (Exception e) { }
			if (hgLogger == null) {
				SysProperties pro = SysProperties.getInstance();
				String projectId = pro.get("projectId");
				String envId = pro.get("envId");
				LogRepository logRepository = context.getBean(LogRepository.class);
				hgLogger = new HgLogger(projectId, envId, logRepository);
				hgLogger.setProjectId(projectId);
				hgLogger.setEnvId(envId);
			}
		}
		return hgLogger;
	}

	public HgLogger(String projectId, String envId, LogRepository logRepository) {
		this.projectId = projectId;
		this.envId = envId;
		this.logRepository = logRepository;
	}

	public HgLog debug(String devName, String content) {
		HgLog log = initHgLog(null, devName, content, null, (String[]) null);
		log.setLevel(HgLog.HGLOG_LEVEL_DEBUG);
		saveLog(log);
		return log;
	}

	public HgLog info(String devName, String content) {
		HgLog log = initHgLog(null, devName, content, null, (String[]) null);
		log.setLevel(HgLog.HGLOG_LEVEL_INFO);
		saveLog(log);
		return log;
	}

	public HgLog warn(String devName, String content) {
		HgLog log = initHgLog(null, devName, content, null, (String[]) null);
		log.setLevel(HgLog.HGLOG_LEVEL_WARN);
		saveLog(log);
		return log;
	}

	public HgLog error(String devName, String content) {
		HgLog log = initHgLog(null, devName, content, null, (String[]) null);
		log.setLevel(HgLog.HGLOG_LEVEL_ERROR);
		saveLog(log);
		return log;
	}

	public HgLog debug(Class<?> clazz, String devName, String content,
			String... tags) {
		HgLog log = initHgLog(clazz, devName, content, null, tags);
		log.setLevel(HgLog.HGLOG_LEVEL_DEBUG);
		saveLog(log);
		return log;
	}

	public HgLog info(Class<?> clazz, String devName, String content,
			String... tags) {

		HgLog log = initHgLog(clazz, devName, content, null, tags);
		log.setLevel(HgLog.HGLOG_LEVEL_INFO);
		saveLog(log);
		return log;
	}

	public HgLog warn(Class<?> clazz, String devName, String content,
			String... tags) {
		HgLog log = initHgLog(clazz, devName, content, null, tags);
		log.setLevel(HgLog.HGLOG_LEVEL_WARN);
		saveLog(log);
		return log;
	}

	public HgLog error(Class<?> clazz, String devName, String content,
			Exception e, String... tags) {
		HgLog log = initHgLog(clazz, devName, content, getStackTrace(e), tags);
		log.setLevel(HgLog.HGLOG_LEVEL_ERROR);
		saveLog(log);
		return log;
	}

	private HgLog initHgLog(Class<?> clazz, String devName, String content,
			String errorInfo, String... tags) {
		HgLog log = new HgLog();
		log.setProjectId(projectId);
		log.setEnvId(envId);
		if (clazz != null) {
			log.setClassName(clazz.getName());
		}
		log.setDeveloperName(devName);
		log.setContent(content);
		log.setErrorInfoStack(errorInfo);
		log.setTags(tags);
		log.setCreateDate(new Date());
		return log;
	}

	/**
	 * 将异常堆栈转换为字符串
	 * 
	 * @param aThrowable
	 *            异常
	 * @return String
	 */
	public static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getEnvId() {
		return envId;
	}

	public void setEnvId(String envId) {
		this.envId = envId;
	}

	public LogRepository getLogRepository() {
		return logRepository;
	}

	public void setLogRepository(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

}
