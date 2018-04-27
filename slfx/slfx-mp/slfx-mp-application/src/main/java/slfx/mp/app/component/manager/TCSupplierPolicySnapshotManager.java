package slfx.mp.app.component.manager;

import static slfx.mp.spi.common.MpEnumConstants.CacheKeyEnum.CACHE_KEY_NO_POLICE_SCENIC_SPOT_ID;
import static slfx.mp.spi.common.MpEnumConstants.CacheKeyEnum.CACHE_KEY_TC_SCENIC_SUPPLIER_POLICY;
import static slfx.mp.spi.common.MpEnumConstants.CacheKeyEnum.CACHE_KEY_TC_SUPPLIER_POLICY;
import hg.common.util.JSONUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import slfx.mp.domain.model.supplierpolicy.TCSupplierPolicySnapshot;


/**
 * 供应商（同程）政策列表缓存
 * 
 * @author zhurz
 */
@Component
public class TCSupplierPolicySnapshotManager extends RedisCacheManager {

	/**
	 * 清空所有供应商政策缓存
	 */
	public void clearAll() {
		Jedis jedis = getJedis();
		try {
			jedis.del(CACHE_KEY_TC_SCENIC_SUPPLIER_POLICY);
			jedis.del(CACHE_KEY_TC_SUPPLIER_POLICY);
			jedis.del(CACHE_KEY_NO_POLICE_SCENIC_SPOT_ID);
		} finally {
			returnResource(jedis);
		}
	}
	
	/**
	 * 根据供应商政策ID获取政策缓存
	 * 
	 * @param tcSupplierPolicyId
	 * @return
	 */
	public TCSupplierPolicySnapshot getTcSupplierPolicySnapshot(String tcSupplierPolicyId) {
		if (tcSupplierPolicyId == null)
			return null;
		Jedis jedis = getJedis();
		try {
			String json = jedis.hget(CACHE_KEY_TC_SUPPLIER_POLICY, tcSupplierPolicyId);
			TCSupplierPolicySnapshot snapshot = null;
			if (StringUtils.isNotBlank(json)) {
				snapshot = JSONUtils.p(json, TCSupplierPolicySnapshot.class);
			}
			return snapshot;
		} finally {
			returnResource(jedis);
		}
	}
	
	/**
	 * 保存快照到缓存
	 * 
	 * @param snapshot
	 */
	public void putTcSupplierPolicySnapshot(TCSupplierPolicySnapshot snapshot) {
		if (snapshot == null)
			return;
		Jedis jedis = getJedis();
		try {
			jedis.hset(CACHE_KEY_TC_SUPPLIER_POLICY, 
					String.valueOf(snapshot.getPolicyId()), JSONUtils.c(snapshot));
		} finally {
			returnResource(jedis);
		}
	}
	
	/**
	 * 根据供应商景区ID获取供应商政策列表
	 * 
	 * @param tcScenicId					供应商景点ID
	 * @return
	 */
	public List<TCSupplierPolicySnapshot> getTcSupplierPolicySnapshots(String tcScenicId) {
		if (tcScenicId == null)
			return new ArrayList<TCSupplierPolicySnapshot>();
		Jedis jedis = getJedis();
		try {
			String json = jedis.hget(CACHE_KEY_TC_SCENIC_SUPPLIER_POLICY, tcScenicId);
			List<TCSupplierPolicySnapshot> list = null;
			if (StringUtils.isNotBlank(json)) {
				list = JSONUtils.pa(json, TCSupplierPolicySnapshot.class);
			} else {
				list = new ArrayList<TCSupplierPolicySnapshot>();
			}
			return list;
		} finally {
			returnResource(jedis);
		}
	}
	
	/**
	 * @方法功能说明：设置没有价格政策的同程景区ID
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-13下午4:18:50
	 * @修改内容：
	 * @参数：@param tcScenicId
	 * @return:void
	 * @throws
	 */
	public void setNoPoliceTcScenicId(String tcScenicId){
		Jedis jedis = getJedis();
		try {
			jedis.sadd(CACHE_KEY_NO_POLICE_SCENIC_SPOT_ID, tcScenicId);
		} finally {
			returnResource(jedis);
		}
	}
	
	/**
	 * @方法功能说明：同程景区是否有价格政策
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-13下午4:20:06
	 * @修改内容：
	 * @参数：@param tcScenicId
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean isTcScenicNoPolice(String tcScenicId) {
		Jedis jedis = getJedis();
		try {
			return jedis.sismember(CACHE_KEY_NO_POLICE_SCENIC_SPOT_ID, tcScenicId);
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 刷新供应商政策缓存
	 * 
	 * @param tcSupplierPolicySnapshots			供应商政策缓存
	 */
	public void refresh(List<TCSupplierPolicySnapshot> tcSupplierPolicySnapshots) {
		if (tcSupplierPolicySnapshots == null) return;
		
		Map<String, List<TCSupplierPolicySnapshot>> map = new HashMap<String, List<TCSupplierPolicySnapshot>>();
		for(TCSupplierPolicySnapshot tcSupplierPolicySnapshot:tcSupplierPolicySnapshots){
			String tcScenicId = tcSupplierPolicySnapshot.getScenicSpotSnapshot().getTcScenicSpotsId();
			if (!map.containsKey(tcScenicId)) {
				map.put(tcScenicId, new ArrayList<TCSupplierPolicySnapshot>());
			}
			map.get(tcScenicId).add(tcSupplierPolicySnapshot);
		}

		Jedis jedis = getJedis();
		try {
			for (List<TCSupplierPolicySnapshot> list : map.values()) {
				String tcScenicId = list.get(0).getScenicSpotSnapshot().getTcScenicSpotsId();
				// 删除已经存在的供应商政策缓存
				List<TCSupplierPolicySnapshot> list2 = getTcSupplierPolicySnapshots(tcScenicId);
				for(TCSupplierPolicySnapshot snapshot:list2){
					jedis.hdel(CACHE_KEY_TC_SUPPLIER_POLICY, String.valueOf(snapshot.getPolicyId()));
				}
				// 保存新的政策入缓存
				for(TCSupplierPolicySnapshot snapshot:list){
					jedis.hset(CACHE_KEY_TC_SUPPLIER_POLICY, String.valueOf(snapshot.getPolicyId()), 
							JSONUtils.c(snapshot));
				}
				jedis.hset(CACHE_KEY_TC_SCENIC_SUPPLIER_POLICY, tcScenicId, JSONUtils.c(list));
				jedis.srem(CACHE_KEY_NO_POLICE_SCENIC_SPOT_ID, tcScenicId);
			}
		} finally {
			returnResource(jedis);
		}
	}
	
}
