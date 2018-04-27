package slfx.jp.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.jp.app.dao.JPOrderDAO;
import slfx.jp.command.client.ABEDeletePnrCommand;
import slfx.jp.command.client.YGApplyRefundCommand;
import slfx.jp.domain.model.order.JPOrder;
import slfx.jp.pojo.dto.supplier.abe.ABEDeletePnrDTO;
import slfx.jp.pojo.dto.supplier.yg.YGApplyRefundDTO;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.yg.open.inter.YGFlightService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：LOCAL申请退废票SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:44:33
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class PlatformApplyRefundLocalService extends BaseServiceImpl<JPOrder, PlatformOrderQO, JPOrderDAO> {

	@Autowired
	private YGFlightService ygFlightService;
	@Autowired
	private DomainEventRepository domainEventRepository;
	@Override
	protected JPOrderDAO getDao() {
		return null;
	}
	
	public YGApplyRefundDTO applyRefund(YGApplyRefundCommand command) {
		
		/*DomainEvent event = new DomainEvent();
		event.setOperate(command.getFromAdminId());
		event.setId(command.getCommandId());
		event.setCreateDate(new Date());
		event.setProjectId("商旅分销平台");
		event.setEnvId("SLFX-JP");
		event.setModelName("slfx.jp.app.service.local.PlatformApplyRefundLocalService");
		event.setMethodName("applyRefund");
		
		List<String> params = new ArrayList<String>();
		params.add(JSON.toJSONString(command));
		params.add("易购订单废票");
		event.setParams(params);
		domainEventRepository.save(event);*/
		DomainEvent event = new DomainEvent("slfx.jp.domain.model.order.JPOrder","applyRefund",JSON.toJSONString(command));
		domainEventRepository.save(event);
		return ygFlightService.applyRefund(command);
	}
	
	public ABEDeletePnrDTO deletePnr(ABEDeletePnrCommand deleteCommand) {
		return ygFlightService.deletePnr(deleteCommand);
	}
	
	

}
