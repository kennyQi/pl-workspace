package hg.demo.web.component.job;

import hg.demo.web.controller.fixedPrice.QuJian;
import hg.framework.common.base.quartz.QuartzTask;
import hg.fx.command.report.CreateFixedPriceReportCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.MileOrder;
import hg.fx.domain.ProductInUse;
import hg.fx.domain.fixedprice.FixedPriceInterval;
import hg.fx.domain.fixedprice.FixedPriceSet;
import hg.fx.spi.ChannelSPI;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.FixedPriceIntervalSPI;
import hg.fx.spi.FixedPriceReportSPI;
import hg.fx.spi.FixedPriceSetSPI;
import hg.fx.spi.MileOrderSPI;
import hg.fx.spi.ProductInUseSPI;
import hg.fx.spi.ProductSPI;
import hg.fx.spi.qo.DistributorSQO;
import hg.fx.spi.qo.FixedPriceIntervalSQO;
import hg.fx.spi.qo.FixedPriceSetSQO;
import hg.fx.spi.qo.MileOrderSQO;
import hg.fx.spi.qo.ProductInUseSQO;
import hg.fx.util.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 定价定时任务
 * @author admin
 * @date 2016-7-27上午10:17:57
 * @since
 */
@Component
public class FixedPriceReportJob extends QuartzTask{


	@Autowired
	private DistributorSPI distributorSPIServ;
	
	@Autowired
	private ProductInUseSPI productInUseSPIServ;
	
	@Autowired
	private FixedPriceIntervalSPI fixedPriceIntervalSPIService;
	
	@Autowired
	private FixedPriceSetSPI fixedPriceSetSPIService;
	
	@Autowired
	public MileOrderSPI mileOrderService;
	@Autowired
	public ChannelSPI channelSPIService;
	@Autowired
	public ProductSPI productSPIService;
	@Autowired
	public DistributorSPI distributorService;
	@Autowired
	private FixedPriceReportSPI fixedPriceReportSPIService;
	
	public FixedPriceReportJob(){
		setGroupName("里程分销");
		setTaskName("里程分销--每月10号生成定价商户月报");
		// 每月1号凌时
//		setCronExpression("0 0 0 10 * ?");
		setCronExpression("0 0/1 * * * ?");
	}
	
	public void toJob() {
		DistributorSQO sqo = new DistributorSQO();
		sqo.setDiscountType(Distributor.DISCOUNT_TYPE_FIXED_PRICE);
		// 查询返利的商户
		List<Distributor> distributors = distributorSPIServ.queryList(sqo);
		System.out.println("里程分销--每月10号生成定价商户月报");
		for(Distributor item : distributors){
			ProductInUseSQO piuSqo = new ProductInUseSQO();
			piuSqo.setDistributorId(item.getId());
			piuSqo.setStatusArray(new Integer[]{ProductInUse.STATUS_OF_IN_USE, ProductInUse.STATUS_OF_DISABLE});// 查询使用中和已停用的商品
			List<ProductInUse> list = productInUseSPIServ.queryList(piuSqo);
			if (list==null || list.size()==0)
				continue;
			for(ProductInUse pro:list){
				CreateFixedPriceReportCommand cmd = new CreateFixedPriceReportCommand();
				Long total = calcuReport(item.getId(), pro.getProduct().getId());
					System.out.println(item.getName()+"产生"+total+"订单");
				//设置月消费里程数
				cmd.setConsumeNum(total);
				//渠道商
				cmd.setChannelName(pro.getProduct()
						.getChannel().getChannelName());
				//商户ID
				cmd.setDistributorId(item.getId());
				//商户公司名
				cmd.setDistributorName(item.getName());
				FixedPriceSet fixedPriceSet = getLastMonth(pro.getProduct().getId(), item.getId());
					
				//上月适用的规则
				cmd.setFixedPriceSet(fixedPriceSet);
				if(fixedPriceSet==null||fixedPriceSet.getId()==null){
					//目标折扣
					cmd.setCurrentPercent(1.0);
					//目标区间
					cmd.setTargetInterval("0");
				}else{
					//目标折扣
					cmd.setCurrentPercent(fixedPriceSet.getModifiedPercent());
					//目标区间
					cmd.setTargetInterval(fixedPriceSet.getModifiedInterval());
				}
				//查询区间
				FixedPriceIntervalSQO fixedPriceIntervalSQO = new FixedPriceIntervalSQO();
				fixedPriceIntervalSQO.setProductID(pro.getProduct().getId());
				String yyyymm = DateUtil.formatDate1(DateUtil.parseDate2(DateUtil.getLastMonthFistDate())).substring(0, 6);
				fixedPriceIntervalSQO.setCreateDate(Integer.parseInt(yyyymm));
				FixedPriceInterval fixedPriceInterval = fixedPriceIntervalSPIService.queryLastFixedPriceInterval(fixedPriceIntervalSQO);
				System.out.println(yyyymm+"定价区间"+fixedPriceInterval.getIntervalStr());
				//实际折扣
				cmd.setFactPercent(Double.parseDouble(calFactPercent(total,fixedPriceInterval)));
				//商品id
				cmd.setProdId(pro.getProduct().getId());
				//商品名
				cmd.setProdName(pro.getProduct().getProdName());
				fixedPriceReportSPIService.createFixedPriceReport(cmd);
			}
		}
		//return "/order/list.ftl";
	}
	
