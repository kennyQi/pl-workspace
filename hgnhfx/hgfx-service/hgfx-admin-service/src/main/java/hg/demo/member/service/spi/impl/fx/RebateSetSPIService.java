package hg.demo.member.service.spi.impl.fx;

import hg.demo.member.service.dao.hibernate.fx.DistributorDAO;
import hg.demo.member.service.dao.hibernate.fx.DistributorUserDAO;
import hg.demo.member.service.dao.hibernate.fx.ProductDAO;
import hg.demo.member.service.dao.hibernate.fx.RebateSetDAO;
import hg.demo.member.service.domain.manager.fx.RebateSetManager;
import hg.demo.member.service.qo.hibernate.fx.DistributorQO;
import hg.demo.member.service.qo.hibernate.fx.ProductQO;
import hg.demo.member.service.qo.hibernate.fx.RebateSetQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.rebate.AduitRebateSetCommand;
import hg.fx.command.rebate.CreateRebateSetCommand;
import hg.fx.command.rebate.ModifyRebateSetCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.Product;
import hg.fx.domain.rebate.RebateSet;
import hg.fx.spi.RebateSetSPI;
import hg.fx.spi.qo.RebateSetSQO;
import hg.fx.util.DateUtil;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service("rebateSetSPIService")
public class RebateSetSPIService extends BaseService implements RebateSetSPI{

	@Autowired
	private RebateSetDAO rebateDao;
	@Autowired
	private ProductDAO productDao;
	@Autowired
	private DistributorDAO distribuDao;
	@Autowired
	private DistributorUserDAO distributorUserDao;
	@Override
	public Pagination<RebateSet> queryAduitPagination(RebateSetSQO sqo) {
		Pagination<RebateSet> result = rebateDao.queryPagination(RebateSetQO.build(sqo));
		return result;
	}

	@Override
	public List<RebateSet> queryAduitList(RebateSetSQO sqo) {
		List<RebateSet> result = rebateDao.queryList(RebateSetQO.build(sqo));
		return result;
	}

	@Override
	public RebateSet queryUnique(RebateSetSQO sqo) {
		RebateSetQO qo = RebateSetQO.build(sqo);
		qo.setDistributorQO(new DistributorQO());
		qo.setProductQO(new ProductQO());
		RebateSet result = rebateDao.queryUnique(qo);
		return result;
	}

	/**
	 * 根据当前配置id查询上月配置信息
	 */
	@Override
	public RebateSet queryRelativeSetById(String rebateSetId) {
		if(StringUtils.isBlank(rebateSetId)){
			System.out.println("id is null");
			return null;
		}
		RebateSetSQO sqo = new RebateSetSQO();
		sqo.setId(rebateSetId);
		RebateSet rebateSet = rebateDao.queryUnique(RebateSetQO.build(sqo));
		if(rebateSet==null){
			System.out.println("rebateSet is null");
			return null;
		}
		
		//如果没有修改过
		if(StringUtils.isBlank(rebateSet.getRunningSetId())){
			return rebateSet;
		}
		if(!DateUtil.formatDateTime1(rebateSet.getImplementDate()).equals(DateUtil.forDateFirst()+" 00:00:00")){
			return rebateSet;
		}
	
		//修改过
		RebateSetSQO sqo2 = new RebateSetSQO();
		sqo2.setId(rebateSet.getRunningSetId());
		RebateSet result = rebateDao.queryUnique(RebateSetQO.build(sqo2));
		return result;
	}

	@Override
	public void aduitRebateSet(AduitRebateSetCommand cmd, boolean isPass) {
		if(isPass){
			aduitRebateSetPass(cmd);
		}else{
			aduitRebateSetRefuse(cmd);
		}
		
	}
	
