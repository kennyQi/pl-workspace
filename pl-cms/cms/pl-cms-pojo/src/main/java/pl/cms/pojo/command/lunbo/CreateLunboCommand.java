package pl.cms.pojo.command.lunbo;

import pl.cms.pojo.command.AdminBaseCommand;

/**
 * 
 * @类功能说明：添加广告图
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2015年3月11日下午3:55:47
 * 
 */
@SuppressWarnings("serial")
public class CreateLunboCommand extends AdminBaseCommand {

	private String title;

	private String url;

	private String imageId;

	private String positionId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

}
