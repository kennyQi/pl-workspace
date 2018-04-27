package hsl.spi.action;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import hg.common.util.ClassPathTool;
import hg.common.util.JsonUtil;
import hg.common.util.PathUtil;
import hg.common.util.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.request.command.util.UploadImageCommand;
import hsl.api.v1.response.util.UploadImageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("uploadImageAction")
public class UploadImageAction implements HSLAction{
	@Autowired
	private HgLogger hgLogger;
	@Override
	public String execute(ApiRequest apiRequest) {
		UploadImageCommand uploadImageCommand=JSON.parseObject(apiRequest.getBody().getPayload(), UploadImageCommand.class);
		hgLogger.info("yuqz", "上传图片请求"+JSON.toJSONString(uploadImageCommand));
		return uploadImage(uploadImageCommand);
	}
	
	private String uploadImage(UploadImageCommand uploadImageCommand) {
		Map<String, String> result_map = new HashMap<String, String>();
		//上传图片
		FdfsFileInfo fileInfo = null;
		byte[] bytes = uploadImageCommand.getBytes();
		String imageType = uploadImageCommand.getImageType();
		File file = new File(PathUtil.getRootPath() + "temp."+imageType);
		OutputStream output = null;
		try {
			output = new FileOutputStream(file);
			BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
			bufferedOutput.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FdfsFileUtil.init();
		fileInfo = FdfsFileUtil.upload(file, null);
		
		if(file.exists()){
			file.delete();
		}
		//设置返回值
		String imagePath = SysProperties.getInstance().get("fileUploadPath") + fileInfo.getUri();
		UploadImageResponse uploadImageResponse = new UploadImageResponse();
		uploadImageResponse.setImagePath(imagePath);
		return JSON.toJSONString(uploadImageResponse);
	}
	
}
