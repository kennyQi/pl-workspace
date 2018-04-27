package hg.demo.member.service.spi.impl.fx;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.demo.member.service.dao.hibernate.fx.FixedPriceReportDAO;
import hg.demo.member.service.qo.hibernate.fx.FixedPriceReportQO;
import hg.framework.common.model.Pagination;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.report.CreateFixedPriceReportCommand;
import hg.fx.domain.fixedprice.FixedPriceReport;
import hg.fx.spi.FixedPriceReportSPI;
import hg.fx.spi.qo.FixedPriceReportSQO;

@Transactional
@Service("fixedPriceReportSPIService")
public class FixedPriceReportSPIService implements FixedPriceReportSPI {

	@Autowired
	private FixedPriceReportDAO fixedPriceReportDAO;
	
	@Override
	public Pagination<FixedPriceReport> queryPagination(FixedPriceReportSQO sqo) {
		FixedPriceReportQO qo = new FixedPriceReportQO();
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
		return fixedPriceReportDAO.queryPagination(qo);
	}

	@Override
	public FixedPriceReport createFixedPriceReport(
			CreateFixedPriceReportCommand cmd) {
		FixedPriceReport entity = new FixedPriceReport();
		entity.setChannelName(cmd.getChannelName());
		entity.setConsumeNum(cmd.getConsumeNum());
		entity.setCreateDate(new Date());
		entity.setCurrentPercent(cmd.getCurrentPercent());
		entity.setDistributorId(cmd.getDistributorId());
		entity.setDistributorName(cmd.getDistributorName());
		entity.setFactPercent(cmd.getFactPercent());
		if(cmd.getFixedPriceSet().getId()!=null)
			entity.setFixedPriceSet(cmd.getFixedPriceSet());
		entity.setId(UUIDGenerator.getUUID());
		entity.setProdId(cmd.getProdId());
		entity.setProdName(cmd.getProdName());
		entity.setTargetInterval(cmd.getTargetInterval());
		
		return fixedPriceReportDAO.save(entity);
	}

}
