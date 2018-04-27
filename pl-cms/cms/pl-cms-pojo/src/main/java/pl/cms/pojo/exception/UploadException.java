package pl.cms.pojo.exception;

/**
 * 
 * @类功能说明：文件上传异常类
 * @类修改者：zzb
 * @修改日期：2014年9月1日上午10:37:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月1日上午10:37:50
 *
 */
@SuppressWarnings("serial")
public class UploadException extends BaseException {

	public UploadException(int code, String message) {
		super(code, message);
	}

	/**
	 * 文件不存在
	 */
	public final static int FILE_NOT_EXITS = 1;
	
	/**
	 * 文件格式不正确
	 */
	public final static int FILE_TYPE_ERROR = 2;
	
	/**
	 * 文件像素不正确
	 */
	public final static int FILE_PIX_ERROR = 3;
	
	/**
	 * 文件大小不
     * 正确
	 */
	public final static int FILE_SIZE_ERROR = 4;
	
}
