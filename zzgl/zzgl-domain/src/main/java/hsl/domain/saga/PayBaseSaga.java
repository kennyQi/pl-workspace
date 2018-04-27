package hsl.domain.saga;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import hg.common.component.BaseEvent;
import hg.common.component.BaseSaga;
import hsl.domain.model.xl.order.LineOrder;

/**
 * @类功能说明：支付基本流程类
 * 			从用户打开支付页面开始为新建流程
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhuxy
 * @创建时间：2015年2月26日
 */
@SuppressWarnings("serial")
public class PayBaseSaga extends BaseSaga{

	public final static Integer PAY = 1; // 手机号未验证
	public final static Integer PAY_PAGE = 2; // 手机号已验证，未激活
	public final static Integer PAY_SUCCESS = 2; // 手机号已验证，未激活
	public final static Integer STATUS_ACTIVED = 3; // 已填写密码激活
	
	/**
	 * 关联线路订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LINE_ORDER_ID")
	private LineOrder lineOrder;
	
	

	@Override
	public boolean checkFinish() {
		return super.isFinish();
	}

	@Override
	public void handle(BaseEvent event) {
		
	}

}
