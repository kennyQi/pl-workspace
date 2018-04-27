package hsl.pojo.dto.jp;
import hsl.pojo.dto.BaseDTO;
@SuppressWarnings("serial")
public class PriceInfoGNDTO extends BaseDTO{

	/***
	 * 加密串
	 */
	private String encryptString;
	/**
	 * 票面价
	 */
	private Double ibePrice;
	
	/***
	 * 政策id号
	 */
	private int plcid;
	
	/**
	 *  备注
	 */
	private String memo ;
	/**
	 * 给供应商的单人支付总价(包括机建燃油)
	 */
	private Double singleTotalPrice;
	
	/**
	 * 单人支付总价(包括机建燃油)
	 * singleTotalPrice + 价格政策　＋　手续费
	 */
	private Double singlePlatTotalPrice;

	public String getEncryptString() {
		return encryptString;
	}

	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}

	public Double getIbePrice() {
		return ibePrice;
	}

	public void setIbePrice(Double ibePrice) {
		this.ibePrice = ibePrice;
	}

	public int getPlcid() {
		return plcid;
	}

	public void setPlcid(int plcid) {
		this.plcid = plcid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Double getSingleTotalPrice() {
		return singleTotalPrice;
	}

	public void setSingleTotalPrice(Double singleTotalPrice) {
		this.singleTotalPrice = singleTotalPrice;
	}

	public Double getSinglePlatTotalPrice() {
		return singlePlatTotalPrice;
	}

	public void setSinglePlatTotalPrice(Double singlePlatTotalPrice) {
		this.singlePlatTotalPrice = singlePlatTotalPrice;
	}
	
}
