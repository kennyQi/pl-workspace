package plfx.yeexing.pojo.dto.flight;



import plfx.jp.pojo.dto.BaseJpDTO;
/****
 * 
 * @类功能说明：乘客详细信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月17日下午1:43:34
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YeeXingPassengerDTO extends BaseJpDTO{
	/***
	 *乘客姓名
	 */
	private String passengerName;
	/***
	 * 乘客类型
	 */
	private String passengerType;
	/**
	 * 证件类型
	 */
	private String idType;
	/***
	 * 证件号
	 */
	private String idNo;
	
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getPassengerType() {
		return passengerType;
	}
	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	
}
