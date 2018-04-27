package slfx.mp.app.common.util;

import hg.common.util.DateUtil;
import hg.common.util.SpringContextUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.context.ApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class OrderNumberUtils {

	public final static SimpleDateFormat DATE_FORMAT(){return new SimpleDateFormat("yyyyMMddHHmmssSSS");}

	public final static SimpleDateFormat DATE_TIME_FORMAT(){return new SimpleDateFormat("ddHHmmss");}
	
	private static JedisPool jedisPool;
	

	/**
	 * @方法功能说明：检查JEDIS池
	 * @修改者名字：zhurz
	 * @修改时间：2014-9-1下午5:14:19
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	private static void checkJedis() {
		if (jedisPool == null) {
			ApplicationContext ctx = SpringContextUtil.getApplicationContext();
			if (ctx == null)
				throw new RuntimeException("SPRING尚未初始化");
			jedisPool = ctx.getBean(JedisPool.class);
		}
		if (jedisPool == null)
			throw new RuntimeException("Jedis池未在SPRING上下文中获取");
		
	}

	// 订单编号	         时间戳＋订单来源＋订单类型＋流水号，订单编号长度16位；
	// 时间戳年份按照A-Z取一位，月份取一位16进制数字，日期2位数字；时间精确到秒；
	// 订单来源0来自移动端，1来自pc端；订单类型0机票，1门票，2线路，3酒店；最后是流水号；
	// 分销平台订单编号	分销商id＋订单编号
	
	/**
	 * @方法功能说明：创建门票分销平台订单号
	 * @修改者名字：zhurz
	 * @修改时间：2014-9-1下午5:28:56
	 * @修改内容：
	 * @参数：@param dealerId					供应商ID
	 * @参数：@param sourceType				来源
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String createMPPlatformOrderCode(String dealerId, String sourceType) {
		checkJedis();
		StringBuilder sb = new StringBuilder();
		sb.append(dealerId);
		sb.append(parseDate(new Date()));
		sb.append(sourceType);
		sb.append(OrderType.MP);
		// 从REDIS获取自增ID
		Jedis jedis = jedisPool.getResource();
		Long incrId = jedis.incr("MPPlatformOrder" + sb.toString());
		// 设置自增长ID过期时间
		jedis.expire("MPPlatformOrder" + sb.toString(), 60);
		jedisPool.returnResource(jedis);
		sb.append(String.format("%04d", incrId));
		return sb.toString();
	}

	/**
	 * @方法功能说明：将日期转为订单编号需要格式的时间戳
	 * @修改者名字：zhurz
	 * @修改时间：2014-9-1下午4:53:46
	 * @修改内容：
	 * @参数：@param date
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	protected static String parseDate(Date date) {
		if (date == null)
			date = new Date();
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		int year = c.get(Calendar.YEAR);
		if (year < 2014)
			throw new RuntimeException("订单号年份必须从2014年开始");
		if (year > 2039)
			throw new RuntimeException("订单号最大年份为2039年");
		
		StringBuilder sb = new StringBuilder();

		sb.append((char) ('A' + (year - 2014)));
		sb.append(Integer.toHexString(c.get(Calendar.MONTH) + 1).toUpperCase());
		sb.append(DATE_TIME_FORMAT().format(date));
		
		return sb.toString();
	}
	
	/**
	 * 生成订单流水号
	 * 
	 * @return
	 */
	public static String createOrderNo() {
		StringBuilder sb = new StringBuilder(DATE_FORMAT().format(new Date()));
		for (int i = 0; i < 13; i++) {
			sb.append(RandomUtils.nextInt(10));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
//		for (int i = 0; i < 13; i++) {
//			System.out.println(createOrderNo());
//		}
		Date date = DateUtil.parseDateTime("2014-11-30 19:33:22", "yyyy-MM-dd HH:mm:ss");
		System.out.println(parseDate(date));
//		System.out.println(String.format("%04d", 4l));
	}
	
	
	
	
	
	
	/**
	 * @类功能说明：订单来源
	 * @类修改者：
	 * @修改日期：2014-9-1下午4:35:33
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：zhurz
	 * @创建时间：2014-9-1下午4:35:33
	 */
	public static class SourceType {
		/** 移动端 */
		public final static String MOBILE = "0";
		/** PC端 */
		public final static String PC = "1";
	}
	
	/**
	 * @类功能说明：订单类型
	 * @类修改者：
	 * @修改日期：2014-9-1下午4:35:42
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：zhurz
	 * @创建时间：2014-9-1下午4:35:42
	 */
	public static class OrderType {
		/** 机票 */
		public final static String JP = "0";
		/** 门票 */
		public final static String MP = "1";
		/** 线路 */
		public final static String XL = "2";
		/** 酒店 */
		public final static String JD = "3";
	}

}
