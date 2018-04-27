package hsl.domain.model.xl.salepolicy;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.domain.model.xl.Line;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @类功能说明：价格政策快照
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日下午2:32:14
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_HSL_XL+"SALE_POLICY_SNAPSHOT")
public class SalePolicySnapshot extends BaseModel {

	/**
	 * 快照来源实体
	 */
	@ManyToOne
	@JoinColumn(name = "SALE_POLICY_ID")
	private SalePolicy salePolicy;

	/**
	 * 价格政策名称
	 */
	@Column(name="SALE_POLICY_NAME", length=64)
	private String salePolicyName;

	/**
	 * 价格政策类型
	 */
	@Column(name="TYPE",columnDefinition =M.NUM_COLUM)
	private Integer priceType;
	
	/**
	 * 创建时间
	 */
	@Column(name="CREATE_DATE")
	private Date createDate;

	/**
	 * 所有快照信息，SalePolicy对象的JSON
	 */
	@JSONField(serialize = false)
	@Column(name = "ALL_INFO_SALE_POLICY_JSON",columnDefinition = M.TEXT_COLUM)
	private String allInfoSalePolicyJSON;

	
	/**
	 * 
	 * @方法功能说明：创建快照
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月9日下午3:51:52
	 * @修改内容：
	 * @参数：@param salePolicy
	 * @return:void
	 * @throws
	 */
	public void create(SalePolicy salePolicy){
		setId(UUIDGenerator.getUUID());
		setCreateDate(new Date());
		setSalePolicyName(salePolicy.getSalePolicyLineName());
		setPriceType(salePolicy.getPriceType());
		//政策快照只存储线路ID，不存储线路详细信息
		List<Line> lineIDList = new ArrayList<Line>();
		List<Line> lineList = salePolicy.getLines();
		for(Line line:lineList){
			Line l = new Line();
			l.setId(line.getId());
			lineIDList.add(l);
		}
		salePolicy.setLines(lineIDList);
		salePolicy.setSnapshotList(null);
		setAllInfoSalePolicyJSON(JSON.toJSONString(salePolicy));
		setSalePolicy(salePolicy);
	}

	public SalePolicy getSalePolicy() {
		return salePolicy;
	}
	public void setSalePolicy(SalePolicy salePolicy) {
		this.salePolicy = salePolicy;
	}
	public String getSalePolicyName() {
		return salePolicyName;
	}
	public void setSalePolicyName(String salePolicyName) {
		this.salePolicyName = salePolicyName;
	}
	public Integer getPriceType() {
		return priceType;
	}
	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}
	public String getAllInfoSalePolicyJSON() {
		return allInfoSalePolicyJSON;
	}
	public void setAllInfoSalePolicyJSON(String allInfoSalePolicyJSON) {
		this.allInfoSalePolicyJSON = allInfoSalePolicyJSON;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}