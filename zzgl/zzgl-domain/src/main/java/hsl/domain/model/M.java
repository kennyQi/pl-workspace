package hsl.domain.model;
import hg.common.component.hibernate.dialect.ColumnDefinitionMysql;

public class M implements ColumnDefinitionMysql {
	
	/**
	 * 平台用户表前缀
	 */
	 public static final String TABLE_PREFIX_HSL_USER="U_";
	 /**
	  * 平台门票表前缀
	  */
	 public static final String TABLE_PREFIX_HSL_MP="MP_";
	 /**
	  * 平台企业前缀
	 */
	public static final String TABLE_PREFIX_HSL_COMPANY="C_";
	/**
	  * 平台卡券前缀
	 */
	public static final String TABLE_PREFIX_HSL_COUPON="KQ_";
	/**
	 * 平台广告前缀
	 */
	public static final String TABLE_PREFIX_HSL_AD="AD_";
	/**
	 * 平台线路前缀
	 */
	public static final String TABLE_PREFIX_HSL_XL="XL_";
	/**
	 * 平台线路前缀
	 */
	public static final String TABLE_PREFIX_HSL_JD="JD_";
	/**
	 * 平台线路前缀
	 */
	public static final String TABLE_PREFIX_HSL_JP="JP_";
	/**
	 * 帐号余额前缀
	 */
	public static final String TABLE_PREFIX_HSL_ACCOUNT="ACCOUNT_";
	/**
	 * 线路销售方案前缀（团购和秒杀）
	 */
	public static final String TABLE_PREFIX_LSP="LSP_";
}
