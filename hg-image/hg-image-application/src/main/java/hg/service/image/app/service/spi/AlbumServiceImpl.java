package hg.service.image.app.service.spi;

import java.io.IOException;
import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.EntityConvertUtils;
import hg.service.image.app.service.local.AlbumLocalService;
import hg.service.image.command.album.CreateAlbumCommand;
import hg.service.image.command.album.DeleteAlbumCommand;
import hg.service.image.command.album.ModifyAlbumCommand;
import hg.service.image.command.album.RestoreAlbumCommand;
import hg.service.image.domain.model.Album;
import hg.service.image.domain.qo.AlbumLocalQo;
import hg.service.image.pojo.dto.AlbumDTO;
import hg.service.image.pojo.exception.ImageException;
import hg.service.image.pojo.qo.AlbumQo;
import hg.service.image.spi.inter.AlbumService_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @类功能说明：相册服务Service接口实现类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月4日 上午9:11:49
 */
@Service("spiAlbumServiceImpl_1")
public class AlbumServiceImpl implements AlbumService_1 {
	@Autowired
	private AlbumLocalService albumSer;

	/**
	 * 创建相册
	 * @throws ImageException
	 */
	@Override
	public String createAlbum_1(CreateAlbumCommand command) throws ImageException {
		return albumSer.createAlbum(command).getId();
	}

	/**
	 * 删除相册
	 * @throws ImageException
	 * @throws IOException 
	 */
	@Override
	public void deleteAlbum_1(DeleteAlbumCommand command) throws ImageException, IOException {
		albumSer.deleteAlbum(command);
	}

	/**
	 * 修改相册
	 * @throws ImageException
	 */
	@Override
	public void modifyAlbum_1(ModifyAlbumCommand command) throws ImageException{
		albumSer.modifyAlbum(command);
	}
	
	/**
	 * @方法功能说明：还原相册
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-16下午8:33:44
	 * @param command
	 * @throws ImageException
	 * @throws IOException
	 */
	public void restoreAlbum_1(RestoreAlbumCommand command) throws ImageException,IOException{
		albumSer.restoreAlbum(command);
	}

	/**
	 * 查询数量 
	 * @param qo
	 * @return
	 */
	@Override
	public Integer queryCount_1(AlbumQo qo) {
		AlbumLocalQo localQo = new AlbumLocalQo();
		if (null != qo)
			localQo = BeanMapperUtils.map(qo, AlbumLocalQo.class);
		return albumSer.queryCount(localQo);
	}
	
	/**
	 * 查询相册
	 */
	@Override
	public AlbumDTO queryUniqueAlbum_1(AlbumQo qo) {
		AlbumLocalQo localQo = new AlbumLocalQo();
		if (null != qo)
			localQo = BeanMapperUtils.map(qo, AlbumLocalQo.class);
		
		Album album = albumSer.queryUnique(localQo);
		if (null != album)
			return EntityConvertUtils.convertEntityToDto(album, AlbumDTO.class);
		return null;
	}

	/**
	 * 查询相册列表，分页
	 */
	@Override
	public Pagination queryAlbumPagination_1(AlbumQo qo) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(qo.getPageNo());
		pagination.setPageSize(qo.getPageSize());
		pagination.setCondition(BeanMapperUtils.map(qo, AlbumLocalQo.class));// Qo转换，并重新设置查询条件
		pagination = albumSer.queryPagination(pagination);
		
		// Model至DTO转换
		List<?> list = pagination.getList();
		if (null == list || list.size() < 1)
			list = null;
		else
			list = EntityConvertUtils.convertEntityToDtoList(list,
					AlbumDTO.class);
		pagination.setList(list);
		pagination.setCondition(qo);
		// 返回
		return pagination;
	}
}