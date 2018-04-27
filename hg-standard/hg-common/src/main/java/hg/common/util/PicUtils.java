package hg.common.util;

import hg.common.util.file.DownloadUtil;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;

import org.apache.log4j.helpers.Loader;
import org.csource.fastdfs.ClientGlobal;


public class PicUtils {
	/**
	 * 水印密度：一般
	 */
	public final static int WAWTERMARK_NORMAL=2;
	/**
	 * 水印密度：稀疏
	 */
	public final static int WAWTERMARK_THIN=1;
	/**
	 * 水印密度：密集
	 */
	public final static int WAWTERMARK_THICK=3;
	/**
	 *水印位置：居中
	 */
	public final static int WAWTERMARK_CENTER=3;
	/**
	 *水印位置：左上
	 */
	public final static int WAWTERMARK_LEFT_TOP=1;
	/**
	 *水印位置：左下
	 */
	public final static int WAWTERMARK_LEFT_BUTTON=2;
	/**
	 *水印位置：右上
	 */
	public final static int WAWTERMARK_RIGHT_TOP=4;
	/**
	 *水印位置：右下
	 */
	public final static int WAWTERMARK_RIGHT_BUTTOM=5;

	/**
	 * 图片水印方法-平铺
	 * 以平铺的方式充满整背景
	 * 水印透明度为50%
	 * @author cangs
	 * @since hg-common
	 * @date 2016年4月26日下午4:30:02
	 * @param pic 图片流
	 * @param watermark 水印图片流
	 * @param waterMarkMode 水印密集程度 
	 * PicUtils.WAWTERMARK_THIN 稀疏，
	 * PicUtils.WAWTERMARK_NORMAL 一般，
	 * PicUtils.WAWTERMARK_THICK 密集
	 * @return 图片流
	 * @throws Exception
	 * @modify zqq 2016年5月5日 14:16:18
	 * 
	 */
	public static InputStream waterMarkTile(InputStream pic,InputStream watermark,int waterMarkMode) throws Exception{
		//读取待水印图片
		Image background = ImageIO.read(pic);
		//图片宽度
		int wideth = background.getWidth(null);
		//图片长度
		int height = background.getHeight(null);
		BufferedImage image = new BufferedImage(wideth, height,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		//绘制底面
		g.drawImage(background, 0, 0, wideth, height, null);
		//读取水印图片
		Image mark = ImageIO.read(watermark);
		//水印宽度
		int wideth_watermark = mark.getWidth(null);
		//水印长度
		int height_watermark = mark.getHeight(null);
		//对水印图片设置透明度
		Graphics2D g2d = (Graphics2D)g;
		float alpha = 0.5f;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		//横向打印个数
		int xCount = wideth/wideth_watermark+1;
		//纵向打印个数
		int yCount = height/height_watermark+1;
		int xp=wideth_watermark/2;
		int yp=height_watermark/2;
		//根据模式 设置间距从而调整密集程度
        switch (waterMarkMode) {
		case WAWTERMARK_NORMAL:
			xp=wideth_watermark/2;
	        yp=height_watermark;
			break;
		case WAWTERMARK_THIN:
			xp=wideth_watermark/2;
	        yp=height_watermark*2;
			break;
		case WAWTERMARK_THICK:
			xp=wideth_watermark/2;
	        yp=height_watermark/2;
			break;
		default:
			break;
		}
		//循环绘制水印
		for(int i=0;i<xCount;i++){
			for(int j=0;j<yCount;j++){
				//设置旋转角度(这里是45度向上)，以i*(wideth_watermark+xp), j*(height_watermark+yp)为原点
				g2d.rotate(-(45 * Math.PI / 180), i*(wideth_watermark+xp), j*(height_watermark+yp));
				g2d.drawImage(mark, i*(wideth_watermark+xp), j*(height_watermark+yp), wideth_watermark, height_watermark, null);
				//将画布旋转回来
				g2d.rotate((45 * Math.PI / 180), i*(wideth_watermark+xp), j*(height_watermark+yp));
			}
		}
		//结束绘制
		g2d.dispose();
		g.dispose();
		//返回inputstream流
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", os);
		return new ByteArrayInputStream(os.toByteArray());
	}
	

	/**
	 * 文字水印方法-平铺
	 * 以平铺的方式充满整背景
	 * 水印透明度为50%
	 * @author cangs
	 * @since hg-common
	 * @date 2016年4月26日下午4:30:02
	 * @param pic 图片流
	 * @param watermark 水印字符
	 * @param waterMarkMode 水印密集程度 
	 * PicUtils.WAWTERMARK_THIN 稀疏，
	 * PicUtils.WAWTERMARK_NORMAL 一般，
	 * PicUtils.WAWTERMARK_THICK 密集
	 * @return 图片流
	 * @throws Exception
	 * @modify zqq 2016年5月5日 14:16:18
	 * 
	 */
	public static InputStream waterMarkTile(InputStream pic,String watermark,int waterMarkMode) throws Exception{
		//读取待水印图片
		Image background = ImageIO.read(pic);
		//图片宽度
		int wideth = background.getWidth(null);
		//图片长度
		int height = background.getHeight(null);
		BufferedImage image = new BufferedImage(wideth, height,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		//绘制底面
		g.drawImage(background, 0, 0, wideth, height, null);
		int size = 0;
		if(wideth<height){
			size=(int)Math.ceil(wideth*0.1);
		}else{
			size=(int)Math.ceil(height*0.1);
		}
		g.setColor(Color.BLACK);  
        g.setFont(new Font("TimesRoman", Font.BOLD, size)); 
        //对水印图片设置透明度
        Graphics2D g2d = (Graphics2D)g;
        float alpha = 0.5f;
      	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        int wideth_watermark = size*8/3;
        int height_watermark = size;
        //横向打印个数
        int xCount = wideth/wideth_watermark+1;
        //纵向打印个数
        int yCount = height/height_watermark+1;
        //设置间距
        int xp=wideth_watermark/2;
        int yp=height_watermark*3;
        //根据模式 设置间距从而调整密集程度
        switch (waterMarkMode) {
		case WAWTERMARK_NORMAL:
			xp=wideth_watermark/2;
	        yp=height_watermark*3;
			break;
		case WAWTERMARK_THIN:
			xp=wideth_watermark/2;
	        yp=height_watermark*6;
			break;
		case WAWTERMARK_THICK:
			xp=wideth_watermark/2;
	        yp=height_watermark;
			break;
		default:
			break;
		}
        //循环绘制水印
        for(int i=0;i<xCount;i++){
        	for(int j=0;j<yCount;j++){
		    	//设置旋转角度(这里是45度向上)，以i*(wideth_watermark+xp), j*(height_watermark+yp)为原点
		     	g2d.rotate(-(45 * Math.PI / 180), i*(wideth_watermark+xp), j*(height_watermark+yp));
		      	g.drawString(watermark, i*(wideth_watermark+xp), j*(height_watermark+yp));  
		      //	g2d.drawString(watermark, i*(wideth_watermark+xp), j*(height_watermark+yp), wideth_watermark, height_watermark, null);
		      	//将画布旋转回来
		      	g2d.rotate((45 * Math.PI / 180), i*(wideth_watermark+xp), j*(height_watermark+yp));
        	}
        }
        //绘制水印
        //g.drawString(watermark, (int)Math.ceil((wideth - size) *(60-watermark.length())/100),(int)Math.ceil((height - size) *0.9));  
        //结束绘制
        g.dispose();  
        g2d.dispose();
		//返回inputstream流
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", os);
		return new ByteArrayInputStream(os.toByteArray());
	}
	
	/**
	 * 图片水印方法-单个
	 * 整个背景只有一个水印
	 * 水印透明度为50%
	 * @author cangs
	 * @since hg-common
	 * @date 2016年4月26日下午4:30:02
	 * @param pic 图片流
	 * @param watermark 水印图片流
	 * @param waterMarkMode 水印密集程度 
	 * PicUtils.WAWTERMARK_CENTER 居中，
	 * PicUtils.WAWTERMARK_LEFT_TOP 左上，
	 * PicUtils.WAWTERMARK_LEFT_BUTTON 左下，
	 * PicUtils.WAWTERMARK_RIGHT_TOP 右上，
	 * PicUtils.WAWTERMARK_RIGHT_BUTTOM 右下，
	 * @return 图片流
	 * @throws Exception
	 * @modify zqq 2016年5月5日 14:16:18
	 * 
	 */
	public static InputStream waterMark(InputStream pic,InputStream watermark,int waterMarkLocation) throws Exception{
		//读取待水印图片
		Image background = ImageIO.read(pic);
		//图片宽度
		int wideth = background.getWidth(null);
		//图片长度
		int height = background.getHeight(null);
		BufferedImage image = new BufferedImage(wideth, height,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		//绘制底面
		g.drawImage(background, 0, 0, wideth, height, null);
		//读取水印图片
		Image mark = ImageIO.read(watermark);
		//水印宽度
		int wideth_watermark = mark.getWidth(null);
		//水印长度
		int height_watermark = mark.getHeight(null);
		//设置水印位置
		int x=(wideth-wideth_watermark)/2;
		int y=(height-height_watermark)/2;
		switch (waterMarkLocation) {
		case WAWTERMARK_CENTER:
			x=(wideth-wideth_watermark)/2;
			y=(height-height_watermark)/2;
			break;
		case WAWTERMARK_LEFT_TOP:
			x=height_watermark/2;
			y=height_watermark/2;
			break;
		case WAWTERMARK_LEFT_BUTTON:
			x=height_watermark/2;
			y=height-height_watermark;
			break;
		case WAWTERMARK_RIGHT_TOP:
			x=wideth-wideth_watermark-height_watermark;
			y=height_watermark/2;
			break;
		case WAWTERMARK_RIGHT_BUTTOM:
			x=wideth-wideth_watermark-height_watermark;
			y=height-height_watermark;
			break;

		default:
			break;
		}
		//对水印图片设置透明度
		Graphics2D g2d = (Graphics2D)g;
		float alpha = 0.5f;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		g2d.drawImage(mark, x, y, wideth_watermark, height_watermark, null);
		//结束绘制
		g2d.dispose();
		g.dispose();
		//返回inputstream流
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", os);
		return new ByteArrayInputStream(os.toByteArray());
	}
	
	/**
	 * 图片水印方法-单个
	 * 整个背景只有一个水印
	 * 水印透明度为50%
	 * @author cangs
	 * @since hg-common
	 * @date 2016年4月26日下午4:30:02
	 * @param pic 图片流
	 * @param watermark 水印字符
	 * @param waterMarkMode 水印密集程度 
	 * PicUtils.WAWTERMARK_CENTER 居中，
	 * PicUtils.WAWTERMARK_LEFT_TOP 左上，
	 * PicUtils.WAWTERMARK_LEFT_BUTTON 左下，
	 * PicUtils.WAWTERMARK_RIGHT_TOP 右上，
	 * PicUtils.WAWTERMARK_RIGHT_BUTTOM 右下，
	 * @return 图片流
	 * @throws Exception
	 * @modify zqq 2016年5月5日 14:16:18
	 * 
	 */
	public static InputStream waterMark(InputStream pic,String watermark,int waterMarkLocation) throws Exception{
		//读取待水印图片
		Image background = ImageIO.read(pic);
		//图片宽度
		int wideth = background.getWidth(null);
		//图片长度
		int height = background.getHeight(null);
		BufferedImage image = new BufferedImage(wideth, height,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		//绘制底面
		g.drawImage(background, 0, 0, wideth, height, null);
		int size = 0;
		if(wideth<height){
			size=(int)Math.ceil(wideth*0.1);
		}else{
			size=(int)Math.ceil(height*0.1);
		}
		g.setColor(Color.BLACK);  
        g.setFont(new Font("TimesRoman", Font.BOLD, size)); 
        //对水印图片设置透明度
        Graphics2D g2d = (Graphics2D)g;
        float alpha = 0.5f;
      	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
      	int wideth_watermark = size*8/3;
        int height_watermark = size;
      //设置水印位置
      		int x=(wideth-wideth_watermark)/2;
      		int y=(height-height_watermark/2)/2;
      		switch (waterMarkLocation) {
      		case WAWTERMARK_CENTER:
      			x=(wideth-wideth_watermark)/2;
      			y=(height-height_watermark/2)/2;
      			break;
      		case WAWTERMARK_LEFT_TOP:
      			x=height_watermark/2;
      			y=height_watermark;
      			break;
      		case WAWTERMARK_LEFT_BUTTON:
      			x=height_watermark/2;
      			y=height-height_watermark/2;
      			break;
      		case WAWTERMARK_RIGHT_TOP:
      			x=wideth-wideth_watermark-height_watermark;
      			y=height_watermark;
      			break;
      		case WAWTERMARK_RIGHT_BUTTOM:
      			x=wideth-wideth_watermark-height_watermark;
      			y=height-height_watermark/2;
      			break;

      		default:
      			break;
      		}
        //绘制水印
        g.drawString(watermark, x,y);  
        //结束绘制
        g.dispose(); 
        g2d.dispose();
		//返回inputstream流
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", os);
		return new ByteArrayInputStream(os.toByteArray());
	}

	/**
	 * 图片剪裁方法
	 * @author cangs
	 * @since hg-common
	 * @date 2016年4月26日下午4:30:02
	 * @param pic 图片流
	 * @param cutArea 压缩规格
	 * @return 图片流
	 * @throws Exception
	 */
	public static InputStream cut(InputStream pic,CutArea cutArea) throws Exception{
		if(cutArea==null){
			throw new Exception("非法剪裁参数");
		}
        Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");  
        ImageReader reader = it.next();  
        ImageInputStream iis = ImageIO.createImageInputStream(pic);  
        reader.setInput(iis, true);  
        ImageReadParam param = reader.getDefaultReadParam();  
        Rectangle rect = new Rectangle(cutArea.getX(), cutArea.getY(), cutArea.getWidth(), cutArea.getHeight());  
        param.setSourceRegion(rect);  
        BufferedImage image = reader.read(0, param);  
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
		return new ByteArrayInputStream(os.toByteArray());
	}
	
	/**
	 * 图片压缩方法
	 * @author cangs
	 * @since hg-common
	 * @date 2016年4月26日下午4:30:02
	 * @param pic 图片流
	 * @param percent 压缩百分比
	 * @return 图片流
	 * @throws Exception
	 */
	public static InputStream compact(InputStream pic,int percent) throws Exception{
		if(percent>100||percent<1){
			throw new Exception("非法压缩比例");
		}
		ImageWriter imgWrier;
		ImageWriteParam imgWriteParams;
		ByteArrayOutputStream out = new ByteArrayOutputStream();  
		imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
		imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
		// 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
		imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		// 这里指定压缩的程度，参数qality是取值0~1范围内，
		imgWriteParams.setCompressionQuality((float)percent*(float) 0.01);
		imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
		ColorModel colorModel = ColorModel.getRGBdefault();
		// 指定压缩时使用的色彩模式
		imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));
		imgWrier.reset();
		// 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何 OutputStream构造
		imgWrier.setOutput(ImageIO.createImageOutputStream(out));  
        // 调用write方法，就可以向输入流写图片  
        imgWrier.write(null, new IIOImage(ImageIO.read(pic), null, null), imgWriteParams);  
        out.flush();  
        out.close(); 
		return new ByteArrayInputStream(out.toByteArray());
	}


	/**
	 * 上传图片
	 * @author cangs 
	 * @since hg-common
	 * @date 2016年4月26日下午4:30:02
	 * @param pic 图片流
	 * @param host fastdfs地址
	 * @return 图片url
	 * @throws Exception
	 */
	public static String uploadImage(InputStream pic,String host) throws Exception{
		//config本地保存路径
		String localPath = new File(PicUtils.class.getResource("/").getFile()).getCanonicalPath()+"/fastdfs_client_PicUtils.conf";
		//获取config模板
		URL url = Loader.getResource("fastdfs_client.conf");
		//开始替换模板
		BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(url.getPath()))));
		StringBuffer strBuffer = new StringBuffer();
		for (String temp = null; (temp = bufReader.readLine()) != null; temp = null) {
			if(temp.indexOf("{host:port}") != -1 ){ 
				temp = temp.replace("{host:port}", host+":22122");
			}
			strBuffer.append(temp);
			strBuffer.append(System.getProperty("line.separator"));
		}
		bufReader.close();
		//替换完成写入本地
		PrintWriter printWriter = new PrintWriter(localPath);
		printWriter.write(strBuffer.toString().toCharArray());
		printWriter.flush();
		printWriter.close();
		try {
			ClientGlobal.init(localPath);
		} catch (Exception e) {
			throw new Exception("上传环境初始化失败");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("author", "PicUtils");
		FdfsFileInfo fileinfo = FdfsFileUtil.upload((FileInputStream)pic,"jpg",map);
		return "http://"+fileinfo.getSourceIpAddr()+fileinfo.getUri();
	}
	
	/**
	 * 下载图片
	 * @author cangs
	 * @since hg-common
	 * @date 2016年4月26日下午4:30:02
	 * @param url 图片url
	 * @return 图片流
	 * @throws Exception
	 */
	public static InputStream downImage(String url) throws Exception{
		return new FileInputStream(DownloadUtil.saveToFile(url));
	}
}
