package hsl.pojo.dto.commonContact;
import hsl.pojo.dto.BaseDTO;
import hsl.pojo.dto.user.UserDTO;
public class CommonContactDTO extends BaseDTO{
	private static final long serialVersionUID = 1L;
	/**
	 * 联系人姓名
	 */
	private String name;
	/**
	 * 证件类型
	 */
	private Integer cardType;
	/**
	 * 证件号码
	 */
	private String cardNo;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 乘客类型
	 */
	private Integer type;
	/**
	 * 所属用户
	 */
	private UserDTO user;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCardType() {
		return cardType;
	}
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
}
