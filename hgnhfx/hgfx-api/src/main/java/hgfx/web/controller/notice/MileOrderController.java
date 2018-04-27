package hgfx.web.controller.notice;

import hg.fx.domain.CZFile;
import hg.fx.spi.CzFileSPI;
import hg.fx.spi.MileOrderSPI;
import hg.fx.spi.qo.CZFileSQO;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 接受分销的订单已发送到南航的通知
 * @author xinglj
 *
 */
@Controller
@RequestMapping("/mileOrder")
public class MileOrderController   {
	@Autowired
	MileOrderSPI mileOrderService;
	@Autowired
	CzFileSPI czFileService;
	 
	final static String navTabRel = "mileOrder";
	@ResponseBody
	@RequestMapping("/noticeSend")
	public String noticeMileOrder(Model model, HttpServletRequest request,@RequestParam String id) {
		 mileOrderService.sentOrder(id);
		 return "1";
	}
	/**
	 * 修改南航文件数据包状态
	 * @author zqq
	 * @since hgfx-api
	 * @date 2016-7-11 下午2:40:01 
	 * @param model
	 * @param request
	 * @param fileName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateFile")
	public String updateFile(Model model, HttpServletRequest request,@RequestParam String fileName
			,@RequestParam String status) {
		CZFileSQO  sqo =  new CZFileSQO();
		sqo.setFileName(fileName);
		List<CZFile> list = czFileService.getCzFile(sqo).getList();
		CZFile czFile;
		for(int i=0;i<list.size();i++){
			czFile = list.get(i);
			if("TOSEND".equals(czFile.getStatus())){
				if("true".equals(status)){
					czFile.setStatus(CZFile.SEND);
				}else{
					czFile.setStatus(CZFile.SENDFAIL);
				}
				czFile.setFeedbacktime(new Date());
				czFileService.update(czFile);
			}
		}
		 return "1";
	}

}
