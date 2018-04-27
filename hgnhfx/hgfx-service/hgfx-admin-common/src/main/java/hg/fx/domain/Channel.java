package hg.fx.domain;

import java.util.List;

import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @类功能说明：渠道
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-5-31下午4:33:44
 * @版本：V1.0
 *
 */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "CHANNEL")
public class Channel extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 渠道名 v0.1只有南航
	 * */
	@Column(name = "CHANNEL_NAME", length = 32)
	private String channelName;
	
	/**
	 * 联系人姓名
	 * */
	@Column(name = "LINK_MAN", length = 32)
	private String linkMan;
	
	/**
	 * 联系电话--手机
	 * */
	@Column(name = "MOBILE", length = 16)
	private String mobile;
	
	/**
	 * 联系电话--座机
	 * */
	@Column(name = "PHONE", length = 16)
	private String phone;
	
	/**
	 * 备注
	 * */
	@Column(name = "REMARK", length = 256)
	private String remark;
	
	/**
	 * 渠道下面所属的商品
	 * v0.1只有南航里程(渠道只有南航)
	 */
	@OneToMany(mappedBy = "channel", cascade = { CascadeType.ALL })
	private List<Product> products;
	

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
}
