package hg.service.image.domain.qo;

import hg.common.component.BaseQo;
import hg.system.qo.AuthStaffQo;

/**
 * @类功能说明：工程查询对象
 * @类修改者：zzb
 * @修改日期：2014年11月28日下午4:23:53
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月28日下午4:23:53
 */
@SuppressWarnings("serial")
public class ProjectLocalQo extends BaseQo {

	/**
	 * 工程名
	 */
	private String name;

	/**
	 * 工程环境
	 */
	private String envName;
	
	/**
	 * 关联的操作员
	 */
	private AuthStaffQo staffQo;
	
	public ProjectLocalQo(){
		super();
	}
	public ProjectLocalQo(String id){
		super();
		setId(id);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName == null ? null : envName.trim();
	}
	public AuthStaffQo getStaffQo() {
		return staffQo;
	}
	public void setStaffQo(AuthStaffQo staffQo) {
		this.staffQo = staffQo;
	}
}