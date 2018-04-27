/**
 * 
 */
package hg.demo.web.task;

import hg.fx.domain.CZFile;
import hg.fx.spi.CzFileSPI;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Administrator 积分系统后台任务
 */
@Component("czReturnTask")
public class CZReturnTask {
	@Autowired
	CzFileSPI czFileService;
	static Log logger = LogFactory.getLog(CZReturnTask.class);

	public void taskJob() {


		String tcAppsPath = TaskProperty.getProperties().getProperty("czPath");
		
		List<CZFile> FileList = czFileService.getSendCzFile();
		logger.debug("待处理南航文件" + FileList.size() + "个");
		for (CZFile czFile : FileList) {
			String returnFileName = czFile.getFileName().replace(".", "_CZ.");
			String filePath = tcAppsPath + returnFileName;
			File file = new File(filePath);
			if (file.exists()) {
				
				logger.debug("找到反馈文件：" + filePath);
				try {
					if (czFileService.handleFile(filePath) == czFileService.CZ_FILE_SUCCEED) {
						czFile.setStatus(CZFile.FEEDBACK);
						czFile.setFeedbacktime(new Date());
						czFileService.update(czFile);
					}
				} catch (IOException e) {
					//  
					e.printStackTrace();
				} catch (Exception e) {
					//  
					e.printStackTrace();
				}
			}else{
				logger.info("未找到反馈文件：" + filePath);
			}
		}
	}

	// 启动一个线程，执行批任务
	public void startTask() {
		new Thread() {
			public void run() {
				Integer getFileTime = Integer.valueOf((String) TaskProperty.getProperties().get("getFileTime"));
//				System.out.println(String.format("每%s分钟执行一次检查 南航反馈文件 任务。",getFileTime));
				while (true) {
					try {
						Thread.sleep(10*1000);
						logger.debug("getCzReturnFile");
						taskJob();
						logger.debug("getCzReturnFileEnd");
						Thread.sleep(getFileTime*60*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

}
