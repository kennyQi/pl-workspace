package hg.demo.member.service.qo.hibernate;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.demo.member.common.spi.qo.DepartmentSQO;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhurz
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "departmentDAO")
public class DepartmentQO extends BaseHibernateQO<String> {

	/**
	 * 名称
	 */
	@QOAttr(name = "name", type = QOAttrType.LIKE_ANYWHERE)
	private String name;

	/**
	 * 部门经理
	 */
	@QOAttr(name = "manager", type = QOAttrType.LEFT_JOIN)
	private MemberQO managerQO;

	public static DepartmentQO build(DepartmentSQO sqo) {
		DepartmentQO qo = new DepartmentQO();
		qo.setName(sqo.getName());
		if (StringUtils.isNotBlank(sqo.getManagerId())) {
			qo.setManagerQO(new MemberQO());
			qo.getManagerQO().setId(sqo.getManagerId());
		}
		qo.setLimit(sqo.getLimit());
		return qo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MemberQO getManagerQO() {
		return managerQO;
	}

	public void setManagerQO(MemberQO managerQO) {
		this.managerQO = managerQO;
	}
}
