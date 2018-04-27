package hg.demo.member.service.spi.impl.fx;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.demo.member.service.dao.hibernate.fx.ProductInUseDAO;
import hg.demo.member.service.dao.hibernate.fx.RebateReportDAO;
import hg.demo.member.service.dao.mybatis.RebateReportMyBatisDao;
import hg.demo.member.service.qo.hibernate.fx.FixedPriceReportQO;
import hg.demo.member.service.qo.hibernate.fx.RebateReportQO;
import hg.framework.common.model.Pagination;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.report.CreateRebateReportCommand;
import hg.fx.command.reserveRecord.CreateReserveRecordCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.MileOrder;
import hg.fx.domain.ProductInUse;
import hg.fx.domain.rebate.RebateReport;
import hg.fx.domain.rebate.RebateSet;
import hg.fx.domain.rebate.RebateSetDto;
import hg.fx.spi.ProductInUseSPI;
import hg.fx.spi.RebateReportSPI;
import hg.fx.spi.RebateSetSPI;
import hg.fx.spi.ReserveInfoSPI;
import hg.fx.spi.ReserveRecordSPI;
import hg.fx.spi.qo.ProductInUseSQO;
import hg.fx.spi.qo.RebateReportSQO;
import hg.fx.spi.qo.RebateSetSQO;
import hg.fx.util.DateUtil;

@Transactional
@Service("rebateReportSPIService")
public class RebateReportSPIService implements RebateReportSPI {

	@Autowired
	private RebateReportMyBatisDao rebateReportMyBatisDao;
	
	@Autowired
	private RebateReportDAO rebateReportDAO;
	
	@Autowired
	private ProductInUseSPI productInUseSPIServ;
	
	@Autowired
	private RebateSetSPI rebateSetSPIServ;
	
	@Autowired
	private ReserveInfoSPI reserveInfoSPIServ;
	
	@Autowired
	private ReserveRecordSPI reserveRecordSPIServ;
	
	
	@Override
	public List<MileOrder> queryOrderForRebateReport(HashMap<String, Object> map) {
		return rebateReportMyBatisDao.queryOrderForRebateReport(map);
	}


	@Override
	public void createRebateReport(CreateRebateReportCommand command) {
		
		RebateReport entity = new RebateReport();
		entity.setChannelName(command.getProductInUse().getProduct()
				.getChannel().getChannelName());
		entity.setConsumeNum(command.getConsumeNum());
		entity.setCreateDate(new Date());
		entity.setDistributorId(command.getDistributor().getId());
		entity.setDistributorName(command.getDistributor().getName());
		entity.setId(UUIDGenerator.getUUID());
		entity.setInterval(command.getInterval());
		entity.setPercent(command.getPercent());
		entity.setProdId(command.getProductInUse().getProduct().getId());
		entity.setProdName(command.getProductInUse().getProduct().getProdName());
		entity.setRebateNum(command.getRebateNum());
		entity.setRebateSet(command.getRebateSet());
		rebateReportDAO.save(entity);
	}


	@Override
	public Pagination<RebateReport> queryPagination(RebateReportSQO sqo) {
		RebateReportQO qo = new RebateReportQO();
		qo.setLimit(sqo.getLimit());
		if(StringUtils.isNotBlank(sqo.getDistributorId())){
			qo.setDistributorId(sqo.getDistributorId());
		}
		if(StringUtils.isNotBlank(sqo.getProdId())){
			qo.setProdId(sqo.getProdId());
		}
		if(sqo.getStartDate()!=null&&sqo.getEndDate()!=null){
			qo.setStartDate(sqo.getStartDate());
			qo.setEndDate(sqo.getEndDate());
		}
		return rebateReportDAO.queryPagination(qo);
	}


