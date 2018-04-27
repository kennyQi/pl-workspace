package hsl.h5.control;
import hg.log.util.HgLogger;
import hsl.h5.base.result.api.ApiResult;
import hsl.h5.base.result.coupon.ConsumeCouponResult;
import hsl.h5.base.result.coupon.CouponQueryResult;
import hsl.h5.control.constant.Constants;
import hsl.pojo.command.coupon.ConsumeCouponCommand;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.exception.CouponException;
import hsl.pojo.qo.coupon.HslCouponQO;
import java.io.PrintWriter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：卡劵Action
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2014年11月6日下午1:57:14
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping("coupon")
public class CouponCtrl extends HslCtrl{

	@Autowired
	private hsl.spi.inter.Coupon.CouponService couponService;
	
	/**
	 * 
	 * @方法功能说明：卡劵查询
	 * @修改者名字：yuqz
	 * @修改时间：2014年11月6日下午2:07:07
	 * @修改内容：
	 * @参数：@param hslCouponQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("query")
	public String query(HslCouponQO hslCouponQO){
		HgLogger.getInstance().info("yuqz", "CouponCtrl->query卡劵查询" + JSON.toJSONString(hslCouponQO));
		List<CouponDTO> couponDTOList= couponService.queryList(hslCouponQO);
		CouponQueryResult couponQueryResult = new CouponQueryResult();
		if(couponDTOList == null || couponDTOList.size() <= 0){
			couponQueryResult.setMessage("查询失败");
			couponQueryResult.setResult(CouponQueryResult.RESULT_CODE_FAIL);
			return JSON.toJSONString(couponQueryResult);
		}
		couponQueryResult.setCouponDTOList(couponDTOList);
		couponQueryResult.setMessage("查询成功");
		couponQueryResult.setResult(CouponQueryResult.RESULT_CODE_OK);
		return JSON.toJSONString(couponQueryResult);
	}
	
	/**
	 * 
	 * @方法功能说明：消费卡劵
	 * @修改者名字：yuqz
	 * @修改时间：2014年11月6日下午2:15:04
	 * @修改内容：
	 * @参数：@param commandList
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("consume")
	public String consume(List<ConsumeCouponCommand> commandList, PrintWriter out){
		HgLogger.getInstance().info("yuqz", "CouponCtrl->consume消费卡劵" + JSON.toJSONString(commandList));
		ConsumeCouponResult consumeCouponResult = new ConsumeCouponResult();
		try {
			List<CouponDTO> couponDTOList = couponService.consumeCoupon(commandList);
			HgLogger.getInstance().info("chenxy", "消费的卡券："+JSON.toJSONString(couponDTOList));
		} catch (CouponException e) {
			HgLogger.getInstance().error("yuqz", "消费卡劵失败"+e.getMessage());
			consumeCouponResult.setResult(Constants.exceptionMap.get(e.getCode()));
			consumeCouponResult.setMessage(e.getMessage());
		}
		consumeCouponResult.setResult(ApiResult.RESULT_CODE_OK);
		consumeCouponResult.setMessage("消费成功");
		HgLogger.getInstance().info("yuqz","CouponCtrl->consume消费卡劵完成->结果" + JSON.toJSONString(consumeCouponResult));
		return JSON.toJSONString(consumeCouponResult);
	}
}
