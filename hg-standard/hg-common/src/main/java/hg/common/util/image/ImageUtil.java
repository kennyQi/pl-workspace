package hg.common.util.image;

import hg.common.util.file.SimpleFileUtil;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.concurrent.Callable;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明：图片处理工具类<br>
 * 注意使用needCut和notDistort两个参数。<br>
 * notDistort参数，用于判断是否重新计算图片高宽比，避免产生的图片畸形、失真。false，以高度为基础计算；true，以宽度为基础计算；null，不处理<br>
 * needCut参数，用于在高宽比与原图不相等的情况下，是否进行特殊处理。true，若宽比高大则裁取中间，其余留白,若宽比高小则裁取顶部，其余留白；false，不处理
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月5日 上午9:39:48
 */
public class ImageUtil {
	/**
	 * @方法功能说明：压缩图片(按宽度压缩，高度等比缩小)
	 * @修改者名字：chenys
	 * @修改时间：2014年11月5日 上午9:44:02
	 * @修改内容：
	 * @param srcFile		源图片
	 * @param destPath		存储路径
	 * @param maxWidth		最大宽度
	 * @throws IOException
	 */
	public static void compressWidth(File srcFile, String destPath, int maxWidth)
			throws IOException {
		ImageUtil.compress(srcFile, destPath, maxWidth, 0, false, null);
	}
	
	/**
	 * @方法功能说明：压缩图片(按宽度压缩，高度等比缩小)
	 * @修改者名字：chenys
	 * @修改时间：2014年11月5日 上午9:44:02
	 * @修改内容：
	 * @param srcPath		源图片路径
	 * @param destPath		存储路径
	 * @param maxWidth		最大宽度
	 * @throws IOException
	 */
	public static void compressWidth(String srcPath, String destPath, int maxWidth)
			throws IOException {
		ImageUtil.compress(srcPath, destPath, maxWidth, 0, false, null);
	}
	
	/**
	 * @方法功能说明：压缩图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月5日 上午9:40:58
	 * @修改内容：
	 * @param srcFile		源图片
	 * @param destPath		存储路径
	 * @param maxWidth		最大宽度
	 * @param maxHeight		最大高度
	 * @throws IOException
	 */
	public static void compress(File srcFile, String destPath, int maxWidth,int maxHeight)
			throws IOException{
		ImageUtil.compress(srcFile, destPath, maxWidth, maxHeight, false, null);
	}
	
	/**
	 * @方法功能说明：压缩图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月5日 上午9:40:58
	 * @修改内容：
	 * @param srcPath		源图片路径
	 * @param destPath		存储路径
	 * @param maxWidth		最大宽度
	 * @param maxHeight		最大高度
	 * @throws IOException
	 */
	public static void compress(String srcPath, String destPath, int maxWidth,int maxHeight)
			throws IOException{
		ImageUtil.compress(srcPath, destPath, maxWidth, maxHeight, false, null);
	}
	
	/**
	 * @方法功能说明：压缩图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月5日 上午9:46:35
	 * @修改内容：
	 * @param srcFile		源图片
	 * @param destPath		存储路径
	 * @param maxWidth		最大宽度
	 * @param maxHeight		最大高度
	 * @param notDistort	是否失真(null，失真；true，以宽度为基础重新计算高宽比；false，以高度为基础重新计算高宽比)
	 * @throws IOException
	 */
	public static void compress(File srcFile, String destPath, int maxWidth,int maxHeight, Boolean notDistort
			) throws IOException {
		ImageUtil.compress(srcFile, destPath, maxWidth, maxHeight,false,notDistort);
	}
	
	/**
	 * @方法功能说明：压缩图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月5日 上午9:46:35
	 * @修改内容：
	 * @param srcPath		源图片路径
	 * @param destPath		存储路径
	 * @param maxWidth		最大宽度
	 * @param maxHeight		最大高度
	 * @param notDistort	是否失真(null，失真；true，以宽度为基础重新计算高宽比；false，以高度为基础重新计算高宽比)
	 * @throws IOException
	 */
	public static void compress(String srcPath, String destPath, int maxWidth,int maxHeight, Boolean notDistort
			) throws IOException {
		ImageUtil.compress(srcPath, destPath, maxWidth, maxHeight,false,notDistort);
	}
	
