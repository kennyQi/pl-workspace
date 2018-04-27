package hsl.pojo.qo.dzp.policy;

import hg.common.component.BaseQo;

import java.util.Date;
import java.util.Map;

/**电子票-门票政策快照QO
 * Created by hgg on 2016/3/8.
 */
public class DZPTicketPolicySnapshotQO extends BaseQo{
/***************************门票政策基本信息*******************************/
    /**
     * 名称
     */
    private String name;
    /**
     * 简称
     */
    private String shortName;
    /**
     * 联票OR单票介绍
     */
    private String intro;
    /**
     * 代码
     */
    private String key;
    /**
     * 单票、联票门市价/市场票面价
     */
    private Double rackRate;
    /**
     * 联票(与经销商)游玩价
     */
    private Double playPrice;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date modifyDate;
    /**
     * 售卖协议
     */
    private String saleAgreement;
    /**
     * 交通信息
     */
    private String traffic;
    /**
     * 包含景点(冗余字段)
     */
    private String scenicSpotNameStr;
    /**
     * 预定须知
     */
    private String notice;


    /***************************门票政策销售信息*******************************/

    /**
     * 最低价格（冗余字段）
     */
    private Double priceMin = 0d;
    /**
     * 退票规则
     *
     * @see hsl.pojo.util.HSLConstants.DZPWTicketPolicySellReturnRule
     */
    private Integer returnRule;
    /**
     * 退票手续费
     */
    private Double returnCost = 0d;
    /**
     * 是否过期自动退款
     */
    private Boolean autoMaticRefund;

    /***************************门票政策使用条件信息*******************************/

    /**
     * 有效天数(独立单票可入园天数 or 联票自出票后的有效天数)
     */
    private Integer validDays;
    /**
     * 单票单天可入园次数
     */
    private Integer validTimesPerDay;
    /**
     * 单票可入园总次数
     */
    private Integer validTimesTotal;
    /**
     * 是否套票使用条件优先
     */
    private Boolean groupPolicyFirst = true;
    /**
     * 使用的验证类型
     *
     * @see hsl.pojo.util.HSLConstants.DZPTicketPolicyValidUseDateType
     */
    private Integer validUseDateType;

    /***************************其他条件信息*******************************/

    /**
     * 所属的景区ID
     */
    private String scenicSpotId;
    /**
     * 所属的景区名称
     */
    private String scenicSpotName;
    /**
     * 政策类型
     *
     * @see hsl.pojo.util.HSLConstants.DZPWTicketPolicyType
     */
    private Integer type;
    /**
     * 版本
     */
    private Integer version;
    /**
     * 每日价格
     * <pre>
     * 价格日历：日期(yyyyMMdd)，当日价格
     * </pre>
     */
    private Map<String, DZPTicketPolicySnapshotDatePriceQO> price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getRackRate() {
        return rackRate;
    }

    public void setRackRate(Double rackRate) {
        this.rackRate = rackRate;
    }

    public Double getPlayPrice() {
        return playPrice;
    }

    public void setPlayPrice(Double playPrice) {
        this.playPrice = playPrice;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getSaleAgreement() {
        return saleAgreement;
    }

    public void setSaleAgreement(String saleAgreement) {
        this.saleAgreement = saleAgreement;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getScenicSpotNameStr() {
        return scenicSpotNameStr;
    }

    public void setScenicSpotNameStr(String scenicSpotNameStr) {
        this.scenicSpotNameStr = scenicSpotNameStr;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
    }

    public Integer getReturnRule() {
        return returnRule;
    }

    public void setReturnRule(Integer returnRule) {
        this.returnRule = returnRule;
    }

    public Double getReturnCost() {
        return returnCost;
    }

    public void setReturnCost(Double returnCost) {
        this.returnCost = returnCost;
    }

    public Boolean getAutoMaticRefund() {
        return autoMaticRefund;
    }

    public void setAutoMaticRefund(Boolean autoMaticRefund) {
        this.autoMaticRefund = autoMaticRefund;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public Integer getValidTimesPerDay() {
        return validTimesPerDay;
    }

    public void setValidTimesPerDay(Integer validTimesPerDay) {
        this.validTimesPerDay = validTimesPerDay;
    }

    public Integer getValidTimesTotal() {
        return validTimesTotal;
    }

    public void setValidTimesTotal(Integer validTimesTotal) {
        this.validTimesTotal = validTimesTotal;
    }

    public Boolean getGroupPolicyFirst() {
        return groupPolicyFirst;
    }

    public void setGroupPolicyFirst(Boolean groupPolicyFirst) {
        this.groupPolicyFirst = groupPolicyFirst;
    }

    public Integer getValidUseDateType() {
        return validUseDateType;
    }

    public void setValidUseDateType(Integer validUseDateType) {
        this.validUseDateType = validUseDateType;
    }

    public String getScenicSpotId() {
        return scenicSpotId;
    }

    public void setScenicSpotId(String scenicSpotId) {
        this.scenicSpotId = scenicSpotId;
    }

    public String getScenicSpotName() {
        return scenicSpotName;
    }

    public void setScenicSpotName(String scenicSpotName) {
        this.scenicSpotName = scenicSpotName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Map<String, DZPTicketPolicySnapshotDatePriceQO> getPrice() {
        return price;
    }

    public void setPrice(Map<String, DZPTicketPolicySnapshotDatePriceQO> price) {
        this.price = price;
    }
}
