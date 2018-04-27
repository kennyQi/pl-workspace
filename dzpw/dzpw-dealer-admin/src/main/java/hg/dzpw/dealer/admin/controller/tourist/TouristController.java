package hg.dzpw.dealer.admin.controller.tourist;

import hg.common.page.Pagination;
import hg.dzpw.app.pojo.qo.TouristQo;
import hg.dzpw.app.service.api.alipay.AlipaySignProtocolService;
import hg.dzpw.app.service.local.TouristLocalService;
import hg.log.util.HgLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @类功能说明：游客管理控制器
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015-12-4下午2:29:45
 * @版本：
 */
@Controller
@RequestMapping(value="/tourist")
public class TouristController {
	
	@Autowired
	private TouristLocalService touristLocalService;
	
	@Autowired
	private AlipaySignProtocolService alipayService;
	
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request, Model model,
						@RequestParam(value="pageNum", required = false)Integer pageNum,
						@RequestParam(value="numPerPage", required = false)Integer pageSize,
					    @ModelAttribute TouristQo touristQo) {

		HgLogger.getInstance().info("zzx", "进入游客分页查询方法:touristQo【" + touristQo + "】");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String firstBuyDateStartStr=touristQo.getFirstBuyDateStartStr();
		String firstBuyDateEndStr=touristQo.getFirstBuyDateEndStr();
		
		Pagination pagination = new Pagination();
		
		if(pageNum!=null){
			pagination.setPageNo(pageNum);
		}else{
			pagination.setPageNo(1);
		}
		
		if(pageSize!=null){
			pagination.setPageSize(pageSize);
		}else{
			pagination.setPageSize(20);
		}
		
		touristQo.setFirstBuyDateSort(-1);
		
		if(StringUtils.isNotBlank(firstBuyDateStartStr)){
			try {
				touristQo.setFirstBuyDateStart(sdf.parse(firstBuyDateStartStr+" 00:00:00"));
			} catch (ParseException e) {
				touristQo.setFirstBuyDateStart(null);
			}
		}
		
		if(StringUtils.isNotBlank(firstBuyDateEndStr)){
			try {
				touristQo.setFirstBuyDateEnd(sdf.parse(firstBuyDateEndStr+" 23:59:59"));
			} catch (ParseException e) {
				touristQo.setFirstBuyDateEnd(null);
			}
		}
		
		
		pagination.setCondition(touristQo);
		pagination = touristLocalService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("touristQo", touristQo);
		return "/tourist/list.html";
	}

}
