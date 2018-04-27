package hg.demo.member.service.spi.impl.fx;

import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.service.dao.hibernate.AuthUserDAO;
import hg.demo.member.service.dao.hibernate.fx.DistributorDAO;
import hg.demo.member.service.dao.hibernate.fx.DistributorUserDAO;
import hg.demo.member.service.dao.hibernate.fx.FixedPriceSetDAO;
import hg.demo.member.service.qo.hibernate.AuthUserQO;
import hg.demo.member.service.qo.hibernate.fx.DistributorQO;
import hg.demo.member.service.qo.hibernate.fx.DistributorUserQO;
import hg.demo.member.service.qo.hibernate.fx.FixedPriceSetQO;
import hg.demo.member.service.qo.hibernate.fx.ProductQO;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.fixedprice.FixedPriceSet;
import hg.fx.spi.FixedPriceSetSPI;
import hg.fx.spi.qo.DistributorSQO;
import hg.fx.spi.qo.FixedPriceSetSQO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service("fixedPriceSetSPIService")
public class FixedPriceSetSPIService implements FixedPriceSetSPI{

	@Autowired
	private DistributorDAO distributorDAO;
	@Autowired
	private FixedPriceSetDAO fixedPriceSetDAO;
	@Autowired
	private DistributorUserDAO distributorUserDAO;
	@Autowired
	private AuthUserDAO authUserDAO;
	
