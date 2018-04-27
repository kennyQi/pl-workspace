package hsl.admin.controller.ad;

import hg.common.page.Pagination;
import hsl.pojo.qo.coupon.HslCouponActivityQO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *               
 * @类功能说明：特价活动控制器
 * @类修改者：
 * @修改日期：2014年12月12日上午11:17:23
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhuxy
 * @创建时间：2014年12月12日上午11:17:23
 * *******************作废**********************
 *
 */
@Controller
@Deprecated
@RequestMapping("/discount")
public class DiscountAD {
	
	/**
	 * 展现表单
	 */
	@RequestMapping("/form")
	public String showForm(HslCouponActivityQO hslCouponActivityQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			Model model){
		//添加分页查询条件
		Pagination pagination=new Pagination();
		pagination.setCondition(hslCouponActivityQO);
		pageNo=pageNo==null?new Integer(1):pageNo;
		pageSize=pageSize==null?new Integer(20):pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		
		return "Discount/add.html";
	}
	
	/**
	 * 添加
	 */
	@RequestMapping("/add")
	public void addAD(){
	}
	
	/**
	 * 当前列表
	 */
	@RequestMapping("/list")
	public String currentList(){
		return "Discount/current_list.html";
	}
	
	/**
	 * 历史列表
	 */
	@RequestMapping("/hlist")
	public String historyList(){
		return "Discount/history_list.html";
	}
}
