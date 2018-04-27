package hg.dzpw.dealer.client.dto.meta;

import hg.dzpw.dealer.client.common.BaseDTO;

/**
 * @类功能说明：行政区
 * @类修改者：
 * @修改日期：2014-11-24上午10:23:34
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-24上午10:23:34
 */
@SuppressWarnings("serial")
public class RegionDTO extends BaseDTO {

	/** 行政区类型：省 */
	public final static int TYPE_PROVINCE = 1;
	/** 行政区类型：市 */
	public final static int TYPE_CITY = 2;
	/** 行政区类型：区 */
	public final static int TYPE_AREA = 3;

	/**
	 * 行政区类型
	 */
	private int type;
	
	/**
	 * 行政区名称
	 */
	private String name;
	
	/**
	 * 行政区编码
	 */
	private String code;
	
	/**
	 * 上级行政区代码（省份没有parentcode）
	 */
	private String parentCode;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

}
