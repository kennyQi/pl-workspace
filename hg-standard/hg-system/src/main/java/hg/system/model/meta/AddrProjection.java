package hg.system.model.meta;

import hg.common.component.BaseModel;
import hg.system.model.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 行政区映射
 * 
 * @author zhurz
 */
@Entity
@Table(name = M.TABLE_PREFIX_SYS + "ADDR_PROJECTION")
public class AddrProjection extends BaseModel {
	private static final long serialVersionUID = 1L;

	public final static Integer ADDR_TYPE_PROV = 1;
	public final static Integer ADDR_TYPE_CITY = 2;
	public final static Integer ADDR_TYPE_AREA = 3;

	/** 同程 */
	public final static Integer CHANNEL_TYPE_TC = 1;

	/**
	 * 本地地址CODE
	 */
	@Column(name = "ADDR_CODE", length = 64)
	private String addrCode;
	/**
	 * 地址类型(省市区)
	 */
	@Column(name = "ADDR_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer addrType;
	/**
	 * 地址名称
	 */
	@Column(name = "ADDR_NAME", length = 64)
	private String addrName;
	/**
	 * 渠道类型
	 */
	@Column(name = "CHANNEL_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer channelType;
	/**
	 * 渠道对应的地址CODE
	 */
	@Column(name = "CHANNEL_ADDR_CODE", length = 64)
	private String channelAddrCode;

	public String getAddrCode() {
		return addrCode;
	}

	public void setAddrCode(String addrCode) {
		this.addrCode = addrCode;
	}

	public Integer getAddrType() {
		return addrType;
	}

	public void setAddrType(Integer addrType) {
		this.addrType = addrType;
	}

	public String getAddrName() {
		return addrName;
	}

	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public String getChannelAddrCode() {
		return channelAddrCode;
	}

	public void setChannelAddrCode(String channelAddrCode) {
		this.channelAddrCode = channelAddrCode;
	}

}
