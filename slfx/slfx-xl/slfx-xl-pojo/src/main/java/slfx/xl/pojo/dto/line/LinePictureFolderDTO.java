package slfx.xl.pojo.dto.line;

import java.util.Date;
import java.util.List;
import slfx.xl.pojo.dto.BaseXlDTO;

/**
 * @类功能说明：线路图片文件夹DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月18日下午1:59:23
 * @版本：V1.0
 */
public class LinePictureFolderDTO extends BaseXlDTO {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 是否主文件夹
	 */
	private boolean matter = false;
	
	/**
	 * 文件夹中的图片
	 */
	private List<LinePictureDTO> pictureList;
	
	/**
	 * 所属的线路
	 */
	private LineDTO line;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public boolean isMatter() {
		return matter;
	}
	public void setMatter(boolean matter) {
		this.matter = matter;
	}
	public List<LinePictureDTO> getPictureList() {
		return pictureList;
	}
	public void setPictureList(List<LinePictureDTO> pictureList) {
		this.pictureList = (null == pictureList || pictureList.size() < 1)?null:pictureList;
	}
	public LineDTO getLine() {
		return line;
	}
	public void setLine(LineDTO line) {
		this.line = line;
	}
	
}