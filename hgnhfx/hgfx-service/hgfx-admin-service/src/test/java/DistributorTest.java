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
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.Product;
import hg.fx.domain.ProductInUse;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.DistributorUserSPI;
import hg.fx.spi.qo.DistributorUserSQO;
import hg.fx.spi.qo.ProductInUseSQO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author Caihuan
 * @date   2016年6月2日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class DistributorTest {

	@Autowired
	private DistributorUserSPIService distributorUserSPIService;
	
	@Autowired
	private DistributorSPIService distributorSPIService;
	
	@Autowired
	private ProductInUseSPIService productInUseSPIService;
	
//	@Test
	public void create()
	{
		CreateDistributorUserCommand command = new CreateDistributorUserCommand();
		command.setType(1); //主帐号
		command.setDistributorId("8105cc6aad1a4d3aab8ad356d3cef0f7");
		command.setStatus(1);
		command.setCheckStatus(2);
		command.setAccount("test1");
		command.setCompanyName("测试");
		command.setPhone("12345678911");
		command.setPassword("123456");
		command.setName("商户姓名");
		command.setWebSite("www.baidu.com");
//		distributorUserSPIService.create(command);
	}
	
//	@Test
	public void queryPagination() throws ParseException
	{
		DistributorUserSQO sqo = new DistributorUserSQO();
//		sqo.setAccount("asd");
//		sqo.setName("商户姓名");
//		sqo.setStatus(1);
		sqo.setType(1); //主帐号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = sdf.parse("2016-06-02 11:30:35");
		Date endDate = sdf.parse("2016-06-02 11:34:42");
		sqo.setBeginDate(beginDate);
		sqo.setEndDate(endDate);
		LimitQuery limitQuery = new LimitQuery();
		limitQuery.setPageNo(1);
		limitQuery.setPageSize(20);
		sqo.setLimit(limitQuery);
		Pagination<DistributorUser> pagination = distributorUserSPIService.queryPagination(sqo);
//		System.out.println(pagination.getTotalCount());
//		System.out.println(pagination.getList());
	}
	
//	@Test
	public void queryUnique()
	{
		DistributorUserSQO sqo = new DistributorUserSQO();
//		sqo.setAccount("asd");
//		sqo.setName("商户姓名");
		sqo.setId("1");
		DistributorUser d = distributorUserSPIService.queryUnique(sqo);
//		System.out.println(d.getLoginName()+"   "+d.getName()+"   "+d.getDistributor().getPhone());
	}
	
	//修改,手机号
//	@Test
	public void modifySignKeyorPhone()
	{
		DistributorUserSQO sqo = new DistributorUserSQO();
		sqo.setId("3fedc16ea0a5488abdc5dc4f65503362");
		DistributorUser d = distributorUserSPIService.queryUnique(sqo);
		
//		System.out.println(d.getDistributor().getId());
		ModifyDistributorCommand command = new ModifyDistributorCommand();
		command.setId(d.getDistributor().getId());
		command.setPhone("1111124544"); //改手机号操作
		distributorSPIService.modifyDistributor(command);
	}
	
	//修改密码
//	@Test
	public void modifyPassword()
	{
		ModifyDistributorUserCommand command = new ModifyDistributorUserCommand();
		command.setId("3fedc16ea0a5488abdc5dc4f65503362");
		command.setPassword("123123");
		distributorUserSPIService.modify(command);
	}
	
	//逻辑删除
//	@Test
	public void delete()
	{
		RemoveDistributorUserCommand command = new RemoveDistributorUserCommand();
		command.setId("3fedc16ea0a5488abdc5dc4f65503362");
		distributorUserSPIService.delete(command);
	}
	
	
	//测试商户批量审核通过或者拒绝
//	@Test
	public void batchEnable()
	{
		String [] ids = {"8105cc6aad1a4d3aab8ad356d3cef0f7","test"};
		Boolean flag = false; //批量通过 false：批量拒绝
		try {
			List<Distributor> list = distributorSPIService.batchEnableDisable(ids, flag);
//			System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 商户使用商品列表
	 * @author Caihuan
	 * @date   2016年6月2日
	 */
	@Test
	public void productInUse()
	{
		ProductInUseSQO sqo = new ProductInUseSQO();
		sqo.setDistributorId("test");
		sqo.setStatus(ProductInUse.STATUS_OF_DISABLE);
		List<ProductInUse> list = productInUseSPIService.queryList(sqo);
//		System.out.println(list.size());
		for(ProductInUse bean:list)
		{
//			System.out.println(bean.getPhone()+"  商户："+bean.getDistributor().getName()+" 商品："+bean.getProduct().getProdName());
		}
	}
	
	/**
	 * 修改商品使用状态
	 * @author Caihuan
	 * @date   2016年6月2日
	 */
//	@Test
	public void modifyproductInUse()
	{
		ModifyProductInUseCommand command = new ModifyProductInUseCommand();
		command.setProductInUseId("1");
		command.setStatus(ProductInUse.STATUS_OF_DISABLE);
		productInUseSPIService.changeStatus(command);
	}
	
	/**
	 * 未添加商品列表
	 * @author Caihuan
	 * @date   2016年6月3日
	 */
//	@Test
	public void productNotUse()
	{
		List<Product> list = productInUseSPIService.productNotUseList("8105cc6aad1a4d3aab8ad356d3cef0f7");
		for(Product p:list)
		{
//			System.out.println(p.getId()+"  名字："+p.getProdName()+"  编码:"+p.getProdCode());
		}
	}
	/**
	 * 添加使用商品
	 * @author Caihuan
	 * @date   2016年6月3日
	 */
//	@Test
	public void addproductInUse()
	{
		CreateProductInUseCommand command = new CreateProductInUseCommand();
		command.setId(UUIDGenerator.getUUID());
		command.setAgreementPath("/sdasd/asd/");
		command.setDistributorId("test");
		command.setUseDate(new Date());
		command.setTrialDate(new Date());
		command.setStatus(ProductInUse.STATUS_OF_IN_USE);
		command.setProdId("a5312848c6f941f0ae9249b944335e10");
		command.setPhone("18060606061");
		command.setQq("10086");
		try {
			productInUseSPIService.addProductInUse(command);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
