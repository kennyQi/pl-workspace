package hg.demo.web.task;

 
import hg.fx.spi.CzFileSPI;
import hg.fx.spi.MileOrderSPI;

import java.io.IOException;
import java.util.TimerTask;

 

public class GenCzFileTask extends TimerTask {
	private CzFileSPI czFileService;
	private MileOrderSPI mileOrderService;
	@Override
	public void run() {
		try {
//			System.out.println(toString()+ " 执行。。。");
			 czFileService.genFile(TaskProperty.getProperties().getProperty("czPath"), null, "date");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//mileOrderService.sendOrder(sendMileOrderList);

	}
	public GenCzFileTask(CzFileSPI czFileService, MileOrderSPI mileOrderService) {
		this.czFileService = czFileService;
		this.mileOrderService = mileOrderService;
	}

	@Override
	public String toString() {
		return "GenCzFileTask";
	}
}
