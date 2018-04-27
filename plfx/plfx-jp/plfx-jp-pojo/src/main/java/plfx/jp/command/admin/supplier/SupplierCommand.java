package plfx.jp.command.admin.supplier;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：添加、修改、供应商的command类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午3:03:46
 * @版本：V1.0
 *
 */
public class SupplierCommand  extends BaseCommand {
	/**
	 * @FieldsserialVersionUID:TODO
	 */
	private static final long serialVersionUID = -1943540135660669378L;
	
	private String id;
	
	private String name;
	
	private String code;
	
	private String number;
	
	private String[] ids;
	
	private String flag;
	
	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	//private Date createDate;
	
	private String status;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
