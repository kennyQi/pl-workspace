package hsl.spi.inter.line.ad;
import hg.common.page.Pagination;
import hsl.pojo.command.line.ad.CreateLineIndexAdCommand;
import hsl.pojo.command.line.ad.ModifyLineIndexAdCommand;
import hsl.pojo.dto.line.ad.LineIndexAdDTO;
import hsl.pojo.exception.LineIndexAdException;
import hsl.pojo.qo.line.ad.LineIndexAdQO;
import hsl.spi.inter.BaseSpiService;
/**
 * @类功能说明：线路首页广告
 * @类修改者：
 * @修改日期：2015年4月22日下午5:04:50
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年4月22日下午5:04:50
 */
public interface HslLineIndexAdService extends BaseSpiService<LineIndexAdDTO,LineIndexAdQO >{
	/**
	 * @方法功能说明：创建线路首页广告
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月22日下午4:55:07
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void createLineIndexAd(CreateLineIndexAdCommand command)throws LineIndexAdException;
	/**
	 * @方法功能说明：修改线路首页广告
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月22日下午5:02:53
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyLineIndexAd(ModifyLineIndexAdCommand command)throws LineIndexAdException;
	/**
	 * @方法功能说明：删除线路首页广告
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月22日下午5:03:41
	 * @修改内容：
	 * @参数：@param id
	 * @return:void
	 * @throws
	 */
	public void deleteLineIndexAd(String id)throws LineIndexAdException;
	public Pagination queryLineIndexAds(Pagination pagination) ;
}
