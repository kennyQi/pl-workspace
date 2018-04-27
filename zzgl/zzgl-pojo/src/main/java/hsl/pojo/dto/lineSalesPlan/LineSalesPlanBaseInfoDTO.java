package hsl.pojo.dto.lineSalesPlan;
import hsl.pojo.dto.BaseDTO;
import java.util.Date;
/**
 * @类功能说明：线路销售方案基本信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/11/28 11:09
 */
public class LineSalesPlanBaseInfoDTO extends BaseDTO {
	/**
	 * 方案名称
	 */
	private String planName;
	/**
	 * 方案类型
	 */
	private Integer planType;
	/**
	 * 方案的图片
	 */
	private String  imageUri;
	/**
	 * 创建日期
	 */
	private String createDate;
	/**
	 * 最后修改日期
	 */
	private String lastUpdateDate;
	/**
	 * 方案的规则描述
	 */
	private String planRule;

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getPlanType() {
		return planType;
	}

	public void setPlanType(Integer planType) {
		this.planType = planType;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getPlanRule() {
		return planRule;
	}

	public void setPlanRule(String planRule) {
		this.planRule = planRule;
	}
}
