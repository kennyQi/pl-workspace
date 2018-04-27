package hg.dzpw.dealer.client.api.v1.request;

import hg.dzpw.dealer.client.common.BaseClientQO;
import hg.dzpw.dealer.client.common.api.DealerApiAction;

/**
 * @类功能说明：行政区域查询
 * @类修改者：
 * @修改日期：2014-11-21下午5:14:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-21下午5:14:17
 */
@SuppressWarnings("serial")
@DealerApiAction(DealerApiAction.QueryRegion)
public class RegionQO extends BaseClientQO {

	/** 行政区类型：查询省 */
	public final static Integer TYPE_PROVINCE = 1;
	
	/** 行政区类型：查询市*/
	public final static Integer TYPE_CITY = 2;
	
	/** 行政区类型：查询区 */
	public final static Integer TYPE_AREA = 3;

	/**
	 * 行政区类型 必传
	 */
	private Integer type;
	
	/**
	 * 行政区编码
	 */
	private String code;

	/**
	 * 上级行政区编码(查询省份时 该字段为null)
	 */
	private String parentCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

}
