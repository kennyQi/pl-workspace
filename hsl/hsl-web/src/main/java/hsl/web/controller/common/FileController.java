package hsl.web.controller.common;

import hg.common.util.JsonUtil;
import hsl.app.component.config.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.log.util.HgLogger;
import hsl.web.controller.BaseController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping(value="/file")
public class FileController extends BaseController{
	/**
	 * 日志
	 */
	@Autowired
	HgLogger hgLogger;
	
	@ResponseBody
	@RequestMapping(value = "/imgUpload")
	public String imgUpload(@RequestParam("file") CommonsMultipartFile file) {
		
		Map<String, String> result_map = new HashMap<String, String>();
		
		//上传图片
		FdfsFileInfo fileInfo = null;
		String imageName = file.getFileItem().getName();
		String imageType = imageName.substring(imageName.lastIndexOf(".") + 1);
		try {
			FdfsFileUtil.init();
			fileInfo = FdfsFileUtil.upload((FileInputStream) file.getInputStream(), imageType, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//设置返回值
		String imageUrl = SysProperties.getInstance().get("fileUploadPath") + fileInfo.getUri();
		result_map.put("imageUrl", imageUrl);
		result_map.put("status", "success");
		return JsonUtil.parseObject(result_map, false);
	}
	
}
