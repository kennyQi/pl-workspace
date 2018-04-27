package hsl.pojo.dto.mp;

import java.io.Serializable;

/**
 * 景点基本信息
 * 
 * @author Administrator
 */
public class ScenicSpotsBaseInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 景点名称
	 */
	private String name;
	
	/**
	 * 景点简介
	 */
	private String intro;

	/**
	 * 景点首图
	 */
	private String image;

	/**
	 * 景点级别
	 */
	private String grade;

	/**
	 * 景点别名
	 */
	private String alias;

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}