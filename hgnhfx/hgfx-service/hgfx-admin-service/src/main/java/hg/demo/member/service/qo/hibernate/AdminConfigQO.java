package hg.demo.member.service.qo.hibernate;

import javax.persistence.Column;

import hg.demo.member.common.spi.qo.AdminConfigSQO;
import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;

@SuppressWarnings("serial")
@QOConfig(daoBeanId = "adminConfigDAO")
public class AdminConfigQO extends BaseHibernateQO<String> {
	/**
	*配置值
	*/
	@QOAttr(name="value",type = QOAttrType.EQ)
	private String value;

	/**
	*键
	*/
	@QOAttr(name="dataKey",type = QOAttrType.EQ)
	private String dataKey;
	public static AdminConfigQO build(AdminConfigSQO sqo) {
		AdminConfigQO qo = new AdminConfigQO();
		// set数据。。。

		qo.setLimit(sqo.getLimit());
		return qo;
	}
	public String getValue() {
		return value;
	}
	public String getDataKey() {
		return dataKey;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}
	
	

}
