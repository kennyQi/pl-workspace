package hsl.domain.model.xl;
import hsl.domain.model.M;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import slfx.xl.pojo.command.line.CreateLinePictureCommand;

/**
 * @类功能说明：线路图片
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月3日下午7:59:23
 * @版本：V1.0
 */
@Entity
@Table(name = M.TABLE_PREFIX_HSL_XL + "LINE_PICTURE")
public class LinePicture extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 512)
	private String name;
	
	/**
	 * 位置(图片访问地址)
	 */
	@Column(name = "SITE", length = 1024)
	private String site;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	
	/**
	 * 所属的文件夹
	 */
	@ManyToOne
	@JoinColumn(name="LINE_PICTURE_FOLDER_ID")
	private LinePictureFolder folder;

	/**
	 * @方法功能说明：创建线路图片
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-18上午10:20:41
	 * @param command
	 */
	public void create(CreateLinePictureCommand command,LinePictureFolder folder){
		setId(UUIDGenerator.getUUID());
		setCreateDate(new Date());
		setName(command.getName());
		setSite(command.getSite());
		setFolder(folder);
	}
	
	/**
	 * 
	 * @方法功能说明：复制线路图片
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月25日下午2:30:26
	 * @修改内容：
	 * @参数：@param linePicture
	 * @参数：@param linePictureFolder
	 * @return:void
	 * @throws
	 */
	public void copy(LinePicture linePicture,LinePictureFolder linePictureFolder){
		setId(UUIDGenerator.getUUID());
		setCreateDate(new Date());
		setName(linePicture.getName());
		setSite(linePicture.getSite());
		setFolder(linePictureFolder);
	}
	
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
	public LinePictureFolder getFolder() {
		return folder;
	}
	public void setFolder(LinePictureFolder folder) {
		this.folder = folder;
	}
}