	private void aduitRebateSetPass(AduitRebateSetCommand cmd){
		/*
		 *审核通过 
		 *如果本月同一商品的商户规则没有被审核过(状态为审核通过的)
		 *A.将生效时间设置为次月，例如本月是7月，则生效时间为2016-08-01 00:00:00 
		 *B.将状态给为Y (生效)，审核状态为1(已通过)
		 *C.将本月(7月)的正在生效的规则的失效时间改为本月月底(2016-07-31 23:59:59)
		 *D.记录审核时间，审核人
		 *如果本月同一商品的商户规则已经审核过(状态为审核通过的)
		 *A.将上一条审核通过的记录的状态改为N(失效) 
		 */
		RebateSetSQO sqo = new RebateSetSQO();
		sqo.setDistributorId(cmd.getDistributorId());
		sqo.setProductId(cmd.getProductId());
		RebateSet aduitRebateSet = rebateDao.get(cmd.getId());
		String lastRebateId = aduitRebateSet.getRunningSetId();
		cmd.setIsImplement(true);
		//将本月(7月)的正在生效的规则的失效时间改为本月月底(20160731 23:59:59)
		List<RebateSet> runningList = queryCurrMonthSet(sqo).getList();
		if(runningList!=null&&runningList.size()!=0){
			//将本月(7月)的正在生效的规则的失效时间改为本月月底(2016-07-31 23:59:59)
			RebateSet currRebateSet = runningList.get(0);
			ModifyRebateSetCommand modifyCmd = new ModifyRebateSetCommand();
			//去除标识标志当月设置已经有审核数据了
			modifyCmd.setIsCheck(false);
			modifyCmd.setInvalidDate(DateUtil.parseDateTime1(DateUtil.forDatelast()+" 23:59:59"));
			rebateDao.update(new RebateSetManager(currRebateSet).modify(modifyCmd).get());
		}else{
			throw new RuntimeException("没有正在运行的规则,审核失败");
		}
		//
		rebateDao.update(new RebateSetManager(aduitRebateSet).aduit(cmd,runningList.get(0).getId()).get());
		//将上一条审核通过的记录的状态改为N(失效) 
		RebateSet lastRebateSet = rebateDao.get(lastRebateId);
		//如果生效时间是下个月说明是审核记录作废，否则是正在生效的数据处理
		if(DateUtil.formatDateTime1(lastRebateSet.getImplementDate()).equals(DateUtil.getNextFirstMonthdate()+" 00:00:00")){
			ModifyRebateSetCommand modifyCmd = new ModifyRebateSetCommand();
			modifyCmd.setIsImplement(false);
			rebateDao.update(new RebateSetManager(lastRebateSet).modify(modifyCmd).get());
		}
	}
	private void aduitRebateSetRefuse(AduitRebateSetCommand cmd){
		/*
		 * A.将状态给为N (失效)，审核状态为-1(已拒绝)
		 * B.记录审核时间，审核人
		 */
		RebateSet aduitRebateSet = rebateDao.get(cmd.getId());
		cmd.setIsImplement(false);
		
		rebateDao.update(new RebateSetManager(aduitRebateSet).aduit(cmd,aduitRebateSet.getRunningSetId()).get());
		//去除标识标志当月设置已经有审核数据了
		RebateSet lastAduitRebateSet = rebateDao.get(aduitRebateSet.getRunningSetId());
		ModifyRebateSetCommand modifyCmd = new ModifyRebateSetCommand();
		modifyCmd.setIsCheck(false);
		rebateDao.update(new RebateSetManager(lastAduitRebateSet).modify(modifyCmd).get());
	}

	@Override
	public Pagination<RebateSet> queryCurrMonthSet(RebateSetSQO sqo) {
		/* 查询条件
		 * A.生效时间<=(本月 00:00:00 )
		 * B.生效状态为Y
		 * C.失效时间为空或者失效时间=(本月 23:59:59)
		 */
		String implementDateEnd = DateUtil.forDateFirst();
		sqo.setImplementDateEnd(implementDateEnd);
		String invalidDate = DateUtil.forDatelast();
		sqo.setIsImplement(true);
		RebateSetQO qo = RebateSetQO.build(sqo);
		qo.setQueryWay(qo.QUERY_CURR_MONTH);
		qo.setInvalidDateEQ1(DateUtil.parseDateTime1(invalidDate+" 23:59:59"));
		Pagination<RebateSet> result = rebateDao.queryPagination(qo);
		return result;
	}

