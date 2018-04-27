package hg.dzpw.admin.controller.test;

import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.common.util.file.SimpleFileUtil;
import hg.dzpw.app.component.event.DealerApiEventPublisher;
import hg.dzpw.app.component.manager.TicketOrderCacheManager;
import hg.dzpw.dealer.client.common.publish.PublishEventRequest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TicketOrderCacheManager ticketOrderManager;
	
	@Autowired
	private JedisPool jedisPool;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@ResponseBody
	@RequestMapping("/echo")
	public String echo(HttpServletRequest request) {
		
		String json = JSON.toJSONString(request.getParameterMap());
		System.out.println("回显--->>");
		System.out.println(json);
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping("/qrcode")
	public void qrCode(HttpServletResponse response){
		MultiFormatWriter mfw = new MultiFormatWriter();
		Map map = new HashMap();
		map.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); //容错率
		map.put(EncodeHintType.MARGIN, 1);  //二维码边框宽度，文档说设置0-4
		
		try {
			BitMatrix bm = mfw
					.encode("JX00060000000177#100523010457",
							BarcodeFormat.QR_CODE, 200, 200, map);
		     try {
		    	 
		    	 
		    	 BufferedImage bfImage = MatrixToImageWriter.toBufferedImage(bm);
		    	 
		    	String tempPath = SimpleFileUtil.getPathToRename("jpg");
		 		tempPath = tempPath.replace(File.separator + "temp", File.separator
		 				+ "webapps" + File.separator + "group0");
		    	 
		 		File file = new File(tempPath);
		 		
		    	 ImageIO.write(bfImage, "jpg", file);
		    	 FdfsFileUtil.init();
		    	 FdfsFileInfo info = FdfsFileUtil.upload(file, new HashMap<String, String>());
		    	 
		    	 System.out.println(info.getUri());
		    	 
		    	// 删除临时图片
		    	 file.delete();
		    	 
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	@RequestMapping("/pushorder")
	public void pushorder(){
		
		Jedis jedis = jedisPool.getResource();
		try {
//			if(jedis.exists("PUSH_ORDER_NO_MAP_TEST")){
//				
//				JSONObject o = new JSONObject();
//				o.put("orderId", "000001");
//				o.put("time", new Date());
//				jedis.hset("PUSH_ORDER_NO_MAP_TEST", "A0001", o.toString());
//			}else{
//				Map<String, String> map = new HashMap<String, String>();
//				JSONObject o = new JSONObject();
//				o.put("orderId", "000002");
//				o.put("time", new Date());
//				map.put("A0002", o.toString());
//				jedis.hmset("PUSH_ORDER_NO_MAP_TEST", map);
//			}
			
//			jedis.hdel("PUSH_ORDER_NO_MAP_TEST",  "A0001");
//			jedis.hdel("PUSH_ORDER_NO_MAP_TEST",  "A0002");
//			String s = jedis.hget("PUSH_ORDER_NO_MAP_TEST", "A0001");
//			
			JSONObject o = JSON.parseObject(jedis.hget("PUSH_ORDER_NO_MAP_TEST", "A0001"));
			
			System.out.println(o.get("time"));
			System.out.println(JSON.parse(jedis.hget("PUSH_ORDER_NO_MAP_TEST", "A0001")));
			
			System.out.println(jedis.hexists("PUSH_ORDER_NO_MAP_TEST", "A0002"));
			System.out.println(jedis.hexists("PUSH_ORDER_NO_MAP_TEST", "A0001"));
			
			
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		}
		
//		ticketOrderManager.setPushOrder("123456790");
//		ticketOrderManager.setPushOrder("123456790");
//		ticketOrderManager.setPushOrder("123456791");
		
		Map<String, String> map = ticketOrderManager.getPushOrder();
		
		Date now = new Date();
		List<String> list = new ArrayList<String>();		
		
		if (map!=null){
			Iterator it = map.keySet().iterator();
			// 遍历记录更新的时间
			while(it.hasNext()){
				// key为订单号
				String key = (String)it.next();
				try {
					// 时间差 --毫秒
					Long m = now.getTime() - sdf.parse(map.get(key)).getTime();
					// 10分钟内 则推送
					if (m>=0 && m<=600000){
						
						// 待删除
						list.add(key);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		ticketOrderManager.delPushOrderRecord(list, now);
	}
	
	
    private BitMatrix deleteWhite(BitMatrix matrix){  
        int[] rec = matrix.getEnclosingRectangle();  
        int resWidth = rec[2] + 5;  
        int resHeight = rec[3] + 5;
        
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);  
        resMatrix.clear();  
        for (int i = 0; i < resWidth; i++) {  
            for (int j = 0; j < resHeight; j++) {  
                if (matrix.get(i + rec[0], j + rec[1]))  
                    resMatrix.set(i, j);  
            }  
        }  
        return resMatrix;  
    }  
	
	public static void main(String[] args) {
//		Date d = DateUtils.ceiling(new Date(), );
		Date d = new Date();
		Date dd = DateUtils.truncate(d, Calendar.DAY_OF_MONTH);
		System.out.println(DateUtils.truncate(d, Calendar.DAY_OF_MONTH));
		System.out.println(DateUtils.truncate(d, Calendar.HOUR_OF_DAY));
		
		String x = "-1";
		Integer.parseInt(x);
		System.out.println(x);
		
	}
}
