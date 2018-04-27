package hsl.spi.inter.ad;

import hsl.pojo.dto.ad.HslAdPositionDTO;
import hsl.pojo.qo.ad.HslAdPositionQO;
import hsl.spi.inter.BaseSpiService;
import java.util.List;

/**
 * 广告位service
 */
public interface HslAdPositionService extends BaseSpiService<HslAdPositionDTO, HslAdPositionQO>{
	
	/**
	 * 
	 * @方法功能说明：查询广告位
	 * @修改者名字：yuqz
	 * @修改时间：2014年12月15日下午5:10:01
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:List<AdPositionDTO>
	 * @throws
	 */
	public List<HslAdPositionDTO> getAdPositionList(HslAdPositionQO qo);

	public HslAdPositionDTO queryAdPosition(HslAdPositionQO hslAdPositionQO);
}
