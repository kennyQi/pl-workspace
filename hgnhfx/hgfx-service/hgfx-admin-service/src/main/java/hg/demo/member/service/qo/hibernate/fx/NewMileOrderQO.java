package hg.demo.member.service.qo.hibernate.fx;

import hg.demo.member.service.qo.hibernate.AuthUserQO;
import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.enums.MileOrderOrderWayEnum;
import hg.fx.spi.qo.MileOrderSQO;
import hg.fx.util.DateUtil;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
/**
 * 
 * @author zqq
 * @date 2016-6-2下午3:43:14
 * @since
 */
public class NewMileOrderQO extends BaseHibernateQO<String> {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 分销商
	 */
	@QOAttr(name = "distributor", type = QOAttrType.LEFT_JOIN)
	private DistributorQO distributorQO;

	/**
	 * 订单号
	 */
	@QOAttr(name = "orderCode", type = QOAttrType.EQ)
	private String orderCode;
	/**
	 * 流水号
	 */
	@QOAttr(name = "flowCode", type = QOAttrType.EQ)
	private String flowCode;

	/**
	 * 南航卡号
	 */
	@QOAttr(name = "csairCard", type = QOAttrType.LIKE_ANYWHERE)
	private String csairCard;
	
	@QOAttr(name = "csairCard", type = QOAttrType.EQ)
	private String csairCardEQ;
	
	/**
	 * 南航姓名
	 */
	@QOAttr(name = "csairName", type = QOAttrType.LIKE_ANYWHERE)
	private String csairName;


	/**
	 * 支付时间
	 */
	@QOAttr(name = "payDate", type = QOAttrType.GE)
	private String payDateStart;
	@QOAttr(name = "payDate", type = QOAttrType.LE)
	private String payDateEnd;

	/**
	 * 导入时间
	 */
	@QOAttr(name = "importDate", type = QOAttrType.GE)
	private Date importDateStart;
	@QOAttr(name = "importDate", type = QOAttrType.LE)
	private Date importDateEnd;

	/**
	 * 状态
	 */
	@QOAttr(name = "status", type = QOAttrType.EQ)
	private Integer status;
	
	/**
	 * 不是该状态
	 */
	@QOAttr(name = "status", type = QOAttrType.NE)
	private Integer nonStatus;
	
	/**购买商品	
	 */
	@QOAttr(name = "product", type = QOAttrType.LEFT_JOIN)
	private ProductQO productQO;
	
	@QOAttr(name = "type", type=QOAttrType.EQ)
	private Integer type;
	
	@QOAttr(name = "type", type=QOAttrType.NE)
	private Integer typeNotEQ;
	
	/**
	 * 排序字段
	 */
	@QOAttr(name = "importDate", type=QOAttrType.ORDER)
	private Integer orderByImportDate;
	@QOAttr(name = "num", type=QOAttrType.ORDER)
	private Integer orderByImportNum;
	
	@QOAttr(name = "checkPerson", type=QOAttrType.LEFT_JOIN)
	private AuthUserQO authUserQO;

	
	
	public AuthUserQO getAuthUserQO() {
		return authUserQO;
	}


	public void setAuthUserQO(AuthUserQO authUserQO) {
		this.authUserQO = authUserQO;
	}


