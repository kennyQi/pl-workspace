package plfx.mp.app.service.spi;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.api.client.api.v1.mp.request.qo.MPScenicSpotsQO;
import plfx.api.client.api.v1.mp.response.MPQueryScenicSpotsResponse;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.mp.app.service.local.ScenicSpotLocalService;
import plfx.mp.pojo.dto.scenicspot.ScenicSpotDTO;
import plfx.mp.pojo.dto.supplierpolicy.NoticeTypeDTO;
import plfx.mp.pojo.dto.supplierpolicy.TCPolicyNoticeDTO;
import plfx.mp.qo.PlatformSpotsQO;
import plfx.mp.spi.inter.PlatformSpotService;
import plfx.mp.spi.inter.api.ApiMPScenicSpotsService;

import com.alibaba.fastjson.JSON;

@Service
public class ApiMPScenicSpotsServiceImpl implements ApiMPScenicSpotsService {

	@Autowired
	private PlatformSpotService platformSpotService;
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	@SuppressWarnings("unchecked")
	@Override
	public MPQueryScenicSpotsResponse queryScenicSpots(MPScenicSpotsQO qo) {
		HgLogger.getInstance().info("wuyg", "queryScenicSpots->queryScenicSpots景区查询->开始"+JSON.toJSONString(qo));
		MPQueryScenicSpotsResponse response = new MPQueryScenicSpotsResponse();
		
		// 将api qo转换成SPI查询qo
		Pagination pagination = new Pagination();
		pagination.setPageNo(qo.getPageNo());
		pagination.setPageSize(qo.getPageSize());
		pagination.setCondition(qo);

		// 将pagination里的条件qo转换成SPI查询qo
		Pagination paginationSpi = parsePaginationQo(pagination);

		try {
			// SPI查询
			paginationSpi = platformSpotService.queryPagination(paginationSpi);
			// 返回景区列表
			response.setScenicSpots((List<ScenicSpotDTO>) paginationSpi.getList());
			response.setTotalCount(paginationSpi.getTotalCount());
			// 查询详情时获取景点简介详情
			HgLogger.getInstance().info("wuyg", "queryScenicSpots->queryScenicSpots查询详情时获取景点简介详情"+JSON.toJSONString(qo));
			if (StringUtils.isNotBlank(qo.getScenicSpotId())) {
				String detail = scenicSpotLocalService.getScenicDetail(qo
						.getScenicSpotId());
				List<ScenicSpotDTO> dtos = response.getScenicSpots();
				if (dtos != null && dtos.size() > 0) {
					dtos.get(0).setDetail(detail);
				}
			}
			
			// 检查是否查询buyNotice
			if (!qo.isTcPolicyNoticeFetchAble()) {
				List<ScenicSpotDTO> dtos = response.getScenicSpots();
				if (dtos != null && dtos.size() > 0) {
					for (ScenicSpotDTO dto : dtos) {
						if (dto.getTcScenicSpotInfo() != null && dto.getTcScenicSpotInfo().getTcPolicyNotice() != null) {
							TCPolicyNoticeDTO noticeDTO = new TCPolicyNoticeDTO();
							noticeDTO.setId(dto.getTcScenicSpotInfo().getTcPolicyNotice().getId());
							noticeDTO.setNoticeTypes(new ArrayList<NoticeTypeDTO>());
							dto.getTcScenicSpotInfo().setTcPolicyNotice(noticeDTO);
						}
					}
				}
			}
			
		} catch (Exception e) {
			HgLogger.getInstance();
			HgLogger.getInstance().error("wuyg", "queryScenicSpots->queryScenicSpots景区查询->失败 ,"+e.getMessage()+HgLogger.getStackTrace(e));
			response.setResult(ApiResponse.RESULT_CODE_FAILE);
		}

		return response;
	}
	
	protected PlatformSpotsQO parseQo(MPScenicSpotsQO qo) {
		return BeanMapperUtils.map(qo, PlatformSpotsQO.class);
	}

	protected Pagination parsePaginationQo(Pagination pagination) {
		Pagination pagination2 = BeanMapperUtils.map(pagination, Pagination.class);
		pagination2.setPageNo(pagination.getPageNo());
		pagination2.setPageSize(pagination.getPageSize());
		MPScenicSpotsQO qo = (MPScenicSpotsQO) pagination.getCondition();
		PlatformSpotsQO scenicSpotQO = parseQo(qo);
		pagination2.setCondition(scenicSpotQO);
		return pagination2;
	}

}
