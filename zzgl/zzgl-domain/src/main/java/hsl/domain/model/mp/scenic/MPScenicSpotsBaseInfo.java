package hsl.domain.model.mp.scenic;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 景点基本信息
 * 
 * @author Administrator
 */
@Embeddable
public class MPScenicSpotsBaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 景点名称
	 */
	@Column(name = "SCENICSPOT_NAME", length = 128)
	private String name;
	
	/**
	 * 景点简介
	 */
	@Column(name = "SCENICSPOT_INTRO", length = 4000)
	private String intro;

	/**
	 * 景点首图
	 */
	@Column(name = "SCENICSPOT_IMAGE", length = 256)
	private String image;

	/**
	 * 景点级别
	 */
	@Column(name = "SCENICSPOT_GRADE", length = 64)
	private String grade;

	/**
	 * 景点别名
	 */
	@Column(name = "SCENICSPOT_ALIAS", length = 128)
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