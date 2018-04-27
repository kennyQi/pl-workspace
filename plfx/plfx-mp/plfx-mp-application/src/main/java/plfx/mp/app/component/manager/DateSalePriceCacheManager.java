package plfx.mp.app.component.manager;

import static plfx.mp.spi.common.MpEnumConstants.CacheKeyEnum.CACHE_KEY_DATE_SALE_PRICE;
import hg.common.util.JSONUtils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import plfx.mp.domain.model.platformpolicy.DateSalePrice;
import redis.clients.jedis.Jedis;

/**
 * 价格日历缓存 供应商政策ID_经销商ID:价格日历
 * 
 * @author zhurz
 */
@Component
public class DateSalePriceCacheManager extends RedisCacheManager {

	/**
	 * 获取缓存KEY
	 * 
	 * @param supplierPolicyId			供应商政策ID
	 * @param dealerId					经销商ID
	 * @return
	 */
	protected String getKey(String supplierPolicyId, String dealerId){
		return supplierPolicyId + "_" + dealerId;
	}

	/**
	 * 清空所有价格日历
	 */
	public void clearAll() {
		Jedis jedis = getJedis();
		try {
			jedis.del(CACHE_KEY_DATE_SALE_PRICE);
		} finally {
			returnResource(jedis);
		}
	}
	
	/**
	 * 清除政策快照下的价格日历
	 * 
	 * @param supplierPolicyId			供应商政策ID
	 * @param dealerId					经销商ID
	 */
	public void clear(String supplierPolicyId, String dealerId) {
		if (supplierPolicyId == null || dealerId == null) return;
		Jedis jedis = getJedis();
		try {
			jedis.hdel(CACHE_KEY_DATE_SALE_PRICE, getKey(supplierPolicyId, dealerId));
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * @方法功能说明：刷新价格日历
	 * @修改者名字：zhurz
	 * @修改时间：2014-8-25下午3:17:07
	 * @修改内容：
	 * @参数：@param dateSalePrices			价格日历
	 * @参数：@param supplierPolicyId			供应商政策ID
	 * @参数：@param dealerId					经销商ID
	 * @return:void
	 * @throws
	 */
	public void refresh(List<DateSalePrice> dateSalePrices, String supplierPolicyId, String dealerId) {
		if (dateSalePrices == null) return;
		String json = JSONUtils.c(dateSalePrices);
		Jedis jedis = getJedis();
		try {
			jedis.hdel(CACHE_KEY_DATE_SALE_PRICE, getKey(supplierPolicyId, dealerId));
			jedis.hset(CACHE_KEY_DATE_SALE_PRICE, getKey(supplierPolicyId, dealerId), json);
		} finally {
			returnResource(jedis);
		}
	}
	
	/**
	 * 根据供应商政策ID获取价格日历
	 * 
	 * @param supplierPolicyId			供应商政策ID
	 * @param dealerId					经销商ID
	 * @return
	 */
	public List<DateSalePrice> getDateSalePrices(String supplierPolicyId, String dealerId) {
		List<DateSalePrice> dateSalePrices = new ArrayList<DateSalePrice>();
		if (supplierPolicyId == null || dealerId == null) return dateSalePrices;
		Jedis jedis = getJedis();
		try {
			String json = jedis.hget(CACHE_KEY_DATE_SALE_PRICE, getKey(supplierPolicyId, dealerId));
			if (json != null && json.length() > 0) {
				dateSalePrices = JSONUtils.pa(json, DateSalePrice.class);
			}
			return dateSalePrices;
		} finally {
			returnResource(jedis);
		}
	}
}
