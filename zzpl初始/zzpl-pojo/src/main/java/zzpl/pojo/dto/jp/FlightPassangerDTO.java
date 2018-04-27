package zzpl.pojo.dto.jp;

//import zzpl.pojo.dto.user.UserDTO;


/**
 * 乘客信息 dto
 * 
 * @author tandeng
 * 
 */
@SuppressWarnings("serial")
//public class FlightPassangerDTO extends UserDTO {
public class FlightPassangerDTO{

	/** 乘客序号 */
	private Integer psgNo;
	
	/** 乘客姓名 */
	private String name;

	/** 乘客类型
		ADT 成人 
		CHD 儿童 
		INF 婴儿 
		UM 无陪伴儿童 
	 */
	private String passangerType;
	
	/**
	 * 乘客证件类型
	 */
	private String identityType;

	/** 航信系统证件类型 
		NI 身份证号 
		FOID 护照号 
		ID 其它类型 
	 */
	private String cardType;
	
	/**
	 * 证件号   cardType的证件号
	 */
	private String cardNo;

	/** 
	 * 出生日期 yyyy-MM-dd
	 */
	private String birthday;

	/** 跟随成人序号 */
	private Integer carrierPsgNo;

	/** 保险份数 */
	private Integer insueSum;

	/** 保险单价 */
	private Double insueFee;

	/**
	 * 该乘机人所支付的政策
	 */
	private FlightPolicyDTO flightPolicyDTO;

	/**
	 * 该乘机人所出的机票
	 */
	private TicketDTO ticket;
	
	/**
	 *  组织id
	 */
	private  String  companyId;
	
	/**
	 * 组织名称
	 */
	private String companyName;
	
	/**
	 * 部门id
	 */
	private String departmentId;
	
	/**
	 * 部门名称
	 */
	private String departmentName;
	
	/**
	 * 成员id
	 */
    private String memeberId;
    
	public Integer getPsgNo() {
		return psgNo;
	}

	public void setPsgNo(Integer psgNo) {
		this.psgNo = psgNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassangerType() {
		return passangerType;
	}

	public void setPassangerType(String passangerType) {
		this.passangerType = passangerType;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getCarrierPsgNo() {
		return carrierPsgNo;
	}

	public void setCarrierPsgNo(Integer carrierPsgNo) {
		this.carrierPsgNo = carrierPsgNo;
	}

	public Integer getInsueSum() {
		return insueSum;
	}

	public void setInsueSum(Integer insueSum) {
		this.insueSum = insueSum;
	}

	public Double getInsueFee() {
		return insueFee;
	}

	public void setInsueFee(Double insueFee) {
		this.insueFee = insueFee;
	}

	public FlightPolicyDTO getFlightPolicyDTO() {
		return flightPolicyDTO;
	}

	public void setFlightPolicyDTO(FlightPolicyDTO flightPolicyDTO) {
		this.flightPolicyDTO = flightPolicyDTO;
	}

	public TicketDTO getTicket() {
		return ticket;
	}

	public void setTicket(TicketDTO ticket) {
		this.ticket = ticket;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getMemeberId() {
		return memeberId;
	}

	public void setMemeberId(String memeberId) {
		this.memeberId = memeberId;
	}

}
