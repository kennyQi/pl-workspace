package hg.system.service;

import hg.common.component.BaseService;
import hg.common.util.file.FdfsFileInfo;
import hg.system.command.imageSpec.ImageSpecCreateCommand;
import hg.system.command.imageSpec.ImageSpecDeleteCommand;
import hg.system.command.imageSpec.ImageSpecModifyCommand;
import hg.system.command.imageSpec.ImageSpecPubCommand;
import hg.system.command.imageSpec.ImageSpecUploadCommand;
import hg.system.exception.HGException;
import hg.system.model.image.ImageSpec;
import hg.system.qo.ImageSpecQo;

/**
 * 
 * @类功能说明：图片附件_service接口
 * @类修改者：zzb
 * @修改日期：2014年9月4日下午12:31:54
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月4日下午12:31:54
 *
 */
public interface ImageSpecService extends BaseService<ImageSpec, ImageSpecQo> {

	/**
	 * 
	 * @方法功能说明：附件添加
	 * @修改者名字：zzb
	 * @修改时间：2014年9月4日下午5:14:22
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws HGException
	 * @return:ImageSpec
	 * @throws
	 */
	public ImageSpec imageSpecCreate(ImageSpecCreateCommand command) throws HGException;
	
	/**
	 * 
	 * @方法功能说明：检查command合理性
	 * @修改者名字：zzb
	 * @修改时间：2014年9月4日下午5:00:37
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws HGException
	 * @return:void
	 * @throws
	 */
	public void imageSpecModifyCheck(ImageSpecModifyCommand command) throws HGException;
	
	/**
	 * @throws HGException 
	 * 
	 * @方法功能说明：图片附件裁剪保存
	 * @修改者名字：zzb
	 * @修改时间：2014年9月4日下午2:39:47
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:ImageSpec
	 * @throws
	 */
	public ImageSpec imageSpecModify(ImageSpecModifyCommand command) throws HGException;
	
	/**
	 * 
	 * @方法功能说明：图片附件删除
	 * @修改者名字：zzb
	 * @修改时间：2014年9月4日下午1:47:48
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void imageSpecDelete(ImageSpecDeleteCommand command) throws HGException ;


	/**
	 * 
	 * @方法功能说明：图片附件上传
	 * @修改者名字：zzb
	 * @修改时间：2014年9月17日上午9:19:08
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws HGException
	 * @return:void
	 * @throws
	 */
	public FdfsFileInfo imageSpecUpload(ImageSpecUploadCommand command) throws HGException ;
	
	
	/**
	 * 
	 * @方法功能说明：根据imageId和key获取图片公网链接
	 * @修改者名字：zzb
	 * @修改时间：2014年9月19日上午9:28:49
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @参数：@throws HGException
	 * @return:String
	 * @throws
	 */
	public String imageSpecQuePubUrl(ImageSpecPubCommand command) throws HGException ;
}
