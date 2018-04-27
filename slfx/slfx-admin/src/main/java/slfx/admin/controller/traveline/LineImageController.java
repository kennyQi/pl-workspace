package slfx.admin.controller.traveline;

import hg.common.util.DwzJsonResultUtil;
import hg.common.util.EntityConvertUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import slfx.admin.controller.BaseController;
import slfx.xl.domain.model.line.DayRoute;
import slfx.xl.domain.model.line.Line;
import slfx.xl.pojo.command.line.CreateLineImageCommand;
import slfx.xl.pojo.dto.line.LineDTO;
import slfx.xl.pojo.qo.LineImageQO;
import slfx.xl.pojo.qo.LineQO;
import slfx.xl.spi.inter.DayRouteService;
import slfx.xl.spi.inter.LineImageService;
import slfx.xl.spi.inter.LineService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：线路图片controller
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年4月22日下午3:07:27
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping("/lineImage")
public class LineImageController extends BaseController{

	@Autowired
	private LineImageService lineImageService;
	@Autowired
	private LineService lineService;
	@Autowired
	private DayRouteService dayRouteService;
	
	public static final String LINE_IMAGE = "/traveline/line/lineImage.html";
	/**
	 * 
	 * @方法功能说明：线路图片显示
	 * @修改者名字：yuqz
	 * @修改时间：2015年4月22日下午3:08:06
	 * @修改内容：
	 * @参数：@param lineQO
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/show")
	public String show(LineImageQO lineImageQO,Model model){
		HgLogger.getInstance().info("yuqz", "LineImageController->show->lineImageQO:" + JSON.toJSONString(lineImageQO));
		try {
			LineQO lineQO = new LineQO();
			lineQO.setId(lineImageQO.getLineId());
			LineDTO lineDTO = lineService.queryUnique(lineQO);
			Line line = EntityConvertUtils.convertDtoToEntity(lineDTO, Line.class);
			HgLogger.getInstance().info("yuqz", "LineImageController->show->line:" + JSON.toJSONString(line));
			List<DayRoute> dayRouteListSort = new ArrayList<DayRoute>();
			List<DayRoute> dayRouteList = line.getRouteInfo().getDayRouteList();
			for(int i = 1; i <= dayRouteList.size(); i++){
				for(int j = 0; j < dayRouteList.size(); j++){
					DayRoute dayRoute = new DayRoute();
					dayRoute = dayRouteList.get(j);
					if(null != dayRoute.getDays() && dayRoute.getDays() == i){
						dayRouteListSort.add(dayRoute);
					}
				}
			}
			line.getRouteInfo().setDayRouteList(dayRouteListSort);
			HgLogger.getInstance().info("yuqz", "LineImageController->show->sortLine:" + JSON.toJSONString(line));
			int routeCounts = 0;
			if(null != line.getRouteInfo() && null != line.getRouteInfo().getDayRouteList()){
				routeCounts = line.getRouteInfo().getDayRouteList().size();
			}
			HgLogger.getInstance().info("yuqz", "LineImageController->show->routeCounts:" + routeCounts + ",image_host:"
					+ SysProperties.getInstance().get("image_host"));
			model.addAttribute("line", line);
			model.addAttribute("routeCounts", routeCounts);
			model.addAttribute("image_host", SysProperties.getInstance().get("image_host"));
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz","LineImageController->show线路图片页面："+HgLogger.getStackTrace(e));
		}
		return LINE_IMAGE;
	}
	
	/**
	 * 
	 * @方法功能说明：修改线路图片
	 * @修改者名字：yuqz
	 * @修改时间：2015年4月27日上午8:53:46
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyLineImage")
	public String modifyLineImage(HttpServletRequest request, Model model,
			@ModelAttribute CreateLineImageCommand command) {
		HgLogger.getInstance().info("yuqz", "LineImageController->modifyLineImage->修改线路图片开始:" + command.getFdfsFileInfoJSON());
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		String navTabId = "lineList";

		if (command != null) {
			try {
				lineImageService.modifyLineImage(command);
			} catch (Exception e) {
				HgLogger.getInstance().error("yuqz", "LineImageController->modifyLineImage->修改线路图片失败"
						+ HgLogger.getStackTrace(e));
				e.printStackTrace();
				statusCode = DwzJsonResultUtil.STATUS_CODE_500;
				message = "保存失败:";
			}
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId);

	}
}

