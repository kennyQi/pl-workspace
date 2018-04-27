package hsl.domain.model.notice;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import hg.common.component.BaseModel;
import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.CheckNoticeCommand;
import hsl.pojo.command.CreateNoticeCommand;
import hsl.pojo.command.UpdateNoticeCommand;
/**
 * @类功能说明：公告
 * @类修改者：
 * @修改日期：2014年12月12日上午10:13:31
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月12日上午10:13:31
 *
 */
@Entity
@Table(name=M.TABLE_PREFIX_HSL_AD+"NOTICE")
public class Notice extends BaseModel{
	private static final long serialVersionUID = 1L;
	@Embedded
	private NoticeBaseInfo baseInfo;
	@Embedded
	private NoticeStatus status;
	public NoticeBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(NoticeBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public NoticeStatus getStatus() {
		return status;
	}

	public void setStatus(NoticeStatus status) {
		this.status = status;
	}
	/**
	 * @方法功能说明：创建公告
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月15日下午1:42:26
	 * @修改内容：
	 * @参数：@param createNoticeCommand
	 * @return:void
	 * @throws
	 */
	public void createNotice(CreateNoticeCommand createNoticeCommand){
		baseInfo=new NoticeBaseInfo();
		baseInfo.setTitle(createNoticeCommand.getTitle());
		baseInfo.setIssueUser(createNoticeCommand.getIssueUser());
		baseInfo.setCreateTime(new Date());
		baseInfo.setCutOffTime(DateUtil.parseDateTime(createNoticeCommand.getCutOffTime()));
		baseInfo.setLastTime(new Date());
		baseInfo.setDetails(createNoticeCommand.getDetails());
		status=new NoticeStatus();
		status.setCheckedStatus(NoticeStatus.CHECKEDSTATUS_NOCHECK);
		setBaseInfo(baseInfo);
		setStatus(status);
		setId(UUIDGenerator.getUUID());
	}
	/**
	 * @方法功能说明：修改公告
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月15日下午3:20:50
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void updateNotice(UpdateNoticeCommand command){
		baseInfo.setTitle(command.getTitle());
		baseInfo.setCutOffTime(DateUtil.parseDateTime(command.getCutOffTime()));
		baseInfo.setLastTime(new Date());
		baseInfo.setDetails(command.getDetails());
		status.setCheckedStatus(NoticeStatus.CHECKEDSTATUS_NOCHECK);
		setBaseInfo(baseInfo);
		setStatus(status);
	}
	/**
	 * @方法功能说明：审核公告
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月18日下午5:41:52
	 * @修改内容：
	 * @参数：@param checkNoticeCommand
	 * @return:void
	 * @throws
	 */
	public void checkNotice(CheckNoticeCommand checkNoticeCommand){
		status.setCheckedStatus(checkNoticeCommand.getCheckedStatus());
		status.setChcekDeclare(checkNoticeCommand.getChcekDeclare());
		setStatus(status);
	}
}