package hg.jxc.admin.controller.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import hg.common.util.ClassPathTool;
import hg.common.util.Validator;
import hg.jxc.admin.controller.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys/common")
public class CommonFunctionController extends BaseController {
	
	@RequestMapping("/download")
	public void downloadImportTemplate(String fileName, HttpServletResponse response) {
		if (fileName == null || !Validator.isSafe(fileName)) {
			return;
		}
		InputStream inStream = null;
		try {
			ClassPathTool.getInstance();
			String fileNamePath = ClassPathTool.getWebRootPath() +File.separator+ "excel"+File.separator+"template" +File.separator+ fileName;
			fileNamePath=fileNamePath.replaceAll("file:", "");
			inStream = new FileInputStream(fileNamePath);
			response.reset();
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
			
			byte[] b = new byte[100];
			int len;
			while ((len = inStream.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
