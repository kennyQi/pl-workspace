package pl.admin.controller.indexlunbo;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.SysProperties;
import hg.service.ad.domain.model.ad.Ad;
import hg.service.ad.pojo.qo.ad.AdQO;
import hg.service.image.pojo.dto.ImageDTO;
import hg.service.image.pojo.qo.AlbumQo;
import hg.service.image.pojo.qo.ImageQo;
import hg.service.image.pojo.qo.ImageUseTypeQo;
import hg.service.image.spi.inter.ImageService_1;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.admin.controller.BaseController;
import pl.app.service.AdServiceImpl;
import pl.app.service.IndexLunboServiceImpl;
import pl.cms.domain.entity.lunbo.IndexLunbo;
import pl.cms.pojo.command.lunbo.CreateIndexLunboCommand;
import pl.cms.pojo.command.lunbo.HideIndexLunboCommand;
import pl.cms.pojo.command.lunbo.ModifyIndexLunboImageCommand;
import pl.cms.pojo.command.lunbo.ModifyIndexLunboTitleCommand;
import pl.cms.pojo.command.lunbo.ModifyIndexLunboUrlCommand;
import pl.cms.pojo.command.lunbo.ModifyLunboCommand;
import pl.cms.pojo.command.lunbo.ShowIndexLunboCommand;
import pl.cms.pojo.qo.IndexLunboQO;
/**
 * @类功能说明：首页轮播Controller
 * @类修改者：
 * @修改日期：2015年3月18日上午9:20:18
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年3月18日上午9:20:18
 */
@Controller
@RequestMapping("/indexlunbo")
public class IndexLunboController extends BaseController {

