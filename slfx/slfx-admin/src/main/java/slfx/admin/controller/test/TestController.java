//package slfx.admin.controller.test;
//
//import hg.common.page.Pagination;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import slfx.admin.controller.BaseController;
//import slfx.mp.spi.inter.AdminSupplierPolicyService;
//
//import com.alibaba.fastjson.JSON;
//
///**
// * 测试
// * @author liujz
// *
// */
//@Controller
//@RequestMapping(value = "/dubbo")
//public class TestController extends BaseController {
//
//	@Autowired
//	private AdminSupplierPolicyService adminSupplierPolicyService;
//	
//	@RequestMapping(value = "/test")
//	public  String querySpotsList(HttpServletRequest request, Model model){
//		
//		Pagination pagination = new Pagination();
//		pagination = adminSupplierPolicyService.queryPagination(pagination);
//		
//		System.out.println(JSON.toJSONString(pagination));
//		return "/viewspot/spots/spots_list.html";
//	}
//	
//
//}
