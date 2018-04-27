package plfx.jp.qo.api;

import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

@SuppressWarnings("serial")
@QOConfig(daoBeanId = "airPortCodeDao")
public class AirPortCodeQO extends BaseQo{
	/**
	 * 机场名称
	 */
	private String airName;
	/**
	 * 机场代码
	 */
	private String airCode;
	
	public String getAirName() {
		return airName;
	}
	public void setAirName(String airName) {
		this.airName = airName;
	}
	public String getAirCode() {
		return airCode;
	}
	public void setAirCode(String airCode) {
		this.airCode = airCode;
	}

	
}
