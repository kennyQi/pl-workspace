package hsl.domain.model.dzp.ticket;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.dzpw.dealer.client.dto.tourist.TouristDTO;
import hsl.domain.model.M;
import hsl.domain.model.dzp.order.DZPTicketOrder;
import hsl.pojo.command.dzp.order.CreateDZPTicketOrderCommand;
import hsl.pojo.util.HSLConstants;

import javax.persistence.*;

/**
 * 游客信息
 *
 * @author zhurz
 * @since 1.8
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_DZP + "TOURIST")
public class DZPTourist extends BaseModel implements HSLConstants.DZPTouristIdType {

	/**
	 * 所属订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_ORDER_ID")
	private DZPTicketOrder ticketOrder;

	/**
	 * 持有门票
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "GROUP_TICKET_ID")
	private DZPGroupTicket ticket;

	/**
	 * 在订单中的序号，从1开始
	 */
	@Column(name = "ORDER_SEQ", columnDefinition = M.NUM_COLUM)
	private Integer seq;

	/**
	 * 姓名
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	/**
	 * 证件类型
	 * <pre>
	 * 直销现阶段仅支持身份证
	 * (1、	身份证；2、	军官证；3、	驾驶证；4、	护照)
	 * </pre>
	 *
	 * @see HSLConstants.DZPTouristIdType
	 */
	@Column(name = "ID_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer idType;

	/**
	 * 证件号
	 */
	@Column(name = "ID_NO", length = 64)
	private String idNo;

	/**
	 * 手机号码
	 */
	@Column(name = "MOBILE", length = 32)
	private String mobile;

	public DZPTicketOrder getTicketOrder() {
		return ticketOrder;
	}

	public void setTicketOrder(DZPTicketOrder ticketOrder) {
		this.ticketOrder = ticketOrder;
	}

	public DZPGroupTicket getTicket() {
		return ticket;
	}

	public void setTicket(DZPGroupTicket ticket) {
		this.ticket = ticket;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	// --------------------------------------------------------------------

	private transient Manager manager;

	public Manager manager() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}

	public class Manager {
		public DZPTourist create(CreateDZPTicketOrderCommand.Tourist tourist,
								 DZPTicketOrder ticketOrder, Integer seq) {
			setId(UUIDGenerator.getUUID());
			setTicketOrder(ticketOrder);
			setSeq(seq);
			setName(tourist.getName());
			setIdType(ID_TYPE_ID);
			setIdNo(tourist.getIdNo());
			return DZPTourist.this;
		}

		/**
		 * 转为电子票务接口用DTO
		 *
		 * @return
		 */
		public TouristDTO convertToDZPWTouristDTO() {

			TouristDTO dto = new TouristDTO();
			dto.setName(getName());
			dto.setIdType(getIdType());
			dto.setIdNo(getIdNo());

			return dto;
		}
	}
}