	@Autowired
	private IndexLunboServiceImpl indexLunboServiceImpl;
	@Autowired
	private AdServiceImpl adServiceImpl;
	@Autowired
	private ImageService_1 imageService_1;
	/**
	 * 
	 * @方法功能说明：首页轮播列表
	 * @修改者名字：yuxx
	 * @修改时间：2015年3月16日下午2:25:45
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model,
			@ModelAttribute IndexLunboQO qo) {
		
		model.addAttribute("param", qo);
		qo.setOrderByCreateDate(-1);
		Pagination pagination = new Pagination();
		pagination.setPageNo(qo.getPageNo());
		pagination.setPageSize(qo.getPageSize());
		pagination.setCondition(qo);
		pagination = indexLunboServiceImpl.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		String fileUploadPath = SysProperties.getInstance().get("fileUploadPath");
		model.addAttribute("fileUploadPath", fileUploadPath);
		Boolean s=new Boolean(true);
		model.addAttribute("s", s);
//		model.addAttribute("param", qo);
//		List<IndexLunbo> list = indexLunboServiceImpl.queryList(qo);
//		model.addAttribute("indexLunboList", list);

		return "/indexlunbo/list.html";
	}
	
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model,
			@ModelAttribute IndexLunboQO qo) {
		return "/indexlunbo/add.html";
	}
	
	@ResponseBody
	@RequestMapping(value = "/create")
	public String create(HttpServletRequest request, Model model,
			@ModelAttribute CreateIndexLunboCommand command) {

		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		String navTabId = "indexLunboList";

		if (command != null) {
			try {
				indexLunboServiceImpl.createIndexLunbo(command);
			} catch (Exception e) {
				e.printStackTrace();
				statusCode = DwzJsonResultUtil.STATUS_CODE_500;
				message = "保存失败:" + e.getMessage();
			}
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId);

	}
	
	@RequestMapping(value = "/edit/{indexLunboId}")
	public String edit(HttpServletRequest request, Model model,
			@PathVariable String indexLunboId) {
		
		IndexLunboQO qo = new IndexLunboQO();
		qo.setId(indexLunboId);
		IndexLunbo indexLunbo = indexLunboServiceImpl.queryUnique(qo);
		String fileUploadPath = SysProperties.getInstance().get("fileUploadPath");
		model.addAttribute("fileUploadPath", fileUploadPath);
		model.addAttribute("indexLunbo",indexLunbo);
		
		AdQO adQO=new AdQO();
		adQO.setId(indexLunbo.getAdId());
		Ad ad=adServiceImpl.queryUnique(adQO);
		ImageQo imageQo = new ImageQo();
		imageQo.setProjectId(SysProperties.getInstance().get("imageServiceProjectId"));
		imageQo.setEnvName(SysProperties.getInstance().get("imageServiceProjectEnvName"));
		imageQo.setId(ad.getImage().getImageId());
		ImageUseTypeQo useType=new ImageUseTypeQo();
		useType.setId("PL_CMS_INDEX_LUNBO");
		imageQo.setUseType(useType);
		AlbumQo albumQo=new AlbumQo();
		albumQo.setId("PL_CMS_INDEX_LUNBO");
		imageQo.setAlbumQo(albumQo);
		ImageDTO imageDTO = imageService_1.queryUniqueImage_1(imageQo);
		
		model.addAttribute("fdfsFileInfoJSON", imageDTO.getSpecImageMap().get("default").getFileInfo());
		return "/indexlunbo/edit.html";
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(HttpServletRequest request, Model model,
			@ModelAttribute ModifyLunboCommand command) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		String navTabId = "indexLunboList";

		if (command != null) {
			try {
				indexLunboServiceImpl.modifyIndexLunbo(command);
			} catch (Exception e) {
				e.printStackTrace();
				statusCode = DwzJsonResultUtil.STATUS_CODE_500;
				message = "修改失败:" + e.getMessage();
			}
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId);
	}
	
	@RequestMapping(value = "/title/update")
	@ResponseBody
	public String updateTitle(HttpServletRequest request, Model model,
			@ModelAttribute ModifyIndexLunboTitleCommand command) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		String navTabId = "";

		if (command != null) {
			try {
				indexLunboServiceImpl.modifyIndexLunboTitle(command);
				;
			} catch (Exception e) {
				e.printStackTrace();
				statusCode = DwzJsonResultUtil.STATUS_CODE_500;
				message = "修改失败:" + e.getMessage();
			}
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId);
	}

	@RequestMapping(value = "/url/update")
	@ResponseBody
	public String updateUrl(HttpServletRequest request, Model model,
			@ModelAttribute ModifyIndexLunboUrlCommand command) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		String navTabId = "";

		if (command != null) {
			try {
				indexLunboServiceImpl.modifyIndexLunboUrl(command);
			} catch (Exception e) {
				e.printStackTrace();
				statusCode = DwzJsonResultUtil.STATUS_CODE_500;
				message = "修改失败:" + e.getMessage();
			}
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId);
	}

	@RequestMapping(value = "/image/update")
	@ResponseBody
	public String updateImage(HttpServletRequest request, Model model,
			@ModelAttribute ModifyIndexLunboImageCommand command) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		String navTabId = "";

		if (command != null) {
			try {
				indexLunboServiceImpl.modifyIndexLunboImage(command);
			} catch (Exception e) {
				e.printStackTrace();
				statusCode = DwzJsonResultUtil.STATUS_CODE_500;
				message = "修改失败:" + e.getMessage();
			}
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId);
	}

	@RequestMapping(value = "/show/{indexLunboId}")
	@ResponseBody
	public String show(HttpServletRequest request, Model model,
			@PathVariable String indexLunboId) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		String navTabId = "";

		ShowIndexLunboCommand command = new ShowIndexLunboCommand();
		command.setLunboId(indexLunboId);
		try {
			indexLunboServiceImpl.showIndexLunbo(command);
		} catch (Exception e) {
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "修改失败:" + e.getMessage();
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId);
	}

	@RequestMapping(value = "/hide")
	@ResponseBody
	public String hide(HttpServletRequest request, Model model,
			@PathVariable String indexLunboId) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		String navTabId = "";

		HideIndexLunboCommand command = new HideIndexLunboCommand();
		try {
			indexLunboServiceImpl.hideIndexLunbo(command);
		} catch (Exception e) {
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "修改失败:" + e.getMessage();
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId);
	}

	@ResponseBody
	@RequestMapping(value = "/delete/{articleId}")
	public String delete(HttpServletRequest request, Model model,
			@PathVariable String articleId) {
		// articleServiceImpl.deleteById(articleId);
		return DwzJsonResultUtil.createSimpleJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "删除成功");
	}

}