	/**
	 * @方法功能说明：压缩图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月5日 上午9:50:51
	 * @修改内容：
	 * @param srcFile		源图片
	 * @param destPath		存储路径
	 * @param maxWidth		最大宽度
	 * @param maxHeight		最大高度
	 * @param needCut		是否裁剪图片(宽比高大则裁取中间,宽比高小则裁取顶部。如果notDistort为null，则此参数失效)
	 * @param notDistort	是否失真(null，失真；true，以宽度为基础重新计算高宽比；false，以高度为基础重新计算高宽比)
	 * @throws IOException
	 */
	public static void compress(File srcFile, String destPath, int maxWidth,int maxHeight,boolean needCut,
			Boolean notDistort) throws IOException {
		//校验参数合法性
		if (!SimpleFileUtil.isFile(srcFile))
			throw new NullPointerException("源图片不存在");
		if(StringUtils.isBlank(destPath))
			throw new NullPointerException("存储路径不能为空");
		//如果notDistort为null，则needCut为false
		if(null == notDistort)
			needCut = false;
		
		InputStream in = null;
		ImageCompressResult result = null;
		try {
			in = new FileInputStream(srcFile);
			//图片设置
			ImageConfig config = new ImageConfig(in, destPath, maxWidth,
					maxHeight, needCut, notDistort);
			//压缩图片，获取执行结果
			result = new ImageCompressExecutor<ImageCompressResult>(config).call();
		} finally {
			//关闭输入流
			if (in != null)
				in.close();
		}
		//如果有异常则向上抛出
		if (result.isFailure()){
			//删除临时文件
			File file = new File(destPath);
			if(file.exists())
				file.delete();
			throw new IOException(result.getFailureException());
		}
	}
	
	/**
	 * @方法功能说明：压缩图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月5日 上午9:50:51
	 * @修改内容：
	 * @param srcPath		源图片路径
	 * @param destPath		存储路径
	 * @param maxWidth		最大宽度
	 * @param maxHeight		最大高度
	 * @param needCut		是否裁剪图片(宽比高大则裁取中间,宽比高小则裁取顶部。如果notDistort为null，则此参数失效)
	 * @param notDistort	是否失真(null，失真；true，以宽度为基础重新计算高宽比；false，以高度为基础重新计算高宽比)
	 * @throws IOException
	 */
	public static void compress(String srcPath, String destPath, int maxWidth,int maxHeight,boolean needCut,
			Boolean notDistort) throws IOException {
		ImageUtil.compress(new File(srcPath), destPath, maxWidth, maxHeight,needCut,notDistort);
	}
	
	/**
	 * @方法功能说明：裁剪图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月5日 上午11:56:37
	 * @修改内容：
	 * @param srcPath 		源图片路径
	 * @param destPath 		输出图片路径
	 * @param width 		截剪宽度
	 * @param height  		截剪高度
	 * @throws IOException 
	 * @throws ImageFormatException 
	 */
	public static void cutImage(String srcPath, String destPath, int width,int height) 
			throws  IOException {
		ImageUtil.cutImage(srcPath, destPath, 0, 0, width, height);
	}
	
	/**
	 * @方法功能说明：裁剪图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月5日 上午11:56:37
	 * @修改内容：
	 * @param srcImg 		源图片文件
	 * @param destPath 		输出图片路径
	 * @param width 		截剪宽度
	 * @param height  		截剪高度
	 * @throws IOException 
	 * @throws ImageFormatException 
	 */
	public static void cutImage(File srcImg, String destPath, int width,int height) 
			throws  IOException {
		ImageUtil.cutImage(srcImg, destPath, 0, 0, width, height);
	}
	
	/**
	 * @方法功能说明：裁剪图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月5日 上午11:56:13
	 * @修改内容：
	 * @param srcPath 		源图片路径
	 * @param destPath 		输出图片路径
	 * @param left 			左边距
	 * @param top 			上边距
	 * @param width 		截剪宽度
	 * @param height 		截剪高度
	 * @throws IOException 
	 * @throws ImageFormatException 
	 */
	public static void cutImage(String srcPath, String destPath, int left,int top, int width, int height) 
			throws  IOException {
		ImageUtil.cutImage(new File(srcPath), destPath, left, top, width,height);
	}
	
