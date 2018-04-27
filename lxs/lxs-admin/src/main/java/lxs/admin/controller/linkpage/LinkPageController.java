package lxs.admin.controller.linkpage;

import hg.common.util.DwzJsonResultUtil;
import hg.common.util.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.log.util.HgLogger;
import hg.service.image.command.image.CreateImageCommand;
import hg.service.image.pojo.dto.ImageDTO;
import hg.service.image.pojo.exception.ImageException;
import hg.service.image.pojo.qo.ImageQo;
import hg.service.image.spi.inter.ImageService_1;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lxs.app.service.app.LinkPageService;
import lxs.domain.model.app.LinkPage;
import lxs.pojo.command.app.LinkPageAddCommand;
import lxs.pojo.qo.app.LinkPageQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：引导页控制器
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：jinyy
 * @创建时间：2015年5月7日下午2:12:11
 */
@Controller
@RequestMapping(value = "/linkpage")
public class LinkPageController {

	@Autowired
	private ImageService_1 spiImageServiceImpl_1;

	@Autowired
	private LinkPageService linkPageService;

	/**
	 * 
	 * 
	 * @方法功能说明：添加/修改/
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 下午2:27:21
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	@RequestMapping(value = "/linkpageadd")
	@ResponseBody
	public String addlinkpage(Model model, LinkPageAddCommand command) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		if (command.getLinkpageid() != null) {

		} else {
			for (int i = 0; i < 5; i++) {
				if (command.getImagePath() != null
						&& StringUtils.isBlank(command.getImagePath().get(i))) {
					return DwzJsonResultUtil.createJsonString(statusCode,
							"图片未上传完整，请上传全部图片", null, "");
				}
			}

		}
		for (int i = 0; i < 5; i++) {
			if (StringUtils.isBlank(command.getFileName().get(i))) {

			} else {
				CreateImageCommand createImageCommand = new CreateImageCommand();
				// 利旧URL存储file的json
				createImageCommand.setFileInfo(command.getImageInfo().get(i));
				createImageCommand.setFileName(command.getFileName().get(i));
				createImageCommand.setRemark("旅行社APP广告");
				createImageCommand.setUseTypeId(SysProperties.getInstance()
						.get("imageUseTypeId"));
				createImageCommand.setFromProjectEnvName(SysProperties
						.getInstance().get("imageEnvName"));
				createImageCommand.setFromProjectId(SysProperties.getInstance()
						.get("imageProjectId"));
				createImageCommand.setAlbumId(SysProperties.getInstance().get(
						"imageUseTypeId"));

				try {
					String imageID = spiImageServiceImpl_1
							.createImage_1(createImageCommand);
					HgLogger.getInstance().info("lxs_dev",
							"【addAd】" + "新增轮播图片在图片服务中的ID：" + imageID);
					ImageQo imageQo = new ImageQo();
					imageQo.setId(imageID);
					ImageDTO imageDTO = spiImageServiceImpl_1
							.queryUniqueImage_1(imageQo);
					HgLogger.getInstance().info(
							"lxs_dev",
							"【addCarousel】" + "查询图片服务里得到结果："
									+ JSON.toJSONString(imageDTO));
					Map<String, String> map = new HashMap<String, String>();
					for (String key : imageDTO.getSpecImageMap().keySet()) {
						FdfsFileInfo fdfsFileInfo = JSON.parseObject(imageDTO
								.getSpecImageMap().get(key).getFileInfo(),
								FdfsFileInfo.class);
						map.put(key, fdfsFileInfo.getUri());
					}

					command.getImagePath().set(
							i,
							map.get(SysProperties.getInstance().get(
									"adImageType")));
				} catch (ImageException e) {
					HgLogger.getInstance().info(
							"lxs_dev",
							"【addCarousel】" + "上传轮播图片，图片服务报错"
									+ HgLogger.getStackTrace(e));
					e.printStackTrace();
				} catch (IOException e) {
					HgLogger.getInstance().info(
							"lxs_dev",
							"【addCarousel】" + "上传轮播图片，图片服务IO报错"
									+ HgLogger.getStackTrace(e));
					e.printStackTrace();
				}

				linkPageService.addLinkPage(command, i);
			}
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message, null,
				"pageView");

	}

	/**
	 * 
	 * 
	 * @方法功能说明：跳转到添加/修改页面
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 下午2:27:40
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	@RequestMapping(value = "/linkpageaddView")
	public String linkpageaddView(Model model) {
		
		List<LinkPage> linkPages = linkPageService.queryList(new LinkPageQO());
		HgLogger.getInstance().info("jinyy", "【LinkPageController】【linkpageaddView】【】"+JSON.toJSONString(linkPages));
		if (linkPages != null && linkPages.size() == 5) {
			for (LinkPage linkPage : linkPages) {
				if (linkPage.getImageURL() != null) {
					linkPage.setImageURL(SysProperties.getInstance().get(
							"fileUploadPath")
							+ linkPage.getImageURL());
				}
			}
			if (linkPages.get(0).getSort() == 0)
				model.addAttribute("linkPage0", linkPages.get(0));
			if (linkPages.get(1).getSort() == 1)
				model.addAttribute("linkPage1", linkPages.get(1));
			if (linkPages.get(2).getSort() == 2)
				model.addAttribute("linkPage2", linkPages.get(2));
			if (linkPages.get(3).getSort() == 3)
				model.addAttribute("linkPage3", linkPages.get(3));
			if (linkPages.get(4).getSort() == 4)
				model.addAttribute("linkPage4", linkPages.get(4));
		} else {
			HgLogger.getInstance().info("lxs_dev", "【linkpageaddView——4】");
			return "/linkPage/linkpageAdd.html";
		}

		HgLogger.getInstance().info("lxs_dev", "【linkpageaddView——5】");
		return "/linkPage/linkpageEdit.html";
	}

}
