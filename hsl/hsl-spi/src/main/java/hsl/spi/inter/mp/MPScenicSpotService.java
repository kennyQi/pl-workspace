package hsl.spi.inter.mp;

import hg.common.component.BaseQo;
import hg.common.page.Pagination;
import hsl.pojo.command.CreateHotScenicSpotCommand;
import hsl.pojo.command.ModifyHotScenicSpotCommand;
import hsl.pojo.command.ad.CreatePCHotSecnicSpotCommand;
import hsl.pojo.command.ad.CreateSpecCommand;
import hsl.pojo.command.ad.DeleteSpecCommand;
import hsl.pojo.command.ad.UpdatePCHotCommand;
import hsl.pojo.command.ad.UpdateSpecCommand;
import hsl.pojo.dto.mp.HotScenicSpotDTO;
import hsl.pojo.dto.mp.MPSimpleDTO;
import hsl.pojo.dto.mp.ScenicSpotDTO;
import hsl.pojo.dto.mp.SpecialOfferMpDTO;
import hsl.pojo.exception.MPException;
import hsl.pojo.qo.mp.HslHotScenicSpotQO;
import hsl.pojo.qo.mp.HslMPDatePriceQO;
import hsl.pojo.qo.mp.HslMPPolicyQO;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
import hsl.pojo.qo.mp.HslRankListQO;
import hsl.spi.inter.BaseSpiService;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface MPScenicSpotService extends BaseSpiService<ScenicSpotDTO,BaseQo>{
	
	/**
	 * 查询景点列表
	 * @param mpScenicSpotsQO
	 * @return
	 */
	public Map queryScenicSpot(HslMPScenicSpotQO mpScenicSpotsQO)  throws MPException;

	/**
	 * 查询政策
	 * @param mpPolicyQO
	 * @return
	 */
	public Map queryScenicPolicy(HslMPPolicyQO mpPolicyQO);
	
	/**
	 * 查询景区排行榜
	 * @param rankListQO
	 * @return
	 */
	public List<MPSimpleDTO> queryScenicSpotClickRate(HslRankListQO rankListQO);
	
	/**
	 * 查询价格日历
	 * @param mpDatePriceQO
	 * @return
	 * @throws MPException 
	 */
	public Map queryDatePrice(HslMPDatePriceQO mpDatePriceQO) throws MPException;
	
	/**
	 * 新建热门景点
	 * @param command
	 */
	public void createHotScenicSpot(CreateHotScenicSpotCommand command);
	
	/**
	 * 查询热门景点
	 * @param qo
	 * @return
	 */
	HotScenicSpotDTO getScenicSpot(HslHotScenicSpotQO qo);
	
	/**
	 * 取消当前热门景点
	 * @param mpScenicSpotsQO
	 */
	public void removeCurrentHotScenicSpot(HslMPScenicSpotQO mpScenicSpotsQO);
	
	/**
	 * 设置热门景点为当前热门景点
	 * @param command
	 */
	public void modifyCurrentHotScenicSpot(ModifyHotScenicSpotCommand command);
	
	/**
	 * 创建特价门票
	 * @param command
	 * @throws Exception 
	 */
	public void createSpecScenic(CreateSpecCommand command) throws Exception;
	
	/**
	 * 查询特价门票列表
	 * @param pagination
	 * @return
	 */
	public Pagination getSpecialList(Pagination pagination);
	
	/**
	 * 根据id查找特价门票
	 * @param id
	 * @return
	 */
	public SpecialOfferMpDTO getSpecialOfferMp(String id);

	/**
	 * 更新特价门票
	 * @param command
	 * @throws Exception
	 */
	public void updateSpec(UpdateSpecCommand command) throws Exception;
	
	/**
	 * 删除特价门票
	 * @param command
	 * @throws Exception
	 */
	public void deleteSpec(DeleteSpecCommand command) throws Exception;

	/**
	 * 添加PC热门景点
	 * @param command
	 * @throws MPException 
	 */
	public void createPCHot(CreatePCHotSecnicSpotCommand command) throws MPException;
	
	
	/**
	 * 查询PC热门推荐景点
	 * @param qo
	 * @return
	 */
	public Pagination getHotScenicSpots(Pagination pagination);
	
	/**
	 * 更新PC热门景点
	 * @param command
	 */
	public void updatePCHotScenic(UpdatePCHotCommand command);
}
