package lxs.domain.model.app;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @类功能说明：主题表
 * @类修改者：
 * @修改日期：2015年9月18日上午10:10:31
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月18日上午10:10:31
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_APP + "SUBJECT")
public class Subject extends BaseModel {

	/**
	 * 线路
	 */
	public static final Integer SUNGECT_TYPE_LINE = 1;
	/**
	 * 门票
	 */
	public static final Integer SUNGECT_TYPE_MENPIAO = 2;

	/**
	 * 主题名
	 */
	@Column(name = "SUNGECT_NAME", length = 512)
	private String subjectName;

	/**
	 * 主题分类
	 */
	@Column(name = "SUNGECT_TYPE", columnDefinition = M.NUM_COLUM)
	private Integer subjectType;

	/**
	 * 该主题产品数量 每次线路设置或取消线路主题事都要更新相应字段
	 */
	@Column(name = "PRODUCT_SUM", length = 512)
	private String productSUM;

	/**
	 * 排序
	 * */
	@Column(name = "SORT", columnDefinition = M.NUM_COLUM)
	private Integer sort;

	/**
	 * 类型
	 * */
	/** 手动添加 */
	public final static Integer MANUAL = 0;
	/** 同步 */
	public final static Integer SYNC = 1;

	@Column(name = "TYPE", columnDefinition = M.NUM_COLUM)
	private Integer type;

	/**
	 * 添加时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	public String getProductSUM() {
		return productSUM;
	}

	public void setProductSUM(String productSUM) {
		this.productSUM = productSUM;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
