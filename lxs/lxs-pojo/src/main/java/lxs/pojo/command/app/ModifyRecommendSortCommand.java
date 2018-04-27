package lxs.pojo.command.app;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ModifyRecommendSortCommand extends BaseCommand {
	
	/**
	 * 推荐ID
	 */
	private String recommendID;

	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 移动类型
	 */
	private String type;

	public String getRecommendID() {
		return recommendID;
	}

	public void setRecommendID(String recommendID) {
		this.recommendID = recommendID;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
