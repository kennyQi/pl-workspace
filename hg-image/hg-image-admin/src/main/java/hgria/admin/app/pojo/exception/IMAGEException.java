package hgria.admin.app.pojo.exception;

@SuppressWarnings("serial")
public class IMAGEException extends BaseException {

	public IMAGEException() {
		super();
	}

	public IMAGEException(int code, String message) {
		super(code, message);
	}
	
	
	// ----------------------- 异常代码定义 -----------------------
	
	// 工程
	/** 工程创建字段不足 */
	public final static int PROJECT_CREATE_NOT_REQUIRED = 1001;
	/** 工程管理员被占用 */
	public final static int PROJECT_STAFF_USED 		    = 1002;
	/** 工程id为空 */
	public final static int PROJECT_ID_IS_NULL			= 1003;
	/** 工程不存在 */
	public final static int PROJECT_NOT_EXISTS 			= 1004;

}
