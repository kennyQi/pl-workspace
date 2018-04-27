package hsl.spi.action;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.base.ApiResponse;
import hsl.api.v1.request.command.coupon.ConsumeCouponListCommand;
import hsl.api.v1.response.coupon.ConsumeCouponResponse;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.exception.CouponException;
import hsl.spi.action.constant.Constants;
import hsl.spi.inter.Coupon.CouponService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
@Component("consumeCouponAction")
public class ConsumeCouponAction implements HSLAction{
	@Autowired
	private CouponService couponService;
	@Override
	public String execute(ApiRequest apiRequest) {
		ConsumeCouponListCommand consumeCouponListCommand = JSON.parseObject(
				apiRequest.getBody().getPayload(),
				ConsumeCouponListCommand.class);
		HgLogger.getInstance().info("yuqz", "消费卡劵请求"+JSON.toJSONString(consumeCouponListCommand));
		return consumeCoupon(consumeCouponListCommand);
	}
	
	private String consumeCoupon(ConsumeCouponListCommand consumeCouponListCommand) {
		ConsumeCouponResponse consumeCouponResponse = new ConsumeCouponResponse();
		try {
			List<CouponDTO> couponDTOList = couponService.consumeCoupon(consumeCouponListCommand.getCommandlist());
			HgLogger.getInstance().info("yuqz", "消费卡劵失败"+JSON.toJSONString(couponDTOList));
		} catch (CouponException e) {
			HgLogger.getInstance().error("yuqz", "消费卡劵失败"+e.getMessage());
			consumeCouponResponse.setResult(Constants.exceptionMap.get(e.getCode()));
			consumeCouponResponse.setMessage(e.getMessage());
		}
		consumeCouponResponse.setResult(ApiResponse.RESULT_CODE_OK);
		consumeCouponResponse.setMessage("消费成功");
		return JSON.toJSONString(consumeCouponResponse);
	}

}
