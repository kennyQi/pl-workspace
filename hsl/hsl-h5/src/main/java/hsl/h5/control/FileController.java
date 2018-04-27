package hsl.h5.control;

import hg.common.util.JsonUtil;
import hsl.app.component.config.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.log.util.HgLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping(value="/file")
public class FileController extends HslCtrl{
	
	@ResponseBody
	@RequestMapping(value = "/imgUpload")
	public String imgUpload(@RequestParam("file") CommonsMultipartFile file) {
		HgLogger.getInstance().info("yuqz", "开始图片上传");
		Map<String, String> result_map = new HashMap<String, String>();
		
		//上传图片
		FdfsFileInfo fileInfo = null;
		String imageName = file.getFileItem().getName();
		String imageType = imageName.substring(imageName.lastIndexOf(".") + 1);
		HgLogger.getInstance().info("yuqz", "图片名称:" + imageName);
		HgLogger.getInstance().info("yuqz", "图片类型:" + imageType);
		try {
			HslFdfsFileUtil.init();
			fileInfo = HslFdfsFileUtil.upload((FileInputStream) file.getInputStream(), imageType, null);
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "上传图片出错:" + e.getMessage()+HgLogger.getStackTrace(e));
		}
		
		//设置返回值
		String imageUrl = SysProperties.getInstance().get("fileUploadPath") + fileInfo.getUri();
		HgLogger.getInstance().info("yuqz", "图片上传成功返回地址:" + imageUrl);
		result_map.put("imageUrl", imageUrl);
		result_map.put("status", "success");
		return JsonUtil.parseObject(result_map, false);
	}
	
}
