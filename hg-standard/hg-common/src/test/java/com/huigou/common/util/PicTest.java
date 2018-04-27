package com.huigou.common.util;

import hg.common.util.CutArea;
import hg.common.util.PicUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.junit.Test;

public class PicTest {

	
	/**
	 * 
	 * @方法功能说明：图片上传测试
	 * @修改者名字：cangs
	 * @修改时间：2016年4月27日下午2:53:37
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	//@Test
	public void upload(){
		File pic = new File("src/test/pic/pic.jpg");
		try {
			System.out.println(PicUtils.uploadImage(new FileInputStream(pic),"192.168.2.214"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @方法功能说明：下载测试
	 * @修改者名字：cangs
	 * @修改时间：2016年4月27日下午3:34:54
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	//@Test
	public void down(){
		try {
		File outFile = new File("src/test/pic/downPic.jpg");
		FileInputStream inStream = (FileInputStream) PicUtils.downImage("http://192.168.2.214/group1/M00/00/00/wKgC1lcgbDSIVY-aABFeO-k4YUEAAAATQJeZJgAEV5T717.jpg");
		FileOutputStream outStream = new FileOutputStream(outFile);
		byte[] inOutb = new byte[inStream.available()];
		inStream.read(inOutb);  
		outStream.write(inOutb);  
		inStream.close();
		outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @方法功能说明：图片压缩测试
	 * @修改者名字：cangs
	 * @修改时间：2016年4月26日下午4:29:42
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	@Test
	public void compactTest() {
		File pic = new File("src/test/pic/pic.jpg");
		try {
			// 压缩
			InputStream result = PicUtils.compact(new FileInputStream(pic), 99);
			byte[] data = readInputStream(result);
			File imageFile = new File("src/test/pic/waterMarkZip.jpg");
			FileOutputStream outStream = new FileOutputStream(imageFile);
			outStream.write(data);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @方法功能说明：图片剪裁测试
	 * @修改者名字：cangs
	 * @修改时间：2016年4月26日下午4:29:54
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	@Test
	public void cutTest() {
		File pic = new File("src/test/pic/pic.jpg");
		try {
			// 剪裁
			CutArea cutArea = new CutArea();
			cutArea.setX(100);
			cutArea.setY(100);
			cutArea.setHeight(100);
			cutArea.setWidth(100);
			InputStream result = PicUtils.cut(new FileInputStream(pic), cutArea);
			byte[] data = readInputStream(result);
			File imageFile = new File("src/test/pic/cutPic.jpg");
			FileOutputStream outStream = new FileOutputStream(imageFile);
			outStream.write(data);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @方法功能说明：水印测试
	 * @修改者名字：cangs
	 * @修改时间：2016年4月26日下午4:30:02
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	@Test
	public void watermarkTest() {
		File pic = new File("src/test/pic/pic.jpg");
		try {
			// 图片水印
			//单个5个模式测试
			File watermark = new File("src/test/pic/111111.png");
			for (int i = 1; i < 6; i++) {
				InputStream result = PicUtils.waterMark(new FileInputStream(pic),
						new FileInputStream(watermark),i);
				byte[] data = readInputStream(result);
				File imageFile = new File("src/test/pic/waterMarkPic"+i+".jpg");
				FileOutputStream outStream = new FileOutputStream(imageFile);
				outStream.write(data);
				outStream.close();
			}
			//平铺3个模式测试
			for (int i = 1; i < 4; i++) {
				InputStream result = PicUtils.waterMarkTile(new FileInputStream(pic),
						new FileInputStream(watermark),i);
				byte[] data = readInputStream(result);
				File imageFile = new File("src/test/pic/waterMarkPic"+i+".jpg");
				FileOutputStream outStream = new FileOutputStream(imageFile);
				outStream.write(data);
				outStream.close();
			}
			// 文字水印
			//单个5个模式测试
			for (int i = 1; i < 6; i++) {
				InputStream result1 = PicUtils.waterMark(new FileInputStream(pic),
						"String",i);
				byte[] data1 = readInputStream(result1);
				File imageFile1 = new File("src/test/pic/waterMarkString"+i+".jpg");
				FileOutputStream outStream1 = new FileOutputStream(imageFile1);
				outStream1.write(data1);
				outStream1.close();
			}
			//平铺3个模式测试
			for (int i = 1; i < 4; i++) {
				InputStream result1 = PicUtils.waterMarkTile(new FileInputStream(pic),
						"String",i);
				byte[] data1 = readInputStream(result1);
				File imageFile1 = new File("src/test/pic/waterMarkString"+i+".jpg");
				FileOutputStream outStream1 = new FileOutputStream(imageFile1);
				outStream1.write(data1);
				outStream1.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}
}
