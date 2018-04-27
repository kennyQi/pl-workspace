package pl.cms.pojo.command.lunbo;

import hg.common.util.file.FdfsFileInfo;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

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
public class CreateIndexLunboCommand extends AdminBaseCommand {

	private String title;

	private String url;

	private FdfsFileInfo titleImageFileInfo;

	private String titleImageFileInfoJSON;

	private Boolean isShow;

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

	public FdfsFileInfo getTitleImageFileInfo() {
		if (titleImageFileInfo == null
				&& StringUtils.isNotBlank(titleImageFileInfoJSON)) {
			setTitleImageFileInfo(JSON.parseObject(titleImageFileInfoJSON,
					FdfsFileInfo.class));
		}
		return titleImageFileInfo;
	}

	public void setTitleImageFileInfo(FdfsFileInfo titleImageFileInfo) {
		this.titleImageFileInfo = titleImageFileInfo;
	}

	public String getTitleImageFileInfoJSON() {
		return titleImageFileInfoJSON;
	}

	public void setTitleImageFileInfoJSON(String titleImageFileInfoJSON) {
		this.titleImageFileInfoJSON = titleImageFileInfoJSON;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

}
