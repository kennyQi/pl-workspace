package plfx.jd.pojo.dto.ylclient.hotel;
/**
 * 
 * @类功能说明：酒店特殊信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年3月26日上午10:42:48
 * @版本：V1.0
 *
 */
public class AvailPolicyDTO {

	/**
	 * 提示内容
	 */
    protected String availPolicyText;
    /**
	 * 有效开始时间
	 */
    protected String startDate;
    /**
	 * 有效结束时间
	 */
    protected String endDate;

    public String getAvailPolicyText() {
        return availPolicyText;
    }
    public void setAvailPolicyText(String value) {
        this.availPolicyText = value;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String value) {
        this.startDate = value;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String value) {
        this.endDate = value;
    }

}
