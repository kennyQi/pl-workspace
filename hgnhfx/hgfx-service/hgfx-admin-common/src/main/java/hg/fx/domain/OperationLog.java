package hg.fx.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;

/**
 * @类功能说明：操作日志 业务日志
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-5-31下午4:56:39
 * @版本：V1.0
 *
 */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "OPERATION_LOG")
public class OperationLog extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;
	
	/** 后台添加商户操作 */
	public static final Integer OPERATION_TYPE_ADD_DISTRIBUTOR = 1;
	/** 对审核商户申请操作*/
	public static final Integer OPERATION_TYPE_CHECK_DISTRIBUTOR = 2;
	/** 对商户启用禁用操作*/
	public static final Integer OPERATION_TYPE_ENABLE_DISABLE = 3;
	/** 对商户的商品启用禁用操作*/
	public static final Integer OPERATION_TYPE_MER_PRODUCT_ENABLE_DISABLE = 4;
	/** 申请里程添加操作*/
	public static final Integer OPERATION_TYPE_APPLY_ADD_MILEAGE= 5;
	/** 里程添加审核操作*/
	public static final Integer OPERATION_TYPE_CHECK_ADD_MILEAGE= 6;
	/** 申请可欠费里程修改操作*/
	public static final Integer OPERATION_TYPE_APPLY_EDIT_ARREARS= 7;
	/** 可欠费里程修改审核操作*/
	public static final Integer OPERATION_TYPE_CHECK_EDIT_ARREARS= 8;
	/** 预警里程设置操作 */
	public static final Integer OPERATION_TYPE_SET_WARN = 9;
	/** 异常订单审核操作*/
	public static final Integer OPERATION_TYPE_CHECK_EXCEPTION_ORDER= 10;
	/**商户注册申请审核**/
	public static final Integer OPERATION_TYPE_ADUIT_DISTRIBUTORREGIST= 11;
	
	
	/**
	 * 操作者
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPERATOR_ID")
	private AuthUser operator;
	
	/**
	 * 创建日期
	 */
	@Column(name = "CREATE_DATE", columnDefinition = O.DATE_COLUM)
	private Date createDate;
	
	/**
	 * 操作内容 对操作的详细描述 
	 */
	@Column(name = "CONTENT", columnDefinition = O.TEXT_COLUM)
	private String content;
	
	/**
	 * 操作类型
	 * 1--后台添加商户操作
	 * 2--对审核商户申请操作
	 * 3--对商户启用禁用操作
	 * 4--对商户的商品启用禁用操作
	 * 5--申请里程添加操作
	 * 6--里程添加审核操作
	 * 7--申请可欠费里程修改操作
	 * 8--可欠费里程修改审核操作
	 * 9--预警里程设置操作
	 * 10--异常订单审核操作
	 */
	@Column(name = "TYPE", columnDefinition = O.TYPE_NUM_COLUM)
	private Integer type;

	public AuthUser getOperator() {
		return operator;
	}

	public void setOperator(AuthUser operator) {
		this.operator = operator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
