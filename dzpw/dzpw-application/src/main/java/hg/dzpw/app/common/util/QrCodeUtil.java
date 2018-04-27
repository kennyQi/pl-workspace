package hg.dzpw.app.common.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @类功能说明：生成二维码工具类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-3-24下午2:26:56
 * @版本：V1.0
 */
public class QrCodeUtil {
	
	private static MultiFormatWriter mfw = new MultiFormatWriter();
	
	private static Map map = new HashMap();
	
	/** 二维码图片类型 */
	public static String IMAGE_TYPE = "jpg";
	
	static{
		map.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); //容错率
		map.put(EncodeHintType.MARGIN, 0);  //二维码边框宽度，文档说设置0-4
	}
	
	public static BitMatrix getBitMatrix(String content){
		try {
			BitMatrix bm = mfw.encode(content, BarcodeFormat.QR_CODE, 200, 200, map);
			return bm;
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
}
