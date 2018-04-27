package hsl.admin.listener;

import hg.common.component.BaseAmqpMessage;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hsl.domain.model.coupon.CouponActivity;
import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.dto.coupon.CouponActivityDTO;
import hsl.pojo.exception.CouponException;
import hsl.pojo.qo.coupon.HslCouponActivityQO;
import hsl.spi.inter.Coupon.CouponActivityService;
import hsl.spi.inter.Coupon.CouponService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
/**
 * @类功能说明：消息监听器
 * @类修改者：
 * @修改日期：2014-10-20下午2:26:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-20下午2:26:18
 */
public class AmqpMessageListener {
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private CouponActivityService couponActivityService;
	

	@SuppressWarnings("unchecked")
	public void listen(Object message) {
		if(!(message instanceof BaseAmqpMessage))
		return;
		HgLogger.getInstance().info("chenxy","卡券发放监听器>>监听到消息：" + JSON.toJSONString(message, true));
		BaseAmqpMessage<CreateCouponCommand> msg = (BaseAmqpMessage<CreateCouponCommand>)message;
		if(msg.getType()!=null&&msg.getContent()!=null){
			HslCouponActivityQO qo = new HslCouponActivityQO();
			qo.setCurrentValue(CouponActivity.COUPONACTIVITY_STATUS_ACTIVE);
			if(msg.getType().toString().equals(SysProperties.getInstance().get("issue_on_register"))){
				//注册发放
				qo.setIssueWay(CouponActivity.ISSUE_WAY_REGIST);
				createCoupon(msg, qo);
			}else if(msg.getType().toString().equals(SysProperties.getInstance().get("issue_on_full"))){
				//订单满发放
				qo.setIssueWay(CouponActivity.ISSUE_WAY_ORDER_OVER_LINE);
				//设置订单满的数值
				qo.setIssueNumLine(msg.getContent().getPayPrice());
				qo.setGreater(true);
				createCoupon(msg, qo);
			}
		}
		
	}


	private void createCoupon(BaseAmqpMessage<CreateCouponCommand> msg,
			HslCouponActivityQO qo) {
		HgLogger.getInstance().info("chenxy","卡券发放监听器>>发放卡券：" + JSON.toJSONString(qo, true));
		CreateCouponCommand cmd = msg.getContent();
		qo.setCurrentValue(CouponActivity.COUPONACTIVITY_STATUS_ACTIVE);
		qo.setOrderbyPriority(true);
		List<CouponActivityDTO> activityDTOs = couponActivityService.queryList(qo);
		HgLogger.getInstance().info("chenxy","卡券发放监听器>>发放卡券：" + JSON.toJSONString(activityDTOs, true));
		if(activityDTOs!=null&&activityDTOs.size()>0){
			//判断卡券活动的优先级
			int maxPriority=activityDTOs.get(0).getIssueConditionInfo().getPriority();
			for(CouponActivityDTO activityDTO : activityDTOs){
				if(maxPriority==activityDTO.getIssueConditionInfo().getPriority()){
					cmd.setCouponActivityId(activityDTO.getId());
					try {
						HgLogger.getInstance().info("chenxy","卡券发放监听器>>发放卡券：" + JSON.toJSONString(cmd, true));
						couponService.createCoupon(cmd);
					} catch (CouponException e) {
						e.printStackTrace();
						HgLogger.getInstance().error("chenxy", "卡券发放监听器>>活动id为："+activityDTO.getId()+"的卡券发放失败");
					}
				}
			}
		}
	}
}
