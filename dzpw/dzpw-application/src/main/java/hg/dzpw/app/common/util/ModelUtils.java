package hg.dzpw.app.common.util;

import hg.common.component.BaseModel;

import org.hibernate.proxy.HibernateProxy;

/**
 * @类功能说明：基础MODEL工具
 * @类修改者：
 * @修改日期：2015-4-24下午4:09:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-24下午4:09:10
 */
public class ModelUtils {
	
	/**
	 * @方法功能说明：获取实体对象ID
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-24下午4:09:19
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String getId(BaseModel model) {
		if (model == null)
			return null;
		if (model instanceof HibernateProxy)
			return ((HibernateProxy) model).getHibernateLazyInitializer()
					.getIdentifier().toString();
		return model.getId();
	}
}
