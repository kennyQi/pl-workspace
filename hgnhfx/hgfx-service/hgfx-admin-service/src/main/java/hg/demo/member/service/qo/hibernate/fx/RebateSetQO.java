package hg.demo.member.service.qo.hibernate.fx;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.RebateSetSQO;
import hg.fx.util.DateUtil;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

@QOConfig(daoBeanId = "rebateSetDAO")
public class RebateSetQO extends BaseHibernateQO<String> {

	/**  */
	private static final long serialVersionUID = 1L;
	/***查询当前月**/
	public static final int QUERY_CURR_MONTH = 1;
	/** 查询次月  */
	public static final int QUERY_NEXT_MONTH = 2;
	/**
	 * 查询方式，在查询当月或次月配置时有特殊设置
	 * RebateSetQO.QUERY_CURR_MONTH ：查询当前月
	 * RebateSetQO.QUERY_NEXT_MONTH ：查询次月 
	 * 空或其他则正常查询
	 */
	private Integer queryWay;
	/**
	 * 商品
	 */
	@QOAttr(name = "product", type = QOAttrType.LEFT_JOIN)
	private ProductQO productQO;
	
	/**
	 * 商户
	 */
	@QOAttr(name = "distributor", type = QOAttrType.LEFT_JOIN)
	private DistributorQO distributorQO;
	
	
	/**
	 * 审核状态
	 * 0-待审核     1-已通过 2-已拒绝
	 * */
	@QOAttr(name = "checkStatus", type = QOAttrType.EQ)
	private Integer checkStatus;
	
	
	/**
	 *  生效时间开始
	 * */
	@QOAttr(name = "implementDate", type = QOAttrType.GE)
	private Date implementDateStart;
	/**
	 * 用于特殊查询
	 */
	private Date implementDateStart1;
	/**
	 * 生效时间结束
	 */
	@QOAttr(name = "implementDate", type = QOAttrType.LE)
	private Date implementDateEnd;
	/**
	 * 用于特殊查询
	 */
	private Date implementDateEnd1;
	/**
	 * 生效时间等于
	 */
	@QOAttr(name = "implementDate", type = QOAttrType.EQ)
	private Date implementDateEQ;
	
	/**
	 * 用于特殊查询
	 */
	private Date implementDateEQ1;
	/**
	 *  失效时间开始
	 * */
	@QOAttr(name = "invalidDate", type = QOAttrType.GE)
	private Date invalidDateStart;
	/**
	 * 用于特殊查询
	 */
	private Date invalidDateStart1;
	/**
	 *  失效时间结束
	 * */
	@QOAttr(name = "invalidDate", type = QOAttrType.LE)
	private Date invalidDateEnd;
	/**
	 * 用于特殊查询
	 */
	private Date invalidDateEnd1;
	/**
	 *  失效时间等于或者null
	 * */
	@QOAttr(name = "invalidDate", type = QOAttrType.EQ_OR_NULL)
	private Date invalidDateEQ;
	/**
	 * 用于特殊查询
	 */
	private Date invalidDateEQ1;
	
	/**
	 * 是否有效
	 * Y-有效  N-无效
	 * */
	@QOAttr(name = "isImplement", type = QOAttrType.EQ)
	private Boolean isImplement;
	/**
	 * 特殊查询
	 */
	private Boolean isImplement1;
	
	/**
	 * 排序字段审核状态
	 */
	@QOAttr(name = "checkStatus", type = QOAttrType.ORDER)
	private Integer orderCheckStatus;
	/***
	 * 排序字段申请时间
	 */
	@QOAttr(name = "applyDate", type = QOAttrType.ORDER)
	private Integer orderApplyDate;
	
	@QOAttr(name = "runningSetId", type = QOAttrType.EQ)
	private String runningSetId;
	/**
	 * 不等于
	 */
	@QOAttr(name = "runningSetId", type = QOAttrType.NE)
	private String runningSetIdNE;
	

	public static RebateSetQO build(RebateSetSQO sqo) {
		RebateSetQO qo = new RebateSetQO();
		qo.setId(sqo.getId());
		qo.setLimit(sqo.getLimit());
		if(!StringUtils.isBlank(sqo.getProductId())){
			ProductQO pqo = new ProductQO();
			pqo.setId(sqo.getProductId());
			qo.setProductQO(pqo);
		}
		if(!StringUtils.isBlank(sqo.getDistributorId())){
			DistributorQO dqo = new DistributorQO();
			dqo.setId(sqo.getDistributorId());
			qo.setDistributorQO(dqo);
		}
		qo.setCheckStatus(sqo.getCheckStatus());
		qo.setIsImplement(sqo.getIsImplement());
		qo.setImplementDateStart(sqo.getImplementDateStart());
		qo.setImplementDateEnd(sqo.getImplementDateEnd());
		qo.setImplementDateEQ(sqo.getImplementDate());
		qo.setInvalidDateStart(sqo.getInvalidDateStart());
		qo.setInvalidDateEnd(sqo.getInvalidDateEnd());
		qo.setInvalidDateEQ(sqo.getInvalidDate());
		//默认排序方式
		qo.setOrderApplyDate(-1);
		qo.setOrderCheckStatus(2);
		if("-".equals(sqo.getRunningSetId())){
			qo.setRunningSetIdNE(sqo.getRunningSetId());
		}else{
			qo.setRunningSetId(sqo.getRunningSetId());
		}
		return qo ;
	}


