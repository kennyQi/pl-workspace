package hg.service.image.command.image.usetype;

import hg.service.image.base.BaseCommand;
import hg.service.image.pojo.dto.ImageSpecKeyDTO;
import java.util.List;

/**
 * @类功能说明：创建图片用途规格集合
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午4:27:20
 */
public class ModifyImageUseTypeCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 图片用途ID
	 */
	private String useTypeId;

	/**
	 * 用途名称
	 */
	private String title;

	/**
	 * 用途备注
	 */
	private String remark;

	/**
	 * 当前用途关联的多个尺寸规格，会在创建该用途图片时一次性裁剪出全部尺寸
	 */
	private List<ImageSpecKeyDTO> imageSpecKeys;

	public ModifyImageUseTypeCommand(){}
	public ModifyImageUseTypeCommand(String projectId,String envName){
		super(projectId, envName);
	}
	
	public String getUseTypeId() {
		return useTypeId;
	}
	public void setUseTypeId(String useTypeId) {
		this.useTypeId = useTypeId == null ? null : useTypeId.trim();
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
	public List<ImageSpecKeyDTO> getImageSpecKeys() {
		return imageSpecKeys;
	}
	public void setImageSpecKeys(List<ImageSpecKeyDTO> imageSpecKeys) {
		this.imageSpecKeys = (imageSpecKeys == null || imageSpecKeys.size() < 1)?null:imageSpecKeys;
	}
}