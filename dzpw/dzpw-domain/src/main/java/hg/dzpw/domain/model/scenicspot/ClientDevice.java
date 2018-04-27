package hg.dzpw.domain.model.scenicspot;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hg.common.component.BaseModel;
import hg.dzpw.domain.model.M;

/**
 * @类功能说明:客户端设备
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:08:29
 * @版本：V1.0
 */
@Entity
@Table(name = M.TABLE_PREFIX + "CLIENT_DEVICE")
public class ClientDevice extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属景区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCENIC_SPOT_ID")
	private ScenicSpot scenicSpot;

	public ScenicSpot getScenicSpot() {
		return scenicSpot;
	}
	public void setScenicSpot(ScenicSpot scenicSpot) {
		this.scenicSpot = scenicSpot;
	}
}