	@Override
	public void queryRebateReportData(Distributor d) {
		
		// 查询商户下使用的商品
		ProductInUseSQO piuSqo = new ProductInUseSQO();
		piuSqo.setDistributorId(d.getId());
		piuSqo.setStatusArray(new Integer[]{ProductInUse.STATUS_OF_IN_USE, ProductInUse.STATUS_OF_DISABLE});// 查询使用中和已停用的商品
		List<ProductInUse> list = productInUseSPIServ.queryList(piuSqo);
		
		if (list==null || list.size()==0)
			return;
		
		// 计算上月第一天、最后一天日期
		String fist = DateUtil.getLastMonthFistDate();
	    String end = DateUtil.getLastMonthEndDate();
		
	    RebateSetSQO rsqo = new RebateSetSQO();
		
		for(ProductInUse piu : list){
			
			RebateSet set = null;
			
			// 查询当前商品本月执行的返利规则
			rsqo.setDistributorId(d.getId());
			rsqo.setProductId(piu.getProduct().getId());
			Pagination<RebateSet> pag = rebateSetSPIServ.queryCurrMonthSet(rsqo);
			
			// 查询当前商品上月执行的返利规则
			if (pag.getList()!=null && pag.getList().size()==1){
				set = pag.getList().get(0);
				set = rebateSetSPIServ.queryRelativeSetById(set.getId());
			}
			
			// 查询商户商品的订单
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("prodId", piu.getProduct().getId());
			map.put("merId", d.getId());
			map.put("startDate", DateUtil.parseDateTime1(fist+" 00:00:00"));
			map.put("endDate", DateUtil.parseDateTime1(end+" 23:59:59"));
			map.put("status", MileOrder.STATUS_CSAIR_SUCCEED); // 南航订单返回处理成功
			
			List<MileOrder> orders = queryOrderForRebateReport(map);
			
			// 当前商品无订单
			if (orders==null || orders.size()==0){
				System.out.println("经销商{id="+d.getId()+" "+d.getName()+"}  "+fist+" 至 "+end+" 没有商品"+piu.getProduct().getProdName()+"的订单数据");	
				continue;
			}
				
			Long total = 0l;
			// 统计月订单销售额
			for (MileOrder o : orders){
				total = total.longValue() + o.getCount().longValue();
			}
			
			if (set!=null){ // 商品对应有返利规则时
				
				// 满足的折扣
				Double percent = 0d;
				// 满足的折扣区间
				String interval = null;
				
				// 解析规则中的区间
				RebateSetDto dto = new RebateSetDto(set);
				// 对比规则  判断返利区间 计算返利额
				for (int i=dto.getIntervalList().size()-1; i>=0; i-- ){
					if (total >= Long.valueOf(dto.getIntervalList().get(i).getQj())){
						interval = dto.getIntervalList().get(i).getQj();
						percent = Double.valueOf(dto.getIntervalList().get(i).getZk());
						break;
					}
				}
				
				CreateRebateReportCommand cmd = new CreateRebateReportCommand();
				cmd.setConsumeNum(total); // 消费额
				cmd.setDistributor(d); // 商户
				cmd.setInterval(interval); // 满足的折扣区间
				cmd.setPercent(percent); // 区间的折扣
				cmd.setProductInUse(piu); // 对应商品
				cmd.setRebateSet(set); // 执行的折扣规则
				// 计算返利数量
				BigDecimal bd = BigDecimal.valueOf(total).multiply(BigDecimal.valueOf(percent));
				cmd.setRebateNum(bd.longValue());

				createRebateReport(cmd);
				
				// 商户账户返利
				reserveInfoSPIServ.updateAccount(d.getId(), bd.longValue());
				
				// 生成商户余额变化明细
				CreateReserveRecordCommand rCmd = new CreateReserveRecordCommand();
//				rCmd.setAuthUserID(authUserID);// 返利不设该字段
//				rCmd.setCardNo(cardNo);// 返利不设该字段
				rCmd.setDistributorID(d.getId());
				rCmd.setIncrement(bd.longValue());
//				rCmd.setOrderCode(orderCode);// 返利不设该字段
				rCmd.setProdName("月末返利");
				rCmd.setStatus(CreateReserveRecordCommand.RECORD_STATUS_CHONGZHI_SUCC);
				rCmd.setTradeNo(UUIDGenerator.getUUID());
				rCmd.setType(CreateReserveRecordCommand.RECORD_TYPE_REBATE);
				reserveRecordSPIServ.create(rCmd);
				
				System.out.println("经销商{id="+d.getId()+" "+d.getName()+"}  "+fist+" 至 "+end+" 在商品 "+piu.getProduct().getProdName()+"的月消费里程 "+total+",返利比例 "+ percent);
			}else{
				// 商品对应无返利规则时  生成报表 不返利
				CreateRebateReportCommand cmd = new CreateRebateReportCommand();
				cmd.setConsumeNum(total); // 消费额
				cmd.setDistributor(d); // 商户
				cmd.setInterval(null); // 满足的折扣区间
				cmd.setPercent(null); // 区间的折扣
				cmd.setProductInUse(piu); // 对应商品
				cmd.setRebateSet(set); // 执行的折扣规则
				cmd.setRebateNum(null);
				createRebateReport(cmd);
				
				System.out.println("经销商{id="+d.getId()+" "+d.getName()+"}  "+fist+"至"+end+" 在商品 "+piu.getProduct().getProdName()+"的月消费里程 "+total+"  没有特定返利规则，所以没有返利");
			}
		}
	}

	
	
	
	
}
