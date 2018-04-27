package hg.dzpw.admin.controller.tourist;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.admin.controller.BaseController;
import hg.dzpw.app.pojo.qo.TouristQo;
import hg.dzpw.app.service.local.TouristLocalService;
import hg.dzpw.domain.model.tourist.Tourist;
import hg.log.util.HgLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @类功能说明：游客（用户）管理
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午4:47:48
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value="/tourist")
public class TouristController extends BaseController {
	
	@Resource
	private TouristLocalService touristService;
	
	public final static String PAGE_PATH_LIST = "/tourist/tourist_list.html";
	
	public final static String PAGE_PATH_VIEW = "/tourist/tourist_view.html";
	
	public final static String PAGE_PATH_ADD = "/tourist/tourist_add.html";
	
	public final static String PAGE_PATH_UPDATE = "/tourist/tourist_edit.html";
	
	/**
	 * 游客列表
	 * @param request
	 * @param response
	 * @param model
	 * @param touristQo
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
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
		pagination = touristService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("touristQo", touristQo);
		return PAGE_PATH_LIST;
	}
	/**
	 * 跳转到查看游客的页面
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/view/{id}")
	public String preview(HttpServletRequest request, HttpServletResponse response,
			Model model, @PathVariable String id){
		HgLogger.getInstance().info("zzx", "进入查看游客查询方法:id【" + id + "】");
		Tourist tourist = touristService.load(id);
		model.addAttribute("tourist", tourist);
		return PAGE_PATH_VIEW;
	}
	
	/**
	 * 添加游客
	 * @param tourist 游客实体信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public String add(@ModelAttribute Tourist tourist){
		try{
			tourist.setId(UUIDGenerator.getUUID());
			touristService.save(tourist);
		}catch(Exception ex){
			ex.printStackTrace();
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, "添加失败！");
		}
		
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "添加游客成功！",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,"tourist");
	}
	
	/**
	 * 返回添加游客页面
	 * @return
	 */
	@RequestMapping("/to_add")
	public String to_add(){
		return PAGE_PATH_ADD;
	}
	/**
	 * @描述 返回游客编辑页面
	 * @param request
	 * @param response
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/edit/{id}")
	public String edit(HttpServletRequest request, HttpServletResponse response,
			Model model, @PathVariable String id){
		Tourist tourist = touristService.load(id);
		model.addAttribute("tourist", tourist);
		return PAGE_PATH_UPDATE;
	}
	/**
	 * @描述 执行更新操作
	 * @param tourist
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public String update(@ModelAttribute Tourist tourist){
		try{
			touristService.update(tourist);
		}catch(Exception ex){
			DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, "更新失败！");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "更新成功！",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,"tourist");
	}
}
