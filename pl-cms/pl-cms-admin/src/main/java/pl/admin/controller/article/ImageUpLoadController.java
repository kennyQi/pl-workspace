package pl.admin.controller.article;
import hg.common.util.JsonUtil;
import hg.common.util.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.log.util.HgLogger;
import hg.service.image.command.image.CreateTempImageCommand;
import hg.service.image.pojo.exception.ImageException;
import hg.service.image.spi.inter.ImageService_1;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import pl.admin.controller.BaseController;
import pl.cms.pojo.exception.UploadException;
import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：文件控制器
 * @类修改者：yuqz
 * @修改日期：2014年9月1日上午11:18:29
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：yuqz
 * @创建时间：2014年9月1日上午11:18:29
 * 
 */
@Controller
@RequestMapping(value = "/image")
public class ImageUpLoadController extends BaseController {
	@Resource
	private ImageService_1 spiImageServiceImpl_1;

	/**
	 * 
	 * @方法功能说明：图片上传
	 * @修改者名字：yuqz
	 * @修改时间：2014年9月1日上午11:22:13
	 * @修改内容：
	 * @参数：@param file
	 * @参数：@param maxHeight 最大高度(px)
	 * @参数：@param maxWidth 最大宽度(px)
	 * @参数：@param maxSize 最大大小(kb)
	 * @参数：@param metaMap
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/upload")
	public String upload(@RequestParam("imgFile") CommonsMultipartFile file,
			String maxHeight, String maxWidth, String maxSize) {
		HgLogger.getInstance().info("yu" + "qz", "进入图片上传方法:file【" + file + "】");
		// 1. 验证图片格式, 尺寸, 大小
		try {
			validate(file, maxHeight, maxWidth, maxSize);
		} catch (UploadException e) {
			HgLogger.getInstance().error("yuqz",
					"客户端组上传文件失败:file【" + file + "】");
			e.printStackTrace();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("error", 1);
			result.put("message", "上传失败");
			return JsonUtil.parseObject(result, false);
		}

		// 2. 上传图片
		FdfsFileInfo fileInfo = uploadFileToFastDfs(file);

		// 上传完 图片 使用图片服务创建临时图片得到imageId
		CreateTempImageCommand imageCreateCommand = new CreateTempImageCommand(
				SysProperties.getInstance().get("imageServiceProjectId"),
				SysProperties.getInstance().get("imageServiceProjectEnvName"));
		imageCreateCommand.setTitle(fileInfo.getMetaMap().get("title"));
		imageCreateCommand.setRemark("省协会-H5");
		imageCreateCommand.setFileInfo(JSON.toJSONString(fileInfo));
		String imageId;
		try {
			imageId = spiImageServiceImpl_1.createTempImage_1(imageCreateCommand);
			fileInfo.setImageId(imageId);
		} catch (ImageException e) {
			e.printStackTrace();
		}

		// 3. 设置返回值
		String imageUrl = SysProperties.getInstance().get("fileUploadPath")
				+ fileInfo.getUri();
		HgLogger.getInstance().info("yuqz", "图片上传成功");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("error", 0);
		result.put("url", imageUrl);
		result.put("fdfsFileInfo", fileInfo);
		return JSON.toJSONString(result);
	}

}
