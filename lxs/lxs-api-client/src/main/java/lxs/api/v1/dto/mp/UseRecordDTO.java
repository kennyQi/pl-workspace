package lxs.api.v1.dto.mp;

import java.util.Date;

import lxs.api.v1.dto.BaseDTO;

@SuppressWarnings("serial")
public class UseRecordDTO extends BaseDTO {

	/**
	 * 景区名称
	 */
	private String scenicName;

	/**
	 * 门票使用时间
	 */
	private Date useDate;

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

}
