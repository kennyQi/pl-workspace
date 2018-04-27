package hsl.pojo.dto.mp;


/**
 * 预订信息
 * @author liujz
 *
 */
public class BookInfoDTO{

	/**
	 * 是否可预订
	 */
	private Boolean bookFlag;
	/**
	 * 最低价
	 */
	private Double amountAdvice;
	/**
	 * 是否需要证件号
	 */
	private Boolean ifUseCard;

	/**
	 * 购票需知
	 */
	private String buyNotie;
	/**
	 * 支付类型
	 */
	private String payMode;
	public Boolean getBookFlag() {
		return bookFlag;
	}
	public void setBookFlag(Boolean bookFlag) {
		this.bookFlag = bookFlag;
	}
	public Double getAmountAdvice() {
		return amountAdvice;
	}
	public void setAmountAdvice(Double amountAdvice) {
		this.amountAdvice = amountAdvice;
	}
	public Boolean getIfUseCard() {
		return ifUseCard;
	}
	public void setIfUseCard(Boolean ifUseCard) {
		this.ifUseCard = ifUseCard;
	}
	public String getBuyNotie() {
		return buyNotie;
	}
	public void setBuyNotie(String buyNotie) {
		this.buyNotie = buyNotie;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	
	
}
