package hg.dzpw.dealer.client.dto.scenicspot;

import java.util.List;

import hg.dzpw.dealer.client.common.BaseDTO;

/**
 * @类功能说明: 入盟的景区
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:26:50
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class ScenicSpotDTO extends BaseDTO {

	/**
	 * 景区基本信息
	 */
	private ScenicSpotBaseInfoDTO baseInfo;

	/**
	 * 景区联系信息
	 */
	private ScenicSpotContactInfoDTO contactInfo;
	
	/**
	 * 景区图片
	 */
	private List<ScenicSpotPicDTO> pics;

	public ScenicSpotBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(ScenicSpotBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public ScenicSpotContactInfoDTO getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ScenicSpotContactInfoDTO contactInfo) {
		this.contactInfo = contactInfo;
	}

	public List<ScenicSpotPicDTO> getPics() {
		return pics;
	}

	public void setPics(List<ScenicSpotPicDTO> pics) {
		this.pics = pics;
	}

}