package dzpw.test;

import java.util.Date;
import java.util.List;

import hg.common.util.UUIDGenerator;
import hg.dzpw.app.pojo.qo.ApplyRefundRecordQo;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.TicketOrderQo;
import hg.dzpw.app.service.api.alipay.AliPayRefundFastService;
import hg.dzpw.app.service.local.ApplyRefundRecordLocalService;
import hg.dzpw.app.service.local.GroupTicketLocalService;
import hg.dzpw.app.service.local.SingleTicketLocalService;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.dealer.DealerRefundNotifyRecord;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.domain.model.pay.ApplyRefundRecord;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.ticket.GroupTicket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
public class TestService {
	@Autowired
	private ApplyRefundRecordLocalService service;
	@Autowired
	private GroupTicketLocalService groupTicketLocalService;
	@Autowired
	private SingleTicketLocalService singleTicketLocalService;
	
	@Test
	public void testApplyRefundService(){
//		TicketOrderQo ticketOrderQo = new TicketOrderQo();
//		GroupTicketQo groupTicketQo = new GroupTicketQo();
//		
//		ApplyRefundRecordQo qo=new ApplyRefundRecordQo();
//		qo.setGroupTicketQo(groupTicketQo);
//		qo.setTicketOrderQo(ticketOrderQo);
//		List<ApplyRefundRecord> list=service.queryList(qo);
//		System.out.println(list.size());
		ApplyRefundRecord record=new ApplyRefundRecord();
		record.setId(UUIDGenerator.getUUID());
		record.setRecordDate(new Date());
		service.save(record);
	}
	
	@Test
	public void testGroup(){
		GroupTicket groupTicket = groupTicketLocalService.get("8ff0d1fcfdd994380e4a277e8aa83b6b");
		TicketOrder ticketOrder = groupTicket.getTicketOrder();
		Dealer fromDealer = ticketOrder.getBaseInfo().getFromDealer();
		String key = fromDealer.getClientInfo().getKey();
		System.out.println(key);
	}
	@Test
	public void test(){
		String[] ticketNos=new String[1];
		ticketNos[0]="100523009488";
		singleTicketLocalService.updateCanRefundSingleStatus(ticketNos);
	}
}
