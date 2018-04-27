package hg.service.image.pojo.exception;

import hg.service.image.base.BaseException;

/**
 * @类功能说明：图片自定义异常
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @操作时间：2014年11月3日 下午6:25:56
 */
@SuppressWarnings("serial")
public class ImageException extends BaseException{

	public ImageException(){
		super();
	}
	public ImageException(int code){
		super(code);
	}
	public ImageException(int code, String message) {
		super(code, message);
	}
	
	
	/**
     * 异常定义 
     * 1.相册管理模块异常编号 1000-1999
     * 2.图片管理模块异常编号 2000-2999
     * 3.图片规格管理模块异常编号 3000-3999
     * 4.用途管理模块异常编号 4000-4999
     * 5.项目模块异常编号 5000-5999
     * 9.其他异常编号 6000-6999
     */
	// --------------------- 相册管理 ---------------------
	/**
	 * 操作相册缺少参数
	 */
	public final static Integer NEED_ALBUM_WITHOUTPARAM = 1000;
	
	/**
	 * 相册不存在或已删除
	 */
	public final static Integer RESULT_ALBUM_NOTFOUND = 1001;
	
	// --------------------- 图片管理 ---------------------
	/**
	 * 操作图片缺少参数
	 */
	public final static Integer NEED_IMAGE_WITHOUTPARAM = 2000;
	
	/**
	 * 图片不存在或已删除
	 */
	public final static Integer RESULT_IMAGE_NOTFOUND = 2001;
	
	/**
	 * 默认原图不存在
	 */
	public final static Integer IMAGE_DEFAULT_NOTEXIT = 2002;
	
	// --------------------- 图片规格管理 ---------------------
	/**
	 * 操作图片规格缺少参数
	 */
	public final static Integer NEED_IMAGESPEC_WITHOUTPARAM = 3000;
	
	/**
	 * 图片规格不存在或已删除
	 */
	public final static Integer RESULT_IMAGESPEC_NOTFOUND = 3001;
	
	// --------------------- 用途管理 ---------------------
	/**
	 * 操作用途缺少参数
	 */
	public final static Integer NEED_USETYPE_WITHOUTPARAM = 4000;
	
	/**
	 * 用途不存在或已删除
	 */
	public final static Integer RESULT_USETYPE_NOTFOUND = 4001;
	
	/**
	 * 图片所选用途和相册用途不一致
	 */
	public final static Integer NEED_USETYPE_NOTEQUAL = 2002;
	
	// --------------------- 项目模块 ---------------------
	/**
	 * 查询项目缺少参数
	 */
	public final static Integer NEED_PROJECT_WITHOUTPARAM = 5000;
	
	/**
	 * 用途不存在或已删除
	 */
	public final static Integer RESULT_PROJECT_NOTFOUND = 5001;
	// --------------------- 其它 ---------------------
	/**
	 * 缺少FastDFS配置必要参数
	 */
	public final static Integer NEED_FASTDFS_CONFIG_NOTEXIST = 6000;
	
	/**
	 * FastDFS操作错误
	 */
	public final static Integer NEED_FASTDFS_ERROR = 6001;
	
	/**
	 * 其它参数空值异常
	 */
	public final static Integer NEED_OTHERS_NOTEXIST = 6002;
	
	/**
	 * 其它参数空值异常
	 */
	public final static Integer NEED_OTHERS_ILLEGAL = 6003;
}