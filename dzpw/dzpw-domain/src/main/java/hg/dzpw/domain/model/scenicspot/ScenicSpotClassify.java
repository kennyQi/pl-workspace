package hg.dzpw.domain.model.scenicspot;

import hg.common.component.BaseModel;
import hg.dzpw.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @类功能说明：景区分类
 * @类修改者：
 * @修改日期：2015-3-3上午11:21:53
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-3上午11:21:53
 */
@Entity
@Table(name = M.TABLE_PREFIX + "SCENIC_SPOT_CLASSIFY")
public class ScenicSpotClassify extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	/**
	 * 代码
	 */
	@Column(name = "CODE_", length = 64)
	private String code;

	/**
	 * 说明
	 */
	@Column(name = "REMARK", length = 512)
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
