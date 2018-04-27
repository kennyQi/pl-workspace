package hg.demo.member.service.spi.impl.fx;

import hg.demo.member.service.dao.hibernate.fx.DistributorDAO;
import hg.demo.member.service.dao.hibernate.fx.ReserveInfoDao;
import hg.demo.member.service.dao.hibernate.fx.ReserveRecordDAO;
import hg.demo.member.service.domain.manager.fx.ReserveInfoManager;
import hg.demo.member.service.domain.manager.fx.ReserveRecordManager;
import hg.framework.common.util.UUIDGenerator;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.reserveInfo.CreateReserveInfoCommand;
import hg.fx.command.reserveInfo.ModifyReserveInfoCommand;
import hg.fx.command.reserveRecord.AuditReserveRecordCommand;
import hg.fx.command.reserveRecord.ChargeUpdateReserveRecordCommand;
import hg.fx.command.reserveRecord.CreateChargeReserveRecordCommand;
import hg.fx.command.reserveRecord.CreateReserveRecordCommand;
import hg.fx.domain.MileOrder;
import hg.fx.domain.ReserveInfo;
import hg.fx.domain.ReserveRecord;
import hg.fx.spi.ReserveInfoSPI;
import hg.fx.spi.ReserveRecordSPI;
import hg.fx.spi.qo.ReserveRecordSQO;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service("reserveInfoSPIService")
public class ReserveInfoSPIService extends BaseService implements ReserveInfoSPI{

	private static final String PROD_积分充值 = "积分充值";
	@Autowired
	ReserveInfoDao infoDao;
	@Autowired
	ReserveRecordDAO recordDAO;
	@Autowired
	DistributorDAO distributorDAO;
	@Autowired
	ReserveRecordSPI recordSPI;
	@Autowired
	ReserveRecordDAO reserveRecordDAO;
	
	@Override
	public void onlineCharge(CreateChargeReserveRecordCommand cr) {
		//update account
		 
		final String distributorID = cr.getDistributorID();
		Long count = cr.getIncrement();
		this.updateAccount(distributorID, count);
		
		//insert flow
		CreateReserveRecordCommand command = new CreateReserveRecordCommand();
		command.setAuthUserID(cr.getAuthUserID());
		command.setProvePath(cr.getProvePath());
		command.setDistributorID(distributorID);
		command.setIncrement(count);
		command.setProdName(PROD_积分充值);
		command.setStatus(CreateReserveRecordCommand.RECORD_STATUS_CHONGZHI_SUCC);
		command.setTradeNo(UUIDGenerator.getUUID());
		command.setType(  cr.getType());
		recordSPI.create(command);
	}

	/**更新备付金余额
	 * @param distributorID 商户id
	 * @param count 要增加的数量
	 */
	@Override
	public void updateAccount(final String distributorID, Long count) {
		ReserveInfo info = distributorDAO.get(distributorID).getReserveInfo();
		info.setAmount(info.getAmount() + count);
		info.setUsableBalance(info.getUsableBalance() + count);
		infoDao.update(info);
	}

	@Override
	public String freeze(MileOrder order) {
		boolean freezeOrCancel=true; 
		return freeze(order, freezeOrCancel);
	}
	@Override
	public void finishFreeze(MileOrder order) {
		//  
		ReserveInfo info = distributorDAO.get(order.getDistributor().getId()).getReserveInfo();
		info.setAmount(info.getAmount() - order.getCount());
		info.setFreezeBalance(info.getFreezeBalance() - order.getCount());
		infoDao.update(info);
	}

	@Override
	public void cancelFreeze(MileOrder order) {
		freeze(order, false);
	}	
	
