package hg.service.image.command.album;

import hg.service.image.base.BaseCommand;
import java.util.List;

/**
 * @类功能说明：从回收站还原相册
 * @类修改者：
 * @修改日期：2014-12-12上午10:56:19
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenys
 * @创建时间：2014-12-12上午10:56:19
 */
public class RestoreAlbumCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 景区ID
	 */
	private List<String> albumIds;

	public RestoreAlbumCommand(){}
	public RestoreAlbumCommand(String projectId,String envName){
		super(projectId, envName);
	}
	
	public List<String> getAlbumIds() {
		return albumIds;
	}
	public void setAlbumIds(List<String> albumIds) {
		this.albumIds = (null == albumIds || albumIds.size() < 1)?null:albumIds;
	}
}