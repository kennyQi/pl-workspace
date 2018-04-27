package hsl.domain.model.xl;
import hg.common.component.BaseModel;
import hsl.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;



import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @类功能说明：
 * @备注：
 * @类修改者：
 * @修改日期：2015-01-27 14:30:59
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：cc
 * @创建时间：2015-01-27 14:30:59
 */

@Entity
@Table(name = M.TABLE_PREFIX_HSL_XL + "LINE_IMAGE")
@SuppressWarnings("serial")
public class LineImage  extends BaseModel {
	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 512)
	private String name;
	
	/**
	 * 图片标识
	 */
	@Column(name = "IMAGEID", length = 1024)
	private String imageId;
	
	/**
	 * 图片JSON
	 */
	@Column(name = "IMAGEJSON", length = 1024)
	private String fdfsFileInfoJSON;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	
	/**
	 * 所属的线路
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LINE_ID", nullable = true)
	private Line line;
	
	/**
	 * 所属的行程
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="DAYROUTE_ID", nullable = true)
	private DayRoute dayRoute;
	
	/**
	 * 图片访问相对地址，urlMaps
	 */
	@Column(name = "IMG_URL_JSON", columnDefinition = M.TEXT_COLUM)
	private String urlMapsJSON;
	
	@Transient
	//private Map<String, String> urlMaps;
	
	
	public String getFdfsFileInfoJSON() {
		return fdfsFileInfoJSON;
	}

	public void setFdfsFileInfoJSON(String fdfsFileInfoJSON) {
		this.fdfsFileInfoJSON = fdfsFileInfoJSON;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public DayRoute getDayRoute() {
		return dayRoute;
	}

	public void setDayRoute(DayRoute dayRoute) {
		this.dayRoute = dayRoute;
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
	
	


}
