package hgria.admin.common.hgUtil;

import hg.system.exception.HGException;

import java.awt.Image;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 
 * @类功能说明：文件上传工具类
 * @类修改者：zzb
 * @修改日期：2014年9月1日上午11:32:55
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月1日上午11:32:55
 *
 */
public class FileUploadUtils {


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
	public static ImageInfo validate(CommonsMultipartFile file,
			String maxHeight, String maxWidth, String maxSize) throws HGException {
		
		ImageInfo imgInfo = new ImageInfo();
		// 1. 验证文件是否存在
		if(file == null || file.getFileItem() == null)
			throw new HGException(HGException.FILE_NOT_EXITS, "文件不存在！");
		
		// 2. 验证图片上传格式
		String fileName = file.getFileItem().getName();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		imgInfo.setFileName(fileName);
		imgInfo.setFileType(fileType);
		if(!fileName.endsWith(".jpg") && !fileName.endsWith(".gif")
				&& !fileName.endsWith(".png") && !fileName.endsWith(".bmp")) {
			throw new HGException(HGException.FILE_TYPE_ERROR, "请上传jpg、gif、png或bmp格式的文件！");
		}
		
		// 3. 验证文件尺寸
		try {
			Image src =javax.imageio.ImageIO.read(file.getInputStream());
			
			int height = src.getHeight(null);
			int width = src.getWidth(null);
			imgInfo.setHeight(height);
			imgInfo.setWidth(width);
			
			if(StringUtils.isNotEmpty(maxHeight) && height > Integer.parseInt(maxHeight))
				throw new HGException(HGException.FILE_PIX_ERROR,
					"请上传【高度】小于等于" + maxHeight + "的图片！");
			
			if(StringUtils.isNotEmpty(maxWidth) && width > Integer.parseInt(maxWidth))
				throw new HGException(HGException.FILE_PIX_ERROR,
					"请上传【宽度】小于等于" + maxHeight + "的图片！");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 4. 验证文件大小
		Long size = file.getFileItem().getSize();
		imgInfo.setFileSize(size);
		if(StringUtils.isNotEmpty(maxSize) && size > Long.parseLong(maxSize) * 1024)
			throw new HGException(HGException.FILE_SIZE_ERROR,
				"请上传【大小】小于等于" + maxSize + "KB的图片！");

		return imgInfo;
	}
	
}
