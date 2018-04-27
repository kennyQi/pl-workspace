package jxc.app.util;

import hg.common.util.SpringContextUtil;
import hg.common.util.SysProperties;
import hg.log.po.normal.HgLog;
import hg.pojo.command.JxcCommand;
import hg.pojo.dto.log.JxcLog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import jxc.app.repository.JxcRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JxcLogger {

	private String projectId;

	private String envId;

	private static JxcLogger jxcLogger;

	private JxcRepository jxcRepository;

	private final static Logger logger = LoggerFactory.getLogger(JxcLogger.class);

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
	 */
	public static class JxcLogSaveTask implements Runnable {

		private JxcLog jxcLog;
		private JxcRepository jxcRepository;

		public JxcLogSaveTask(JxcLog jxcLog, JxcRepository jxcRepository) {
			this.jxcRepository = jxcRepository;
			this.jxcLog = jxcLog;
		}

		@Override
		public void run() {
			try {
				jxcRepository.save(jxcLog);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * @方法功能说明：异步保存日志
	 * @修改内容：
	 * @参数：@param log
	 * @return:void
	 * @throws
	 */
	public void saveLog(JxcLog log) {
		synchronized (executor) {
			if (executor.getQueue().size() >= hgLogQueueMaxNum) {
				logger.warn("日志队列已满，无法继续接收保存请求。");
				return;
			}
			executor.submit(new JxcLogSaveTask(log, jxcRepository));
		}
	}

	public static JxcLogger getInstance() {
		SysProperties pro = SysProperties.getInstance();
		String projectId = pro.get("projectId");
		String envId = pro.get("envId");
		if (null == jxcLogger) {
			JxcRepository jxcRepository = (JxcRepository) SpringContextUtil.getBean("jxcRepository");
			jxcLogger = new JxcLogger(projectId, envId, jxcRepository);
		} else {
			jxcLogger.setProjectId(projectId);
			jxcLogger.setEnvId(envId);
		}

		return jxcLogger;
	}

	public JxcLogger(String projectId, String envId, JxcRepository jxcRepository) {
		this.projectId = projectId;
		this.envId = envId;
		this.jxcRepository = jxcRepository;
	}

	public JxcLog debug(Class<?> clazz, String devName, String content, String operatorName, String operatorType, String operatorAccount, String... tags) {
		JxcLog log = initJxcLog(clazz, devName, content, null, operatorName, operatorType, operatorAccount, tags);
		log.setLevel(HgLog.HGLOG_LEVEL_DEBUG);
		saveLog(log);
		return log;
	}

	public HgLog info(Class<?> clazz, String devName, String content, String operatorName, String operatorType, String operatorAccount, String... tags) {

		JxcLog log = initJxcLog(clazz, devName, content, null, operatorName, operatorType, operatorAccount, tags);
		log.setLevel(HgLog.HGLOG_LEVEL_INFO);
		saveLog(log);
		return log;
	}

	public HgLog warn(Class<?> clazz, String devName, String content, String operatorName, String operatorType, String operatorAccount, String... tags) {
		JxcLog log = initJxcLog(clazz, devName, content, null, operatorName, operatorType, operatorAccount, tags);
		log.setLevel(HgLog.HGLOG_LEVEL_WARN);
		saveLog(log);
		return log;
	}

	public HgLog error(Class<?> clazz, String devName, String content, String operatorName, String operatorType, String operatorAccount, Exception e,
			String... tags) {
		JxcLog log = initJxcLog(clazz, devName, content, null, operatorName, operatorType, operatorAccount, tags);
		log.setLevel(HgLog.HGLOG_LEVEL_ERROR);
		saveLog(log);
		return log;
	}

	private JxcLog initJxcLog(Class<?> clazz, String devName, String content, String errorInfo, String operatorName, String operatorType,
			String operatorAccount, String... tags) {
		JxcLog log = new JxcLog();
		log.setProjectId(projectId);
		log.setEnvId(envId);
		if (clazz != null) {
			log.setClassName(clazz.getName());
		}
		log.setDeveloperName(devName);
		log.setContent(content);
		log.setErrorInfoStack(errorInfo);
		log.setOperatorName(operatorName);
		log.setOperatorAccount(operatorAccount);
		log.setOperatorType(operatorType);
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

	public JxcRepository getJxcRepository() {
		return jxcRepository;
	}

	public void setJxcRepository(JxcRepository jxcRepository) {
		this.jxcRepository = jxcRepository;
	}

	public void simpleDebuglog(JxcCommand command, Class<?> clazz, String content) {
		debug(clazz, "wkq", content, command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount());

	}

}
