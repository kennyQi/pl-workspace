package hg.dzpw.dealer.client.api.v1.response;

import java.util.List;

import hg.dzpw.dealer.client.common.ApiResponse;
import hg.dzpw.dealer.client.dto.meta.RegionDTO;

/**
 * @类功能说明：行政区查询结果
 * @类修改者：
 * @修改日期：2014-11-24上午10:27:42
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-24上午10:27:42
 */
public class RegionResponse extends ApiResponse {
	private static final long serialVersionUID = 1L;

	/** 缺少必填参数 */
	public final static String RESULT_REQUIRED_PARAM = "-1";

	/** 查询失败 */
	public final static String RESULT_QUERY_FAIL = "-2";

	/**
	 * 行政区列表
	 */
	private List<RegionDTO> regions;

	public List<RegionDTO> getRegions() {
		return regions;
	}

	public void setRegions(List<RegionDTO> regions) {
		this.regions = regions;
	}

}
