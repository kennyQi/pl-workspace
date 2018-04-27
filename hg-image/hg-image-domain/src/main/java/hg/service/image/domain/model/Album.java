package hg.service.image.domain.model;

import java.util.Date;

import hg.common.util.UUIDGenerator;
import hg.service.image.command.album.CreateAlbumCommand;
import hg.service.image.command.album.ModifyAlbumCommand;
import hg.service.image.domain.model.base.ImageBaseModel;
import hg.service.image.domain.model.base.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明：相册
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年9月1日下午2:44:35
 */
@Entity(name="imgAlbum")
@Table(name = M.TABLE_PREFIX + "ALBUM")
public class Album extends ImageBaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 相册标题
	 */
	@Column(name = "TITLE", length = 512)
	private String title;

	/**
	 * 相册路径(路径结尾带斜杠)
	 */
	@Column(name = "PATH", length = 1024)
	private String path;

	/**
	 * 相册名
	 */
	@Column(name = "NAME", length = 1024)
	private String name;

	/**
	 * 相册备注
	 */
	@Column(name = "REMARK", length = 2048)
	private String remark;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	/**
	 * 回收状态(0.未删除；1.已删除；2.彻底删除)
	 */
	@Column(name = "REFUSE", columnDefinition = M.NUM_COLUM)
	private Integer refuse;

	public final static Integer REFUSE_STATUS_NO = 0;		//	未删除相册
	public final static Integer REFUSE_STATUS_YES = 1;		//	回收站相册
	public final static Integer REFUSE_STATUS_CLEAR = 2;	//	彻底删除待清理相册
	
	public Album() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @方法功能说明：创建相册指令
	 * @修改者名字：chenys
	 * @修改时间：2014年11月4日 下午2:35:20
	 * @修改内容：
	 * @param command
	 * @param album
	 * @param isRefuse
	 */
	public Album(CreateAlbumCommand command) {
		if (StringUtils.isNotBlank(command.getAlbumId())) {
			setId(command.getAlbumId());
		} else {
			setId(UUIDGenerator.getUUID());
		}
		
		setProjectId(command.getFromProjectId());
		setEnvName(command.getFromProjectEnvName());
		setTitle(command.getTitle());
		setRemark(command.getRemark());
		// 层级路径
		setPath(command.getPath());
		//相册状态
		setRefuse(CreateAlbumCommand.REFUSE_STATUS_NO);
		setCreateDate(new Date());
	}

	/**
	 * @方法功能说明：修改相册指令
	 * @修改者名字：chenys
	 * @修改时间：2014年11月4日 下午2:35:20
	 * @修改内容：
	 * @param command
	 */
	public void modify(ModifyAlbumCommand command) {
		setTitle(command.getTitle());
		setRemark(command.getRemark());
		setPath(command.getPath());
	}

	/**
	 * @方法功能说明: 回收
	 */
	public void remove() {
		setRefuse(REFUSE_STATUS_YES);
	}

	/**
	 * @方法功能说明: 删除
	 */
	public void delete() {
		setRefuse(REFUSE_STATUS_CLEAR);
	}

	/**
	 * @方法功能说明: 还原
	 */
	public void restore() {
		setRefuse(REFUSE_STATUS_NO);
	}

	public final static String getRootPath(String projectId, String envName) {
		return new StringBuilder().append("/")
				.append(projectId).append("/")
				.append(envName).toString();
	}
	
	public String getRootPath() {
		return new StringBuilder().append("/")
				.append(getProjectId()).append("/")
				.append(getEnvName()).toString();		
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getRefuse() {
		return refuse;
	}

	public void setRefuse(Integer refuse) {
		this.refuse = refuse;
	}
	
}