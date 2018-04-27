package slfx.xl.pojo.dto.line;

import java.util.Date;

import slfx.xl.pojo.dto.BaseXlDTO;
import slfx.xl.pojo.dto.route.DayRouteDTO;

/**
 * 
 * @类功能说明：线路图片DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年4月22日下午3:41:18
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class LineImageDTO extends BaseXlDTO{
	/**
	 *  线路
	 */
	private LineDTO line;
	
	/**
	 * 行程
	 */
	private DayRouteDTO dayRoute;
	
	/**
	 * 原图(可设置返回图片规格)
	 */
	private String site;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 图片标识
	 */
	private String imageId;
	
	/**
	 * 图片JSON
	 */
	private String urlMapsJSON;
	
	/**
	 * 创建时间 
	 */
	private Date createDate;
	
	
	public LineDTO getLine() {
		return line;
	}

	public void setLine(LineDTO line) {
		this.line = line;
	}

	public DayRouteDTO getDayRoute() {
		return dayRoute;
	}

	public void setDayRoute(DayRouteDTO dayRoute) {
		this.dayRoute = dayRoute;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getUrlMapsJSON() {
		return urlMapsJSON;
	}

	public void setUrlMapsJSON(String urlMapsJSON) {
		this.urlMapsJSON = urlMapsJSON;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
