package lxs.api.v1.dto.line;

import lxs.api.v1.dto.BaseDTO;

@SuppressWarnings("serial")
public class TravlerNOActivityDTO extends BaseDTO{
	private String lineID;
	
	private String lineNO;
	
	private String lineName;
	
	private String pictureUri;
	
	private Integer type;
	
	private String starting;
	
	private String travlerNO;
	
	private Double minPrice;

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

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

	public String getPictureUri() {
		return pictureUri;
	}

	public void setPictureUri(String pictureUri) {
		this.pictureUri = pictureUri;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getStarting() {
		return starting;
	}

	public void setStarting(String starting) {
		this.starting = starting;
	}

	public String getTravlerNO() {
		return travlerNO;
	}

	public void setTravlerNO(String travlerNO) {
		this.travlerNO = travlerNO;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}
	
	
}
