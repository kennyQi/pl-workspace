package hg.jxc.admin.controller.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxc.domain.model.image.SupplierAptitudeImage;

import hg.common.util.ClassPathTool;
import hg.common.util.UUIDGenerator;
import hg.common.util.Validator;
import hg.jxc.admin.common.FileConstants;
import hg.jxc.admin.common.FileUploadUtils;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.controller.BaseController;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
			String fileNamePath = ClassPathTool.getWebRootPath() + File.separator + "excel" + File.separator + "template" + File.separator + fileName;
			fileNamePath = fileNamePath.replaceAll("file:", "");
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

	@RequestMapping(value = "/ueditor", params = "action=config")
	@ResponseBody
	public String ueditorGetConf(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String requestURL = request.getRequestURL().toString();
		int i = requestURL.indexOf(contextPath)+contextPath.length();
		String url = requestURL.substring(0,i);
		return "{\"imageActionName\":\"uploadimage\",\"imageFieldName\":\"upfile\",\"imageMaxSize\":2048000,\"imageAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\"],\"imageCompressEnable\":true,\"imageCompressBorder\":1600,\"imageInsertAlign\":\"none\",\"imageUrlPrefix\":\""
				+ url
				+ "\",\"imagePathFormat\":\"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",\"scrawlActionName\":\"uploadscrawl\",\"scrawlFieldName\":\"upfile\",\"scrawlPathFormat\":\"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",\"scrawlMaxSize\":2048000,\"scrawlUrlPrefix\":\"\",\"scrawlInsertAlign\":\"none\",\"snapscreenActionName\":\"uploadimage\",\"snapscreenPathFormat\":\"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",\"snapscreenUrlPrefix\":\"\",\"snapscreenInsertAlign\":\"none\",\"catcherLocalDomain\":[\"127.0.0.1\",\"localhost\",\"img.baidu.com\"],\"catcherActionName\":\"catchimage\",\"catcherFieldName\":\"source\",\"catcherPathFormat\":\"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",\"catcherUrlPrefix\":\"\",\"catcherMaxSize\":2048000,\"catcherAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\"],\"videoActionName\":\"uploadvideo\",\"videoFieldName\":\"upfile\",\"videoPathFormat\":\"/ueditor/jsp/upload/video/{yyyy}{mm}{dd}/{time}{rand:6}\",\"videoUrlPrefix\":\"\",\"videoMaxSize\":102400000,\"videoAllowFiles\":[\".flv\",\".swf\",\".mkv\",\".avi\",\".rm\",\".rmvb\",\".mpeg\",\".mpg\",\".ogg\",\".ogv\",\".mov\",\".wmv\",\".mp4\",\".webm\",\".mp3\",\".wav\",\".mid\"],\"fileActionName\":\"uploadfile\",\"fileFieldName\":\"upfile\",\"filePathFormat\":\"/ueditor/jsp/upload/file/{yyyy}{mm}{dd}/{time}{rand:6}\",\"fileUrlPrefix\":\"\",\"fileMaxSize\":51200000,\"fileAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\",\".flv\",\".swf\",\".mkv\",\".avi\",\".rm\",\".rmvb\",\".mpeg\",\".mpg\",\".ogg\",\".ogv\",\".mov\",\".wmv\",\".mp4\",\".webm\",\".mp3\",\".wav\",\".mid\",\".rar\",\".zip\",\".tar\",\".gz\",\".7z\",\".bz2\",\".cab\",\".iso\",\".doc\",\".docx\",\".xls\",\".xlsx\",\".ppt\",\".pptx\",\".pdf\",\".txt\",\".md\",\".xml\"],\"imageManagerActionName\":\"listimage\",\"imageManagerListPath\":\"/ueditor/jsp/upload/image/\",\"imageManagerListSize\":20,\"imageManagerUrlPrefix\":\"\",\"imageManagerInsertAlign\":\"none\",\"imageManagerAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\"],\"fileManagerActionName\":\"listfile\",\"fileManagerListPath\":\"/ueditor/jsp/upload/file/\",\"fileManagerUrlPrefix\":\"\",\"fileManagerListSize\":20,\"fileManagerAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\",\".flv\",\".swf\",\".mkv\",\".avi\",\".rm\",\".rmvb\",\".mpeg\",\".mpg\",\".ogg\",\".ogv\",\".mov\",\".wmv\",\".mp4\",\".webm\",\".mp3\",\".wav\",\".mid\",\".rar\",\".zip\",\".tar\",\".gz\",\".7z\",\".bz2\",\".cab\",\".iso\",\".doc\",\".docx\",\".xls\",\".xlsx\",\".ppt\",\".pptx\",\".pdf\",\".txt\",\".md\",\".xml\"]}";
	}

	@RequestMapping(value = "/ueditor", params = "action=uploadimage")
	@ResponseBody
	public String ueditorUploadImg(MultipartFile upfile) {
		String fileUrl = null;
		String realFileName = upfile.getOriginalFilename();
		String imageType = realFileName.substring(realFileName.lastIndexOf("."));
		String deFileName = UUIDGenerator.getUUID() + imageType;
		File tempFile = new File(deFileName);
		if (imageType == null || !(imageType.toLowerCase().equals(".jpg") || imageType.toLowerCase().equals(".png") || imageType.toLowerCase().equals(".jpeg"))) {
			return "{\"state\": \"只能上传jpg、jpeg、png格式图片\"}";
		}

		try {
			upfile.transferTo(tempFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String timeStr = String.valueOf(new Date().getTime());
		String filePath = timeStr.substring(timeStr.length() - 6, timeStr.length());

		boolean IsUpload = FileUploadUtils.uploadFile(tempFile, FileConstants.UPLOAD_TYPE_IMAGE, filePath, deFileName);
		if (IsUpload) {
			fileUrl = FileUploadUtils.getFileURL(FileConstants.UPLOAD_TYPE_IMAGE, filePath, "", deFileName);
		} else {
			return "{\"state\": \"上传失败\"}";
		}
		if (tempFile != null) {
			tempFile.delete();
		}
		return "{\"state\": \"SUCCESS\",\"original\": \"" + realFileName + "\",\"type\": \"" + imageType + "\",\"url\": \"" + fileUrl + "\"}";
	}

}