	/**
	 * @方法功能说明：裁剪图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月5日 上午11:56:13
	 * @修改内容：
	 * @param srcImg 		源图片文件
	 * @param destPath 		输出图片路径
	 * @param left 			左边距
	 * @param top 			上边距
	 * @param width 		截剪宽度
	 * @param height 		截剪高度
	 * @throws IOException 
	 * @throws ImageFormatException 
	 */
	public static void cutImage(File srcImg, String destPath, int left,int top, int width, int height) 
			throws  IOException {
		//判断图片是否存在
		if (!SimpleFileUtil.isFile(srcImg))
			throw new NullPointerException("源图片不存在");
		
		Image srcImage = ImageIO.read(srcImg);
		int src_w = srcImage.getWidth(null); // 源图宽
		int src_h = srcImage.getHeight(null);// 源图高
		//校验
		if (left < 0 || left >= src_w) 
			throw new IllegalArgumentException("左边距超出原图有效宽度");
		if (top < 0 || top >= src_h) 
			throw new IllegalArgumentException("上边距超出原图有效高度");
		if (width < 1) 
			throw new IllegalArgumentException("截剪宽度不能小于1");
		if (height < 1) 
			throw new IllegalArgumentException("截剪高度不能小于1");
		
		// 目标图片宽
		if (width > src_w || width + left > src_w) {
			width = src_w - left;
		}
		// 目标图片高
		if (height > src_h || height + top > src_h) {
			height = src_h - top;
		}
		//AWT的图片过滤器，用于剪裁图片的ImageFilter，提取图片的矩形区域
		ImageFilter cropFilter = new CropImageFilter(left, top, width, height);
		//获取目标图片
		Image cutImage = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(srcImage.getSource(), cropFilter));
		
		//重新绘制图片
		BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);//创建指定大小的三色画布
		Graphics g = tag.getGraphics();
		//获取图片类型
