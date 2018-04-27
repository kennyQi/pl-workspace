package slfx.xl.domain.model.line;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.common.util.file.FdfsFileInfo;
import hg.service.image.pojo.dto.ImageDTO;
import hg.service.image.pojo.dto.ImageSpecDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import slfx.xl.domain.model.M;
import slfx.xl.pojo.command.line.CreateLineImageCommand;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：线路图片model
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年4月22日下午3:38:58
 * @版本：V1.0
 *
 */
@Entity
@Table(name = M.TABLE_PREFIX + "LINE_IMAGE")
public class LineImage extends BaseModel {
	private static final long serialVersionUID = 1L;
	
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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="LINE_ID", nullable = true)
	private Line line;
	
	/**
	 * 所属的行程
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="DAYROUTE_ID", nullable = true)
	private DayRoute dayRoute;
	
	/**
	 * 图片访问相对地址，urlMaps
	 */
	@Column(name = "IMG_URL_JSON", columnDefinition = M.TEXT_COLUM)
	private String urlMapsJSON;
	
	@Transient
	private Map<String, String> urlMaps;
	
	/**
	 * 
	 * @方法功能说明：线路图片创建
	 * @修改者名字：yuqz
	 * @修改时间：2015年4月24日下午2:06:12
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param folder
	 * @return:void
	 * @throws
	 */
	public void create(ImageDTO imageDTO, CreateLineImageCommand command) {
		setId(UUIDGenerator.getUUID());
		setName(command.getName());
		setImageId(command.getImageId());
		setCreateDate(new Date());
		//如果有行程id就是行程图只和行程关联，否则只和线路关联是首图.
		if(StringUtils.isNotBlank(command.getDayRouteId())){
			DayRoute dayRoute = new DayRoute();
			dayRoute.setId(command.getDayRouteId());
			setDayRoute(dayRoute);
		}else{
			Line line = new Line();
			line.setId(command.getLineId());
			setLine(line);
		}
		Map<String, String> specImageUrlMap = new HashMap<String, String>();
		if (imageDTO.getSpecImageMap() != null) {
			for (Entry<String, ImageSpecDTO> entry : imageDTO.getSpecImageMap()
					.entrySet()) {
				specImageUrlMap.put(
						entry.getKey(),
						JSON.parseObject(entry.getValue().getFileInfo(),
								FdfsFileInfo.class).getUri());
			}
			setUrlMaps(specImageUrlMap);
			setUrlMapsJSON(JSON.toJSONString(specImageUrlMap));
		}
		
	}

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
		if (StringUtils.isBlank(urlMapsJSON) && urlMaps != null) {
			setUrlMapsJSON(JSON.toJSONString(urlMaps));
		}
		return urlMapsJSON;
	}

	public void setUrlMapsJSON(String urlMapsJSON) {
		this.urlMapsJSON = urlMapsJSON;
	}
	
	public void setUrlMaps(Map<String, String> urlMaps) {
		this.urlMaps = urlMaps;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getUrlMaps() {
		if (urlMaps == null && StringUtils.isNotBlank(urlMapsJSON)) {
			urlMaps = JSON.parseObject(urlMapsJSON, HashMap.class);
		}
		return urlMaps;
	}

	/**
	 * @方法功能说明：线路图片复制
	 * @修改者名字：yuqz
	 * @修改时间：2015年4月30日下午2:09:47
	 * @修改内容：
	 * @参数：
	 * @return:
	 * @throws
	 */
	public void copy(LineImage lineImage) {
		setId(UUIDGenerator.getUUID());
		if(StringUtils.isNotBlank(lineImage.getName())){
			setName(lineImage.getName());
		}
		if(StringUtils.isNotBlank(lineImage.getImageId())){
			setImageId(lineImage.getImageId());
		}
		if(StringUtils.isNotBlank(lineImage.getFdfsFileInfoJSON())){
			setFdfsFileInfoJSON(lineImage.getFdfsFileInfoJSON());
		}
		if(null != lineImage.getCreateDate()){
			setCreateDate(lineImage.getCreateDate());
		}
//		if(null != lineImage.getLine()){
//			setLine(lineImage.getLine());
//		}
//		if(null != lineImage.getDayRoute()){
//			setDayRoute(lineImage.getDayRoute());
//		}
		if(StringUtils.isNotBlank(lineImage.getUrlMapsJSON())){
			setUrlMapsJSON(lineImage.getUrlMapsJSON());
		}
	}
	
}