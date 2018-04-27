package slfx.xl.pojo.qo;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：线路图片查询QO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年4月22日下午3:22:47
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class LineImageQO extends BaseQo{
	/**
	 * 线路id
	 */
	public String lineId;
	
	/**
	 * 行程id
	 */
	public String dayRouteId;
	
	/**
	 * 图片id
	 */
	public String imageId;
	
	/**
	 * 是否根据创建时间排序
	 */
	public Boolean OrderByCreateDate = true;

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getDayRouteId() {
		return dayRouteId;
	}

	public void setDayRouteId(String dayRouteId) {
		this.dayRouteId = dayRouteId;
	}

	public Boolean getOrderByCreateDate() {
		return OrderByCreateDate;
	}

	public void setOrderByCreateDate(Boolean orderByCreateDate) {
		OrderByCreateDate = orderByCreateDate;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
}

