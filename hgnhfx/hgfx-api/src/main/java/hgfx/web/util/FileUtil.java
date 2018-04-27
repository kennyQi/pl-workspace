package hgfx.web.util;

import hg.demo.member.common.domain.model.system.JsonUtil;
import hg.framework.common.util.file.FdfsFileInfo;
import hg.framework.common.util.file.FdfsFileUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 
 * @类功能说明：上传工具
 * @类修改者：
 * @修改日期：2016年6月2日下午4:43:41
 * @作者：cangs
 * @创建时间：2016年6月2日下午4:43:41
 */
@Controller
@RequestMapping(value="/file")
public class FileUtil {
	
	/**
	 * 
	 * @方法功能说明：上传 type=1上传excel
	 * @修改者名字：cangs
	 * @修改时间：2016年6月6日上午10:15:41
	 * @修改内容：
	 * @参数：@param file
	 * @参数：@param type
	 * @参数：@return
	 * @参数：@throws Exception
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/upload")
	public String imgUpload(@RequestParam("file") CommonsMultipartFile file)  throws Exception {
		Map<String, String> result_map = new HashMap<String, String>();
		// 1 验证图片上传格式
		String fileName = file.getFileItem().getName();
		if(!fileName.endsWith(".xlsx") && !fileName.endsWith(".XLSX") 
				&& !fileName.endsWith(".xls") && !fileName.endsWith(".XLS") 
				) {
			result_map.put("status", "false");
			result_map.put("msg", "请上传xlsx、xls或XLSX、XLS格式的文件！");
			return JsonUtil.parseObject(result_map, false);
		}
		// 2 验证文件大小
		Long size = file.getFileItem().getSize();
		String maxSize="2048";
		if(size > Long.parseLong(maxSize) * 1024){
			result_map.put("status", "false");
			result_map.put("msg", "请上传【大小】小于等于" + maxSize + "KB的图片！");
			return JsonUtil.parseObject(result_map, false);
		}
		try {
			InputStream in = file.getInputStream();
			FdfsFileUtil.init();
			FdfsFileInfo fileinfo = FdfsFileUtil.upload((FileInputStream) (in), file.getFileItem().getName().split("\\.")[1], result_map);
			result_map.put("imageUrl", "http://"+fileinfo.getSourceIpAddr()+"/"+fileinfo.getGroupName()+"/"+fileinfo.getRemoteFileName());
			result_map.put("status", "success");
		} catch (IOException e) {
			e.printStackTrace();
			result_map.put("status", "success");
		}
		return JsonUtil.parseObject(result_map, false);
	}

}
