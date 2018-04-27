package hg.jxc.admin.task;

import hg.common.util.ClassPathTool;
import hg.hjf.cz.CzFileService;
import hg.jxc.admin.common.TaskProperty;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

import jxc.app.service.distributor.MileOrderService;
import jxc.domain.model.distributor.MileOrder;

public class GenCzFileTask extends TimerTask {
	private CzFileService czFileService;
	private MileOrderService mileOrderService;
	@Override
	public void run() {
		ClassPathTool.getInstance();
		String webRootPath = ClassPathTool.getWebRootPath().replace("file:", "");
		String[] pathArr = webRootPath.split("/");
		String tcAppsPath = webRootPath.replace(pathArr[pathArr.length-1], "");
		List<MileOrder> sendMileOrderList = null;
		try {
			sendMileOrderList = czFileService.genFile(TaskProperty.getProperties().getProperty("czPath"), null, "date");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//mileOrderService.sendOrder(sendMileOrderList);

	}
	public GenCzFileTask(CzFileService czFileService, MileOrderService mileOrderService) {
		this.czFileService = czFileService;
		this.mileOrderService = mileOrderService;
	}

}
