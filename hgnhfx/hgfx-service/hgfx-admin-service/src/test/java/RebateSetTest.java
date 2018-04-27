import hg.fx.command.rebate.CreateRebateSetCommand;
import hg.fx.domain.rebate.RebateSet;
import hg.fx.spi.RebateSetSPI;
import hg.fx.spi.qo.RebateSetSQO;
import hg.fx.util.DateUtil;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class RebateSetTest {

	@Autowired
	private RebateSetSPI rebateSetSPIService;
	@Test
	public void create(){
		CreateRebateSetCommand cmd = new CreateRebateSetCommand();
		//创建一个默认设置
		cmd.setProductId("337cc75220a24d81b0f53c996b8e7854");
		cmd.setDistributorId("73e9171ce9a9454ebe9ef3202272907c");
		cmd.setDistributorUserId("508a14d8fed544559c4584cea8afb6cf");
		cmd.setApplyDate(new Date());
		cmd.setCheckStatus(RebateSet.CHECK_STATUS_PASS);
		cmd.setImplementDate(DateUtil.parseDateTime1(DateUtil.forDateFirst()+"00:00:00"));
		cmd.setIntervalStr(getdefult());
		cmd.setIsImplement(true);
		
		rebateSetSPIService.createDefaultRebateSet(cmd);
	}
	@Test
	public void create1(){
		CreateRebateSetCommand cmd = new CreateRebateSetCommand();
		//创建一个待审核
		cmd.setProductId("337cc75220a24d81b0f53c996b8e7854");
		cmd.setDistributorId("f982ec3f5ecd448f9d70a6eca62527da");
		cmd.setDistributorUserId("628ba807a03842548c2941a9740b554c");
		cmd.setCheckStatus(RebateSet.CHECK_STATUS_WAITTING);
		cmd.setRunningSetId("709c502d33354aa89abad3614d06631f");
		cmd.setApplyDate(new Date());
		//设置申请人
		
		cmd.setIntervalStr(getdefult());
		
		rebateSetSPIService.createApplyRebateSet(cmd);
	}
	
	private String getdefult(){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("100", "0.95");
		map.put("1000", "0.9");
		map.put("10000", "0.8");
		map.put("100000", "0.85");
		return JSONObject.toJSONString(map);
	}
	@Test
	public void queryPaging(){
		RebateSetSQO sqo = new RebateSetSQO();
		//Pagination<RebateSet> result = rebateSetSPIService.queryAduitPagination(sqo);
		//List<RebateSet> list = rebateSetSPIService.queryCurrMonthSet(new RebateSetSQO()).getList();
		List<RebateSet> list2 = rebateSetSPIService.queryNextMonthSet(new RebateSetSQO()).getList();
		//System.out.println(result.getList().size());
		System.out.println(list2);
	}
}
