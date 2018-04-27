package hg.pojo.exception;

@SuppressWarnings("serial")
public class WarehouseException extends BaseException {
	
	public WarehouseException(Integer code, String message){
		super(code,message);
	}

	/**
	 * 仓库类型名称重复
	 */
	public final static Integer RESULT_WAREHOUSETYPE_NAME_REPEAT=1;
	
	/**
	 * 仓库类型已使用
	 */
	public final static Integer RESULT_WAREHOUSETYPE_USE=2;
	
	/**
	 * 仓库名称重复
	 */
	public final static Integer RESULT_WAREHOUSE_NAME_REPEAT=3;
	
	/**
	 * 仓库已使用
	 */
	public final static Integer RESULT_WAREHOUSE_USE=4;

}
