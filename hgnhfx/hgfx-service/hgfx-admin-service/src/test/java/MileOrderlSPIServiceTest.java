import java.util.Arrays;
import java.util.Date;

import hg.framework.common.model.Pagination;
import hg.framework.common.util.DateUtil;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.distributor.CreateDistributorCommand;
import hg.fx.command.distributoruser.CreateDistributorUserCommand;
import hg.fx.command.mileOrder.CheckMileOrderCommand;
import hg.fx.command.mileOrder.ConfirmMileOrderCommand;
import hg.fx.command.mileOrder.CreateMileOrderCommand;
import hg.fx.command.mileOrder.ImportMileOrderCommand;
import hg.fx.command.productInUse.CreateProductInUseCommand;
import hg.fx.command.reserveInfo.ModifyReserveInfoCommand;
import hg.fx.command.reserveRecord.CreateChargeReserveRecordCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.MileOrder;
import hg.fx.domain.Product;
import hg.fx.domain.ProductInUse;
import hg.fx.spi.CzFileSPI;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.DistributorUserSPI;
import hg.fx.spi.MileOrderSPI;
import hg.fx.spi.ProductInUseSPI;
import hg.fx.spi.ProductSPI;
import hg.fx.spi.ReserveInfoSPI;
import hg.fx.spi.qo.DistributorSQO;
import hg.fx.spi.qo.DistributorUserSQO;
import hg.fx.spi.qo.MileOrderSQO;
import hg.fx.spi.qo.ProductSQO;
import hg.hjf.nh.NHFile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author zqq
 * @date 2016-6-2上午10:15:54
 * @since
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class MileOrderlSPIServiceTest {

	private static final String authUserid = "4ab91281a1fd4ebaae2c2adc8616ac8b";
	@Autowired
	private MileOrderSPI mileOrderSPIService;
	@Autowired
	ReserveInfoSPI reserveInfoSPI;
	@Autowired
	CzFileSPI  czFileSPI;	
	@Autowired
	ProductSPI productSPI;
	@Autowired
	DistributorSPI distributorSPI;
	@Autowired
	ProductInUseSPI productInUseSPI;
	@Autowired
	DistributorUserSPI distributorUserSPI;
	
	@Test
	public void testQueryPagination() {
		MileOrderSQO sqo = new MileOrderSQO();
		Pagination<MileOrder> p = new Pagination<>();
		p = mileOrderSPIService.queryPagination(sqo);
//		System.out.println(p.getList().size());
		for(int i= 0;i<p.getList().size();i++){
//			System.out.println(p.getList().get(i).getId()+"**"+p.getList().get(i).getProduct().getId());
		}
	}

	/**
	 * 测试导入订单
	 */
	@Test
	public void testImport(){
		ImportMileOrderCommand imp = new ImportMileOrderCommand();
		imp.getList().add(newOrder("111111111", "", 100));
		imp.getList().add(newOrder("2222222", "200ren", 200));		
		imp.getList().add(newOrder("415305153869", "这个是汉字邢立军十这个是", 300));		

		imp = mileOrderSPIService.importBatch(imp);
//		System.out.println("expect 1,and "+ imp.getOkRows());
		for(CreateMileOrderCommand c:imp.getList()){
//			System.out.println( c.getErrorTip()==null?"ok":c.getErrorTip());
		}
	}
	/**
	 * 批量提交
	 */
	@Test
	public void testSubmitBatch(){
		//testCharge();
		
		ImportMileOrderCommand imp = new ImportMileOrderCommand();
		imp.getList().add(newOrder("415305153869", "100ren615", 100));
		imp.getList().add(newOrder("415305153869", "200ren615", 200));		
		imp.getList().add(newOrder("415305153869", "300ren615", 300));		
		final String distributorId = "43c45f5ee7974d83b80f08192aafccd3";
		imp.setDistributorId(distributorId);
		
		 mileOrderSPIService.submitBatch(imp);
		 MileOrderSQO qo = new MileOrderSQO();
		 qo.setDistributorId(distributorId);
		 for(MileOrder o:mileOrderSPIService.queryList(qo)){
//			 System.out.println(o );
		 }
	}
	/**
	 * 冻结完成
	 */
	@Test
	public void testFreezeFinish(){
		MileOrderSQO qo = new MileOrderSQO();
		 qo.setDistributorId("93a5b4e67ebe4f97a33f786759f6856b");
		 qo.setStatus(MileOrder.STATUS_CHECK_PASS);
		 for(MileOrder o:mileOrderSPIService.queryList(qo)){
//			 System.out.println(o );
			 reserveInfoSPI.finishFreeze(o);
		 }
	}
	
	@Test
	public void testCharge() {
		//充预付金
		CreateChargeReserveRecordCommand cr =new CreateChargeReserveRecordCommand();
		cr.setAuthUserID(authUserid);
		cr.setDistributorID("test");
		cr.setIncrement(600L);
		cr.setType(CreateChargeReserveRecordCommand.RECORD_TYPE_RECHARGE);
		reserveInfoSPI.onlineCharge(cr);
	}
	/**
	 * 审核
	 * @throws Exception
	 */
	@Test
	public void testCheckOrder() throws Exception{
		
		CheckMileOrderCommand cmd=new CheckMileOrderCommand();
		cmd.setIds(Arrays.asList(new String[]{"06033d68f7c041a3acb2f7f9334faab6"}));
		cmd.setCheckPersonId(authUserid);
		mileOrderSPIService.batchCheck(cmd,true);
	}
	
	/**
	 * 人工确认
	 * @throws Exception
	 */
	@Test
	public void testConfirmOrder() throws Exception{
		
		ConfirmMileOrderCommand cmd=new ConfirmMileOrderCommand();
		cmd.setIds(Arrays.asList(new String[]{"38b0c5b9ca784352937cebf926fe0c5f","f770675dca9b48feac58e678e16faaf1"}));
		cmd.setConfirmPerson("小王");;
		mileOrderSPIService.batchConfirm(cmd,true);
	}
	/**
	 * 人工确认拒绝
	 * @throws Exception
	 */
	@Test
	public void testNoConfirmOrder() throws Exception{
		
		ConfirmMileOrderCommand cmd=new ConfirmMileOrderCommand();
		cmd.setIds(Arrays.asList(new String[]{"63f2c16491e74eeda55c353c75e0296d","f770675dca9b48feac58e678e16faaf1"}));
		cmd.setConfirmPerson("小王");;
		mileOrderSPIService.batchConfirm(cmd,false);
	}
	/**
	 * 生成南航文件
	 * @throws Exception
	 */
	@Test
	public void testGenNHFile() throws Exception{
		
		 czFileSPI.genFile("d:/fenxiao", null, "date");
	}
	
	/**
	 * 生成100个商户，每个商户1万卡号，每卡3笔的订单。供300万
	 * @throws Exception 
	 */
	@Test
	public   void genBigData() throws Exception{
		
		//不检查订单风险
		mileOrderSPIService.setCheckAbnormal(false);
		
		for (int disIndex = 0; disIndex < 100; disIndex++) {

			//造个商户
			Distributor dis = null;
			try {
				CreateDistributorCommand discmd = new CreateDistributorCommand();
				discmd.setCheckStatus(Distributor.CHECKSTATUS_PASS);
				discmd.setStatus(Distributor.STATUS_OF_IN_USE);
				discmd.setCompanyName("分销商" + disIndex);
				discmd.setCode("CODE" + disIndex);
				discmd.setFirstLetter("C");
				discmd.setLinkMan("linkman");
				discmd.setPhone("153051" + String.format("%05d", disIndex));
				discmd.setSignKey("signKey");
				discmd.setWebSite("http://51hjf.com");
				if(distributorSPI.checkExistPhone(discmd.getPhone())){
					DistributorSQO dqo=new DistributorSQO();
					dqo.setPhone(discmd.getPhone());
					dis = distributorSPI.queryUnique(dqo);
				}else 
					dis = distributorSPI.createDistributor(discmd);
				
				//商户登录用户
				CreateDistributorUserCommand disUserc = new CreateDistributorUserCommand();
				disUserc.setAccount(dis.getCode()+"acc");
				disUserc.setCheckStatus(1);
				disUserc.setCompanyName("compay");
				disUserc.setDistributorId(dis.getId());
				disUserc.setName(dis.getCode()+"name");
				disUserc.setStatus(1);
				disUserc.setType(DistributorUser.DSTRIBUTOR_USER_TYPE_MAIN);
				DistributorUserSQO duq=new DistributorUserSQO();
				duq.setAccount(disUserc.getAccount());
				duq.setEqAccount(true);
				final DistributorUser queryUnique = distributorUserSPI.queryUnique(duq);
				if (queryUnique==null)
					distributorUserSPI.create(disUserc);
				
					//可欠费金额
				ModifyReserveInfoCommand reserverCmd = new ModifyReserveInfoCommand();
				reserverCmd.setId(dis.getReserveInfo().getId());
				reserverCmd.setArrearsAmount(100000000);
				reserveInfoSPI.modifyArrearsAmount(reserverCmd);
					//使用商品
				ProductSQO sqo=new ProductSQO();
				sqo.setProdCode("setProdCode");
				Product product = productSPI.queryUnique(sqo);
				CreateProductInUseCommand usecommand=new CreateProductInUseCommand();
				usecommand.setDistributorId(dis.getId());
				usecommand.setProdId(product.getId());
				usecommand.setId(UUIDGenerator.getUUID());
				usecommand.setAgreementPath("/a.jpg");
				usecommand.setPhone(dis.getPhone());
				usecommand.setQq("qq");
				usecommand.setStatus(ProductInUse.STATUS_OF_IN_USE);
				ProductInUse pu=productInUseSPI.addProductInUse(usecommand);
				productInUseSPI.checkInUse(pu.getDistributor().getId(),pu.getProduct().getId());
			} catch (Exception e) {
//				System.out.println("error when 造个商户");
				e.printStackTrace();
			}
			
			for(int cardIndex=0;cardIndex < 0; cardIndex++){
				// 造个卡
				String card=NHFile.genCardNo(disIndex + cardIndex);
				// 每卡三笔订单
				ImportMileOrderCommand imp = new ImportMileOrderCommand();
				imp.getList().add(newOrder(card, "神秘人", 100));
				imp.getList().add(newOrder(card, "神秘人", 100));
				imp.getList().add(newOrder(card, "神秘人", 100));
				imp.setDistributorId(dis.getId());
				mileOrderSPIService.submitBatch(imp);
				if( (cardIndex+1) % 1000 ==0 ){
					
				}
//				System.out.println(DateUtil.formatDateTime4(new Date()) +" "+dis.getName() +" submit order "+cardIndex +" ");
			}
		}
	}
	
	private CreateMileOrderCommand newOrder(String card, String name, int miles) {
		CreateMileOrderCommand cr = new CreateMileOrderCommand();
		cr.setCsairCard(card);
		cr.setCsairName(name);
		cr.setNum(miles);
		ProductSQO sqo = new ProductSQO();
		sqo.setProdCode("setProdCode");
		Product product = productSPI.queryUnique(sqo);
		cr.setProduct(product);
		return cr;
	}
	
	
}
