package hg.dzpw.app.pojo.qo;

import hg.common.component.BaseQo;

/**
 * @类功能说明：入园设备查询对象
 * @类修改者：
 * @修改日期：2014-11-12下午4:29:48
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-12下午4:29:48
 */
public class ClientDeviceQo extends BaseQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 景区
	 */
	private ScenicSpotQo scenicSpotQo;

	public ScenicSpotQo getScenicSpotQo() {
		return scenicSpotQo;
	}

	public void setScenicSpotQo(ScenicSpotQo scenicSpotQo) {
		this.scenicSpotQo = scenicSpotQo;
	}
	
	public ClientDeviceQo(){}
	
	public ClientDeviceQo(ScenicSpotQo qo){
		this.scenicSpotQo = qo;
	}
}
