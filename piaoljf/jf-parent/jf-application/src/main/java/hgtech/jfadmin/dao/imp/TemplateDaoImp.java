/**
 * @文件名称：TemplateDaoImp.java
 * @类路径：hgtech.jfaddmin.dao.imp
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月14日上午10:44:35
 */
package hgtech.jfadmin.dao.imp;

import org.springframework.stereotype.Repository;

import hgtech.jf.JfProperty;
import hgtech.jf.entity.StringUK;
import hgtech.jf.entity.dao.BaseEntityFileDao;
import hgtech.jfcal.model.RuleTemplate;

/**
 * @类功能说明：模版dao
 * @类修改者：
 * @修改日期：2014年10月14日上午10:44:35
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月14日上午10:44:35
 *
 */
@Repository("templateDao")
public class TemplateDaoImp extends BaseEntityFileDao<StringUK, RuleTemplate>{

	/**
	 * @类名：TemplateDaoImp.java
	 * @@param entityClass
	 */
	public TemplateDaoImp() {
		super(RuleTemplate.class);
		objectPath=JfProperty.getProperties().getProperty("jfPath");
	}

}
