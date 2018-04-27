package hg.dzpw.dealer.client.dto.dealer;

import hg.dzpw.dealer.client.common.BaseDTO;

/**
 * @类功能说明：经销商
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-11-10下午2:10:05
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class DealerDTO extends BaseDTO {

	/**
	 * 经销商基础信息
	 */
	private DealerBaseInfoDTO baseInfo;

	public DealerBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(DealerBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}

}