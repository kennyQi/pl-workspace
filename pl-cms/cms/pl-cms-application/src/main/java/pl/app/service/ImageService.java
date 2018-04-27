package pl.app.service;

import hg.common.util.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.service.image.command.image.CreateImageCommand;
import hg.service.image.pojo.dto.ImageDTO;
import hg.service.image.pojo.dto.ImageSpecDTO;
import hg.service.image.pojo.exception.ImageException;
import hg.service.image.pojo.qo.AlbumQo;
import hg.service.image.pojo.qo.ImageQo;
import hg.service.image.pojo.qo.ImageUseTypeQo;
import hg.service.image.spi.inter.ImageService_1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.cms.domain.entity.image.Image;

import com.alibaba.fastjson.JSON;

@Service("imageLocalService")
public class ImageService {
	
	@Autowired
	private ImageService_1 imageSpiService;
	
	/**
	 * 
	 * @方法功能说明：向图片服务创建文章标题图片
	 * @修改者名字：yuxx
	 * @修改时间：2015年4月8日上午10:19:02
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Image
	 * @throws
	 */
	public Image createTitleImage(FdfsFileInfo tempFileInfo, String title,
			String remark, String useTypeId, String albumId) {
		// 调用图片服务一定要有图片服务中所配的工程id和环境名，可能会和CC控制中心所配的有不同，因此在CC中要添加两个自定义属性
		CreateImageCommand createImageCommand = new CreateImageCommand(
				SysProperties.getInstance().get("imageServiceProjectId"),
				SysProperties.getInstance().get("imageServiceProjectEnvName"));

		createImageCommand.setAlbumId(albumId);
		createImageCommand.setFileInfo(JSON.toJSONString(tempFileInfo));
		createImageCommand.setTitle(title);
		createImageCommand.setRemark(remark);
		createImageCommand.setUseTypeId(useTypeId); // 用途id已提前在图片服务后台建好，并设定了裁剪副本数量和尺寸

		String imageId = null;
		try {
			imageId = imageSpiService.createImage_1(createImageCommand);
		} catch (ImageException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageQo imageQo = new ImageQo();
		imageQo.setId(imageId);
		ImageUseTypeQo useType=new ImageUseTypeQo();
		useType.setId(useTypeId);
		imageQo.setUseType(useType);
		AlbumQo albumQo=new AlbumQo();
		albumQo.setId(albumId);
		imageQo.setAlbumQo(albumQo);
		ImageDTO imageDTO = imageSpiService.queryUniqueImage_1(imageQo);
		return convertTitleImage(imageDTO);
	}
	
	/**
	 * 
	 * @方法功能说明：根据图片服务返回的ImageDTO组装成本地Image值对象
	 * @修改者名字：yuxx
	 * @修改时间：2015年4月8日上午10:03:54
	 * @修改内容：
	 * @参数：@param imageDTO
	 * @参数：@return
	 * @return:Image
	 * @throws
	 */
	private Image convertTitleImage(ImageDTO imageDTO) {
		Image titleImage = new Image();
		titleImage.setImageId(imageDTO.getId());
		titleImage.setTitle(imageDTO.getTitle());
		titleImage.setRemark(imageDTO.getRemark());
		Map<String, String> specImageUrlMap = new HashMap<String, String>();
		if (imageDTO.getSpecImageMap() != null) {
			for (Entry<String, ImageSpecDTO> entry : imageDTO.getSpecImageMap()
					.entrySet()) {
				specImageUrlMap.put(
						entry.getKey(),
						JSON.parseObject(entry.getValue().getFileInfo(),
								FdfsFileInfo.class).getUri());
			}
			titleImage.setUrlMaps(specImageUrlMap);
			titleImage.setUrlMapsJSON(JSON.toJSONString(specImageUrlMap));
		}

		return titleImage;
	}

}
