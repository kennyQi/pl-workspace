package slfx.xl.pojo.exception;

/**
 * @类功能说明：商旅分销-线路异常
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @版本：V1.0
 */
public class SlfxXlException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String message;

	public SlfxXlException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
    * 线路不存在
    */
   public final static String RESULT_LINE_NOT_FOUND = "1010";
   
   /**
    * 添加一日行程时所属天数不合理
    */
   public final static String CREATE_DAYROUTE_DAY_NOT_NOTVALID = "1011";
   
   /**
	 * 线路-ID不能为空
	 */
	public final static String LINE_ID_IS_NULL = "1012";
	
   /**
    * 订单不存在
    */
   public final static String RESULT_LINEORDER_NOT_FOUND = "1020";
   
   /**
    * 取消订单缺少必要参数
    */
   public final static String CANCLE_LINEORDER_WITHOUT_PARAM = "1021";
   
   /**
	 * 传递支付订单信息时缺少必要参数
	 */
   public final static String PAY_LINEORDER_WITHOUT_PARAM = "1022";
   
	/**
    * 更改订单状态缺少必要参数
    */
   public final static String CHANGE_LINEORDER_WITHOUT_PARAM = "1023";
   
	/**
	 * 游玩人不存在
	 */
   public final static String RESULT_TRAVELER_NOT_FOUND = "1024";
   
   /**
    * 商城付款类型不明：定金，尾款，全款
    */
   public final static String SHOP_PAY_TYPE_NOT_FOUND = "1025";
   
   /**
	 * 团期信息不存在
	 */
	public final static String RESULT_DATESALEPRICE_NOT_FOUND = "1030";
	
	
	
	/**
	 * 价格政策状态不合理
	 */
	public final static String SALE_POLICY_STATUS_NOT_NOTVALID = "1041";
	
	
	/**
	 * 线路文件夹-ID不能为空
	 */
	public final static String FOLDER_ID_IS_NULL = "1051";
	
	/**
	 * 线路文件夹-参数不存在
	 */
	public final static String FOLDER_WITHOUTPARAM_NOT_EXISTS = "1052";
	
	/**
	 * 线路文件夹-数据不存在
	 */
	public final static String FOLDER_RESULT_NOT_FOUND = "1053";
	
	/**
	 * 线路图片-ID不能为空
	 */
	public final static String PICTURE_ID_IS_NULL = "1054";
	
	/**
	 * 线路图片-参数不存在
	 */
	public final static String PICTURE_WITHOUTPARAM_NOT_EXISTS = "1055";
	
	/**
	 * 线路图片-数据不存在
	 */
	public final static String PICTURE_RESULT_NOT_FOUND = "1056";
	
	/**
	 * 线路图片-图片不存在
	 */
	public final static String PICTURE_IMAGE_NOT_EXISTS = "1057";
	
	/**
	 * 线路图片-图片格式不符合要求
	 */
	public final static String PICTURE_IMAGETYPE_NOT_REQUIRE = "1058";
	
	/**
	 * 线路图片-图片大小超出范围
	 */
	public final static String PICTURE_IMAGESIZE_GT_SCOPE = "1059";
	
	
	
	
	
}

