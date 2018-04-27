package hg.service.image.domain.model;

import hg.common.component.BaseModel;
import hg.system.model.staff.Staff;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @类功能说明：工程表
 * @类修改者：zzb
 * @修改日期：2014年11月28日下午3:34:41
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月28日下午3:34:41
 */
@Entity(name="imgProject")
@Table(name = "SYS_PROJECT")
public class Project extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 工程名称
	 */
	@Column(name = "PROJECT_NAME", length = 128)
	private String name;
	
	/**
	 * 工程环境
	 */
	@Column(name = "PROJEC_ENV_NAME", length = 128)
	private String envName;
	
	/**
	 * 工程描述
	 */
	@Column(name = "PROJECT_DESC", length = 128)
	private String desc;
	
	/**
	 * 工程和人员关联
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STAFF_ID")
	private Staff staff;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName;
	}
}