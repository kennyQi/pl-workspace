package hsl.domain.model.mp.ad;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.domain.model.programa.ProgramaContent;
import hsl.pojo.command.ad.CreateSpecCommand;
import hsl.pojo.command.ad.UpdateSpecCommand;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @类功能说明：特价门票活动
 * @类修改者：zhuxy
 * @修改日期：2014年12月12日上午10:08:57
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月12日上午10:08:57
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_HSL_MP+"SPECIAL_OFFER_MP")
public class SpecialOfferMp extends BaseModel{
	public static final String ZTLY = "hsl_spec_ztly";
	public static final String GZMQ = "hsl_spec_gzmq";
	public static final String YGXG = "hsl_spec_ygxg";
	public static final String SQQS = "hsl_spec_sqqs";
	public static final String SSYL = "hsl_spec_ssyl";
	public static final String MJRW = "hsl_spec_mjrw";
	public static final String WZHW = "hsl_spec_wzhw";
	public static final String YPWQ = "hsl_spec_ypwq";
	
	public static Map<String,String> TYPEMAP = new HashMap<String,String>();
	
	public static Map<String,String> getTypeMap(){
		TYPEMAP.put(ZTLY, "主题乐园");
		TYPEMAP.put(GZMQ, "古镇迷情");
//		TYPEMAP.put(YGXG, "游逛香港");
//		TYPEMAP.put(SQQS, "暑期亲水");
		TYPEMAP.put(SSYL, "山水园林");
		TYPEMAP.put(MJRW, "美景人文");
//		TYPEMAP.put(WZHW, "玩转海外");
		TYPEMAP.put(YPWQ, "约泡温泉");
		return TYPEMAP;
	}

	
	
	/**
	 * 原价价格
	 */
	@Column(name = "PRICE", columnDefinition = M.MONEY_COLUM)
	private Double price;
	/**
	 * 特价价格
	 */
	@Column(name = "SPEC_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double specPrice;
	/**
	 * 景点类型
	 */
	@Column(name = "SCENIC_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private int scenicType;
	/**
	 * 广告ID
	 */
	@Column(name = "AD_ID", length = 64)
	private String adId;
	
	/**
	 * 所属的栏目的内容
	 */
	@ManyToOne
	@JoinColumn(name="content_ID")
	private ProgramaContent programaContent;

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

	/**
	 * 创建一个特价门票
	 * @param command
	 */
	public void create(CreateSpecCommand command) {
		this.setId(UUIDGenerator.getUUID());
		this.setPrice(command.getPrice());
		//this.setScenicType(command.getScenicType());
		this.setSpecPrice(command.getSpecPrice());
		this.setAdId(command.getAdId());
		
		ProgramaContent content=new ProgramaContent();
		content.setId(command.getContentId());
		this.setProgramaContent(content);
	}
	
	/**
	 * 更新特价门票
	 * @param command
	 */
	public void update(UpdateSpecCommand command){
		this.setPrice(command.getPrice());
		this.setSpecPrice(command.getSpecPrice());
		this.setAdId(command.getAdId());
		
		ProgramaContent content=new ProgramaContent();
		content.setId(command.getContentId());
		this.setProgramaContent(content);
	}

	

	public ProgramaContent getProgramaContent() {
		return programaContent;
	}

	public void setProgramaContent(ProgramaContent programaContent) {
		this.programaContent = programaContent;
	}
	

}