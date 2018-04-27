package plfx.mp.spi.inter;

import plfx.mp.pojo.dto.scenicspot.ScenicSpotDTO;
import plfx.mp.qo.PlatformSpotsQO;

/**
 * 平台景点服务
 * 
 * @author zhurz
 */
public interface PlatformSpotService extends BaseMpSpiService<ScenicSpotDTO, PlatformSpotsQO> {
	
	public ScenicSpotDTO queryScenicSpotById(PlatformSpotsQO platformSpotsQO);
}
