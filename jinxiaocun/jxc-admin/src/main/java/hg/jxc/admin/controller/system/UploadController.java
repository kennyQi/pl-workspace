package hg.jxc.admin.controller.system;

import hg.common.util.JsonUtil;
import hg.common.util.UUIDGenerator;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.jxc.admin.common.FileConstants;
import hg.jxc.admin.common.FileUploadUtils;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.UploadProductImageCommand;
import hg.pojo.command.UploadSupplierAptitudeImageCommand;
import hg.service.image.command.image.CreateImageCommand;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxc.app.service.image.ProductImageService;
import jxc.app.service.image.SupplierAptitudeImageService;
import jxc.domain.model.image.ProductImage;
import jxc.domain.model.image.SupplierAptitudeImage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/system/upload")
public class UploadController extends BaseController {

	@Autowired
	SupplierAptitudeImageService supplierAptitudeImageService;
	@Autowired
	ProductImageService productImageService;

	@RequestMapping("/img2")
	@ResponseBody
	public String uploadImg(MultipartFile brandPictureFile) {

		String fileUrl = null;
		String realFileName = brandPictureFile.getOriginalFilename();
		File tempFile = new File(realFileName);
		try {
			brandPictureFile.transferTo(tempFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long filePath = new Date().getTime();

		boolean IsUpload = FileUploadUtils.uploadFile(tempFile, FileConstants.UPLOAD_TYPE_IMAGE, filePath, realFileName);
		if (IsUpload) {
			fileUrl = FileUploadUtils.getFileURL(FileConstants.UPLOAD_TYPE_IMAGE, filePath, "", realFileName);
		}

		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put("status", "success");// success or error
		retMap.put("message", fileUrl);

		return JsonUtil.parseObject(retMap, false);
	}

	@ResponseBody
	@RequestMapping("/img")
	public String uploadImg(Model model, MultipartFile imageFile, HttpServletRequest request) {

		FdfsFileInfo fileInfo = null;

		try {
			File tempFile = new File(UUIDGenerator.getUUID() + imageFile.getOriginalFilename());
			imageFile.transferTo(tempFile);
			FileInputStream fis = new FileInputStream(tempFile);
			FdfsFileUtil.init();
			String imageName = imageFile.getOriginalFilename();
			String imageType = imageName.substring(imageName.lastIndexOf(".") + 1);
			fileInfo = FdfsFileUtil.upload(fis, imageType, null);

			CreateImageCommand command = new CreateImageCommand();

			command.setTitle("title");
			command.setFromProjectEnvName("projectName");
			command.setFromProjectId("projectId");
			command.setFromProjectId(JsonUtil.parseObject(fileInfo, false));
			// imgId = imageService.createImage(command);

			System.out.println();
		} catch (Exception e) {
			// TODO: handle exception
		}

		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put("imgSrc", "http://192.168.2.214/" + fileInfo.getGroupName() + "/" + fileInfo.getRemoteFileName());
		retMap.put("message", "请上传jpg、png、gif格式的图片！");
		retMap.put("imgId", "img test id");

		return JsonUtil.parseObject(retMap, false);
	}



	@ResponseBody
	@RequestMapping("/upload_img_supplier")
	public String uploadImgSupplier(Model model, MultipartFile imageFile,UploadSupplierAptitudeImageCommand command) {
		String fileUrl = null;
		String realFileName = imageFile.getOriginalFilename();
		String imageType = realFileName.substring(realFileName.lastIndexOf(".") + 1);
		String deFileName=UUIDGenerator.getUUID()+"."+imageType;
		File tempFile = new File(deFileName);
		
		
		try {
			imageFile.transferTo(tempFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long filePath = new Date().getTime();

		boolean IsUpload = FileUploadUtils.uploadFile(tempFile, FileConstants.UPLOAD_TYPE_IMAGE, filePath, deFileName);
		if (IsUpload) {
			fileUrl = FileUploadUtils.getFileURL(FileConstants.UPLOAD_TYPE_IMAGE, filePath, "", deFileName);
		}
		String imgId = UUIDGenerator.getUUID();
		command.setUrl(fileUrl);
		command.setImageId(imgId);
		SupplierAptitudeImage img = new SupplierAptitudeImage();
		if (StringUtils.isNotBlank(command.getSupplierAptitudeImageId())) {
		 	 img = supplierAptitudeImageService.updateSupplierAptitudeImage(command);
		} else {
			img= supplierAptitudeImageService.uploadSupplierAptitudeImage(command);
		}
		JsonResultUtil jru = new JsonResultUtil();
		jru.addAttr("status", "success");
		jru.addAttr("imgUrl", fileUrl);
		jru.addAttr("imgId",img.getId() );
		if (tempFile!=null) {
			tempFile.delete();
		}
		return jru.outputJsonString();
	}

	@ResponseBody
	@RequestMapping("/upload_img_product")
	public String uploadImgProduct(MultipartFile imageFile, UploadProductImageCommand command) {
		String fileUrl = null;
		String realFileName = imageFile.getOriginalFilename();
		
		String imageType = realFileName.substring(realFileName.lastIndexOf(".") + 1);
		String deFileName=UUIDGenerator.getUUID()+"."+imageType;
		
		File tempFile = new File(deFileName);
		try {
			imageFile.transferTo(tempFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long filePath = new Date().getTime();

		boolean IsUpload = FileUploadUtils.uploadFile(tempFile, FileConstants.UPLOAD_TYPE_IMAGE, filePath, deFileName);
		if (IsUpload) {
			fileUrl = FileUploadUtils.getFileURL(FileConstants.UPLOAD_TYPE_IMAGE, filePath, "", deFileName);
		}

		String imgId = UUIDGenerator.getUUID();
		command.setUrl(fileUrl);
		command.setImageId(imgId);
		ProductImage img = new ProductImage();
		if (StringUtils.isNotBlank(command.getProductImageId())) {
		 	 img = productImageService.updateProductImage(command);
		} else {
			img= productImageService.uploadProductImage(command);
		}
		JsonResultUtil jru = new JsonResultUtil();
		jru.addAttr("status", "success");
		jru.addAttr("imgUrl", fileUrl);
		jru.addAttr("imgId",img.getId() );
		
		if (tempFile!=null) {
			tempFile.delete();
		}
		return jru.outputJsonString();
	}

}