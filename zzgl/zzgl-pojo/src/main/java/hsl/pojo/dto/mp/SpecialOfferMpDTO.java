package hsl.pojo.dto.mp;

import hsl.pojo.dto.ad.HslAdDTO;
import hsl.pojo.dto.programa.ProgramaContentDTO;


@SuppressWarnings("serial")
public class SpecialOfferMpDTO extends HslAdDTO{
	/**
	 * 原价价格
	 */
	private Double price;
	/**
	 * 特价价格
	 */
	private Double specPrice;
	/**
	 * 景点类型
	 */
	private int scenicType;
	/**
	 * 广告ID
	 */
	private String adId;
	
	/** 
	 * 所属的栏目的内容
	 */
	private ProgramaContentDTO programaContent;
	 
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
	public int getScenicType() {
		return scenicType;
	}
	public void setScenicType(int scenicType) {
		this.scenicType = scenicType;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public ProgramaContentDTO getProgramaContent() {
		return programaContent;
	}
	public void setProgramaContent(ProgramaContentDTO programaContent) {
		this.programaContent = programaContent;
	}

}
