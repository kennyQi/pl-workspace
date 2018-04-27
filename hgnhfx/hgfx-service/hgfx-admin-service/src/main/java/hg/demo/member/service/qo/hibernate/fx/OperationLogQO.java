package hg.demo.member.service.qo.hibernate.fx;

import java.util.Date;

import hg.demo.member.service.qo.hibernate.AuthUserQO;
import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.OperationLogSQO;

/**
 * 操作日志QO
 * */
@SuppressWarnings("serial")
@QOConfig(daoBeanId="operationLogDAO")
public class OperationLogQO extends BaseHibernateQO<String>{
	
	/**
	 * 日志关联操作人
	 */
	@QOAttr(name = "operator", type = QOAttrType.LEFT_JOIN)
	private AuthUserQO authUserQO;
	
	/** 操作开始时间 */
	@QOAttr(name = "createDate", type = QOAttrType.GE)
	private Date startDate;
	
	/** 操作开始时间 */
	@QOAttr(name = "createDate", type = QOAttrType.LE)
	private Date endDate;
	
	@QOAttr(name = "createDate", type = QOAttrType.ORDER)
	private Integer orderByCreateDate = -1;

	
	public static OperationLogQO bulid(OperationLogSQO sqo){
		OperationLogQO qo = new OperationLogQO();
		AuthUserQO au = new AuthUserQO();
		au.setId(sqo.getOperatorId());
		qo.setAuthUserQO(au);
		qo.setStartDate(sqo.getStartDate());
		qo.setEndDate(sqo.getEndDate());
		qo.setLimit(sqo.getLimit());
		return qo;
	}
	
	public AuthUserQO getAuthUserQO() {
		return authUserQO;
	}

	public void setAuthUserQO(AuthUserQO authUserQO) {
		this.authUserQO = authUserQO;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
