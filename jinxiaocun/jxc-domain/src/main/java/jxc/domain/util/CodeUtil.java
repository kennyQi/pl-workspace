package jxc.domain.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import hg.common.config.SysConfigCacheManager;
import hg.common.util.SpringContextUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 编码生成规则
 * 
 * @author Administrator
 * 
 */
public class CodeUtil {

	private static JedisPool jedisPool = null;

	// 商品编号及编码
	private static Integer PRODUCT_NUMBER = 0;
	private static final String PRODUCT_CODE = "S";
	// 品牌编号及编码
	private static Integer BRAND_NUMBER = 0;
	private static final String BRAND_CODE = "B";
	// 分类编号及编码
	private static Integer CATEGORY_NUMBER = 0;
	private static final String CATEGORY_CODE = "C";
	// 规格编号及编码
	private static Integer SPECIFICATION_NUMBER = 0;
	private static final String SPECIFICATION_CODE = "A";
	// 规格值编号
	private static Integer SPECVALUE_NUMBER = 0;

	// 仓库编号及编码
	private static Integer WAREHOUSE_NUMBER = 0;
	private static final String WAREHOUSE_CODE = "W";

	// 采购单编号及编码
	private static Integer PURCHASE_ORDER_NUMBER = 0;
	private static final String PURCHASE_ORDER_CODE = "CG";

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	public static String createProductCode() {
		PRODUCT_NUMBER = getJedis("productNumber");
		PRODUCT_NUMBER++;
		setJedis("productNumber", PRODUCT_NUMBER);
		String productCode = PRODUCT_CODE + createNumber(PRODUCT_NUMBER, 6);
		return productCode;
	}

	public static String createBrandCode() {
		BRAND_NUMBER = getJedis("brandNumber");
		BRAND_NUMBER++;
		setJedis("brandNumber", BRAND_NUMBER);
		String brandCode = BRAND_CODE + createNumber(BRAND_NUMBER, 3);
		return brandCode;
	}

	public static String createCategoryCode() {
		CATEGORY_NUMBER = getJedis("categoryNumber");
		CATEGORY_NUMBER++;
		setJedis("categoryNumber", CATEGORY_NUMBER);
		String categoryCode = CATEGORY_CODE + createNumber(CATEGORY_NUMBER, 3);
		return categoryCode;
	}

	public static String createSpecificationCode() {
		SPECIFICATION_NUMBER = getJedis("specificationNumber");
		SPECIFICATION_NUMBER++;
		setJedis("specificationNumber", SPECIFICATION_NUMBER);
		String specificationCode = SPECIFICATION_CODE + createNumber(SPECIFICATION_NUMBER, 3);
		return specificationCode;
	}

	public static String createSpecValueCode() {
		SPECVALUE_NUMBER = getJedis("specvalueNumber");
		SPECVALUE_NUMBER++;
		setJedis("specvalueNumber", SPECVALUE_NUMBER);
		String specValueCode = createNumber(SPECVALUE_NUMBER, 6);
		return specValueCode;
	}

	public static String createSkuProductCode(String productCode, String... specValues) {
		StringBuffer skuProductCode = new StringBuffer();
		skuProductCode.append(productCode);
		for (int i = 0; i < specValues.length; i++) {
			skuProductCode.append(specValues[i]);
		}
		return skuProductCode.toString();
	}

	public static String createWarehouseCode() {
		WAREHOUSE_NUMBER = getJedis("warehouseNumber");
		WAREHOUSE_NUMBER++;
		setJedis("warehouseNumber", WAREHOUSE_NUMBER);
		String warehouseCode = WAREHOUSE_CODE + createNumber(WAREHOUSE_NUMBER, 3);
		return warehouseCode;
	}

	public static String createPurchaseOrderCode() {
		PURCHASE_ORDER_NUMBER = getJedis("purchaseOrderNumber");
		PURCHASE_ORDER_NUMBER++;
		setJedis("purchaseOrderNumber", PURCHASE_ORDER_NUMBER);

		
		String warehouseCode = PURCHASE_ORDER_CODE + dateFormat.format(new Date()) + createNumber(PURCHASE_ORDER_NUMBER, 6);
		return warehouseCode;
	}

	private static String createNumber(Integer num, int len) {
		StringBuffer returnStr = new StringBuffer();
		for (int i = 0; i < len - num.toString().length(); i++) {
			returnStr.append("0");
		}
		return returnStr.append(num.toString()).toString();
	}

	private static void setJedis(String key, Integer value) {
		jedisPool = getJedisPool();
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value.toString());
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	private static Integer getJedis(String key) {
		jedisPool = getJedisPool();
		Jedis jedis = jedisPool.getResource();
		Integer num = 0;
		try {
			num = Integer.valueOf(jedis.get(key));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return num;
	}

	private static JedisPool getJedisPool() {
		if (jedisPool == null) {
			SysConfigCacheManager manager = SpringContextUtil.getApplicationContext().getBean(SysConfigCacheManager.class);
			jedisPool = manager.getJedisPool();
		}
		return jedisPool;
	}
}
