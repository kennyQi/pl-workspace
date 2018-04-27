package hsl.app.service.local.notice;
import java.util.List;
import hg.common.component.BaseServiceImpl;
import hsl.app.dao.NoticeDao;
import hsl.domain.model.notice.Notice;
import hsl.pojo.command.CheckNoticeCommand;
import hsl.pojo.command.CreateNoticeCommand;
import hsl.pojo.command.DeleteNoticeCommand;
import hsl.pojo.command.UpdateNoticeCommand;
import hsl.pojo.qo.notice.HslNoticeQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class NoticeLocalService extends BaseServiceImpl<Notice,HslNoticeQO,NoticeDao>{
	@Autowired
	private NoticeDao noticeDao;
	@Override
	protected NoticeDao getDao() {
		return noticeDao;
	}
	/**
	 * @方法功能说明：创建公告
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月15日上午11:25:58
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Notice
	 * @throws
	 */
	public Notice createNotice(CreateNoticeCommand command){
		Notice notice=new Notice();
		notice.createNotice(command);
		noticeDao.save(notice);
		return notice;
	}
	/**
	 * @方法功能说明：查询公告最大数量
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月22日上午10:54:52
	 * @修改内容：
	 * @参数：@param hslNoticeQO
	 * @参数：@param maxSize
	 * @参数：@return
	 * @return:List<Notice>
	 * @throws
	 */
	public List<Notice> queryNoticeTop(HslNoticeQO hslNoticeQO,int maxSize){
		return noticeDao.queryList(hslNoticeQO, maxSize);
	}
	/**
	 * @方法功能说明：修改公告
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月15日下午3:17:49
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Notice
	 * @throws
	 */
	public Notice updateNotice(UpdateNoticeCommand command){
		HslNoticeQO noticeQO=new HslNoticeQO();
		noticeQO.setId(command.getId());
		Notice notice=noticeDao.queryUnique(noticeQO);
		notice.updateNotice(command);
		noticeDao.update(notice);
		return notice;
	}
	/**
	 * @方法功能说明：删除公告
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月18日下午5:39:33
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Notice
	 * @throws
	 */
	public Notice deleteNotice(DeleteNoticeCommand command){
		HslNoticeQO noticeQO=new HslNoticeQO();
		noticeQO.setId(command.getId());
		Notice notice=noticeDao.queryUnique(noticeQO);
		noticeDao.delete(notice);
		return notice;
	}
	/**
	 * @方法功能说明：审核公告
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月18日下午5:25:27
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws NoticeException
	 * @return:NoticeDTO
	 * @throws
	 */
	public Notice checkNotice(CheckNoticeCommand command){
		HslNoticeQO noticeQO=new HslNoticeQO();
		noticeQO.setId(command.getId());
		Notice notice=noticeDao.queryUnique(noticeQO);
		notice.checkNotice(command);
		noticeDao.update(notice);
		return notice;
	}
}
