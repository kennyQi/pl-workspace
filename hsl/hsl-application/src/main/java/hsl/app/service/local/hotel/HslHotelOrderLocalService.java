package hsl.app.service.local.hotel;


import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import hg.common.component.BaseServiceImpl;
import hsl.app.dao.hotel.HslHotelOrderDao;
import hsl.app.dao.hotel.OrderCancelDAO;
import hsl.domain.model.hotel.order.HotelOrder;
import hsl.pojo.qo.hotel.HotelOrderQO;
@Service
@Transactional
public class HslHotelOrderLocalService extends BaseServiceImpl<HotelOrder, HotelOrderQO, HslHotelOrderDao>{

	@Autowired
	private HslHotelOrderDao hslHotelOrderDAO;
	
	@Autowired
	private JedisPool jedisPool;

	@Override
	protected HslHotelOrderDao getDao() {
		return this.hslHotelOrderDAO;
	}
	



	/**
	 * @功能说明：从redis拿取序列号，也许该加个同步锁
	 * @修改者：
	 * @修改日期：
	 * @修改说明：
	 * @创建者 zhuxy
	 * @创建时间：2015年2月28日
	 */
	public int getOrderSequence() {
		Jedis jedis = null;
		try {

			jedis = jedisPool.getResource();
			String value = jedis.get("line_sequence");
			String date = jedis.get("line_sequence_date");
			if (StringUtils.isBlank(value)
					|| StringUtils.isBlank(date)
					|| !date.equals(String.valueOf(Calendar.getInstance()
							.getTime().getTime()))) {
				value = "0";
			}

			return Integer.parseInt(value);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	// 设置下一条订单流水号
	public String setNextOrderSequence() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			int value = this.getOrderSequence();

			if (value >= 9999) {
				value = 0;
			} else {
				value++;
			}

			jedis.set("line_sequence", String.valueOf(value));
			jedis.set("line_sequence_date",
					String.valueOf(Calendar.getInstance().getTime().getTime()));
		} finally {
			jedisPool.returnResource(jedis);
		}
		return null;
	}
	
	

}
