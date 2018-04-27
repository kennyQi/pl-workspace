package hg.service.image.command.image.usetype;

import hg.service.image.base.BaseCommand;
import java.util.List;

/**
 * @类功能说明：删除图片用途规格集合
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午4:27:20
 */
public class DeleteImageUseTypeCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 用途ID
	 */
	private List<String> useTypeIds;

	public DeleteImageUseTypeCommand(){}
	public DeleteImageUseTypeCommand(String projectId,String envName){
		super(projectId, envName);
	}
	
	public List<String> getUseTypeIds() {
		return useTypeIds;
	}
	public void setUseTypeIds(List<String> useTypeIds) {
		this.useTypeIds = (useTypeIds == null || useTypeIds.size() < 1)?null:useTypeIds;
	}
}