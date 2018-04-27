package plfx.xl.pojo.dto.line;

import java.util.Date;

import plfx.xl.pojo.dto.BaseXlDTO;

/**
 * @类功能说明：线路图片文件夹DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月18日下午1:59:23
 * @版本：V1.0
 */
public class LinePictureDTO extends BaseXlDTO {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 位置(图片访问地址)
	 */
	private String site;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 所属的文件夹
	 */
	private LinePictureFolderDTO folderDto;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site == null ? null : site.trim();
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public LinePictureFolderDTO getFolderDto() {
		return folderDto;
	}
	public void setFolderDto(LinePictureFolderDTO folderDto) {
		this.folderDto = folderDto;
	}
}