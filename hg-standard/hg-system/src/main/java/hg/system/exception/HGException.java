package hg.system.exception;


@SuppressWarnings("serial")
public class HGException extends BaseException {

	public HGException(int code, String message) {
		super(code, message);
	}

	
	/**
	 * 相册_不存在
	 */
	public final static int CODE_ALBUM_NOT_EXIST = 1001;
	
	/**
	 * 相册_有子节点
	 */
	public final static int CODE_ALBUM_HAS_CHILDS = 1002;
	
	/**
	 * 相册_删除异常
	 */
	public final static int CODE_ALBUM_DEL_ERR = 1003;
	
	/**
	 * 相册_是子节点
	 */
	public final static int CODE_ALBUM_IS_CHILD = 1004;
	
	/**
	 * 相册_是子节点
	 */
	public final static int CODE_ALBUM_PAR_IS_OWN = 1005;
	
	/**
	 * 相册_useType不正确
	 */
	public final static int CODE_ALBUM_USETYPE_ERROR = 1006;
	
	/**
	 * 相册_数据不完整
	 */
	public final static int CODE_ALBUM_DATA_NOT_ENOUGH = 1007;
	
	
	
	/**
	 * 图片_不存在
	 */
	public final static int CODE_IMAGE_NOT_EXIST = 2001;
	
	/**
	 * 图片_有附件
	 */
	public final static int CODE_IMAGE_HAS_SPEC = 2002;
	
	/**
	 * 图片_删除异常
	 */
	public final static int CODE_IMAGE_DEL_ERR = 2003;
	
	/**
	 * 图片_数据不完整
	 */
	public final static int CODE_IMAGE_DATA_NOT_ENOUGH = 2004;
	
	
	
	/**
	 * 图片附件_不存在
	 */
	public final static int CODE_IMAGESPEC_NOT_EXIST = 3001;
	
	/**
	 * 图片附件_删除异常
	 */
	public final static int CODE_IMAGESPEC_DEL_ERR = 3002;
	
	/**
	 * 图片附件_别名_异常
	 */
	public final static int CODE_IMAGESPEC_KEY_ERR = 3003;
	
	/**
	 * 图片附件_别名_不存在
	 */
	public final static int CODE_IMAGESPEC_KEY_NOT_EXIST = 3004;
	
	/**
	 * 图片附件_别名_已存在
	 */
	public final static int CODE_IMAGESPEC_KEY_HAS_EXIST = 3005;
	
	/**
	 * 图片附件_裁剪异常
	 */
	public final static int CODE_IMAGESPEC_CUT_ERROR = 3006;
	
	/**
	 * 图片附件_路径不存在
	 */
	public final static int CODE_IMAGESPEC_URI_NOT_EXIST = 3007;
	
	/**
	 * 图片附件_上传路径不存在
	 */
	public final static int CODE_IMAGESPEC_UPLOAD_PATH_NOT_EXIST = 3008;
	
	/**
	 * 图片附件_已存在
	 */
	public final static int CODE_IMAGESPE_HAS_EXIST = 3009;
	
	/**
	 * 图片附件_上传异常
	 */
	public final static int CODE_IMAGESPE_UPLOAD_ERROR = 3010;
	
	/**
	 * 图片附件_MD5解析异常
	 */
	public final static int CODE_IMAGESPE_MD5_ERROR = 3011;
	
	/**
	 * 图片附件_原图不能删除
	 */
	public final static int CODE_IMAGESPE_DEFAULT_ERROR = 3012;
	
	
	
	
	/**
	 * 没有提供待办事项ID
	 */
	public final static int  BACKLOG_NOT_HAVE_ID = 4001;
	
	/**
	 * 待办事项不存在
	 */
	public final static int BACKLOG_NOT_FOUND = 4002;
	
	/**
	 * 缺少必要参数
	 */
	public final static int BACKLOG_WITHOUT_NEED_PARAM = 4003;
	
	/**
	 * 待办事项执行次数超过规定执行次数
	 */
	public final static int BACKLOG_EXECUTE_COUNT_NOTVALID = 4004;
	
	/**
	 * 待办事项重复执行
	 */
	public final static int BACKLOG_EXECUTE_DUPLICATE = 4005;
	
	
	
	
	/**
	 * 资源添加required
	 */
	public final static int AUTH_PERM_CREATE_NOT_REQUIRED 	= 5001;
	
	/**
	 * 资源command为null
	 */
	public final static int AUTH_PERM_COMMAND_NULL 			= 5002;
	
	/**
	 * 资源不存在
	 */
	public final static int AUTH_PERM_NOT_EXISTS 			= 5003;
	
	
	
	
	/**
	 * 角色添加required
	 */
	public final static int AUTH_ROLE_CREATE_NOT_REQUIRED 	= 6001;
	
	/**
	 * 角色command为null
	 */
	public final static int AUTH_ROLE_COMMAND_NULL 			= 6002;
	
	/**
	 * 角色不存在
	 */
	public final static int AUTH_ROLE_NOT_EXISTS 			= 6003;
	
	/**
	 * 角色qo为null
	 */
	public final static int AUTH_ROLE_QO_NULL 				= 6004;
	
	
	
	
	/**
	 * 员工添加required
	 */
	public final static int STAFF_CREATE_NOT_REQUIRED 		= 7001;
	
	/**
	 * 员工command为null
	 */
	public final static int STAFF_COMMAND_NULL 				= 7002;
	
	/**
	 * 员工不存在
	 */
	public final static int STAFF_NOT_EXISTS 				= 7003;
	
	/**
	 * 员工登陆名已存在
	 */
	public final static int STAFF_LOGIN_NAME_EXISTS 		= 7004;
	
	/**
	 * 员工qo为null
	 */
	public final static int STAFF_QO_NULL 					= 7005;
	
	
	
	
	
	
	/**
	 * 文件不存在
	 */
	public final static int FILE_NOT_EXITS 					= 8001;
	
	/**
	 * 文件格式不正确
	 */
	public final static int FILE_TYPE_ERROR 				= 8002;
	
	/**
	 * 文件像素不正确
	 */
	public final static int FILE_PIX_ERROR 					= 9003;
	
	/**
	 * 文件大小不正确
	 */
	public final static int FILE_SIZE_ERROR 				= 9004;
	
}
