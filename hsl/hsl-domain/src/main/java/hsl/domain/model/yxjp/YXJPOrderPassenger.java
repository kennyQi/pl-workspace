package hsl.domain.model.yxjp;

import hg.common.component.BaseModel;
import hg.common.util.MoneyUtil;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.domain.model.company.Member;
import hsl.pojo.dto.yxjp.YXPassengerDTO;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.util.HSLConstants;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

/**
 * 易行机票订单乘客（相当于机票订单下的子订单）
 *
 * @author zhurz
 * @since 1.7
 */
@Entity
@DynamicUpdate
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_YJ + "ORDER_PASSENGER")
public class YXJPOrderPassenger extends BaseModel {

	/**
	 * 所属订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID")
	private YXJPOrder fromOrder;

	/**
	 * 基本信息
	 */
	@Embedded
	private YXJPOrderPassengerBaseInfo baseInfo;

	/**
	 * 员工在公司的信息（企业账户使用通讯录里的员工下单才有）
	 */
	@Embedded
	private YXJPOrderPassengerCompanyInfo companyInfo;

	/**
	 * 机票信息
	 */
	@Embedded
	private YXJPOrderPassengerTicket ticket;

	/**
	 * 支付信息
	 */
	@Embedded
	private YXJPOrderPassengerPayInfo payInfo;

	public YXJPOrder getFromOrder() {
		return fromOrder;
	}

	public void setFromOrder(YXJPOrder fromOrder) {
		this.fromOrder = fromOrder;
	}

	public YXJPOrderPassengerBaseInfo getBaseInfo() {
		if (baseInfo == null)
			baseInfo = new YXJPOrderPassengerBaseInfo();
		return baseInfo;
	}

