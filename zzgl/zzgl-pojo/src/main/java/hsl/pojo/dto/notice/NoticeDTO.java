package hsl.pojo.dto.notice;

import hsl.pojo.dto.BaseDTO;

/**
 * @类功能说明：公告DTO
 * @类修改者：
 * @修改日期：2014年12月12日上午10:13:31
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月12日上午10:13:31
 *
 */
public class NoticeDTO extends BaseDTO{
	private static final long serialVersionUID = 1L;
	private NoticeBaseInfoDTO baseInfo;
	private NoticeStatusDTO status;

	public NoticeBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(NoticeBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public NoticeStatusDTO getStatus() {
		return status;
	}

	public void setStatus(NoticeStatusDTO status) {
		this.status = status;
	}
}