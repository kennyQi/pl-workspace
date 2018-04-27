package slfx.xl.pojo.command.line;

import java.util.List;
import hg.common.component.BaseCommand;

/**
 * @类功能说明：删除线路图片
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月18日上午9:59:23
 * @版本：V1.0
 */
public class BatchDeleteLinePictureCommand extends BaseCommand{  
	private static final long serialVersionUID = 1L;

	/**
	 * 线路图片ID
	 */
	private List<String> pictureIds;

	public BatchDeleteLinePictureCommand(){
		super();
	}
	public BatchDeleteLinePictureCommand(List<String> pictureIds) {
		super();
		setPictureIds(pictureIds);
	}

	public List<String> getPictureIds() {
		return pictureIds;
	}
	public void setPictureIds(List<String> pictureIds) {
		this.pictureIds = (pictureIds == null || pictureIds.size() < 1)?null:pictureIds;
	}
}