	@Override
	public Pagination<FixedPriceSet> queryPage(FixedPriceSetSQO sqo) {
		List<FixedPriceSet> fixedPriceSets =  new ArrayList<>();
		ProductQO productQO = new ProductQO();
		DistributorQO distributorQO = new DistributorQO();
		distributorQO.setDiscountType(1);
		distributorQO.setStatus(1);
		if(StringUtils.isNotBlank(sqo.getDistributorID())){
			distributorQO.setId(sqo.getDistributorID());
		}else{
			distributorQO.setLimit(sqo.getLimit());
		}
		Pagination<Distributor> distributorPage = distributorDAO.queryPagination(distributorQO);
		List<Distributor> distributors = distributorPage.getList();
		FixedPriceSet fixedPriceSet = new FixedPriceSet();
		for (Distributor distributor : distributors) {
			//1
			//查询SQO传入月份 
			//查询 SQO传入月份 正在审核的
			FixedPriceSetQO qo =  new FixedPriceSetQO();
			distributorQO = new DistributorQO();
			distributorQO.setStatus(1);
			distributorQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_FIXED_PRICE);
			distributorQO.setId(distributor.getId());
			productQO.setId(sqo.getProductID());
			qo.setProductQO(productQO);
			qo.setDistributorQO(distributorQO);
			qo.setImplementYYYYMMType(1);
			qo.setImplementYYYYMM(sqo.getImplementDate());
			qo.setCheckStatus(0);
			qo.setInvalidDate(new Date());
			fixedPriceSet = fixedPriceSetDAO.queryUnique(qo);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			int thismonth = Integer.parseInt(simpleDateFormat.format(new Date()));
			if(thismonth==sqo.getImplementDate()){
				fixedPriceSet=null;
			}
			if(fixedPriceSet==null){
				//2
				//查询SQO传入月份 结果为空 证明这个月份没有正在审核的
				//查询 SQO传入月份 生效的
				qo =  new FixedPriceSetQO();
				distributorQO = new DistributorQO();
				distributorQO.setStatus(1);
				distributorQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_FIXED_PRICE);
				distributorQO.setId(distributor.getId());
				productQO.setId(sqo.getProductID());
				qo.setProductQO(productQO);
				qo.setDistributorQO(distributorQO);
				qo.setImplementYYYYMMType(1);
				qo.setImplementYYYYMM(sqo.getImplementDate());
				qo.setIsImplement(true);
				qo.setCheckStatus(1);
				qo.setInvalidDate(new Date());
				fixedPriceSet = fixedPriceSetDAO.queryUnique(qo);
				if(fixedPriceSet==null){
					//3
					//查询SQO传入月份 结果为空 证明这个月份没有设置
					//查询 小于SQO传入月份
					qo =  new FixedPriceSetQO();
					distributorQO = new DistributorQO();
					distributorQO.setStatus(1);
					distributorQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_FIXED_PRICE);
					distributorQO.setId(distributor.getId());
					productQO.setId(sqo.getProductID());
					qo.setProductQO(productQO);
					qo.setDistributorQO(distributorQO);
					qo.setImplementYYYYMMType(2);
					qo.setImplementYYYYMM(sqo.getImplementDate());
					qo.setCheckStatus(1);
					qo.setIsImplement(true);
					qo.setInvalidDate(null);
					fixedPriceSet = fixedPriceSetDAO.queryUnique(qo);
					if(fixedPriceSet==null){
						//4
						//查询 小于SQO传入月份 结果为空 证明这个商户就没设置过
						FixedPriceSet fPriceSet = new FixedPriceSet();
						fPriceSet.setDistributor(distributor);
						DistributorUserQO distributorUserQO = new DistributorUserQO();
						distributorUserQO.setDistributorQo(distributorQO);
						distributorUserQO.setStatus(1);
						DistributorUser distributorUser =distributorUserDAO.queryUnique(distributorUserQO);
						if(distributorUser!=null&&StringUtils.isNotBlank(distributorUser.getLoginName()))
							fPriceSet.setLoginName(distributorUser.getLoginName());
						simpleDateFormat = new SimpleDateFormat("yyyyMM");
						int createDate = Integer.parseInt(simpleDateFormat.format(distributor.getCreateDate()));
						int now = Integer.parseInt(simpleDateFormat.format(new Date()));
						if(now !=createDate){
							fPriceSet.setCheckStatus(99);
						}
						fixedPriceSets.add(fPriceSet);
					}else{
						DistributorUserQO distributorUserQO = new DistributorUserQO();
						distributorUserQO.setDistributorQo(distributorQO);
						distributorUserQO.setStatus(1);
						DistributorUser distributorUser =distributorUserDAO.queryUnique(distributorUserQO);
						if(distributorUser!=null&&StringUtils.isNotBlank(distributorUser.getLoginName()))
							fixedPriceSet.setLoginName(distributorUser.getLoginName());
						fixedPriceSets.add(fixedPriceSet);
					}
				}else{
					DistributorUserQO distributorUserQO = new DistributorUserQO();
					distributorUserQO.setDistributorQo(distributorQO);
					distributorUserQO.setStatus(1);
					DistributorUser distributorUser =distributorUserDAO.queryUnique(distributorUserQO);
					if(distributorUser!=null&&StringUtils.isNotBlank(distributorUser.getLoginName()))
						fixedPriceSet.setLoginName(distributorUser.getLoginName());
					fixedPriceSets.add(fixedPriceSet);
				}
			}else{
				DistributorUserQO distributorUserQO = new DistributorUserQO();
				distributorUserQO.setDistributorQo(distributorQO);
				distributorUserQO.setStatus(1);
				DistributorUser distributorUser =distributorUserDAO.queryUnique(distributorUserQO);
				if(distributorUser!=null&&StringUtils.isNotBlank(distributorUser.getLoginName()))
					fixedPriceSet.setLoginName(distributorUser.getLoginName());
				fixedPriceSets.add(fixedPriceSet);
			}
		}
		Pagination<FixedPriceSet> pagination = new Pagination<>();
		pagination.setList(fixedPriceSets);
		pagination.setPageNo(distributorPage.getPageNo());
		pagination.setPageSize(distributorPage.getPageSize());
		pagination.setTotalCount(distributorPage.getTotalCount());
		return pagination;
	}

	@Override
	public void saveFixedPriceSet(FixedPriceSet fixedPriceSet,String authUserID,String datetype) {
		String id=UUIDGenerator.getUUID();
		fixedPriceSet.setId(id);
		if(StringUtils.equals(datetype, "now")){
			fixedPriceSet.setCheckStatus(1);
			fixedPriceSet.setApplyDate(new Date());
			fixedPriceSet.setIsImplement(true);
			//开始判断下个月是否有规则 如果有更新本月的失效时间
			String now = String.valueOf(fixedPriceSet.getImplementYYYYMM());
			FixedPriceSetQO fixedPriceSetQO = new FixedPriceSetQO();
			DistributorQO distributorQO = new DistributorQO();
			distributorQO.setStatus(1);
			distributorQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_FIXED_PRICE);
			distributorQO.setId(fixedPriceSet.getDistributor().getId());
			ProductQO productQO = new ProductQO();
			productQO.setId(fixedPriceSet.getProduct().getId());
			fixedPriceSetQO.setProductQO(productQO);
			fixedPriceSetQO.setDistributorQO(distributorQO);
			fixedPriceSetQO.setImplementYYYYMMType(1);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			Calendar calendar = Calendar.getInstance();
			try {
				calendar.setTime(simpleDateFormat.parse(now));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			calendar.add(Calendar.MONTH, 1);
			String nextMonth = simpleDateFormat.format(calendar.getTime());
			fixedPriceSetQO.setImplementYYYYMM(Integer.parseInt(nextMonth));
			fixedPriceSetQO.setIsImplement(true);
			fixedPriceSetQO.setCheckStatus(1);
			FixedPriceSet nextFixedPriceSet = fixedPriceSetDAO.queryUnique(fixedPriceSetQO);
			if(nextFixedPriceSet!=null){
				nextMonth = nextMonth+"01 235959"; 
				simpleDateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
				calendar = Calendar.getInstance();
				try {
					calendar.setTime(simpleDateFormat.parse(nextMonth));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				calendar.add(Calendar.DATE, -1);
				fixedPriceSet.setInvalidDate(calendar.getTime());
			}
		}else{
			fixedPriceSet.setCheckStatus(0);
			fixedPriceSet.setApplyDate(new Date());
			fixedPriceSet.setIsImplement(false);
		}
		fixedPriceSet.setApplyUser(authUserDAO.get(authUserID));
		fixedPriceSetDAO.save(fixedPriceSet);
	}

	@Override
	public FixedPriceSet queryByID(String fixedPriceSetID) {
		return fixedPriceSetDAO.get(fixedPriceSetID);
	}

	@Override
	public Pagination<FixedPriceSet> queryPagination(FixedPriceSetSQO sqo) {
		FixedPriceSetQO qo = new FixedPriceSetQO();
		if(sqo.getLimit()!=null){
			LimitQuery limitQuery = sqo.getLimit();
			if(limitQuery.getPageNo()==null){
				limitQuery.setPageNo(1);
			}
			if(limitQuery.getPageSize()==null){
				limitQuery.setPageSize(20);
			}
			limitQuery.setByPageNo(true);
			qo.setLimit(limitQuery);
			qo.setDistributorQO(new DistributorQO());
			qo.setProductQO(new ProductQO());
			qo.setAuthUserQO(new AuthUserQO());
			qo.setSortflag(true);
			qo.setStatusflag(true);
			qo.setInvalidDate(new Date());
 			return fixedPriceSetDAO.queryPagination(qo);
		}else{
			LimitQuery limitQuery = new LimitQuery();
			limitQuery.setByPageNo(true);
			limitQuery.setPageSize(20);
			limitQuery.setPageNo(1);
			qo.setLimit(limitQuery);
			qo.setDistributorQO(new DistributorQO());
			qo.setProductQO(new ProductQO());
			qo.setAuthUserQO(new AuthUserQO());
			qo.setSortflag(true);
			qo.setStatusflag(true);
			qo.setInvalidDate(new Date());
			return fixedPriceSetDAO.queryPagination(qo);
		}
	}

	@Override
	public boolean shenhe(String fixedPriceSetID,String checkUserID,boolean result) {
		try{
			/**
			 * 校验
			 */
			if(StringUtils.isBlank(fixedPriceSetID)){
				throw new Exception();
			}
			FixedPriceSet fixedPriceSet =fixedPriceSetDAO.get(fixedPriceSetID);
			if(fixedPriceSet==null){
				throw new Exception();
			}
			if(StringUtils.isBlank(checkUserID)){
				throw new Exception();
			}
			AuthUser authUser=authUserDAO.get(checkUserID);
			if(authUser==null){
				throw new Exception();
			}
			/**
			 * 校验结束
			 */
			//判断当月是否有记录 有把状态改为N
			FixedPriceSetQO fixedPriceSetQO = new FixedPriceSetQO();
			DistributorQO distributorQO = new DistributorQO();
			distributorQO.setStatus(1);
			distributorQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_FIXED_PRICE);
			distributorQO.setId(fixedPriceSet.getDistributor().getId());
			ProductQO productQO = new ProductQO();
			productQO.setId(fixedPriceSet.getProduct().getId());
			fixedPriceSetQO.setProductQO(productQO);
			fixedPriceSetQO.setDistributorQO(distributorQO);
			fixedPriceSetQO.setImplementYYYYMMType(1);
			fixedPriceSetQO.setImplementYYYYMM(fixedPriceSet.getImplementYYYYMM());
			fixedPriceSetQO.setIsImplement(true);
			fixedPriceSetQO.setCheckStatus(1);
			FixedPriceSet nowFixedPriceSet = fixedPriceSetDAO.queryUnique(fixedPriceSetQO);
			if(nowFixedPriceSet!=null){
				nowFixedPriceSet.setIsImplement(false);
				nowFixedPriceSet.setInvalidDate(new Date());
				fixedPriceSetDAO.update(nowFixedPriceSet);
			}else{
				//更新之前记录的失效时间
				fixedPriceSetQO = new FixedPriceSetQO();
				distributorQO = new DistributorQO();
				distributorQO.setStatus(1);
				distributorQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_FIXED_PRICE);
				distributorQO.setId(fixedPriceSet.getDistributor().getId());
				productQO = new ProductQO();
				productQO.setId(fixedPriceSet.getProduct().getId());
				fixedPriceSetQO.setProductQO(productQO);
				fixedPriceSetQO.setDistributorQO(distributorQO);
				fixedPriceSetQO.setImplementYYYYMMType(2);
				fixedPriceSetQO.setImplementYYYYMM(fixedPriceSet.getImplementYYYYMM());
				fixedPriceSetQO.setIsImplement(true);
				fixedPriceSetQO.setCheckStatus(1);
				FixedPriceSet oldFixedPriceSet = fixedPriceSetDAO.queryUnique(fixedPriceSetQO);
				if(oldFixedPriceSet!=null){
					String lastMonth = String.valueOf(fixedPriceSet.getImplementYYYYMM()); 
					lastMonth = lastMonth+"01 235959"; 
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(simpleDateFormat.parse(lastMonth));
					calendar.add(Calendar.DATE, -1);
					oldFixedPriceSet.setInvalidDate(calendar.getTime());
					fixedPriceSetDAO.update(oldFixedPriceSet);
				}
			}
			//判断后一个月是否有规则 如果有更新失效时间 
			String now = String.valueOf(fixedPriceSet.getImplementYYYYMM());
			fixedPriceSetQO = new FixedPriceSetQO();
			distributorQO = new DistributorQO();
			distributorQO.setStatus(1);
			distributorQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_FIXED_PRICE);
			distributorQO.setId(fixedPriceSet.getDistributor().getId());
			productQO = new ProductQO();
			productQO.setId(fixedPriceSet.getProduct().getId());
			fixedPriceSetQO.setProductQO(productQO);
			fixedPriceSetQO.setDistributorQO(distributorQO);
			fixedPriceSetQO.setImplementYYYYMMType(1);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(simpleDateFormat.parse(now));
			calendar.add(Calendar.MONTH, 1);
			String nextMonth = simpleDateFormat.format(calendar.getTime());
			fixedPriceSetQO.setImplementYYYYMM(Integer.parseInt(nextMonth));
			fixedPriceSetQO.setIsImplement(true);
			fixedPriceSetQO.setCheckStatus(1);
			FixedPriceSet nextFixedPriceSet = fixedPriceSetDAO.queryUnique(fixedPriceSetQO);
			if(nextFixedPriceSet!=null){
				nextMonth = nextMonth+"01 235959"; 
				simpleDateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
				calendar = Calendar.getInstance();
				calendar.setTime(simpleDateFormat.parse(nextMonth));
				calendar.add(Calendar.MONTH, 1);
				calendar.add(Calendar.DATE, -1);
				nextFixedPriceSet.setInvalidDate(calendar.getTime());
				nextFixedPriceSet.setIsImplement(false);
				fixedPriceSetDAO.update(nextFixedPriceSet);
			}
			
			if(result){
				fixedPriceSet.setCheckStatus(1);
				fixedPriceSet.setIsImplement(true);
			}else{
				fixedPriceSet.setCheckStatus(2);
				fixedPriceSet.setIsImplement(false);
			}
			fixedPriceSet.setCheckDate(new Date());
			fixedPriceSet.setCheckUser(authUser);
			simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String thisMonth = simpleDateFormat.format(new Date());
			if(!(Integer.parseInt(thisMonth)+1==fixedPriceSet.getImplementYYYYMM())){
				//只有本月设置下月规则的时候 不改变生效时间
				//否则生效时间 都按照审批时间+1个月 开始
				calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.MONTH, 1);
				nextMonth = simpleDateFormat.format(calendar.getTime());
				fixedPriceSet.setImplementYYYYMM(Integer.parseInt(nextMonth));
				simpleDateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
				nextMonth = nextMonth+"01 000000";
				try {
					fixedPriceSet.setImplementDate(simpleDateFormat.parse(nextMonth));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			//更新审批记录
			fixedPriceSetDAO.update(fixedPriceSet);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void setFixedPriceIntervalID(String fixedPriceIntervalID,int theNextMonth) {
		FixedPriceSetQO fixedPriceSetQO = new FixedPriceSetQO();
		if(StringUtils.isNotBlank(fixedPriceIntervalID)){
			fixedPriceSetQO.setFixedPriceIntervalID(fixedPriceIntervalID);
		}
		fixedPriceSetQO.setImplementYYYYMMType(theNextMonth);
		List<FixedPriceSet> fixedPriceSets = fixedPriceSetDAO.queryList(fixedPriceSetQO);
		for (FixedPriceSet fixedPriceSet : fixedPriceSets) {
			
			//查询 小于SQO传入月份
			FixedPriceSetQO qo =  new FixedPriceSetQO();
			DistributorQO distributorQO = new DistributorQO();
			distributorQO.setStatus(1);
			distributorQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_FIXED_PRICE);
			distributorQO.setId(fixedPriceSet.getDistributor().getId());
			ProductQO productQO = new ProductQO();
			productQO.setId(fixedPriceSet.getProduct().getId());
			qo.setProductQO(productQO);
			qo.setDistributorQO(distributorQO);
			qo.setImplementYYYYMMType(2);
			qo.setImplementYYYYMM(fixedPriceSet.getImplementYYYYMM());
			qo.setCheckStatus(1);
			qo.setIsImplement(true);
			qo.setInvalidDate(null);
			FixedPriceSet oldfixedPriceSet = fixedPriceSetDAO.queryUnique(qo);
			if(oldfixedPriceSet!=null){
				String thistMonth = fixedPriceSet.getImplementYYYYMM()+"01 235959"; 
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
				Calendar calendar = Calendar.getInstance();
				try {
					calendar.setTime(simpleDateFormat.parse(thistMonth));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				calendar.add(Calendar.DATE, -1);
				oldfixedPriceSet.setInvalidDate(calendar.getTime());
				fixedPriceSetDAO.update(oldfixedPriceSet);
			}
			
			qo =  new FixedPriceSetQO();
			distributorQO = new DistributorQO();
			distributorQO.setStatus(1);
			distributorQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_FIXED_PRICE);
			distributorQO.setId(fixedPriceSet.getDistributor().getId());
			productQO = new ProductQO();
			productQO.setId(fixedPriceSet.getProduct().getId());
			qo.setProductQO(productQO);
			qo.setDistributorQO(distributorQO);
			qo.setImplementYYYYMMType(1);
			qo.setImplementYYYYMM(fixedPriceSet.getImplementYYYYMM());
			qo.setCheckStatus(1);
			qo.setIsImplement(true);
			qo.setInvalidDate(new Date());
			FixedPriceSet thisfixedPriceSet = fixedPriceSetDAO.queryUnique(qo);
			if(thisfixedPriceSet!=null){
				thisfixedPriceSet.setInvalidDate(new Date());
				thisfixedPriceSet.setIsImplement(false);
				fixedPriceSetDAO.update(thisfixedPriceSet);
			}
			
			
			fixedPriceSet.setFixedPriceIntervalID(null);
			String oldInterval = fixedPriceSet.getModifiedInterval();
			double oldPercent = fixedPriceSet.getModifiedPercent();
			fixedPriceSet.setCurrentInterval(oldInterval);
			fixedPriceSet.setCurrentPercent(oldPercent);
			fixedPriceSet.setModifiedInterval("0");
			fixedPriceSet.setModifiedPercent(1.0);
			fixedPriceSet.setCheckStatus(1);
			fixedPriceSet.setIsImplement(true);
			fixedPriceSetDAO.update(fixedPriceSet);
		}
	}

	
}
