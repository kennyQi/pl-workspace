package pl.cms.pojo.command.contribution;
import hg.common.component.BaseCommand;
import java.util.Date;
/**
 * @类功能说明：创建稿件command
 * @类修改者：
 * @修改日期：2015年3月18日下午4:19:04
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年3月18日下午4:19:04
 */
@SuppressWarnings("serial")
public class CreateContributionCommand extends BaseCommand {
	/**
	 * 稿件标题
	 */
	private String title;
	/**
	 * 稿件作者
	 */
	private String author;
	/**
	 * 联系电话
	 */
	private String mobile;
	/**
	 * 稿件内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}