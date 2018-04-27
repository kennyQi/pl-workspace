package com.huigou.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Test;
import hg.common.util.QRCodeUtil;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016年4月26日下午4:34:32
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chaoyuhui
 * @创建时间：2016年4月26日下午4:34:32
 */
public class TestQRCodeService {
	
		
    /**
     * @方法功能说明：生成二维码
     * @修改者名字：chaoyuhui
     * @修改时间：2016年4月26日下午4:44:12
     * @修改内容：
     * @参数：@throws Exception
     * @return:void
     * @throws
     */
	@Test
    public void encode() throws Exception {
    	//二维码内容
		String text = "生成二维码";
		//LOGO路径
		String imagesPath = "test/pic/logo.jpg";
		//生成二维码路径
		String destPath = "test/pic/logo_qrcode.jpg";
    	OutputStream output = new FileOutputStream(destPath);
        QRCodeUtil.encode(text, imagesPath, output, true);
        output.close();
    }
    
    /**
     * @方法功能说明：识别二维码
     * @修改者名字：chaoyuhui
     * @修改时间：2016年4月26日下午5:35:31
     * @修改内容：
     * @参数：@throws Exception
     * @return:void
     * @throws
     */
	@Test
    public void decode() throws Exception {
		//只包含二维码
    	String destPath1 = "test/pic/logo_qrcode.jpg";
    	//二维码居中，包含文字
    	String destPath2 = "test/pic/center.jpg";
    	//二维码居右，左边文字
    	String destPath3 = "test/pic/right.jpg";
    	//二维码居左，右边文字
    	String destPath4 = "test/pic/left.jpg";
    	
    	InputStream input1 = new FileInputStream(destPath1);
    	InputStream input2 = new FileInputStream(destPath2);
    	InputStream input3 = new FileInputStream(destPath3);
    	InputStream input4 = new FileInputStream(destPath4);
    	System.out.println(QRCodeUtil.decode(input1));
    	System.out.println(QRCodeUtil.decode(input2));
    	System.out.println(QRCodeUtil.decode(input3));
    	System.out.println(QRCodeUtil.decode(input4));
    	input1.close();
    	input2.close();
    	input3.close();
    	input4.close();
    }
}
