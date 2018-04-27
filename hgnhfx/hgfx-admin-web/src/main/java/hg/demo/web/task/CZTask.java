/**
 * @TimeTest.java Create on 2015-6-11上午10:27:24
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.web.task;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2015-6-11上午10:27:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2015-6-11上午10:27:24
 * @version：
 */
 
import java.util.TimerTask;

import hg.fx.spi.CzFileSPI;
import hg.fx.spi.MileOrderSPI;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("czTask")
public class CZTask extends EveryDayTask {
	@Autowired
	CzFileSPI  czFileSPI;
	@Autowired
	MileOrderSPI mileOrderService;
	//public static String CZPATH = (String) TaskProperty.getProperties().get("czPath")+"/sum";
	public void CZJob() {
		String getFileTime = (String) TaskProperty.getProperties().get("sendToCzTime");
		TimerTask task = new GenCzFileTask(czFileSPI, mileOrderService);

		startEveryDayTask(getFileTime, task);
	}
	 

}