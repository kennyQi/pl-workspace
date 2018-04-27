package plfx.yeexing.pojo.command.order;

/**
 * 
 * @类功能说明：拒绝出票COMMAND
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月30日下午5:13:37
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPOrderRefuseCommand extends JPBaseCommand{
	/**
	 * 乘客姓名
	 * 多个乘客之间用 ^ 分隔(获取时使用urldecode解密)
	 */
	private String passengerName;
	
	/**
	 * 拒绝理由
	 */
	private String refuseMemo;

	public String getRefuseMemo() {
		return refuseMemo;
	}

	public void setRefuseMemo(String refuseMemo) {
		this.refuseMemo = refuseMemo;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
}
