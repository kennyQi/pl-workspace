package hg.service.image.domain.model;

import hg.common.util.UUIDGenerator;
import hg.common.util.file.FdfsFileInfo;
import hg.service.image.command.image.spec.CreateImageSpecCommand;
import hg.service.image.command.image.spec.ModifyImageSpecCommand;
import hg.service.image.domain.model.base.ImageBaseModel;
import hg.service.image.domain.model.base.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @类功能说明：图片规格——每个ImageSpec对应FastDFS中的一个文件
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月15日下午2:02:34
 */
@Entity(name = "imgImageSpec")
@Table(name = M.TABLE_PREFIX + "IMAGE_SPEC")
public class ImageSpec extends ImageBaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属图片
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IMAGE_ID")
	private Image image;

	/**
	 * 该规格图片在同一张图中的规格key(比如大图、中图、小图、略缩图)
	 */
	@Column(name = "SPEC_KEY", length = 256)
	private String key;

	/**
	 * 图大小(单位byte)
	 */
	@Column(name = "FILE_SIZE", columnDefinition = M.NUM_COLUM)
	private Long fileSize = 0L;

	/**
	 * 宽
	 */
	@Column(name = "WIDTH", columnDefinition = M.NUM_COLUM)
	private Integer width;

	/**
	 * 高
	 */
	@Column(name = "HEIGHT", columnDefinition = M.NUM_COLUM)
	private Integer height;

	/**
	 * MD5值(用于判断图片是否重复)
	 */
	@Column(name = "SRC_FILE_MD5", length = 64)
	private String fileMd5;

	/**
	 * FdfsFileInfo JSON
	 */
	@Column(name = "FILE_INFO", length = 512)
	private String fileInfo;

	public ImageSpec() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @方法功能说明：创建图片规格指令，产生实际图片文件前的预处理
	 * @修改者名字：yuxx
	 * @修改时间：2014年11月4日 下午2:57:10
	 * @修改内容：
	 * @param command
	 * @param fileInfo
	 * @param fileSize
	 * @param width
	 * @param height
	 */
	public ImageSpec(CreateImageSpecCommand command, ImageSpecKey specKey) {

		setId(UUIDGenerator.getUUID());
		setProjectId(command.getFromProjectId());
		setEnvName(command.getFromProjectEnvName());
		setKey(command.getKey());
		setFileInfo(command.getFileInfo());
		//

		// 由规格Key取得高宽
		setWidth(specKey.getWidth());
		setHeight(specKey.getHeight());
	}

	public void addFileInfo(Image image, FdfsFileInfo fdfsFileInfo) {

		fdfsFileInfo.setImageId(image.getId());
		setFileInfo(JSON.toJSONString(fdfsFileInfo,
				SerializerFeature.DisableCircularReferenceDetect));
		setFileSize(fdfsFileInfo.getFileSize());// long转int
		setFileMd5(String.valueOf(fdfsFileInfo.getCrc32()));

		setImage(image);
	}

	/**
	 * @方法功能说明：创建图片规格指令
	 * @修改者名字：chenys
	 * @修改时间：2014年11月4日 下午2:57:10
	 * @修改内容：
	 * @param command
	 * @param fileInfo
	 * @param fileSize
	 * @param width
	 * @param height
	 */
	public void modifyImageSpec(ModifyImageSpecCommand command) {

		// 获取规格图的fdfs信息
		FdfsFileInfo imageSpecFileInfo = JSON.parseObject(
				command.getFileInfo(), FdfsFileInfo.class);
		// 图片
		setFileInfo(JSON.toJSONString(imageSpecFileInfo,
				SerializerFeature.DisableCircularReferenceDetect));
		setFileSize(imageSpecFileInfo.getFileSize());// long转int
		setFileMd5(String.valueOf(imageSpecFileInfo.getCrc32()));
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key == null ? null : key.trim();
	}

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo == null ? null : fileInfo.trim();
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

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

}