	@Override
	public Pagination<RebateSet> queryNextMonthSet(RebateSetSQO sqo) {
		/*
		 * 查询条件
		 *A.生效时间=(次月 00:00:00)
		 *B.生效状态为Y
			--------------和下面的关系是并
		 *C.生效时间<=(本月 00:00:00 )
		 *D.生效状态为Y或null
		 *E.失效时间null
		 */
		//sqo.setIsImplement(true);
		RebateSetQO qo = RebateSetQO.build(sqo);
		//用dao去处理 不用标签处理
		qo.setQueryWay(qo.QUERY_NEXT_MONTH);
		String implementDate = DateUtil.getNextFirstMonthdate();
		qo.setImplementDateEQ1(DateUtil.parseDateTime1(implementDate+" 00:00:00"));
		String implementDateEnd = DateUtil.forDateFirst();
		qo.setImplementDateEnd1(DateUtil.parseDateTime1(implementDateEnd+" 23:59:59"));
		Pagination<RebateSet> result1 = rebateDao.queryPagination(qo);
		
		//sqo.setImplementDate(null);
		//String implementDateEnd = DateUtil.forDateFirst();
		//sqo.setImplementDateEnd(implementDateEnd);
		//sqo.setInvalidDate(null);
		//合并
		//List<RebateSet> result2 = rebateDao.queryList(RebateSetQO.build(sqo));
		//for(RebateSet item : result2){
		//	result1.add(item);
		//}
		return result1;
	}

	@Override
	public void createApplyRebateSet(CreateRebateSetCommand cmd) {
		Product pro = productDao.get(cmd.getProductId());
		cmd.setProduct(pro);
		cmd.setProductName(pro.getProdName());
		Distributor distributor = distribuDao.get(cmd.getDistributorId());
		cmd.setDistributor(distributor);
		cmd.setDistributorName(distributor.getName());
		DistributorUser distributorUser = distributorUserDao.get(cmd.getDistributorUserId());
		cmd.setLoginName(distributorUser.getLoginName());
		rebateDao.save(new RebateSetManager(new RebateSet()).createApply(cmd).get());
		//标识标志当月设置已经有审核数据了
		RebateSet lastAduitRebateSet = rebateDao.get(cmd.getRunningSetId());
		ModifyRebateSetCommand modifyCmd = new ModifyRebateSetCommand();
		modifyCmd.setIsCheck(true);
		rebateDao.update(new RebateSetManager(lastAduitRebateSet).modify(modifyCmd).get());
	}

	@Override
	public void createDefaultRebateSet(CreateRebateSetCommand cmd) {
		Product pro = productDao.get(cmd.getProductId());
		cmd.setProduct(pro);
		cmd.setProductName(pro.getProdName());
		Distributor distributor = distribuDao.get(cmd.getDistributorId());
		cmd.setDistributor(distributor);
		cmd.setDistributorName(distributor.getName());
		DistributorUser distributorUser = distributorUserDao.get(cmd.getDistributorUserId());
		cmd.setLoginName(distributorUser.getLoginName());
		rebateDao.save(new RebateSetManager(new RebateSet()).createDefault(cmd).get());
		
	}

	@Override
	public void modifyDefaultRebateSet(ModifyRebateSetCommand cmd,String id) {
		// TODO Auto-generated method stub
		RebateSet rebateSet = rebateDao.get(id);
		rebateDao.update(new RebateSetManager(rebateSet).modify(cmd).get());
	}
	
	

}
