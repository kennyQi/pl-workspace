package hg.demo.member.service.spi.impl.fx;

import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.service.dao.hibernate.AuthUserDAO;
import hg.demo.member.service.dao.hibernate.fx.ArrearsRecordDAO;
import hg.demo.member.service.dao.hibernate.fx.DistributorDAO;
import hg.demo.member.service.dao.hibernate.fx.ReserveInfoDao;
import hg.demo.member.service.domain.manager.fx.ArrearsRecordManager;
import hg.demo.member.service.domain.manager.fx.ReserveInfoManager;
import hg.demo.member.service.qo.hibernate.fx.ArrearsRecordQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.arrearsRecord.AuditArrearsRecordCommand;
import hg.fx.command.arrearsRecord.CreateArrearsRecordCommand;
import hg.fx.command.reserveInfo.ModifyReserveInfoCommand;
import hg.fx.domain.ArrearsRecord;
import hg.fx.domain.Distributor;
import hg.fx.domain.ReserveInfo;
import hg.fx.spi.ArrearsRecordSPI;
import hg.fx.spi.qo.ArrearsRecordSQO;

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
@Service("arrearsRecordSPIService")
public class ArrearsRecordSPIService extends BaseService implements ArrearsRecordSPI{

	@Autowired
	private ArrearsRecordDAO arrearsRecordDAO;
	@Autowired
	private AuthUserDAO authUserDAO;
	@Autowired
	private DistributorDAO distributorDAO;
	@Autowired
	ReserveInfoDao infoDao;
	
	/**
	 * 新增可欠费里程变更记录
	 */
	@Override
	public ArrearsRecord create(CreateArrearsRecordCommand command) {
		ArrearsRecord arrearsRecord = new ArrearsRecord();
		AuthUser authUser = null;
		if(StringUtils.isNotBlank(command.getAuthUserID())){
			authUser = authUserDAO.get(command.getAuthUserID());
		}
		Distributor distributor = null;
		if(StringUtils.isNotBlank(command.getDistributorID())){
			distributor = distributorDAO.get(command.getDistributorID());
		}
		return arrearsRecordDAO.save(new ArrearsRecordManager(arrearsRecord).create(command, distributor, authUser).get());
	}

	/**
	 * 分页查询可欠费里程变更记录
	 */
	@Override
	public Pagination<ArrearsRecord> queryArrearsRecordPagination(ArrearsRecordSQO sqo) {
		Pagination<ArrearsRecord> pagination = arrearsRecordDAO.queryPagination(ArrearsRecordQO.build(sqo));
		for (ArrearsRecord arrearsRecord : pagination.getList()) {
			Hibernate.initialize(arrearsRecord.getOperator());
			Hibernate.initialize(arrearsRecord.getDistributor());
		}
		return pagination;
	}

	/**
	 * 列表查询可欠费里程变更记录
	 */
	@Override
	public List<ArrearsRecord> queryArrearsRecordList(ArrearsRecordSQO sqo) {
		List<ArrearsRecord> arrearsRecords = arrearsRecordDAO.queryList(ArrearsRecordQO.build(sqo));
		for (ArrearsRecord arrearsRecord : arrearsRecords) {
			Hibernate.initialize(arrearsRecord.getOperator());
			Hibernate.initialize(arrearsRecord.getDistributor());
		}
		return arrearsRecords;
	}

	/**
	 * 查询可欠费里程变更记录
	 */
	@Override
	public ArrearsRecord queryArrearsRecordByID(ArrearsRecordSQO sqo) {
		ArrearsRecord arrearsRecord = arrearsRecordDAO.queryUnique(ArrearsRecordQO.build(sqo));
		if(arrearsRecord!=null){
			Hibernate.initialize(arrearsRecord.getOperator());
			Hibernate.initialize(arrearsRecord.getDistributor());
		}
		return arrearsRecord;
	}

	/**
	 * 可欠费里程变更记录审核
	 */
	@Override
	public ArrearsRecord audit(AuditArrearsRecordCommand command) {
		ArrearsRecord arrearsRecord = new ArrearsRecord();
		//如果审核通过 则更新 可欠费里程
		if(AuditArrearsRecordCommand.CHECK_STATUS_PASS.equals(command.getCheckStatus())){
			arrearsRecord = arrearsRecordDAO.get(command.getArrearsRecordID());
			ModifyReserveInfoCommand cmd = new ModifyReserveInfoCommand();
			cmd.setId(arrearsRecord.getDistributor().getReserveInfo().getId());
			cmd.setArrearsAmount(arrearsRecord.getApplyArrears());
			//reserveInfoService.modifyArrearsAmount(cmd);
			ReserveInfo reserveInfo = infoDao.get(cmd.getId());
			infoDao.update(new ReserveInfoManager(reserveInfo).modifyArrearsAmount(cmd.getArrearsAmount()).get());
		}
		if(StringUtils.isNotBlank(command.getArrearsRecordID())&&arrearsRecordDAO.get(command.getArrearsRecordID())!=null){
			arrearsRecord = arrearsRecordDAO.get(command.getArrearsRecordID());
			return arrearsRecordDAO.update(new ArrearsRecordManager(arrearsRecord).audit(command).get());
		}else{
			return null;
		}
	}

}
