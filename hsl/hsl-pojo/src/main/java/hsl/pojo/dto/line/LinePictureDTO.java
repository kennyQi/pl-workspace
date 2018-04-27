package hsl.pojo.dto.line;

import hsl.pojo.dto.BaseDTO;

import java.util.Date;

@SuppressWarnings("serial")
public class LinePictureDTO extends BaseDTO{
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
