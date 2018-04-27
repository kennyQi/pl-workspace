//package service.test;
//
//import hg.common.model.HttpResponse;
//import hg.common.page.Pagination;
//import hg.common.util.HttpUtil;
//import hg.log.util.HgLogger;
//import hsl.domain.model.coupon.CouponActivity;
//import hsl.domain.model.jp.JPOrder;
//import hsl.pojo.command.CreateCompanyCommand;
//import hsl.pojo.command.CreateCouponCommand;
//import hsl.pojo.command.CreateDepartmentCommand;
//import hsl.pojo.command.CreateMemberCommand;
//import hsl.pojo.command.DeleteMemberCommand;
//import hsl.pojo.dto.company.CompanyDTO;
//import hsl.pojo.dto.company.DepartmentDTO;
//import hsl.pojo.dto.company.MemberDTO;
//import hsl.pojo.dto.coupon.CouponActivityDTO;
//import hsl.pojo.dto.jp.JPOrderDTO;
//import hsl.pojo.exception.CouponException;
//import hsl.pojo.qo.company.HslCompanyQO;
//import hsl.pojo.qo.company.HslDepartmentQO;
//import hsl.pojo.qo.company.HslMemberQO;
//import hsl.pojo.qo.company.HslTravelQO;
//import hsl.pojo.qo.coupon.HslCouponActivityEventQO;
//import hsl.pojo.qo.coupon.HslCouponActivityQO;
//import hsl.pojo.qo.coupon.HslCouponQO;
//import hsl.pojo.qo.jp.HslJPOrderQO;
//import hsl.spi.inter.Coupon.CouponActivityService;
//import hsl.spi.inter.Coupon.CouponService;
//import hsl.spi.inter.api.jp.JPService;
//import hsl.spi.inter.company.CompanyService;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class CompanyServiceTest {
//	@Resource
//	private CompanyService companyService;
//	
//	@Resource
//	private JPService jPService;
//	
//	@Resource
//	private CouponService couponService;
//	
//	@Resource
//	private CouponActivityService couponActivityService;
//	
//    @Test
//	public void addCompany(){
//    	//传递商城订单编号给商城
//    			String url = "http://192.168.10.52:8080/hsl-web/jp/getclient?orderNo=A928111140000000";
//    			HgLogger.getInstance().debug("luoyun", "支付平台通知商城地址：" + url);
//    			//通过商城的消息地址通知商城
//    			HttpResponse res = HttpUtil.reqForGet(url,null);
//    			HgLogger.getInstance().info("luoyun", "支付平台通知商城地址response状态码" + res.getRespoinsCode());
//    			if(res.getRespoinsCode() == 200){
//    				//设置订单通知状态为通知业务系统成功
//    				System.out.println("sds");
//    			}else{
//    				System.out.println("shibai");
//    			}
//	}
//	
////	@Test
//	public void testGetCompanys(){
//		HslCompanyQO hslCompanyQO = new HslCompanyQO();
//		hslCompanyQO.setUserId("1");
//		List<CompanyDTO> companyList = companyService.getCompanys(hslCompanyQO);
//		Assert.assertTrue(companyList.size()>0);
//	}
//	
//	//	@Test
//	public void testAddDepartment(){
//		CreateDepartmentCommand createDepartmentCommand = new CreateDepartmentCommand();
//		createDepartmentCommand.setCompanyId("cc189182406145f8b799052611ceaf13");
//		createDepartmentCommand.setDeptName("第一部门");
//		companyService.addDepartment(createDepartmentCommand);
//	}
//	
//	//	@Test
//	public void testGetDepartment(){
//		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
//		hslDepartmentQO.setCompanyId("cc189182406145f8b799052611ceaf13");
//		List<DepartmentDTO> departmentList = companyService.getDepartments(hslDepartmentQO);
//		Assert.assertTrue(departmentList.size()>0);
//	}
//	
////	//	@Test
//	public void testAddMember(){
//		CreateMemberCommand createMemberCommand = new CreateMemberCommand();
//		createMemberCommand.setDeptId("393e5b9c312443f78a3e6e0b697cebc2");
//		createMemberCommand.setName("第一个员工");
//		createMemberCommand.setCertificateID("320981199525622224");
//		createMemberCommand.setJob("员工");
//		createMemberCommand.setMobilePhone("19351268761");
//		companyService.addMember(createMemberCommand);
//	}
//
//	//	@Test
//	public void testGetMember(){
//		HslMemberQO hslMemberQO = new HslMemberQO();
//		hslMemberQO.setDepartmentId("393e5b9c312443f78a3e6e0b697cebc2");
//		List<MemberDTO> memberList = companyService.getMembers(hslMemberQO);
//		Assert.assertTrue(memberList.size()>0);
//	}
//	
//	//	@Test
//	public void testDeleteMember(){
//		DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand();
//		deleteMemberCommand.setUserId("17150c60ebb64092a06ae703f0cd2336");
//		companyService.delMember(deleteMemberCommand);
//	}
//	
////	@Test
//	public void testGetTravelInfo(){
//		HslTravelQO hslTravelQO = new HslTravelQO();
//	}
//	
//	//@Test
//	public void testJpOrderQuery(){
//		HslCouponQO hslCouponQO=new HslCouponQO();
//		hslCouponQO.setEventType(4);
//		System.out.println(couponService.getCoupon(hslCouponQO).getId());;
////		hslCouponQO.setCurrentValue(4);
////		hslCouponQO.setIsNoCanUsed(true);
////		Pagination pagination=new Pagination();
////		pagination.setCondition(hslCouponQO);
////		Integer pageNo=null;
////		Integer pageSize=null;
////		pageNo=pageNo==null?new Integer(1):pageNo;
////		pageSize=pageSize==null?new Integer(20):pageSize;
////		pagination.setPageNo(pageNo);
////		pagination.setPageSize(pageSize);
////		
////		pagination =couponService.queryPagination(pagination);
////		System.out.println("dsd"+pagination.getList().size());
//	}
//	//@Test
//	public void createCoupon(){
//	HslCouponActivityQO qo=new HslCouponActivityQO();
//	qo.setCurrentValue(CouponActivity.COUPONACTIVITY_STATUS_ACTIVE);
//	qo.setOrderbyPriority(true);
//	List<CouponActivityDTO> activityDTOs = couponActivityService.queryList(qo);
//	if(activityDTOs!=null&&activityDTOs.size()>0){
//		int maxPriority=activityDTOs.get(0).getIssueConditionInfo().getPriority();
//		for(CouponActivityDTO activityDTO : activityDTOs){
//			if(maxPriority==activityDTO.getIssueConditionInfo().getPriority()){
//				CreateCouponCommand command=new CreateCouponCommand();
//				command.setLoginName("liusong");
//				command.setMobile("18638236219");
//				command.setOrderId("");
//				command.setRealName("sdsds");
//				command.setDetail("sdsdsd");
//				command.setEmail("595035525@qq.com");
//				command.setUserId("2");
//				command.setSourceDetail("注册成功");
//				command.setCouponActivityId(activityDTO.getId());
//				try {
//					couponService.createCoupon(command);
//				} catch (CouponException e) {
//					e.printStackTrace();
//					HgLogger.getInstance().error("zhuxy", "卡券发放监听器：活动id为："+activityDTO.getId()+"的卡券发放失败");
//				}
//			}
//		}
//	}
//	}
//}
