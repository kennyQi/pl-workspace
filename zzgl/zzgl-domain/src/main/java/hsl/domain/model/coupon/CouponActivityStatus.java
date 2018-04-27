package hsl.domain.model.coupon;
import hsl.domain.model.M;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/***********************************************************************
 * Module:  CouponActivityStatus.java
 * Author:  yuxx
 * Purpose: Defines the Class CouponActivityStatus
 ***********************************************************************/
/**
 * @类功能说明：卡券活动状态
 * @类修改者：
 * @修改日期：2014年10月15日上午8:31:42
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日上午8:31:42
 */
@Embeddable
public class CouponActivityStatus {
	
	/**
	 * 当前活动状态
	 * 1未审核 2审核未通过 3审核成功 4发放中 5名额已满 6活动结束7已取消
	 */
	@Column(name="CURRENTVALUE",columnDefinition=M.TYPE_NUM_COLUM)
    private Integer currentValue;
    /**
     * 已发放数量
     */
	@Column(name="ISSUENUM",columnDefinition=M.NUM_COLUM)
    private Long issueNum;
    
    public Integer getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}
	
	public Long getIssueNum() {
		return issueNum;
	}
	public void setIssueNum(Long issueNum) {
		this.issueNum = issueNum;
	}
	/**
    * 可以发放
    * @方法功能说明：
    * @修改者名字：chenxy
    * @修改时间：2014年10月15日上午8:33:57
    * @修改内容：
    * @参数：@return
    * @return:java.lang.Boolean
    * @throws
    */
   public java.lang.Boolean issueEnable() {
      return null;
   }
   /**
    * 可以使用
    * @方法功能说明：
    * @修改者名字：chenxy
    * @修改时间：2014年10月15日上午8:34:08
    * @修改内容：
    * @参数：@return
    * @return:java.lang.Boolean
    * @throws
    */
   public java.lang.Boolean useEnable() {
      return null;
   }

}