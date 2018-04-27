package hg.demo.web.task;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import freemarker.template.utility.DateUtil;

public class EveryDayTask {

	public EveryDayTask() {
		super();
	}

	protected void startEveryDayTask(String hhmm, TimerTask task) {
		// 设置执行时间
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);// 每天
	
		String[] time = hhmm.split(":");
		// 定制每天执行，
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
//		 System.out.println(String.format("每日任务%s已设置，首次定时在%s",task,hg.fx.util.DateUtil.formatDateTime4(date)));
	}

	private Date addDay(Date date, int num) { 
	  Calendar startDT = Calendar.getInstance(); 
	  startDT.setTime(date); 
	  startDT.add(Calendar.DAY_OF_MONTH, num); 
	  return startDT.getTime(); 
	}

}