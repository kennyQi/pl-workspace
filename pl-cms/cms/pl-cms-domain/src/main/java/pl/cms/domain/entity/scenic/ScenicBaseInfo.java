package pl.cms.domain.entity.scenic;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import pl.cms.domain.entity.M;
import pl.cms.domain.entity.image.Image;

/**
 * 景区基本信息
 * 
 * @author Administrator
 */
@Embeddable
@SuppressWarnings("serial")
public class ScenicBaseInfo implements Serializable {

	/**
	 * 景区名称
	 */
	@Column(name = "SCENICSPOT_NAME", length = 128)
	private String name;

	/**
	 * 景区简介
	 */
	@Column(name = "SCENICSPOT_INTRO", length = 4000)
	private String intro;

	/**
	 * 景区首图
	 */
	private Image titleImage;
	/**
	 * 景区级别
	 */
	@Column(name = "SCENICSPOT_GRADE", length = 64)
	private String grade;
	/**
	 * 景区创建时间
	 */
	@Column(name = "SCENICSPOT_CREATEDATE",columnDefinition=M.DATE_COLUM)
	private Date createDate;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Image getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(Image titleImage) {
		this.titleImage = titleImage;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}