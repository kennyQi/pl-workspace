package lxs.domain.model.line;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lxs.domain.model.M;
import slfx.xl.pojo.command.price.BatchModifyDateSalePriceCommand;
import slfx.xl.pojo.command.price.CreateDateSalePriceCommand;
import slfx.xl.pojo.command.price.ModifyDateSalePriceCommand;
/**
 * 
 * @类功能说明：价格日历中的一天
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日下午3:22:02
 * 
 */
@Entity
@Table(name = M.TABLE_PREFIX_XL + "DATE_SALE_PRICE")
@SuppressWarnings("serial")
public class DateSalePrice extends BaseModel {

	/**
	 * 属于哪个线路
	 */
	@ManyToOne
	@JoinColumn(name = "LINE_ID")
	private Line line;

	/**
	 * 属于哪天
	 */
	@Column(name = "SALE_DATE")
	private Date saleDate;

	/**
	 * 成人价
	 */
	@Column(name = "ADULT_PRICE")
	private Double adultPrice;

	/**
	 * 儿童价
	 */
	@Column(name = "CHILD_PRICE")
	private Double childPrice;

	/**
	 * 剩余人数
	 */
	@Column(name = "NUMBER")
	private Integer number;

	/**
	 * 
	 * @方法功能说明：修改单天团期
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月18日下午1:56:33
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyDateSalePrice(ModifyDateSalePriceCommand command){
		if(command.getAdultPrice() != null){
			setAdultPrice(command.getAdultPrice());
		}
		if(command.getChildPrice() != null){
			setChildPrice(command.getChildPrice());
		}
		setNumber(command.getNumber() + getNumber()); //人数修改是累加
	}
	
	
	/**
	 * 
	 * @方法功能说明：批量修改团期
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月18日下午5:14:40
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void batchModifyDateSalePrice(BatchModifyDateSalePriceCommand command){
		setAdultPrice(command.getAdultPrice());
		setChildPrice(command.getChildPrice());
		setNumber(command.getNumber() + getNumber());//人数修改是累加
	}
	
	/**
	 * 
	 * @方法功能说明：新增单天团期
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月19日下午4:49:14
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void createDateSalePrice(CreateDateSalePriceCommand command){
		setId(UUIDGenerator.getUUID());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			setSaleDate(format.parse(command.getSaleDate()));
		} catch (ParseException e) {}
		setAdultPrice(command.getAdultPrice());
		setChildPrice(command.getChildPrice());
		setNumber(command.getNumber());
	}
	
	
	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Double getAdultPrice() {
		return adultPrice;
	}

	public void setAdultPrice(Double adultPrice) {
		this.adultPrice = adultPrice;
	}

	public Double getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(Double childPrice) {
		this.childPrice = childPrice;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}