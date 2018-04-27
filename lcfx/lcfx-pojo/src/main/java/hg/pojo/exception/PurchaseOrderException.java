package hg.pojo.exception;

@SuppressWarnings("serial")
public class PurchaseOrderException extends BaseException {

	public PurchaseOrderException (Integer code, String message){
		super(code,message);
	}

	public final static Integer PURCHASE_ORDER_IS_USING=1;
	
}
