package hsl.admin.controller.util;


import hg.common.util.JsonUtil;
import hsl.app.component.config.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.log.util.HgLogger;
import hsl.admin.controller.BaseController;
import hsl.pojo.exception.UploadException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;

/**
 * 
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
@RequestMapping(value="/file")
public class FileController extends BaseController {
	
	/**
	 * 
	 * @方法功能说明：图片上传
	 * @修改者名字：yuqz
	 * @修改时间：2014年9月1日上午11:22:13
	 * @修改内容：
	 * @参数：@param file
	 * @参数：@param maxHeight 最大高度(px)
	 * @参数：@param maxWidth  最大宽度(px)
	 * @参数：@param maxSize	    最大大小(kb)
	 * @参数：@param metaMap
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/imgUpload")
	public String imgUpload(@RequestParam("file") CommonsMultipartFile file, 
			String maxHeight, String maxWidth, String maxSize) {

		HgLogger.getInstance().info("yuqz", "进入图片上传方法:file【" + file + "】");
		
		// 1. 验证图片格式, 尺寸, 大小
		Map<String, String> result_map = new HashMap<String, String>();
		try {
			FileUploadUtils.validate(file, maxHeight, maxWidth, maxSize);
		} catch (UploadException e) {
			HgLogger.getInstance().error("yuqz", "客户端组上传文件失败:file【" + file + "】");
			e.printStackTrace();
			result_map.put("status", "false");
			result_map.put("msg", e.getMessage());
			
			return JsonUtil.parseObject(result_map, false);
		}

		// 2. 上传图片
		FdfsFileInfo fileInfo = null;
		String imageName = file.getFileItem().getName();
		String imageType = imageName.substring(imageName.lastIndexOf(".") + 1);
		try {
			HslFdfsFileUtil.init();
//			System.out.println("对象类型名称"+file.getInputStream().getClass().getName());
			fileInfo = HslFdfsFileUtil.upload((InputStream) file.getInputStream(), imageType, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 3. 设置返回值
		String imageUrl = SysProperties.getInstance().get("fileUploadPath") + fileInfo.getUri();
		
		result_map.put("status", "success");
		result_map.put("imageUrl", imageUrl);
		result_map.put("imageName", imageName);
		result_map.put("fileInfo", JSON.toJSONString(fileInfo));
//		result_map.put("groupName", fileInfo.getGroupName());
//		result_map.put("remoteFileName", fileInfo.getRemoteFileName());
		
		HgLogger.getInstance().info("yuqz", "图片上传成功");
		
		return JsonUtil.parseObject(result_map, false);
	}
	
	
	
}
