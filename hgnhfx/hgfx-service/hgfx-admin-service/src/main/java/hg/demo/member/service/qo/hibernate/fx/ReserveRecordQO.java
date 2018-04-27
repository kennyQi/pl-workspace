package hg.demo.member.service.qo.hibernate.fx;

import java.util.Date;

import javax.persistence.Column;

import hg.demo.member.common.domain.model.def.M;
import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.ReserveRecordSQO;

import org.apache.commons.lang.StringUtils;

/**
 * @author cangs
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "reserveRecordDAO")
public class ReserveRecordQO extends BaseHibernateQO<String> {

	/**
	 * 商户
	 */
	@QOAttr(name = "distributor", type = QOAttrType.LEFT_JOIN)
	private DistributorQO distributorQO;

	/**
	 * 审核状态
	 */
	@QOAttr(name = "checkStatus", type = QOAttrType.EQ)
	private Integer checkStatus;
	
	/**
	 * 按状态排序 
	 * 排序:>0升序，<0降序，=0不排序。（绝对值越大的排的越前）
	 */
	@QOAttr(name = "checkStatus", type = QOAttrType.ORDER)
	private Integer orderByStatus;
	/**
	 * 按申请时间排序
	 * 排序:>0升序，<0降序，=0不排序。（绝对值越大的排的越前）
	 */
	@QOAttr(name = "applyDate", type = QOAttrType.ORDER)
	private Integer orderByApplyDate;
	
	/**
	 * 交易时间排序
	 * 排序:>0升序，<0降序，=0不排序。（绝对值越大的排的越前）
	 * */
	@QOAttr(name = "tradeDate", type = QOAttrType.ORDER)
	private Integer orderByTradeDate = -1;
	
	/**
	 * 交易类型.
	 * 1--交易  2--交易退款  3--后台备付金充值  4--在线备付金充值  5--月末返利
	 */
	@QOAttr(name = "type", type = QOAttrType.EQ)
	private Integer type;
	
	@QOAttr(name = "prodName", type = QOAttrType.EQ)
	private String prodName;
	
	@QOAttr(name = "tradeNo", type = QOAttrType.LIKE_ANYWHERE)
	private String tradeNo;
	
	@QOAttr(name = "status", type = QOAttrType.EQ)
	private Integer statusEQ;
	
	@QOAttr(name = "tradeDate", type = QOAttrType.GE)
	private Date tradeStartDate;
	
	@QOAttr(name = "tradeDate", type = QOAttrType.LE)
	private Date tradeEndDate;
	
	/**
	 * 状态集合
	 * */
	@QOAttr(name = "status", type = QOAttrType.IN)
	private Integer[] statusArray;

	public static ReserveRecordQO build(ReserveRecordSQO sqo) {
		ReserveRecordQO qo = new ReserveRecordQO();
		if (StringUtils.isNotBlank(sqo.getDistributorID())) {
			DistributorQO distributorQO = new DistributorQO();
			distributorQO.setId(sqo.getDistributorID());
			qo.setDistributorQO(distributorQO);
		}
		qo.setOrderByStatus(sqo.getOrderByStatus());
		qo.setOrderByApplyDate(sqo.getOrderByApplyDate());
		qo.setType(sqo.getType());
		qo.setId(sqo.getReserveRecordID());
		qo.setCheckStatus(sqo.getCheckStatus());
		qo.setLimit(sqo.getLimit());
		qo.setProdName(sqo.getProdName());
		qo.setTradeNo(sqo.getTradeNo());
		qo.setStatusArray(sqo.getStatusArray());
		qo.setTradeStartDate(sqo.getTradeDateStart());
		qo.setTradeEndDate(sqo.getTradeDateEnd());
		return qo;
	}
	

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}


	public String getTradeNo() {
		return tradeNo;
	}


	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}


	public Integer getStatusEQ() {
		return statusEQ;
	}


	public void setStatusEQ(Integer statusEQ) {
		this.statusEQ = statusEQ;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Integer getOrderByStatus() {
		return orderByStatus;
	}


	public void setOrderByStatus(Integer orderByStatus) {
		this.orderByStatus = orderByStatus;
	}


	public Integer getOrderByApplyDate() {
		return orderByApplyDate;
	}


	public void setOrderByApplyDate(Integer orderByApplyDate) {
		this.orderByApplyDate = orderByApplyDate;
	}


	public DistributorQO getDistributorQO() {
		return distributorQO;
	}

	public void setDistributorQO(DistributorQO distributorQO) {
		this.distributorQO = distributorQO;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}


	public Date getTradeStartDate() {
		return tradeStartDate;
	}


	public void setTradeStartDate(Date tradeStartDate) {
		this.tradeStartDate = tradeStartDate;
	}


	public Date getTradeEndDate() {
		return tradeEndDate;
	}


	public void setTradeEndDate(Date tradeEndDate) {
		this.tradeEndDate = tradeEndDate;
	}


	public Integer[] getStatusArray() {
		return statusArray;
	}


	public void setStatusArray(Integer[] statusArray) {
		this.statusArray = statusArray;
	}

}
