package hg.payment.app.controller.hjb;

import hg.payment.app.controller.BaseController;
import hg.payment.domain.common.util.hjb.DesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 
 *@类功能说明：汇金宝同步回调
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月20日下午3:21:53
 *
 */
@Controller
@RequestMapping(value = "/hjb")
public class HJBReturnController extends BaseController{
	
	@RequestMapping("/return")
	public String hjbReturn(HttpServletRequest request,HttpServletResponse response){
		return "/payorder/hjb_return.html"; 
	}
	
	

}
