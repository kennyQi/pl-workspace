package hg.payment.app.service.spi;

import hg.payment.app.service.local.payorder.AlipayPayOrderLocalService;
import hg.payment.app.service.local.payorder.PayOrderLocalService;
import hg.payment.pojo.command.spi.payorder.alipay.CreateAlipayPayOrderCommand;
import hg.payment.pojo.exception.PaymentException;
import hg.payment.spi.inter.AlipayPayOrderSpiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 
 * 
 *@类功能说明：支付宝订单对外接口实现类
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年9月5日上午9:53:59
 */
@Service("alipayPayOrderSpiService")
public class AlipayPayOrderSpiServiceImpl implements AlipayPayOrderSpiService{

	@Autowired
	AlipayPayOrderLocalService alipayPayOrderLocalService;
	@Autowired
	PayOrderLocalService payOrderLocalService;
	
	@Override
	public String createAlipayPayOrder(CreateAlipayPayOrderCommand command)
			throws PaymentException {
		return  alipayPayOrderLocalService.createAlipayPayOrder(command);
	}

}
