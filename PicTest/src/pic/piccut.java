package pic;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class piccut {
	 /* 
     * 图片裁剪通用接口 
     */  
  
    public static void cutImage(InputStream pic,String dest,int x,int y,int w,int h) throws IOException{   
           Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");   
           ImageReader reader = (ImageReader)iterator.next();   
           ImageInputStream iis = ImageIO.createImageInputStream(pic);   
           reader.setInput(iis, true);   
           ImageReadParam param = reader.getDefaultReadParam();   
           Rectangle rect = new Rectangle(x, y, w,h);    
           param.setSourceRegion(rect);   
           BufferedImage bi = reader.read(0,param);     
           ImageIO.write(bi, "jpg", new File(dest));             
  
    }   
    public static void main(String[] args) {
    	File pic = new File("F:/pic.jpg");
    	try {
    		cutImage(new FileInputStream(pic),"F:/pic1.jpg",1,1,20,20);
    	}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
