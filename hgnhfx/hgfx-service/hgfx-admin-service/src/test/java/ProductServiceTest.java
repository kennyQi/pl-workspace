import hg.demo.member.service.spi.impl.fx.ProductSPIService;
import hg.framework.common.model.LimitQuery;
import hg.fx.command.product.CreateProductCommand;
import hg.fx.command.product.DeleteProductByIDCommand;
import hg.fx.command.product.ModifyProductCommand;
import hg.fx.spi.qo.ProductSQO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class ProductServiceTest {

	@Autowired
	private ProductSPIService productSPIService;
	
	@Test
	public void create(){
//		System.out.println("CREATE_start");
		CreateProductCommand command = new CreateProductCommand();
		command.setAgreementPath("setAgreementPath");
		command.setChannelID("9d211334b4b148edb9a3eca20aaaac09");
		command.setDocumentPath("setDocumentPath");
		command.setFromPlatform("setFromPlatform");
		command.setProdCode("setProdCode");
		command.setProdName("setProdName");
		command.setType(CreateProductCommand.PRODUCT_TYPE_DOCUMENT);
//		System.out.println(JSON.toJSONString(productSPIService.create(command)));
//		System.out.println("CREATE_end");
	}
	
	@Test
	public void modify(){
//		System.out.println("MODIFY_start");
		ModifyProductCommand command = new ModifyProductCommand();
		command.setProductID("fd82adee764642bc8f552ee565187b59");
		command.setAgreementPath("haha_setAgreementPath");
		command.setChannelID("haha_9d211334b4b148edb9a3eca20aaaac09");
		command.setDocumentPath("haha_setDocumentPath");
		command.setFromPlatform("haha_setFromPlatform");
		command.setProdCode("haha_setProdCode");
		command.setProdName("haha_setProdName");
		command.setType(CreateProductCommand.PRODUCT_TYPE_DOCUMENT);
//		System.out.println(JSON.toJSONString(productSPIService.modify(command)));
//		System.out.println("MODIFY_end");
	}
	
	@Test
	public void delete(){
//		System.out.println("DELETE_start");
		DeleteProductByIDCommand command = new DeleteProductByIDCommand();
		command.setProductID("337cc75220a24d81b0f53c996b8e7854");
//		System.out.println(JSON.toJSONString(productSPIService.deleteByID(command)));
//		System.out.println("DELETE_end");
	}
	
	@Test
	public void query(){
//		System.out.println("QUERY_end");
		ProductSQO productSQO = new ProductSQO();
		productSQO.setProductID("98b48bb8203041e4942f6ca3c8b72a01");
//		System.out.println(JSON.toJSONString(productSPIService.queryProductByID(productSQO)));
//		System.out.println("QUERY_end");
	}
	
	@Test
	public void queryList(){
//		System.out.println("LIST_end");
		ProductSQO productSQO = new ProductSQO();
		productSQO.setChannelID("9d211334b4b148edb9a3eca20aaaac09");
		productSQO.setProdName("haha");
		productSQO.setProdCode("_");
//		System.out.println(JSON.toJSONString(productSPIService.queryProductList(productSQO)));
//		System.out.println("LIST_end");
	}
	
	@Test
	public void queryPagination(){
//		System.out.println("PAGINATION_end");
		ProductSQO productSQO = new ProductSQO();
		LimitQuery limitQuery = new LimitQuery();
		limitQuery.setByPageNo(true);
		limitQuery.setPageNo(2);
		limitQuery.setPageSize(1);
		productSQO.setLimit(limitQuery);
		productSQO.setChannelID("9d211334b4b148edb9a3eca20aaaac09");
//		System.out.println(JSON.toJSONString(productSPIService.queryProductPagination(productSQO)));
//		System.out.println("PAGINATION_end");
	}
}
