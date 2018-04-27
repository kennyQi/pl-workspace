package hg.service.ad.domain.model.position;
import hg.system.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明：广告位的基本信息
 * @类修改者：
 * @修改日期：2014年12月11日下午4:05:05
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：yuxx
 * @创建时间：2014年12月11日下午4:05:05
 */
@Embeddable
public class AdPositionBaseInfo {
	/**
	 * 广告位名称
	 */
	@Column(name="NAME", length = 64)
	private String name;
	
	/**
	 * 客户端类型
	 */
	@Column(name="CLIENT_TYPE",columnDefinition=M.NUM_COLUM)
	private Integer clientType;
	
	public static int CLIENT_TYPE_COMMON=0;	//	客户端类型通用
	public static int CLIENT_TYPE_PC=1;	//	客户端类型 pc端
	public static int CLIENT_TYPE_H5=2;	//	客户端类型 H5端
	public static int CLIENT_TYPE_ANDROID=3;		//	客户端类型 安卓端
	public static int CLIENT_TYPE_IOS=4;		//	客户端类型 IOS端
	
	/**
	 * 描述
	 */
	@Column(name="DESCRIPTION", length = 1024)
	private String description;
	
	/**
	 * 图片服务用途
	 */
	@Column(name="IMAGE_USE_TYPE_ID",length = 64)
	private String imageUseTypeId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUseTypeId() {
		return imageUseTypeId;
	}

	public void setImageUseTypeId(String imageUseTypeId) {
		this.imageUseTypeId = imageUseTypeId;
	}

}