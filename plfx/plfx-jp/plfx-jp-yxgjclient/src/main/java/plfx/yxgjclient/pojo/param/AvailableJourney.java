package plfx.yxgjclient.pojo.param;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 行程信息
 * @author guotx
 * 2015-07-07
 */
@XStreamAlias("availableJourney")
public class AvailableJourney {
	/**
	 * 去程航班信息
	 */
	private TakeoffAvailJourney takeoffAvailJourney;
	/**
	 * 返程航班信息
	 */
	private BackAvailJourney backAvailJourney;
	/**
	 * 价格信息
	 */
	private List<PriceInfo> priceInfos;
	/**
	 * 加密字符串（供获取政策接口使用）
	 */
	private String encryptString;
	
	public TakeoffAvailJourney getTakeoffAvailJourney() {
		return takeoffAvailJourney;
	}
	public void setTakeoffAvailJourney(TakeoffAvailJourney takeoffAvailJourney) {
		this.takeoffAvailJourney = takeoffAvailJourney;
	}
	
	public String getEncryptString() {
		return encryptString;
	}
	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}
	public BackAvailJourney getBackAvailJourney() {
		return backAvailJourney;
	}
	public void setBackAvailJourney(BackAvailJourney backAvailJourney) {
		this.backAvailJourney = backAvailJourney;
	}
	public List<PriceInfo> getPriceInfos() {
		return priceInfos;
	}
	public void setPriceInfos(List<PriceInfo> priceInfos) {
		this.priceInfos = priceInfos;
	}
	

}
