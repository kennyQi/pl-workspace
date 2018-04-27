package plfx.gjjp.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.gjjp.app.dao.GJJPTicketDao;
import plfx.gjjp.app.dao.GJPassengerDao;
import plfx.gjjp.app.pojo.qo.GJJPOrderQo;
import plfx.gjjp.app.pojo.qo.GJJPTicketQo;
import plfx.gjjp.app.pojo.qo.GJPassengerQo;
import plfx.gjjp.domain.model.GJJPTicket;
import plfx.gjjp.domain.model.GJPassenger;
import plfx.jp.command.api.gj.GJRefundTicketCommand;

/**
 * @类功能说明：乘客服务
 * @类修改者：
 * @修改日期：2015年7月19日下午9:31:06
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015年7月19日下午9:31:06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GJPassengerLocalService extends BaseServiceImpl<GJPassenger, GJPassengerQo, GJPassengerDao> {

	@Autowired
	private GJPassengerDao dao;

	@Autowired
	private GJJPTicketDao ticketDao;

	@Override
	protected GJPassengerDao getDao() {
		return dao;
	}

	/**
	 * @方法功能说明：退废票处理
	 * @修改者名字：zhurz
	 * @修改时间：2015年7月19日下午9:32:55
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:GJPassenger
	 * @throws
	 */
	public GJPassenger refundSupplierTicket(GJRefundTicketCommand command) {

		GJJPTicketQo ticketQo = new GJJPTicketQo();
		GJPassengerQo passengerQo = new GJPassengerQo();
		GJJPOrderQo orderQo = new GJJPOrderQo();
		ticketQo.setPassengerQo(passengerQo);
		passengerQo.setJpOrderQo(orderQo);
		// 取一个票号就能确定乘机人
		ticketQo.setTicketNo(command.getEticketNo().get(0));
		orderQo.setId(command.getPlatformOrderId());
		passengerQo.setPassengerName(command.getPassengerName());

		GJJPTicket ticket = ticketDao.queryUnique(ticketQo);
		if (ticket == null)
			return null;

		GJPassenger passenger = ticket.getPassenger();
		passenger.refundSupplierOver(command);
		getDao().update(passenger.getJpOrder());

		return passenger;
	}

}
