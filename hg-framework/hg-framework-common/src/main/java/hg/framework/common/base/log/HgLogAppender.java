package hg.framework.common.base.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.AppenderBase;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 汇购日志附加器（配置详见logback.xml）
 *
 * @author zhurz
 */
public class HgLogAppender extends AppenderBase<ILoggingEvent> {

	/**
	 * 日志配置
	 */
	private HgLogConfig config;

	private MongoClient mongoClient;

	private MongoClient getMongoClient() {

		if (mongoClient != null)
			return mongoClient;

		synchronized (this) {
			if (mongoClient == null) {
				String[] addrEntryArray = config.getAddress().split(",");
				List<ServerAddress> addresses = new ArrayList<>();
				for (String addrEntry : addrEntryArray) {
					String[] addr = addrEntry.split(":");
					String ip = addr[0];
					int port = Integer.valueOf(addr[1]);
					addresses.add(new ServerAddress(ip, port));
				}
				mongoClient = new MongoClient(addresses);
			}
		}

		return mongoClient;
	}

	@Override
	@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
	protected void append(ILoggingEvent eventObject) {

		// 日志级别
		Level level = Level.valueOf(eventObject.getLevel().toString());

		// 异常堆栈信息
		String errorStackInfo = null;
		IThrowableProxy throwableProxy = eventObject.getThrowableProxy();
		if (throwableProxy instanceof ThrowableProxy) {
			ThrowableProxy throwable = (ThrowableProxy) throwableProxy;
			Throwable throwable1 = throwable.getThrowable();
			StringWriter sw = new StringWriter();
			throwable1.printStackTrace(new PrintWriter(sw));
			errorStackInfo = sw.toString();
		}

		HgLog log = new HgLog(level, config.getProjectName(), config.getProjectVersion(), eventObject.getLoggerName(), eventObject.getFormattedMessage(),
				new Date(eventObject.getTimeStamp()), errorStackInfo);

		MongoDatabase database = getMongoClient().getDatabase(config.getDbName());
		MongoCollection<Document> collection = database.getCollection(config.getCollectionName());
		Document document = log.buildMongoDocument();
		try {
			collection.insertOne(document);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public HgLogConfig getConfig() {
		return config;
	}

	public void setConfig(HgLogConfig config) {
		this.config = config;
	}

	public static void main(String[] args) throws InterruptedException, UnknownHostException, SocketException {
		LoggerFactory.getLogger(HgLogAppender.class).info("这是一条测试日志{}", new Date());
		LoggerFactory.getLogger(HgLogAppender.class).error("这是一条测试异常", new RuntimeException("测试异常"));
		Thread.sleep(1000);
	}

}
