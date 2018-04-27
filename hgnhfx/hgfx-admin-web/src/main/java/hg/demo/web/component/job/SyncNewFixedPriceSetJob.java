package hg.demo.web.component.job;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hg.demo.web.component.cache.FixedPriceSetManager;
import hg.framework.common.base.quartz.QuartzTask;
import hg.fx.domain.Distributor;
import hg.fx.domain.ProductInUse;
import hg.fx.domain.fixedprice.FixedPriceSet;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.FixedPriceSetSPI;
import hg.fx.spi.ProductInUseSPI;
import hg.fx.spi.qo.DistributorSQO;
import hg.fx.spi.qo.FixedPriceSetSQO;
import hg.fx.spi.qo.ProductInUseSQO;
import hg.fx.util.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 每月1号凌晨  同步新的商户定价折扣到redis
 * */
@Component
public class SyncNewFixedPriceSetJob extends QuartzTask{

	@Autowired
	private FixedPriceSetManager fixedManager;
	
	@Autowired
	private FixedPriceSetSPI fixedPriceSetSPI;
	
	@Autowired
	private DistributorSPI distributorSPIServ;
	
	@Autowired
	private ProductInUseSPI productInUseSPIServ;
	
	private static Log logger = LogFactory.getLog(SyncNewFixedPriceSetJob.class);
	
	
	public SyncNewFixedPriceSetJob(){
		setGroupName("里程分销");
		setTaskName("里程分销--每月1号凌晨同步新的商户定价折扣到缓存");
		// 每月1号凌时
		setCronExpression("0 0 0 1 * ?");
//		setCronExpression("0 0/1 * * * ?");
	}                      
	
	@Override
	public void execute() {
		System.out.println("每月1号凌晨同步商户定价折扣到缓存------启动");
		
		DistributorSQO sqo = new DistributorSQO();
		sqo.setDiscountType(Distributor.DISCOUNT_TYPE_FIXED_PRICE);
		// 查询定价模式的商户
		List<Distributor> distributors = distributorSPIServ.queryList(sqo);
		
		ProductInUseSQO piuSqo = new ProductInUseSQO();
		//为设置本月
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String thisMonth = simpleDateFormat.format(new Date());
		
		// 查询商户使用关联的商品
		for (Distributor d : distributors){
			
			piuSqo.setDistributorId(d.getId());
			piuSqo.setStatusArray(new Integer[]{ProductInUse.STATUS_OF_IN_USE, ProductInUse.STATUS_OF_DISABLE});// 查询使用中和已停用的商品
			List<ProductInUse> list = productInUseSPIServ.queryList(piuSqo);
			
			if (list==null || list.size()==0)
				continue;
			
			for(ProductInUse p : list){
				// 查询商户当前商品本月使用的定价规则
				FixedPriceSetSQO fpQo = new FixedPriceSetSQO();
				fpQo.setDistributorID(d.getId());
				fpQo.setImplementDate(Integer.parseInt(thisMonth));
				fpQo.setProductID(p.getProduct().getId());
				List<FixedPriceSet> fl = fixedPriceSetSPI.queryPage(fpQo).getList();
				
				if (fl==null || fl.size()!=1)
					continue;
				
				if (StringUtils.isBlank(fl.get(0).getId())){
					System.out.println("经销商{"+d.getId()+" "+d.getName()+"}在本月"+thisMonth+" 无新定价规则");					
					continue;
				}
				
				// 同步到redis
				fixedManager.updateFixedPriceSet(p.getProduct().getId(), d.getId(), fl.get(0).getModifiedPercent().toString());
			}
		}
		System.out.println("每月1号凌晨同步商户定价折扣到缓存------结束");
	}

	
}
