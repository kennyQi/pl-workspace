package hsl.pojo.command.coupon;
import java.util.List;
import hg.common.component.BaseCommand;
@SuppressWarnings("serial")
/**
 * @类功能说明：批量发放卡券command
 * @类修改者：
 * @修改日期：2015年3月4日下午4:20:49
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年3月4日下午4:20:49
 */
public class BatchIssueCouponCommand extends BaseCommand{
	private List<String> mobiles; 
	private String couponId;
	public List<String> getMobiles() {
		return mobiles;
	}
	public void setMobiles(List<String> mobiles) {
		this.mobiles = mobiles;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
}
