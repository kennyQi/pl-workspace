package hsl.pojo.command;

import java.util.Date;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ModifyHotScenicSpotCommand extends BaseCommand{

	/**
	 * 热门景区id
	 */
	private String id;
	
	/**
	 * 加入当前热门景区时间
	 */
	private Date openDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

}
