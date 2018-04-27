package lxs.domain.model.line;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lxs.domain.model.M;


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
@Table(name = M.TABLE_PREFIX_XL + "SELECTIVE_LINE")
@SuppressWarnings("serial")
public class LineSelective extends BaseModel {
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
	 * 关联线路信息
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LINE_ID")
	private Line line;
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

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}
}
