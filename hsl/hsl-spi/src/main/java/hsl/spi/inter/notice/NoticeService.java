package hsl.spi.inter.notice;
import java.util.List;

import hg.common.component.BaseQo;
import hg.common.page.Pagination;
import hsl.pojo.command.CheckNoticeCommand;
import hsl.pojo.command.CreateNoticeCommand;
import hsl.pojo.command.DeleteNoticeCommand;
import hsl.pojo.command.UpdateNoticeCommand;
import hsl.pojo.dto.notice.NoticeDTO;
import hsl.pojo.exception.NoticeException;
import hsl.pojo.qo.notice.HslNoticeQO;
import hsl.spi.inter.BaseSpiService;
public interface NoticeService extends BaseSpiService<NoticeDTO,BaseQo>{
	
	/**
	 * @方法功能说明：创建公告
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月15日上午9:59:18
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Map<String,Object>
	 * @throws
	 */
	public NoticeDTO createNotice(CreateNoticeCommand command)throws NoticeException;
	/**
	 * @方法功能说明：查询公告列表
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月15日上午10:01:13
	 * @修改内容：
	 * @参数：@param mpOrderQO
	 * @参数：@return
	 * @参数：@throws MPException
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryNoticesByPagination(Pagination pagination) throws NoticeException;
	/**
	 * @方法功能说明：修改公告
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月18日下午5:25:12
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws NoticeException
	 * @return:NoticeDTO
	 * @throws
	 */
	public NoticeDTO updateNotice(UpdateNoticeCommand command)throws NoticeException;
	/**
	 * @方法功能说明：删除公告
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月18日下午5:25:27
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws NoticeException
	 * @return:NoticeDTO
	 * @throws
	 */
	public NoticeDTO deleteNotice(DeleteNoticeCommand command)throws NoticeException; 
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
	public NoticeDTO checkNotice(CheckNoticeCommand command)throws NoticeException; 
	/**
	 * @方法功能说明：查询相应的条数的公告
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月22日上午11:00:55
	 * @修改内容：
	 * @参数：@param hslNoticeQO
	 * @参数：@param maxSize
	 * @参数：@return
	 * @return:List<NoticeDTO>
	 * @throws
	 */
	public List<NoticeDTO> queryNoticeTop(HslNoticeQO hslNoticeQO,int maxSize);
}
