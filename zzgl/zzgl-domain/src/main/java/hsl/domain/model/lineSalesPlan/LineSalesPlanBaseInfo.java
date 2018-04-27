package hsl.domain.model.lineSalesPlan;

import hg.common.component.BaseModel;
import hsl.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
@Embeddable
public class LineSalesPlanBaseInfo{
	/**
	 * 方案名称
	 */
	@Column(name="PLAN_NAME",length = 255)
	private String planName;
	/**
	 * 方案类型---团购
	 */
	public static  final  Integer PLANTYPE_GROUP=1;
	/**
	 * 方案类型---秒杀
	 */
	public static  final  Integer PLANTYPE_SECKILL=2;
	/**
	 * 方案类型
	 */
	@Column(name ="PLAN_TYPE",columnDefinition = M.TYPE_NUM_COLUM)
	private Integer planType;
	/**
	 * 方案的图片
	 */
	@Column(name="IMAGEURI",length = 1024)
	private String  imageUri;
	/**
	 * 创建日期
	 */
	@Column(name="CREATEDATE",columnDefinition = M.DATE_COLUM)
	private Date createDate;
	/**
	 * 最后修改日期
	 */
	@Column(name="LASTUPDATEDATE",columnDefinition = M.DATE_COLUM)
	private Date lastUpdateDate;
	/**
	 * 方案的规则描述
	 */
	@Column(name="PLANRULE",columnDefinition = M.TEXT_COLUM)
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getPlanRule() {
		return planRule;
	}

	public void setPlanRule(String planRule) {
		this.planRule = planRule;
	}
}
