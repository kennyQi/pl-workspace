package plfx.yxgjclient.pojo.param;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 单张操作信息
 * @author guotx
 * 2015-07-08
 */
@XStreamAlias("perOperationIsSuc")
public class PerOperationIsSuc{
	/**
	 * 乘客姓名
	 */
	private String passengerName;
	/**
	 * 单张是否申请成功
	 */
	private String perIsSuccess;
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getPerIsSuccess() {
		return perIsSuccess;
	}
	public void setPerIsSuccess(String perIsSuccess) {
		this.perIsSuccess = perIsSuccess;
	}
	
}