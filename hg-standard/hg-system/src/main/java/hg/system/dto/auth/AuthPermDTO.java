package hg.system.dto.auth;

import hg.system.dto.EmbeddDTO;

/**
 * @类功能说明：资源DTO
 * @类修改者：zzb
 * @修改日期：2014年10月16日上午10:56:56
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年10月16日上午10:56:56
 *
 */
@SuppressWarnings("serial")
public class AuthPermDTO extends EmbeddDTO {
	
	/**
	 * 资源id
	 */
	private String id;
	
	/**
	 * 资源名称
	 */
	private String displayName;
	
	/**
	 * 资源链接
	 */
	private String url;

	/**
	 * 资源类型
	 */
	private Short permType;

	/**
	 * 角色
	 */
	private String permRole;

	/**
	 * 上级资源
	 */
	private String parentId;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Short getPermType() {
		return permType;
	}

	public void setPermType(Short permType) {
		this.permType = permType;
	}

	public String getPermRole() {
		return permRole;
	}

	public void setPermRole(String permRole) {
		this.permRole = permRole;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}