	public void setBaseInfo(YXJPOrderPassengerBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public YXJPOrderPassengerCompanyInfo getCompanyInfo() {
		if (companyInfo == null)
			companyInfo = new YXJPOrderPassengerCompanyInfo();
		return companyInfo;
	}

	public void setCompanyInfo(YXJPOrderPassengerCompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public YXJPOrderPassengerTicket getTicket() {
		if (ticket == null)
			ticket = new YXJPOrderPassengerTicket();
		return ticket;
	}

	public void setTicket(YXJPOrderPassengerTicket ticket) {
		this.ticket = ticket;
	}

	public YXJPOrderPassengerPayInfo getPayInfo() {
		if (payInfo == null)
			payInfo = new YXJPOrderPassengerPayInfo();
		return payInfo;
	}

	public void setPayInfo(YXJPOrderPassengerPayInfo payInfo) {
		this.payInfo = payInfo;
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
		 * 创建乘客
		 *
		 * @param dto       下单command里的乘客DTO
		 * @param fromOrder 来自订单
		 * @param member    企业用户选择的员工
		 * @param flight    航班信息
		 * @param seq       在订单中的序号
		 * @return
		 */
		YXJPOrderPassenger create(YXPassengerDTO dto, YXJPOrder fromOrder, Member member, YXJPOrderFlight flight, Integer seq) {

			setId(UUIDGenerator.getUUID());
			setFromOrder(fromOrder);

			// 检查是否为企业员工
			if (member != null) {
				// 基本信息
				getBaseInfo().setName(member.getName());
				getBaseInfo().setMobile(member.getMobilePhone());
				getBaseInfo().setIdNo(member.getCertificateID());
				getBaseInfo().setIdType(HSLConstants.Traveler.ID_TYPE_SFZ);
				getBaseInfo().setType(HSLConstants.Traveler.TYPE_ADULT);
				// 公司员工信息
				getCompanyInfo().setMemeberId(member.getId());
				getCompanyInfo().setDepartmentId(member.getDepartment().getId());
				getCompanyInfo().setDepartmentName(member.getDepartment().getDeptName());
				getCompanyInfo().setCompanyId(member.getDepartment().getCompany().getId());
				getCompanyInfo().setCompanyName(member.getDepartment().getCompany().getCompanyName());
			} else {
				// 基本信息
				getBaseInfo().setName(dto.getName());
				getBaseInfo().setMobile(dto.getMobile());
				getBaseInfo().setIdNo(dto.getIdNo());
				getBaseInfo().setIdType(dto.getIdType());
				getBaseInfo().setType(dto.getType());
			}
			getBaseInfo().setSeq(seq);
			// 机票信息
			getTicket().setFlight(flight);
			getTicket().setStatus(HSLConstants.YXJPOrderPassengerTicket.STATUS_PAY_WAIT);
			// 支付信息
			getPayInfo().setPrice(flight.getPolicyInfo().getPayPrice());
			return YXJPOrderPassenger.this;
		}

		/**
		 * 取消未支付订单
		 */
		void cancelNoPayOrder() {
			// 检查状态，非待支付的抛出异常
			if (!YXJPOrderPassengerTicket.STATUS_PAY_WAIT.equals(getTicket().getStatus()))
				throw new ShowMessageException(String.format("非待支付的乘客[%s]不能取消", getBaseInfo().getName()));
			getTicket().setStatus(YXJPOrderPassengerTicket.STATUS_TICKET_CANCEL_NOPAY);
		}

		/**
		 * 支付成功处理
		 *
		 * @param payRecord 成功的支付记录
		 * @param payAmount 平摊的支付金额
		 */
		void processPaySuccess(YXJPOrderPayRecord payRecord, Double payAmount) {
			// 已取消未付款且支付成功时改变乘客状态为出票处理中
			if (payRecord.getPaySuccess()) {
				getPayInfo().setPayAmount(payAmount);
				getPayInfo().setPaySuccessRecord(payRecord);
				// 如果状态为待支付，改变状态外出票处理中
				if (YXJPOrderPassengerTicket.STATUS_PAY_WAIT.equals(getTicket().getStatus())) {
					getTicket().setStatus(YXJPOrderPassengerTicket.STATUS_TICKET_DEALING);
				}
				// 如果状态为已取消未付款，则改变为出票失败待退款
				else if (YXJPOrderPassengerTicket.STATUS_TICKET_CANCEL_NOPAY.equals(getTicket().getStatus())) {
					getTicket().setStatus(YXJPOrderPassengerTicket.STATUS_TICKET_FAIL_REBACK_WAIT);
				}
			}
		}

		/**
		 * 退废票申请处理
		 */
		void processRefundOrderApply() {
			// 需要乘客的状态为：已出票、退废票失败
			List<Integer> needStatus = Arrays.asList(
					YXJPOrderPassengerTicket.STATUS_TICKET_SUCC,
					YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_FAIL);
			if (needStatus.contains(getTicket().getStatus()))
				getTicket().setStatus(YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_DEALING);
		}

		/**
		 * 处理向分销下单失败
		 */
		void processOutOrderFailure() {
			if (YXJPOrderPassengerTicket.STATUS_TICKET_DEALING.equals(getTicket().getStatus())) {
				getTicket().setStatus(YXJPOrderPassengerTicket.STATUS_TICKET_FAIL_REBACK_WAIT);
				needPayRefund();
			}
		}

		/**
		 * 出票成功处理
		 *
		 * @param notify 出票成功通知
		 */
		void processOutTicketSuccess(String ticketNo) {
			getTicket().setTicketNo(ticketNo);
			// 设置状态为出票处理中的机票为已出票
			if (YXJPOrderPassengerTicket.STATUS_TICKET_DEALING.equals(getTicket().getStatus()))
				getTicket().setStatus(YXJPOrderPassengerTicket.STATUS_TICKET_SUCC);
		}

		/**
		 * 拒绝出票处理
		 *
		 * @param notify 绝出票通知
		 */
		void processOutTicketRefuse() {
			// 设置状态为出票处理中的机票为出票失败待退款
			if (YXJPOrderPassengerTicket.STATUS_TICKET_DEALING.equals(getTicket().getStatus())) {
				getTicket().setStatus(YXJPOrderPassengerTicket.STATUS_TICKET_FAIL_REBACK_WAIT);
				needPayRefund();
			}
		}

		/**
		 * 需要退款
		 */
		void needPayRefund() {
			// 计算需要退款的金额
			double needRefundAmount = Math.max(0d, MoneyUtil.round(getPayInfo().getPayAmount() - getPayInfo().getSupplierProceduresAmount(), 2));
			getPayInfo().setNeedRefundAmount(needRefundAmount);
			// 如果需要退款的金额为0且未用卡券支付则改变状态为退款成功
			if (needRefundAmount == 0d && getPayInfo().getPaySuccessRecord().getCouponAmount() == 0d) {
				processPayRefundSuccess(null);
			}
		}

		/**
		 * 退废票成功处理
		 *
		 * @param refundResult             成功的退废票结果
		 * @param supplierRefundAmount     供应商退款给分销的金额
		 * @param supplierProceduresAmount 供应商退款给分销的手续费
		 */
		void processRefundTicketSuccess(YXJPOrderRefundResult refundResult, Double supplierRefundAmount, Double supplierProceduresAmount) {
			getPayInfo().setSupplierProceduresAmount(supplierProceduresAmount);
			getPayInfo().setSupplierRefundAmount(supplierRefundAmount);
			getPayInfo().setRefundSuccessResult(refundResult);
			// 需要的状态
			List<Integer> needStatus = Arrays.asList(
					YXJPOrderPassengerTicket.STATUS_TICKET_SUCC,
					YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_DEALING,
					YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_FAIL
			);
			// 设置状态为退废处理中的机票为退废成功待退款
			if (needStatus.contains(getTicket().getStatus())) {
				getTicket().setStatus(YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_SUCC_REBACK_WAIT);
				// 计算需要退款的金额
				needPayRefund();
				// 如果需要退款的金额为0则直接更改状态为退废成功已退款
				if (getPayInfo().getNeedRefundAmount() == 0d)
					getTicket().setStatus(YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_SUCC_REBACK_SUCC);
			}
		}

		/**
		 * 退废票失败处理
		 *
		 * @param notify 退废票成功通知
		 */
		void processRefundTicketFailure() {
			// 设置状态为退废处理中的机票为退废失败
			if (YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_DEALING.equals(getTicket().getStatus()))
				getTicket().setStatus(YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_FAIL);
		}

		/**
		 * 退款成功处理
		 *
		 * @param payRefundSuccessRecord 成功的支付退款记录（不用退款时可为空）
		 */
		void processPayRefundSuccess(YXJPOrderPayRefundRecord payRefundSuccessRecord) {
			getPayInfo().setRefundAmount(getPayInfo().getNeedRefundAmount());
			getPayInfo().setPayRefundSuccessRecord(payRefundSuccessRecord);
			if (YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_SUCC_REBACK_WAIT.equals(getTicket().getStatus())) {
				// 退废成功待退款变更状态为退废票成功已退款
				getTicket().setStatus(YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_SUCC_REBACK_SUCC);
			} else if (YXJPOrderPassengerTicket.STATUS_TICKET_FAIL_REBACK_WAIT.equals(getTicket().getStatus())) {
				// 出票失败待退款变更状态为出票失败已退款
				getTicket().setStatus(YXJPOrderPassengerTicket.STATUS_TICKET_FAIL_REBACK_SUCC);
			}
		}

	}


}
