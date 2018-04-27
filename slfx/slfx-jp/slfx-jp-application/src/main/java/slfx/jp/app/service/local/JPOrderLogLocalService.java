package slfx.jp.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.jp.app.dao.JPOrderLogDAO;
import slfx.jp.command.admin.orderLog.CreateJPOrderLogCommand;
import slfx.jp.domain.model.log.JPOrderLog;
import slfx.jp.domain.model.order.JPOrder;
import slfx.jp.qo.admin.JPOrderLogQO;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：机票订单日志操作日志内层Service
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年1月21日上午10:27:31
 * @版本：V1.0
 */
@Service
@Transactional
public class JPOrderLogLocalService extends BaseServiceImpl<JPOrderLog,JPOrderLogQO,JPOrderLogDAO>{

	@Autowired
	private  JPOrderLogDAO dao;
	@Autowired
	private DomainEventRepository domainEvent;
	
	@Override
	protected JPOrderLogDAO getDao() {
		return dao;
	}
	
	/**
	 * 
	 * @方法功能说明：创建机票订单日志
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年1月21日上午11:00:24
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean create(CreateJPOrderLogCommand command,JPOrder order){
		try {
			JPOrderLog log = new JPOrderLog();
			log.create(command,order);
			dao.save(log);
			
			//领域日志
			DomainEvent event = new DomainEvent("slfx.jp.domain.model.log.JPOrderLog","create",JSON.toJSONString(command));
			domainEvent.save(event);
			return true;
		} catch (Exception e) {
			HgLogger.getInstance().error("caizhenghao", "JPOrderLogLocalService->create->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	}
	
	
}