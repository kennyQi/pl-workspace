package hg.service.ad.domain.model.ad;

import hg.system.model.M;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明：广告的基本信息
 * @类修改者：
 * @修改日期：2014年12月11日下午4:02:08
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月11日下午4:02:08
 */
@Embeddable
public class AdBaseInfo {
	/**
	 * 标题
	 */
	@Column(name = "TITLE", length = 64)
	private String title;
	/**
	 * 链接地址
	 */
	@Column(name = "URL", length = 500)
	private String url;
	/**
	 * 文字备注
	 */
	@Column(name = "REMARK", length = 64)
	private String remark;
	/**
	 * 优先级
	 */
	@Column(name = "PRIORITY", length = 64)
	private Integer priority;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME", columnDefinition = M.DATE_COLUM)
	private Date createTime;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}