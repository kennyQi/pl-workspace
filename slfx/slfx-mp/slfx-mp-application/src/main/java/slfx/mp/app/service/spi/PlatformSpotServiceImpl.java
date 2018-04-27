package slfx.mp.app.service.spi;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hg.system.cache.AddrProjectionCacheManager;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.mp.app.common.util.EntityConvertUtils;
import slfx.mp.app.pojo.qo.ScenicSpotQO;
import slfx.mp.app.service.local.ScenicSpotLocalService;
import slfx.mp.domain.model.scenicspot.ScenicSpot;
import slfx.mp.pojo.dto.scenicspot.ScenicSpotDTO;
import slfx.mp.qo.PlatformSpotsQO;
import slfx.mp.spi.inter.PlatformSpotService;

@Service
public class PlatformSpotServiceImpl implements PlatformSpotService {
	
	@Autowired
	private ScenicSpotLocalService service;
	
	@Autowired
	private AddrProjectionCacheManager addrProjectionCacheManager;
	
	@Override
	public ScenicSpotDTO queryUnique(PlatformSpotsQO qo) {
		ScenicSpotQO scenicSpotQO = parseQo(qo);
		ScenicSpot scenicSpot = service.queryUnique(scenicSpotQO);
		return EntityConvertUtils.convertEntityToDto(scenicSpot, ScenicSpotDTO.class);
	}

	@Override
	public List<ScenicSpotDTO> queryList(PlatformSpotsQO qo) {
		ScenicSpotQO scenicSpotQO = parseQo(qo);
		List<ScenicSpot> list = service.queryList(scenicSpotQO);
		List<ScenicSpotDTO> dtolist = EntityConvertUtils
				.convertEntityToDtoList(list, ScenicSpotDTO.class);
		return fetchAddr(dtolist);
	}
	
	/**
	 * @方法功能说明：获取省市名
	 * @修改者名字：zhurz
	 * @修改时间：2014-8-18下午2:46:09
	 * @修改内容：
	 * @参数：@param dtolist
	 * @参数：@return
	 * @return:List<ScenicSpotDTO>
	 * @throws
	 */
	protected List<ScenicSpotDTO> fetchAddr(List<ScenicSpotDTO> dtolist) {
		HgLogger.getInstance().info("wuyg", "获取省市名");
		if (dtolist == null)
			return null;
		for (ScenicSpotDTO dto : dtolist) {
			String pcode = dto.getScenicSpotGeographyInfo().getProvinceCode();
			String ccode = dto.getScenicSpotGeographyInfo().getCityCode();
			Province p = addrProjectionCacheManager.getProvince(pcode);
			City c = addrProjectionCacheManager.getCity(ccode);
			dto.getScenicSpotGeographyInfo().setProvinceName(p == null ? "" : p.getName());
			dto.getScenicSpotGeographyInfo().setCityName(c == null ? "" : c.getName());
		}
		return dtolist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination2 = parsePaginationQo(pagination);
		Pagination pagination3 = service.queryPagination(pagination2);
		List<ScenicSpot> list = (List<ScenicSpot>) pagination3.getList();
		List<ScenicSpotDTO> dtolist = EntityConvertUtils
				.convertEntityToDtoList(list, ScenicSpotDTO.class);
		fetchAddr(dtolist);
		pagination3.setList(dtolist);
		pagination3.setCondition(pagination.getCondition());
		return pagination3;
	}
	
	protected ScenicSpotQO parseQo(PlatformSpotsQO qo) {
		ScenicSpotQO scenicSpotQO = BeanMapperUtils.map(qo, ScenicSpotQO.class);
		scenicSpotQO.setId(qo.getScenicSpotId());
		return scenicSpotQO;
	}
	
	protected Pagination parsePaginationQo(Pagination pagination) {
		Pagination pagination2 = BeanMapperUtils.map(pagination, Pagination.class);
		pagination2.setPageNo(pagination.getPageNo());
		PlatformSpotsQO qo = (PlatformSpotsQO) pagination.getCondition();
		ScenicSpotQO scenicSpotQO = parseQo(qo);
		pagination2.setCondition(scenicSpotQO);
		return pagination2;
	}
	
	public ScenicSpotDTO queryScenicSpotById(PlatformSpotsQO platformSpotsQO){
		ScenicSpot scenicSpot=service.get(platformSpotsQO.getId());
		ScenicSpotDTO scenicSpotDTO=BeanMapperUtils.map(scenicSpot, ScenicSpotDTO.class);
		return scenicSpotDTO;
	}

}
