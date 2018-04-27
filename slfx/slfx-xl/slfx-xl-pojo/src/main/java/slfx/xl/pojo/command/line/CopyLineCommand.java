package slfx.xl.pojo.command.line;

import hg.common.component.BaseCommand;

/**
 * 
 * 
 *@类功能说明：线路复制command
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：liusong
 *@创建时间：2014年12月17日下午16:58:49
 *
 */
@SuppressWarnings("serial")
public class CopyLineCommand extends BaseCommand{

	
	/**
	 * 复制的线路ID
	 */
	private  String  lineID;
	
	/**
	 * 线路名称
	 */
	private String name;
	
	/**
	 * 编号
	 */
	private String number;
	
	/**
	 * 推荐级别
	 */
	private Integer recommendationLevel;
	
	/**
	 * 供应商
	 */
	private String lineSupplierID;
	
	/**
	 * 线路类别
	 */
	private Integer type;

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getRecommendationLevel() {
		return recommendationLevel;
	}

	public void setRecommendationLevel(Integer recommendationLevel) {
		this.recommendationLevel = recommendationLevel;
	}

	public String getLineSupplierID() {
		return lineSupplierID;
	}

	public void setLineSupplierID(String lineSupplierID) {
		this.lineSupplierID = lineSupplierID;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

	
	
}
