package hgfx.web.controller.importHistory;

import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.ImportHistory;
import hg.fx.spi.ImportHistorySPI;
import hg.fx.spi.qo.ImportHistorySQO;
import hgfx.web.controller.sys.BaseController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： cangs
 * @创建时间： 2016年6月1日 下午5:04:23
 * @版本： V1.0
 */
@Controller
@RequestMapping(value = "importHistory")
public class ImportHistoryController extends BaseController{

	@Autowired
	private ImportHistorySPI importHistoryService;

	/**
	 * 
	 * @方法功能说明：历史记录列表展现
	 * @修改者名字：cangs
	 * @修改时间：2016年6月3日下午7:14:00
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param pageNum
	 * @参数：@param pageSize
	 * @参数：@param dstributorUserID
	 * @参数：@param startDate
	 * @参数：@param endDate
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/importHistoryList")
	public String toList(HttpServletRequest request,HttpServletResponse response,Model model,
	        @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
	        @RequestParam(value = "numPerPage" ,defaultValue = "10")Integer pageSize,
	        @RequestParam(value = "dstributorUserID",defaultValue = "")String dstributorUserID,
	        @RequestParam(value = "startDate",defaultValue = "")String startDate,
	        @RequestParam(value = "endDate",defaultValue = "")String endDate,
	        @RequestParam(value = "qo",defaultValue = "")String qo){
		ImportHistorySQO sqo = new ImportHistorySQO();
		/**
		 * 获取商户ID
		 */
		DistributorUser distributorUser = getSessionUserInfo(request.getSession());
		try{
			if(StringUtils.isBlank(startDate)&&StringUtils.isBlank(endDate)){
				SimpleDateFormat dateToStr = new SimpleDateFormat("yyyy-MM-dd");
				String date = dateToStr.format(new Date());
				SimpleDateFormat strToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sqo.setBeginImportDate(strToDate.parse(date+" 00:00:00"));
				sqo.setEndImportDate(strToDate.parse(date+" 23:59:59"));
				startDate=date;
				endDate=date;
			}
			if(StringUtils.isNotBlank(startDate)&&StringUtils.isNotBlank(endDate)){
				SimpleDateFormat strToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sqo.setBeginImportDate(strToDate.parse(startDate+" 00:00:00"));
				sqo.setEndImportDate(strToDate.parse(endDate+" 23:59:59"));
			}
			if(StringUtils.isNotBlank(dstributorUserID)){
				sqo.setDstributorUserId(dstributorUserID);
			}
			LimitQuery limitQuery = new LimitQuery();
			if(pageNum!=1&&!StringUtils.equals(qo, dstributorUserID+startDate+endDate)){
				pageNum=1;
			}
			limitQuery.setPageNo(pageNum);
			limitQuery.setPageSize(pageSize);
			sqo.setLimit(limitQuery);
			sqo.setDistributorID(distributorUser.getDistributor().getId());
			Pagination<ImportHistory> pagination = importHistoryService.queryPagination(sqo);
			ImportHistorySQO importHistorySQO = new ImportHistorySQO();
			/**
			 * 获取商户ID
			 */
			importHistorySQO.setDistributorID(distributorUser.getDistributor().getId());
			Map<String, String> map = importHistoryService.queryDstributorUser(importHistorySQO);
			List<String> dstributorUsers = new ArrayList<String>();
			for(String key:map.keySet()){
				dstributorUsers.add(key+";"+map.get(key));
			}
			model.addAttribute("pagination", pagination.getList());
			model.addAttribute("dstributorUsers", dstributorUsers);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("dstributorUserID", dstributorUserID);
			model.addAttribute("pageNum", pageNum);
			model.addAttribute("pageNum", pageNum);
			double totalPageNum = 1;
			if(pagination.getTotalCount()>pageSize){
				totalPageNum=Math.ceil(Double.parseDouble(Integer.toString(pagination.getTotalCount()))/Double.parseDouble(Integer.toString(pageSize)));
			}
			model.addAttribute("totalPageNum", totalPageNum);
			model.addAttribute("qo", dstributorUserID+startDate+endDate);
		}catch(Exception e){
			
		}
		return "importhistory/order-manage-history.html";
	}
	
	/**
	 * 
	 * @方法功能说明：异步获取上传人列表
	 * @修改者名字：cangs
	 * @修改时间：2016年6月3日下午5:39:37
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("queryDstributorUser")
	public String queryDstributorUser(HttpServletRequest request,HttpServletResponse response){
		ImportHistorySQO sqo = new ImportHistorySQO();
		/**
		 * 获取商户ID
		 */
		DistributorUser distributorUser = getSessionUserInfo(request.getSession());
		sqo.setDistributorID(distributorUser.getDistributor().getId());
		Map<String, String> map = importHistoryService.queryDstributorUser(sqo);
		List<String> dstributorUsers = new ArrayList<String>();
		for(String key:map.keySet()){
			dstributorUsers.add(key+";"+map.get(key));
		}
		return JSON.toJSONString(dstributorUsers);
			
	}
}
