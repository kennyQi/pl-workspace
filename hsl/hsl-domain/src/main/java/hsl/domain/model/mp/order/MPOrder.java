package hsl.domain.model.mp.order;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.domain.model.mp.scenic.MPPolicy;
import hsl.domain.model.mp.scenic.MPScenicSpot;
import hsl.pojo.command.MPOrderCreateCommand;
import hsl.pojo.dto.mp.MPOrderStatusDTO;
import hsl.pojo.dto.user.UserDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang.StringUtils;
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_HSL_MP + "ORDER")
public class MPOrder extends BaseModel {
	/**
	 * 商城订单号
	 */
	@Column(name = "DEALERORDERCODE", length = 64)
	private String dealerOrderCode;
	
	/**
	 * 平台订单号
	 */
	@Column(name="PLATFORM_ORDER_CODE",length=64)
	private String platformOrderCode;
	/**
	 * 下单时间
	 */
	@Column(name = "CREATEDATE",columnDefinition=M.DATE_COLUM)
	private Date createDate;
	
	/**
	 * 游玩时间
	 */
	@Column(name="TRAVREL_DATE")
	private String travelDate;
	
	/**
	 * 实付价
	 */
	@Column(name = "PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double price;

	/**
	 * 订单所购景区
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "SCENICSPOT_ID")
	private MPScenicSpot scenicSpot;

	/**
	 * 商品快照，PolicyDTO
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "POLICY_ID")
	private MPPolicy mpPolicy;

	/**
	 * 订购数量
	 */
	@Column(name = "ORDER_NUMBER", columnDefinition = M.NUM_COLUM)
	private Integer number;

	/**
	 * 下单人
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ORDER_USER_ID",nullable=false)
	private MPOrderUser orderUser;

	/**
	 * 取票人信息
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "TAKE_TICKET_USER_ID",nullable=false)
	private MPOrderUser takeTicketUser;

	/**
	 * 陪同游玩人信息
	 */
	@OneToMany(mappedBy="order")
	private List<MPOrderUser> travelers;

	/**
	 * 门票订单状态
	 */
	@Embedded
	private MPOrderStatus status;
	
	/**
	 * 取消原因
	 */
	@Column(name = "CANCEL_REMARK", length = 256)
	private String cancelRemark;

	public MPScenicSpot getScenicSpot() {
		return scenicSpot;
	}

	public void setScenicSpot(MPScenicSpot scenicSpot) {
		this.scenicSpot = scenicSpot;
	}

	public MPPolicy getMpPolicy() {
		return mpPolicy;
	}

	public void setMpPolicy(MPPolicy mpPolicy) {
		this.mpPolicy = mpPolicy;
	}

	public MPOrderUser getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(MPOrderUser orderUser) {
		this.orderUser = orderUser;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}


	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public MPOrderStatus getStatus() {
		return status;
	}

	public void setStatus(MPOrderStatus status) {
		this.status = status;
	}

	public MPOrderUser getTakeTicketUser() {
		return takeTicketUser;
	}

	public void setTakeTicketUser(MPOrderUser takeTicketUser) {
		this.takeTicketUser = takeTicketUser;
	}

	public List<MPOrderUser> getTravelers() {
		return travelers;
	}

	public void setTravelers(List<MPOrderUser> travelers) {
		this.travelers = travelers;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}

	
	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	
	public String getPlatformOrderCode() {
		return platformOrderCode;
	}

	public void setPlatformOrderCode(String platformOrderCode) {
		this.platformOrderCode = platformOrderCode;
	}

	/**
	 * 门票预定
	 * 
	 * @param mpOrderCreateCommand
	 */
	public void createOrder(MPOrderCreateCommand mpOrderCreateCommand,
			String platformOrderCode, MPScenicSpot scenicSpot,
			MPPolicy policy,String dealerOrderId) {
		setId(dealerOrderId);
		setCreateDate(new Date());
		setTravelDate((mpOrderCreateCommand.getTravelDate()));
		setNumber(mpOrderCreateCommand.getNumber());
		setPlatformOrderCode(platformOrderCode);
		setDealerOrderCode(dealerOrderId);
		setPrice(mpOrderCreateCommand.getPrice());
		setScenicSpot(scenicSpot);
		setMpPolicy(policy);
		
		MPOrderStatus status=new MPOrderStatus();
		status.setPrepared(true);
		setStatus(status);
		//填充下单人信息
		MPOrderUser mpOrderUser = new MPOrderUser();
		mpOrderUser.setUserId(mpOrderCreateCommand.getOrderUserInfo().getId());
		mpOrderUser.setIdCardNo(mpOrderCreateCommand.getOrderUserInfo()
				.getBaseInfo().getIdCardNo());
		mpOrderUser.setMobile(mpOrderCreateCommand.getOrderUserInfo()
				.getContactInfo().getMobile());
		mpOrderUser.setName(mpOrderCreateCommand.getOrderUserInfo()
				.getBaseInfo().getName());
		mpOrderUser.setId(UUIDGenerator.getUUID());
		mpOrderUser.setUserId(mpOrderCreateCommand.getOrderUserInfo().getId());
		setOrderUser(mpOrderUser);
		//取票人信息
		MPOrderUser takeTicketUser = new MPOrderUser();
		if(StringUtils.isBlank(mpOrderCreateCommand.getTakeTicketUserInfo().getId())){
			takeTicketUser.setUserId(null);
		}else{
			takeTicketUser.setUserId(mpOrderCreateCommand.getTakeTicketUserInfo().getId());
		}
		takeTicketUser.setIdCardNo(mpOrderCreateCommand.getTakeTicketUserInfo()
				.getBaseInfo().getIdCardNo());
		takeTicketUser.setMobile(mpOrderCreateCommand.getTakeTicketUserInfo()
				.getContactInfo().getMobile());
		takeTicketUser.setName(mpOrderCreateCommand.getTakeTicketUserInfo()
				.getBaseInfo().getName());

		takeTicketUser.setId(UUIDGenerator.getUUID());
		setTakeTicketUser(takeTicketUser);
		if(StringUtils.isNotBlank(mpOrderCreateCommand.getCompanyId())){
			takeTicketUser.setCompanyId(mpOrderCreateCommand.getCompanyId());
			takeTicketUser.setCompanyName(mpOrderCreateCommand.getCompanyName());
		}

		if(StringUtils.isNotBlank(mpOrderCreateCommand.getDepartmentId())){
			takeTicketUser.setDepartmentId(mpOrderCreateCommand.getDepartmentId());
			takeTicketUser.setDepartmentName(mpOrderCreateCommand.getDepartmentName());
		}

		//游客信息
		if (mpOrderCreateCommand.getTraveler() != null) {
			List<MPOrderUser> travelers = new ArrayList<MPOrderUser>();
			for (UserDTO userDTO : mpOrderCreateCommand.getTraveler()) {
				MPOrderUser traveler = new MPOrderUser();
				traveler.setIdCardNo(userDTO.getBaseInfo().getIdCardNo());
				traveler.setMobile(userDTO.getContactInfo().getMobile());
				traveler.setName(userDTO.getBaseInfo().getName());
				traveler.setOrder(this);
				traveler.setId(UUIDGenerator.getUUID());
				travelers.add(traveler);
			}
			setTravelers(travelers);
		}
	}

	/**
	 * 取消订单
	 */
	public void cancelOrder(String cancelRemark) {
		setCancelRemark(cancelRemark);
		status.setCancel(true);
		status.setPrepared(false);
	}

	public void syncStatusFromSlfx(MPOrderStatusDTO statusDTO) {
		status.setCancel(statusDTO.getCancel());
		status.setOutOfDate(statusDTO.getOutOfDate());
		status.setPrepared(statusDTO.getPrepared());
		status.setUsed(statusDTO.getUsed());
	}

}