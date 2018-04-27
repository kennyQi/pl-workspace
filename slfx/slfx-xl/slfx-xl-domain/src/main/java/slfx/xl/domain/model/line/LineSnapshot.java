package slfx.xl.domain.model.line;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import slfx.xl.domain.model.M;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @类功能说明：线路快照
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日下午1:54:57
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX+"LINE_SNAPSHOT")
public class LineSnapshot extends BaseModel {

	/**
	 * 快照来源实体
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LINE_ID")
	private Line line;

	/**
	 * 线路名称
	 */
	@Column(name="LINE_NAME", length=64)
	private String lineName;

	/**
	 * 线路类别
	 */
	@Column(name="TYPE",columnDefinition =M.NUM_COLUM)
	private Integer type;

	/**
	 * 出发地城市编号
	 */
	@Column(name = "STARTING_CITY",length=12)
	private String starting;

	/**
	 * 所有快照信息，Line对象的JSON
	 */
	@JSONField(serialize = false)
	@Column(name = "ALL_INFO_LINE_JSON",columnDefinition = M.TEXT_COLUM)
	private String allInfoLineJSON;
	
	/**
	 * 快照创建时间
	 */
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@Column(name = "CITY_OF_TYPE")
	private String cityOfType;
	
	public LineSnapshot() {
		if (this.line != null) {
			create(this.line);
		}
	}
	
	public LineSnapshot(Line line) {
		create(line);
	}
	
	//	创建快照
	public void create(Line line) {
		
		//	实体关联
		this.setLine(line);
		
		//	订单搜索可能会用到的几个线路属性
		if(null != line.getBaseInfo()){
			this.setLineName(line.getBaseInfo().getName());
			this.setStarting(line.getBaseInfo().getStarting());
			this.setType(line.getBaseInfo().getType());
			this.setCityOfType(line.getBaseInfo().getCityOfType());	
		}
		//	去掉无快照意义的属性，进行完整快照
		Line sLine = JSON.parseObject(JSON.toJSONString(line), Line.class);
//		sLine.setDateSalePriceList(null);
//		sLine.setFolderList(null);
		
		this.setAllInfoLineJSON(JSON.toJSONString(sLine));
		
		this.setCreateDate(new Date());
		
		setId(UUIDGenerator.getUUID());
	}
	
	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getStarting() {
		return starting;
	}

	public void setStarting(String starting) {
		this.starting = starting;
	}

	public String getAllInfoLineJSON() {
		return allInfoLineJSON;
	}

	public void setAllInfoLineJSON(String allInfoLineJSON) {
		this.allInfoLineJSON = allInfoLineJSON;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCityOfType() {
		return cityOfType;
	}

	public void setCityOfType(String cityOfType) {
		this.cityOfType = cityOfType;
	}
	
	
}