	public String getRunningSetIdNE() {
		return runningSetIdNE;
	}


	public void setRunningSetIdNE(String runningSetIdNE) {
		this.runningSetIdNE = runningSetIdNE;
	}


	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Date getImplementDateStart() {
		return implementDateStart;
	}

	public void setImplementDateStart(String implementDateStart) {
		this.implementDateStart = DateUtil.parseDateTime1(implementDateStart+" 00:00:00");
	}

	public Date getImplementDateEnd() {
		return implementDateEnd;
	}

	public void setImplementDateEnd(String implementDateEnd) {
		this.implementDateEnd = DateUtil.parseDateTime1(implementDateEnd+" 23:59:59");
	}

	public Date getImplementDateEQ() {
		return implementDateEQ;
	}

	public void setImplementDateEQ(String implementDateEQ) {
		this.implementDateEQ = DateUtil.parseDateTime1(implementDateEQ);
	}

	public Date getInvalidDateStart() {
		return invalidDateStart;
	}

	public void setInvalidDateStart(String invalidDateStart) {
		this.invalidDateStart = DateUtil.parseDateTime1(invalidDateStart+" 00:00:00");
	}

	public Date getInvalidDateEnd() {
		return invalidDateEnd;
	}

	public void setInvalidDateEnd(String invalidDateEnd) {
		this.invalidDateEnd = DateUtil.parseDateTime1(invalidDateEnd+" 23:59:59");
	}

	public Date getInvalidDateEQ() {
		return invalidDateEQ;
	}

	public void setInvalidDateEQ(String invalidDateEQ) {
		this.invalidDateEQ = DateUtil.parseDateTime1(invalidDateEQ);
	}

	public Boolean getIsImplement() {
		return isImplement;
	}

	public void setIsImplement(Boolean isImplement) {
		this.isImplement = isImplement;
	}

	public Integer getOrderCheckStatus() {
		return orderCheckStatus;
	}

	public void setOrderCheckStatus(Integer orderCheckStatus) {
		this.orderCheckStatus = orderCheckStatus;
	}

	public Integer getOrderApplyDate() {
		return orderApplyDate;
	}

	public void setOrderApplyDate(Integer orderApplyDate) {
		this.orderApplyDate = orderApplyDate;
	}


	public ProductQO getProductQO() {
		return productQO;
	}


	public void setProductQO(ProductQO productQO) {
		this.productQO = productQO;
	}


	public DistributorQO getDistributorQO() {
		return distributorQO;
	}


	public void setDistributorQO(DistributorQO distributorQO) {
		this.distributorQO = distributorQO;
	}


	public Integer getQueryWay() {
		return queryWay;
	}


	public void setQueryWay(Integer queryWay) {
		this.queryWay = queryWay;
	}


	public Date getImplementDateStart1() {
		return implementDateStart1;
	}


	public void setImplementDateStart1(Date implementDateStart1) {
		this.implementDateStart1 = implementDateStart1;
	}


	public Date getImplementDateEnd1() {
		return implementDateEnd1;
	}


	public void setImplementDateEnd1(Date implementDateEnd1) {
		this.implementDateEnd1 = implementDateEnd1;
	}


	public Date getImplementDateEQ1() {
		return implementDateEQ1;
	}


	public void setImplementDateEQ1(Date implementDateEQ1) {
		this.implementDateEQ1 = implementDateEQ1;
	}


	public Date getInvalidDateStart1() {
		return invalidDateStart1;
	}


	public void setInvalidDateStart1(Date invalidDateStart1) {
		this.invalidDateStart1 = invalidDateStart1;
	}


	public Date getInvalidDateEnd1() {
		return invalidDateEnd1;
	}


	public void setInvalidDateEnd1(Date invalidDateEnd1) {
		this.invalidDateEnd1 = invalidDateEnd1;
	}


	public Date getInvalidDateEQ1() {
		return invalidDateEQ1;
	}


	public void setInvalidDateEQ1(Date invalidDateEQ1) {
		this.invalidDateEQ1 = invalidDateEQ1;
	}


	public Boolean getIsImplement1() {
		return isImplement1;
	}


	public void setIsImplement1(Boolean isImplement1) {
		this.isImplement1 = isImplement1;
	}


	public String getRunningSetId() {
		return runningSetId;
	}


	public void setRunningSetId(String runningSetId) {
		this.runningSetId = runningSetId;
	}
	
}
