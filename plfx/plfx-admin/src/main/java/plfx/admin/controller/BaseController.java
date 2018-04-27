package plfx.admin.controller;

import hg.common.component.BaseQo;
import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.ClassPathTool;
import hg.common.util.StringUtil;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.KeyValue;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import plfx.jp.pojo.exception.UploadException;

import com.alibaba.fastjson.JSON;


/**
 * @author yuxx
 * 
 */
public class BaseController {
	
	/**
	 * 将str打印给响应对象
	 * @param response
	 * @param str
	 */
	protected void print(HttpServletResponse response, String str){
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			HgLogger.getInstance().error("tuhualiang", "BaseController->print->exception:" + HgLogger.getStackTrace(e));
		}
	}
	
	public void ouputExcel(HSSFWorkbook wb, HttpServletResponse response) throws IOException {
		InputStream inStream = null;
		try {
			// 将文件存到指定位置
			ClassPathTool.getInstance();
			String fileName = ClassPathTool.getWebRootPath() + "/excel/"
					+ StringUtil.getRandomName() + ".xls";
			FileOutputStream fout = new FileOutputStream(fileName);

			wb.write(fout);
			fout.close();
			// 将文件输入并下载 读到流中
			inStream = new FileInputStream(fileName); // 文件的存放路径
			// 设置输出的格式
			response.reset();
			response.setContentType("application/vnd.ms-excel MSEXCEL");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ StringUtil.getRandomName() + ".xls" + "\"");
			// 循环取出流中的数据
			byte[] b = new byte[100];
			int len;
			while ((len = inStream.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("tuhualiang", "BaseController->ouputExcel->exception:" + HgLogger.getStackTrace(e));
		} finally {
			if(inStream != null){
				inStream.close();				
			}
		}
	}
	
	/**
	 * 自动将yyyy-MM-dd格式的参数转换成Date型参数
	 * @param binder
	 */
	@InitBinder 
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false)); 
	}
	
	/**
	 * 根据DwzPaginQo和Qo创建Pagination
	 * 
	 * @param dwzPaginQo
	 * @return
	 */
	public Pagination createPagination(DwzPaginQo dwzPaginQo, BaseQo qo) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(dwzPaginQo.getPageNum());
		pagination.setPageSize(dwzPaginQo.getNumPerPage());
		pagination.setCondition(qo);
		return pagination;
	}

	/**
	 * 根据DwzPaginQo创建Pagination
	 * 
	 * @param dwzPaginQo
	 * @return
	 */
	public Pagination createPagination(DwzPaginQo dwzPaginQo) {
		return createPagination(dwzPaginQo, null);
	}
	
	/**
	 * 直接获取请求参数
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public String getParam(HttpServletRequest request, String name) {
		return request.getParameter(name);
	}
	
	/**
	 * @方法功能说明: 获取当前登录的管理员 
	 * @param session
	 * @return
	 */
	@ModelAttribute
	public AuthUser getAuthUser(HttpSession session){
		Object obj = session.getAttribute(SecurityConstants.SESSION_USER_KEY);
		if(null == obj || !(obj instanceof AuthUser))
			return null;
		return (AuthUser)obj;
	}
	
	/**
	 * 
	 * @方法功能说明：验证图片格式, 像素(px), 大小(kb)
	 * @修改者名字：zzb
	 * @修改时间：2014年9月1日上午10:22:49
	 * @修改内容：
	 * @参数：@param file
	 * @参数：@param maxHeight 最大高度(px)
	 * @参数：@param maxWidth  最大宽度(px)
	 * @参数：@param maxSize	    最大大小(kb)
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static void validate(CommonsMultipartFile file,
			String maxHeight, String maxWidth, String maxSize) throws UploadException {
		
		// 1. 验证文件是否存在
		if(file == null || file.getFileItem() == null)
			throw new UploadException(UploadException.FILE_NOT_EXITS, "文件不存在！");
		
		// 2. 验证图片上传格式
		String fileName = file.getFileItem().getName();
		if(!fileName.endsWith(".jpg") && !fileName.endsWith(".JPG") 
				&& !fileName.endsWith(".gif") && !fileName.endsWith(".GIF") 
				&& !fileName.endsWith(".png") && !fileName.endsWith(".PNG")
				&& !fileName.endsWith(".bmp") && !fileName.endsWith(".BMP")
				) {
			throw new UploadException(UploadException.FILE_TYPE_ERROR, 
					"请上传jpg、gif、png、bmp或JPG、GIF、PNG、BMP格式的文件！");
		}
		
		// 3. 验证文件尺寸
		try {
			Image src =javax.imageio.ImageIO.read(file.getInputStream());
			
			if(StringUtils.isNotEmpty(maxHeight) && src.getHeight(null) > Integer.parseInt(maxHeight))
				throw new UploadException(UploadException.FILE_PIX_ERROR,
					"请上传【高度】小于等于" + maxHeight + "的图片！");
			
			if(StringUtils.isNotEmpty(maxWidth) && src.getWidth(null) > Integer.parseInt(maxWidth))
				throw new UploadException(UploadException.FILE_PIX_ERROR,
					"请上传【宽度】小于等于" + maxHeight + "的图片！");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 4. 验证文件大小
		Long size = file.getFileItem().getSize();
		if(StringUtils.isNotEmpty(maxSize) && size > Long.parseLong(maxSize) * 1024)
			throw new UploadException(UploadException.FILE_SIZE_ERROR,
				"请上传【大小】小于等于" + maxSize + "KB的图片！");
		
	}
	
	protected FdfsFileInfo uploadFileToFastDfs(CommonsMultipartFile file) {
		HgLogger.getInstance().info("yuqz", "BaseController->uploadFileToFastDfs->上传文件到fastDfs:" + JSON.toJSONString(file));
		try {
			Image src = ImageIO.read(file.getInputStream());
			String imageName = file.getFileItem().getName();
			String imageType = imageName
					.substring(imageName.lastIndexOf(".") + 1);
			Map<String, String> metaMap = new HashMap<String, String>();
			metaMap.put("title", imageName);
			metaMap.put("height", String.valueOf(src.getHeight(null)));
			metaMap.put("width", String.valueOf(src.getWidth(null)));
			HgLogger.getInstance().info("yuqz", "BaseController->uploadFileToFastDfs->metaMap:" + JSON.toJSONString(metaMap));
			FdfsFileUtil.init();
			HgLogger.getInstance().info("yuqz", "BaseController->uploadFileToFastDfs->对象类型名称:"+file.getInputStream().getClass().getName());
			// System.out.println("对象类型名称"+file.getInputStream().getClass().getName());
			HgLogger.getInstance().info("yuqz", "BaseController->uploadFileToFastDfs->InputStream="+(FileInputStream) file.getInputStream()
					+",imageType="+imageType+",metaMap="+metaMap);
			FdfsFileInfo fileInfo = FdfsFileUtil
					.upload((FileInputStream) file.getInputStream(), imageType,
							metaMap);
			HgLogger.getInstance().info("yuqz", "BaseController->uploadFileToFastDfs->fileInfo:"+JSON.toJSONString(fileInfo));
			return fileInfo;
		} catch (IOException e) {
			HgLogger.getInstance().info("yuqz",
					"上传图片出错！" + HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
		return null;
	}
	
	public final static List<KeyValue> TRADE_TYPE_LIST = new ArrayList<KeyValue>();

}
