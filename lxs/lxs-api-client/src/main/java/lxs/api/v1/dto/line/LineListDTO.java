package lxs.api.v1.dto.line;

import javax.persistence.Column;

import lxs.api.v1.dto.BaseDTO;

@SuppressWarnings("serial")
public class LineListDTO extends BaseDTO{
	
	/**
	 * 线路id
	 */
	private String lineID;

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	/**
	 * 线路名称
	 */
	private String lineName;
	/**
	 * 线路编号
	 */
	private String lineNO;
	
	/**
	 * 线路详情
	 */
	private String lineDescription;
	
	/**
	 * 价格
	 */
	private String minPrice;
	
	/**
	 * 首图
	 */
	private String pictureUri;

	/**
	 * 推荐指数
	 */
	private Integer recommendationLevel;
	
	/**
	 * 出发地城市编号
	 */
	private String starting;
	
	/**
	 * 线路类型
	 */
	private Integer type;
	
	public String getLineNO() {
		return lineNO;
	}

	public void setLineNO(String lineNO) {
		this.lineNO = lineNO;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getLineDescription() {
		return lineDescription;
	}

	public void setLineDescription(String lineDescription) {
		this.lineDescription = lineDescription;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public String getPictureUri() {
		return pictureUri;
	}

	public void setPictureUri(String pictureUri) {
		this.pictureUri = pictureUri;
	}

	public Integer getRecommendationLevel() {
		return recommendationLevel;
	}

	public void setRecommendationLevel(Integer recommendationLevel) {
		this.recommendationLevel = recommendationLevel;
	}

	public String getStarting() {
		return starting;
	}

	public void setStarting(String starting) {
		this.starting = starting;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
