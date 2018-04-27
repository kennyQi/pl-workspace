package hsl.admin.common;

import hg.log.util.HgLogger;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片缩放
 * @author Administrator
 *
 */
@Component
public class PictureUtil {

	/**
	 * 访问路径
	 */
	private String visitLogoImagePath;
	
	/**
	 * 上传路径
	 */
	private String fileUploadPath;
	
	private String imageType = ".jpg";
	
	public String getVisitLogoImagePath() {
		return visitLogoImagePath;
	}

	public void setVisitLogoImagePath(String visitLogoImagePath) {
		this.visitLogoImagePath = visitLogoImagePath;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	/**
	 * 创建上传目录
	 * @param FilePath
	 */
	public void newDir(String FilePath){
		File file = new File(FilePath);
		boolean exits = file.exists();
		if(!exits){
			file.mkdir();
		}
	}
	
	
	/**
	 * 上传文件
	 * @param file 文件
	 * @param path 文件保存路径
	 * @param name 文件名
	 * @return
	 */
	public void uploadFile(MultipartFile file,String path, String name){
		File fileObj=new File(path+File.separator+name);
		fileObj.getParentFile().mkdirs();
		if(fileObj.exists()){
			fileObj.delete();
		}
	    try {
			BufferedInputStream in = new BufferedInputStream(file.getInputStream());
			BufferedOutputStream ou = new BufferedOutputStream(new FileOutputStream(new File(path+File.separator+name)));
			byte[] buffer = new byte[64 * 1024];
			int length;
			while ((length = in.read(buffer, 0, buffer.length)) > 0) {
				ou.write(buffer, 0, length);
			}
			in.close();
			ou.flush();
			ou.close();
		} catch (Exception e) {
//			LogMessage.logwrite(this.getClass(), e.getMessage());
			HgLogger.getInstance().error("zhangka", "PictureUtil->uploadFile->exception:" + HgLogger.getStackTrace(e));
		}
	}
	
	
	/**
	 * 缩放
	 * @param descFilePath
	 * 			  目标图片路径
	 * @param height
	 *            高度
	 * @param width
	 *            宽度
	 * @param bb
	 *            比例不对时是否需要补白
	 */
	@SuppressWarnings("static-access")
	public void resize(MultipartFile file, String descFilePath, int height, int width, boolean bb) {
		
		try {
			double ratio = 0.0; // 缩放比例
//			File f = new File(srcFilePath);
			BufferedImage bi = ImageIO.read(file.getInputStream());
			Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue()
							/ bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,itemp.getWidth(null), itemp.getHeight(null),Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,itemp.getWidth(null), itemp.getHeight(null),Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "jpg",new File(descFilePath));
		} catch (IOException e) {
//			LogMessage.logwrite(PictureUtil.class, e.getMessage());
			HgLogger.getInstance().error("zhangka", "PictureUtil->resize->exception:" + HgLogger.getStackTrace(e));
		}
	}
}