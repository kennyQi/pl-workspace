package hg.dzpw.app.pojo.command;

import java.util.List;
import hg.common.util.file.FdfsFileInfo;
import hg.dzpw.pojo.common.BaseCommand;

/**
 * @类功能说明：创建景区图片
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenys
 * @创建时间：2014-12-22上午10:43:37
 */
public class CreateScenicImageCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 标题
	 */
	private String[] titles;
	
	/**
	 * 文件原始名称
	 */
	private String[] fileNames;
	
	/**
	 * 文件info
	 */
	private List<FdfsFileInfo> fileInfos;
	
	/**
	 * 景区id
	 */
	private String scenicSpotId;
	
	/**
	 * 景区名称
	 */
	private String scenicSpotName;

	public String[] getTitles() {
		return titles;
	}
	public void setTitles(String[] titles) {
		this.titles = (null == titles || titles.length < 1) ? null : titles;
	}
	public String[] getFileNames() {
		return fileNames;
	}
	public void setFileNames(String[] fileNames) {
		this.fileNames = (null == fileNames || fileNames.length < 1) ? null : fileNames;
	}
	public List<FdfsFileInfo> getFileInfos() {
		return fileInfos;
	}
	public void setFileInfos(List<FdfsFileInfo> fileInfos) {
		this.fileInfos = (null == fileInfos || fileInfos.size() < 1) ? null : fileInfos;
	}
	public String getScenicSpotId() {
		return scenicSpotId;
	}
	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId == null ? null : scenicSpotId.trim();
	}
	public String getScenicSpotName() {
		return scenicSpotName;
	}
	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName == null ? null : scenicSpotName.trim();
	}
}