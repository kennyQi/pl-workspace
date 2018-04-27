package hg.pojo.dto.ems;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_ems_deliverytrace")
public class TraceDTO {


	@Id
//	@GeneratedValue(generator = "generator")
//	@GenericGenerator(name = "generator", strategy = "com.ckm.base.util.UUIDGenerator")
	@Column(name = "ID", unique = true, nullable = false, precision = 36, scale = 0)
	private String id;
	
	
	/**
	 * 事务ID
	 */
	@Column(name = "transactionId")
	private String transaction_id;
	
	/**
	 * 运单号
	 */
	@Column(name = "mailno")
	private String mailno;
	
	/**
	 * 订单号
	 */
	@Column(name = "orderId")
	private String order_id;
	
	/**
	 * 操作时间
	 */
	@Column(name = "operateTime")
	private String operate_time;
	
	/**
	 * 操作类型
	 */
	@Column(name = "operateType")
	private String operate_type;
	
	/**
	 * 操作描述
	 */
	@Column(name = "operateDesc")
	private String operate_desc;
	
	/**
	 * 操作机构
	 */
	@Column(name = "operateOrg")
	private String operate_org;
	
	/**
	 * 扩展属性1(妥投的签收人信息)
	 */
	@Column(name = "extAttr1")
	private String ext_attr1;
	
	/**
	 * 扩展属性2(未妥投的原因)
	 */
	@Column(name = "extAttr2")
	private String ext_attr2;
	
	/**
	 * 扩展属性3
	 */
	@Column(name = "extAttr3")
	private String ext_attr3;
	
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getMailno() {
		return mailno;
	}

	public void setMailno(String mailno) {
		this.mailno = mailno;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOperate_time() {
		return operate_time;
	}

	public void setOperate_time(String operate_time) {
		this.operate_time = operate_time;
	}

	public String getOperate_type() {
		return operate_type;
	}

	public void setOperate_type(String operate_type) {
		this.operate_type = operate_type;
	}

	public String getOperate_desc() {
		return operate_desc;
	}

	public void setOperate_desc(String operate_desc) {
		this.operate_desc = operate_desc;
	}

	public String getOperate_org() {
		return operate_org;
	}

	public void setOperate_org(String operate_org) {
		this.operate_org = operate_org;
	}

	public String getExt_attr1() {
		return ext_attr1;
	}

	public void setExt_attr1(String ext_attr1) {
		this.ext_attr1 = ext_attr1;
	}

	public String getExt_attr2() {
		return ext_attr2;
	}

	public void setExt_attr2(String ext_attr2) {
		this.ext_attr2 = ext_attr2;
	}

	public String getExt_attr3() {
		return ext_attr3;
	}

	public void setExt_attr3(String ext_attr3) {
		this.ext_attr3 = ext_attr3;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
