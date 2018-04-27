package hg.service.image.spi.inter;

import hg.common.page.Pagination;
import hg.service.image.command.album.CreateAlbumCommand;
import hg.service.image.command.album.DeleteAlbumCommand;
import hg.service.image.command.album.ModifyAlbumCommand;
import hg.service.image.command.album.RestoreAlbumCommand;
import hg.service.image.pojo.dto.AlbumDTO;
import hg.service.image.pojo.exception.ImageException;
import hg.service.image.pojo.qo.AlbumQo;
import java.io.IOException;

/**
 * @类功能说明：相册服务Service接口
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午5:02:17
 */
public interface AlbumService_1 {
	/**
	 * @方法功能说明：创建相册
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午2:24:22
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	String createAlbum_1(CreateAlbumCommand command) throws ImageException;
	
	/**
	 * @方法功能说明：删除相册
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午2:24:36
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	void deleteAlbum_1(DeleteAlbumCommand command) throws ImageException, IOException;
	
	/**
	 * @方法功能说明：修改相册
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午2:24:45
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	void modifyAlbum_1(ModifyAlbumCommand command) throws ImageException;
	
	/**
	 * @方法功能说明：还原相册
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-16下午8:33:44
	 * @param command
	 * @throws ImageException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	void restoreAlbum_1(RestoreAlbumCommand command) throws ImageException, IOException;
	
	/**
	 * @方法功能说明: 查询数量 
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午2:24:53
	 * @修改内容：
	 * @param qo
	 * @return
	 */
	Integer queryCount_1(AlbumQo qo);
	
	/**
	 * @方法功能说明：查询相册
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午2:24:53
	 * @修改内容：
	 * @param qo
	 * @return
	 */
	AlbumDTO queryUniqueAlbum_1(AlbumQo qo);
	
	/**
	 * @方法功能说明：查询相册列表，分页
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午2:25:01
	 * @修改内容：
	 * @param pag
	 * @return
	 */
	Pagination queryAlbumPagination_1(AlbumQo qo);
}