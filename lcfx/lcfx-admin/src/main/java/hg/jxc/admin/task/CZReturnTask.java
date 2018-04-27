/**
 * 
 */
package hg.jxc.admin.task;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import jxc.domain.model.cz.CZFile;

import hg.common.util.ClassPathTool;
import hg.common.util.UUIDGenerator;
import hg.hjf.cz.CzFileService;
import hg.jxc.admin.common.TaskProperty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Administrator 积分系统后台任务
 */
@Component("czReturnTask")
public class CZReturnTask {
	@Autowired
	CzFileService czFileService;
	static Log logger = LogFactory.getLog(CZReturnTask.class);

	public void taskJob() {

		ClassPathTool.getInstance();
		String webRootPath = ClassPathTool.getWebRootPath().replace("file:", "");
		String[] pathArr = webRootPath.split("/");
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
						czFileService.update(czFile);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
				while (true) {
					try {
						Thread.sleep(10*1000);
						logger.debug("getCzReturnFile");
						taskJob();
						logger.debug("getCzReturnFileEnd");
						Thread.sleep(getFileTime*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

}
