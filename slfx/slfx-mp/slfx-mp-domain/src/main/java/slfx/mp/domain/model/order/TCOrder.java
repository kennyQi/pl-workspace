package slfx.mp.domain.model.order;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.beanutils.BeanUtils;

import slfx.api.v1.request.command.mp.MPOrderCreateCommand;
import slfx.mp.domain.model.M;
import slfx.mp.pojo.dto.order.MPOrderUserInfoDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 同程门票订单
 * 
 * @author Administrator
 */
@Entity
@Table(name = M.TABLE_PREFIX + "TC_ORDER")
public class TCOrder extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 所属平台订单
	 */
	@Column(name = "PLATFORM_ORDER_ID", length = 64)
	private String platformOrderId;
	/**
	 * 预订人姓名
	 */
	@Column(name = "BOOK_NAME", length = 64)
	private String bookName;
	/**
	 * 预订人手机
	 */
	@Column(name = "BOOK_MOBILE", length = 64)
	private String bookMobile;
	/**
	 * 预订人地址
	 */
	@Column(name = "BOOK_ADDRESS", length = 256)
	private String bookAddress;
	/**
	 * 预订人邮编
	 */
	@Column(name = "BOOK_POSTCODE", length = 16)
	private String bookPostcode;
	/**
	 * 预订人邮箱
	 */
	@Column(name = "BOOK_EMAIL", length = 256)
	private String bookEmail;
	/**
	 * 政策id
	 */
	@Column(name = "POLICY_ID", length = 64)
	private String policyId;
	/**
	 * 预订票数
	 */
	@Column(name = "NUMBER_", columnDefinition = M.NUM_COLUM)
	private Integer number;
	/**
	 * 旅游日期
	 */
	@Column(name = "TRAVEL_DATE", columnDefinition = M.DATE_COLUM)
	private Date travelDate;
	/**
	 * 预订人IP
	 */
	@Column(name = "BOOK_MAN_IP", length = 64)
	private String bookManIP;
	/**
	 * 身份证号
	 */
	@Column(name = "ID_CARD_NO", length = 64)
	private String idCardNo;
	/**
	 * 游客(存JSON)
	 */
	@Transient
	public List<Traveler> traveler;
	
	@JSONField(serialize = false)
	@Column(name = "TRAVELER_JSON", length = 4000)
	private String travelerJson;
	
	/**
	 * 下单
	 * 
	 * @param command
	 * @param platformOrderId		平台订单号
	 * @param policyId				供应商政策ID
	 */
	public void apiOrderTicket(MPOrderCreateCommand command, String platformOrderId, String policyId) {
		setId(UUIDGenerator.getUUID());
		setPlatformOrderId(platformOrderId);
		setBookName(command.getOrderUserInfo().getName());
		setBookMobile(command.getOrderUserInfo().getMobile());
		setBookAddress(command.getOrderUserInfo().getAddress());
		setBookPostcode(command.getOrderUserInfo().getPostcode());
		setBookEmail(command.getOrderUserInfo().getEmail());
		setPolicyId(policyId);
		setNumber(command.getNumber());
		setTravelDate(command.getTravelDate());
		setBookManIP(command.getBookManIP());
		setIdCardNo(command.getOrderUserInfo().getIdCardNo());
		if (command.getTraveler() != null) {
			List<Traveler> travelers = new ArrayList<Traveler>();
			for (MPOrderUserInfoDTO dto : command.getTraveler()) {
				Traveler traveler = new Traveler();
				traveler.setAccompany(true);
				try {
					BeanUtils.copyProperties(traveler, dto);
					travelers.add(traveler);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			setTraveler(travelers);
		}
	}

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookMobile() {
		return bookMobile;
	}

	public void setBookMobile(String bookMobile) {
		this.bookMobile = bookMobile;
	}

	public String getBookAddress() {
		return bookAddress;
	}

	public void setBookAddress(String bookAddress) {
		this.bookAddress = bookAddress;
	}

	public String getBookPostcode() {
		return bookPostcode;
	}

	public void setBookPostcode(String bookPostcode) {
		this.bookPostcode = bookPostcode;
	}

	public String getBookEmail() {
		return bookEmail;
	}

	public void setBookEmail(String bookEmail) {
		this.bookEmail = bookEmail;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public String getBookManIP() {
		return bookManIP;
	}

	public void setBookManIP(String bookManIP) {
		this.bookManIP = bookManIP;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public List<Traveler> getTraveler() {
		if (traveler == null) {
			if (travelerJson != null) {
				traveler = JSON.parseArray(this.travelerJson, Traveler.class);
			} else {
				setTraveler(new ArrayList<Traveler>());
			}
		}
		return traveler;
	}

	public void setTraveler(List<Traveler> traveler) {
		this.traveler = traveler;
		if (traveler != null)
			this.travelerJson = JSON.toJSONString(traveler);
		else
			this.travelerJson = "";
	}

	public String getTravelerJson() {
		if (travelerJson == null && traveler != null) {
			travelerJson = JSON.toJSONString(traveler);
		}
		return travelerJson;
	}

	public void setTravelerJson(String travelerJson) {
		this.travelerJson = travelerJson;
	}

}