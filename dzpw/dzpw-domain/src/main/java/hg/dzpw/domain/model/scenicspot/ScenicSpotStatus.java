package hg.dzpw.domain.model.scenicspot;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.Type;

/**
 * @类功能说明:景区状态
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:07:24
 * @版本：V1.0
 */
@Embeddable
public class ScenicSpotStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 是否启用
	 */
	@Type(type = "yes_no")
	@Column(name = "ACTIVATED")
	private Boolean activated = true;

	/**
	 * 是否被逻辑删除
	 */
	@Type(type = "yes_no")
	@Column(name = "REMOVED")
	private Boolean removed = false;

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}
}