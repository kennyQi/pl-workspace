package hg.system.command.album;

import hg.common.component.BaseCommand;

import java.util.List;

/**
 * 
 * @类功能说明：相册 删除 command
 * @类修改者：zzb
 * @修改日期：2014年9月1日下午6:07:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月1日下午6:07:50
 * 
 */
@SuppressWarnings("serial")
public class AlbumDeleteCommand extends BaseCommand {

	/**
	 * 相册id
	 */
	private String albumId;
	
	/**
	 * 相册ids
	 */
	private String albumIdsStr;

	/**
	 * 相册ids
	 */
	private List<String> albumIds;

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getAlbumIdsStr() {
		return albumIdsStr;
	}

	public void setAlbumIdsStr(String albumIdsStr) {
		this.albumIdsStr = albumIdsStr;
	}

	public List<String> getAlbumIds() {
		return albumIds;
	}

	public void setAlbumIds(List<String> albumIds) {
		this.albumIds = albumIds;
	}

}
