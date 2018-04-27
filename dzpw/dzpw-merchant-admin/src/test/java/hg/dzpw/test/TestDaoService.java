package hg.dzpw.test;

import hg.common.page.Pagination;
import hg.dzpw.app.dao.TicketOrderDao;
import hg.dzpw.pojo.qo.TicketOrderListQo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class TestDaoService {
	
	@Autowired
	TicketOrderDao dao;
	
	public void test() {
		TicketOrderListQo qo = new TicketOrderListQo();
//		qo.setScenicspotId("b5bd9481652745eca34f46e925d90567");
//		qo.setTicketPolicyName("2015联票2");
//		qo.setTicketPolicyType(TicketPolicy.TICKET_POLICY_TYPE_GROUP);
//		qo.setFromDealerId("512866a1741742dabf2e1ff1833f5684");
//		qo.setOrderDateBegin(DateUtil.parseDateTime("2010-10-10", "yyyy-MM-dd"));
//		qo.setOrderDateEnd(DateUtil.parseDateTime("2020-10-10", "yyyy-MM-dd"));
//		qo.setTouristName("zhangyongbao");
//		qo.setOrderId("PL2015010600002");
//		qo.setDealerOrderId("dxpw201501060001");
//		qo.setOrderStatus(1);
		Pagination pagination = dao.queryListVoPagination(qo);
		System.out.println(JSON.toJSONString(pagination, true));
	}
	
}
