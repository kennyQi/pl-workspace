package hsl.pojo.qo.dzp.scenicspot;

import hg.common.page.Pagination;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * 电子票景区请求查询对象，用于接收页面参数
 *
 * @author zhurz
 * @since 1.8
 */
@SuppressWarnings("serial")
public class DZPScenicSpotRQO implements Serializable {

	/**
	 * 查询名称
	 */
	private String q;
	/**
	 * 省ID
	 */
	private String provinceId;
	/**
	 * 市ID
	 */
	private String cityId;
	/**
	 * 价格区间 - 最低价格
	 */
	private Integer priceMin;
	/**
	 * 价格区间 - 最高价格
	 */
	private Integer priceMax;
	/**
	 * 景区级别
	 */
	private Integer level;
	/**
	 * 排序
	 * <pre>
	 *     1 - 修改时间降序（默认）
	 *     2 - 修改时间升序
	 *     3 - 价格升序
	 *     4 - 价格降序
	 * </pre>
	 */
	private Integer order;
	/**
	 * 页码
	 */
	private Integer pageNo;
	/**
	 * 分页大小
	 */
	private Integer pageSize;

	/**
	 * 构建分页查询对象
	 *
	 * @return
	 */
	public Pagination buildQueryPagination() {
		Pagination pagination = new Pagination();
		pagination.setPageNo(getPageNo());
		pagination.setPageSize(getPageSize());
		pagination.setCondition(buildQO());
		return pagination;
	}

	/**
	 * 构建Service查询对象
	 *
	 * @return
	 */
	public DZPScenicSpotQO buildQO() {

		DZPScenicSpotQO qo = new DZPScenicSpotQO();
		qo.setNameOrPolicyName(getQ());
		qo.setQueryTicketPolicy(true);
		if (StringUtils.isBlank(qo.getCityId()))
			qo.setProvinceId(getProvinceId());
		qo.setCityId(getCityId());
		qo.setPriceMinBegin(getPriceMin() == null ? 0d : Double.valueOf(getPriceMin()));
		qo.setPriceMinEnd(getPriceMax() == null ? null : Double.valueOf(getPriceMax()));
		qo.setLevel(getLevel());
		
		if (Integer.valueOf(2).equals(getOrder()))
			// 修改时间升序
			qo.setModifyDateOrder(1);
		else if (Integer.valueOf(3).equals(getOrder()))
			// 价格升序
			qo.setPriceMinOrder(1);
		else if (Integer.valueOf(3).equals(getOrder()))
			// 价格降序
			qo.setPriceMinOrder(-1);
		else
			// 修改时间降序（默认）
			qo.setModifyDateOrder(-1);

		return qo;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Integer getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(Integer priceMin) {
		this.priceMin = priceMin;
	}

	public Integer getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(Integer priceMax) {
		this.priceMax = priceMax;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize != null) {
			if (pageSize < 1) pageSize = 1;
			else if (pageSize > 100) pageSize = 100;
		}
		this.pageSize = pageSize;
	}
}
