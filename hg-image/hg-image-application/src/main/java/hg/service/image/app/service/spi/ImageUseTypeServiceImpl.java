package hg.service.image.app.service.spi;

import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.EntityConvertUtils;
import hg.service.image.app.service.local.ImageUseTypeLocalService;
import hg.service.image.command.image.usetype.CreateImageUseTypeCommand;
import hg.service.image.command.image.usetype.DeleteImageUseTypeCommand;
import hg.service.image.command.image.usetype.ModifyImageUseTypeCommand;
import hg.service.image.domain.model.ImageUseType;
import hg.service.image.domain.qo.ImageUseTypeLocalQo;
import hg.service.image.pojo.dto.ImageUseTypeDTO;
import hg.service.image.pojo.exception.ImageException;
import hg.service.image.pojo.qo.ImageUseTypeQo;
import hg.service.image.spi.inter.ImageUseTypeService_1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @类功能说明：明细规格用途服务Service接口实现类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月4日 上午9:11:49
 */
@Service("spiImageUseTypeServiceImpl_1")
public class ImageUseTypeServiceImpl implements ImageUseTypeService_1 {
	@Autowired
	private ImageUseTypeLocalService useSer;

	/**
	 * 创建图片用途KEY
	 * @throws ImageException
	 */
	@Override
	public void createImageSpecKeyUseType_1(CreateImageUseTypeCommand command) throws ImageException {
		useSer.createImageSpecKeyUseType(command);
	}

	/**
	 * 删除图片用途KEY
	 * @throws ImageException
	 */
	@Override
	public void deleteImageSpecKeyUseType_1(DeleteImageUseTypeCommand command) throws ImageException {
		useSer.deleteImageSpecKeyUseType(command);
	}

	/**
	 * 修改图片用途KEY
	 * @throws ImageException
	 */
	@Override
	public void modifyImageSpecKeyUseType_1(ModifyImageUseTypeCommand command) throws ImageException {
		useSer.modifyImageSpecKeyUseType(command);
	}

	/**
	 * 查询数量 
	 * @param qo
	 * @return
	 */
	@Override
	public Integer queryCount_1(ImageUseTypeQo qo){
		ImageUseTypeLocalQo localQo = new ImageUseTypeLocalQo();
		if (null != qo)
			localQo = BeanMapperUtils.map(qo, ImageUseTypeLocalQo.class);
		return useSer.queryCount(localQo);
	}
	
	/**
	 * 查询图片用途KEY
	 */
	@Override
	public ImageUseTypeDTO queryUniqueImageSpecKeyUseType_1(ImageUseTypeQo qo) {
		ImageUseTypeLocalQo localQo = new ImageUseTypeLocalQo();
		if (null != qo)
			localQo = BeanMapperUtils.map(qo, ImageUseTypeLocalQo.class);
		
		ImageUseType useKey = useSer.queryUnique(localQo);
		if (null != useKey) {
			return EntityConvertUtils.convertEntityToDto(useKey,ImageUseTypeDTO.class);
		}
		return null;
	}

	/**
	 * 查询图片用途KEY列表，分页
	 */
	@Override
	public Pagination queryImageSpecKeyUseTypePagination_1(ImageUseTypeQo qo) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(qo.getPageNo());
		pagination.setPageSize(qo.getPageSize());
		pagination.setCondition(BeanMapperUtils.map(qo,ImageUseTypeLocalQo.class));
		Pagination pag = useSer.queryPagination(pagination);
		
		// Model至DTO转换
		List<?> list = pag.getList();
		if (null == list || list.size() < 1)
			list = null;
		else
			list = EntityConvertUtils.convertEntityToDtoList(list,ImageUseTypeDTO.class);
		pag.setList(list);
		pag.setCondition(qo);
		// 返回
		return pag;
	}

}