package hg.dzpw.app.service.api.dealer;

import hg.common.page.Pagination;
import hg.dzpw.app.common.adapter.DealerApiAdapter;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.dealer.client.api.v1.request.ScenicSpotQO;
import hg.dzpw.dealer.client.api.v1.response.ScenicSpotResponse;
import hg.dzpw.dealer.client.common.ApiRequest;
import hg.dzpw.dealer.client.common.api.DealerApiAction;
import hg.dzpw.dealer.client.common.api.DealerApiService;
import hg.dzpw.dealer.client.dto.scenicspot.ScenicSpotDTO;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：面向经销商的景区服务
 * @类修改者：
 * @修改日期：2014-12-5下午4:00:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-12-5下午4:00:07
 */
@Service
public class ScenicSpotDealerApiService implements DealerApiService {

	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	

	/**
	 * @方法功能说明：查询景区
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-5下午4:04:22
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:ScenicSpotResponse
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@DealerApiAction(DealerApiAction.QueryScenicSpot)
	public ScenicSpotResponse queryScenicSpots(ApiRequest<ScenicSpotQO> request) {

		ScenicSpotResponse response = new ScenicSpotResponse();
		try {
			// 组装查询条件
			ScenicSpotQO QO = request.getBody();
			Pagination pagination = scenicSpotLocalService.queryPagination(DealerApiAdapter.convertPaginationCondition(QO));

			List<ScenicSpot> scenicSpots = pagination.getTotalCount() > 0 ? (List<ScenicSpot>) pagination
					.getList() : new ArrayList<ScenicSpot>();
					
			// 类型转换
			List<ScenicSpotDTO> list = new ArrayList<ScenicSpotDTO>();

			for (ScenicSpot scenicSpot : scenicSpots)
				list.add(DealerApiAdapter.scenicSpot.convertDTO(scenicSpot));

			response.setScenicSpots(list);
			response.setResult(ScenicSpotResponse.RESULT_SUCCESS);
			response.setMessage("查询成功");
			response.setPageNo(pagination.getPageNo());
			response.setPageSize(pagination.getPageSize());
			response.setTotalCount(pagination.getTotalCount());

			return response;
		} catch (Exception e) {
			e.printStackTrace();
			response.setResult(ScenicSpotResponse.RESULT_QUERY_FAIL);
			response.setMessage("查询失败");
			return response;
		}
	}

}
