

import java.util.Date;
import java.util.List;

import lxs.app.service.line.LineService;
import hg.common.page.Pagination;
import hg.common.util.UUIDGenerator;
import oracle.net.aso.l;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import slfx.api.base.ApiRequest;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.request.qo.xl.XLLineApiQO;
import slfx.api.v1.response.xl.XLQueryLineResponse;
import slfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import slfx.xl.pojo.dto.line.LineDTO;
import slfx.xl.pojo.qo.LineQO;
import slfx.xl.pojo.system.LineMessageConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class LineTest {
	@Autowired
	private LineService lineService;
	/*@Autowired
	private LineOrderLocalService lineOrderLocalService;
	@Autowired
	private HslLineService hslLineService;*/
	
	@Autowired
	private SlfxApiClient slfxApiClient;
	@Test
	public void queryLineOrder() throws Exception{
		LineDTO lineDTO=new LineDTO();
		lineDTO.setId("b1cd8d8781794518ba463c7c0172ae72");
		lineService.delLineCascadeEntity(lineDTO);
	}
}
