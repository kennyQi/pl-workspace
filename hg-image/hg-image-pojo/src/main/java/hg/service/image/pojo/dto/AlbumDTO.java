package hg.service.image.pojo.dto;

import java.util.Date;
import hg.service.image.base.BaseDTO;

/**
 * @类功能说明：相册DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年9月1日下午2:44:35
 */
public class AlbumDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 相册标题
	 */
	private String title;

	/**
	 * 相册备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 所有者id
	 */
	private String ownerId;
	
	/**
	 * 对应ID
	 */
	private String fromId;

	/**
	 * 用途
	 */
	private ImageUseTypeDTO useType;

	/**
	 * 层级路径
	 */
	private String path;
	
	/**
	 * 是否回收(回收站)
	 */
	private Integer isRefuse = 0;
	
	/**
	 * 扩展参数
	 */
	private ExtArgsDTO extArgs;

	public ExtArgsDTO getExtArgs() {
		return extArgs;
	}
	public void setExtArgs(ExtArgsDTO extArgs) {
		this.extArgs = extArgs;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public ImageUseTypeDTO getUseType() {
		return useType;
	}
	public void setUseType(ImageUseTypeDTO useType) {
		this.useType = useType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getIsRefuse() {
		return isRefuse;
	}
	public void setIsRefuse(int isRefuse) {
		if(isRefuse > 1)
			isRefuse = 1;
		this.isRefuse = isRefuse;
	}
}