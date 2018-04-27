package pic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;

public class test {

	public static InputStream waterMark(InputStream pic,InputStream watermark) throws Exception{
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
		//绘制水印
		g.drawImage(mark, (int)Math.ceil((wideth - wideth_watermark) *0.9), (int)Math.ceil((height - height_watermark) *0.9), wideth_watermark, height_watermark, null);
		//结束绘制
		g.dispose();
		//返回inputstream流
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", os);
		return new ByteArrayInputStream(os.toByteArray());
	}
	
	public static InputStream waterMark(InputStream pic,String watermark) throws Exception{
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
        //绘制水印
        g.drawString(watermark, (int)Math.ceil((wideth - size) *(60-watermark.length())/100),(int)Math.ceil((height - size) *0.9));  
        //结束绘制
        g.dispose();  
		//返回inputstream流
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", os);
		return new ByteArrayInputStream(os.toByteArray());
	}
	
	public static InputStream cut(InputStream pic,CutArea cutArea) throws Exception{
		/*//读取待水印图片
		Image background = ImageIO.read(pic);
		//图片宽度
		int wideth = background.getWidth(null);
		//图片长度
		int height = background.getHeight(null);*/
		/*if(cutArea.getX()+cutArea.getWidth()>wideth||cutArea.getY()+cutArea.getHeight()>height){
			throw new Exception("剪裁区域超出图片大小");
		}*/
		Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("jpg");   
        ImageReader reader = (ImageReader)iterator.next();   
        ImageInputStream iis = ImageIO.createImageInputStream(pic);   
        reader.setInput(iis, true);   
        ImageReadParam param = reader.getDefaultReadParam();   
        Rectangle rect = new Rectangle(cutArea.getX(), cutArea.getY(), cutArea.getWidth(), cutArea.getHeight());   
        param.setSourceRegion(rect);   
        BufferedImage bi = reader.read(0,param);     
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", os);
		return new ByteArrayInputStream(os.toByteArray());          
		
		
		
		
		
		
		
/*		
        Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");  
        ImageReader reader = it.next();  
        ImageInputStream iis = ImageIO.createImageInputStream(pic);  
        reader.setInput(iis, true);  
        ImageReadParam param = reader.getDefaultReadParam();  
//        Rectangle rect = new Rectangle(cutArea.getX(), cutArea.getY(), cutArea.getWidth(), cutArea.getHeight());
        Rectangle rect = new Rectangle(20,20,100,100);  
        param.setSourceRegion(rect);  
        BufferedImage image = reader.read(0, param);  
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
		return new ByteArrayInputStream(os.toByteArray());*/
	}
	
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
	
	public static void main(String[] args) {
		File pic = new File("F:/pic.jpg");
		try {
			//压缩
			/*InputStream result = compact(new FileInputStream(pic),1);
			byte[] data = readInputStream(result);  
	        File imageFile = new File("f:/waterMarkZip.jpg");  
	        FileOutputStream outStream = new FileOutputStream(imageFile);  
	        outStream.write(data);  
	        outStream.close();*/
			//剪裁
			CutArea cutArea = new CutArea();
			cutArea.setX(100);
			cutArea.setY(100);
			cutArea.setHeight(1000);
			cutArea.setWidth(1000);
			InputStream result = cut(new FileInputStream(pic),cutArea);
			byte[] data = readInputStream(result);  
	        File imageFile = new File("f:/cutPic.jpg");  
	        FileOutputStream outStream = new FileOutputStream(imageFile);  
	        outStream.write(data);  
	        outStream.close();
	        //图片水印
	        /*File watermark = new File("F:/watermark.png");
			InputStream result = waterMark(new FileInputStream(pic), new FileInputStream(watermark));
			byte[] data = readInputStream(result);  
	        File imageFile = new File("f:/waterMarkPic.jpg");  
	        FileOutputStream outStream = new FileOutputStream(imageFile);  
	        outStream.write(data);  
	        outStream.close();*/
	        //文字水印 
	        /*InputStream result1=waterMark(new FileInputStream(pic), "1");
	        byte[] data1 = readInputStream(result1); 
	        File imageFile1 = new File("f:/waterMarkString.jpg");  
	        FileOutputStream outStream1 = new FileOutputStream(imageFile1);  
	        outStream1.write(data1);  
	        outStream1.close();  */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
    }  
}
