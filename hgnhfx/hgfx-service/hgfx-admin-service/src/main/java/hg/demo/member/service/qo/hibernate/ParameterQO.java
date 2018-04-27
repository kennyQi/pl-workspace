package hg.demo.member.service.qo.hibernate;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.demo.member.common.spi.qo.ParameterSQO;


/**
 * @author zhurz
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "parameterDAO")
public class ParameterQO extends BaseHibernateQO<String> {

	/**
	 * 名称
	 */
	@QOAttr(name = "name", type = QOAttrType.EQ)
	private String name;


	public static ParameterQO build(ParameterSQO sqo) {
		ParameterQO qo = new ParameterQO();
		qo.setName(sqo.getName());
		return qo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
