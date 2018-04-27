import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hg.demo.member.service.spi.impl.fx.DistributorSPIService;
import hg.demo.member.service.spi.impl.fx.DistributorUserSPIService;
import hg.demo.member.service.spi.impl.fx.ProductInUseSPIService;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.distributor.ModifyDistributorCommand;
import hg.fx.command.distributoruser.CreateDistributorUserCommand;
import hg.fx.command.distributoruser.ModifyDistributorUserCommand;
import hg.fx.command.distributoruser.RemoveDistributorUserCommand;
import hg.fx.command.productInUse.CreateProductInUseCommand;
import hg.fx.command.productInUse.ModifyProductInUseCommand;
import hg.fx.domain.CZFile;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.Product;
import hg.fx.domain.ProductInUse;
import hg.fx.spi.CzFileSPI;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.DistributorUserSPI;
import hg.fx.spi.qo.CZFileSQO;
import hg.fx.spi.qo.DistributorUserSQO;
import hg.fx.spi.qo.ProductInUseSQO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.container.page.Page;

/**
 * 
 * @author Caihuan
 * @date   2016年6月2日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class CZFileTest {

	@Autowired
	private CzFileSPI CzFileService;
	
	
	@Test
	public void testquery()
	{
		//CZFileSQO sqo = new CZFileSQO();
		//Pagination<CZFile> page = CzFileService.getCzFile(sqo);
		String status = "true";
		CZFileSQO  sqo =  new CZFileSQO();
		sqo.setFileName("CM20160711HGTECH");
		List<CZFile> list = CzFileService.getCzFile(sqo).getList();
		CZFile czFile;
		for(int i=0;i<list.size();i++){
			czFile = list.get(i);
			if("TOSEND".equals(czFile.getStatus())){
				if("true".equals(status)){
					czFile.setStatus(CZFile.SEND);
				}else{
					czFile.setStatus(CZFile.SENDFAIL);
				}
				czFile.setFeedbacktime(new Date());
				CzFileService.update(czFile);
			}
		}
	}
	
}
