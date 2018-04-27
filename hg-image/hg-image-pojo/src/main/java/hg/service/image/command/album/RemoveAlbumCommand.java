package hg.service.image.command.album;

import java.util.List;
import hg.service.image.base.BaseCommand;

/**
 * @类功能说明：删除相册到回收站
 * @类修改者：
 * @修改日期：2014-10-30下午3:40:45
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午3:40:45
 */
public class RemoveAlbumCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 相册ID
	 */
	private List<String> albumIds;

	/**
	 * 是否同时删除所有子相册
	 */
	private Boolean removeSubAlbum;

	public RemoveAlbumCommand() {
	}

	public RemoveAlbumCommand(String projectId, String envName) {
		super(projectId, envName);
	}

	public List<String> getAlbumIds() {
		return albumIds;
	}

	public void setAlbumIds(List<String> albumIds) {
		this.albumIds = (albumIds == null || albumIds.size() < 1) ? null
				: albumIds;
	}

	public Boolean getRemoveSubAlbum() {
		return removeSubAlbum;
	}

	public void setRemoveSubAlbum(Boolean removeSubAlbum) {
		this.removeSubAlbum = removeSubAlbum;
	}

}