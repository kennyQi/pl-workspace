package lxs.api.v1.response.app;

import java.util.Date;
import java.util.List;

import lxs.api.base.ApiResponse;

/**
 * 
 * @类功能说明：启动页引导页查询接口返回
 * @类修改者：
 * @修改日期：2015年9月18日上午11:05:20
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月18日上午11:05:20
 */
public class QueryLinkPageResponse extends ApiResponse {

	/**
	 * 启动页图片
	 */
	private HeadImage headImage;

	/**
	 * 引导页图片
	 */
	private List<LinkImage> linkImages;

	public HeadImage getHeadImage() {
		return headImage;
	}

	public void setHeadImage(HeadImage headImage) {
		this.headImage = headImage;
	}

	public List<LinkImage> getLinkImages() {
		return linkImages;
	}

	public void setLinkImages(List<LinkImage> linkImages) {
		this.linkImages = linkImages;
	}

	public class HeadImage {
		/**
		 * 启动页图片
		 */
		private String headImageURL;

		/**
		 * 添加时间
		 */
		private Date createDate;

		public String getHeadImageURL() {
			return headImageURL;
		}

		public void setHeadImageURL(String headImageURL) {
			this.headImageURL = headImageURL;
		}

		public Date getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}

	}

	public class LinkImage {
		private String linkImageURL;

		/**
		 * 添加时间
		 */
		private Date createDate;
		
		private int sort;

		public String getLinkImageURL() {
			return linkImageURL;
		}

		public void setLinkImageURL(String linkImageURL) {
			this.linkImageURL = linkImageURL;
		}

		public Date getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}

		public int getSort() {
			return sort;
		}

		public void setSort(int sort) {
			this.sort = sort;
		}

	}

}
