package hsl.pojo.dto.line;

import hsl.pojo.dto.BaseDTO;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class LinePictureFolderDTO extends BaseDTO{
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		this.pictureList = pictureList;
	}

	
}
