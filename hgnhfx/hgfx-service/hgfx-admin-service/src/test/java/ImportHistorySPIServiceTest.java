import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.fx.command.importHistory.CreateImportHistoryCommand;
import hg.fx.domain.ImportHistory;
import hg.fx.spi.ImportHistorySPI;
import hg.fx.spi.qo.ImportHistorySQO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;


/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 下午3:19:03
 * @版本： V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class ImportHistorySPIServiceTest {

	@Autowired
	private ImportHistorySPI importHistorySPI;
	
	//@Test
	public void testCreate(){
		CreateImportHistoryCommand command = new CreateImportHistoryCommand();
		command.setDstributorUserId("400cda3047f24d62b2bd1d3a033577e0");
		command.setFileName("测试"+1000+".xls");
		command.setFilePath("D://ceshi//测试"+1000+".xls");
		command.setImportDate(new Date());
		command.setNormalNum(10);
		command.setNormalMileages(10000l);
//		System.out.println(JSON.toJSONString(importHistorySPI.create(command)));;
		
	}
	
	@Test
	public void testqueryPagination() throws ParseException{
		ImportHistorySQO sqo = new ImportHistorySQO();
		String beginImportDate = "2016-05-31 16:06:11";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		LimitQuery limitQuery = new LimitQuery();
		limitQuery.setPageNo(1);
		limitQuery.setPageSize(30);
		
		sqo.setDstributorUserId("400cda3047f24d62b2bd1d3a033577e0");
		sqo.setBeginImportDate(sdf.parse(beginImportDate));
		sqo.setEndImportDate(new Date());
		sqo.setLimit(limitQuery);
		Pagination<ImportHistory> hPagination = importHistorySPI.queryPagination(sqo);
		List<ImportHistory> list = hPagination.getList();
		for(ImportHistory history : list){
//			System.out.println(JSON.toJSONString(history));
		}
//		System.out.println(list.size());
	}
	
	@Test
	public void testQueryDstributorUser(){
		ImportHistorySQO sqo = new ImportHistorySQO();
		sqo.setDistributorID("8105cc6aad1a4d3aab8ad356d3cef0f7");
		HashMap<String, String> hashMap = (HashMap<String, String>) importHistorySPI.queryDstributorUser(sqo);
		for(String key:hashMap.keySet()){
//			System.out.println(hashMap.get(key));
		}
	}
}
