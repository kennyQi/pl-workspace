package hg.fx.util;

import hg.fx.springbean.SpringContextUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

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

	
	
	private static Integer MILE_ORDER_FLOW_CODE_NUM = 0;
	private static final String MILE_ORDER_FLOW_CODE_NUM_NAME = "mileOderFlowCodeNum";

	public static String getMileOrderFlowCode(String distributorCode) {
		String dateCode = new SimpleDateFormat("yyyyMMdd").format(new Date());
		MILE_ORDER_FLOW_CODE_NUM = getJedis(MILE_ORDER_FLOW_CODE_NUM_NAME);
		MILE_ORDER_FLOW_CODE_NUM++;
		setJedis(MILE_ORDER_FLOW_CODE_NUM_NAME, MILE_ORDER_FLOW_CODE_NUM);
		//非常重要！这个code会被做为 发送给南航的流水号，最长为20位！
		String code = "HG" + dateCode + createNumber(MILE_ORDER_FLOW_CODE_NUM, 10);
		return code;
	}
	
	private static Integer DISTRIBUTOR_CODE_NUM = 0;
	private static final String DISTRIBUTOR_CODE_NUM_NAME  = "distributorCodeCount";
	
	public static String getDistributorCode() throws LcfxException {
		//String dateCode = new SimpleDateFormat("yyyyMMdd").format(new Date());
		DISTRIBUTOR_CODE_NUM = getJedis(DISTRIBUTOR_CODE_NUM_NAME);
		DISTRIBUTOR_CODE_NUM++;
		setJedis(DISTRIBUTOR_CODE_NUM_NAME, DISTRIBUTOR_CODE_NUM);
		String code = num2Code(DISTRIBUTOR_CODE_NUM.intValue());
		return code;
	}

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
		final String string = value.toString();
		setJedis(key, string);
	}

	/**
	 * @param key
	 * @param string
	 */
	public static void setJedis(String key, final String string) {
		jedisPool = getJedisPool();
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, string);
		} catch (Exception e) {
//			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace(System.out);
		} finally {
//			jedisPool.returnResource(jedis);
			jedis.close();
		}
	}
 
	/**
	 * 
	 * @param key
	 * @param string
	 * @param expireDays 多少天后失效
	 */
	public static void setJedis(String key, final String string,int expireDays) {
		jedisPool = getJedisPool();
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, string);
			jedis.expire(key.getBytes(), expireDays * 24*60*60);
		} catch (Exception e) {
//			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace(System.out);
		} finally {
//			jedisPool.returnResource(jedis);
			jedis.close();
		}
	}
	public static Integer getJedis(String key) {
		String s = getJedisString(key);
		Integer num = 0;
		if(s!=null)
			num = Integer.valueOf(s);
		return num;
	}

	public static void removeJedis(String key){
		jedisPool = getJedisPool();
		Jedis jedis = jedisPool.getResource();
		try {
			 jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			try {
				jedis.close();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
	}
	
	/**
	 * @param key
	 * @return
	 */
	public static String getJedisString(String key) {
		jedisPool = getJedisPool();
		Jedis jedis = jedisPool.getResource();
		String s = "0";
		try {
			 s = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			try {
				jedis.close();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
		return s;
	}

	private static JedisPool getJedisPool() {
		if (jedisPool == null) {
			jedisPool = SpringContextUtil.getApplicationContext().getBean(JedisPool.class);
		}
		return jedisPool;
	}
	
	private static String num2Code(int val) throws LcfxException {
		if (val>1295) {
			throw new LcfxException(null,"分销商数量已达到上限");
		}
		Character c1 = null;
		Character c2 = null;
		int i = val % 36;
		if (i < 10) {
			c1 = String.valueOf(i).toCharArray()[0];
		} else {
			c1 = (char) (i + 96 - 9);
		}

		if (val > 35) {
			val = val / 36;
			if (val < 10) {
				c2 = String.valueOf(val).toCharArray()[0];
			} else {
				c2 = (char) (val + 96 - 9);
			}
		} else {
			c2 = '0';
		}
		return c2.toString() + c1.toString();

	}

}
