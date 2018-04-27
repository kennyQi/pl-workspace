package hg.dzpw.app.pojo.qo;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：景区图片查询实体
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015-12-11下午2:13:50
 * @版本：
 */
public class ScenicSpotPicQo extends BaseQo {

	private static final long serialVersionUID = 1L;
	/**
	 * 关联景区
	 */
	private ScenicSpotQo scenicSpotQo;
	/**
	 * 图片地址
	 */
	private String picUrl;
	/**
	 * 主键id
	 */
	private String id;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ScenicSpotQo getScenicSpotQo() {
		return scenicSpotQo;
	}

	public void setScenicSpotQo(ScenicSpotQo scenicSpotQo) {
		this.scenicSpotQo = scenicSpotQo;
	}

}
