package hg.demo.member.service.spi.impl.fx;

import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.service.dao.hibernate.AuthUserDAO;
import hg.demo.member.service.dao.hibernate.fx.DistributorDAO;
import hg.demo.member.service.dao.hibernate.fx.ReserveRecordDAO;
import hg.demo.member.service.domain.manager.fx.ReserveRecordManager;
import hg.demo.member.service.qo.hibernate.fx.ReserveRecordQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.reserveRecord.AuditReserveRecordCommand;
import hg.fx.command.reserveRecord.CreateReserveRecordCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.ReserveRecord;
import hg.fx.spi.ReserveRecordSPI;
import hg.fx.spi.qo.ReserveRecordSQO;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cangs
 */
@Transactional
@Service("reserveRecordSPIService")
public class ReserveRecordSPIService extends BaseService implements ReserveRecordSPI{

	@Autowired
	private ReserveRecordDAO reserveRecordDAO;
	@Autowired
	private AuthUserDAO authUserDAO;
	@Autowired
	private DistributorDAO distributorDAO;
	
	/**
	 * 新增里程余额变更记录
	 */
	@Override
	public ReserveRecord create(CreateReserveRecordCommand command) {
		ReserveRecord reserveRecord = new ReserveRecord();
		AuthUser authUser = null;
		if(StringUtils.isNotBlank(command.getAuthUserID())){
			authUser = authUserDAO.get(command.getAuthUserID());
		}
		Distributor distributor = null;
		if(StringUtils.isNotBlank(command.getDistributorID())){
			distributor = distributorDAO.get(command.getDistributorID());
		}
		return reserveRecordDAO.save(new ReserveRecordManager(reserveRecord).create(command, distributor, authUser).get());
	}
	
	/**
	 * 分页查询里程余额变更记录
	 */
	@Override
	public Pagination<ReserveRecord> queryReserveRecordPagination(ReserveRecordSQO sqo) {
		Pagination<ReserveRecord> pagination = reserveRecordDAO.queryPagination(ReserveRecordQO.build(sqo));
		for (ReserveRecord reserveRecord : pagination.getList()) {
			Hibernate.initialize(reserveRecord.getOperator());
			Hibernate.initialize(reserveRecord.getDistributor());
		}
		return pagination;
	}
	
	/**
	 * 列表查询里程余额变更记录
	 */
	@Override
	public List<ReserveRecord> queryReserveRecordList(ReserveRecordSQO sqo) {
		List<ReserveRecord> reserveRecords = reserveRecordDAO.queryList(ReserveRecordQO.build(sqo));
		for (ReserveRecord reserveRecord : reserveRecords) {
			Hibernate.initialize(reserveRecord.getOperator());
			Hibernate.initialize(reserveRecord.getDistributor());
		}
		return reserveRecords;
	}
	
	/**
	 * 查询里程余额变更记录
	 */
	@Override
	public ReserveRecord queryReserveRecordByID(ReserveRecordSQO sqo) {
		ReserveRecord reserveRecord = reserveRecordDAO.queryUnique(ReserveRecordQO.build(sqo));
		if(reserveRecord!=null){
			Hibernate.initialize(reserveRecord.getOperator());
			Hibernate.initialize(reserveRecord.getDistributor());
		}
		return reserveRecord;
	}
	
	/**
	 * 里程余额变更记录审核
	 */
	@Override
	public ReserveRecord audit(AuditReserveRecordCommand command) {
		ReserveRecord reserveRecord = new ReserveRecord();
		if(StringUtils.isNotBlank(command.getReserveRecordID())&&reserveRecordDAO.get(command.getReserveRecordID())!=null){
			reserveRecord = reserveRecordDAO.get(command.getReserveRecordID());
			return reserveRecordDAO.update(new ReserveRecordManager(reserveRecord).audit(command).get());
		}else{
			return null;
		}
	}

}