	/**
	 * 冻结以及取消冻结
	 * @param order
	 * @param freezeOrCancel
	 * @return 错误信息 or null
	 */
	private String  freeze(MileOrder order, boolean freezeOrCancel) {
		//update account
		ReserveInfo info = distributorDAO.get(order.getDistributor().getId(),true).getReserveInfo();
		Long freezeCount =new Long( freezeOrCancel ? order.getCount()  : order.getCount()*-1);
//		logger.info(String.format("%s订单,可用余额%s，订单里程%s，可欠费余额%s",(freezeOrCancel ? "冻结" : "取消冻结"), info.getUsableBalance()+"",
//				order.getCount()+"", info.getArrearsAmount()+""));
		//  可欠费余额判断
//		if(info.getUsableBalance() - freezeCount < -info.getArrearsAmount())
		if(info.getUsableBalance().longValue() - freezeCount.longValue() + info.getArrearsAmount().longValue()<0 ){	
			return  String .format("可用余额%s，将要冻结%s，可欠费余额%s，冻结后余额不足。", info.getUsableBalance(),freezeCount,info.getArrearsAmount());
		}else{
			info.setFreezeBalance(info.getFreezeBalance() + freezeCount);
			info.setUsableBalance(info.getUsableBalance() - freezeCount);
			infoDao.update(info);//
			
			//insert flow
			CreateReserveRecordCommand command = new CreateReserveRecordCommand();
			//command.setAuthUserID("系统");
			command.setCardNo(order.getCsairCard());
			command.setDistributorID(order.getDistributor().getId());
			command.setFromPlatform(order.getDistributor().getName());
			command.setIncrement(-freezeCount);
			command.setOrderCode(order.getFlowCode());
			if(order.getProduct()!=null)
				command.setProdName(order.getProduct().getProdName());
			command.setStatus(freezeOrCancel? CreateReserveRecordCommand.RECORD_STATUS_KOUKUAN_SUCC:CreateReserveRecordCommand.RECORD_STATUS_REFUND_SUCC);
			command.setTradeNo(UUIDGenerator.getUUID());
			command.setType(freezeOrCancel?  ReserveRecord.RECORD_TYPE_TRADE: ReserveRecord.RECORD_TYPE_REFUND);
			recordSPI.create(command);
			return null;
		}
	}

	@Override
	public ReserveInfo create(CreateReserveInfoCommand command) {
		ReserveInfo reserveInfo = new ReserveInfo();
		return infoDao.save(new ReserveInfoManager(reserveInfo).create(command).get());
	}

	@Override
	public ReserveInfo modifyWarnValue(String id, Integer warnValue) {
		ReserveInfo reserveInfo = infoDao.get(id);
		return infoDao.update(new ReserveInfoManager(reserveInfo).modifyWarnValue(warnValue).get());
	}

	@Override
	public void auditAndUpdateReserve(ChargeUpdateReserveRecordCommand cr) {
		//审核
		AuditReserveRecordCommand command = cr.getAuditCmd();
		ReserveRecord reserveRecord = new ReserveRecord();
		if(StringUtils.isNotBlank(command.getReserveRecordID())&&reserveRecordDAO.get(command.getReserveRecordID())!=null){
			reserveRecord = reserveRecordDAO.get(command.getReserveRecordID());
			reserveRecordDAO.update(new ReserveRecordManager(reserveRecord).audit(command).get());
		}
		//update account
		final String distributorID = cr.getDistributorID();
		if(reserveRecord!=null)
			updateAccount(distributorID, reserveRecord.getIncrement());
		else
			throw new RuntimeException("command参数有误");

	}

	@Override
	public ReserveInfo modifyArrearsAmount(ModifyReserveInfoCommand cmd) {
		ReserveInfo reserveInfo = infoDao.get(cmd.getId());
		return infoDao.update(new ReserveInfoManager(reserveInfo).modifyArrearsAmount(cmd.getArrearsAmount()).get());
	}	

	public static void main(String[] args) {
		if(-100 - 300 +100< 0)
			System.out.println( "可用余额%s，将要冻结%s，可欠费余额%s，冻结后余额不足。" );
		else
			System.out.println();
	}
}
