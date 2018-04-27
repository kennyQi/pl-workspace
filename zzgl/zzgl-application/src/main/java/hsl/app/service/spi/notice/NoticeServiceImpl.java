package hsl.app.service.spi.notice;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hg.common.component.BaseQo;
import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.system.common.util.EntityConvertUtils;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.notice.NoticeLocalService;
import hsl.domain.model.notice.Notice;
import hsl.pojo.command.CheckNoticeCommand;
import hsl.pojo.command.CreateNoticeCommand;
import hsl.pojo.command.DeleteNoticeCommand;
import hsl.pojo.command.UpdateNoticeCommand;
import hsl.pojo.dto.notice.NoticeDTO;
import hsl.pojo.exception.NoticeException;
import hsl.pojo.qo.notice.HslNoticeQO;
import hsl.spi.inter.notice.NoticeService;
@Service
public class NoticeServiceImpl extends BaseSpiServiceImpl<NoticeDTO, BaseQo, NoticeLocalService> implements NoticeService {
	@Autowired
	private NoticeLocalService noticeLocalService;
	@Override
	protected NoticeLocalService getService() {
		return noticeLocalService;
	}

	@Override
	protected Class<NoticeDTO> getDTOClass() {
		return NoticeDTO.class;
	}
	@Override
	public NoticeDTO createNotice(CreateNoticeCommand command) {
		Notice notice=noticeLocalService.createNotice(command);
		NoticeDTO noticeDTO=BeanMapperUtils.map(notice, NoticeDTO.class);
		return noticeDTO;
	}

	@Override
	public Pagination queryNoticesByPagination(Pagination pagination)throws NoticeException {
		Pagination pagination2=noticeLocalService.queryPagination(pagination);
		return pagination2;
	}

	@Override
	public NoticeDTO updateNotice(UpdateNoticeCommand command) throws NoticeException {
		Notice notice=noticeLocalService.updateNotice(command);
		NoticeDTO noticeDTO=BeanMapperUtils.map(notice, NoticeDTO.class);
		return noticeDTO;
	}

	@Override
	public NoticeDTO deleteNotice(DeleteNoticeCommand command)throws NoticeException {
		Notice notice=noticeLocalService.deleteNotice(command);
		NoticeDTO noticeDTO=BeanMapperUtils.map(notice, NoticeDTO.class);
		return noticeDTO;
	}

	@Override
	public NoticeDTO checkNotice(CheckNoticeCommand command)throws NoticeException {
		Notice notice=noticeLocalService.checkNotice(command);
		NoticeDTO noticeDTO=BeanMapperUtils.map(notice, NoticeDTO.class);
		return noticeDTO;
	}
	public List<NoticeDTO> queryNoticeTop(HslNoticeQO hslNoticeQO,int maxSize){
		List<Notice> notices=noticeLocalService.queryNoticeTop(hslNoticeQO,maxSize);
		List<NoticeDTO> noticeDtos=EntityConvertUtils.convertDtoToEntityList(notices, NoticeDTO.class);
		return noticeDtos;
	}
}
