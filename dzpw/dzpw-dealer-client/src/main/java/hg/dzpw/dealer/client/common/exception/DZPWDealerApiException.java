package hg.dzpw.dealer.client.common.exception;

/**
 * @类功能说明：对经销商接口异常
 * @类修改者：
 * @修改日期：2014-12-17下午4:09:36
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-12-17下午4:09:36
 */
public class DZPWDealerApiException extends Exception {
	private static final long serialVersionUID = 1L;

	/** 无效经销商代码*/
	public final static int DEALER_KEY_ERROR = 1000;
	/** 参数错误  */
	public final static int DEALER_PARAM_ERROR = 1001;
	//---经销商下单接口异常情况---- 
	/** 门票政策已下架或已被删除*/
	public final static int TICKET_POLICY_NOT_EXISTS = 1002;
	/** 门票政策版本错误*/
	public final static int TICKET_POLICY_VERSION_ERROR = 1003;
	/** 无权购买当前景区单票*/
	public final static int DEALER_NO_AUTH_BUY = 1004;
	/** 出游日期错误*/
	public final static int TRAVEL_DATE_ERROR= 1005;
	/** 价格错误*/
	public final static int PRICE_ERROR = 1006;
	//---------------------
	
	/**
	 * 错误编码字符
	 */
	private String code;

	/**
	 * 错误的Class标识
	 */
	private Class<?> clazz;

	public DZPWDealerApiException() {
		super();
	}

	public DZPWDealerApiException(String msg) {
		super(msg);
	}

	public DZPWDealerApiException(Throwable cause, Class<?> clazz) {
		super(cause);
		this.clazz = clazz;
	}

	public DZPWDealerApiException(String msg, Class<?> clazz) {
		super(msg);
		this.clazz = clazz;
	}

	public DZPWDealerApiException(String code, String msg, Class<?> clazz) {
		super(msg);
		this.code = code;
		this.clazz = clazz;
	}

	public DZPWDealerApiException(String msg, Throwable cause, Class<?> clazz) {
		super(msg, cause);
		this.clazz = clazz;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
}
