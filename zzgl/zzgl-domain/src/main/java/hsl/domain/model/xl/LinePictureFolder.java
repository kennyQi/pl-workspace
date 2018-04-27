package hsl.domain.model.xl;
import hg.common.component.BaseModel;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import hsl.domain.model.M;
/**
 * @类功能说明：线路图片文件夹
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenxy
 * @创建时间：2014年12月3日下午7:59:23
 * @版本：V1.0
 */
@Entity
@Table(name = M.TABLE_PREFIX_HSL_XL + "LINE_PICTURE_FOLDER")
public class LinePictureFolder extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 512)
	private String name;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	
	/**
	 * 是否主文件夹
	 */
	@Column(name = "MATTER")
	@Type(type = "yes_no")
	private boolean matter = false;
	
	/**
	 * 文件夹中的图片
	 */
	@OneToMany(mappedBy = "folder",cascade={CascadeType.ALL})
	private List<LinePicture> pictureList;
	
	/**
	 * 所属的线路
	 */
	@ManyToOne
	@JoinColumn(name="LINE_ID")
	private Line line;

	
	public Line getLine() {
		return line;
	}
	public void setLine(Line line) {
		this.line = line;
	}
	public List<LinePicture> getPictureList() {
		return pictureList;
	}
	public void setPictureList(List<LinePicture> pictureList) {
		this.pictureList = pictureList;
	}
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
	
}