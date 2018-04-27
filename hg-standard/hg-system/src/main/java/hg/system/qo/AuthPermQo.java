package hg.system.qo;

import hg.common.model.qo.DwzBasePaginQo;

/**
 * 
 * @类功能说明：查询资源Qo
 * @类修改者：zzb
 * @修改日期：2014年10月16日上午10:38:03
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年10月16日上午10:38:03
 * 
 */
@SuppressWarnings("serial")
public class AuthPermQo extends DwzBasePaginQo {

	/**
	 * 资源类型
	 */
	private String permType;

	/**
	 * 资源链接模糊
	 */
	private String urlLike;

	/**
	 * 上级资源
	 */
	private String parentId;

	public String getPermType() {
		return permType;
	}

	public void setPermType(String permType) {
		this.permType = permType;
	}

	public String getUrlLike() {
		return urlLike;
	}

	public void setUrlLike(String urlLike) {
		this.urlLike = urlLike;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