//		ImageType imageType = ImageTypeHandler.getImageType(srcImg);
		String type = getImageFormatName(srcImg);
		
		//特别处理透明色的图片
		if (ImageType.PNG.getType().equals(type)) {
			Graphics2D g2 = (Graphics2D) g;
			//重新获取透明色画布和画笔
			tag = g2.getDeviceConfiguration().createCompatibleImage(
					width,height, Transparency.TRANSLUCENT);
			g2 = tag.createGraphics();
			g2.drawImage(cutImage, 0, 0, null);
//		} else if (ImageType.GIF.getType().equals(type)) {
//			cutImage = srcImage.getScaledInstance(width,height,Image.SCALE_SMOOTH);
//			Graphics g = tag.getGraphics();
//			g.drawImage(cutImage,0,0, null); // 绘制缩小后的图
//			g.dispose();
		}else {
			g.drawImage(cutImage, 0, 0, null); // 绘制缩小后的图
			g.dispose();
		}
		
		// 输出文件
		ImageIO.write(tag,/* imageType.getType()*/type, new File(destPath));
	}

	/**
     * 获取图片格式
     * @param file	输入源，只能是File或者InputStream类型
     * @return    	图片格式
     */
    public static String getImageFormatName(Object file) throws IOException{
    	String formatName = "jpg";//默认为JPG格式字符
    	
    	//判断类型，只能是File或者InputStream类型
    	if(file instanceof File || file instanceof InputStream){
    		ImageInputStream iis = ImageIO.createImageInputStream(file);
    		try{
    			//图片读取器
    			Iterator<ImageReader> imageReader =  ImageIO.getImageReaders(iis);
    			if(imageReader.hasNext())
    				formatName = imageReader.next().getFormatName();//返回输入源格式字符
    		}finally{
    			if(null != iis)
    				iis.close();
    		}
    	}
		return formatName;
    }
	
	/**
	 * 检查图片尺寸
	 * @param imgFile 		图片文件
	 * @param minWidth		最小宽度
	 * @param minHeight		最小高度
	 * @return
	 * @throws IOException
	 */
	public static boolean checkSize(File imgFile, int minWidth, int minHeight) throws IOException {
		//判断图片是否存在
		if (!SimpleFileUtil.isFile(imgFile))
			return false;
		//判断图片高宽的有效性
		BufferedImage image = ImageIO.read(imgFile);
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		if (width < minWidth || height < minHeight)
			return false;
		return true;
	}
	
	/**
	 * @类功能说明：静态内部类——压缩图片设置类
	 * @公司名称：浙江汇购科技有限公司
	 * @部门：技术部
	 * @作者：chenys
	 * @创建时间：2014年11月5日 上午9:52:08
	 */
	static class ImageConfig {
		private final InputStream inputStream;	//输入流
		private final String destPath;			//路径
		private final int maxWidth;				//最大宽度
		private final int maxHeight;			//最大高度
		private final boolean needCut;			//是否剪裁
		private final Boolean notDistort;		//是否失真(null，失真；true，以宽度为基础重新计算高宽比；false，以高度为基础重新计算高宽比)
//		private ImageQuality quality;			//图片质量类型

		/**
		 * 构造函数
		 */
		public ImageConfig(InputStream inputStream, String destPath,
				int maxWidth, int maxHeight, boolean needCut, Boolean notDistort) {
			this.inputStream = inputStream;
			this.destPath = destPath;
			this.maxWidth = maxWidth;
			this.maxHeight = maxHeight;
			this.needCut = null == notDistort?false:needCut;
			this.notDistort = notDistort;
		}

		public InputStream getInputStream() {
			return inputStream;
		}
		public String getDestPath() {
			return destPath;
		}
		public int getMaxWidth() {
			return maxWidth;
		}
		public int getMaxHeight() {
			return maxHeight;
		}
		public double getMinWidthHeightRatio() {
			return 0.1;
		}
		public boolean isNeedCut() {
			return needCut;
		}
		public Boolean isNotDistort() {
			return notDistort;
		}
//		public ImageQuality getQuality() {
//			if (quality == null)
//				quality = ImageQuality.BEST;
//			return quality;
//		}
	}
	
	/**
	 * @类功能说明：静态内部类——压缩图片执行/调用类，线程调用
	 * @公司名称：浙江汇购科技有限公司
	 * @部门：技术部
	 * @作者：chenys
	 * @创建时间：2014年11月5日 上午9:54:24
	 * @param <V>
	 */
	static class ImageCompressExecutor<V> implements Callable<V> {
		private final ImageConfig config;	//压缩设置
		private final byte[] srcContents;	//内容字节数组
		private Image srcImage;				//从srcIn读取的原图片对象，在构造函数中初始化，防止二次读取
		private int compressedWidth;		//压缩后的宽度
		private int compressedHeight;		//压缩后的高度
		private final ImageType imageType;	//图片类型

		/**
		 * 构造函数
		 */
		public ImageCompressExecutor(ImageConfig config) throws IOException {
			this.config = config;
			this.srcContents = IOUtils.toByteArray(this.config.getInputStream());//输入流转字节
			this.srcImage = ImageIO.read(new ByteArrayInputStream(this.srcContents));//读取图片
			this.imageType = ImageTypeHandler.getImageType(this.srcContents);//获取图片类型
//			String type = getImageFormatName(this.config.getInputStream());
//			this.imageType = ImageType.get(type);//获取图片类型
		}

		/**
		 * 调用
		 */
		@Override
		@SuppressWarnings("unchecked")
		public V call() {
			try {
				compress();
				return (V) new ImageCompressResult(null, null);
			} catch (Exception e) {
				return (V) new ImageCompressResult(null, e);
			}
		}
		
		/**
		 * @方法功能说明：压缩图片
		 * @修改者名字：chenys
		 * @修改时间：2014年11月5日 上午11:57:17
		 * @修改内容：
		 * @throws Exception
		 */
		private void compress() throws Exception {
			Image originalImage = getSrcImage();
			// 计算压缩后的图片的宽和高
			calculatedCompressedWithHeight(originalImage);
			// 开始压缩图片
			compress(this.srcImage, this.config.getDestPath(), compressedWidth,compressedHeight);
			this.srcImage = null;
		}
		
		/**
		 * @方法功能说明：压缩图片
		 * @修改者名字：chenys
		 * @修改时间：2014年11月5日 上午11:57:31
		 * @修改内容：
		 * @param srcImage			源图片
		 * @param destPath			目标路径
		 * @param compressedWidth	剪裁宽度
		 * @param compressedHeight	剪裁高度
		 * @throws Exception
		 */
		private void compress(Image srcImage, String destPath, int compressedWidth,int compressedHeight) throws Exception {
			FileOutputStream out = null;
			this.mkdirs(destPath);
			out = new FileOutputStream(createCompressedFile(new File(destPath)));
			compress(srcImage, out, compressedWidth, compressedHeight);
			out.flush();
			out.close();
		}

		/**
		 * @方法功能说明：压缩图片
		 * @修改者名字：chenys
		 * @修改时间：2014年11月5日 上午11:57:31
		 * @修改内容：
		 * @param srcImage			源图片
		 * @param dest				输出流
		 * @param compressedWidth	剪裁宽度
		 * @param compressedHeight	剪裁高度
		 * @throws Exception
		 */
		private void compress(Image srcImage, OutputStream dest,int compressedWidth, int compressedHeight) 
				throws Exception {
			BufferedImage bufferedImage = null;
			
			//特别处理png图片类型，它支持透明色，一般处理会造成像素丢失
			if (ImageType.PNG.equals(imageType)) {
				bufferedImage = (BufferedImage) srcImage;
				Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
				
				//创建透明色(四色)画布
				bufferedImage = g2.getDeviceConfiguration().createCompatibleImage(
						compressedWidth,compressedHeight, Transparency.TRANSLUCENT
				);
				g2 = bufferedImage.createGraphics();//重新获取画笔
				//使用Image.SCALE_SMOOTH的缩略算法，生成缩略图片的平滑度的优先级比较高 生成的图片质量比较好 缺点是速度慢 
				g2.drawImage(
						srcImage.getScaledInstance(compressedWidth,compressedHeight, Image.SCALE_SMOOTH), 0, 0, null
				);
				g2.dispose();
				//就是说是，先压缩后剪裁
				if (this.config.needCut)
					bufferedImage = cutImage(compressedWidth, compressedHeight,bufferedImage,true);
				ImageIO.write(bufferedImage, ImageType.PNG.getType(), dest);//输出为png格式图片
			} else {
				//普通(三色)画布
				bufferedImage = new BufferedImage(compressedWidth,compressedHeight, BufferedImage.TYPE_INT_RGB);
				bufferedImage.getGraphics().drawImage(
						srcImage.getScaledInstance(compressedWidth,compressedHeight, Image.SCALE_SMOOTH),0,0,null
				);
				//就是说是，先压缩后剪裁
				if (this.config.needCut)
					bufferedImage = cutImage(compressedWidth, compressedHeight,bufferedImage, false);
				
				//输出对应的图片格式
				ImageIO.write(bufferedImage,imageType.getType(), dest);
			}
		}

		/**
		 * @方法功能说明：剪裁图片
		 * 这边的剪裁和外部类不相同，就是说如果目标宽高比和源图宽高比不相同，在保证图片不失真的情况下目标图留白，以满足图片比例
		 * @修改者名字：chenys
		 * @修改时间：2014年11月5日 下午12:00:04
		 * @修改内容：
		 * @param compressedWidth		裁剪宽度
		 * @param compressedHeight		裁剪高度
		 * @param bufferedImage			画布
		 * @param isPng					是否透明图片格式
		 * @return
		 * @throws Exception
		 */
		private BufferedImage cutImage(int compressedWidth,int compressedHeight, BufferedImage bufferedImage, boolean isPng)
				throws Exception {
			//如果高宽相等，无需剪裁，立即返回
			if (compressedWidth == compressedHeight)
				return bufferedImage;
			
			int top = 0;
			int left = 0;
			int width = config.getMaxWidth();
			int height = config.getMaxHeight();
			if (compressedWidth > compressedHeight)
				left = (compressedWidth - width) / 2;
			
			//AWT的图片过滤器，用于剪裁图片的ImageFilter，提取图片的矩形区域
			ImageFilter cropFilter = new CropImageFilter(left, top, width, height);
			//获取目标图片
			Image cutImage = Toolkit.getDefaultToolkit().createImage(
					new FilteredImageSource(bufferedImage.getSource(),cropFilter)
			);
			//重新绘制
			BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			
			//特别处理透明色的图片
			if (isPng) {
				Graphics2D g2 = (Graphics2D) g;
				//重新获取透明色画布和画笔
				tag = g2.getDeviceConfiguration().createCompatibleImage(
						width,height, Transparency.TRANSLUCENT);
				g2 = tag.createGraphics();
				g2.drawImage(cutImage, 0, 0, null);
			} else {
				g.drawImage(cutImage, 0, 0, null); // 绘制缩小后的图
				g.dispose();
			}
			return tag;
		}
		
		/**
		 * @方法功能说明：根据文件的原始高度和宽度，以及config中提供的最大压缩宽度和高度计算实际的压缩宽度和高度
		 * @修改者名字：chenys
		 * @修改时间：2014年11月5日 下午12:00:31
		 * @修改内容：
		 * @param image		源图片
		 * @throws IOException
		 */
		private void calculatedCompressedWithHeight(Image image) throws IOException {
			//源图高宽
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			
			//如果最大高度为0，则按宽度得出高度
			if (this.config.getMaxHeight() == 0) {
				if (this.config.getMaxWidth() < width) {
					this.compressedWidth = this.config.getMaxWidth();
				} else {
					this.compressedWidth = width;
				}
				this.compressedHeight = this.compressedWidth * height / (width);
			}
			
			//是否自动计算高宽
			if(null == this.config.isNotDistort()){
				this.compressedWidth = this.config.getMaxWidth();
				this.compressedHeight = this.config.getMaxHeight();
			}else{
				//按高度计算高宽比
				if(!this.config.isNotDistort()){
					this.compressedWidth = this.config.getMaxWidth();
					this.compressedHeight = this.config.getMaxWidth() * height / width;
					if (this.compressedHeight < this.config.getMaxHeight()) {
						this.compressedHeight = this.config.getMaxHeight();
						this.compressedWidth = this.config.getMaxHeight() * width/ height;
					}
					
				//按宽度计算高宽比
				}else{
					//--------------------------
					//--------------------------可能会有问题，反正我是绕晕了
					//--------------------------
					if (width > height) {
						if (width > this.config.getMaxWidth()) {
							this.compressedWidth = this.config.getMaxWidth();
							this.compressedHeight = this.config.getMaxWidth() * height/ width;
						} else {
							this.compressedWidth = width;
							this.compressedHeight = this.compressedWidth * height / width;
						}
					} else {
						if (height > this.config.getMaxHeight()) {
							this.compressedWidth = this.config.getMaxHeight() * width/ height;
							this.compressedHeight = this.config.getMaxHeight();
						} else {
							this.compressedHeight = height;
							this.compressedWidth = this.compressedHeight * width / height;
						}
					}
					if (this.compressedWidth > this.config.getMaxWidth()) {
						this.compressedWidth = this.config.getMaxWidth();
						this.compressedHeight = this.config.getMaxWidth() * height/ width;
					}
					if (this.compressedHeight > this.config.getMaxHeight()) {
						this.compressedHeight = this.config.getMaxHeight();
						this.compressedWidth = this.compressedHeight * width / height;
					}
				}
			}
		}

		/**
		 * @方法功能说明：验证原图片的宽高比是否小于设定的最大宽度和高度的宽高比， 因为一旦出现这种情况会导致压缩后的图片宽高会畸形，影响显示
		 * @修改者名字：chenys
		 * @修改时间：2014年11月5日 下午12:00:53
		 * @修改内容：
		 * @param image		源图片
		 * @return
		 */
		@SuppressWarnings("unused")
		private boolean validateWidthHeightRatio(Image image) {
			int width = image.getWidth(null); 
			int height = image.getHeight(null);
			if (((width + 0.0d) / height) < this.config.getMinWidthHeightRatio()) 
				return false; 
			return true;
		}

		/**
		 * @方法功能说明：创建将要压缩后的图片文件
		 * @修改者名字：chenys
		 * @修改时间：2014年11月5日 下午12:01:32
		 * @修改内容：
		 * @param file		源文件
		 * @return
		 * @throws IOException
		 */
		private File createCompressedFile(File file) throws IOException {
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			if (!file.exists())
				file.createNewFile();
			return file;
		}

		/**
		 * @方法功能说明：创建相关目录
		 * @修改者名字：chenys
		 * @修改时间：2014年11月5日 下午12:02:39
		 * @修改内容：
		 * @param path		文件存储路径
		 */
		public void mkdirs(String path) {
			File dir = new File(path).getParentFile();
			if (!dir.exists())
				dir.mkdirs(); // 创建文件夹
		}

		public Image getSrcImage() {
			return srcImage;
		}
	}
	
	/**
	 * @类功能说明：静态内部类——压缩图片结果类
	 * @公司名称：浙江汇购科技有限公司
	 * @部门：技术部
	 * @作者：chenys
	 * @创建时间：2014年11月5日 上午10:08:54
	 */
	static class ImageCompressResult {
		private final String imageName;				//图片名称
		private final Exception failureException;	//异常信息

		/**
		 * 构造函数
		 * @param imageName 原图片的图片名称
		 * @param failureException 失败的异常
		 */
		public ImageCompressResult(String imageName, Exception failureException) {
			this.imageName = imageName;
			this.failureException = failureException;
		}

		public String getImageName() {
			return imageName;
		}
		public Exception getFailureException() {
			return failureException;
		}
		/**
		 * @方法功能说明：是否存在异常
		 * @修改者名字：chenys
		 * @修改时间：2014年11月5日 下午12:03:00
		 * @修改内容：
		 * @return
		 */
		public boolean isFailure() {
			return this.failureException != null;
		}
	}

	/**
	 * @类功能说明：静态内部类——图片格式处理类
	 * @公司名称：浙江汇购科技有限公司
	 * @部门：技术部
	 * @作者：chenys
	 * @创建时间：2014年11月5日 上午11:16:35
	 */
	static class ImageTypeHandler {
		/**
		 * @方法功能说明：获取图片类型
		 * @修改者名字：chenys
		 * @修改时间：2014年11月5日 上午11:15:04
		 * @修改内容：
		 * @param file			源文件
		 * @return
		 * @throws IOException 
		 */
		public static ImageType getImageType(File file) throws IOException {
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				return ImageTypeHandler.getImageType(IOUtils.toByteArray(in));
			} catch (IOException e) {
				throw e;
			} finally {
				//关闭输入流
				IOUtils.closeQuietly(in);
			}
		}

		/**
		 * @方法功能说明：获取图片类型
		 * @修改者名字：chenys
		 * @修改时间：2014年11月5日 上午11:15:04
		 * @修改内容：
		 * @param path			源文件路径
		 * @return
		 * @throws IOException 
		 */
		public static ImageType getImageType(String path) throws IOException {
			return ImageTypeHandler.getImageType(new File(path));
		}

		/**
		 * @方法功能说明：获取图片类型
		 * @修改者名字：chenys
		 * @修改时间：2014年11月5日 上午11:14:39
		 * @修改内容：
		 * @param imgContent	内容字节数组
		 * @return
		 */
		public static ImageType getImageType(byte[] imgContent) {
			int len = imgContent.length;
			byte n1 = imgContent[len - 2];
			byte n2 = imgContent[len - 1];
			byte b0 = imgContent[0];
			byte b1 = imgContent[1];
			byte b2 = imgContent[2];
			byte b3 = imgContent[3];
			byte b4 = imgContent[4];
			byte b5 = imgContent[5];
			byte b6 = imgContent[6];
			byte b7 = imgContent[7];
			byte b8 = imgContent[8];
			byte b9 = imgContent[9];

			/**
			 * 判断gif
			 */
			if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F'
					&& b3 == (byte) '8' && b4 == (byte) '7' && b5 == (byte) 'a') {
				return ImageType.GIF;
				// GIF(G I F 8 9 a)
			} else if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F'
					&& b3 == (byte) '8' && b4 == (byte) '9' && b5 == (byte) 'a') {
				return ImageType.GIF;
			}

			/**
			 * 判断jpg
			 */
			if (b0 == -1 && b1 == -40 && n1 == -1 && n2 == -39) {
				return ImageType.JPG;
			} else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I'
					&& b9 == (byte) 'F') {
				return ImageType.JPG;
			} else if (b6 == (byte) 'E' && b7 == (byte) 'x' && b8 == (byte) 'i'
					&& b9 == (byte) 'f') {
				return ImageType.JPG;
			}

			/**
			 * 判断jpeg
			 */
			if (b0 == -1 && b1 == -40 && n1 == -1 && n2 == -39) {
				return ImageType.JPEG;
			} else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I'
					&& b9 == (byte) 'F') {
				return ImageType.JPEG;
			} else if (b6 == (byte) 'E' && b7 == (byte) 'x' && b8 == (byte) 'i'
					&& b9 == (byte) 'f') {
				return ImageType.JPEG;
			}

			/**
			 * 判断png
			 */
			if (b0 == -119 && b1 == (byte) 'P' && b2 == (byte) 'N'
					&& b3 == (byte) 'G' && b4 == 13 && b5 == 10 && b6 == 26) {
				return ImageType.PNG;
			}

			/**
			 * 判断png
			 */
			if (b0 == (byte) 'B' && b1 == (byte) 'M') {
				return ImageType.PNG;
			}
			return null;
		}
	}
}