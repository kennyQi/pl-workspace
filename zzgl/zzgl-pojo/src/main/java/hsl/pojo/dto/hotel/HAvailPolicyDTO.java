package hsl.pojo.dto.hotel;

public class HAvailPolicyDTO {
	//提示编号
	protected String id;
	/**
	 * 提示内容
	 */
    protected String availPolicyText;
    /**
	 * 有效开始时间
	 */
    protected String startDate;
    /**
	 * 有效结束时间
	 */
    protected String endDate;
    
	public String getId() {
		return id;
	}

	public void setId(String value) {
		this.id = value;
	}

}
