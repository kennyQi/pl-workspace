package hg.common.model.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统参数配置信息
 * 
 * @author zhurz
 */
public class SysConfig {

	public final static Short ENABLE_FALSE = 0;
	public final static Short ENABLE_TRUE = 1;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "assigned")
	@Column(name = "ID", unique = true)
	private String id;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE")
	private Date createDate;

	/**
	 * 最后修改时间
	 */
	@Column(name = "LAST_MODIFIED_DATE")
	private Date lastModifiedDate;

	/** 配置名（唯一且不可修改） */
	private String confName;
	/** 配置值 */
	private String confValue;

	private String remark;

	/** 是否可用 0/1:不可用/可用 */
	private Short enable;

	public String getConfName() {
		return confName;
	}

	public void setConfName(String confName) {
		this.confName = confName;
	}

	public String getConfValue() {
		return confValue;
	}

	public void setConfValue(String confValue) {
		this.confValue = confValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getEnable() {
		return enable;
	}

	public void setEnable(Short enable) {
		this.enable = enable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/** 是否可用 */
	public boolean enable() {
		if (ENABLE_TRUE.equals(this.enable)) {
			return true;
		} else if (ENABLE_FALSE.equals(this.enable)) {
			return false;
		}
		return false;
	}
}
