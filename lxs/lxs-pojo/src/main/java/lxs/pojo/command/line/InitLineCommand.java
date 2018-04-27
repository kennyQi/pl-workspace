package lxs.pojo.command.line;

import hg.common.component.BaseCommand;

import java.util.Date;

@SuppressWarnings("serial")
public class InitLineCommand extends BaseCommand{
	/**
	 * 更新的起始时间
	 */
	private Date startDate;
	/**
	 * 更新的结束事件
	 */
	private Date endDate;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