	public static NewMileOrderQO build(MileOrderSQO sqo) {
		NewMileOrderQO qo = new NewMileOrderQO();
		qo.setId(sqo.getId());
		if(StringUtils.isNotBlank(sqo.getDistributorId())){
			qo.setDistributorQO(new DistributorQO());
			qo.getDistributorQO().setId(sqo.getDistributorId());
		}else if(sqo.isJoin()==true){
			qo.setDistributorQO(new DistributorQO());
		}
		qo.setOrderCode(sqo.getOrderCode());
		qo.setCsairCard(sqo.getCsairCard());
		qo.setStatus(sqo.getStatus());
		qo.setType(sqo.getOrderType());
		qo.setNonStatus(sqo.getNonStatus());
		qo.setImportDateStart(sqo.getStrImportDate());
		qo.setImportDateEnd(sqo.getEndImportDate());
		if(StringUtils.isNotBlank(sqo.getProductId())){
			qo.setProductQO(new ProductQO());
			qo.getProductQO().setId(sqo.getProductId());
		}else if(sqo.isJoin()==true){
			qo.setProductQO(new ProductQO());
		}
		if(sqo.isJoin()==true){
			qo.setAuthUserQO(new AuthUserQO());
		}
		if(StringUtils.isNotBlank(sqo.getChannelId())){
			if(qo.getProductQO()!=null){
				qo.getProductQO().setChannelQO(new ChannelQO());
				qo.getProductQO().getChannelQO().setId(sqo.getChannelId());
			}else{
				qo.setProductQO(new ProductQO());
				qo.getProductQO().setChannelQO(new ChannelQO());
				qo.getProductQO().getChannelQO().setId(sqo.getChannelId());
			}
		}
		qo.setFlowCode(sqo.getFlowNo());
		//设置排序
		if(sqo.getOrderWay()!=null){
			if(MileOrderOrderWayEnum.ORDER_BY_CREATEDATE_DESC.equals(sqo.getOrderWay())){
				qo.setOrderByImportDate(-1);
			}else if(MileOrderOrderWayEnum.ORDER_BY_CREATEDATE_AES.equals(sqo.getOrderWay())){
				qo.setOrderByImportDate(1);
			}else if(MileOrderOrderWayEnum.ORDER_BY_NUM_DESC.equals(sqo.getOrderWay())){
				qo.setOrderByImportNum(-1);
			}else if(MileOrderOrderWayEnum.ORDER_BY_NUM_AES.equals(sqo.getOrderWay())){
				qo.setOrderByImportNum(1);
			}
		}else{//默认导入时间降序排序
			qo.setOrderByImportDate(-1);
		}
		qo.setLimit(sqo.getLimit());
		return qo;
	}

	
	public String getFlowCode() {
		return flowCode;
	}


	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}


	public Integer getOrderByImportDate() {
		return orderByImportDate;
	}


	public void setOrderByImportDate(Integer orderByImportDate) {
		this.orderByImportDate = orderByImportDate;
	}


	public Integer getOrderByImportNum() {
		return orderByImportNum;
	}


	public void setOrderByImportNum(Integer orderByImportNum) {
		this.orderByImportNum = orderByImportNum;
	}


	public Integer getNonStatus() {
		return nonStatus;
	}

	public void setNonStatus(Integer nonStatus) {
		this.nonStatus = nonStatus;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public DistributorQO getDistributorQO() {
		return distributorQO;
	}


	public void setDistributorQO(DistributorQO distributorQO) {
		this.distributorQO = distributorQO;
	}


	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getCsairCard() {
		return csairCard;
	}

	public void setCsairCard(String csairCard) {
		this.csairCard = csairCard;
	}

	public String getCsairName() {
		return csairName;
	}

	public void setCsairName(String csairName) {
		this.csairName = csairName;
	}

	public String getPayDateStart() {
		return payDateStart;
	}

	public void setPayDateStart(String payDateStart) {
		this.payDateStart = payDateStart;
	}

	public String getPayDateEnd() {
		return payDateEnd;
	}

	public void setPayDateEnd(String payDateEnd) {
		this.payDateEnd = payDateEnd;
	}

	public Date getImportDateStart() {
		return importDateStart;
	}

	public void setImportDateStart(String importDateStart) {
		this.importDateStart = DateUtil.parseDateTime1(importDateStart+" 00:00:00");
	}

	public Date getImportDateEnd() {
		return importDateEnd;
	}

	public void setImportDateEnd(String importDateEnd) {
		this.importDateEnd = DateUtil.parseDateTime1(importDateEnd+" 23:59:59");
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public ProductQO getProductQO() {
		return productQO;
	}


	public void setProductQO(ProductQO productQO) {
		this.productQO = productQO;
	}


	public Integer getTypeNotEQ() {
		return typeNotEQ;
	}


	public void setTypeNotEQ(Integer typeNotEQ) {
		this.typeNotEQ = typeNotEQ;
	}


	public String getCsairCardEQ() {
		return csairCardEQ;
	}


	public void setCsairCardEQ(String csairCardEQ) {
		this.csairCardEQ = csairCardEQ;
	}


}