	private Long calcuReport(String distributorId,String productId){
		//查找符合条件的订单
		MileOrderSQO mileOrderSQO = new MileOrderSQO();
		mileOrderSQO.setDistributorId(distributorId);
		mileOrderSQO.setProductId(productId);
		mileOrderSQO.setStrImportDate(DateUtil.getLastMonthFistDate());
		mileOrderSQO.setEndImportDate(DateUtil.getLastMonthEndDate());
		mileOrderSQO.setStatus(MileOrderSQO.STATUS_CSAIR_SUCCEED);
		
		List<MileOrder> list = mileOrderService.queryList(mileOrderSQO);
		Long total = 0l;
		// 统计月订单销售额
		for (MileOrder o : list){
			total = total.longValue() + o.getNum().longValue()*o.getUnitPrice().longValue();
		}
		return total;
	}
	
	private String calFactPercent(Long total,FixedPriceInterval fixedPriceInterval){
		List<QuJian> intervalList = getQujianList(fixedPriceInterval.getIntervalStr());
		for(int i=0;i<intervalList.size();i++){
			Long intervalLong = Long.parseLong(intervalList.get(i).getQj());
			if(total.longValue()<intervalLong.longValue()){
				if(i==0){
					return intervalList.get(i).getZk();
				}else{
					return intervalList.get(i-1).getZk();
				}
			}
			if(i==intervalList.size()-1){
				return intervalList.get(i).getZk();
			}
		}
		return "1";
	}
	
	private List<QuJian> getQujianList(String intervalStr){
		Map<String, String> map ;
		if(StringUtils.isNotBlank(intervalStr)){
			map = (Map<String, String>)JSONObject.parse(intervalStr);
		}else{
			map = new LinkedHashMap<String, String>();
		}
		List<QuJian> intervalList = new ArrayList<>();
		for (String key : map.keySet()) {
			QuJian qujian = new QuJian();
			qujian.setQj(key);
			qujian.setZk(map.get(key));
			intervalList.add(qujian);
		}
		Collections.sort(intervalList, new Comparator<QuJian>() {
			@Override
			public int compare(QuJian o1, QuJian o2) {
				return Integer.valueOf(o1.getQj()).compareTo(Integer.valueOf(o2.getQj()));
			}
		});
		return intervalList;
	}
	
	private FixedPriceSet getLastMonth(String proId,String distributorId){
		FixedPriceSetSQO fixedPriceSetSQO = new FixedPriceSetSQO();
		fixedPriceSetSQO.setDistributorID(distributorId);
		fixedPriceSetSQO.setProductID(proId);
		String yyyymm = DateUtil.formatDate1(DateUtil.parseDate2(DateUtil.getLastMonthFistDate())).substring(0, 6);
		System.out.println("规则时间："+yyyymm);
		fixedPriceSetSQO.setImplementDate(Integer.parseInt(yyyymm));
		FixedPriceSet fixedPriceSet = null;
		try {
			fixedPriceSet = fixedPriceSetSPIService.queryPage(fixedPriceSetSQO).getList().get(0);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("查找上月规则失败！");
			System.out.println("商户Id："+distributorId);
			System.out.println("商品id："+proId);
			throw e;
		}
		return fixedPriceSet;
	}

	@Override
	public void execute() {
		toJob();
	}
}
