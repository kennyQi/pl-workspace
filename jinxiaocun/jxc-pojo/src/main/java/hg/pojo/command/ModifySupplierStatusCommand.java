package hg.pojo.command;


/**
 * 修改供应商状态
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class ModifySupplierStatusCommand extends JxcCommand {

	/**
	 * 供应商id
	 */
	private String supplierId;
	
	/**
	 * 供应商状态
	 */
	private Integer status;
	
	/**
	 * 审核内容
	 */
	private String content;

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
