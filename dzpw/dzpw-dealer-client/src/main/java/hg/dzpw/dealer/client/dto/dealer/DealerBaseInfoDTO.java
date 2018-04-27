package hg.dzpw.dealer.client.dto.dealer;

import hg.dzpw.dealer.client.common.EmbeddDTO;

/**
 * @类功能说明：经销商基础信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-11-10下午2:11:06
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class DealerBaseInfoDTO extends EmbeddDTO {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 简介
	 */
	private String intro;

	/**
	 * 联系电话
	 */
	private String telephone;

	/**
	 * 联系邮箱
	 */
	private String email;

	/**
	 * 联系人
	 */
	private String linkMan;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

}