package slfx.mp.spi.inter;

import slfx.mp.pojo.dto.scenicspot.ScenicSpotDTO;
import slfx.mp.qo.PlatformSpotsQO;

/**
 * 平台景点服务
 * 
 * @author zhurz
 */
public interface PlatformSpotService extends BaseMpSpiService<ScenicSpotDTO, PlatformSpotsQO> {
	
	public ScenicSpotDTO queryScenicSpotById(PlatformSpotsQO platformSpotsQO);
}
