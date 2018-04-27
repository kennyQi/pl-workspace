package hg.demo.member.service.qo.hibernate;

import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;

/**
 * @author zhurz
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "memberDAO")
public class MemberQO extends BaseHibernateQO<String> {
}
