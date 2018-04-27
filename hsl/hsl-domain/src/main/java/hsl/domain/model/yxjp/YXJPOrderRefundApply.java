package hsl.domain.model.yxjp;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.yxjp.order.ApplyRefundYXJPOrderCommand;
import hsl.pojo.dto.yxjp.notify.ApplyRefundTicketNotify;
import hsl.pojo.util.HSLConstants;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 退废票申请
 *
 * @author zhurz
 * @since 1.7
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_YJ + "REFUND_APPLY")
public class YXJPOrderRefundApply extends BaseModel implements HSLConstants.YXJPOrderRefundApply {

	/**
	 * 所属订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID")
	private YXJPOrder fromOrder;
	/**
	 * 操作人
	 */
	@Column(name = "OPERATOR", length = 64)
	private String operator;
	/**
	 * 包含乘客
	 */
	@ManyToMany
	private List<YXJPOrderPassenger> passengers = new ArrayList<YXJPOrderPassenger>();
	/**
	 * 申请种类
	 *
	 * @see HSLConstants.YXJPOrderRefundApply
	 */
	@Column(name = "REFUND_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer refundType;
	/**
	 * 申请理由
	 */
	@Column(name = "REFUND_MEMO", length = 512)
	private String refundMemo;
	/**
	 * 申请时间
	 */
	@Column(name = "APPLY_DATE", columnDefinition = M.DATE_COLUM)
	private Date applyDate;
	/**
	 * 是否申请成功
	 */
	@Type(type = "yes_no")
	@Column(name = "APPLY_SUCCESS")
	private Boolean applySuccess = false;
	/**
	 * 申请失败后的错误信息
	 */
	@Column(name = "ERROR_MSG", length = 512)
	private String errorMsg;

	public YXJPOrder getFromOrder() {
		return fromOrder;
	}

	public void setFromOrder(YXJPOrder fromOrder) {
		this.fromOrder = fromOrder;
	}

	public List<YXJPOrderPassenger> getPassengers() {
		if (passengers == null)
			passengers = new ArrayList<YXJPOrderPassenger>();
		return passengers;
	}

	public void setPassengers(List<YXJPOrderPassenger> passengers) {
		this.passengers = passengers;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public String getRefundMemo() {
		return refundMemo;
	}

	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Boolean getApplySuccess() {
		if (applySuccess == null)
			applySuccess = false;
		return applySuccess;
	}

	public void setApplySuccess(Boolean applySuccess) {
		this.applySuccess = applySuccess;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	// --------------------------------------------------------------------

	private transient Manager manager;

	public Manager manager() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}

	public class Manager {

		/**
		 * 创建退废票申请
		 *
		 * @param command    退废票申请command
		 * @param order      所属订单
		 * @param passengers 需要退废票的乘客
		 * @param operator   操作人
		 * @return
		 */
		YXJPOrderRefundApply create(ApplyRefundYXJPOrderCommand command, YXJPOrder order,
									List<YXJPOrderPassenger> passengers, String operator) {
			setId(UUIDGenerator.getUUID());
			setFromOrder(order);
			getPassengers().addAll(passengers);
			setRefundType(command.getRefundType());
			setRefundMemo(command.getRefundMemo());
			setApplyDate(new Date());
			setOperator(operator);
			return YXJPOrderRefundApply.this;
		}

		/**
		 * 创建退废票申请
		 *
		 * @param notify     分销申请退废票通知
		 * @param order      所属订单
		 * @param passengers 需要退废票的乘客
		 * @return
		 */
		YXJPOrderRefundApply createByNotify(ApplyRefundTicketNotify notify, YXJPOrder order, List<YXJPOrderPassenger> passengers) {
			setId(UUIDGenerator.getUUID());
			setFromOrder(order);
			getPassengers().addAll(passengers);
			setRefundType(notify.getRefundType());
			setRefundMemo(notify.getRefundMemo());
			setApplyDate(new Date());
			setOperator("分销通知");
			setApplySuccess(true);
			return YXJPOrderRefundApply.this;
		}

		/**
		 * 处理退废票申请响应
		 *
		 * @param success
		 * @param errorMsg
		 */
		void processResponse(boolean success, String errorMsg) {
			setApplySuccess(success);
			setErrorMsg(errorMsg);
		}

	}


}
