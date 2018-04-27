package hgria.admin.controller.hgCommon.spi.image;

import hg.system.command.imageSpec.ImageSpecPubCommand;
import hg.system.model.image.ImageSpec;
import hg.system.service.ImageSpecService;
import hgria.admin.common.hgUtil.HGSystemProperties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @类功能说明：图片重定向控制
 * @类修改者：
 * @修改日期：2014-9-26下午5:41:55
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-9-26下午5:41:55
 */
@Controller
public class ImageRedirectController {
	
	@Autowired
	private ImageSpecService imageSpecService;
	
	// @Autowired
	private HGSystemProperties properties;
	
	@RequestMapping(value="/images/{imageId}/{key}")
	public RedirectView fetchImageUrl(
			HttpServletRequest request, 
			@PathVariable(value="imageId") String imageId, 
			@PathVariable(value="key") String key){
		
		if(key.equals("{key}"))
		     key = ImageSpec.DEFAULT_KEY;
		     
		ImageSpecPubCommand command=new ImageSpecPubCommand();
		command.setImageId(imageId);
		command.setKey(key);
		command.setPubUrl(properties.getImageStaticUrl());

		try {
			String imageUrl = imageSpecService.imageSpecQuePubUrl(command);
			return new RedirectView(imageUrl.replace('\\', '/'));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new RedirectView(request.getContextPath() + "/resources/image/nopic.png");
	}

	@RequestMapping(value="/images/{imageId}")
	public RedirectView fetchImageUrl(
			HttpServletRequest request, 
			@PathVariable(value="imageId") String imageId){
		return fetchImageUrl(request, imageId, ImageSpec.DEFAULT_KEY);
	}

}
