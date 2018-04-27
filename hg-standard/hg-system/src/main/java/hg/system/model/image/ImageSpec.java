package hg.system.model.image;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.system.command.imageSpec.ImageSpecCreateCommand;
import hg.system.model.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @类功能说明：每个ImageSpec对应fastdfs中的一个文件
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月15日下午2:02:34
 * 
 */
@Entity
@Table(name = M.TABLE_PREFIX_SYS + "IMAGE_SPEC")
@SuppressWarnings("serial")
public class ImageSpec extends BaseModel {

	public final static String DEFAULT_KEY = "default";
	
	/**
	 * 所属图片
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IMAGE_ID")
	private Image image;

	/**
	 * 该规格图片在同一张图中的规格key
	 */
	@Column(name = "KEY_", length = 60)
	private String key;

	/**
	 * FdfsFileInfo JSON
	 */
	@Column(name = "FILE_INFO", length = 512)
	private String fileInfo;

	/**
	 * 宽
	 */
	@Transient
	private Integer width;

	/**
	 * 高
	 */
	@Transient
	private Integer height;

	/**
	 * 
	 * @方法功能说明：附件属性设置
	 * @修改者名字：zzb
	 * @修改时间：2014年9月3日下午3:22:53
	 * @修改内容：
	 * @参数：@param imageSpec
	 * @参数：@param image2
	 * @return:void
	 * @throws
	 */
	public void imageSpecCreate(ImageSpecCreateCommand imageSpec, Image image) {

		setId(UUIDGenerator.getUUID());
		setImage(image);
		setKey(imageSpec.getKey());
		setFileInfo(imageSpec.getFileInfo());

	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public ImageSpec(String key){
		this.key = key;
	}
	
	public ImageSpec(){
	}
}