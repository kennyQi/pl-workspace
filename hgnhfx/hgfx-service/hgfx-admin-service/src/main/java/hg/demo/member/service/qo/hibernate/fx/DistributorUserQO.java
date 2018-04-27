package hg.demo.member.service.qo.hibernate.fx;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.DistributorUserSQO;

/**
 * 
 * @author Caihuan
 * @date   2016年6月1日
 */
@SuppressWarnings("serial")
public class DistributorUserQO extends BaseHibernateQO{
	
	
	/**
	 * 商户帐号
	 */
	@QOAttr(name = "loginName", type = QOAttrType.LIKE_ANYWHERE)
	private String account;
	
	/**
	 * 商户帐号
	 */
	@QOAttr(name = "loginName", type = QOAttrType.EQ)
	private String eqAccount;
	
	/**
	 * 商户姓名
	 */
	@QOAttr(name = "name", type = QOAttrType.LIKE_ANYWHERE)
	private String name;
	
	/**
	 * 帐号类型 1主账号 2子帐号
	 */
	@QOAttr(name = "status", type = QOAttrType.EQ_OR_NULL)
	private Integer status;
	
	/**
	 * 开始时间
	 */
	@QOAttr(name = "createDate", type = QOAttrType.GE)
	private Date beginDate;
	
	/**
	 * 结束时间
	 */
	@QOAttr(name = "createDate", type = QOAttrType.LE)
	private Date endDate;
	
	/**
	 * 是否删除
	 */
	@QOAttr(name = "removed", type = QOAttrType.EQ_OR_NULL)
	private Boolean removed;
	
	
	private DistributorQO distributorQo;
	
	

	public DistributorQO getDistributorQo() {
		return distributorQo;
	}

	public void setDistributorQo(DistributorQO distributorQo) {
		this.distributorQo = distributorQo;
	}


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@SuppressWarnings("unchecked")
	public static DistributorUserQO build(DistributorUserSQO sqo) {
		DistributorUserQO qo = new DistributorUserQO();
		
		if(sqo.isEqAccount())
		{
			qo.setEqAccount(sqo.getAccount());
		}
		else
		{
			qo.setAccount(sqo.getAccount());
		}
		qo.setName(sqo.getName());
		qo.setLimit(sqo.getLimit());
		qo.setStatus(sqo.getType());
		qo.setBeginDate(sqo.getBeginDate());
		qo.setEndDate(sqo.getEndDate());
		DistributorQO dqo = new DistributorQO();
		dqo.setCode(sqo.getCode());
		dqo.setCheckStatus(sqo.getCheckStatus());
		dqo.setStatus(sqo.getStatus());
		dqo.setQueryReserveInfo(sqo.isQueryReserveInfo());
		dqo.setId(sqo.getDistributorId());
		qo.setDistributorQo(dqo);
		qo.setId(sqo.getId());
		if(sqo.getUserRemoved()!=null)
		qo.setRemoved(sqo.getUserRemoved());
		return qo;
	}

	public String getEqAccount() {
		return eqAccount;
	}

	public void setEqAccount(String eqAccount) {
		this.eqAccount = eqAccount;
	}

	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}
	
	

}
