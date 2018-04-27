package hsl.pojo.util;

import hg.log.util.HgLogger;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @类功能说明：流水号Util
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/18 9:40
 */
public class SequenceUtil {
	public int getOrderSequence(JedisPool jedisPool,String sequenceName){
		Jedis jedis = null;
		String value;
		int v=0;
		try {
			jedis = jedisPool.getResource();
			value = jedis.get(sequenceName);
			String date = jedis.get(sequenceName+"_date");

			if (StringUtils.isBlank(value) || StringUtils.isBlank(date)
					|| date.equals(String.valueOf(this.getDateString("yyyyMMdd")))) {
				value = "0";
			}
			v = Integer.parseInt(value);
			if (v >= 9999) {
				v = 0;
			}
			v++;
			jedis.set(sequenceName, String.valueOf(v));
			jedis.set(sequenceName+"_date", String.valueOf(this.getDateString("yyyyMMdd")));
		}catch (Exception e){
			HgLogger.getInstance().error("chenxy","创建订单流水号出错"+HgLogger.getStackTrace(e));
			e.printStackTrace();
		}finally {
			jedisPool.returnResource(jedis);
		}
		return v;
	}
	private String getDateString(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(Calendar.getInstance().getTime());
	}
}
