package hsl.domain.model.preferential;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.CreatePreferentialCommand;
import hsl.pojo.command.UpdatePreferentialCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @类功能说明：特惠专区广告位
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhaows
 * @创建时间：2015年4月27日上午10:09:03
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_HSL_AD+"Preferential")
public class Preferential extends BaseModel{
	/**
	 * 原价价格
	 */
	@Column(name="PRICE", columnDefinition = M.MONEY_COLUM)
	private Double price;
	/**
	 * 特价价格
	 */
	@Column(name="SPEC_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double specPrice;
	/**
	 * 发团日期
	 */
	@Column(name="START_DATE", columnDefinition = M.DATE_COLUM)
	private Date startDate;
	/**
	 * 广告ID
	 */
	@Column(name = "AD_ID", length = 64)
	private String adId;
	public void create(CreatePreferentialCommand command) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.setId(UUIDGenerator.getUUID());
		this.setPrice(command.getPrice());
		this.setSpecPrice(command.getSpecPrice());
		this.setStartDate(sdf.parse(command.getStartDate()));
		this.setAdId(command.getAdId());
	}
	public void update(UpdatePreferentialCommand command) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(command.getPrice()!=null){
			this.price = command.getPrice();
		}
		if(command.getSpecPrice()!=null){
			this.specPrice = command.getSpecPrice();
		}
		if(command.getStartDate()!=null){
			this.startDate = sdf.parse(command.getStartDate());
		}
		if(command.getAdId()!=null){
		this.setAdId(command.getAdId());
		}
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getSpecPrice() {
		return specPrice;
	}
	public void setSpecPrice(Double specPrice) {
		this.specPrice = specPrice;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
}
