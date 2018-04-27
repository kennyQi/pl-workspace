package hg.demo.member.service.qo.hibernate;

import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;

/**
 * 
* <p>Title: UserBaseInfoQO</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuwangwei
* @date 2016年6月27日 下午2:16:20
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "opLogDAO")
public class OpLogQO extends BaseHibernateQO<String> {
	
}
