package hg.dzpw.admin.controller.scenic;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;

import hg.common.util.JsonUtil;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.dzpw.admin.controller.BaseController;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.app.service.local.ScenicSpotPicLocalService;
import hg.dzpw.pojo.exception.DZPWException;
import hg.log.util.HgLogger;

/**
 * 
 * @类功能说明：景区图片管理
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015-12-10下午4:59:07
 * @版本：
 */
@Controller
@RequestMapping(value = "/scenicPic")
public class ScenicSpotPicController extends BaseController {
	private final int maxHeight = 1080;
	private final int maxWidth = 1800;
	private final int maxSize = 1024;
	@Autowired
	private ScenicSpotPicLocalService scenicSpotPicLocalService;

	@ResponseBody
	@RequestMapping(value = "/upload")
	public String upload(@RequestParam("imgFile") CommonsMultipartFile file) {
		HgLogger.getInstance().info("gtx", "上传景区图片:file【" + file + "】");
		// 1. 验证图片格式, 尺寸, 大小
		try {
			validate(file, maxHeight, maxWidth, maxSize);
		} catch (DZPWException e) {
			HgLogger.getInstance().error("gtx", "景区图片上传失败:file【" + file + "】");
			e.printStackTrace();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("error", 1);
			result.put("message", "上传失败");
			return JsonUtil.parseObject(result, false);
		}

		// 2. 上传图片
		FdfsFileInfo fileInfo = uploadFileToFastDfs(file);

		// 3. 设置返回值
		String imageUrl = SystemConfig.imageHost + fileInfo.getUri();
		HgLogger.getInstance().info("guotx", "景区图片上传成功");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("fileName", file.getFileItem().getName());
		result.put("error", 0);
		result.put("url", imageUrl);
		result.put("fdfsFileInfo", fileInfo);
		return JSON.toJSONString(result);
	}

	/**
	 * 删除文件，删除远程fastdfs文件
	 * 
	 * @描述：
	 * @author: guotx
	 * @version: 2015-12-11 下午2:06:37
	 */
	@ResponseBody
	@RequestMapping("/deleteRemotePic")
	public String deleteRemotePic(
			@RequestParam(value = "picUrl", required = true) String picUrl) {
		HgLogger.getInstance().info("guotx", "删除未保存景区图片" + picUrl);
		scenicSpotPicLocalService.deleteRemotePic(picUrl);
		return null;
	}

	/**
	 * 
	 * @描述：上传文件至dfs
	 * @author: guotx
	 * @version: 2015-12-10 下午5:25:24
	 */
	protected FdfsFileInfo uploadFileToFastDfs(CommonsMultipartFile file) {
		try {
			Image src = ImageIO.read(file.getInputStream());
			String imageName = file.getFileItem().getName();
			String imageType = imageName
					.substring(imageName.lastIndexOf(".") + 1);
			Map<String, String> metaMap = new HashMap<String, String>();
			metaMap.put("title", imageName);
			metaMap.put("height", String.valueOf(src.getHeight(null)));
			metaMap.put("width", String.valueOf(src.getWidth(null)));
			FdfsFileUtil.init();
			FdfsFileInfo fileInfo = FdfsFileUtil
					.upload((FileInputStream) file.getInputStream(), imageType,
							metaMap);
			return fileInfo;
		} catch (IOException e) {
			HgLogger.getInstance().info("guotx",
					"上传景区图片出错！" + HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @描述：图片验证，格式、尺寸、大小、是否存在
	 * @author: guotx
	 * @version: 2015-12-10 下午5:12:47
	 */
	public static void validate(CommonsMultipartFile file, int maxHeight,
			int maxWidth, int maxSize) throws DZPWException {

		// 1. 验证文件是否存在
		if (file == null || file.getFileItem() == null)
			throw new DZPWException(DZPWException.SCENICSPOT_PIC_NOT_EXISTS,
					"文件不存在！");

		// 2. 验证图片上传格式
		String fileName = file.getFileItem().getName();
		if (!fileName.endsWith(".jpg") && !fileName.endsWith(".JPG")
				&& !fileName.endsWith(".gif") && !fileName.endsWith(".GIF")
				&& !fileName.endsWith(".png") && !fileName.endsWith(".PNG")
				&& !fileName.endsWith(".bmp") && !fileName.endsWith(".BMP")) {
			throw new DZPWException(DZPWException.SCENICSPOT_PIC_FORMAT_ERROR,
					"请上传jpg、gif、png、bmp或JPG、GIF、PNG、BMP格式的文件！");
		}

		// 3. 验证文件尺寸
		try {
			Image src = javax.imageio.ImageIO.read(file.getInputStream());

			if (src.getHeight(null) > maxHeight)
				throw new DZPWException(DZPWException.SCENICSPOT_PIC_PIX_ERROR,
						"请上传【高度】小于等于" + maxHeight + "的图片！");

			if (src.getWidth(null) > maxWidth)
				throw new DZPWException(DZPWException.SCENICSPOT_PIC_PIX_ERROR,
						"请上传【宽度】小于等于" + maxHeight + "的图片！");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 4. 验证文件大小
		Long size = file.getFileItem().getSize();
		if (size > maxSize * 1024)
			throw new DZPWException(DZPWException.SCENICSPOT_PIC_SIZE_EERROR,
					"请上传【大小】小于等于" + maxSize + "KB的图片！");

	}

}
