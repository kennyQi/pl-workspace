package hsl.account;
import hg.common.page.Pagination;
import hsl.domain.model.user.account.GrantCodeRecord;
import hsl.pojo.command.account.BusinessPartnersCommand;
import hsl.pojo.command.account.GrantCodeRecordCommand;
import hsl.pojo.dto.account.GrantCodeRecordDTO;
import hsl.pojo.dto.account.PayCodeDTO;
import hsl.pojo.qo.account.GrantCodeRecordQO;
import hsl.spi.inter.account.GrantCodeRecordService;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.alibaba.fastjson.JSON;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class GrantCodeRecordTest {
	@Autowired
	private GrantCodeRecordService grantCodeRecordService;
	@Test
	public void creat(){//发送充值码
		try {
			GrantCodeRecordCommand grantCodeRecordCommand=new GrantCodeRecordCommand();
			grantCodeRecordCommand.setGranterName("赵文书");
			grantCodeRecordCommand.setGranterTel("18648697197");
			grantCodeRecordCommand.setPayCodeMoney(50.00);
			grantCodeRecordCommand.setPayCodeNum(500);
			BusinessPartnersCommand businessPartnersCommand=new BusinessPartnersCommand();
			businessPartnersCommand.setCommandId("93f97bd2b5bf48d1afef901a7405ed2c");
			businessPartnersCommand.setCompanyLinkName("余大壮");
			businessPartnersCommand.setCompanyName("汇购科技");
			businessPartnersCommand.setCompanyLinkTel("13111111111");
			grantCodeRecordCommand.setBusinessPartnersCommand(businessPartnersCommand);
			//PayCodeCommand payCodeCommand=new PayCodeCommand();
			//grantCodeRecordCommand.setPayCodes(payCodeCommand);
			List<PayCodeDTO> list=grantCodeRecordService.issueRechargeCode(grantCodeRecordCommand);
			System.out.println("22222");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*@Test
	public void updateStatus(){//修改状态
		try {
			GrantCodeRecordCommand grantCodeRecordCommand=new GrantCodeRecordCommand();	
			grantCodeRecordCommand.setId("d99662bd96d4438d925d6a00775e6bde");
			grantCodeRecordCommand.setStatus(GrantCodeRecord.GRANT_CODE_RECORD_CHECKED);
			String msg=grantCodeRecordService.examineRechargeCode(grantCodeRecordCommand);
			
			System.out.println(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void queryPagination(){//查询发放记录列表
		try {
			Pagination pagination=new Pagination();
			pagination.setPageNo(1);
			pagination.setPageSize(10);
			GrantCodeRecordQO grantCodeRecordQO=new GrantCodeRecordQO();
			grantCodeRecordQO.setCompanyId("93f97bd2b5bf48d1afef901a7405ed2c");
			
			//grantCodeRecordQO.setCompanyLinkName("姚三峰");
			//grantCodeRecordQO.setCompanyLinkTel("");
			//grantCodeRecordQO.setCompanyName("");
			pagination.setCondition(grantCodeRecordQO);
			pagination=grantCodeRecordService.queryPagination(pagination);
			System.out.println("queryPagination");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void queryList(){//查询合作伙伴下充值码列表
		try {
			GrantCodeRecordQO grantCodeRecordQO=new GrantCodeRecordQO();
			grantCodeRecordQO.setId("b6c5b4d0d2c043d4823cbf414fee7c70");
			List<GrantCodeRecordDTO> list=grantCodeRecordService.queryList(grantCodeRecordQO);
			System.out.println(JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
