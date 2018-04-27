package hg.demo.member.service.spi.impl.fx;

import hg.demo.member.service.dao.hibernate.fx.NewMileOrderDao;
import hg.demo.member.service.qo.hibernate.fx.NewMileOrderQO;
import hg.framework.common.model.Pagination;
import hg.framework.common.util.DateUtil;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.mileOrder.CheckMileOrderCommand;
import hg.fx.command.mileOrder.ConfirmMileOrderCommand;
import hg.fx.command.mileOrder.ImportMileOrderCommand;
import hg.fx.domain.MileOrder;
import hg.fx.spi.MileOrderSPI;
import hg.fx.spi.qo.MileOrderSQO;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author zqq
 * @date 2016-6-1下午4:29:01
 * @since
 */
@Transactional
@Service
public class MileOrderSPIService extends BaseService implements MileOrderSPI{

	@Autowired
	NewMileOrderDao mileOrderDao;
	
	@Autowired
	MileOrderService mileOrderService;
	
	@Override
	public MileOrder queryUnique(MileOrderSQO qo) {
		//  
		return mileOrderDao.queryUnique(NewMileOrderQO.build(qo));
	}

	@Override
	public List<MileOrder> queryList(MileOrderSQO qo) {
		//   Auto-generated method stub
		return mileOrderDao.queryList(NewMileOrderQO.build(qo));
	}

	@Override
	public Pagination<MileOrder> queryPagination(MileOrderSQO qo) {
		//   Auto-generated method stub
		return mileOrderDao.queryPagination(NewMileOrderQO.build(qo));
	}

	/**
	 * 导入批量订单，检查卡号.
	 * @param importMileOrderCommand
	 * @return 错误在前的批量列表
	 */
	public ImportMileOrderCommand importBatch(
			ImportMileOrderCommand importMileOrderCommand) {
		return mileOrderService.importExcel(importMileOrderCommand);
	}

	/**
	 * 提交批量订单。提交导入的订单。逐笔处理：预付金够，冻结预付金，生成订单和预付金变化明细，否则生成状态为取消的订单。
	 * @param importMileOrderCommand
	 * @return 
	 * @return 
	 */
	public ImportMileOrderCommand submitBatch(ImportMileOrderCommand importMileOrderCommand) {
		if(importMileOrderCommand.getList().size()>=1000){
			
		}
//			System.out.println("submitBatch start " +DateUtil.formatDateTime4(new Date()) +" "+importMileOrderCommand);

		final ImportMileOrderCommand submitBatch = mileOrderService.submitBatch(importMileOrderCommand);
		
		if(importMileOrderCommand.getList().size()>=1000){
			
		}
//			System.out.println("submitBatch end " +DateUtil.formatDateTime4(new Date()) +" "+importMileOrderCommand);
		
		return submitBatch;
	}

	/**
	 * 订单批量通过、拒绝， 输入ids记录id数组，flag=true通过 false拒绝
	 * @param ids
	 * @param flag
	 * @throws Exception
	 */
	public void  batchCheck(CheckMileOrderCommand cmd, Boolean flag)throws Exception{
		 
		mileOrderService.batchCheck(cmd, flag);
	}

	/**
	 * 标记 已提交订单给外部处理
	 * @param id
	 */
	@Override
	public void sentOrder(String id) {
		mileOrderService.sendOrderByOrderCode(id);
	}

	@Override
	public void setCheckAbnormal(boolean checkAbnormal) {
		mileOrderService.setCheckAbnormal(checkAbnormal);
	}

	@Override
	public void batchConfirm(ConfirmMileOrderCommand cmd, Boolean flag){
		mileOrderService.batchConfirm(cmd, flag);
	}


}
