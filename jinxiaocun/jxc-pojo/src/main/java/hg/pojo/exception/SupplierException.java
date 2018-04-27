package hg.pojo.exception;

@SuppressWarnings("serial")
public class SupplierException extends BaseException {

	public SupplierException(Integer code, String message){
		super(code, message);
	}
	
	/**
	 * 供应商名称重复
	 */
	public final static Integer RESULT_SUPPLIER_NAME_REPEAT=1;
	
	/**
	 * 供应商已使用
	 */
	public final static Integer RESULT_SUPPLIER_USE=2;
	
}
