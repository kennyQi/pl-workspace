package hsl.spi.inter.ad;

import hsl.pojo.command.ad.HslCreateAdCommand;
import hsl.pojo.command.ad.HslDeleteAdCommand;
import hsl.pojo.command.ad.HslUpdateAdCommand;
import hsl.pojo.command.ad.HslUpdateAdStatusCommand;
import hsl.pojo.dto.ad.HslAdDTO;
import hsl.pojo.dto.ad.HslAdPositionDTO;
import hsl.pojo.qo.ad.HslAdPositionQO;
import hsl.pojo.qo.ad.HslAdQO;
import hsl.pojo.qo.mp.HslADQO;
import hsl.spi.inter.BaseSpiService;

import java.util.List;


public interface HslAdService extends BaseSpiService<HslAdDTO,HslAdQO>{
	/**
	 * 新增广告
	 * @param command
	 * @throws Exception 
	 */
	public HslAdDTO createAd(HslCreateAdCommand command) throws Exception;
	
	/**
	 * 删除广告
	 */
	public void deletAd(HslDeleteAdCommand command);
	
	/**
	 * 修改广告
	 */
	public HslAdDTO modifyAd(HslUpdateAdCommand command);
	/**
	 * 查询广告
	 */
	public HslAdDTO queryAd(HslAdQO qo);
	
	public List<HslAdDTO> getADList(HslADQO hslADQO);
	
	public List<HslAdPositionDTO> getPositionList(HslAdPositionQO hslADPositionQO);

	/**
	 * 修改广告状态（显示隐藏）
	 */
	public HslAdDTO modifyAdStatus(HslUpdateAdStatusCommand command);
	
}
