package hsl.api.v1.request.command.coupon;
import hsl.api.base.ApiPayload;
import hsl.pojo.command.coupon.ConsumeCouponCommand;
import java.util.List;
public class ConsumeCouponListCommand extends ApiPayload{
	/**
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 消费卡券list
	 */
	List<ConsumeCouponCommand> commandlist;

	public List<ConsumeCouponCommand> getCommandlist() {
		return commandlist;
	}

	public void setCommandlist(List<ConsumeCouponCommand> commandlist) {
		this.commandlist = commandlist;
	}

}
