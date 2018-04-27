package pl.cms.domain.entity.contribution;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import pl.cms.domain.entity.M;
import pl.cms.domain.entity.article.ArticleBaseInfo;
import pl.cms.domain.entity.article.ArticleStatus;
import pl.cms.domain.entity.article.BaseArticle;
import pl.cms.pojo.command.contribution.CreateContributionCommand;
import hg.common.util.UUIDGenerator;

/**
 * 
 * @类功能说明：稿件
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2015年3月11日下午2:36:09
 * 
 */
@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("contribution")
@Table(name = M.TABLE_PREFIX + "CONTRIBUTION")
public class Contribution extends BaseArticle {
	/**
	 * 稿件未审核
	 */
	public final static Integer  CONTRIBUTION_NOCHECK=1;
	/**
	 * 稿件审核通过
	 */
	public final static Integer  CONTRIBUTION_APPROVE=2;
	/**
	 * 稿件审核未通过
	 */
	public final static Integer  CONTRIBUTION_DISAPPROVE=3;
	
	/**
	 * 联系电话
	 */
	@Column(name = "MOBILE")
	private String mobile;
	/**
	 * 审核状态
	 */
	@Column(name="CHECKSTATUS",columnDefinition=M.TYPE_NUM_COLUM)
	private Integer checkStatus;
	
	public void create(CreateContributionCommand command){
		this.setId(UUIDGenerator.getUUID());
		ArticleBaseInfo articleBaseInfo=new ArticleBaseInfo();
		articleBaseInfo.setAuthor(command.getAuthor());
		articleBaseInfo.setContent(command.getContent());
		articleBaseInfo.setTitle(command.getTitle());
		setBaseInfo(articleBaseInfo);
		this.setMobile(command.getMobile());
		this.setCreateDate(new Date());
		this.setCheckStatus(CONTRIBUTION_NOCHECK);
		ArticleStatus status = new ArticleStatus();
		status.setCurrentValue(ArticleStatus.VALUE_HIDE);
		status.setCommentNo(0);
		status.setViewNo(0);
		status.setSupportCount(0);
		status.setOpposeCount(0);
		setStatus(status);
	}
	
	

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

}
