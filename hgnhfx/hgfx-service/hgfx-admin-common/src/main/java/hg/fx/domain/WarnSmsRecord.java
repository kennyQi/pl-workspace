package hg.fx.domain;

import java.util.Date;

import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @类功能说明：余额警戒(预警)短信记录
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-5-31下午7:08:58
 * @版本：V1.0
 *
 */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "WARN_SMS_RECORD")
public class WarnSmsRecord extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 短信接收商户
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DISTRIBUTOR_ID")
	private Distributor distributor;
	
	/**
	 * 系统发出时间（调用短信平台）
	 */
	@Column(name = "SEND_TIME", columnDefinition = O.DATE_COLUM)
	private Date sendTime;
	
	/**
	 * 接收手机号
	 * */
	@Column(name = "MOBILE", length = 16)
	private String mobile;
	
	/**
	 * 短信内容
	 */
	@Column(name = "CONTENT", length = 512)
	private String content;

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
