package hg.system.service;

import hg.common.component.BaseService;
import hg.system.command.album.AlbumCreateByUseTypeCommand;
import hg.system.command.album.AlbumCreateCommand;
import hg.system.command.album.AlbumDeleteCommand;
import hg.system.command.album.AlbumModifyCommand;
import hg.system.exception.HGException;
import hg.system.model.image.Album;
import hg.system.qo.AlbumQo;

/**
 * 
 * @类功能说明：相册 service层接口
 * @类修改者：zzb
 * @修改日期：2014年9月1日下午4:25:28
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月1日下午4:25:28
 *
 */
public interface AlbumService extends BaseService<Album, AlbumQo> {

	/**
	 * 
	 * @方法功能说明：相册新增保存
	 * @修改者名字：zzb
	 * @修改时间：2014年9月2日上午10:00:15
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Album
	 * @throws
	 */
	public Album albumCreate(AlbumCreateCommand command);

	/**
	 * 
	 * @方法功能说明：检查checkId是否是albumId的子节点或本身节点
	 * @修改者名字：zzb
	 * @修改时间：2014年9月5日上午9:09:54
	 * @修改内容：
	 * @参数：@param albumId
	 * @参数：@param checkId
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public void checkIsChild(String albumId, String checkId) throws HGException ;
	
	/**
	 * 
	 * @方法功能说明：相册编辑保存
	 * @修改者名字：zzb
	 * @修改时间：2014年9月2日上午11:29:49
	 * @修改内容：
	 * @参数：@param command
	 * @return:Album
	 * @throws
	 */
	public Album albumModify(AlbumModifyCommand command) throws HGException ;

	/**
	 * 
	 * @方法功能说明：相册删除 检查 是否含子节点
	 * @修改者名字：zzb
	 * @修改时间：2014年9月2日下午2:27:22
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws UCException
	 * @return:void
	 * @throws
	 */
	public void albumDeleteCheck(AlbumDeleteCommand command) throws HGException;
	
	/**
	 * 
	 * @方法功能说明：相册删除
	 * @修改者名字：zzb
	 * @修改时间：2014年9月2日下午12:07:43
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void albumDelete(AlbumDeleteCommand command) throws HGException;

	/**
	 * 
	 * @方法功能说明：相册递归删除
	 * @修改者名字：zzb
	 * @修改时间：2014年9月3日下午1:31:39
	 * @修改内容：
	 * @参数：@param albumId
	 * @参数：@throws UCException
	 * @return:void
	 * @throws
	 */
	public void albumDeleteAllById(String albumId) throws HGException;

	
	/**
	 * 
	 * @方法功能说明：根据useType创建相册路径
	 * @修改者名字：zzb
	 * @修改时间：2014年9月29日下午6:35:19
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws HGException
	 * @return:Album
	 * @throws
	 */
	public Album albumCreateByUseType(AlbumCreateByUseTypeCommand command) throws HGException;


}
