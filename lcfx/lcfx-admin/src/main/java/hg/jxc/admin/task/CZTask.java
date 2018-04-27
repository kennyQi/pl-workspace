/**
 * @TimeTest.java Create on 2015-6-11上午10:27:24
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.jxc.admin.task;

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
import hg.hjf.cz.CzFileService;
import hg.jxc.admin.common.TaskProperty;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import jxc.app.service.distributor.MileOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("czTask")
public class CZTask {
	@Autowired
	CzFileService czFileService;
	@Autowired
	MileOrderService mileOrderService;
	//public static String CZPATH = (String) TaskProperty.getProperties().get("czPath")+"/sum";
	public void CZJob() {
		String getFileTime = (String) TaskProperty.getProperties().get("sendToCzTime");
		TimerTask task = new GenCzFileTask(czFileService, mileOrderService);

		// 设置执行时间
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);// 每天

		String[] time = getFileTime.split(":");
		// 定制每天的00:00:00执行，
		calendar.set(year, month, day, Integer.parseInt(time[0]),
				Integer.parseInt(time[1]), 00);
		Date date = calendar.getTime();
		if (date.before(new Date())) { 
		      date = this.addDay(date, 1); 
		} 
		Timer timer = new Timer();

		int period =24*60*60*1000;
		// 每天的date时刻执行task，每隔period秒重复执行
		 timer.schedule(task, date, period);
		// 每天的date时刻执行task, 仅执行一次
		//timer.schedule(task, date);
	}
	 // 增加或减少天数 
	 public Date addDay(Date date, int num) { 
	  Calendar startDT = Calendar.getInstance(); 
	  startDT.setTime(date); 
	  startDT.add(Calendar.DAY_OF_MONTH, num); 
	  return startDT.getTime(); 
	}
	 

}