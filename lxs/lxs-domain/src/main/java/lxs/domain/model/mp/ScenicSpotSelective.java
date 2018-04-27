package lxs.domain.model.mp;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lxs.domain.model.M;


/**
 * @类功能说明：景区精选
 * @备注：
 * @类修改者：
 * @修改日期：2016年3月4日 15:25:16
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：guok
 * @创建时间：2016年3月4日 15:25:21
 */
@Entity
@Table(name = M.TABLE_PREFIX_MP + "SELECTIVE_SCENICSPOT")
@SuppressWarnings("serial")
public class ScenicSpotSelective extends BaseModel {
	/**
	 * 排序
	 * */
	@Column(name = "SORT", columnDefinition = M.NUM_COLUM)
	private Integer sort;

	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "NAME")
	private String name;

	/**
	 * 关联景区信息
	 */
	@Column(name="SCENICSPOT_ID")
	private String scenicSpotID;
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getScenicSpotID() {
		return scenicSpotID;
	}

	public void setScenicSpotID(String scenicSpotID) {
		this.scenicSpotID = scenicSpotID;
	}

}
