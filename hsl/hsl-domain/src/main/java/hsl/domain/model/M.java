package hsl.domain.model;

import hg.common.component.BaseModel;
import hg.common.component.hibernate.dialect.ColumnDefinitionMysql;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

public class M implements ColumnDefinitionMysql {
	
	/**
	 * 平台用户表前缀
	 */
	public static final String TABLE_PREFIX_HSL_USER = "U_";
	/**
	 * 平台易行机票表前缀
	 */
	public static final String TABLE_PREFIX_HSL_YJ = "YJ_";
	/**
	 * 平台门票表前缀
	 */
	public static final String TABLE_PREFIX_HSL_MP = "MP_";
	/**
	 * 电子票
	 */
	public static final String TABLE_PREFIX_HSL_DZP = "DZP_";
	/**
	 * 平台企业前缀
	 */
	public static final String TABLE_PREFIX_HSL_COMPANY = "C_";
	/**
	 * 平台卡券前缀
	 */
	public static final String TABLE_PREFIX_HSL_COUPON = "KQ_";
	/**
	 * 平台广告前缀
	 */
	public static final String TABLE_PREFIX_HSL_AD = "AD_";
	/**
	 * 平台线路前缀
	 */
	public static final String TABLE_PREFIX_HSL_XL = "XL_";
	/**
	 * 平台线路前缀
	 */
	public static final String TABLE_PREFIX_HSL_JD = "JD_";
	/**
	 * 系统配置
	 */
	public static final String TABLE_PREFIX_HSL_SYS = "SYS_";

	/**
	 * 得到模型对象的id
	 *
	 * @param modelObject model对象
	 * @return
	 */
	public static String getModelObjectId(BaseModel modelObject) {

		if (modelObject == null)
			return null;

		// 已初始化的对象直接获取ID
		if (Hibernate.isInitialized(modelObject)) {
			return modelObject.getId();
		}

		// 未初始化的对象获取代理类记录的id
		if (modelObject instanceof HibernateProxy) {
			return ((HibernateProxy) modelObject).getHibernateLazyInitializer().getIdentifier().toString();
		}

		return null;
	}

}
