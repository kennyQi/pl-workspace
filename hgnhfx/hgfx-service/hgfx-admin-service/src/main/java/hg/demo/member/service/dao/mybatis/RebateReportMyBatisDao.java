package hg.demo.member.service.dao.mybatis;

import java.util.HashMap;
import java.util.List;

import hg.fx.domain.MileOrder;

/**
 * 返利月报MYBATIS DAO
 * @date 2016-07-26
 * @author yangkang
 * */
public interface RebateReportMyBatisDao {
	
	
	public List<MileOrder> queryOrderForRebateReport(HashMap<String, Object> map);
	
		
}
