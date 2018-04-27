package hsl.pojo.dto.hotel;
import hsl.pojo.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.List;
/**
 * @类功能说明：酒店信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年3月26日上午10:50:03
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class HotelDTO extends BaseDTO{
	/**
	 * 酒店id
	 */
    protected String hotelId;
    /**
     * 最小价格
     */
    protected BigDecimal lowRate;
    /**
     * 最低价格的货币
     */
    protected String currencyCode;
    /**
     * 酒店设置
     */
    protected String facilities;
    /**
     * 距离
     */
    protected BigDecimal distance;
    /**
     * 预定规则
     */
    protected List<BookingRuleDTO> bookingRules;
    /**
     * 担保规则
     */
    protected List<GuaranteeRuleDTO> guaranteeRules;
    /**
     * 预付规则
     */
    protected List<PrepayRuleDTO> prepayRules;
    /**
     * 增值服务
     */
    protected List<ValueAddDTO> valueAdds;
    /**
     * 促销规则
     */
    protected List<DrrRuleDTO> drrRules;
    /**
     * 房间
     */
    protected List<RoomDTO> rooms;
    /**
     * 酒店信息
     */
    protected DetailDTO detail;
    /**
     * 图片信息
     */
    protected List<HotelImgDTO> images;
    /**
     * 礼物信息
     */
    protected List<GiftDTO> gifts;
    /**
     * 有用的信息
     */
    protected List<HAvailPolicyDTO> hAvailPolicys;
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public BigDecimal getLowRate() {
		return lowRate;
	}
	public void setLowRate(BigDecimal lowRate) {
		this.lowRate = lowRate;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getFacilities() {
		return facilities;
	}
	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}
	public BigDecimal getDistance() {
		return distance;
	}
	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}
	public List<BookingRuleDTO> getBookingRules() {
		return bookingRules;
	}
	public void setBookingRules(List<BookingRuleDTO> bookingRules) {
		this.bookingRules = bookingRules;
	}
	public List<GuaranteeRuleDTO> getGuaranteeRules() {
		return guaranteeRules;
	}
	public void setGuaranteeRules(List<GuaranteeRuleDTO> guaranteeRules) {
		this.guaranteeRules = guaranteeRules;
	}
	public List<PrepayRuleDTO> getPrepayRules() {
		return prepayRules;
	}
	public void setPrepayRules(List<PrepayRuleDTO> prepayRules) {
		this.prepayRules = prepayRules;
	}
	public List<ValueAddDTO> getValueAdds() {
		return valueAdds;
	}
	public void setValueAdds(List<ValueAddDTO> valueAdds) {
		this.valueAdds = valueAdds;
	}
	public List<DrrRuleDTO> getDrrRules() {
		return drrRules;
	}
	public void setDrrRules(List<DrrRuleDTO> drrRules) {
		this.drrRules = drrRules;
	}
	public List<RoomDTO> getRooms() {
		return rooms;
	}
	public void setRooms(List<RoomDTO> rooms) {
		this.rooms = rooms;
	}
	
	public DetailDTO getDetail() {
		return detail;
	}
	public void setDetail(DetailDTO detail) {
		this.detail = detail;
	}
	public List<HotelImgDTO> getImages() {
		return images;
	}
	public void setImages(List<HotelImgDTO> images) {
		this.images = images;
	}
	public List<GiftDTO> getGifts() {
		return gifts;
	}
	public void setGifts(List<GiftDTO> gifts) {
		this.gifts = gifts;
	}
	public List<HAvailPolicyDTO> gethAvailPolicys() {
		return hAvailPolicys;
	}
	public void sethAvailPolicys(List<HAvailPolicyDTO> hAvailPolicys) {
		this.hAvailPolicys = hAvailPolicys;
	}


}
