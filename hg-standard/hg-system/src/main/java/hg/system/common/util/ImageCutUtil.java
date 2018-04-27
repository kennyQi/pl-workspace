package hg.system.common.util;

import hg.common.util.UUIDGenerator;
import hg.system.exception.HGException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明：图片裁剪工具类
 * @类修改者：zzb
 * @修改日期：2014年9月4日下午5:05:08
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月4日下午5:05:08
 */
public class ImageCutUtil {

	private static final String CUT_TEMP_PATH = System.getProperty("user.dir")
			+ File.separator;

	/**
	 * 图像切割（改） *
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param dirImageFile
	 *            新图像地址
	 * @param x
	 *            目标切片起点x坐标
	 * @param y
	 *            目标切片起点y坐标
	 * @param destWidth
	 *            目标切片宽度
	 * @param destHeight
	 *            目标切片高度
	 * @param showWidth 
	 */
	public static String abscut(ImageCutCommand cutCommand) throws HGException {
		
		// 1. 填充裁剪默认数据
		cutCommand.setCutDefalut();
		
		String imageType = cutCommand.getImgUrl().substring(
				cutCommand.getImgUrl().lastIndexOf(".") + 1);
		String fileType = (imageType == null ? "JPEG" : imageType);
		String filePath = CUT_TEMP_PATH + UUIDGenerator.getUUID() + "."
				+ fileType;

		try {
			Image img;
			ImageFilter cropFilter;
			// 2. 裁剪基本判断
			BufferedImage bi = ImageIO.read(new URL(cutCommand.getImgUrl()));
			int srcWidth = bi.getWidth(); 		// 源图宽度
			int srcHeight = bi.getHeight(); 	// 源图高度
			
			if (srcWidth >= cutCommand.getWidth() 
					&& srcHeight >= cutCommand.getHeight()) {

				// 2.1   根据显示比率计算裁剪比例(去除比率, 前台计算)
				/*if(StringUtils.isNotBlank(cutCommand.getShowWidth())) {
					cutCommand.setCompreDefalut();
					
					Double ratio = Double.parseDouble(cutCommand.getShowWidth()) / srcWidth;
					
					cutCommand.setLeft((int) (cutCommand.getLeft() / ratio));
					cutCommand.setTop((int) (cutCommand.getTop() / ratio));
					cutCommand.setWidth((int) (cutCommand.getWidth() / ratio));
					cutCommand.setHeight((int) (cutCommand.getHeight() / ratio));
				}*/
				
				// 2.2   裁剪图片
				Image image = javax.imageio.ImageIO.read(new URL(cutCommand.getImgUrl()));
				
				cropFilter = new CropImageFilter(cutCommand.getLeft(), cutCommand.getTop(),
						cutCommand.getWidth(), cutCommand.getHeight());
				img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				
				// 2.3  设置新的高度和宽度
				if(StringUtils.isNotBlank(cutCommand.getNewWidth()) 
						&& StringUtils.isNotBlank(cutCommand.getNewHeight())) {
					img = img.getScaledInstance(Integer.parseInt(cutCommand.getNewWidth()), 
							Integer.parseInt(cutCommand.getNewHeight()), Image.SCALE_DEFAULT);
					cutCommand.setWidth(Integer.parseInt(cutCommand.getNewWidth()));
					cutCommand.setHeight(Integer.parseInt(cutCommand.getNewHeight()));
				}
				
				// 2.4   输出到临时文件
				BufferedImage tag = new BufferedImage(cutCommand.getWidth(), 
						cutCommand.getHeight(), 
						BufferedImage.TYPE_INT_RGB);
				
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null); // 绘制缩小后的图
				g.dispose();
				ImageIO.write(tag, fileType, new File(filePath));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HGException(HGException.CODE_IMAGESPEC_CUT_ERROR,
					"图片裁剪异常！");
		}

		return filePath;
	}

	/**
	 * 缩放图像
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param scale
	 *            缩放比例
	 * @param flag
	 *            缩放选择:true 放大; false 缩小;
	 */
	public static void scale(String srcImageFile, String result, int scale,
			boolean flag) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长
			if (flag) {
				// 放大
				width = width * scale;
				height = height * scale;
			} else {
				// 缩小
				width = width / scale;
				height = height / scale;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重新生成按指定宽度和高度的图像
	 * 
	 * @param url
	 *            源图像文件地址
	 * @param result
	 *            新的图像地址
	 * @param _width
	 *            设置新的图像宽度
	 * @param _height
	 *            设置新的图像高度
	 */
	public static String scale(ImageCutCommand cutCommand) {
		
		cutCommand.setCompreDefalut();
		return scale(cutCommand.getImgUrl(), Integer.parseInt(cutCommand.getNewWidth()),
				Integer.parseInt(cutCommand.getNewHeight()));
	}

	public static String scale(String url, int _width,
			int _height) {
		return scale(url, _width, _height, 0, 0);
	}

	public static String scale(String url, int _width,
			int _height, int x, int y) {
		
		String imageType = url.substring(url.lastIndexOf(".") + 1);
		String fileType = (imageType == null ? "JPEG" : imageType);
		String filePath = CUT_TEMP_PATH + UUIDGenerator.getUUID() + "."
				+ fileType;
		
		try {
			BufferedImage src = ImageIO.read(new URL(url)); // 读入文件

			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长

//			if (width > _width) {
//				width = _width;
//			}
//			if (height > _height) {
//				height = _height;
//			}
			width = _width;
			height = _height;
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, x, y, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, fileType, new File(filePath));// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return filePath;
	}

	/**
	 * 图像类型转换 GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
	 */
	public static void convert(String source, String result) {
		try {
			File f = new File(source);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, "JPG", new File(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 彩色转为黑白
	 * 
	 * @param source
	 * @param result
	 */
	public static void gray(String source, String result) {
		try {
			BufferedImage src = ImageIO.read(new File(source));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "JPEG", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 晕。。。搞成多个了...
		scale("C://4.gif", "c://ipomoea.gif", 200, true);
		// ok
		// gray("c:/images/ipomoea.jpg", "c:/images/t/ipomoea.jpg");
		// convert("c:/images/ipomoea.jpg", "c:/images/t/ipomoea.gif");
		// scale("c:/images/5105049910001020110718648723.jpg",
		// "c:/images/t/5105049910001020110718648725.jpg",154,166,157,208);
		// scale("c:/images/rose1.jpg",
		// "c:/images/t/rose1.jpg",154,166,157,208);
//		scale("file://C:/323.png", 154, 166, 10, 10);

	}
}