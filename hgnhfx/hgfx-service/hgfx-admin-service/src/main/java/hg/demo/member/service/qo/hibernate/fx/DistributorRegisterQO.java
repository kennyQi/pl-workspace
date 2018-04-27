package hg.demo.member.service.qo.hibernate.fx;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.DistributorRegisterSQO;
import hg.fx.util.DateUtil;

import java.util.Date;

/**
 * @author zqq
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "distributorRegisterDAO")
public class DistributorRegisterQO extends BaseHibernateQO<String> {

	/**
	 * 登录名
	 */
	@QOAttr(name = "loginName", type = QOAttrType.EQ)
	private String loginName;
	
	/**
	 * 公司名称
	 */
	@QOAttr(name = "companyName", type = QOAttrType.LIKE_ANYWHERE)
	private String companyName;
	
	
	/**
	 * 联系电话(手机号)
	 */
	@QOAttr(name = "phone", type = QOAttrType.EQ)
	private String phone;
	
	
	/**
	 * 注册申请日期大于
	 */
	@QOAttr(name = "createDate", type = QOAttrType.GE)
	private Date strCreateDate;
	/**
	 * 注册申请日期小于
	 */
	@QOAttr(name = "createDate", type = QOAttrType.LE)
	private Date endCreateDate;
	
	/**
	 * 状态    0--待审核   1--已通过   2--已拒绝
	 */
	@QOAttr(name = "status", type = QOAttrType.EQ)
	private Integer status;
	
	/**
	 * 不能等于该状态
	 */
	@QOAttr(name = "status", type = QOAttrType.NE)
	private Integer notStatus;
	
	@QOAttr(name = "createDate", type = QOAttrType.ORDER)
	private Integer orderByCreateDate;
	
	@QOAttr(name = "status", type = QOAttrType.ORDER)
	private Integer orderByStatus;

	public static DistributorRegisterQO build(DistributorRegisterSQO sqo) {
		DistributorRegisterQO qo = new DistributorRegisterQO();
		qo.setLoginName(sqo.getLoginName());
		qo.setStatus(sqo.getStatus());
		qo.setLimit(sqo.getLimit());
		qo.setId(sqo.getId());
		qo.setStrCreateDate(sqo.getStrCreateDate());
		qo.setEndCreateDate(sqo.getEndCreateDate());
		qo.setPhone(sqo.getPhone());
		qo.setCompanyName(sqo.getCompanyName());
		//设置排序
		if(sqo.getOrderWay()==1){
			qo.setOrderByCreateDate(-1);
		}else if(sqo.getOrderWay()==2){
			qo.setOrderByStatus(2);
			qo.setOrderByCreateDate(-1);
		}
		return qo;
	}
	
	
	public Integer getOrderByStatus() {
		return orderByStatus;
	}


	public void setOrderByStatus(Integer orderByStatus) {
		this.orderByStatus = orderByStatus;
	}


	public Integer getNotStatus() {
		return notStatus;
	}


	public void setNotStatus(Integer notStatus) {
		this.notStatus = notStatus;
	}


	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getStrCreateDate() {
		return strCreateDate;
	}

	public void setStrCreateDate(String strCreateDate) {
		this.strCreateDate = DateUtil.parseDateTime1(strCreateDate+" 00:00:00");
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = DateUtil.parseDateTime1(endCreateDate+" 23:59:59");
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderByCreateDate() {
		return orderByCreateDate;
	}

	public void setOrderByCreateDate(Integer orderByCreateDate) {
		this.orderByCreateDate = orderByCreateDate;
	}
	
}
