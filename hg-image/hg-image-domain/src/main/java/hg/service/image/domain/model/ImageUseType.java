package hg.service.image.domain.model;

import hg.common.util.EntityConvertUtils;
import hg.common.util.UUIDGenerator;
import hg.service.image.command.image.usetype.CreateImageUseTypeCommand;
import hg.service.image.command.image.usetype.ModifyImageUseTypeCommand;
import hg.service.image.domain.model.base.ImageBaseModel;
import hg.service.image.domain.model.base.M;
import hg.service.image.pojo.dto.ImageSpecKeyDTO;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @类功能说明：图片明细规格的用途集合
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午5:03:22
 */
@Entity(name="imgImageUseType")
@Table(name = M.TABLE_PREFIX + "KEY_USE_TYPE")
public class ImageUseType extends ImageBaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 用途名称
	 */
	@Column(name = "TITLE", length = 64)
	private String title;

	/**
	 * 用途备注
	 */
	@Column(name = "REMARK", length = 512)
	private String remark;

	/**
	 * 图片规格KEY用途 使用JSON保存
	 */
	@Transient
	private List<ImageSpecKey> imageSpecKeys;

	/**
	 * 图片规格 KEY JSON
	 */
	@Column(name = "IMAGE_SPEC_KEYS_JSON", columnDefinition = M.TEXT_COLUM)
	private String imageSpecKeysJson;

	/**
	 * @方法功能说明：创建规格用途指令
	 * @修改者名字：chenys
	 * @修改时间：2014年11月4日 下午2:35:20
	 * @修改内容：
	 * @param command
	 */
	public void create(CreateImageUseTypeCommand command) {
		setId(command.getUseTypeId());
		setProjectId(command.getFromProjectId());
		setTitle(command.getTitle());
		setRemark(command.getRemark());
		setImageSpecKeys(this.converDtoToKeys(command.getImageSpecKeys()));
		getImageSpecKeysJson();
	}

	/**
	 * @方法功能说明：修改规格用途指令
	 * @修改者名字：chenys
	 * @修改时间：2014年11月4日 下午2:35:20
	 * @修改内容：
	 * @param command
	 */
	public void modify(ModifyImageUseTypeCommand command) {
		setTitle(command.getTitle());
		setRemark(command.getRemark());
		setImageSpecKeys(this.converDtoToKeys(command.getImageSpecKeys()));
		getImageSpecKeysJson();
	}

	/**
	 * @方法功能说明：将规格Key的DTO列表转换成规格Key列表
	 * @修改者名字：chenys
	 * @修改时间：2014年11月4日 下午5:05:58
	 * @修改内容：
	 * @param dtoList
	 * @return
	 */
	public List<ImageSpecKey> converDtoToKeys(List<ImageSpecKeyDTO> dtoList) {
		if (null == dtoList || dtoList.size() < 1)
			return null;
		return EntityConvertUtils.convertDtoToEntityList(dtoList,
				ImageSpecKey.class);
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
	public List<ImageSpecKey> getImageSpecKeys() {
		if (null != imageSpecKeysJson) {
			// 将JSON字符转换成规格Key列表
			this.imageSpecKeys = JSON.parseArray(this.imageSpecKeysJson,
					ImageSpecKey.class);
		}
		return this.imageSpecKeys;
	}
	public void setImageSpecKeys(List<ImageSpecKey> imageSpecKeys) {
		this.imageSpecKeys = (imageSpecKeys == null || imageSpecKeys.size() < 1) ? null
				: imageSpecKeys;
	}
	public String getImageSpecKeysJson() {
		if (null != imageSpecKeys) {
			// 将规格Key列表转换成JSON字符
			this.imageSpecKeysJson = JSON.toJSONString(this.imageSpecKeys,
					SerializerFeature.DisableCircularReferenceDetect);
		}
		return this.imageSpecKeysJson;
	}
	public void setImageSpecKeysJson(String imageSpecKeysJson) {
		this.imageSpecKeysJson = imageSpecKeysJson == null ? null
				: imageSpecKeysJson.trim();
	}
}